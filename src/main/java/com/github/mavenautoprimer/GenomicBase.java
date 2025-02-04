
package com.github.mavenautoprimer;

/*
 * This superclass is in public domain
 * Written by Bert Gold, PhD <bgold04@gmail.com> and ChatGPT
 *
 * Revised February 4, 2025
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class GenomicBase {

    private static final String DB_URL = "jdbc:mysql://genome-mysql.cse.ucsc.edu?user=genomep&password=password&no-auto-rehash";
    private static Connection conn;

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
        return conn;
    }

    public abstract List<GeneDetails> getGeneFromSymbol(String symbol, String build, String db) throws SQLException;

    public List<GeneDetails> getGeneFromID(String id, String build, String db) throws SQLException {
        List<GeneDetails> transcripts = new ArrayList<>();
        String query = "SELECT name, chrom, txStart, txEnd, cdsStart, cdsEnd, exonCount, exonStarts, exonEnds, strand FROM " + build + "." + db + " WHERE name=?";

        try (Connection conn = getConnection();
                    PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                transcripts.addAll(parseGeneDetailsFromResultSet(rs));
            }
        } catch (SQLException ex) {
            throw new SQLException("Error retrieving gene by ID: " + id, ex);
        }
        return transcripts;
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
        return transcripts;
    }

    protected void logError(String message, Exception ex) {
        System.err.println(message + ": " + ex.getMessage());
        ex.printStackTrace();
    }
    public class KgID {
        public String kgID;
        public String getkgID() {
            return kgID;
        } public void setKgID(String kgID,String name, String id) {
            this.kgID = kgID = name = id;
        }
    }
    public static class GenomicException extends Exception {
        public GenomicException(String message) {
            super(message);
        }
        public GenomicException(String message, Throwable cause) {
            super(message, cause);
        }
    }
    public class EnsemblToNameException extends Exception {
        public EnsemblToNameException() {
            super();
        }
        public EnsemblToNameException(String message) {
            super(message);
        }
        public EnsemblToNameException(String message, Throwable cause) {
            super(message, cause);
        }
        public EnsemblToNameException(Throwable cause) {
            super(cause);
        }
    }
    public class EnsemblTranscriptException  extends Exception {
        public EnsemblTranscriptException() {
            super();
        }
        public EnsemblTranscriptException (String message) {
            super(message);
        }
        public EnsemblTranscriptException (String message, Throwable cause) {
            super(message, cause);
        }
        public EnsemblTranscriptException (Throwable cause) {
            super(cause);
        }
    }
    public class GeneDetailsException extends Exception {
        public GeneDetailsException() {
            super();
        } public GeneDetailsException(String message) {
            super(message);
        } public GeneDetailsException(String message, Throwable cause) {
            super(message, cause);
        } public GeneDetailsException(Throwable cause) {
            super(cause);
        }
    }
    public class GetGeneExonException extends Exception {
        public GetGeneExonException() {
            super();
        } public GetGeneExonException(String message) {
            super(message);
        } public GetGeneExonException(String message, Throwable cause) {
            super(message, cause);
        } public GetGeneExonException(Throwable cause) {
            super(cause);
        }
    }
    public class GetGeneFromIDException extends Exception {
        public GetGeneFromIDException() {
            super();
        } public GetGeneFromIDException(String message) {
            super(message);
        } public GetGeneFromIDException(String message, Throwable cause) {
            super(message, cause);
        } public GetGeneFromIDException(Throwable cause) {
            super(cause);
        }
    }
    public class GetGeneFromSymbolException extends Exception {
        public GetGeneFromSymbolException() {
            super();
        } public GetGeneFromSymbolException(String message) {
            super(message);
        } public GetGeneFromSymbolException(String message, Throwable cause) {
            super(message, cause);
        } public GetGeneFromSymbolException(Throwable cause) {
            super(cause);
        }
    }
    public class GetSnpCoordinatesException extends Exception {
        public GetSnpCoordinatesException() {
            super();
        } public GetSnpCoordinatesException(String message) {
            super(message);
        } public GetSnpCoordinatesException(String message, Throwable cause) {
            super(message, cause);
        } public GetSnpCoordinatesException(Throwable cause) {
            super(cause);
        }
    }
    public class HashToDetailException extends Exception {
        public HashToDetailException() {
            super();
        } public HashToDetailException(String message) {
            super(message);
        } public HashToDetailException(String message, Throwable cause) {
            super(message, cause);
        } public HashToDetailException(Throwable cause) {
            super(cause);
        }
    }
    public class KgIdTranscriptException extends Exception {
        public KgIdTranscriptException() {
            super();
        } public KgIdTranscriptException(String message) {
            super(message);
        } public KgIdTranscriptException(String message, Throwable cause) {
            super(message, cause);
        } public KgIdTranscriptException(Throwable cause) {
            super(cause);
        }
    }
    public class SamHeaderFromDictException extends Exception {
        public SamHeaderFromDictException() {
            super();
        }
        public SamHeaderFromDictException(String message) {
            super(message);
        }
        public SamHeaderFromDictException(String message, Throwable cause) {
            super(message, cause);
        }
        public SamHeaderFromDictException(Throwable cause) {
            super(cause);
        }
    }
    public class SequenceFromFastaException extends Exception {
        public SequenceFromFastaException() {
            super();
        }
        public SequenceFromFastaException(String message) {
            super(message);
        }
        public SequenceFromFastaException(String message, Throwable cause) {
            super(message, cause);
        }
        public SequenceFromFastaException(Throwable cause) {
            super(cause);
        }
    }
    public class TranscriptsFromrsException extends Exception {
        public TranscriptsFromrsException() {
            super();
        } public TranscriptsFromrsException(String message) {
            super(message);
        } public TranscriptsFromrsException(String message, Throwable cause) {
            super(message, cause);
        } public TranscriptsFromrsException(Throwable cause) {
            super(cause);
        }
    }
    public class TryCatchException extends Exception {
        public TryCatchException() {
            super();
        } public TryCatchException(String message) {
            super(message);
        } public TryCatchException(String message, Throwable cause) {
            super(message, cause);
        } public TryCatchException(Throwable cause) {
            super(cause);
        }
    }
    public static class GetGeneFromSymbolException extends Exception {
        public GetGeneFromSymbolException() {
            super();
        } public GetGeneFromSymbolException(String message) {
            super(message);
        } public GetGeneFromSymbolException(String message, Throwable cause) {
            super(message, cause);
        } public GetGeneFromSymbolException(Throwable cause) {
            super(cause);
        }
    }
    public class getEnsemblTranscriptException extends Exception {
        public getEnsemblTranscriptException() {
            super();
        } public getEnsemblTranscriptException(String message) {
            super(message);
        } public getEnsemblTranscriptException(String message, Throwable cause) {
            super(message, cause);
        } public getEnsemblTranscriptException(Throwable cause) {
            super(cause);
        }
    }
// Helper method to display alerts with exceptions
    private void showAlert(String title, String header, Throwable ex) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle(title);
        errorAlert.setHeaderText(header);
        errorAlert.setContentText(ex.getMessage());
        errorAlert.showAndWait();
        ex.printStackTrace();
    }
// Helper method to display error alerts
    private void showErrorAlert(String title, String content, Exception ex) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(content + "\n\nSee exception below:\n" + ex.getMessage());
        alert.showAndWait();
    }
}