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
package com.github.mavenautoprimer;

import static com.github.autoprimer3A.GetGeneCoordinates.conn;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 *
 * @author David A. Parry <d.a.parry@leeds.ac.uk>
 */
public class GetUcscGeneCoordinates extends GetGeneCoordinates {
    
final ArrayList<String> fields = new ArrayList<>(Arrays.asList("name", "chrom", "txStart", "txEnd", "cdsStart", "cdsEnd", "exonCount", "exonStarts", "exonEnds", "strand")); 
    
 public class GetGeneExonException extends Exception{
        public GetGeneExonException() { super(); }
        public GetGeneExonException(String message) { super(message); }
        public GetGeneExonException(String message, Throwable cause) { super(message, cause); }
        public GetGeneExonException(Throwable cause) { super(cause); }
}      
    //@Override
    public class GetGeneFromSymbol {
    public ArrayList<GeneDetails> getGeneFromSymbol(String symbol, String build, String db) throws SQLException, GetGeneExonException{
        ArrayList<GeneDetails> transcripts = new ArrayList<>();
        String fieldsToRetrieve = String.join(", ", fields);
        PreparedStatement ps = conn.prepareStatement(query);
        query = ("SELECT kgID, geneSymbol FROM " + build + ".kgXref WHERE "
                        + "geneSymbol='" + symbol +"'");
        ResultSet rs2  = (ResultSet) ps.executeQuery(query); 
        while (rs2.next()){
        query = ("SELECT " + fieldsToRetrieve + 
                " FROM " + build + "." + db +" WHERE name='"+ 
                    rs2.getString("kgID") + "'");
        ResultSet rs = (ResultSet) ps.executeQuery(query);
            transcripts.addAll(getTranscriptsFromResultSet(rs, symbol));
        }
        return transcripts;
    }
    
    //@Override
    public ArrayList<GeneDetails> getGeneFromId(String id, String build, String db) 
            throws SQLException, GetGeneExonException{
        String fieldsToRetrieve = String.join(", ", fields);
        PreparedStatement ps = conn.prepareStatement(query);
        query = ("SELECT " + fieldsToRetrieve + 
                " FROM " + build + "." + db + " WHERE name='"+ id + "'");
        ResultSet rs  = (ResultSet) ps.executeQuery(query);         
        query = ("SELECT kgID, geneSymbol FROM " + build + ".kgXref WHERE kgID='" + id +"'");
        ResultSet rs2  = (ResultSet) ps.executeQuery(query);
        String symbol = new String();
        while(rs2.next()){
            symbol = rs2.getString("geneSymbol");
        }
        return getTranscriptsFromResultSet(rs, symbol);
    }
    
    protected ArrayList<GeneDetails> getTranscriptsFromResultSet(ResultSet rs, String symbol) 
             throws SQLException, GetGeneExonException{
        ArrayList<HashMap<String, String>> genes = new ArrayList<>();
        while (rs.next()){
            HashMap<String, String> geneCoords = new HashMap<>();
            for (String f: fields){
                geneCoords.put(f, rs.getString(f));
            }
            geneCoords.put("name2", symbol);
            genes.add(geneCoords);
        }
        ArrayList<GeneDetails> TranscriptsToReturn = new ArrayList<>();
        for (HashMap<String, String> gene: genes){
        GeneDetails GeneDetails = GeneHashToGeneDetails(gene);
            TranscriptsToReturn.add(GeneDetails);
        }
        return TranscriptsToReturn;
    }
            private GeneDetails GeneHashToGeneDetails(HashMap<String, String> gene) {
                throw new UnsupportedOperationException("Method Creation to support GeneHashToGeneDetails is inadquate"); //To change body of generated methods, choose Tools | Templates.
            }   
}
}