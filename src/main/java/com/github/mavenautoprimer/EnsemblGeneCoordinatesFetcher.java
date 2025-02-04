
package com.github.mavenautoprimer;

/*
 * Copyright (C) 2014 David A. Parry <d.a.parry@leeds.ac.uk>
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

/**
 *
 * @author David A. Parry <d.a.parry@leeds.ac.uk> and
 * edited by Bert Gold <bgold04@gmail.com> and ChatGPT
 * February 4, 2025
 *
 */


import java.sql.*;
import java.util.*;

public class EnsemblGeneCoordinatesFetcher extends GeneCoordinatesFetcher {
    private static final List<String> FIELDS = Arrays.asList("name", "chrom", "txStart", "txEnd", "cdsStart", "cdsEnd", "exonCount", "exonStarts", "exonEnds", "strand");

    @Override
    public ArrayList<GeneDetails> getGeneFromSymbol(String symbol, String build, String db) throws SQLException, GetGeneFromSymbolException {
        ArrayList<GeneDetails> transcripts = new ArrayList<>();

        try (Connection conn = getConnection()) {
            if (tableExists(conn, build, "ensemblToGeneName")) {
                transcripts.addAll(fetchFromEnsemblToGeneName(conn, symbol, build, db));
            } else {
                transcripts = getTranscriptsViaKgId(symbol, build, db, String.join(", ", FIELDS));
            }
        }
        return transcripts;
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
        String query = "SELECT name FROM " + build + ".ensemblToGeneName WHERE value = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, symbol);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    transcripts.addAll(super.getGeneFromID(rs.getString("name"), build, db));
                }
            }
        }
        return transcripts;
    }

    private ArrayList<GeneDetails> getTranscriptsViaKgId(String symbol, String build, String db, String fieldsToRetrieve) throws SQLException {
        ArrayList<GeneDetails> transcripts = new ArrayList<>();

        try (Connection conn = getConnection()) {
            if (!tableExists(conn, build, "knownToEnsembl")) {
                return transcripts;
            }

            List<String> kgIDs = fetchKgIDs(conn, symbol, build);
            if (kgIDs.isEmpty()) return transcripts;

            List<String> ensemblIDs = fetchEnsemblIDs(conn, kgIDs, build);
            if (ensemblIDs.isEmpty()) return transcripts;

            transcripts.addAll(fetchTranscriptsByEnsemblIDs(conn, ensemblIDs, build, db, fieldsToRetrieve));
        }
        return transcripts;
    }

    private List<String> fetchKgIDs(Connection conn, String symbol, String build) throws SQLException {
        List<String> kgIDs = new ArrayList<>();
        String query = "SELECT kgID FROM " + build + ".kgXref WHERE geneSymbol = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, symbol);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    kgIDs.add(rs.getString("kgID"));
                }
            }
        }
        return kgIDs;
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
        return ensemblIDs;
    }

    private List<GeneDetails> fetchTranscriptsByEnsemblIDs(Connection conn, List<String> ensemblIDs, String build, String db, String fieldsToRetrieve) throws SQLException {
        List<GeneDetails> transcripts = new ArrayList<>();
        String query = "SELECT " + fieldsToRetrieve + " FROM " + build + "." + db + " WHERE name IN (" + String.join(", ", Collections.nCopies(ensemblIDs.size(), "?")) + ")";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            for (int i = 0; i < ensemblIDs.size(); i++) {
                stmt.setString(i + 1, ensemblIDs.get(i));
            }
            try (ResultSet rs = stmt.executeQuery()) {
                transcripts.addAll(getTranscriptsFromResultSet(rs, new ArrayList<>(Arrays.asList(fieldsToRetrieve.split(", ")))));
            }
        }
        return transcripts;
    }

    @Override
    public ArrayList<GeneDetails> getGeneFromID(String id, String build, String db) throws SQLException, GetGeneFromIDException {
        ArrayList<GeneDetails> transcripts = super.getGeneFromID(id, build, db);
        if (transcripts.isEmpty()) {
            String symbol = getSymbolFromEnsemblToName(id, build);
            System.out.println("Fallback to Ensembl symbol resolution for " + id + ": " + symbol);
        }
        return transcripts;
    }

    private String getSymbolFromEnsemblToName(String id, String build) throws SQLException {
        String query = "SELECT value FROM " + build + ".ensemblToGeneName WHERE name = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("value");
                }
            }
        }
        return "";
    }
}


