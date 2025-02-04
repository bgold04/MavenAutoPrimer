/*

*Copyright (C) 2014 David A. Parry <d.a.parry@leeds.ac.uk>

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
 * This program was written by David A Parry and edited by Bert Gold <bgold04@gmail.com>
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.github.mavenautoprimer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class ApplicationConfig {
    final GetUcscBuildsAndTables buildsAndTables = new GetUcscBuildsAndTables();
    String fileSeparator = System.getProperty("file.separator");
    File configDir = new File(System.getProperty("user.home") + fileSeparator + ".MavenAutoPrimer");
    File genomeXmlFile = new File(configDir + fileSeparator + "genome.xml");
    File tableDir = new File(configDir + fileSeparator + "tables");
    File misprimingDir = new File(configDir + fileSeparator + "mispriming_libs");
    List<String> misprimingLibs = Arrays.asList("human", "rodent", "drosophila", "none");
    File thermoDir = new File(configDir + fileSeparator + "thermo_config");
    File primer3ex;

    private HashMap<String, String> buildToMapMaster = new HashMap<>();
    private LinkedHashMap<String, String> buildToDescription = new LinkedHashMap<>();
    private HashMap<String, LinkedHashSet<String>> buildToTables = new HashMap<>();

    public ApplicationConfig() throws IOException {
        this.primer3ex = File.createTempFile("primer3", "exe");
        primer3ex.deleteOnExit();
    }

    public File getGenomeXmlFile() {
        return genomeXmlFile;
    }

    public HashMap<String, String> getBuildToMapMaster() {
        return buildToMapMaster;
    }

    public LinkedHashMap<String, String> getBuildToDescription() {
        return buildToDescription;
    }

    public HashMap<String, LinkedHashSet<String>> getBuildToTables() {
        return buildToTables;
    }

    public void setBuildToMapMaster(HashMap<String, String> buildMaster) {
        buildToMapMaster = buildMaster;
    }

    public void setBuildToDescription(LinkedHashMap<String, String> buildDesc) {
        buildToDescription = buildDesc;
    }

    public void setBuildToTables(HashMap<String, LinkedHashSet<String>> buildTables) {
        buildToTables = buildTables;
    }

    public void writeGenomeXmlFile() throws IOException, DocumentException, MalformedURLException {
        buildsAndTables.readDasGenomeXmlDocument();
        writeGenomeXmlFile(buildsAndTables.getDasGenomeXmlDocument());
    }

    public void writeGenomeXmlFile(Document xmldoc) throws IOException {
        if (!configDir.exists()) {
            configDir.mkdir();
        }
        File temp = File.createTempFile("temp_genome", ".xml");
        OutputFormat format = OutputFormat.createPrettyPrint();
        BufferedWriter out = new BufferedWriter(new FileWriter(temp));
        XMLWriter writer = new XMLWriter(out, format);
        writer.write(xmldoc);
        out.close();
        Files.move(temp.toPath(), genomeXmlFile.toPath(), REPLACE_EXISTING);
    }
}
