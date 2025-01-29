/*
 * This superclass is in public domain
 * Written by Bert Gold, PhD <bgold04@gmail.com> and ChatGPT
 *
 * Revised January 25, 2025
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

import java.lang.Object;
import java.lang.Throwable;
import java.lang.Exception;
import java.lang.RuntimeException;
import java.lang.IllegalStateException;
import java.util.ArrayList;
import java.sql.SQLException;
import java.util.HashMap;
public class GenomicBase {
    public  Integer ChromEnd;
    public  Integer ChromStart;
    public  String  Chromosome;
    public  Object  Cursor;
    public  Integer EndPos;
    public  Object  ResultSet;
    public  Integer StartPos;
    public  String  Statement;
    public  Integer TotalExons;
    public  Object  Transcripts;
    public  String  build;
    public  Integer cdsEnd;
    public  Integer cdsStart;
    public  String  chrom;
    public  String  chromSet;
    public  String  chromosome;
    public  Object  db;
    public  Object  document;
    public  Integer e;
    public  Integer end;
    public  Integer ex;
    public  Integer exon;
    public  Integer exonCount;
    public  Integer exonEnds;
    public  Integer exonStarts;
    public  String  f;
    public  Object  fieldsToRetrieve;
    public  String  gene;
    public  Object  geneDetails;
    public  String  geneSymbol;
    public  String  genes;
    public  String  genome;
    public  Object  getTranscriptsFromResultSet;
    public  String  id;
    public  String  j;
    public  String  name;
    public  Object  node;
    public  Object  ps;
    public  String  qu1;
    public  String  qu;
    public  String  query1;
    public  String  query2;
    public  String  query7;
    public  String  query;
    public  String  regions;
    public  Object  rs2;
    public  Object  rs3;
    public  Object  rs4;
    public  Object  rs;
    public  String  snpDb;
    public  Object  sql;
    public  Integer st;
    public  Integer start;
    public  Object  statement;
    public  Object  stmt10;
    public  Object  stmt1;
    public  Object  stmt2;
    public  Object  stmt3;
    public  Object  stmt4;
    public  Object  stmt5;
    public  Object  stmt6;
    public  Object  stmt7;
    public  Object  stmt8;
    public  Object  stmt;
    public  String  strand;
    public  String  symbol;
    public  String  t;
    public  Integer txEnd;
    public  Integer txStart;
// need a better definition of GenomicRegionSummary
    public class Db {
        public String db;
        public String getDb() {
            return db;
        }
        public void setDb(String db) {
            this.db = db;
        }
    }
    public class EnsemblToNameException extends Exception {
        public EnsemblToNameException() {
            super();
        }
        public EnsemblToNameException(String message) {
            super(message);
        }
        public EnsemblToNameException(String message, Throwable cause) {
            super(message, cause);
        }
        public EnsemblToNameException(Throwable cause) {
            super(cause);
        }
    }
    public class EnsemblTranscriptException  extends Exception {
        public EnsemblTranscriptException() {
            super();
        }
        public EnsemblTranscriptException (String message) {
            super(message);
        }
        public EnsemblTranscriptException (String message, Throwable cause) {
            super(message, cause);
        }
        public EnsemblTranscriptException (Throwable cause) {
            super(cause);
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
    public class GeneDetailsException extends Exception {
        public GeneDetailsException() {
            super();
        } public GeneDetailsException(String message) {
            super(message);
        } public GeneDetailsException(String message, Throwable cause) {
            super(message, cause);
        } public GeneDetailsException(Throwable cause) {
            super(cause);
        }
    }
    public class GetGeneExonException extends Exception {
        public GetGeneExonException() {
            super();
        } public GetGeneExonException(String message) {
            super(message);
        } public GetGeneExonException(String message, Throwable cause) {
            super(message, cause);
        } public GetGeneExonException(Throwable cause) {
            super(cause);
        }
    }
    public class GetGeneFromIDException extends Exception {
        public GetGeneFromIDException() {
            super();
        } public GetGeneFromIDException(String message) {
            super(message);
        } public GetGeneFromIDException(String message, Throwable cause) {
            super(message, cause);
        } public GetGeneFromIDException(Throwable cause) {
            super(cause);
        }
    }
    public class GetGeneFromSymbolException extends Exception {
        public GetGeneFromSymbolException() {
            super();
        } public GetGeneFromSymbolException(String message) {
            super(message);
        } public GetGeneFromSymbolException(String message, Throwable cause) {
            super(message, cause);
        } public GetGeneFromSymbolException(Throwable cause) {
            super(cause);
        }
    }
    public class GetSnpCoordinatesException extends Exception {
        public GetSnpCoordinatesException() {
            super();
        } public GetSnpCoordinatesException(String message) {
            super(message);
        } public GetSnpCoordinatesException(String message, Throwable cause) {
            super(message, cause);
        } public GetSnpCoordinatesException(Throwable cause) {
            super(cause);
        }
    }
    public class HashToDetailException extends Exception {
        public HashToDetailException() {
            super();
        } public HashToDetailException(String message) {
            super(message);
        } public HashToDetailException(String message, Throwable cause) {
            super(message, cause);
        } public HashToDetailException(Throwable cause) {
            super(cause);
        }
    }
    public class Id {
        public String id;
        public String getid() {
            return id;
        }
    }
    public class KgID {
        public String kgID;
        public String getkgID() {
            return kgID;
        } public void setKgID(String kgID,String name, String id) {
            this.kgID = kgID = name = id;
        }
    }
    public class KgIdTranscriptException extends Exception {
        public KgIdTranscriptException() {
            super();
        } public KgIdTranscriptException(String message) {
            super(message);
        } public KgIdTranscriptException(String message, Throwable cause) {
            super(message, cause);
        } public KgIdTranscriptException(Throwable cause) {
            super(cause);
        }
    }
    public class Name {
        public String name;
        public String getName() {
            return name;
        } public void setName(String name, String id, String kgID) {
            this.name = id = kgID = name;
        }
    }
    public class Name2 {
        public String name2;
        public String getName2() {
            return name2;
        } public void setName2(String name2, String symbol, String geneSymbol) {
            this.name2 = symbol = geneSymbol;
        }
    } public class Build {
        public String build;
        public String getBuild() {
            return build;
        } public void setBuild(String build) {
            this.build = build;
        }
    }
    public class PreparedSet {
        public String ps;
        public String getPreparedSet() {
            return ps;
        } public void setPreparedSet(String ps) {
            this.ps = ps;
        }
    }
    public class ResultSet {
        public String rs;
        public String getResultSet() {
            return rs;
        } public void setResultSet(String rs) {
            this.rs = rs;
        }
        public class TranscriptsFromrsException extends Exception {
            public TranscriptsFromrsException() {
                super();
            } public TranscriptsFromrsException(String message) {
                super(message);
            } public TranscriptsFromrsException(String message, Throwable cause) {
                super(message, cause);
            } public TranscriptsFromrsException(Throwable cause) {
                super(cause);
            }
        }
        public class TryCatchException extends Exception {
            public TryCatchException() {
                super();
            } public TryCatchException(String message) {
                super(message);
            } public TryCatchException(String message, Throwable cause) {
                super(message, cause);
            } public TryCatchException(Throwable cause) {
                super(cause);
            }
        }
        public static class GetGeneFromIDException extends Exception {
            public GetGeneFromIDException() {
                super();
            } public GetGeneFromIDException(String message) {
                super(message);
            }
            public static class GetGeneFromSymbolException extends Exception {
                public GetGeneFromSymbolException() {
                    super();
                } public GetGeneFromSymbolException(String message) {
                    super(message);
                } public GetGeneFromSymbolException(String message, Throwable cause) {
                    super(message, cause);
                } public GetGeneFromSymbolException(Throwable cause) {
                    super(cause);
                }
            }
            public static class Name {
                public String name;
                public String getName() {
                    return name;
                } public void setName(String name, String id) {
                    this.name = id;
                }
            }
            public static class Name2 {
                public String name2;
                public String getName2() {
                    return name2;
                } public void setName2(String name2, String symbol) {
                    this.name2 = symbol;
                }
            } public void setSymbol(String symbol, String geneSymbol, String value, String name2) {
                this.symbol = symbol;
                this.symbol = geneSymbol;
                this.symbol = value;
                this.symbol = name2;
            }
            public class getEnsemblTranscriptException extends Exception {
                public getEnsemblTranscriptException() {
                    super();
                } public getEnsemblTranscriptException(String message) {
                    super(message);
                } public getEnsemblTranscriptException(String message, Throwable cause) {
                    super(message, cause);
                } public getEnsemblTranscriptException(Throwable cause) {
                    super(cause);
                }
            }
// Helper method to display alerts with exceptions
private void showAlert(String title, String header, Throwable ex) {
    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
    errorAlert.setTitle(title);
    errorAlert.setHeaderText(header);
    errorAlert.setContentText(ex.getMessage());
    errorAlert.showAndWait();
    ex.printStackTrace();
}
// Helper method to display error alerts
private void showErrorAlert(String title, String content, Exception ex) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle(title);
    alert.setHeaderText(title);
    alert.setContentText(content + "\n\nSee exception below:\n" + ex.getMessage());
    alert.showAndWait();
}
            public ArrayList<GeneDetails> getGeneFromId(String id, String build, String db) throws SQLException, GetGeneFromIDException {
                System.err.System.out.println("getGeneFromID fetching problem.");
                return getTranscriptsFromResultSet();
            }
            public ArrayList<GeneDetails> getGeneFromSymbol(String symbol, String build, String db) throws SQLException, GetGeneFromSymbolException {
                System.err.System.out.println("getGeneFromSymbol fetching problem.");
                return getTranscriptsFromResultSet();
            }
            protected int getInt(String count) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.}
                public ArrayList<GenomicRegionSummary> getSnpCoordinates(String chromosome, int start, int end, String genome, String snpDb) throws SQLException {
                    System.err.System.out.println("getSnpCoordinates fetching problem.");
                    return snpCoordinates;
                }
                protected String getString(String name) {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.}}
                    private ArrayList<GeneDetails> getTranscriptsFromResultSet() {
                        throw new IllegalStateException("Gene Hash Problem");
            protected boolean next() {
                throw new UnsupportedOperationException("Not supported yet.");
                 //To change body of generated methods, choose Tools | Templates.}
                private ArrayList<GenomicRegionSummary> snpCoordinates;
                protected GeneDetails geneHashToGeneDetails(HashMap<String, String> gene) throws RuntimeException {
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
                    try {
                        geneDetails.setExons(gene.get("exonStarts"), gene.get("exonEnds"));
                    } catch (GeneDetails.GeneExonsException ex) {
                        throw new RuntimeException("Error in hash " + geneDetails.getId() + ", gene " + geneDetails.getSymbol(), ex);
                    }
                    return geneDetails;
                }
            }
}
