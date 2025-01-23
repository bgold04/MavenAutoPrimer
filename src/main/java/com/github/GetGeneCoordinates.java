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

package com.github.autoprimer3A;


import java.sql.Connection;
import java.sql.DriverManager;
import static java.sql.DriverManager.println;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class GetGeneCoordinates{
static Connection conn;
final ArrayList<String> fields = new ArrayList<>(Arrays.asList("name", "chrom", 
            "txStart", "txEnd", "cdsStart", "cdsEnd", "exonCount", "exonStarts", 
            "exonEnds", "strand", "name2")); 
static {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://genome-mysql.cse.ucsc.edu" +
               "?user=genomep&password=password&no-auto-rehash");
        }catch (SQLException ex){
            throw new ExceptionInInitializerError(ex);
        }
    }
public String build;    
public Integer cdsEnd;
public Integer cdsStart;
public String chrom;
public Integer ChromEnd;
public String chromosome;
public String Chromosome;
public String chromSet;
public Integer ChromStart;
public String db;
public String e;
public Integer end;
public Integer EndPos;
public String exon;
public String exonCount;
public String exonEnds;     
public String exonStarts;
public String ex;
public Object fieldsToRetrieve;
public String f;
public String gene;
public Object geneDetails;
public String genes;
public String genome;
public Object GetGeneCoordinates;
public String id;
public String name;
public Object ps;
public String query;
public Object rs;
public String snpDb;
public Object sql;
public Integer start;
public Integer StartPos;  
public Object statement;
public String Statement;
public String strand;
public Object st;    
public String symbol;        
public Integer TotalExons;    
public Integer txStart;
public Integer txEnd;
public Object getTranscriptsFromResultSet;   
    private ArrayList<GenomicRegionSummary> snpCoordinates;
    ArrayList<GenomicRegionSummary> getSnpCoordinates(String chromosome, int start, int end, String genome, String snpDb) throws SQLException {
     System.err.println("getSnpCoordinates fetching problem.");
     return snpCoordinates;
              }
    ArrayList <GeneDetails>  getGeneFromId(String id, String build, String db)  throws SQLException, GetGeneFromIDException {
      System.err.println("getGeneFromID fetching problem.");
      return getTranscriptsFromResultSet();         
              }
    ArrayList <GeneDetails> getGeneFromSymbol(String symbol, String build, String db) throws SQLException, GetGeneFromSymbolException {
       System.err.println("getGeneFromSymbol fetching problem."); 
        return getTranscriptsFromResultSet();
              }
     private ArrayList<GeneDetails> getTranscriptsFromResultSet() {
        throw new IllegalStateException
            ("Gene Hash Problem");    
     }
     public class GetGeneFromSymbolException extends Exception{
        public GetGeneFromSymbolException() { super(); }
        public GetGeneFromSymbolException(String message) { super(message); }
        public GetGeneFromSymbolException(String message, Throwable cause) { super(message, cause); }
        public GetGeneFromSymbolException(Throwable cause) { super(cause); }
}
public class GetGeneFromIDException extends Exception{
        public GetGeneFromIDException() { super(); }
        public GetGeneFromIDException(String message) { super(message); }
        public GetGeneFromIDException(String message, Throwable cause) { super(message, cause); }
        public GetGeneFromIDException(Throwable cause) { super(cause); }
}
public class TranscriptsFromrsException extends Exception{
        public TranscriptsFromrsException() { super(); }
        public TranscriptsFromrsException(String message) { super(message); }
        public TranscriptsFromrsException(String message, Throwable cause) { super(message, cause); }
        public TranscriptsFromrsException(Throwable cause) { super(cause); }
}
public class GetGeneExonException extends Exception{
        public GetGeneExonException() { super(); }
        public GetGeneExonException(String message) { super(message); }
        public GetGeneExonException(String message, Throwable cause) { super(message, cause); }
        public GetGeneExonException(Throwable cause) { super(cause); }
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
public class GetSnpCoordinatesException extends Exception{
        public GetSnpCoordinatesException() { super(); }
        public GetSnpCoordinatesException(String message) { super(message); }
        public GetSnpCoordinatesException(String message, Throwable cause) { super(message, cause); }
        public GetSnpCoordinatesException(Throwable cause) { super(cause); }
}                   
public class Name {
    public String name;
    public String getName() {
        return name;
    }
    public void setName(String name, String id) {
        this.name = id;
    }
    }        
public class Name2 {
    public String name2;   
    public String getName2() {        
        return name2;
    }
    public void setName2(String name2, String symbol) {
        this.name2 = symbol;
    }
    }
    
    public void setSymbol(String symbol, String geneSymbol, String value, String name2){
     this.symbol = symbol; 
     this.symbol = geneSymbol;
     this.symbol = value;
     this.symbol = name2;
    }       
final ArrayList<String> 
        field = new ArrayList<>(Arrays.asList("name", "chrom", "txStart", "txEnd", "cdsStart", "cdsEnd", "exonCount", "exonStarts", "exonEnds", "strand", "name2"));
public void readDataBase()
        throws 
        com.mysql.cj.jdbc.exceptions.CommunicationsException{
try  {//code that may throw an exception    
conn = DriverManager.getConnection("jdbc:mysql://genome-mysql.cse.ucsc.edu" + "?user=genomep&password=password&no-auto-rehash");           
        }
catch(SQLException en){
{
{
        if (en instanceof SQLException) {         
                en.printStackTrace(System.err);
                System.err.println(((SQLException)en).getSQLState() +
                        "SQLState: ");
                System.err.println("Error Code: " +
                    ((SQLException)en).getErrorCode());
                System.err.println("Message: " + en.getMessage());               
                }
            }
        }
    }
}
public class ResultSet {
public ArrayList<GeneDetails> resultSet(String build, String db, String symbol) throws SQLException {  
try  {//code that may throw an exception   
    conn = DriverManager.getConnection("jdbc:mysql://genome-mysql.cse.ucsc.edu" + "?user=genomep&password=password&no-auto-rehash");
}
catch(SQLException ex){
ex.printStackTrace(System.err);
}                   
String fieldsToRetrieve = String.join(", ", fields );
query = ("SELECT " + fieldsToRetrieve + " FROM " + build + "." + db +" WHERE name2='"+ symbol + "'");
Connection conn = DriverManager.getConnection("jdbc:mysql://genome-mysql.cse.ucsc.edu" + "?user=genomep&password=password&no-auto-rehash");
PreparedStatement ps = conn.prepareStatement(query);
ResultSet rs = (ResultSet) ps.executeQuery(query);
if (rs != null)
{
    println("result set is empty");
}
    return (ArrayList<GeneDetails>) ps.executeQuery(query);
}
        boolean next() {
            throw new IllegalStateException
        ("next() exception problem");
        }
        String getString(String rs) {
            throw new 
        ArrayIndexOutOfBoundsException
        ("getString exception problem");
        }        

        int getInt(String count) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        ResultSetMetaData getMetaData() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }       
public class getGeneFromSymbol extends GeneDetails { 
    public ArrayList<GeneDetails> getGeneFromSymbol (String symbol, String build, String db) throws SQLException, GetGeneFromSymbolException {        
    String fieldsToRetrieve = String.join(", ", fields );
        query = ("SELECT " + fieldsToRetrieve + " FROM " + build + "." + db +" WHERE name2='"+ symbol+ "'");
        Connection conn = DriverManager.getConnection("jdbc:mysql://genome-mysql.cse.ucsc.edu" + "?user=genomep&password=password&no-auto-rehash");
        PreparedStatement ps = conn.prepareStatement(query);
        rs = (ResultSet) ps.executeQuery(query);                     
    return getTranscriptsFromResultSet();       
    }
}
//private ArrayList<GeneDetails> 
//        getTranscriptsFromResultSet
//        () {
//           return getTranscriptsFromResultSet(); 
//        }
//}
public class getGeneFromId extends GeneDetails {
//public void getGeneFromId(String id, String build, String db){
public ArrayList <GeneDetails> getGeneFromId(String id, String build, String db) throws SQLException, GetGeneFromIDException {
String fieldsToRetrieve = String.join(", ", fields );
     query = ("SELECT " + fieldsToRetrieve + " FROM " + build + "." + db + " WHERE name='"+ id + "'");
     Connection conn = DriverManager.getConnection("jdbc:mysql://genome-mysql.cse.ucsc.edu" + "?user=genomep&password=password&no-auto-rehash");
     PreparedStatement ps =  conn.prepareStatement(query);
     rs = (ResultSet)ps.executeQuery(query);
    return getTranscriptsFromResultSet();
    }
}
//private ArrayList<GeneDetails> 
//        getTranscriptsFromResultSet
//        () {
//           return getTranscriptsFromResultSet(); 
//}
//} 
class getTranscriptsFromResultSet {

    public ArrayList<GeneDetails> getTranscriptsFromResultSet(ResultSet rs, ArrayList<String> fields)
        throws SQLException, TranscriptsFromrsException{        
        
    ArrayList<HashMap<String, String>> genes = new ArrayList<>();
        while (rs.next()){
            HashMap<String, String> geneCoords = new HashMap<>();
            for (String f : fields){
                geneCoords.put(f, rs.getString(f));
            }
            genes.add(geneCoords);
}
class TranscriptsToReturn {
public void transcriptsToReturn() {
ArrayList<GeneDetails> transcriptsToReturn = new ArrayList<>();
for (HashMap<String, String> gene : genes){
            GeneDetails geneDetails = geneHashToGeneDetails();
            transcriptsToReturn.add(geneDetails);
        }
        }    
        private GeneDetails geneHashToGeneDetails() {
            throw new IllegalStateException
            ("Gene Hash Problem");
}
}    
    return getTranscriptsFromResultSet(rs, fields);
}
}   
class GeneHashToGeneDetails {
protected GeneDetails geneHashToGeneDetails(HashMap<String,String> gene) 
            throws RuntimeException{
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
        try{    
           geneDetails.setExons(gene.get("exonStarts"), gene.get("exonEnds"));
        }
        catch(GeneDetails.GeneExonsException ex) {
  
            throw new RuntimeException("Error in hash "
                    + geneDetails.getId() + ", gene " + geneDetails.getSymbol(), ex);
        }
        return geneDetails;
    }
    }
    class getSnpCoordinates {
        public ArrayList<GenomicRegionSummary> getSnpCoordinates (//String,int,int,String,String)
           String chrom, Integer StartPos, Integer EndPos, String build, String db, String query)
            throws SQLException{
     ArrayList<GenomicRegionSummary> snpCoordinates = new ArrayList<> ();
        //user may not have preceded chromosome with 'chr' which is fine for
        //sequence retrieval but not for snp retrieval in genomes which use it
        //ArrayList<String> chromosomes = new ArrayList<>();
        //ResultSet chromSet = doQuery("SELECT DISTINCT(chrom) AS chrom "
        //        + "FROM " + build + "." + db);
        ArrayList<String> chromosomes = new ArrayList<>();
        Connection conn = DriverManager.getConnection("jdbc:mysql://genome-mysql.cse.ucsc.edu" + "?user=genomep&password=password&no-auto-rehash");
        PreparedStatement ps = conn.prepareStatement(query);
        query = ("SELECT DISTINCT(chrom) AS chrom " + "FROM " + build + "." + db);
        ResultSet rs = (ResultSet) ps.executeQuery(query);
        //ResultSet = chromSet;
        while (rs.next()){
            chromosomes.add(rs.getString("chrom"));
        }
        if (! chromosomes.contains(chrom)){
            chrom = "chr" + chrom;
            if (!chromosomes.contains(chrom)){
                return snpCoordinates;
            }
        }
                 query = ("SELECT chromStart,chromEnd from " + 
                build + "." + db + " where chrom='" + chrom + 
                "' and chromEND >= " + start + " and chromStart < " + end);
        rs = (ResultSet) ps.executeQuery(query);    
        while (rs.next()){
            snpCoordinates.add(new GenomicRegionSummary(chrom,
                    Integer.valueOf(rs.getString("chromStart")) + 1, 
                    Integer.valueOf(rs.getString("chromEnd"))));
        }
        return snpCoordinates;
    }       
}
}    
