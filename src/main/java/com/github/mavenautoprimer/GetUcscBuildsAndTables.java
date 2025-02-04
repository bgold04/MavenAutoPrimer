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

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 *
 * @author David A. Parry <d.a.parry@leeds.ac.uk>
 *
 * Connect to DAS server on UCSC and retrieve all genome builds in constructor.
 * The method getAvailableTables returns all available tables for a given build.
 */
public class GetUcscBuildsAndTables {

    private final HashMap<String, String> buildToMapMaster = new HashMap<>();
    // Maps build name to URL, e.g., hg19 => http://genome.cse.ucsc.edu:80/cgi-bin/das/hg19

    private final LinkedHashMap<String, String> buildToDescription = new LinkedHashMap<>();
    // Maps build name to description, e.g., hg19 => 'Human Feb. 2009 (GRCh37/hg19) Genome at UCSC'

    private Document dasGenomeXml;

    /**
     * Connects to UCSC's DAS server and retrieves genome build information.
     *
     * @throws DocumentException If parsing the XML fails.
     * @throws MalformedURLException If the UCSC DAS URL is malformed.
     */
    public void connectToUcsc() throws DocumentException, MalformedURLException {
        SAXReader reader = new SAXReader();
        URL url = new URL("http://genome.ucsc.edu/cgi-bin/das/dsn");
        dasGenomeXml = reader.read(url);
        readDasGenomeXmlDocument();
    }

    /**
     * Reads the XML document from UCSC DAS and populates build mappings.
     */
    public void readDasGenomeXmlDocument() {
        if (dasGenomeXml == null) {
            return;
        }
        Element root = dasGenomeXml.getRootElement();
        for (Iterator<?> i = root.elementIterator("DSN"); i.hasNext(); ) {
            Element dsn = (Element) i.next();
            Element source = dsn.element("SOURCE");
            Attribute build = source.attribute("id");
            Element mapmaster = dsn.element("MAPMASTER");
            Element desc = dsn.element("DESCRIPTION");

            if (build != null && mapmaster != null && desc != null) {
                buildToMapMaster.put(build.getValue(), mapmaster.getText());
                buildToDescription.put(build.getValue(), desc.getText());
            }
        }
    }

    public Document getDasGenomeXmlDocument() {
        return dasGenomeXml;
    }

    public void setDasGenomeXmlDocument(Document document) {
        dasGenomeXml = document;
    }

    public HashMap<String, String> getBuildToMapMaster() {
        return buildToMapMaster;
    }

    public LinkedHashMap<String, String> getBuildToDescription() {
        return buildToDescription;
    }

    public ArrayList<String> getBuildIds() {
        return new ArrayList<>(buildToDescription.keySet());
    }

    public String getGenomeDescription(String genome) {
        return buildToDescription.get(genome);
    }

    /**
     * Retrieves an XML document containing table information for a given genome build.
     *
     * @param build The genome build ID.
     * @return An XML document containing table data.
     * @throws DocumentException If XML parsing fails.
     * @throws MalformedURLException If the UCSC DAS URL is malformed.
     */
    public Document getTableXmlDocument(String build) throws DocumentException, MalformedURLException {
        SAXReader reader = new SAXReader();
        URL url = new URL("http://genome.ucsc.edu/cgi-bin/das/" + build + "/types");
        return reader.read(url);
    }

    /**
     * Retrieves a DNA sequence from UCSC DAS.
     * Uses 0-based coordinates for compatibility with gene tables, BED format, etc.
     * UCSC DAS uses 1-based coordinates, so this method adjusts accordingly.
     *
     * @param build The genome build ID.
     * @param chrom The chromosome name.
     * @param start The start position (0-based).
     * @param end The end position.
     * @return The DNA sequence as a string, or null if the build is unavailable.
     * @throws DocumentException If XML parsing fails.
     * @throws MalformedURLException If the UCSC DAS URL is malformed.
     */
    public String retrieveSequence(String build, String chrom, Integer start, Integer end) throws DocumentException, MalformedURLException {
        if (buildToDescription.isEmpty()) {
            this.connectToUcsc();
        }
        if (!buildToMapMaster.containsKey(build)) {
            return null;
        } else {
            StringBuilder dna = new StringBuilder();
            URL genomeUrl = new URL(buildToMapMaster.get(build) + "/dna?segment=" + chrom + ":" + (start + 1) + "," + end);
            SAXReader reader = new SAXReader();
            Document dasXml = reader.read(genomeUrl);
            Element root = dasXml.getRootElement();

            for (Iterator<?> i = root.elementIterator("SEQUENCE"); i.hasNext(); ) {
                Element dsn = (Element) i.next();
                Element seq = dsn.element("DNA");
                if (seq != null) {
                    dna.append(seq.getText().replaceAll("\n", ""));
                }
            }
            return dna.toString();
        }
    }
}
