 * Copyright (C) 2014 David A. Parry <d.a.parry@leeds.ac.uk>
 * (at your option) any later version.
public class DasUcscSequenceFetcher {
    HashMap<String, String> buildToMapMaster = new HashMap<>();
    public DasUcscSequenceFetcher() throws DocumentException {
        try {
            SAXReader reader = new SAXReader();
            URL url = new URL("http://genome.ucsc.edu/cgi-bin/das/dsn");
            Document dasXml = reader.read(url);
            Element root = dasXml.getRootElement();
            for (Iterator i = root.elementIterator("DSN"); i.hasNext();) {
                Element dsn = (Element) i.next();
                Element source = dsn.element("SOURCE");
                Attribute build = source.attribute("id");
                Element mapmaster = dsn.element("MAPMASTER");
                buildToMapMaster.put(build.getValue(), mapmaster.getText());
            }
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }
    }
    public String retrieveSequence(String build, String chrom, Integer start, Integer end) throws DocumentException, MalformedURLException {
        if (! buildToMapMaster.containsKey(build)) {
        } else {
            String chromNumber = chrom.replaceFirst("chr", "");
            URL entryPointUrl = new URL(buildToMapMaster.get(build) + "/entry_points");
            SAXReader reader = new SAXReader();
            dasXml  = reader.read(entryPointUrl);
            Element root = dasXml.getRootElement();
            for ( Iterator i = root.elementIterator( "ENTRY_POINTS" ); i.hasNext(); ) {
                Element dsn = (Element) i.next();
                for (Iterator j = dsn.elementIterator("SEGMENT"); j.hasNext();) {
                    Element seg = (Element) j.next();
                    String id = seg.attributeValue("id");
                    if (id != null && id.equals(chromNumber)) {
                        String stop = seg.attributeValue("stop");
                        length = Integer.valueOf(stop);
                    }
                }
            }
            if (length > 0) {
            }
            StringBuilder dna = new StringBuilder();
            URL genomeUrl = new URL(buildToMapMaster.get(build) + "/dna?segment="+chrom + ":" + (start + 1) + "," + end);
            dasXml  = reader.read(genomeUrl);
            root = dasXml.getRootElement();
            for ( Iterator seqIter = root.elementIterator( "SEQUENCE" ); seqIter.hasNext(); ) {
                Element seqDsn = (Element) seqIter.next();
                Element seq = seqDsn.element("DNA");
                String text = seq.getText().replaceAll("\n", "");
                dna.append(text);
            }
            return dna.toString();
        }
    }
}
