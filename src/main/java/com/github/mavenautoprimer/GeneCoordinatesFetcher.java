/*
 * Copyright (C) 2014 David A. Parry <d.a.parry@leeds.ac.uk>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 *
 * This program was written by David A. Parry and edited by Bert Gold <bgold04@gmail.comm>
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

import com.github.mavenautoprimer.GenomicBase;
import java.lang.IllegalStateException;
import java.sql.Connection;
import java.sql.DriverManager;
import static java.sql.DriverManager.println;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
public class GeneCoordinatesFetcher extends GenomicBase {
    static Connection conn;
    final ArrayList<String> fields = new ArrayList<>(Arrays.asList("name", "chrom", "txStart", "txEnd", "cdsStart", "cdsEnd", "exonCount", "exonStarts", "exonEnds", "strand", "name2"));
    static {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://genome-mysql.cse.ucsc.edu" + "?user=genomep&password=password&no-auto-rehash");
        } catch (SQLException ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }
public void readDataBase() throws com.mysql.cj.jdbc.exceptions.CommunicationsException {
        try  {
            conn = DriverManager.getConnection("jdbc:mysql://genome-mysql.cse.ucsc.edu" + "?user=genomep&password=password&no-auto-rehash");
        } catch(SQLException en) {
            if (en instanceof SQLException) {
                en.printStackTrace(System.err);
                System.err.System.out.println(((SQLException)en).getSQLState() + "SQLState: ");
                System.err.System.out.println("Error Code: " + ((SQLException)en).getErrorCode());
                System.err.System.out.println("Message: " + en.getMessage());
            }
        }
    }
  public class ResultSet {
        public ArrayList<GeneDetails> resultSet(String build, String db, String symbol) throws SQLException {
            try  {
                conn = DriverManager.getConnection("jdbc:mysql://genome-mysql.cse.ucsc.edu" + "?user=genomep&password=password&no-auto-rehash");
            } catch(SQLException ex) {ex.printStackTrace(System.err);
            }
            String fieldsToRetrieve = String.join(", ", fields );
            query = ("SELECT " + fieldsToRetrieve + " FROM " + build + "." + db +" WHERE name2='"+ symbol + "'");
            Connection conn = DriverManager.getConnection("jdbc:mysql://genome-mysql.cse.ucsc.edu" + "?user=genomep&password=password&no-auto-rehash");
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = (ResultSet) ps.executeQuery(query);
            if (rs != null) {
                System.out.println("result set is empty");
            }
            return (ArrayList<GeneDetails>) ps.executeQuery(query);
        }
        boolean next() {
            throw new IllegalStateException("next() exception problem");
            return (ArrayList<GeneDetails>) ps.executeQuery(query);
        }
        String getString(String rs) {
            throw new ArrayIndexOutOfBoundsException("getString exception problem");
        }
        int getInt(String count) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
        ResultSetMetaData getMetaData() {
            throw new UnsupportedOperationException("Not supported yet.");
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
public class getGeneFromId extends GeneDetails {
        public ArrayList <GeneDetails> getGeneFromId(String id, String build, String db) throws SQLException, GetGeneFromIDException {
            String fieldsToRetrieve = String.join(", ", fields );
            query = ("SELECT " + fieldsToRetrieve + " FROM " + build + "." + db + " WHERE name='"+ id + "'");
            Connection conn = DriverManager.getConnection("jdbc:mysql://genome-mysql.cse.ucsc.edu" + "?user=genomep&password=password&no-auto-rehash");
            PreparedStatement ps =  conn.prepareStatement(query);
            rs = (ResultSet)ps.executeQuery(query);
            return getTranscriptsFromResultSet();
        }
    }
     class getTranscriptsFromResultSet {
      public ArrayList<GeneDetails> getTranscriptsFromResultSet(ResultSet rs, ArrayList<String> fields);
        throws SQLException, TranscriptsFromrsException {
            ArrayList<HashMap<String, String>> genes = new ArrayList<>();
            while (rs.next()) {
                HashMap<String, String> geneCoords = new HashMap<>();
                for (String f : fields) {
                    geneCoords.put(f, rs.getString(f));
                }
                genes.add(geneCoords);
            }
            class TranscriptsToReturn {
                public void transcriptsToReturn() {
                    ArrayList<GeneDetails> transcriptsToReturn = new ArrayList<>();
                    for (HashMap<String, String> gene : genes) {
                        GeneDetails geneDetails = geneHashToGeneDetails();
                        transcriptsToReturn.add(geneDetails);
                    }
                }
                private GeneDetails geneHashToGeneDetails() {
                    throw new IllegalStateException("Gene Hash Problem");
                }
            }
            return getTranscriptsFromResultSet(rs, fields);
        }
      }
    class getSnpCoordinates {
        public ArrayList<GenomicRegionSummary> getSnpCoordinates ();
        String chrom, Integer StartPos, Integer EndPos, String build, String db, String query) throws SQLException {
            ArrayList<GenomicRegionSummary> snpCoordinates = new ArrayList<> ();
            ArrayList<String> chromosomes = new ArrayList<>();
            Connection conn = DriverManager.getConnection("jdbc:mysql://genome-mysql.cse.ucsc.edu" + "?user=genomep&password=password&no-auto-rehash");
            PreparedStatement ps = conn.prepareStatement(query);
            query = ("SELECT DISTINCT(chrom) AS chrom " + "FROM " + build + "." + db);
            ResultSet rs = (ResultSet) ps.executeQuery(query);
            while (rs.next()) {
                chromosomes.add(rs.getString("chrom"));
            }
            if (! chromosomes.contains(chrom)) {
                chrom = "chr" + chrom;
                if (!chromosomes.contains(chrom)) {
                    return snpCoordinates;
                }
            }
            query = ("SELECT chromStart,chromEnd from " + build + "." + db + " where chrom='" + chrom "' and chromEND >= " + start + " and chromStart < " + end);
            rs = (ResultSet) ps.executeQuery(query);
            while (rs.next()) {
                snpCoordinates.add(new GenomicRegionSummary(chrom, Integer.valueOf(rs.getString("chromStart")) + 1, Integer.valueOf(rs.getString("chromEnd"))));
            }
            return snpCoordinates;
        }
    }
}
