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
 * @author David A. Parry <d.a.parry@leeds.ac.uk> and edited by Bert Gold <bgold04@gmail.com>
 */


package com.github.mavenautoprimer;

import com.github.mavenautoprimer.GeneCoordinatesFetcher;
import com.github.mavenautoprimer.GenomicBase;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

public class EnsemblGeneCoordinatesFetcher extends UcscGeneCoordinatesFetcher {
    final ArrayList<String> fields = new ArrayList<>(Arrays.asList("name", "chrom", "txStart", "txEnd", "cdsStart", "cdsEnd", "exonCount", "exonStarts", "exonEnds", "strand"));
    public void setgeneSymbol(String geneSymbol, String symbol, String name2) {
        this.geneSymbol = symbol = name2;
    }
    public boolean next() {
        throw new IllegalStateException("next() exception problem");
    }
    public String getString(String rs) {
        throw new ArrayIndexOutOfBoundsException("getString exception problem");
    }
    public Integer getInt(Integer COUNT) {
        throw new IllegalArgumentException ("getInt exception problem");
    }
    GeneDetails geneDetails = geneHashToGeneDetails(gene);
     // Inherited from GenomicBase
    class getTranscriptsFromResultSet {
        public ArrayList<GeneDetails> getTranscriptsFromResultSet(GeneCoordinatesFetcher.ResultSet rs, ArrayList<String> fields) throws SQLException, GenomicBase.TranscriptsFromrsException {
            ArrayList<HashMap<String, String>> genes = new ArrayList<>();
            while (rs.next()) {
                HashMap<String, String> geneCoords = new HashMap<>();
                for (String f : fields) {
                    geneCoords.put(f, rs.getString(f));
                }
                genes.add(geneCoords);
            }
            ArrayList<GeneDetails> transcriptsToReturn = new ArrayList<>();
            for (HashMap<String, String> gene: genes) {
                GeneDetails geneDetails = geneHashToGeneDetails(gene);
                transcriptsToReturn.add(geneDetails);
            }
            return transcriptsToReturn;
        }
        @SuppressWarnings({"ThrowableInstanceNotThrown", "ThrowableInstanceNeverThrown"})private GeneDetails geneHashToGeneDetails(HashMap<String, String> gene) {throw new IllegalStateException("Gene Hash Problem");
        }
    }
    class getTranscriptsViaKgId  {
        ArrayList<GeneDetails> getTranscriptsViaKgId(String symbol, String build, String db, String fieldsToRetrieve) throws SQLException {
            ArrayList<GeneDetails> transcripts = new ArrayList<>();
            Connection conn = DriverManager.getConnection("jdbc:mysql://genome-mysql.cse.ucsc.edu" + "?user=genomep&password=password&no-auto-rehash");
            String query = null;
            String fields = new String();
            PreparedStatement stmt3 = conn.prepareStatement(query);
            query  = ("SELECT COUNT(*) FROM " + "information_schema.tables  WHERE table_schema = '" + build + "' AND table_name = 'knownToEnsembl';");
            GeneCoordinatesFetcher.ResultSet rs2 = (GeneCoordinatesFetcher.ResultSet) stmt3.executeQuery(query);
            ResultSetMetaData resultSetMetaData = rs2.getMetaData();
            // and get the first column header
            String columnHeader = resultSetMetaData.getColumnLabel(1);
            // initialize an empty StringBuilder OUTSIDE the loop
            StringBuilder sb = new StringBuilder();
            // then loop through the resultset
            while (rs2.next()) {
                if (rs2.getInt("COUNT(*)") > 0) {
                    ArrayList<String> kgids = new ArrayList<>();
                    System.out.println("SELECT kgID, " + "geneSymbol FROM " + build + ".kgXref WHERE geneSymbol='" + symbol + "';");
                    //db +" WHERE name2='"+ symbol + "'"
                    conn = DriverManager.getConnection("jdbc:mysql://genome-mysql.cse.ucsc.edu" + "?user=genomep&password=password&no-auto-rehash");
                    PreparedStatement stmt5 = conn.prepareStatement(query);
                    query = ("SELECT kgID, " + "geneSymbol FROM " + build + ".kgXref WHERE geneSymbol='" + symbol + "';");
                    GeneCoordinatesFetcher.ResultSet rs3 = (GeneCoordinatesFetcher.ResultSet) stmt5.executeQuery(query);
                    while(rs3.next()) {
                        kgids.add(rs3.getString("kgID"));
                    }
                    System.out.println("Select value FROM " + build + ".knownToEnsembl WHERE name='" + String.join(" or name=", kgids) + "';");
                    PreparedStatement st = conn.prepareStatement(query);
                    query = ("Select value FROM " + build + ".knownToEnsembl WHERE name='" + String.join(" or name=", kgids) + "';");
                    GeneCoordinatesFetcher.ResultSet rs4 = (GeneCoordinatesFetcher.ResultSet) st.executeQuery(query);
                    while (rs4.next()) {
                        query = ("SELECT " + fieldsToRetrieve + " FROM " + build + "." + db +" WHERE name='"+ rs4.getString("value") + "'");
                        PreparedStatement stmt4 = conn.prepareStatement(query);
                        GeneCoordinatesFetcher.ResultSet rs = (GeneCoordinatesFetcher.ResultSet) stmt4.executeQuery(query);
                        Collection<? extends GeneDetails> getTranscriptsFromResultSet = null;
                        transcripts.addAll(getTranscriptsFromResultSet);
                    }
                    return transcripts;
                }
            }
        }
     }
@Override
    class getGeneFromSymbol {
        public ArrayList<GeneDetails> getGeneFromSymbol(String symbol, String build, String db) throws SQLException, GenomicBase.GetGeneExonException {
            String fieldsToRetrieve = String.join(", ", fields);
            ArrayList<GeneDetails> transcripts = new ArrayList<>();
            Connection conn = DriverManager.getConnection("jdbc:mysql://genome-mysql.cse.ucsc.edu" + "?user=genomep&password=password&no-auto-rehash");
            PreparedStatement st = conn.prepareStatement(query);
            query = ("SELECT COUNT(*) COLUMNS FROM information_schema.tables WHERE table_schema = '" + build + "' AND table_name = 'ensemblToGeneName';");
            ResultSet rs2 = (ResultSet) st.executeQuery(query);
            while (rs2.next()) {
                if (rs2.getInt("COUNT(*)") < 1) {
                    transcripts = getTranscriptsViaKgId();
                } else {
                    transcripts = getTranscriptsFromEnsemblToName();
                }
            }
            return transcripts;
        }
        boolean next() {throw new IllegalStateException("next() exception problem");
        }
        public String getString(String rs) {throw new ArrayIndexOutOfBoundsException("getString exception problem");
        }
        private Integer getInt(Integer COUNT) {throw new IllegalArgumentException("getInt exception problem");
        }
        private ArrayList<GeneDetails> getTranscriptsViaKgId() {throw new UnsupportedOperationException("Problem with getTranscriptsViaKgId method");
        	 //To change body of generated methods, choose Tools | Templates.
        }
        private ArrayList<GeneDetails> getTranscriptsFromEnsemblToName() {throw new UnsupportedOperationException("Problem with getTranscriptsFromEnsemblToName method");
        //To change body of generated methods, choose Tools | Templates.
        }
    }
    class getTranscriptsFromEnsemblToName {
        public void getTranscriptsFromEnsemblToName(String symbol, String build, String db, String fieldsToRetrieve) throws SQLException, EnsemblToNameException {
            class transcripts {
                private void addAll(Object transcriptsFromResultSet) {throw new UnsupportedOperationException("Not supported yet.");
                //To change body of generated methods, choose Tools | Templates.
                }
                private transcripts() throws SQLException {
                    String query = null;
                    System.out.println("SELECT name, value FROM '" + build + ".ensemblToGeneName' WHERE value='" + symbol +"';");
                    Connection conn = DriverManager.getConnection("jdbc:mysql://genome-mysql.cse.ucsc.edu" + "?user=genomep&password=password&no-auto-rehash");
                    PreparedStatement ps = conn.prepareStatement(query);
                    query = ("SELECT COUNT(*) FROM " + "information_schema.tables  WHERE table_schema = '" + build + "' AND table_name = 'ensemblToGeneName';");
                    ResultSet rs2 = (ResultSet) ps.executeQuery(query);
                    while (rs2.next()) {
                        if (rs2.getInt("COUNT(*)") < 1) {
                            //PreparedStatement ps = conn.prepareStatement(query);
                            query = ("SELECT " + fieldsToRetrieve + " FROM " + build + "." + db +" WHERE name='"+ rs2.getString("name") + "'");
                            rs = (ResultSet) ps.executeQuery(query);
                            //transcripts.addAll(getTranscriptsFromResultSet());
                        }
                    }
                }
            }
        }
    }
@Override
    class getGeneFromID {
        public ArrayList<GeneDetails> getGeneFromID(String id, String build, String db) throws SQLException, GetGeneFromIDException {
            String fields = new String();
            String symbol = new String();
            Object rs = new Object();
            String query = new String();
            String fieldsToRetrieve = String.join(", ", fields);
            System.out.println("SELECT " + fieldsToRetrieve + " FROM " + build + "." + db + " WHERE name='"+ id + "'");
            query = ("SELECT " + fieldsToRetrieve + " FROM " + build + "." + db + " WHERE name='"+ id + "'");
            Connection conn = DriverManager.getConnection("jdbc:mysql://genome-mysql.cse.ucsc.edu" + "?user=genomep&password=password&no-auto-rehash");
            String query7 = null;
            PreparedStatement stmt7 = conn.prepareStatement(query7);
            query7 = ("SELECT COUNT(*) FROM " + "information_schema.tables  WHERE table_schema = '" + build + "' AND table_name = 'ensemblToGeneName';");
            ResultSet rs2  = (ResultSet)  stmt7.executeQuery(query7);
            while (rs2.next()) {
                if (rs2.getInt("COUNT(*)") < 1) {
                    symbol = getSymbolViaKgId(id, build);
                } else {
                    symbol = getSymbolFromEnsemblToName(id, build);
                }
            }
            return null;
        }
        private String getSymbolViaKgId(String id, String build) throws SQLException {
            System.out.println("SELECT COUNT(*) FROM " + "information_schema.tables  " + "WHERE table_schema = '"+ build +"' AND table_name = 'knownToEnsembl';");
            return null;
        }
        private String getSymbolFromEnsemblToName(String id, String build) throws SQLException {
            System.out.println("SELECT name, value FROM " + build + ".ensemblToGeneName WHERE name='" + id +"';");
            return null;
        }
    }
    class getSymbolFromEnsemblToName {
        public String getSymbolFromEnsemblToName(String id, String build) throws SQLException {
            String symbol = new String();
            Connection conn = DriverManager.getConnection("jdbc:mysql://genome-mysql.cse.ucsc.edu" + "?user=genomep&password=password&no-auto-rehash");
            String query = "SELECT name, value FROM " + build + ".ensemblToGeneName WHERE name='" + id +"';";
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs2  = (ResultSet) ps.executeQuery(query);
            while (rs2.next()) {
                symbol = rs2.getString("value");
            }
            return symbol;
        }
        public int getInt(Integer COUNT) {
            throw new IllegalArgumentException("getInt exception problem");
        }
    }
    class getSymbolViaKgId {
        public String getSymbolViaKgId(String id, String build) throws SQLException {
            String symbol = new String();
            String query = null;
            Connection conn = DriverManager.getConnection("jdbc:mysql://genome-mysql.cse.ucsc.edu" + "?user=genomep&password=password&no-auto-rehash");
            query = ("SELECT COUNT(*) FROM " + "information_schema.tables  WHERE table_schema = '"+ build +"' AND table_name = 'knownToEnsembl';");
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs2 = (ResultSet) ps.executeQuery(query);
            query = null;
            while (rs2.next()) {
                if (rs2.getInt("COUNT(*)") > 0) {
                    query = ("Select name FROM " + build + ".knownToEnsembl WHERE value='" + id + "';");
                    ps = conn.prepareStatement(query);
                    ResultSet rs3  = (ResultSet) ps.executeQuery(query);
                    while (rs3.next()) {
                        query = ("SELECT geneSymbol FROM " + build + ".kgXref WHERE kgID='" + rs3.getString("name") +"';");
                        ps = conn.prepareStatement(query);
                        ResultSet rs4  = (ResultSet)  ps.executeQuery(query);
                        while (rs4.next()) {
                            symbol = rs4.getString("geneSymbol");
                        }
                    }
                }
            }
            return symbol;
        }
        boolean next() {
            throw new IllegalStateException("next() exception problem");
        }
        public String getString(String rs) {
        throw new ArrayIndexOutOfBoundsException("getString exception problem");
        }
        public int getInt(Integer COUNT) {
            throw new IllegalArgumentException("getInt exception problem");
        }
    }
}



