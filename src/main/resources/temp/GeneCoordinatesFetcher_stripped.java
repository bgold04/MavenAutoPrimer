 * Copyright (C) 2014 David A. Parry <d.a.parry@leeds.ac.uk>
 * (at your option) any later version.
public class GeneCoordinatesFetcher {
    static {
        try {
            conn = DriverManager.getConnection(DB_URL);
        } catch (SQLException ex) {
            throw new ExceptionInInitializerError("Database connection initialization failed: " + ex.getMessage());
        }
    }
    protected static Connection getConnection() throws SQLException {
        if (conn == null || conn.isClosed()) {
            conn = DriverManager.getConnection(DB_URL);
        }
    }
    private static final List<String> FIELDS = Arrays.asList("name", "chrom", "txStart", "txEnd", "cdsStart", "cdsEnd", "exonCount", "exonStarts", "exonEnds", "strand", "name2");
    public ArrayList<GeneDetails> getGeneFromSymbol(String symbol, String build, String db) throws SQLException {
        ArrayList<GeneDetails> transcripts = new ArrayList<>();
        String query = "SELECT " + String.join(", ", FIELDS) + " FROM " + build + "." + db + " WHERE name2=?";
        try (Connection conn = getConnection();
                    PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, symbol);
            try (ResultSet rs = ps.executeQuery()) {
                transcripts.addAll(getTranscriptsFromResultSet(rs));
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
    }
    protected ArrayList<GeneDetails> getTranscriptsFromResultSet(ResultSet rs) throws SQLException {
        ArrayList<GeneDetails> transcriptsToReturn = new ArrayList<>();
        while (rs.next()) {
            HashMap<String, String> geneCoords = new HashMap<>();
            for (String f : FIELDS) {
                geneCoords.put(f, rs.getString(f));
            }
            try {
                transcriptsToReturn.add(geneHashToGeneDetails(geneCoords));
            } catch (GetGeneExonsException e) {
                throw new SQLException("Error parsing exons from database results", e);
            }
        }
    }
    protected GeneDetails geneHashToGeneDetails(HashMap<String, String> gene) throws GetGeneExonsException {
        GeneDetails geneDetails = new GeneDetails();
        geneDetails.setSymbol(gene.get("name2"));
        geneDetails.setId(gene.get("name"));
        geneDetails.setStrand(gene.get("strand"));
        geneDetails.setChromosome(gene.get("chrom"));
        geneDetails.setTxStart(Integer.parseInt(gene.get("txStart")));
        geneDetails.setTxEnd(Integer.parseInt(gene.get("txEnd")));
        geneDetails.setCdsStart(Integer.parseInt(gene.get("cdsStart")));
        geneDetails.setCdsEnd(Integer.parseInt(gene.get("cdsEnd")));
        geneDetails.setTotalExons(Integer.parseInt(gene.get("exonCount")));
        try {
            geneDetails.setExons(gene.get("exonStarts"), gene.get("exonEnds"));
        } catch (GeneDetails.GeneExonsException ex) {
            throw new GetGeneExonsException("Error parsing exons for transcript " + geneDetails.getId() + ", gene " + geneDetails.getSymbol(), ex);
        }
    }
    public class GetGeneExonsException extends Exception {
        public GetGeneExonsException() {
            super();
        }
        public GetGeneExonsException(String message) {
            super(message);
        }
        public GetGeneExonsException(String message, Throwable cause) {
            super(message, cause);
        }
        public GetGeneExonsException(Throwable cause) {
            super(cause);
        }
    }
}
