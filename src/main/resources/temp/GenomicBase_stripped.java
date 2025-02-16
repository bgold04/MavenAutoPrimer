 * (at your option) any later version.
public abstract class GenomicBase {
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
    public abstract List<GeneDetails> getGeneFromSymbol(String symbol, String build, String db) throws SQLException;
    public List<GeneDetails> getGeneFromID(String id, String build, String db) throws SQLException {
        List<GeneDetails> transcripts = new ArrayList<>();
        try (Connection conn = getConnection();
                    PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                transcripts.addAll(parseGeneDetailsFromResultSet(rs));
            }
        } catch (SQLException ex) {
            throw new SQLException("Error retrieving gene by ID: " + id, ex);
        }
    }
    protected List<GeneDetails> parseGeneDetailsFromResultSet(ResultSet rs) throws SQLException {
        List<GeneDetails> transcripts = new ArrayList<>();
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
    protected void logError(String message, Exception ex) {
        System.err.println(message + ": " + ex.getMessage());
        ex.printStackTrace();
    }
    public static class GetGeneFromSymbolException extends Exception {
        public GetGeneFromSymbolException(String message) {
            super(message);
        }
        public GetGeneFromSymbolException(String message, Throwable cause) {
            super(message, cause);
        }
    }
    public static class GetGeneFromIDException extends Exception {
        public GetGeneFromIDException(String message) {
            super(message);
        }
        public GetGeneFromIDException(String message, Throwable cause) {
            super(message, cause);
        }
    }
    public static class GetGeneExonException extends Exception {
        public GetGeneExonException(String message) {
            super(message);
        }
        public GetGeneExonException(String message, Throwable cause) {
            super(message, cause);
        }
    }
    public static class GetSnpCoordinatesException extends Exception {
        public GetSnpCoordinatesException(String message) {
            super(message);
        }
        public GetSnpCoordinatesException(String message, Throwable cause) {
            super(message, cause);
        }
    }
    public static class GeneDetailsException extends Exception {
        public GeneDetailsException(String message) {
            super(message);
        }
        public GeneDetailsException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
