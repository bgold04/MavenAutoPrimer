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


package com.github.autoprimer3A;

import com.github.autoprimer3A.GetEnsemblGeneCoordinates.EnsemblToNameException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

public class GetEnsemblGeneCoordinates extends GetUcscGeneCoordinates {
    final ArrayList<String> fields = new ArrayList<>(Arrays.asList("name", "chrom", 
            "txStart", "txEnd", "cdsStart", "cdsEnd", "exonCount", "exonStarts", 
            "exonEnds", "strand"));    
    
    public Integer txEnd;
    public String ChromStart;
    public String ChromEnd;
    public String chromSet;
    public Object gene;
    public Object genes;
    public Integer cdsStart;
    public Integer cdsEnd;
    public String exonCount;
    public String exon;
    public String exonStarts;
    public String exonEnds;        
    public String ex;
    public String j;
    public Object geneDetails;
    public String strand;     
    public String build;
    public String chromosome;
    public Integer TotalExons;
    public Integer end;
    public Integer start;
    public String symbol;
    public String db;
    public String sql;
    public String qu;
    public String qu1;
    public String query;
    public String query1;
    public String query2;
    public Object statement;
    public Object st;    
    public Object ResultSet;
    public String stmt10;
    public Object rs;
    public Object rs2;
    public String f;
    public Object fieldsToRetrieve;
    public String geneSymbol;
    public Object Cursor;
    public Object Transcripts;
    public String query7;
    public Object stmt;
    public Object stmt1;
    public Object stmt2;
    public Object stmt3;
    public Object stmt4;
    public Object stmt5;
    public Object stmt6;
    public Object stmt7;
    public Object stmt8;
    public Object rs3;
    public Object rs4;      

    
    
    public class Id {
    public String id;   
    public String getid() {        
        return id;
    }
    }        
    public class Name {
    public String name;
    public String getName() {
        return name;
    }
    public void setName(String name, String id, String kgID) {
        this.name = id = kgID = name;
    }
    }        
public class Name2 {
    public String name2;   
    public String getName2() {        
        return name2;
    }
    public void setName2(String name2, String symbol, String geneSymbol) {
        this.name2 = symbol = geneSymbol;
    }
    }        
public class Build {
    public String build;   
    public String getBuild() {        
        return build;
    }
    public void setBuild(String build) {
        this.build = build;
    }
    } 
public class Db {
    public String db;   
    public String getDb() {        
        return db;
    }
    public void setDb(String db) {
        this.db = db;
    }
    } 
public class ResultSet {
    public String rs;   
    public String getResultSet() {        
        return rs;
    }
    public void setResultSet(String rs) {
        this.rs = rs;
    }

        private boolean next() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        private int getInt(String count) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        private String getString(String name) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }        
    }
public class PreparedSet {
    public String ps;   
    public String getPreparedSet() {        
        return ps;
    }
    public void setPreparedSet(String ps) {
        this.ps = ps;
    }
    }
public class Fields {
    public String fields;   
    public String getfields() {        
        return fields;
    }
    public void setFields(String fields) {
        this.fields = fields;
    }
    }
public class KgID {
    public String kgID;   
    public String getkgID() {        
        return kgID;
    }
    public void setKgID(String kgID,String name, String id) {
        this.kgID = kgID = name = id;
    }
    }
public class GetGeneFromSymbolException extends Exception{
        public GetGeneFromSymbolException() { super(); }
        public GetGeneFromSymbolException(String message) { super(message); }
        public GetGeneFromSymbolException(String message, Throwable cause) { super(message, cause); }
        public GetGeneFromSymbolException(Throwable cause) { super(cause); }
}
public class EnsemblToNameException extends Exception {
        public EnsemblToNameException() { super(); }
        public EnsemblToNameException(String message) { super(message); }
        public EnsemblToNameException(String message, Throwable cause) { super(message, cause); }
        public EnsemblToNameException(Throwable cause) { super(cause); }        
    }
public class KgIdTranscriptException extends Exception{
        public KgIdTranscriptException() { super(); }
        public KgIdTranscriptException(String message) { super(message); }
        public KgIdTranscriptException(String message, Throwable cause) { super(message, cause); }
        public KgIdTranscriptException(Throwable cause) { super(cause); }
        }  
public class GetGeneExonException extends Exception{
        public GetGeneExonException() { super(); }
        public GetGeneExonException(String message) { super(message); }
        public GetGeneExonException(String message, Throwable cause) { super(message, cause); }
        public GetGeneExonException(Throwable cause) { super(cause); }
}
public class TranscriptsFromrsException extends Exception{
        public TranscriptsFromrsException() { super(); }
        public TranscriptsFromrsException(String message) { super(message); }
        public TranscriptsFromrsException(String message, Throwable cause) { super(message, cause); }
        public TranscriptsFromrsException(Throwable cause) { super(cause); }
} 
public class GetGeneFromIDException extends Exception {
        public GetGeneFromIDException() { super(); }
        public GetGeneFromIDException(String message) { super(message); }
        public GetGeneFromIDException(String message, Throwable cause) { super(message, cause); }
        public GetGeneFromIDException(Throwable cause) { super(cause); }
}     
public class HashToDetailException extends Exception{
        public HashToDetailException() { super(); }
        public HashToDetailException(String message) { super(message); }
        public HashToDetailException(String message, Throwable cause) { super(message, cause); }
        public HashToDetailException(Throwable cause) { super(cause); }
}
public class GeneDetailsException extends Exception{
        public GeneDetailsException() { super(); }
        public GeneDetailsException(String message) { super(message); }
        public GeneDetailsException(String message, Throwable cause) { super(message, cause); }
        public GeneDetailsException(Throwable cause) { super(cause); }
}
public class TryCatchException extends Exception{
        public TryCatchException() { super(); }
        public TryCatchException(String message) { super(message); }
        public TryCatchException(String message, Throwable cause) { super(message, cause); }
        public TryCatchException(Throwable cause) { super(cause); }
}      
        public class getEnsemblTranscriptException extends Exception{
        public getEnsemblTranscriptException() { super(); }
        public getEnsemblTranscriptException(String message) { super(message); }
        public getEnsemblTranscriptException(String message, Throwable cause) { super(message, cause); }
        public getEnsemblTranscriptException(Throwable cause) { super(cause); }
}        
       public class EnsemblTranscriptException  extends Exception{
        public EnsemblTranscriptException() { super(); }
        public EnsemblTranscriptException (String message) { super(message); }
        public EnsemblTranscriptException (String message, Throwable cause) { super(message, cause); }
        public EnsemblTranscriptException (Throwable cause) { super(cause); }
}
    public void setgeneSymbol(String geneSymbol, String symbol, String name2) {
        this.geneSymbol = symbol = name2;
    }
    public boolean next() {
            throw new IllegalStateException
        ("next() exception problem");
        }
    public String getString(String rs) {
            throw new 
        ArrayIndexOutOfBoundsException
        ("getString exception problem");
        } 
     public Integer getInt(Integer COUNT) {
           throw new IllegalArgumentException
               ("getInt exception problem");
        }
class getTranscriptsFromResultSet {
public ArrayList<GeneDetails> getTranscriptsFromResultSet(GetGeneCoordinates.ResultSet rs, ArrayList<String> fields)
        throws SQLException, GetGeneCoordinates.TranscriptsFromrsException{        
        ArrayList<HashMap<String, String>> genes = new ArrayList<>();
        while (rs.next()){
            HashMap<String, String> geneCoords = new HashMap<>();
            for (String f : fields){
                geneCoords.put(f, rs.getString(f));
            }
            genes.add(geneCoords);
}
        ArrayList<GeneDetails> transcriptsToReturn = new ArrayList<>();
        for (HashMap<String, String> gene: genes){
            GeneDetails geneDetails = geneHashToGeneDetails(gene);
            transcriptsToReturn.add(geneDetails);
        }
        return transcriptsToReturn;
    }

@SuppressWarnings({"ThrowableInstanceNotThrown", "ThrowableInstanceNeverThrown"})
        private GeneDetails geneHashToGeneDetails(HashMap<String, String> gene){
throw new IllegalStateException("Gene Hash Problem");
        }
    }
     class getTranscriptsViaKgId  {
//public static void getTranscriptsViaKgId(String symbol, String build, String db, String fieldsToRetrieve) 
//throws new KgIdTranscriptException() {
       ArrayList<GeneDetails> getTranscriptsViaKgId(String symbol, String build, String db, String fieldsToRetrieve) throws SQLException {                     
           ArrayList<GeneDetails> transcripts = new ArrayList<>();
        Connection conn = DriverManager.getConnection("jdbc:mysql://genome-mysql.cse.ucsc.edu" + "?user=genomep&password=password&no-auto-rehash");           
           String query = null;
           String fields = new String(); 
        PreparedStatement stmt3 = conn.prepareStatement(query);
        query  = ("SELECT COUNT(*) FROM " + "information_schema.tables  WHERE table_schema = '" + build + "' AND table_name = 'knownToEnsembl';");        
        GetGeneCoordinates.ResultSet rs2 = (GetGeneCoordinates.ResultSet) stmt3.executeQuery(query); 
        ResultSetMetaData resultSetMetaData = rs2.getMetaData();
        // and get the first column header
        String columnHeader = resultSetMetaData.getColumnLabel(1);
        // initialize an empty StringBuilder OUTSIDE the loop
        StringBuilder sb = new StringBuilder();
        // then loop through the resultset
        while (rs2.next())
            {     
            if (rs2.getInt("COUNT(*)") > 0)
            {
                ArrayList<String> kgids = new ArrayList<>();
                System.out.println("SELECT kgID, " + "geneSymbol FROM " + build + ".kgXref WHERE geneSymbol='" + symbol + "';");
                //db +" WHERE name2='"+ symbol + "'"
                conn = DriverManager.getConnection("jdbc:mysql://genome-mysql.cse.ucsc.edu" + "?user=genomep&password=password&no-auto-rehash");
                PreparedStatement stmt5 = conn.prepareStatement(query);
                query = ("SELECT kgID, " + "geneSymbol FROM " + build + ".kgXref WHERE geneSymbol='" + symbol + "';");
                GetGeneCoordinates.ResultSet rs3 = (GetGeneCoordinates.ResultSet) stmt5.executeQuery(query);
                while(rs3.next())
            {
                    kgids.add(rs3.getString("kgID"));
            }
                System.out.println("Select value FROM " + build 
                        + ".knownToEnsembl WHERE name='" + 
                        String.join(" or name=", kgids) + "';");
                PreparedStatement st = conn.prepareStatement(query);
                query = ("Select value FROM " + build + ".knownToEnsembl WHERE name='" + String.join(" or name=", kgids) + "';");               
                GetGeneCoordinates.ResultSet rs4 = (GetGeneCoordinates.ResultSet) st.executeQuery(query);
                while (rs4.next()){
                query = ("SELECT " + fieldsToRetrieve + " FROM " + build + "." + db +" WHERE name='"+ rs4.getString("value") + "'");
                PreparedStatement stmt4 = conn.prepareStatement(query);
                GetGeneCoordinates.ResultSet rs = (GetGeneCoordinates.ResultSet) stmt4.executeQuery(query);                                                  
                    Collection<? extends GeneDetails> getTranscriptsFromResultSet = null;
               transcripts.addAll(getTranscriptsFromResultSet);
                }
                return transcripts;
            } else {
}
}       
           return transcripts;       
}
}
//@Override
class getGeneFromSymbol {     
//public static void getGeneFromSymbol { //(String symbol, String build, String db) { 
public ArrayList<GeneDetails> getGeneFromSymbol(String symbol, String build, String db) 
  throws SQLException, GetGeneCoordinates.GetGeneExonException{      
        //symbol = null;
        //String query = null;
        //String kgID = null;
    String fieldsToRetrieve = String.join(", ", fields);            
       ArrayList<GeneDetails> transcripts = new ArrayList<>();
       Connection conn = DriverManager.getConnection("jdbc:mysql://genome-mysql.cse.ucsc.edu" + "?user=genomep&password=password&no-auto-rehash");           
        PreparedStatement st = conn.prepareStatement(query);
        //query = ("SELECT COUNT(*) FROM " + "information_schema.tables  WHERE table_schema = '" + build + "' AND table_name = 'ensemblToGeneName';");
        query = ("SELECT COUNT(*) COLUMNS FROM information_schema.tables WHERE table_schema = '" + build + "' AND table_name = 'ensemblToGeneName';");        
        ResultSet rs2 = (ResultSet) st.executeQuery(query);        
        while (rs2.next()){
            if (rs2.getInt("COUNT(*)") < 1){
                transcripts = getTranscriptsViaKgId();
            }
            else
            {
                transcripts = getTranscriptsFromEnsemblToName();
            }            
        }
            return transcripts;
        }        
        boolean next() {
        throw new IllegalStateException
        ("next() exception problem");
        }
        public String getString(String rs) {
            throw new 
        ArrayIndexOutOfBoundsException
        ("getString exception problem");
        } 
        private Integer getInt(Integer COUNT) {
           throw new IllegalArgumentException
               ("getInt exception problem");
        }

        private ArrayList<GeneDetails> getTranscriptsViaKgId() {
            throw new UnsupportedOperationException("Problem with getTranscriptsViaKgId method"); //To change body of generated methods, choose Tools | Templates.
        }

        private ArrayList<GeneDetails> getTranscriptsFromEnsemblToName() {
            throw new UnsupportedOperationException("Problem with getTranscriptsFromEnsemblToName method"); //To change body of generated methods, choose Tools | Templates.
        }             
}
class getTranscriptsFromEnsemblToName {
public void getTranscriptsFromEnsemblToName(String symbol, 
                String build, String db, String fieldsToRetrieve) 
            throws SQLException, EnsemblToNameException {
    
    
    class transcripts {         

        private void addAll(Object transcriptsFromResultSet) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        
        private transcripts() throws SQLException {           
                String query = null;            
    System.out.println("SELECT name, value FROM '"
            + build + ".ensemblToGeneName' WHERE value='" + symbol +"';");
      Connection conn = DriverManager.getConnection("jdbc:mysql://genome-mysql.cse.ucsc.edu" + "?user=genomep&password=password&no-auto-rehash");                  
      PreparedStatement ps = conn.prepareStatement(query);
        query = ("SELECT COUNT(*) FROM " + "information_schema.tables  WHERE table_schema = '" + build + "' AND table_name = 'ensemblToGeneName';");                   
        ResultSet rs2 = (ResultSet) ps.executeQuery(query);     
        while (rs2.next()){
            if (rs2.getInt("COUNT(*)") < 1){
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
//@Override
//void getGeneFromID() {
class getGeneFromID {        
public ArrayList<GeneDetails> getGeneFromID(String id, String build, String db) 
            throws SQLException, GetGeneFromIDException{
        String fields = new String(); 
        String symbol = new String();
        Object rs = new Object();
        String query = new String();
        String fieldsToRetrieve = String.join(", ", fields); 
        System.out.println("SELECT " + fieldsToRetrieve + 
                " FROM " + build + "." + db + " WHERE name='"+ id + "'");           
        query = ("SELECT " + fieldsToRetrieve + 
                " FROM " + build + "." + db + " WHERE name='"+ id + "'");
        Connection conn = DriverManager.getConnection("jdbc:mysql://genome-mysql.cse.ucsc.edu" + "?user=genomep&password=password&no-auto-rehash");
        String query7 = null;
        PreparedStatement stmt7 = conn.prepareStatement(query7);
        query7 = ("SELECT COUNT(*) FROM "
                + "information_schema.tables  WHERE table_schema = '" + build +
                "' AND table_name = 'ensemblToGeneName';");
        ResultSet rs2  = (ResultSet)  stmt7.executeQuery(query7);   
        while (rs2.next()){
            if (rs2.getInt("COUNT(*)") < 1){
                symbol = getSymbolViaKgId(id, build);
            }else {
                symbol = getSymbolFromEnsemblToName(id, build);                
            }
        }
    return null;
}
        private String getSymbolViaKgId(String id, String build) 
            throws SQLException { System.out.println("SELECT COUNT(*) FROM " + "information_schema.tables  "
                    + "WHERE table_schema = '"+ build +"' AND table_name = 'knownToEnsembl';");
    return null;
}       
        private String getSymbolFromEnsemblToName(String id, String build) throws SQLException{
        System.out.println("SELECT name, value FROM "
            + build + ".ensemblToGeneName WHERE name='" + id +"';");    
    return null;
}
}        
                                      
class getSymbolFromEnsemblToName {
//public static void getSymbolFromEnsemblToName(String id, String build) {    
public String getSymbolFromEnsemblToName(String id, String build) 
            throws SQLException{
        String symbol = new String();
        Connection conn = DriverManager.getConnection("jdbc:mysql://genome-mysql.cse.ucsc.edu" + "?user=genomep&password=password&no-auto-rehash");
    String query = "SELECT name, value FROM "
            + build + ".ensemblToGeneName WHERE name='" + id +"';";               
               PreparedStatement ps = conn.prepareStatement(query);
               ResultSet rs2  = (ResultSet) ps.executeQuery(query);                
        while (rs2.next()){
            symbol = rs2.getString("value");
        }
    return symbol;
}
public int getInt(Integer COUNT) {
           throw new IllegalArgumentException
               ("getInt exception problem");      
}    
}

//public void getSymbolViaKgId(String id, String build) {
class getSymbolViaKgId {        
public String getSymbolViaKgId(String id, String build) throws SQLException {
        String symbol = new String();
        String query = null;
        Connection conn = DriverManager.getConnection("jdbc:mysql://genome-mysql.cse.ucsc.edu" + "?user=genomep&password=password&no-auto-rehash");
            query = ("SELECT COUNT(*) FROM " + "information_schema.tables  WHERE table_schema = '"+ build +"' AND table_name = 'knownToEnsembl';");
              PreparedStatement ps = conn.prepareStatement(query);
               ResultSet rs2 = (ResultSet) ps.executeQuery(query);                  
               query = null;
               while (rs2.next()){            
                   if (rs2.getInt("COUNT(*)") > 0){                        
                        query = ("Select name FROM " + build 
                        + ".knownToEnsembl WHERE value='" + 
                        id + "';");
               ps = conn.prepareStatement(query);
               ResultSet rs3  = (ResultSet) ps.executeQuery(query);            
                while (rs3.next()){                            
               query = ("SELECT geneSymbol FROM " + build + ".kgXref WHERE kgID='" + rs3.getString("name") +"';");             
                   ps = conn.prepareStatement(query);
               ResultSet rs4  = (ResultSet)  ps.executeQuery(query);   
               //symbol = null;     
               while (rs4.next()){
        symbol = rs4.getString("geneSymbol");
                    }
                }
            }
               }                      
   return symbol;
}       
        boolean next() {
            throw new IllegalStateException
        ("next() exception problem");
        }
    public String getString(String rs) {
            throw new 
        ArrayIndexOutOfBoundsException
        ("getString exception problem");
        } 
     public int getInt(Integer COUNT) {
           throw new IllegalArgumentException
               ("getInt exception problem");      
}
}//end of class}
}

