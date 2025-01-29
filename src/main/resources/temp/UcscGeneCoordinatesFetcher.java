 * Copyright (C) 2014 David A. Parry <d.a.parry@leeds.ac.uk>
 * (at your option) any later version.
public class UcscGeneCoordinatesFetcher extends GeneCoordinatesFetcher {
    final ArrayList<String> fields = new ArrayList<>(Arrays.asList("name", "chrom", "txStart", "txEnd", "cdsStart", "cdsEnd", "exonCount", "exonStarts", "exonEnds", "strand"));
    public class GetGeneFromSymbol {
        public ArrayList<GeneDetails> getGeneFromSymbol(String symbol, String build, String db) throws SQLException, GetGeneExonException {
            ArrayList<GeneDetails> transcripts = new ArrayList<>();
            String fieldsToRetrieve = String.join(", ", fields);
            PreparedStatement ps = conn.prepareStatement(query);
            query = ("SELECT kgID, geneSymbol FROM " + build + ".kgXref WHERE "
                     + "geneSymbol='" + symbol +"'");
            ResultSet rs2  = (ResultSet) ps.executeQuery(query);
            while (rs2.next()) {
                query = ("SELECT " + fieldsToRetrieve +
                         rs2.getString("kgID") + "'");
                ResultSet rs = (ResultSet) ps.executeQuery(query);
                transcripts.addAll(getTranscriptsFromResultSet(rs, symbol));
            }
        }
        public ArrayList<GeneDetails> getGeneFromId(String id, String build, String db)
        throws SQLException, GetGeneExonException {
            String fieldsToRetrieve = String.join(", ", fields);
            PreparedStatement ps = conn.prepareStatement(query);
            query = ("SELECT " + fieldsToRetrieve +
                     " FROM " + build + "." + db + " WHERE name='"+ id + "'");
            ResultSet rs  = (ResultSet) ps.executeQuery(query);
            query = ("SELECT kgID, geneSymbol FROM " + build + ".kgXref WHERE kgID='" + id +"'");
            ResultSet rs2  = (ResultSet) ps.executeQuery(query);
            String symbol = new String();
            while(rs2.next()) {
                symbol = rs2.getString("geneSymbol");
            }
            return getTranscriptsFromResultSet(rs, symbol);
        }
        protected ArrayList<GeneDetails> getTranscriptsFromResultSet(ResultSet rs, String symbol)
        throws SQLException, GetGeneExonException {
            ArrayList<HashMap<String, String>> genes = new ArrayList<>();
            while (rs.next()) {
                HashMap<String, String> geneCoords = new HashMap<>();
                for (String f: fields) {
                    geneCoords.put(f, rs.getString(f));
                }
                geneCoords.put("name2", symbol);
                genes.add(geneCoords);
            }
            ArrayList<GeneDetails> TranscriptsToReturn = new ArrayList<>();
            for (HashMap<String, String> gene: genes) {
                GeneDetails GeneDetails = GeneHashToGeneDetails(gene);
                TranscriptsToReturn.add(GeneDetails);
            }
        }
        private GeneDetails GeneHashToGeneDetails(HashMap<String, String> gene) {
            throw new UnsupportedOperationException("Method Creation to support GeneHashToGeneDetails is inadquate");
        }
    }
}
