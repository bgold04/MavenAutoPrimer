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
@Override
public ArrayList<GeneDetails> getGeneFromSymbol(String symbol, String build, String db) 
        throws SQLException, GetGeneExonsException {
    ArrayList<GeneDetails> transcripts = new ArrayList<>();

    // Open connection and execute query
    try (Connection conn = DriverManager.getConnection(
            "jdbc:mysql://genome-mysql.cse.ucsc.edu?user=genomep&password=password&no-auto-rehash")) {

        // Step 1: Retrieve UCSC kgID
        String kgQuery = "SELECT kgID FROM " + build + ".kgXref WHERE geneSymbol=?";
        try (PreparedStatement ps = conn.prepareStatement(kgQuery)) {
            ps.setString(1, symbol);
            try (ResultSet rs2 = ps.executeQuery()) {
                while (rs2.next()) {
                    String kgID = rs2.getString("kgID");

                    // Step 2: Retrieve transcript details using kgID
                    String fieldsToRetrieve = String.join(", ", fields);
                    String query = "SELECT " + fieldsToRetrieve + " FROM " + build + "." + db + " WHERE name=?";
                    try (PreparedStatement ps2 = conn.prepareStatement(query)) {
                        ps2.setString(1, kgID);
                        try (ResultSet rs = ps2.executeQuery()) {
                            transcripts.addAll(getTranscriptsFromResultSet(rs, symbol));
                        }
                    }
                }
            }
        }
    }

    return transcripts;
}

@Override
public ArrayList<GeneDetails> getGeneFromID(String id, String build, String db) throws SQLException, GetGeneFromIDException {
    ArrayList<GeneDetails> transcripts = super.getGeneFromID(id, build, db);
    // Special UCSC handling if needed
    if (transcripts.isEmpty()) {
        System.out.println("Warning: UCSC-specific fallback may be needed for " + id);
    }
    return transcripts;
}

    protected ArrayList<GeneDetails> getTranscriptsFromResultSet(ResultSet rs, String symbol) throws SQLException, GetGeneExonException {
        ArrayList<HashMap<String, String>> genes = new ArrayList<>();
        while (rs.next()) {
            HashMap<String, String> geneCoords = new HashMap<>();
            for (String f : fields) {
                geneCoords.put(f, rs.getString(f));
            }            geneCoords.put("name2", symbol);
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
