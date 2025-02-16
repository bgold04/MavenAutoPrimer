
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
 *
 * @author David A. Parry <d.a.parry@leeds.ac.uk> and edited by Bert Gold, PhD <bgold04@gmail.com>
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.github.mavenautoprimer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class UcscGeneCoordinatesFetcher extends GeneCoordinatesFetcher {

    private static final List<String> FIELDS = Arrays.asList("name", "chrom", "txStart", "txEnd", "cdsStart", "cdsEnd", "exonCount", "exonStarts", "exonEnds", "strand");

    @Override
    public ArrayList<GeneDetails> getGeneFromSymbol(String symbol, String build, String db) throws SQLException {
        ArrayList<GeneDetails> transcripts = new ArrayList<>();
        String query = "SELECT kgID FROM " + build + ".kgXref WHERE geneSymbol=?";

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
        return transcripts;
    }

    @Override
    public ArrayList<GeneDetails> getGeneFromId(String id, String build, String db) throws SQLException {
        ArrayList<GeneDetails> transcripts = new ArrayList<>();
        String query = "SELECT " + String.join(", ", FIELDS) + " FROM " + build + "." + db + " WHERE name=?";
        String symbol = "";

        try (Connection conn = getConnection();
                    PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                transcripts.addAll(getTranscriptsFromResultSet(rs));
            }
        }

        // Retrieve gene symbol from kgXref
        query = "SELECT geneSymbol FROM " + build + ".kgXref WHERE kgID=?";
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

        return transcripts;
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
        return transcripts;
    }
}
