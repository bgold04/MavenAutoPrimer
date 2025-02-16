 * Copyright (C) 2014 David A. Parry <d.a.parry@leeds.ac.uk>
 * (at your option) any later version.
public class UcscBuildAndTableFetcher {
    private HashMap<String, String> buildToMapMaster = new HashMap<>();
    private LinkedHashMap<String, String> buildToDescription = new LinkedHashMap<>();
//maps build name to description e.g. hg19 => 'Human Feb. 2009 (GRCh37/hg19) Genome at UCSC'
    public void connectToUcsc() throws DocumentException, MalformedURLException {
        SAXReader reader = new SAXReader();
        URL url = new URL("http://genome.ucsc.edu/cgi-bin/das/dsn");
        //URL url = new URL("http://genome-euro.ucsc.edu/cgi-bin/das/dsn");
        dasGenomeXml  = reader.read(url);
        readDasGenomeXmlDocument();
    }
    public void readDasGenomeXmlDocument() {
        if (dasGenomeXml == null) {
        }
        Element root = dasGenomeXml.getRootElement();
        for ( Iterator i = root.elementIterator( "DSN" ); i.hasNext(); ) {
            Element dsn = (Element) i.next();
            Element source = dsn.element("SOURCE");
            Attribute build = source.attribute("id");
            Element mapmaster = dsn.element("MAPMASTER");
            Element desc = dsn.element("DESCRIPTION");
            buildToMapMaster.put(build.getValue(), mapmaster.getText());
            buildToDescription.put(build.getValue(), desc.getText());
        }
    }
    public Document getDasGenomeXmlDocument() {
    }
    public void setDasGenomeXmlDocument(Document document) {
    }
    public HashMap<String, String> getBuildToMapMaster() {
    }
    public LinkedHashMap<String, String> getBuildToDescription() {
    }
    public ArrayList<String> getBuildIds() {
        return new ArrayList<>(buildToDescription.keySet());
    }
    public String getGenomeDescription(String genome) {
        return buildToDescription.get(genome);
    }
    public Document getTableXmlDocument(String build) throws DocumentException, MalformedURLException {
        SAXReader reader = new SAXReader();
        URL url = new URL("http://genome.ucsc.edu/cgi-bin/das/" + build + "/types");
        dasXml  = reader.read(url);
    }
    public String retrieveSequence(String build, String chrom, Integer start, Integer end) throws DocumentException, MalformedURLException {
        if (buildToDescription.isEmpty()) {
            this.connectToUcsc();
        }
        if (! buildToMapMaster.containsKey(build)) {
        } else {
            StringBuilder dna = new StringBuilder();
            URL genomeUrl = new URL(buildToMapMaster.get(build) + "/dna?segment="+chrom + ":" + (start + 1) + "," + end);
            SAXReader reader = new SAXReader();
            dasXml  = reader.read(genomeUrl);
            Element root = dasXml.getRootElement();
            for ( Iterator i = root.elementIterator( "SEQUENCE" ); i.hasNext(); ) {
                Element dsn = (Element) i.next();
                Element seq = dsn.element("DNA");
                String text = seq.getText().replaceAll("\n", "");
                dna.append(text);
                //dna.append(seq.getText());
            }
            return dna.toString();
        }
    }
}
