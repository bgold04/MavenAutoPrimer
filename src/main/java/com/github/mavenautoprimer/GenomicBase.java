


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

package com.github.mavenautoprimer;

import com.github.mavenautoprimer.GeneDetails;
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
