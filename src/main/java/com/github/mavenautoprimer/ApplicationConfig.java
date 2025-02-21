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
 * This program was written by David A Parry and
 * edited by Bert Gold <bgold04@gmail.com> and ChatGPT
 * February 16, 2025
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.github.mavenautoprimer;

import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Files;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.util.*;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;
import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class ApplicationConfig {
    private final UcscBuildAndTableFetcher buildsAndTables = new UcscBuildAndTableFetcher();
    private final String fileSeparator = System.getProperty("file.separator");
    private final File configDir = new File(System.getProperty("user.home") + fileSeparator + ".MavenAutoPrimer");
    private final File genomeXmlFile = new File(configDir + fileSeparator + "genome.xml");
    private final File tableDir = new File(configDir + fileSeparator + "tables");
    private final File misprimingDir = new File(configDir + fileSeparator + "mispriming_libs");
    private final List<String> misprimingLibs = Arrays.asList("human", "rodent", "drosophila", "none");
    private final File thermoDir = new File(configDir + fileSeparator + "thermo_config");
    private File primer3ex;
    private final HashMap<String, String> buildToMapMaster = new HashMap<>();
    private final LinkedHashMap<String, String> buildToDescription = new LinkedHashMap<>();
    private final HashMap<String, LinkedHashSet<String>> buildToTables = new HashMap<>();

    public ApplicationConfig() throws IOException {
        this.primer3ex = File.createTempFile("primer3", "exe");
        primer3ex.deleteOnExit();
    }

    public File getGenomeXmlFile() {
        return genomeXmlFile;
    }

    public File extractP3Executable() throws IOException {
        InputStream inputStream;
        String osName = System.getProperty("os.name");
        String resource = osName.equals("Mac OS X") ? "primer3_core_macosx" : osName.equals("Linux") && System.getProperty("os.arch").endsWith("64") ? "primer3_core" : "primer3_core32";
        inputStream = this.getClass().getResourceAsStream(resource);
        OutputStream outputStream = new FileOutputStream(primer3ex);
        byte[] bytes = new byte[1024];
        int read;
        while ((read = inputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, read);
        }
        inputStream.close();
        outputStream.close();
        primer3ex.setExecutable(true);
        return primer3ex;
    }

    public File getP3Executable() {
        return primer3ex;
    }

    public File extractMisprimingLibs() throws IOException, ZipException {
        if (!misprimingDir.exists() || Arrays.stream(misprimingLibs.toArray()).anyMatch(s -> !new File(misprimingDir + fileSeparator + s).exists())) {
            File misprimingZip = File.createTempFile("misprime", ".zip");
            misprimingZip.deleteOnExit();
            InputStream inputStream = this.getClass().getResourceAsStream("mispriming_libraries.zip");
            OutputStream outputStream = new FileOutputStream(misprimingZip);
            byte[] bytes = new byte[1024];
            int read;
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
            inputStream.close();
            outputStream.close();
            new ZipFile(misprimingZip).extractAll(misprimingDir.toString());
        }
        return misprimingDir;
    }

    public File getMisprimingDir() {
        return misprimingDir;
    }

    public void writeGenomeXmlFile(Document xmldoc) throws IOException {
        if (!configDir.exists()) {
            configDir.mkdir();
        }
        File temp = File.createTempFile("temp_genome", ".xml");
        OutputFormat format = OutputFormat.createPrettyPrint();
        try (BufferedWriter out = new BufferedWriter(new FileWriter(temp))) {
            XMLWriter writer = new XMLWriter(out, format);
            writer.write(xmldoc);
        }
        Files.move(temp.toPath(), genomeXmlFile.toPath(), REPLACE_EXISTING);
    }

    public void readGenomeXmlFile() throws IOException, DocumentException {
        if (!genomeXmlFile.exists()) {
            InputStream inputStream = this.getClass().getResourceAsStream("genome.xml");
            if (inputStream == null) {
                buildsAndTables.readDasGenomeXmlDocument();
                writeGenomeXmlFile(buildsAndTables.getDasGenomeXmlDocument());
                return;
            }
            OutputStream outputStream = new FileOutputStream(genomeXmlFile);
            byte[] bytes = new byte[1024];
            int read;
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
            inputStream.close();
            outputStream.close();
        }
        readGenomeXmlFile(genomeXmlFile);
    }

    public void readGenomeXmlFile(File xml) throws IOException, DocumentException {
        SAXReader reader = new SAXReader();
        Document doc = reader.read(xml);
        buildsAndTables.setDasGenomeXmlDocument(doc);
        buildsAndTables.readDasGenomeXmlDocument();
        buildToDescription.putAll(buildsAndTables.getBuildToDescription());
        buildToMapMaster.putAll(buildsAndTables.getBuildToMapMaster());
    }

    public LinkedHashMap<String, String> getBuildToDescription() {
        return buildToDescription;
    }

    public void setBuildToDescription(LinkedHashMap<String, String> buildDesc) {
        buildToDescription.clear();
        buildToDescription.putAll(buildDesc);
    }
}
