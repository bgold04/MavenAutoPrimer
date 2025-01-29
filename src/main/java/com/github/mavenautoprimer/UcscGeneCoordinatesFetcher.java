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

public class UcscGeneCoordinatesFetcher extends GeneCoordinatesFetcher {
    final ArrayList<String> fields = new ArrayList<>(Arrays.asList("name", "chrom", "txStart", "txEnd", "cdsStart", "cdsEnd", "exonCount", "exonStarts", "exonEnds", "strand"));

    public ArrayList<GeneDetails> getGeneFromSymbol(String symbol, String build, String db) throws SQLException, GetGeneExonException {
        ArrayList<GeneDetails> transcripts = new ArrayList<>();
        String fieldsToRetrieve = String.join(", ", fields);
        
        String query = "SELECT kgID, geneSymbol FROM " + build + ".kgXref WHERE geneSymbol='" + symbol + "'";
        PreparedStatement ps = conn.prepareStatement(query);
        ResultSet rs2 = ps.executeQuery();

        while (rs2.next()) {
            query = "SELECT " + fieldsToRetrieve + " FROM " + build + "." + db + " WHERE name='" + rs2.getString("kgID") + "'";
            ResultSet rs = ps.executeQuery(query);
            transcripts.addAll(getTranscriptsFromResultSet(rs, symbol));
        }
        return transcripts;
    }

    public ArrayList<GeneDetails> getGeneFromId(String id, String build, String db) throws SQLException, GetGeneExonException {
        String fieldsToRetrieve = String.join(", ", fields);
        String query = "SELECT " + fieldsToRetrieve + " FROM " + build + "." + db + " WHERE name='" + id + "'";
        PreparedStatement ps = conn.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        query = "SELECT kgID, geneSymbol FROM " + build + ".kgXref WHERE kgID='" + id + "'";
        ResultSet rs2 = ps.executeQuery();

        String symbol = "";
        while (rs2.next()) {
            symbol = rs2.getString("geneSymbol");
        }
        return getTranscriptsFromResultSet(rs, symbol);
    }

    protected ArrayList<GeneDetails> getTranscriptsFromResultSet(ResultSet rs, String symbol) throws SQLException, GetGeneExonException {
        ArrayList<HashMap<String, String>> genes = new ArrayList<>();
        while (rs.next()) {
            HashMap<String, String> geneCoords = new HashMap<>();
            for (String f : fields) {
                geneCoords.put(f, rs.getString(f));
            }
            geneCoords.put("name2", symbol);
            genes.add(geneCoords);
        }

        ArrayList<GeneDetails> transcriptsToReturn = new ArrayList<>();
        for (HashMap<String, String> gene : genes) {
            transcriptsToReturn.add(GeneHashToGeneDetails(gene));
        }
        return transcriptsToReturn;
    }

    private GeneDetails GeneHashToGeneDetails(HashMap<String, String> gene) {
        return new GeneDetails(gene.get("name"), gene.get("name2"));
    }
}
