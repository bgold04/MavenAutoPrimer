 * Copyright (C) 2014 David A. Parry <d.a.parry@leeds.ac.uk>
 * (at your option) any later version.
public class UcscGeneCoordinatesFetcher extends GeneCoordinatesFetcher {
    private static final List<String> FIELDS = Arrays.asList("name", "chrom", "txStart", "txEnd", "cdsStart", "cdsEnd", "exonCount", "exonStarts", "exonEnds", "strand");
    public ArrayList<GeneDetails> getGeneFromSymbol(String symbol, String build, String db) throws SQLException {
        ArrayList<GeneDetails> transcripts = new ArrayList<>();
        try (Connection conn = getConnection();
                    PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, symbol);
            try (ResultSet rs2 = ps.executeQuery()) {
                while (rs2.next()) {
                    String kgID = rs2.getString("kgID");
                    transcripts.addAll(getGeneFromId(kgID, build, db));
                }
            }
        }
    }
    public ArrayList<GeneDetails> getGeneFromId(String id, String build, String db) throws SQLException {
        ArrayList<GeneDetails> transcripts = new ArrayList<>();
        String query = "SELECT " + String.join(", ", FIELDS) + " FROM " + build + "." + db + " WHERE name=?";
        try (Connection conn = getConnection();
                    PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                transcripts.addAll(getTranscriptsFromResultSet(rs));
            }
        }
        try (Connection conn = getConnection();
                    PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, id);
            try (ResultSet rs2 = ps.executeQuery()) {
                if (rs2.next()) {
                    symbol = rs2.getString("geneSymbol");
                }
            }
        }
        for (GeneDetails gene : transcripts) {
            gene.setSymbol(symbol);
        }
    }
    protected ArrayList<GeneDetails> getTranscriptsFromResultSet(ResultSet rs) throws SQLException {
        ArrayList<GeneDetails> transcripts = new ArrayList<>();
        while (rs.next()) {
            GeneDetails geneDetails = new GeneDetails();
            geneDetails.setId(rs.getString("name"));
            geneDetails.setChromosome(rs.getString("chrom"));
            geneDetails.setTxStart(rs.getInt("txStart"));
            geneDetails.setTxEnd(rs.getInt("txEnd"));
            geneDetails.setCdsStart(rs.getInt("cdsStart"));
            geneDetails.setCdsEnd(rs.getInt("cdsEnd"));
            geneDetails.setTotalExons(rs.getInt("exonCount"));
            geneDetails.setStrand(rs.getString("strand"));
            transcripts.add(geneDetails);
        }
    }
}
