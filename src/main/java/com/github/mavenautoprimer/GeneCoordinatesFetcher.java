/*
 * Copyright (C) 2014 David A. Parry <d.a.parry@leeds.ac.uk>
 *
 * This superclass was extensively modified by Bert Gold, PhD <bgold04@gmail.com>
 *     and ChatGPT on February 16, 2025
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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class GeneCoordinatesFetcher {

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
        return transcripts;
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
        return transcripts;
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
        return transcriptsToReturn;
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
        return geneDetails;
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
