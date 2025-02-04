
package com.github.mavenautoprimer;

/*
 * Copyright (C) 2014 David A. Parry <d.a.parry@leeds.ac.uk>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program was written by David A. Parry and edited by
 * Bert Gold <bgold04@gmail.com> and ChatGPT
 * February 4, 2025
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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GeneCoordinatesFetcher extends GenomicBase {

    private static final List<String> FIELDS = Arrays.asList("name", "chrom", "txStart", "txEnd", "cdsStart", "cdsEnd", "exonCount", "exonStarts", "exonEnds", "strand", "name2");
    @Override
    public ArrayList<GeneDetails> getGeneFromSymbol(String symbol, String build, String db) throws SQLException {
        ArrayList<GeneDetails> transcripts = new ArrayList<>();
        String query = "SELECT " + String.join(", ", FIELDS) + " FROM " + build + "." + db + " WHERE name2=?";
        try (Connection conn = getConnection();
                    PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, symbol);
            try (ResultSet rs = ps.executeQuery()) {
                transcripts.addAll(parseGeneDetailsFromResultSet(rs));
            }
        } catch (SQLException ex) {
            throw new SQLException("Error retrieving gene data for symbol: " + symbol, ex);
        }
        return transcripts;
    }
    @Override
    public ArrayList<GeneDetails> getGeneFromID(String id, String build, String db) throws SQLException, GetGeneFromIDException {
        return super.getGeneFromID(id, build, db);
    }
    private ArrayList<GeneDetails> parseGeneDetailsFromResultSet(ResultSet rs) throws SQLException {
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
            geneDetails.setSymbol(rs.getString("name2"));
            transcripts.add(geneDetails);
        }
        return transcripts;
    }
}
