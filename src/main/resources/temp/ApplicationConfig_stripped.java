*Copyright (C) 2014 David A. Parry <d.a.parry@leeds.ac.uk>
 * (at your option) any later version.
public class ApplicationConfig {
    private final UcscBuildAndTableFetcher buildsAndTables = new UcscBuildAndTableFetcher();
    private final String fileSeparator = System.getProperty("file.separator");
    private final File configDir = new File(System.getProperty("user.home") + fileSeparator + ".MavenAutoPrimer");
    private final File genomeXmlFile = new File(configDir + fileSeparator + "genome.xml");
    private final File tableDir = new File(configDir + fileSeparator + "tables");
    private final File misprimingDir = new File(configDir + fileSeparator + "mispriming_libs");
    private final List<String> misprimingLibs = Arrays.asList("human", "rodent", "drosophila", "none");
    private final File thermoDir = new File(configDir + fileSeparator + "thermo_config");
    private final HashMap<String, String> buildToMapMaster = new HashMap<>();
    private final LinkedHashMap<String, String> buildToDescription = new LinkedHashMap<>();
    private final HashMap<String, LinkedHashSet<String>> buildToTables = new HashMap<>();
    public ApplicationConfig() throws IOException {
        this.primer3ex = File.createTempFile("primer3", "exe");
        primer3ex.deleteOnExit();
    }
    public File getGenomeXmlFile() {
    }
    public File extractP3Executable() throws IOException {
        String osName = System.getProperty("os.name");
        String resource = osName.equals("Mac OS X") ? "primer3_core_macosx" : osName.equals("Linux") && System.getProperty("os.arch").endsWith("64") ? "primer3_core" : "primer3_core32";
        inputStream = this.getClass().getResourceAsStream(resource);
        OutputStream outputStream = new FileOutputStream(primer3ex);
        while ((read = inputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, read);
        }
        inputStream.close();
        outputStream.close();
        primer3ex.setExecutable(true);
    }
    public File getP3Executable() {
    }
    public File extractMisprimingLibs() throws IOException, ZipException {
        if (!misprimingDir.exists() || Arrays.stream(misprimingLibs.toArray()).anyMatch(s -> !new File(misprimingDir + fileSeparator + s).exists())) {
            File misprimingZip = File.createTempFile("misprime", ".zip");
            misprimingZip.deleteOnExit();
            InputStream inputStream = this.getClass().getResourceAsStream("mispriming_libraries.zip");
            OutputStream outputStream = new FileOutputStream(misprimingZip);
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
            inputStream.close();
            outputStream.close();
            new ZipFile(misprimingZip).extractAll(misprimingDir.toString());
        }
    }
    public File getMisprimingDir() {
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
            }
            OutputStream outputStream = new FileOutputStream(genomeXmlFile);
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
    }
    public void setBuildToDescription(LinkedHashMap<String, String> buildDesc) {
        buildToDescription.clear();
        buildToDescription.putAll(buildDesc);
    }
}
