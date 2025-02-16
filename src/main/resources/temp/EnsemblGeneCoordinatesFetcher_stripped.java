 * Copyright (C) 2014 David A. Parry <d.a.parry@leeds.ac.uk>
 * (at your option) any later version.
public class EnsemblGeneCoordinatesFetcher extends UcscGeneCoordinatesFetcher {
    private static final List<String> FIELDS = Arrays.asList("name", "chrom", "txStart", "txEnd", "cdsStart", "cdsEnd", "exonCount", "exonStarts", "exonEnds", "strand");
    public ArrayList<GeneDetails> getGeneFromSymbol(String symbol, String build, String db) throws SQLException {
        ArrayList<GeneDetails> transcripts = new ArrayList<>();
        try (Connection conn = getConnection()) {
            if (tableExists(conn, build, "ensemblToGeneName")) {
                transcripts.addAll(fetchFromEnsemblToGeneName(conn, symbol, build, db));
            } else {
                transcripts = getTranscriptsViaKgId(symbol, build, db, String.join(", ", FIELDS));
            }
        }
    }
    private boolean tableExists(Connection conn, String build, String tableName) throws SQLException {
        String query = "SELECT COUNT(*) FROM information_schema.tables WHERE table_schema = ? AND table_name = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, build);
            stmt.setString(2, tableName);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }
    private ArrayList<GeneDetails> fetchFromEnsemblToGeneName(Connection conn, String symbol, String build, String db) throws SQLException {
        ArrayList<GeneDetails> transcripts = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, symbol);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    transcripts.addAll(super.getGeneFromId(rs.getString("name"), build, db));
                }
            }
        }
    }
    private ArrayList<GeneDetails> getTranscriptsViaKgId(String symbol, String build, String db, String fieldsToRetrieve) throws SQLException {
        ArrayList<GeneDetails> transcripts = new ArrayList<>();
        try (Connection conn = getConnection()) {
            if (!tableExists(conn, build, "knownToEnsembl")) {
            }
            List<String> kgIDs = fetchKgIDs(conn, symbol, build);
            if (kgIDs.isEmpty()) return transcripts;
            List<String> ensemblIDs = fetchEnsemblIDs(conn, kgIDs, build);
            if (ensemblIDs.isEmpty()) return transcripts;
            transcripts.addAll(fetchTranscriptsByEnsemblIDs(conn, ensemblIDs, build, db, fieldsToRetrieve));
        }
    }
    private List<String> fetchKgIDs(Connection conn, String symbol, String build) throws SQLException {
        List<String> kgIDs = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, symbol);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    kgIDs.add(rs.getString("kgID"));
                }
            }
        }
    }
    private List<String> fetchEnsemblIDs(Connection conn, List<String> kgIDs, String build) throws SQLException {
        List<String> ensemblIDs = new ArrayList<>();
        String query = "SELECT value FROM " + build + ".knownToEnsembl WHERE name IN (" + String.join(", ", Collections.nCopies(kgIDs.size(), "?")) + ")";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            for (int i = 0; i < kgIDs.size(); i++) {
                stmt.setString(i + 1, kgIDs.get(i));
            }
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ensemblIDs.add(rs.getString("value"));
                }
            }
        }
    }
    private List<GeneDetails> fetchTranscriptsByEnsemblIDs(Connection conn, List<String> ensemblIDs, String build, String db, String fieldsToRetrieve) throws SQLException {
        List<GeneDetails> transcripts = new ArrayList<>();
        String query = "SELECT " + fieldsToRetrieve + " FROM " + build + "." + db + " WHERE name IN (" + String.join(", ", Collections.nCopies(ensemblIDs.size(), "?")) + ")";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            for (int i = 0; i < ensemblIDs.size(); i++) {
                stmt.setString(i + 1, ensemblIDs.get(i));
            }
            try (ResultSet rs = stmt.executeQuery()) {
                transcripts.addAll(getTranscriptsFromResultSet(rs));
            }
        }
    }
    public ArrayList<GeneDetails> getGeneFromId(String id, String build, String db) throws SQLException {
        ArrayList<GeneDetails> transcripts = super.getGeneFromId(id, build, db);
        if (transcripts.isEmpty()) {
            String symbol = getSymbolFromEnsemblToName(id, build);
            System.out.println("Fallback to Ensembl symbol resolution for " + id + ": " + symbol);
        }
    }
    private String getSymbolFromEnsemblToName(String id, String build) throws SQLException {
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("value");
                }
            }
        }
    }
}
