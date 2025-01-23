/*things to add:
    server choice

*/

/*
 * Copyright (C) 2014 David A. Parry <d.a.parry@leeds.ac.uk>
 * Edited by Bert Gold, PhD <bgold04@gmail.com> and ChatGPT
 *
 * Revised January 17, 2025
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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import net.lingala.zip4j.exception.ZipException;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import com.sun.javafx.collections.annotations;
import org.eclipse.swt;
import org.eclipse.swt.graphics;
import org.eclipse.swt.widgets;
import org.eclipse.swt.dnd;
import org.eclipse.swt.events;
import org.eclipse.swt.widgets;
import org.eclipse.swt.graphics;
public class AutoPrimer3A extends Application {

    /*
    * @param args the command line arguments
    */

    @FXML
    AnchorPane mainPane;
    //menus
    @FXML
    MenuBar menuBar;
    @FXML
    MenuItem refreshMenuItem;
    @FXML
    MenuItem quitMenuItem;
    @FXML
    MenuItem helpMenuItem;
    @FXML
    MenuItem aboutMenuItem;
    //tabs
    @FXML
    TabPane mainTabPane;
    @FXML
    Tab genesTab;
    @FXML
    Tab primerTab;
    @FXML
    Tab coordTab;
    //Genes tab components
    @FXML
    Button runButton;
    @FXML
    Button cancelButton;
    @FXML
    Button refreshButton;
    @FXML
    ChoiceBox genomeChoiceBox;
    @FXML
    ChoiceBox databaseChoiceBox;
    @FXML
    ChoiceBox snpsChoiceBox;
    @FXML
    ChoiceBox designToChoiceBox;
    @FXML
    TextField minDistanceTextField;
    @FXML
    TextField flankingRegionsTextField;
    @FXML
    TextField genesTextField;
    @FXML
    ProgressIndicator progressIndicator = new ProgressIndicator();
    @FXML
    Label progressLabel;

    //Coordinates tab components
    @FXML
    TextArea regionsTextArea;
    @FXML
    ChoiceBox genomeChoiceBox2;
    @FXML
    ChoiceBox snpsChoiceBox2;
    @FXML
    TextField minDistanceTextField2;
    @FXML
    TextField flankingRegionsTextField2;
    @FXML
    ProgressIndicator progressIndicator2 = new ProgressIndicator();
    @FXML
    Label progressLabel2;
    @FXML
    Button runButton2;
    @FXML
    Button cancelButton2;
    @FXML
    Button loadFileButton;
    @FXML
    Button clearButton;
    @FXML
    Label regionsLabel;
    @FXML
    CheckBox useRegionNamesCheckBox;


    //Primer3 Settings tab components
    @FXML
    TextField minSizeTextField;
    @FXML
    TextField optSizeTextField;
    @FXML
    TextField maxSizeTextField;
    @FXML
    TextField maxDiffTextField;
    @FXML
    TextField sizeRangeTextField;
    @FXML
    TextField maxMisprimeTextField;
    @FXML
    TextField minTmTextField;
    @FXML
    TextField optTmTextField;
    @FXML
    TextField maxTmTextField;
    @FXML
    TextField splitRegionsTextField;
    @FXML
    ChoiceBox misprimingLibraryChoiceBox;
    @FXML
    Button resetValuesButton;
    @FXML
    CheckBox autoSelectMisprimingLibraryCheckBox;

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
    public String t;
    public String regions;
    public Object document;
    public Object node;

    String VERSION = "3A";
    Boolean CANRUN = false;
    final BuildToMisprimingLibrary buildToMisprime = new BuildToMisprimingLibrary();
    Boolean autoSelectMisprime = true;
    final GetUcscBuildsAndTables buildsAndTables = new GetUcscBuildsAndTables();

    File primer3ex;
    File thermoConfig;
    String defaultSizeRange = "150-250 100-300 301-400 401-500 501-600 "
                              + "601-700 701-850 851-1000 1000-2000";
    HashMap<TextField, String> defaultPrimer3Values = new HashMap<>();
    String serverUrl = "http://genome.ucsc.edu";

    File configDirectory;
    AutoPrimer3Config ap3Config;
    File misprimeDir;
    HashSet<String> checkedAlready = new HashSet<>();

    int MAX_GENES_PER_DESIGN = 10;
    int MAX_LINES_PER_DESIGN = 100;
    int MAX_REGION_SIZE = 100000;

    // Method to show error alerts
    private void showErrorAlert(String title, String content, Exception ex) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(content + "\n\nSee exception below:\n" + ex.getMessage());
        alert.showAndWait();
    }

// TODO: Move UCSC-related logic to a utility class after migrating to Java 11 and Maven.

    /**
     * Checks UCSC tables for the given genome in a background task.
     * Handles success and failure scenarios, including updating the UI
     * and displaying alerts for errors.
     *
     * @param genome The genome for which tables are being checked.
     */

// Method to check UCSC tables
    private void checkUcscTables(final String genome) {
        // Create a background task to check UCSC tables
        final Task<Document> checkUcscTablesTask = new Task<>() {
            @Override
            protected Document call() throws DocumentException, MalformedURLException {
                System.out.println("Checking tables for " + genome);
                return buildsAndTables.getTableXmlDocument(genome); // Retrieve the document
            }
        };

        // Handle task success
        checkUcscTablesTask.setOnSucceeded(e -> {
            System.out.println("Finished getting tables for " + genome);
            Document doc = (Document) e.getSource().getValue();
            try {
                System.out.println("Processing document...");
                // Add document processing logic here
            } catch (Exception ex) {
                showErrorAlert("Error Processing Tables",
                               "An error occurred while processing the tables for genome " + genome, ex);
            }
        });

        // Handle task failure
        checkUcscTablesTask.setOnFailed(e -> {
            Throwable exception = e.getSource().getException();
            if (exception != null) {
                exception.printStackTrace();
            }
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Gene Search Failed!");
            alert.setContentText("AutoPrimer3A encountered an error when performing "
                                 + "a background check of available gene/SNP tables for genome " + genome + ".");
            alert.showAndWait();
        });

        // Start the task in a new thread
        new Thread(checkUcscTablesTask).start();
    }
























// Override JavaFX Application start method
    @Override
    public void start(final Stage primaryStage) {
        try {
            try {
                AnchorPane page;
                if (System.getProperty("os.name").equals("Mac OS X")) {
                    page = FXMLLoader.load(AutoPrimer3A.class.getResource("AutoPrimer3Mac.fxml"));
                } else {
                    page = FXMLLoader.load(AutoPrimer3A.class.getResource("AutoPrimer3A.fxml"));
                }
                Scene scene = new Scene(page);
                primaryStage.setScene(scene);
                primaryStage.setTitle("AutoPrimer3A");
                primaryStage.setResizable(false);
                primaryStage.show();
                primaryStage.getIcons().add(new Image(this.getClass().getResourceAsStream("icon.png")));
            }
            primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent e) {
                    Platform.exit();
                    System.exit(0);
                }
            });

            if (System.getProperty("os.name").equals("Mac OS X")) {
                final KeyCombination macCloseKeyComb =
                    new KeyCodeCombination(KeyCode.W, KeyCombination.SHORTCUT_DOWN);

                scene.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent ev) {
                        if (macCloseKeyComb.match(ev)) {
                            primaryStage.close();
                        }
                    }
                });
            }
        } catch (Exception ex) {
            Logger.getLogger(AutoPrimer3A.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//##########################################
//#INSERT FROM CHAT GPT BELOW
//###########################################

    public void initialize(URL url, ResourceBundle rb) {
        try {
            ap3Config.readGenomeXmlFile();
            // ap3Config.readTablesXmlFiles();
        } catch (IOException | DocumentException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Config Error");
            alert.setHeaderText("Error Reading AutoPrimer3A Genome Database");
            alert.setContentText("AutoPrimer3A encountered an error reading local stored genome details - see exception below:\n\n" + ex.getMessage());
            alert.showAndWait();
        }

        try {
            primer3ex = ap3Config.extractP3Executable();
            misprimeDir = ap3Config.extractMisprimingLibs();
            thermoConfig = ap3Config.extractThermoConfig();
            ap3Config.extractTableXml();
        } catch (IOException | ZipException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Config Error");
            alert.setHeaderText("Error Extracting Primer3 Files");
            alert.setContentText("AutoPrimer3A encountered an error while trying to extract the primer3 executable and config files:\n\n" + ex.getMessage());
            alert.showAndWait();
        }

        refreshButton.setOnAction(actionEvent -> refreshDatabase());
        refreshMenuItem.setOnAction(actionEvent -> refreshDatabase());
        quitMenuItem.setOnAction(e -> Platform.exit());
        helpMenuItem.setOnAction(ev -> showHelp());
        aboutMenuItem.setOnAction(this::showAbout);

        genomeChoiceBox.getSelectionModel().selectedIndexProperty().addListener((ov, value, new_value) -> {
            if (new_value.intValue() >= 0) {
                String id = genomeChoiceBox.getItems().get(new_value.intValue());
                genomeChoiceBox.setTooltip(new Tooltip(ap3Config.getBuildToDescription().get(id)));
                if (autoSelectMisprime) {
                    Platform.runLater(() -> selectMisprimingLibrary(id));
                }
                getBuildTables(id, false);
            }
        });

        genomeChoiceBox.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN) {
                event.consume();
            } else if (event.getCode().isLetterKey()) {
                String c = event.getText().toLowerCase();
                List<String> items = genomeChoiceBox.getItems();
                int s = genomeChoiceBox.getSelectionModel().selectedIndexProperty().get();
                for (int i = s + 1; i < items.size(); i++) {
                    if (items.get(i).toLowerCase().startsWith(c)) {
                        genomeChoiceBox.getSelectionModel().select(i);
                        return;
                    }
                }
                for (int i = 0; i < s; i++) {
                    if (items.get(i).toLowerCase().startsWith(c)) {
                        genomeChoiceBox.getSelectionModel().select(i);
                        return;
                    }
                }
            }
        });

// End of replacement from ChatGPT
// Start learning about lambda's replacing method definitions

// Using a lambda instead of an anonymous class for the event filter
        genomeChoiceBox2.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN) {
                event.consume();
            } else if (event.getCode().isLetterKey()) {
                String c = event.getText().toLowerCase();
                List items = genomeChoiceBox2.getItems();
                int s = genomeChoiceBox2.getSelectionModel().selectedIndexProperty().get();
                for (int i = s + 1; i < items.size(); i++) {
                    if (items.get(i).toString().toLowerCase().startsWith(c)) {
                        genomeChoiceBox2.getSelectionModel().select(i);
                        return;
                    }
                }
                for (int i = 0; i < s; i++) { // wrap around
                    if (items.get(i).toString().toLowerCase().startsWith(c)) {
                        genomeChoiceBox2.getSelectionModel().select(i);
                        return;
                    }
                }
            }
        });

// Using a lambda for ChangeListener
        autoSelectMisprimingLibraryCheckBox.selectedProperty().addListener(
        (ObservableValue<? extends Boolean> ov, Boolean value, Boolean newValue) -> {
            autoSelectMisprime = newValue;
            final String id = (String) genomeChoiceBox.getSelectionModel().getSelectedItem();
            Platform.runLater(() -> {
                if (newValue) {
                    selectMisprimingLibrary(id);
                } else {
                    misprimingLibraryChoiceBox.getSelectionModel().select("none");
                }
            });
        });





        genomeChoiceBox.getItems().clear();
        genomeChoiceBox.getItems().addAll(new ArrayList<>(ap3Config.getBuildToDescription().keySet()));
        genomeChoiceBox.getSelectionModel().selectFirst();

        misprimingLibraryChoiceBox.getItems().add("none");
        for (File f: misprimeDir.listFiles()) {
            misprimingLibraryChoiceBox.getItems().add(f.getName());
        }
        misprimingLibraryChoiceBox.getSelectionModel().selectFirst();
        minDistanceTextField.addEventFilter(KeyEvent.KEY_TYPED, checkNumeric());
        minDistanceTextField2.addEventFilter(KeyEvent.KEY_TYPED, checkNumeric());
        flankingRegionsTextField.addEventFilter(KeyEvent.KEY_TYPED, checkNumeric());
        flankingRegionsTextField2.addEventFilter(KeyEvent.KEY_TYPED, checkNumeric());
        minSizeTextField.addEventFilter(KeyEvent.KEY_TYPED, checkNumeric());
        optSizeTextField.addEventFilter(KeyEvent.KEY_TYPED, checkNumeric());
        maxSizeTextField.addEventFilter(KeyEvent.KEY_TYPED, checkNumeric());
        maxDiffTextField.addEventFilter(KeyEvent.KEY_TYPED, checkNumeric());
        maxMisprimeTextField.addEventFilter(KeyEvent.KEY_TYPED, checkNumeric());
        sizeRangeTextField.addEventFilter(KeyEvent.KEY_TYPED, checkRange());
        minTmTextField.addEventFilter(KeyEvent.KEY_TYPED, checkDecimal());
        optTmTextField.addEventFilter(KeyEvent.KEY_TYPED, checkDecimal());
        maxTmTextField.addEventFilter(KeyEvent.KEY_TYPED, checkDecimal());
        splitRegionsTextField.addEventFilter(KeyEvent.KEY_TYPED, checkNumeric());
        defaultPrimer3Values.put(minSizeTextField, "18");
        defaultPrimer3Values.put(optSizeTextField, "20");
        defaultPrimer3Values.put(maxSizeTextField, "27");
        defaultPrimer3Values.put(maxDiffTextField, "10");
        defaultPrimer3Values.put(minTmTextField, "57.0");
        defaultPrimer3Values.put(optTmTextField, "59.0");
        defaultPrimer3Values.put(maxTmTextField, "62.0");
        defaultPrimer3Values.put(splitRegionsTextField, "300");
        defaultPrimer3Values.put(maxMisprimeTextField, "12");
        defaultPrimer3Values.put(sizeRangeTextField, defaultSizeRange);

        minDistanceTextField2.textProperty().bindBidirectional
        (minDistanceTextField.textProperty());
        flankingRegionsTextField2.textProperty().bindBidirectional
        (flankingRegionsTextField.textProperty());

        resetValuesButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                resetPrimerSettings();
            }
        });

        mainTabPane.getSelectionModel().selectedItemProperty().addListener(
        new ChangeListener<Tab>() {
            @Override
            public void changed (ObservableValue<? extends Tab> ov,
                                 Tab ot, Tab nt) {
                if (ot.equals(primerTab)) {
                    resetEmptyPrimerSettings();
                }
                if (nt.equals(genesTab)) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            genesTextField.requestFocus();
                            if (!progressLabel.textProperty().isBound()) {
                                progressLabel.setText("");
                                checkGeneTextField();
                            }
                        }
                    });
                } else if (nt.equals(coordTab)) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            if (!progressLabel.textProperty().isBound()) {
                                progressLabel.setText("");
                                displayValidRegions();
                            }
                        }
                    });
                }
            }
        }
        );

        sizeRangeTextField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable,
                                Boolean oldValue, Boolean newValue ) {
                if (!newValue) {
                    if (!checkSizeRange(sizeRangeTextField)) {
                        displaySizeRangeError();
                    }
                }
            }
        });

        regionsTextArea.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                final String oldValue, final String newValue ) {
                //newValue = newValue.trim();
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        if (progressLabel.textProperty().isBound()) {
                            return;
                        }
                        String reg = newValue.replaceAll("(?m)^\\s", "");
                        int regions = reg.split("\\n").length;
                        progressLabel.setText(regions + " lines");
                    }
                });
            }
        });

        regionsTextArea.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable,
                                Boolean oldValue, Boolean newValue ) {
                if (!newValue) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            displayValidRegions();
                        }
                    }
                                     );
                }
            }
        });

        genesTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                final String oldValue, final String newValue ) {
                //newValue = newValue.trim();
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        checkGeneTextField();
                    }
                });

            }
        });

        if (ap3AConfig.getBuildToDescription().isEmpty()) {
            connectToUcsc();
        } else {
            setLoading(false);
            checkUcscGenomes();
        }
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                genesTextField.requestFocus();
            }
        }
                         );
    }

    private void checkGeneTextField() {
        progressLabel.setText("");
        if (!genesTextField.getText().matches(".*\\w.*")) {
            return;
        }
        String[] genes = genesTextField.getText().split("\\s+");
        if (genes.length > MAX_GENES_PER_DESIGN) {
            progressLabel.setText("Warning: " + genes.length + " genes entered,"
                                  + " max per design is " + MAX_GENES_PER_DESIGN);
        } else if (genes.length > 0) {
            progressLabel.setText(genes.length + " genes entered.");
        }
    }

    private void displayValidRegions() {
        if (progressLabel.textProperty().isBound()) {
            return;
        }
        int validRegions = 0;
        int invalidRegions = 0;
        String[] lines = regionsTextArea.getText().replaceAll("(?m)^\\s", "").split("\\n");
        if (lines.length < 1) {
            return;
        }
        if (lines.length == 1 && lines[0].isEmpty()) {
            return;
        }

        for (String r: lines) {
            if (RegionParser.readRegion(r) == null) {
                invalidRegions++;
            } else {
                validRegions++;
            }
        }
        StringBuilder lbl = new StringBuilder(validRegions + " valid regions");
        if (invalidRegions > 0) {
            lbl.append(" (").append(invalidRegions).append(" invalid)");
        }
        progressLabel.setText(lbl.toString());
    }

// Class related to handling mispriming logic
    class BuildToMisprimingLibrary {
        public String getMisprimingLibrary(String stub) {
            // Placeholder logic
            return "none"; // Replace with actual implementation
        }
    }

// Method for selecting a mispriming library
    private void selectMisprimingLibrary(String id) {
        String stub = id.replaceAll("\\d*$", "");
        String lib = buildToMisprime.getMisprimingLibrary(stub);
        misprimingLibraryChoiceBox.getSelectionModel().select(lib);
    }

    private void checkUcscGenomes() {
        final Task<Void> task = getGenomesTask();
        new Thread(task).start();
    }

    private Task<Void> getGenomesTask() {
        return new Task<Void>() {
            @Override
            protected Void call() throws DocumentException, MalformedURLException {
                System.out.println("Checking genome list.");
                buildsAndTables.connectToUcsc();
                return null;
            }
        };
    }


    getGenomesTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
        @Override
        public void handle(WorkerStateEvent e) {
            boolean rewriteConfig = false;
            if (!ap3AConfig.getBuildToDescription().keySet().equals(
                        buildsAndTables.getBuildToDescription().keySet()) &&
                    buildsAndTables.getBuildToDescription() != null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("Repopulating Genomes");
                alert.setContentText(
                    "The available genomes have changed. AutoPrimer3A "
                    + "will now repopulate the genome menu."
                );
                alert.showAndWait();

                System.out.println("Genome list has changed - repopulating genome choice box");
                rewriteConfig = true;
                ap3AConfig.setBuildToDescription(buildsAndTables.getBuildToDescription());
                String currentSel = (String) genomeChoiceBox.getSelectionModel().getSelectedItem();
                genomeChoiceBox.getItems().clear();
                genomeChoiceBox.getItems().addAll(ap3AConfig.getBuildToDescription().keySet());

                if (genomeChoiceBox.getItems().contains(currentSel)) {
                    genomeChoiceBox.getSelectionModel().select(currentSel);
                } else {
                    genomeChoiceBox.getSelectionModel().selectFirst();
                }
            } else {
                System.out.println("Genome list is the same.");
            }

            if (buildsAndTables.getBuildToMapMaster() != null &&
                    !ap3AConfig.getBuildToMapMaster().equals(buildsAndTables.getBuildToMapMaster())) {
                ap3AConfig.setBuildToMapMaster(buildsAndTables.getBuildToMapMaster());
                System.out.println("Build-to-map master has changed - will rewrite.");
                rewriteConfig = true;
            }

            if (rewriteConfig) {
                try {
                    System.out.println("Rewriting output...");
                    ap3AConfig.writeGenomeXmlFile(getDasGenomeXmlDocument());
                } catch (IOException ex) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error Updating Genomes!");
                    alert.setContentText(
                        "AutoPrimer3A encountered an error writing updated genomes to its database file. " +
                        "See exception details below: \n\n" + ex.getMessage()
                    );
                    ex.printStackTrace();
                    alert.showAndWait();
                }
            }
        }
    });



    getGenomesTask.setOnFailed(new EventHandler<WorkerStateEvent>() {
        @Override
        public void handle(WorkerStateEvent e) {
            progressIndicator.setProgress(0);
            System.out.println(e.getSource().getException());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Gene Search Failed!");
            alert.setContentText(
                "AutoPrimer3A encountered an error when performing "
                + "a background check of available genomes."
                + "See exception below."
            );
            alert.showAndWait();
            e.getSource().getException().printStackTrace();
        }
    });



    checkUcscTablesTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
        @Override
        public void handle(WorkerStateEvent e) {
            System.out.println("Finished getting tables for " + genome);
            Document doc = (Document) e.getSource().getValue();

            try {
                if (doc != null && !ap3AConfig.getBuildXmlDocument(genome).asXML().equals(doc.asXML())) {
                    System.out.println("Tables differ!");
                    LinkedHashSet<String> tables = ap3AConfig.readTableFile(doc);

                    if (configTablesDiffer(tables, ap3AConfig.getBuildToTables().get(genome))) {
                        System.out.println("SNP/Gene tables differ!");
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Tables Updated");
                        alert.setHeaderText("Gene/SNP Tables Updated");
                        alert.setContentText(
                            "The Gene/SNP tables for your currently selected genome have been updated."
                        );
                        alert.showAndWait();

                        String g = (String) genomeChoiceBox.getSelectionModel().getSelectedItem();
                        if (g.equals(genome)) {
                            updateChoiceBoxes(tables);
                        }
                    }

                    ap3AConfig.getBuildToTables().put(genome, tables);

                    try {
                        System.out.println("Writing new xml database file");
                        ap3AConfig.writeTableXmlFile(doc, genome);
                    } catch (IOException ex) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Error Updating Genome Information");
                        alert.setContentText(
                            "AutoPrimer3A encountered an error writing "
                            + "updated gene/SNP tables to its database "
                            + "file for genome " + genome + ". "
                            + "See exception below."
                        );
                        alert.showAndWait();
                        ex.printStackTrace();
                    }
                } else {
                    System.out.println("Tables are the same");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void updateChoiceBoxes(LinkedHashSet<String> tables) {
        String curSnp = (String) snpsChoiceBox.getSelectionModel().getSelectedItem();
        snpsChoiceBox.getItems().clear();
        snpsChoiceBox.getItems().add("No");
        snpsChoiceBox.getItems().addAll(getSnpsFromTables(tables));

        if (snpsChoiceBox.getItems().contains(curSnp)) {
            snpsChoiceBox.getSelectionModel().select(curSnp);
        } else {
            snpsChoiceBox.getSelectionModel().selectFirst();
        }

        String curGene = (String) databaseChoiceBox.getSelectionModel().getSelectedItem();
        databaseChoiceBox.getItems().clear();
        databaseChoiceBox.getItems().addAll(getGenesFromTables(tables));

        if (databaseChoiceBox.getItems().contains(curGene)) {
            databaseChoiceBox.getSelectionModel().select(curGene);
        } else {
            databaseChoiceBox.getSelectionModel().selectFirst();
        }
    });


    // Set the onSucceeded handler
    checkUcscTablesTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
        @Override
        public void handle(WorkerStateEvent e) {
            System.out.println("Finished getting tables for " + genome);
            Document doc = (Document) e.getSource().getValue();
        }
        try {
            if (doc != null && !ap3AConfig.getBuildXmlDocument(genome).asXML().equals(doc.asXML())) {
                // Tables differ, check whether it affects relevant tables
                System.out.println("Tables differ!");
                LinkedHashSet<String> tables = ap3AConfig.readTableFile(doc);
                if (configTablesDiffer(tables, ap3AConfig.getBuildToTables().get(genome))) {
                    // Handle SNP/Gene table differences
                    System.out.println("SNP/Gene tables differ!");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Tables Updated");
                    alert.setHeaderText("Gene/SNP Tables Updated");
                    alert.setContentText(
                        "The Gene/SNP tables for your currently selected genome have been updated."
                    );
                    alert.showAndWait();
                }
                String g = (String) genomeChoiceBox.getSelectionModel().getSelectedItem();
                if (g.equals(genome)) {
                    // Update SNPs
                    String curSnp = (String) snpsChoiceBox.getSelectionModel().getSelectedItem();
                    snpsChoiceBox.getItems().clear();
                    snpsChoiceBox.getItems().add("No");
                    snpsChoiceBox.getItems().addAll(getSnpsFromTables(tables));
                    if (snpsChoiceBox.getItems().contains(curSnp)) {
                        snpsChoiceBox.getSelectionModel().select(curSnp);
                    } else {
                        snpsChoiceBox.getSelectionModel().selectFirst();
                    }
                }
            }
        }
    }
// Helper method to display error alerts
// Helper method to display error alerts
    private void showErrorAlert(String title, String content, Exception ex) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(content + "\n\nSee exception below:\n" + ex.getMessage());
        alert.showAndWait();
    }

    checkUcscTablesTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
        @Override
        public void handle(WorkerStateEvent e) {
            System.out.println("Finished getting tables for " + genome);
            Document doc = (Document) e.getSource().getValue();
            try {
                System.out.println("Processing document...");
            } catch (Exception ex) {
                showErrorAlert("Error Processing Tables", "An error occurred while processing the tables for genome " + genome, ex);
            }
        }
    });

    checkUcscTablesTask.setOnFailed(new EventHandler<WorkerStateEvent>() {
        @Override
        public void handle(WorkerStateEvent e) {
            Throwable exception = e.getSource().getException();
            if (exception != null) {
                exception.printStackTrace();
            }
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Gene Search Failed!");
            alert.setContentText("AutoPrimer3A encountered an error when performing "
                                 + "a background check of available gene/SNP tables for genome " + genome + ".");
            alert.showAndWait();

        }



        new Thread(checkUcscTablesTask).start();
    });
// Method to compare SNP and gene tables
    private boolean configTablesDiffer(LinkedHashSet<String> tables, LinkedHashSet<String> configTables) {
        ArrayList<String> tableComp = new ArrayList<>();
        ArrayList<String> configComp = new ArrayList<>();

        for (String t : tables) {
            if (matchesGeneTable(t) || matchesSnpTable(t)) {
                tableComp.add(t);
            }
        }

        for (String t : configTables) {
            if (matchesGeneTable(t) || matchesSnpTable(t)) {
                configComp.add(t);
            }
        }

        return !(tableComp.containsAll(configComp) && configComp.containsAll(tableComp));
    }

// Helper method to extract genes from tables
    private LinkedHashSet<String> getGenesFromTables(LinkedHashSet<String> tables) {
        LinkedHashSet<String> genes = new LinkedHashSet<>();
        for (String t : tables) {
            if (matchesGeneTable(t)) {
                genes.add(t);
            }
        }
        return genes;
    }

// Helper method to check if a table matches SNP
    private boolean matchesSnpTable(String t) {
        return t.matches("^snp\\d+(\\w+)*");
    }

// Helper method to extract SNPs from tables
    private LinkedHashSet<String> getSnpsFromTables(LinkedHashSet<String> tables) {
        LinkedHashSet<String> snps = new LinkedHashSet<>();
        for (String t : tables) {
            if (matchesSnpTable(t)) {
                snps.add(t);
            }
        }
        return snps;
    }

// Helper method to check if a table matches a gene
    private boolean matchesGeneTable(String t) {
        return t.equals("refGene") || t.equals("knownGene") || t.equals("ensGene") || t.equals("xenoRefGene");
    }
//######################## CLASS BEGINS #######################
// Corrected setupBuildsTaskHandlers method
    private void setupBuildsTaskHandlers(Task<LinkedHashMap<String, String>> getBuildsTask) {
        getBuildsTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent e) {
                LinkedHashMap<String, String> buildIds = (LinkedHashMap<String, String>) e.getSource().getValue();
                genomeChoiceBox.getItems().clear();
                genomeChoiceBox.getItems().addAll(buildIds.keySet());
                genomeChoiceBox.getSelectionModel().selectFirst();
                ap3AConfig.setBuildToDescription(buildIds);
                ap3AConfig.setBuildToMapMaster(buildsAndTables.getBuildToMapMaster());

                try {
                    System.out.println("Writing new XML database file");
                    ap3AConfig.writeGenomeXmlFile();
                } catch (DocumentException | IOException ex) {
                    showErrorAlert("Error Writing Genome Data", "Failed to write updated genomes to the database file.", ex);
                }

                setLoading(false);
            }
        });

        getBuildsTask.setOnFailed(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent e) {
                progressIndicator.setProgress(0);
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error Retrieving Genome Information from UCSC");
                alert.setContentText("AutoPrimer3A encountered an error connecting to the UCSC server to retrieve available genomes.");
                alert.showAndWait();
                System.out.println(e.getSource().getException());
                setLoading(false);
                setCanRun(false);
            }
        });

        getBuildsTask.setOnCancelled(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent e) {
                progressIndicator.setProgress(0);
                progressLabel.setText("UCSC connection cancelled.");
                setLoading(false);
                setCanRun(false);
            }
        });

        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                getBuildsTask.cancel();
            }
        });

        progressLabel.setText("Connecting to UCSC...");
        new Thread(getBuildsTask).start(); // Correctly start the thread outside all blocks
    }



    private void setTables(LinkedHashSet<String> tables) {
        LinkedHashSet<String> genes = getGenesFromTables(tables);
        LinkedHashSet<String> snps = getSnpsFromTables(tables);
        databaseChoiceBox.getItems().clear();
        if (genes.isEmpty()) {
            databaseChoiceBox.getItems().add("No gene databases found - please choose another genome.");
            setCanRun(false);
            databaseChoiceBox.getSelectionModel().selectFirst();
        } else {
            databaseChoiceBox.getItems().addAll(genes);
            if (databaseChoiceBox.getItems().contains("refGene")) {
                databaseChoiceBox.getSelectionModel().select("refGene");
            } else {
                databaseChoiceBox.getSelectionModel().selectFirst();
            }
            setCanRun(true);
        }
        snpsChoiceBox.getItems().clear();
        snpsChoiceBox.getItems().add("No");
        snpsChoiceBox.getItems().addAll(snps);
        snpsChoiceBox.getSelectionModel().selectFirst();
    }

//################################# new suggestion from ChatGPT immediately below

    private void getBuildTables(final String id, final boolean forceRefresh) {
        databaseChoiceBox.getItems().clear();
        snpsChoiceBox.getItems().clear();
        if (ap3Config.getBuildToTables().containsKey(id) && !forceRefresh) {
            setTables(ap3Config.getBuildToTables().get(id));
            if (!checkedAlready.contains(id)) {
                checkUcscTables(id);
                checkedAlready.add(id);
            }
            return;
        }

        if (!ap3Config.getBuildXmlFile(id).exists()) {
            checkedAlready.add(id);
        }

        setLoading(true);
        progressLabel.setText("Getting database information for " + id);

        final Task<Document> getTablesTask = new Task<Document>() {
            @Override
            protected Document call() throws DocumentException, MalformedURLException, IOException {
                return ap3Config.getBuildXmlDocument(id, forceRefresh);
            }
        };

        getTablesTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent e) {
                Document doc = (Document) e.getSource().getValue();
                try {
                    LinkedHashSet<String> tables = ap3Config.readTableFile(doc);
                    ap3Config.getBuildToTables().put(id, tables);
                    if (!doc.asXML().equals(ap3Config.getBuildXmlDocument(id).asXML())) {
                        try {
                            ap3Config.writeTableXmlFile(doc, id);
                        } catch (IOException ex) {
                            showErrorAlert("Error Updating Genome Information", "Failed to write updated gene/SNP tables for genome " + id, ex);
                        }
                    }
                    setTables(tables);
                } catch (IOException | DocumentException ex) {
                    showErrorAlert("Error Reading Genome Information", "Error reading updated gene/SNP tables for genome " + id, ex);
                }
                setLoading(false);
            }
        });

        getTablesTask.setOnFailed(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent e) {
                progressLabel.setText("Get Tables Task Failed");
                showErrorAlert("Error Retrieving Gene/SNP Tables", "Failed to retrieve available gene/SNP tables for genome " + id, e.getSource().getException());
                setLoading(false);
                setCanRun(false);
            }
        });

        getTablesTask.setOnCancelled(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent e) {
                progressLabel.setText("Get Tables Task Cancelled");
                setLoading(false);
                setCanRun(false);
            }
        });

        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                getTablesTask.cancel();
            }
        });

        progressIndicator.setProgress(-1);
        new Thread(getTablesTask).start();
    }

    private void displaySizeRangeError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Size Range");
        alert.setHeaderText("Invalid Primer Product Size Range values");
        alert.setContentText("Primer Product Size Range field must be in the format '"
                             + "100-200 200-400' etc.");
        alert.showAndWait();
    }
    EventHandler<KeyEvent> checkNumeric() {
        return new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (!ke.getCharacter().matches("\\d")) {
                    ke.consume();
                }
            }
        };
    }

    EventHandler<KeyEvent> checkDecimal() {
        return new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (!ke.getCharacter().matches("[\\d.]")) {
                    ke.consume();
                }
            }
        };
    }

    EventHandler<KeyEvent> checkRange() {
        return new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (!ke.getCharacter().matches("[\\d-\\s]")) {
                    ke.consume();
                }
            }
        };
    }

    private boolean checkSizeRange(TextField field) {
        List<String> split = Arrays.asList(field.getText().split("\\s+"));
        for (String s: split) {
            if (!s.matches("\\d+-\\d+")) {
                return false;
            }
        }
        return true;
    }
    @Override
    public void start(final Stage primaryStage) {
        try {
            AnchorPane page;
            if (System.getProperty("os.name").equals("Mac OS X")) {
                page = (AnchorPane) FXMLLoader.load(
                           com.github.autoprimer3A.AutoPrimer3A.class.
                           getResource("AutoPrimer3Mac.fxml"));
            } else {
                page = (AnchorPane) FXMLLoader.load(
                           com.github.autoprimer3A.AutoPrimer3A.class.
                           getResource("AutoPrimer3A.fxml"));
            }
            Scene scene = new Scene(page);
            primaryStage.setScene(scene);
            primaryStage.setTitle("AutoPrimer3A");
            //scene.getStylesheets().add(com.github.autoprimer3A.AutoPrimer3A.class.
            //        getResource("autoprimer3A.css").toExternalForm());
            primaryStage.setResizable(false);
            primaryStage.show();
            primaryStage.getIcons().add(new Image(this.getClass().
                                                  getResourceAsStream("icon.png")));
            primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent e) {
                    Platform.exit();
                    System.exit(0);
                }
            });
            final KeyCombination macCloseKeyComb =
                new KeyCodeCombination(KeyCode.W, KeyCombination.SHORTCUT_DOWN);
            if (System.getProperty("os.name").equals("Mac OS X")) {
                scene.addEventHandler(
                KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent ev) {
                        if (macCloseKeyComb.match(ev)) {
                            primaryStage.close();
                        }
                    }
                });
            }
        } catch (Exception ex) {
            Logger.getLogger(AutoPrimer3A.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        menuBar.setUseSystemMenuBar(true);
        genesTextField.requestFocus();
        progressLabel2.textProperty().bind(progressLabel.textProperty());
        progressIndicator2.progressProperty().bind(progressIndicator.progressProperty());
        genomeChoiceBox2.itemsProperty().bind(genomeChoiceBox.itemsProperty());
        genomeChoiceBox2.selectionModelProperty().bind(genomeChoiceBox.selectionModelProperty());
        snpsChoiceBox2.itemsProperty().bind(snpsChoiceBox.itemsProperty());
        snpsChoiceBox2.selectionModelProperty().bind(snpsChoiceBox.selectionModelProperty());
        cancelButton2.cancelButtonProperty().bind(cancelButton.cancelButtonProperty());
        cancelButton2.onActionProperty().bind(cancelButton.onActionProperty());
        cancelButton.setCancelButton(true);
        setLoading(true);
        try {
            ap3AConfig = new AutoPrimer3AConfig();
        } catch (IOException ex) {
            // Create an error alert
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Config Error");
            alert.setHeaderText("Error Preparing AutoPrimer3A Files");
            alert.setContentText(
                "AutoPrimer3A encountered an error when trying to prepare " +
                "required temporary files. See exception details below:\n\n" + ex.getMessage()
            );
            // Optionally log the exception details somewhere (e.g., console or log file)


            // Show the alert
            alert.showAndWait();
        }
        try {
            ap3AConfig.readGenomeXmlFile();
            //ap3AConfig.readTablesXmlFiles();
        } catch (IOException|DocumentException ex) {
            // Create an error alert
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Config Error");
            alert.setHeaderText("Error Reading AutoPrimer3A Genome Database");
            alert.setContentText(
                "AutoPrimer3A encountered an error reading local stored "
                + "genome details - see exception below.:\n\n" + ex.getMessage()
            );
            // Optionally log the exception details somewhere (e.g., console or log file)

            // Show the alert
            alert.showAndWait();
        }
        try {
            primer3ex = ap3AConfig.extractP3Executable();
            misprimeDir = ap3AConfig.extractMisprimingLibs();
            thermoConfig = ap3AConfig.extractThermoConfig();
            ap3AConfig.extractTableXml();
        } catch(IOException|ZipException ex) {
// Create an error alert
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Config Error");
            alert.setHeaderText("Error Extracting Primer3 Files");
            alert.setContentText(
                "AutoPrimer3A encountered an error while trying to extract"
                + " the primer3 executable and primer3 config files. "
                + "AutoPrimer3A will have to exit. If this reoccurs you may"
                + " need to reinstall AutoPrimer3A."
                + "See exception below.:\n\n" + ex.getMessage()
            );
            // Optionally log the exception details somewhere (e.g., console or log file)

            // Show the alert
            alert.showAndWait();
        }
        designToChoiceBox.getSelectionModel().selectFirst();
        refreshButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                refreshDatabase();
            }
        });

        refreshMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                refreshDatabase();
            }
        });

        quitMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Platform.exit();
            }
        });

        helpMenuItem.setOnAction(new EventHandler() {
            @Override
            public void handle (Event ev) {
                showHelp();
            }
        });

        aboutMenuItem.setOnAction(new EventHandler() {
            @Override
            public void handle (Event ev) {
                showAbout(ev);
            }
        });
    }

    genomeChoiceBox.getSelectionModel().selectedIndexProperty().addListener
    (new ChangeListener<Number>() {
        @Override
        public void changed (ObservableValue ov, Number value, final Number new_value) {
            if (new_value.intValue() >= 0) {
                final String id = (String) genomeChoiceBox.getItems().get(new_value.intValue());
                genomeChoiceBox.setTooltip(new Tooltip (ap3AConfig.getBuildToDescription().get(id)));
                if (autoSelectMisprime) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            selectMisprimingLibrary(id);
                        }
                    });

                }
                getBuildTables(id, false);
            }
        }
    });

    genomeChoiceBox.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {
            if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN) {
                event.consume();
            } else if (event.getCode().isLetterKey()) {
                String c = event.getText().toLowerCase();
                List items = genomeChoiceBox.getItems();
                int s = genomeChoiceBox.getSelectionModel().selectedIndexProperty().get();
                for (int i = s + 1; i < items.size(); i++) {
                    if (items.get(i).toString().toLowerCase().startsWith(c)) {
                        genomeChoiceBox.getSelectionModel().select(i);
                        return;
                    }
                }
                for (int i = 0; i < s; i++) { //wrap around
                    if (items.get(i).toString().toLowerCase().startsWith(c)) {
                        genomeChoiceBox.getSelectionModel().select(i);
                        return;
                    }
                }
            }
        }
    });
    genomeChoiceBox2.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {
            if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN) {
                event.consume();
            } else if (event.getCode().isLetterKey()) {
                String c = event.getText().toLowerCase();
                List items = genomeChoiceBox2.getItems();
                int s = genomeChoiceBox2.getSelectionModel().selectedIndexProperty().get();
                for (int i = s + 1; i < items.size(); i++) {
                    if (items.get(i).toString().toLowerCase().startsWith(c)) {
                        genomeChoiceBox2.getSelectionModel().select(i);
                        return;
                    }
                }
                for (int i = 0; i < s; i++) { //wrap around
                    if (items.get(i).toString().toLowerCase().startsWith(c)) {
                        genomeChoiceBox2.getSelectionModel().select(i);
                        return;
                    }
                }
            }
        }
    });

    private void resetPrimerSettings() {
        for (TextField f: defaultPrimer3Values.keySet()) {
            f.setText(defaultPrimer3Values.get(f));
        }
    }
    private void resetEmptyPrimerSettings() {
        for (TextField f: defaultPrimer3Values.keySet()) {
            if (f.getText().isEmpty()) {
                f.setText(defaultPrimer3Values.get(f));
            } else if (f.getText().trim().length() < 1) {
                f.setText(defaultPrimer3Values.get(f));
            }
        }
        autoSelectMisprimingLibraryCheckBox.selectedProperty().addListener(
        new ChangeListener<Boolean>() {
            @Override
            public void changed (ObservableValue ov, Boolean value, final Boolean newValue) {
                autoSelectMisprime = newValue;
                final String id = (String) genomeChoiceBox.getSelectionModel().getSelectedItem();
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        if (newValue) {
                            selectMisprimingLibrary(id);
                        } else {
                            misprimingLibraryChoiceBox.getSelectionModel().select("none");
                        }
                    }
                });

            }
        });
    }


    genomeChoiceBox.getItems().clear();
    genomeChoiceBox.getItems().addAll(new ArrayList<>(ap3AConfig.getBuildToDescription().keySet()));
    genomeChoiceBox.getSelectionModel().selectFirst();

    misprimingLibraryChoiceBox.getItems().add("none");
    for (File f: misprimeDir.listFiles()) {
    misprimingLibraryChoiceBox.getItems().add(f.getName());
    }
    misprimingLibraryChoiceBox.getSelectionModel().selectFirst();
    minDistanceTextField.addEventFilter(KeyEvent.KEY_TYPED, checkNumeric());
    minDistanceTextField2.addEventFilter(KeyEvent.KEY_TYPED, checkNumeric());
    flankingRegionsTextField.addEventFilter(KeyEvent.KEY_TYPED, checkNumeric());
    flankingRegionsTextField2.addEventFilter(KeyEvent.KEY_TYPED, checkNumeric());
    minSizeTextField.addEventFilter(KeyEvent.KEY_TYPED, checkNumeric());
    optSizeTextField.addEventFilter(KeyEvent.KEY_TYPED, checkNumeric());
    maxSizeTextField.addEventFilter(KeyEvent.KEY_TYPED, checkNumeric());
    maxDiffTextField.addEventFilter(KeyEvent.KEY_TYPED, checkNumeric());
    maxMisprimeTextField.addEventFilter(KeyEvent.KEY_TYPED, checkNumeric());
    sizeRangeTextField.addEventFilter(KeyEvent.KEY_TYPED, checkRange());
    minTmTextField.addEventFilter(KeyEvent.KEY_TYPED, checkDecimal());
    optTmTextField.addEventFilter(KeyEvent.KEY_TYPED, checkDecimal());
    maxTmTextField.addEventFilter(KeyEvent.KEY_TYPED, checkDecimal());
    splitRegionsTextField.addEventFilter(KeyEvent.KEY_TYPED, checkNumeric());
    defaultPrimer3Values.put(minSizeTextField, "18");
    defaultPrimer3Values.put(optSizeTextField, "20");
    defaultPrimer3Values.put(maxSizeTextField, "27");
    defaultPrimer3Values.put(maxDiffTextField, "10");
    defaultPrimer3Values.put(minTmTextField, "57.0");
    defaultPrimer3Values.put(optTmTextField, "59.0");
    defaultPrimer3Values.put(maxTmTextField, "62.0");
    defaultPrimer3Values.put(splitRegionsTextField, "300");
    defaultPrimer3Values.put(maxMisprimeTextField, "12");
    defaultPrimer3Values.put(sizeRangeTextField, defaultSizeRange);

    minDistanceTextField2.textProperty().bindBidirectional
    (minDistanceTextField.textProperty());
    flankingRegionsTextField2.textProperty().bindBidirectional
    (flankingRegionsTextField.textProperty());

    resetValuesButton.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {

            resetPrimerSettings();
        }
    });

    mainTabPane.getSelectionModel().selectedItemProperty().addListener(
    new ChangeListener<Tab>() {
        @Override
        public void changed (ObservableValue<? extends Tab> ov,
                             Tab ot, Tab nt) {
            if (ot.equals(primerTab)) {
                resetEmptyPrimerSettings();
            }
            if (nt.equals(genesTab)) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        genesTextField.requestFocus();
                        if (!progressLabel.textProperty().isBound()) {
                            progressLabel.setText("");
                            checkGeneTextField();
                        }
                    }
                });
            } else if (nt.equals(coordTab)) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        if (!progressLabel.textProperty().isBound()) {
                            progressLabel.setText("");
                            displayValidRegions();
                        }
                    }
                });
            }
        }
    }
    );

    sizeRangeTextField.focusedProperty().addListener(new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable,
                            Boolean oldValue, Boolean newValue ) {
            if (!newValue) {
                if (!checkSizeRange(sizeRangeTextField)) {
                    displaySizeRangeError();
                }
            }
        }
    });

    regionsTextArea.textProperty().addListener(new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observable,
                            final String oldValue, final String newValue ) {
            //newValue = newValue.trim();
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    if (progressLabel.textProperty().isBound()) {
                        return;
                    }
                    String reg = newValue.replaceAll("(?m)^\\s", "");
                    int regions = reg.split("\\n").length;
                    progressLabel.setText(regions + " lines");
                }
            });
        }
    });

    regionsTextArea.focusedProperty().addListener(new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable,
                            Boolean oldValue, Boolean newValue ) {
            if (!newValue) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        displayValidRegions();
                    }
                }
                                 );
            }
        }
    });

    genesTextField.textProperty().addListener(new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observable,
                            final String oldValue, final String newValue ) {
            //newValue = newValue.trim();
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    checkGeneTextField();
                }
            });

        }
    });

    if (ap3AConfig.getBuildToDescription().isEmpty()) {
    connectToUcsc();
    } else {
        setLoading(false);
        checkUcscGenomes();
    }
    Platform.runLater(new Runnable() {
        @Override
        public void run() {
            genesTextField.requestFocus();
        }
    }
                     );
}

private void checkGeneTextField() {
    progressLabel.setText("");
    if (!genesTextField.getText().matches(".*\\w.*")) {
        return;
    }
    String[] genes = genesTextField.getText().split("\\s+");
    if (genes.length > MAX_GENES_PER_DESIGN) {
        progressLabel.setText("Warning: " + genes.length + " genes entered,"
                              + " max per design is " + MAX_GENES_PER_DESIGN);
    } else if (genes.length > 0) {
        progressLabel.setText(genes.length + " genes entered.");
    }
}

private void displayValidRegions() {
    if (progressLabel.textProperty().isBound()) {
        return;
    }
    int validRegions = 0;
    int invalidRegions = 0;
    String[] lines = regionsTextArea.getText().replaceAll("(?m)^\\s", "").split("\\n");
    if (lines.length < 1) {
        return;
    }
    if (lines.length == 1 && lines[0].isEmpty()) {
        return;
    }

    for (String r: lines) {
        if (RegionParser.readRegion(r) == null) {
            invalidRegions++;
        } else {
            validRegions++;
        }
    }
    StringBuilder lbl = new StringBuilder(validRegions + " valid regions");
    if (invalidRegions > 0) {
        lbl.append(" (").append(invalidRegions).append(" invalid)");
    }
    progressLabel.setText(lbl.toString());
}

private void checkUcscGenomes() {
    final Task<Void> task = getGenomesTask();
    new Thread(task).start();
}

private Task<Void> getGenomesTask() {
    return new Task<Void>() {
        @Override
        protected Void call() throws DocumentException, MalformedURLException {
            System.out.println("Checking genome list.");
            buildsAndTables.connectToUcsc();
            return null;
        }
    };
}

{
    getGenomesTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
        @Override
        public void handle(WorkerStateEvent e) {
            boolean rewriteConfig = false;
            if (!ap3AConfig.getBuildToDescription().keySet().equals(
                        buildsAndTables.getBuildToDescription().keySet()) &&
                    buildsAndTables.getBuildToDescription() != null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("Repopulating Genomes");
                alert.setContentText(
                    "The available genomes have changed. AutoPrimer3A "
                    + "will now repopulate the genome menu."
                );
                alert.showAndWait();

                System.out.println("Genome list has changed - repopulating genome choice box");
                rewriteConfig = true;
                ap3AConfig.setBuildToDescription(buildsAndTables.getBuildToDescription());
                String currentSel = (String) genomeChoiceBox.getSelectionModel().getSelectedItem();
                genomeChoiceBox.getItems().clear();
                genomeChoiceBox.getItems().addAll(ap3AConfig.getBuildToDescription().keySet());

                if (genomeChoiceBox.getItems().contains(currentSel)) {
                    genomeChoiceBox.getSelectionModel().select(currentSel);
                } else {
                    genomeChoiceBox.getSelectionModel().selectFirst();
                }
            } else {
                System.out.println("Genome list is the same.");
            }

            if (buildsAndTables.getBuildToMapMaster() != null &&
                    !ap3AConfig.getBuildToMapMaster().equals(buildsAndTables.getBuildToMapMaster())) {
                ap3AConfig.setBuildToMapMaster(buildsAndTables.getBuildToMapMaster());
                System.out.println("Build-to-map master has changed - will rewrite.");
                rewriteConfig = true;
            }

            if (rewriteConfig) {
                try {
                    System.out.println("Rewriting output...");
                    ap3AConfig.writeGenomeXmlFile(getDasGenomeXmlDocument());
                } catch (IOException ex) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error Updating Genomes!");
                    alert.setContentText(
                        "AutoPrimer3A encountered an error writing updated genomes to its database file. " +
                        "See exception details below: \n\n" + ex.getMessage()
                    );
                    ex.printStackTrace();
                    alert.showAndWait();
                }
            }
        }
    });

//##################################### ADD

    getGenomesTask.setOnFailed(new EventHandler<WorkerStateEvent>() {
        @Override
        public void handle(WorkerStateEvent e) {
            progressIndicator.setProgress(0);
            System.out.println(e.getSource().getException());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Gene Search Failed!");
            alert.setContentText(
                "AutoPrimer3A encountered an error when performing "
                + "a background check of available genomes."
                + "See exception below."
            );
            alert.showAndWait();
            e.getSource().getException().printStackTrace();
        }
    });
}

{
    checkUcscTablesTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
        @Override
        public void handle(WorkerStateEvent e) {
            System.out.println("Finished getting tables for " + genome);
            Document doc = (Document) e.getSource().getValue();

            try {
                if (doc != null && !ap3AConfig.getBuildXmlDocument(genome).asXML().equals(doc.asXML())) {
                    System.out.println("Tables differ!");
                    LinkedHashSet<String> tables = ap3AConfig.readTableFile(doc);

                    if (configTablesDiffer(tables, ap3AConfig.getBuildToTables().get(genome))) {
                        System.out.println("SNP/Gene tables differ!");
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Tables Updated");
                        alert.setHeaderText("Gene/SNP Tables Updated");
                        alert.setContentText(
                            "The Gene/SNP tables for your currently selected genome have been updated."
                        );
                        alert.showAndWait();

                        String g = (String) genomeChoiceBox.getSelectionModel().getSelectedItem();
                        if (g.equals(genome)) {
                            updateChoiceBoxes(tables);
                        }
                    }

                    ap3AConfig.getBuildToTables().put(genome, tables);

                    try {
                        System.out.println("Writing new xml database file");
                        ap3AConfig.writeTableXmlFile(doc, genome);
                    } catch (IOException ex) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Error Updating Genome Information");
                        alert.setContentText(
                            "AutoPrimer3A encountered an error writing "
                            + "updated gene/SNP tables to its database "
                            + "file for genome " + genome + ". "
                            + "See exception below."
                        );
                        alert.showAndWait();
                        ex.printStackTrace();
                    } else {
                        System.out.println("Tables are the same");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }

        private void updateChoiceBoxes(LinkedHashSet<String> tables) {
            String curSnp = (String) snpsChoiceBox.getSelectionModel().getSelectedItem();
            snpsChoiceBox.getItems().clear();
            snpsChoiceBox.getItems().add("No");
            snpsChoiceBox.getItems().addAll(getSnpsFromTables(tables));

            if (snpsChoiceBox.getItems().contains(curSnp)) {
                snpsChoiceBox.getSelectionModel().select(curSnp);
            } else {
                snpsChoiceBox.getSelectionModel().selectFirst();
            }

            String curGene = (String) databaseChoiceBox.getSelectionModel().getSelectedItem();
            databaseChoiceBox.getItems().clear();
            databaseChoiceBox.getItems().addAll(getGenesFromTables(tables));

            if (databaseChoiceBox.getItems().contains(curGene)) {
                databaseChoiceBox.getSelectionModel().select(curGene);
            } else {
                databaseChoiceBox.getSelectionModel().selectFirst();
            }
        }
    });
}

checkUcscTablesTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
    @Override
    public void handle(WorkerStateEvent e) {
        System.out.println("Finished getting tables for " + genome);
        Document doc = (Document) e.getSource().getValue();
        try {
            if (doc != null &&
                    !ap3Config.getBuildXmlDocument(genome).asXML().equals(doc.asXML())) {
                // Tables differ
                System.out.println("Tables differ!");
                LinkedHashSet<String> tables = ap3Config.readTableFile(doc);
                if (configTablesDiffer(tables, ap3Config.getBuildToTables().get(genome))) {
                    // SNP/Gene tables differ
                    System.out.println("SNP/Gene tables differ!");

                    // Inform user about updated tables
                    Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
                    infoAlert.setTitle("Tables Updated");
                    infoAlert.setHeaderText("Gene/SNP Tables Updated");
                    infoAlert.setContentText("The Gene/SNP tables for your currently selected genome have been updated.");
                    infoAlert.showAndWait();

                    // Update choice boxes if genome is still selected
                    String selectedGenome = genomeChoiceBox.getSelectionModel().getSelectedItem();
                    if (genome.equals(selectedGenome)) {
                        updateChoiceBoxes(tables);
                    }
                }

                // Update configuration silently
                ap3Config.getBuildToTables().put(genome, tables);
                try {
                    System.out.println("Writing new XML database file");
                    ap3Config.writeTableXmlFile(doc, genome);
                } catch (IOException ex) {
                    handleAlertException("Error Updating Genome Information",
                                         "Failed to write updated gene/SNP tables to the database file for genome " + genome + ".",
                                         ex);
                }
            } else {
                System.out.println("Tables are the same");
            }
        } catch (DocumentException | IOException ex) {
            handleAlertException("Error Updating Genome Information",
                                 "An error occurred while updating gene/SNP tables for genome " + genome + ".",
                                 ex);
        }
    }
});

// Handle task failure
checkUcscTablesTask.setOnFailed(new EventHandler<WorkerStateEvent>() {
    @Override
    public void handle(WorkerStateEvent e) {
        Throwable exception = e.getSource().getException();
        System.out.println(exception);

        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle("Error");
        errorAlert.setHeaderText("Gene Search Failed!");
        errorAlert.setContentText("AutoPrimer3 encountered an error while performing a background check of available gene/SNP tables for genome " + genome + ".");
        errorAlert.showAndWait();
    }
});

// Start the task
new Thread(checkUcscTablesTask).start();


private void handleAlertException(String title, String header, Exception ex) {
    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
    errorAlert.setTitle(title);
    errorAlert.setHeaderText(header);
    errorAlert.setContentText(ex.getMessage());
    errorAlert.showAndWait();

    // Print the stack trace to the console for debugging
    ex.printStackTrace();
}


private void updateChoiceBoxes(LinkedHashSet<String> tables) {
    // Update SNPs choice box
    String currentSnp = snpsChoiceBox.getSelectionModel().getSelectedItem();
    snpsChoiceBox.getItems().clear();
    snpsChoiceBox.getItems().add("No");
    snpsChoiceBox.getItems().addAll(getSnpsFromTables(tables));
    if (snpsChoiceBox.getItems().contains(currentSnp)) {
        snpsChoiceBox.getSelectionModel().select(currentSnp);
    } else {
        snpsChoiceBox.getSelectionModel().selectFirst();
    }

    // Update genes choice box
    String currentGene = databaseChoiceBox.getSelectionModel().getSelectedItem();
    databaseChoiceBox.getItems().clear();
    databaseChoiceBox.getItems().addAll(getGenesFromTables(tables));
    if (databaseChoiceBox.getItems().contains(currentGene)) {
        databaseChoiceBox.getSelectionModel().select(currentGene);
    } else {
        databaseChoiceBox.getSelectionModel().selectFirst();
    }
}


//##########################################  ENO OF SUGGESTIONS


private void updateChoiceBoxes(LinkedHashSet tables) {
    String curSnp = (String) snpsChoiceBox.getSelectionModel().getSelectedItem();
    snpsChoiceBox.getItems().clear();
    snpsChoiceBox.getItems().add("No");
    snpsChoiceBox.getItems().addAll(getSnpsFromTables(tables));
    if (snpsChoiceBox.getItems().contains(curSnp)) {
        snpsChoiceBox.getSelectionModel().select(curSnp);
    } else {
        snpsChoiceBox.getSelectionModel().selectFirst();
    }

    String curGene = (String) databaseChoiceBox.getSelectionModel().getSelectedItem();
    databaseChoiceBox.getItems().clear();
    databaseChoiceBox.getItems().addAll(getGenesFromTables(tables));
    if (databaseChoiceBox.getItems().contains(curGene)) {
        databaseChoiceBox.getSelectionModel().select(curGene);
    } else {
        databaseChoiceBox.getSelectionModel().selectFirst();
    }
});

/*even if available genes and snps are the same we just
     change and rewrite rewrite the config file silently
     to prevent this happening until tables change again
                         */
ap3Config.getBuildToTables().put(genome, tables);
try {
    System.out.println("Writing new xml database file");
    ap3Config.writeTableXmlFile(doc, genome);
} catch (IOException ex) {
    //ex.printStackTrace();
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Error");
    alert.setHeaderText("Error Updating Genome Information!");
    alert.setContentText("AutoPrimer3A encountered an error writing "
                         + "updated gene/SNP tables to the database "
                         + "file for genome " + id + ". "
                         + "See exception below.");
    alert.showAndWait();
}

else {
    System.out.println("Tables are the same");
} catch(DocumentException|IOException ex) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Error");
    alert.setHeaderText("Error Reading Updating Genome Information!");
    alert.setContentText("AutoPrimer3A encountered an error reading "
                         + "updated gene/SNP tables from UCSC for genome "
                         + id + ". See exception below.");
    alert.showAndWait();
}

checkUcscTablesTask.setOnFailed(new EventHandler<WorkerStateEvent>() {
    @Override
    public void handle(WorkerStateEvent e) {
        System.out.println(e.getSource().getException());
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Gene Search Failed!");
        alert.setContentText("AutoPrimer3 encountered an error when performing"
                             + " a background check of available gene/SNP tables"
                             + " for genome " + genome + ". See exception below.");

        alert.showAndWait();
    }
});

new Thread(checkUcscTablesTask).start();


//only checks snp and gene tables in lists to see if they are the same
private boolean configTablesDiffer(LinkedHashSet<String> tables,
        LinkedHashSet<String> configTables) {
    ArrayList<String> tableComp = new ArrayList<>();
    ArrayList<String> configComp = new ArrayList<>();
    for (String t: tables) {
        if (matchesGeneTable(t) || matchesSnpTable(t)) {
            tableComp.add(t);
        }
    }
    for (String t: configTables) {
        if (matchesGeneTable(t) || matchesSnpTable(t)) {
            configComp.add(t);
        }
    }
    return (! (tableComp.containsAll(configComp) && configComp.containsAll(tableComp)));
}

private LinkedHashSet<String> getGenesFromTables(LinkedHashSet<String> tables) {
    LinkedHashSet<String> genes = new LinkedHashSet<>();
    for (String t: tables) {
        if (matchesGeneTable(t)) {
            genes.add(t);
        }
    }
    return genes;
}

// Helper method to extract SNPs from tables
private LinkedHashSet<String> getSnpsFromTables(LinkedHashSet<String> tables) {
    LinkedHashSet<String> snps = new LinkedHashSet<>();
    for (String t : tables) {
        if (matchesSnpTable(t)) {
            snps.add(t);
        }
    }
    return snps;
}

// Helper methods to check if a table matches a gene
private boolean matchesGeneTable(String t) {
    return t.equals("refGene") || t.equals("knownGene") || t.equals("ensGene") || t.equals("xenoRefGene");
}

private boolean matchesGeneTable(String t) {
    return (t.equals("refGene") || t.equals("knownGene") ||
            t.equals("ensGene") || t.equals("xenoRefGene"));
}

private boolean matchesSnpTable(String t) {
    return t.matches("^snp\\d+(\\w+)*");
}

private void connectToUcsc() {
    progressIndicator.setProgress(-1);
    final Task<LinkedHashMap<String, String>> getBuildsTask =
    new Task<LinkedHashMap<String, String>>() {
        @Override
        protected LinkedHashMap<String, String> call()
        throws DocumentException, MalformedURLException {
            buildsAndTables.connectToUcsc();
            return buildsAndTables.getBuildToDescription();
        }
    };
    getBuildsTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
        @Override
        public void handle (WorkerStateEvent e) {
            LinkedHashMap<String, String> buildIds =
                (LinkedHashMap<String, String>) e.getSource().getValue();
            genomeChoiceBox.getItems().clear();
            genomeChoiceBox.getItems().addAll(buildIds.keySet());
            genomeChoiceBox.getSelectionModel().selectFirst();
            ap3Config.setBuildToDescription(buildIds);
            ap3Config.setBuildToMapMaster(buildsAndTables.getBuildToMapMaster());
            try {
                System.out.println("Writing new xml database file");
                ap3Config.writeGenomeXmlFile();
            } catch (DocumentException|IOException ex) {
                //ex.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error Writing Genome Data!");
                alert.setContentText("AutoPrimer3 encountered an error writing "
                                     + "updated genomes to its database file."
                                     + "See exception below.");
                alert.showAndWait();

            }
            setLoading(false);
        }
    });
    getBuildsTask.setOnFailed(new EventHandler<WorkerStateEvent>() {
        @Override
        public void handle(WorkerStateEvent e) {
            Throwable exception = e.getSource().getException();
            if (exception != null) {
                exception.printStackTrace();
            }
            showErrorAlert("Error Loading Builds", "An error occurred while loading UCSC builds.", exception);
        }
    });
}
public void handle(WorkerStateEvent e) {
    progressIndicator.setProgress(0);
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Error");
    alert.setHeaderText("Error Retrieving Genome Information from UCSC");
    alert.setContentText("AutoPrimer3A encountered an error connecting to the "
                         + "UCSC server to retrieve available genomes. "
                         + "See exception below. Use the Refresh/Reconnect "
                         + "button to try again.");
    alert.showAndWait();

    setLoading(false);
    setCanRun(false);

    getBuildsTask.setOnCancelled(new EventHandler<WorkerStateEvent>() {
        @Override
        public void handle(WorkerStateEvent e) {
            progressIndicator.setProgress(0);
            progressLabel.setText("UCSC connection cancelled.");
            setLoading(false);
            setCanRun(false);
        }
    });

    cancelButton.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            getBuildsTask.cancel();
        }
    });

    progressLabel.setText("Connecting to UCSC...");
    new Thread(getBuildsTask).start();

    getTablesTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
        @Override
        public void handle(WorkerStateEvent e) {
            Document doc = (Document) e.getSource().getValue();
            try {
                LinkedHashSet<String> tables = ap3AConfig.readTableFile(doc);
                ap3AConfig.getBuildToTables().put(id, tables);

                if (!doc.asXML().equals(ap3AConfig.getBuildXmlDocument(id).asXML())) {
                    try {
                        ap3AConfig.writeTableXmlFile(doc, id);
                    } catch (IOException ex) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Error Updating Genome Information!");
                        alert.setContentText("AutoPrimer3A encountered an error writing "
                                             + "updated gene/SNP tables to the database "
                                             + "file for genome " + id + ". "
                                             + "See exception below.");
                        alert.showAndWait();
                    }
                }

                setTables(tables);
                progressIndicator.setProgress(0);
                progressLabel.setText("");
                setLoading(false);
            } catch (IOException | DocumentException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error Reading Updating Genome Information!");
                alert.setContentText("AutoPrimer3A encountered an error reading "
                                     + "updated gene/SNP tables from UCSC for genome "
                                     + id + ". See exception below.");
                alert.showAndWait();
            }
        }
    });

    getTablesTask.setOnFailed(new EventHandler<WorkerStateEvent>() {
        @Override
        public void handle(WorkerStateEvent e) {
            progressLabel.setText("Get Tables Task Failed");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error Retrieving Gene/SNP Tables from UCSC");
            alert.setContentText("AutoPrimer3A encountered an error connecting to the "
                                 + "UCSC server to retrieve available gene/SNP tables. "
                                 + "See exception below. Use the Refresh/Reconnect "
                                 + "button to try again or select a different genome.");
            alert.showAndWait();
        }
    });

    getTablesTask.setOnCancelled(new EventHandler<WorkerStateEvent>() {
        @Override
        public void handle(WorkerStateEvent e) {
            progressLabel.setText("Get Tables Task Cancelled");
            setLoading(false);
            setCanRun(false);
        }
    });

    cancelButton.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            getTablesTask.cancel();
        }
    });

    progressIndicator.setProgress(-1);
    new Thread(getTablesTask).start();
}
private void displaySizeRangeError() {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Invalid Size Range");
    alert.setHeaderText("Invalid Primer Product Size Range values");
    alert.setContentText("Primer Product Size Range field must be in the format "
                         + "'100-200 200-400' etc.");
    alert.showAndWait();
}

EventHandler<KeyEvent> checkNumeric() {
    return new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent ke) {
            if (!ke.getCharacter().matches("\\d")) {
                ke.consume();
            }
        }
    };
}
###################### beginning of question to chat GPT about too many open braces
public void handle (WorkerStateEvent e) {
    progressIndicator.setProgress(0);
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Error");
    alert.setHeaderText("Error Retrieving Genome Information from UCSC");
    alert.setContentText("AutoPrimer3A encountered an error connecting to the "
                         + "UCSC server to retrieve available genomes. "
                         + "See exception below. Use the Refresh/Reconnect "
                         + "button to try again.");
    alert.showAndWait();

    setLoading(false);
    setCanRun(false);
}
{
    getBuildsTask.setOnCancelled(new EventHandler<WorkerStateEvent>() {
        @Override
        public void handle (WorkerStateEvent e) {
            progressIndicator.setProgress(0);
            progressLabel.setText("UCSC connection cancelled.");
            setLoading(false);
            setCanRun(false);
        }
    });
    cancelButton.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            getBuildsTask.cancel();

        }
    });
    progressLabel.setText("Connecting to UCSC...");
    new Thread(getBuildsTask).start();
};
{
    getTablesTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

        @Override

        public void handle (WorkerStateEvent e) {
            //System.out.println("getTablesTask succeeded.");
        }    Document doc = (Document) e.getSource().getValue();
        try {
            LinkedHashSet<String> tables = ap3AConfig.readTableFile(doc);
            ap3AConfig.getBuildToTables().put(id, tables);
            if (! doc.asXML().equals(ap3AConfig.getBuildXmlDocument(id).asXML())) {
                try {
                    //System.out.println("Writing output");
                    ap3AConfig.writeTableXmlFile(doc, id);
                } catch (IOException ex) {
                    //ex.printStackTrace();
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error Updating Genome Information!");
                    alert.setContentText("AutoPrimer3A encountered an error writing "
                                         + "updated gene/SNP tables to the database "
                                         + "file for genome " + id + ". "
                                         + "See exception below.");
                    alert.showAndWait();
                }
            }
            setTables(tables);
            progressIndicator.setProgress(0);
            progressLabel.setText("");
            setLoading(false);
        } catch (IOException|DocumentException ex) {
            //ex.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error Reading Updating Genome Information!");
            alert.setContentText("AutoPrimer3A encountered an error reading "
                                 + "updated gene/SNP tables from UCSC for genome "
                                 + id + ". See exception below.");
            alert.showAndWait();
            getTablesTask.setOnFailed(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle (WorkerStateEvent e) {
                    progressLabel.setText("Get Tables Task Failed");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error Retrieving Gene/SNP Tables from UCSC");
                    alert.setContentText("AutoPrimer3A encountered an error connecting to the "
                                         + "UCSC server to retrieve available gene/SNP tables. "
                                         + "See exception below. Use the Refresh/Reconnect "
                                         + "button to try again or select a different genome.");
                    alert.showAndWait();
                }
                {
                    setLoading(false);
                    setCanRun(false);
                }
            }
        }
        getTablesTask.setOnCancelled(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle (WorkerStateEvent e) {
                progressLabel.setText("Get Tables Task Cancelled");
                //System.out.println("getTablesTask cancelled.");
                setLoading(false);
                setCanRun(false);
            }
        });
    }
}
cancelButton.setOnAction(new EventHandler<ActionEvent>() {
    @Override
    public void handle(ActionEvent actionEvent) {
        getTablesTask.cancel();
    }
    progressIndicator.setProgress(-1);
    new Thread(getTablesTask).start();
    private void displaySizeRangeError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Size Range");
        alert.setHeaderText("Invalid Primer Product Size Range values");
        alert.setContentText("Primer Product Size Range field must be in the format "
                             + "'100-200 200-400' etc.");
        alert.showAndWait();
    }
    EventHandler<KeyEvent> checkNumeric() {
        return new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (!ke.getCharacter().matches("\\d")) {
                    ke.consume();
                }
            }
        };
    }


//# end of question to chat gpt about too many open curly braces
    EventHandler<KeyEvent> checkDecimal() {
        return new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (!ke.getCharacter().matches("[\\d.]")) {
                    ke.consume();
                }
            }
        };
    }

    EventHandler<KeyEvent> checkRange() {
        return new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (!ke.getCharacter().matches("[\\d-\\s]")) {
                    ke.consume();
                }
            }
        };
    }

    private boolean checkSizeRange(TextField field) {
        List<String> split = Arrays.asList(field.getText().split("\\s+"));
        for (String s: split) {
            if (!s.matches("\\d+-\\d+")) {
                return false;
            }
        }
        return true;
    }

    private void resetPrimerSettings() {
        for (TextField f: defaultPrimer3Values.keySet()) {
            f.setText(defaultPrimer3Values.get(f));
        }
    }

    private void resetEmptyPrimerSettings() {
        for (TextField f: defaultPrimer3Values.keySet()) {
            if (f.getText().isEmpty()) {
                f.setText(defaultPrimer3Values.get(f));
            } else if (f.getText().trim().length() < 1) {
                f.setText(defaultPrimer3Values.get(f));
            }
        }

    }


    public void refreshDatabase() {
        if (genomeChoiceBox.getSelectionModel().isEmpty()) { //implies no connection to UCSC
            connectToUcsc();
        } else { //we've got a connection to UCSC but want to refresh the database info for current build
            final String id = (String) genomeChoiceBox.getSelectionModel().getSelectedItem();
            getBuildTables(id, true);
        }
    }

    public void loadRegionsFile() {
        /*  we need to know how many regions we already have to make sure we don't go over
            MAX_LINES_PER_DESIGN
        */
        final int regions = regionsTextArea.getText().split("\\n").length;
        if (regions > MAX_LINES_PER_DESIGN) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Max Regions Already Reached");
            alert.setHeaderText("Maximum of " + MAX_LINES_PER_DESIGN
                                + " lines allowed per design");
            alert.setContentText("Cannot load file - you have already reached the maximum"
                                 + " number of lines allowed per design.  Delete some or "
                                 + "all regions if you want to load regions from a file.");
            alert.showAndWait();

        }

        progressLabel.setText("Max regions reached.");
        return;
    }
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Select input file");

    HashMap<String, ArrayList<String>> extFilters = new HashMap<>();
    extFilters.put("Any Region file", new ArrayList<>(
                       Arrays.asList("*.bed", "*.bed.gz", "*.vcf", "*vcf.gz", "*.txt", "*txt.gz",
                                     "*.BED", "*.BED.GZ", "*.VCF", "*VCF.GZ", "*.TXT", "*TXT.GZ") ) );
    extFilters.put("BED file",  new ArrayList<>(
                       Arrays.asList("*.bed", "*.bed.gz", "*.BED", "*.BED.GZ") ) );
    extFilters.put("VCF file", new ArrayList<>(
                       Arrays.asList("*.vcf", "*vcf.gz", "*.VCF", "*VCF.GZ") ) );
    extFilters.put("Text file", new ArrayList<>(
                       Arrays.asList("*.txt", "*txt.gz", "*.TXT", "*TXT.GZ") ) );
    extFilters.put("Any", new ArrayList<>(Arrays.asList("*.*") ) );
    ArrayList<String> keys = new ArrayList<>(extFilters.keySet());
    Collections.sort(keys);
    for (String ext: keys) {
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter(ext, extFilters.get(ext)));
    }
    fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
    setCanRun(false);
    final File inFile = fileChooser.showOpenDialog(mainPane.getScene().getWindow());
    if (inFile != null) {
        final Task<ArrayList<String>> loadFileTask =
        new Task<ArrayList<String>>() {
            @Override
            protected ArrayList<String> call() {
                ArrayList<String> regionStrings = new ArrayList<>();
                int totalRegions = 0;
                int valid = 0;
                int invalid = 0;
                try {
                    updateMessage("Opening " + inFile.getName());
                    BufferedReader br;
                    if (inFile.getName().endsWith(".gz")) {
                        InputStream gzipStream = new GZIPInputStream(new FileInputStream(inFile));
                        Reader decoder = new InputStreamReader(gzipStream);
                        br = new BufferedReader(decoder);
                    } else {
                        br = new BufferedReader(new FileReader(inFile));
                    }
                    String line;
                    int n = 0;

                    while ((line = br.readLine()) != null) {
                        n++;
                        if (line.startsWith("#")) { // skip header lines
                            continue;
                        }
                        updateMessage("Parsing line " + n + "...");
                        GenomicRegionSummary region = RegionParser.readRegion(line);
                        if (region != null) {
                            String r = region.getChromosome() + ":" +
                                       region.getStartPos() + "-" + region.getEndPos();
                            if (region.getName() != null && !region.getName().isEmpty()) {
                                r = r + " " + region.getName();
                            }
                            if (totalRegions > MAX_LINES_PER_DESIGN) {
                                final int lastLine = n - 1;
                                final int validRegions = valid;
                                Platform.runLater(() -> {
                                    Alert alert = new Alert(Alert.AlertType.ERROR);
                                    alert.setTitle("Error");
                                    alert.setHeaderText("Maximum of " + MAX_LINES_PER_DESIGN
                                                        + " lines allowed per design");
                                    alert.setContentText("You have reached the maximum "
                                                         + "number of lines allowed per "
                                                         + "design while processing line "
                                                         + lastLine + " of file " + inFile.getName()
                                                         + ". " + validRegions
                                                         + " valid regions added. "
                                                         + "Remaining lines will not be "
                                                         + "read.");
                                    alert.showAndWait();
                                });
                                break;
                            }
                            regionStrings.add(r);
                            valid++;
                            totalRegions++;
                        } else {
                            invalid++;
                        }
                    }
                    br.close();
                } catch (IOException ex) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error Loading Region File");
                    alert.setContentText("Could not read region file. "
                                         + "See exception below.");

                    ex.printStackTrace();

                    alert.showAndWait();
                }
                if (invalid > 0) {
                    StringBuilder msg = new StringBuilder();
                    msg.append(invalid).append(" invalid region");
                    if (invalid > 1) {
                        msg.append("s");
                    }
                    msg.append(" identified in file ").append(inFile.getName())
                    .append(" (").append(valid).append(" valid region");
                    if (valid != 1) {
                        msg.append("s");
                    }
                    msg.append(" identified).");
                    final String messageString = msg.toString();
                    Platform.runLater(() -> {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Invalid Regions");
                        alert.setHeaderText("Invalid Regions Identified");
                        alert.setContentText(messageString);
                        alert.showAndWait();
                    });
                }
                return regionStrings;
            }
        };
        new Thread(loadFileTask).start();
    }


    loadFileTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
        @Override
        public void handle (WorkerStateEvent e) {
            setRunning(false);
            progressIndicator.progressProperty().unbind();
            progressIndicator.progressProperty().set(0);
            progressLabel.textProperty().unbind();
            ArrayList<String> loadedRegions = (ArrayList<String>)
                                              e.getSource().getValue();
            int n = 0;
            for (String r: loadedRegions) {
                n++;
                regionsTextArea.appendText(r + "\n");
            }
            progressLabel.setText("Added " + n + " regions from "
                                  + inFile.getName() + ".");
        }
    });
    loadFileTask.setOnCancelled(new EventHandler<WorkerStateEvent>() {
        @Override
        public void handle (WorkerStateEvent e) {
            setRunning(false);
            progressLabel.textProperty().unbind();
            progressLabel.setText("Loading cancelled");
            progressIndicator.progressProperty().unbind();
            progressIndicator.progressProperty().set(0);
        }
    });
    loadFileTask.setOnFailed(new EventHandler<WorkerStateEvent>() {
        @Override
        public void handle (WorkerStateEvent e) {
            setRunning(false);
            progressLabel.textProperty().unbind();
            progressLabel.setText("Loading failed!");
            progressIndicator.progressProperty().unbind();
            progressIndicator.progressProperty().set(0);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error Loading Region File");
            alert.setContentText("Could not read region file. "
                                 + "See exception below.");
            alert.showAndWait();
        }

    });
    cancelButton.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            loadFileTask.cancel();
        }
    });
    progressIndicator.progressProperty().unbind();
    progressLabel.textProperty().unbind();
    progressIndicator.progressProperty().bind(loadFileTask.progressProperty());
    progressLabel.textProperty().bind(loadFileTask.messageProperty());
    setRunning(true);
    new Thread(loadFileTask).start();


    public void clearRegions() {
        regionsTextArea.clear();
        if (! progressLabel.textProperty().isBound()) {
            progressLabel.setText("");
        }
    }


    private ArrayList<GenomicRegionSummary> getRegionsForDesign(String regionsInput) {
        ArrayList<GenomicRegionSummary> regions = parseRegions(regionsInput);
        if (regions.isEmpty()) {
            showAlert("No Regions", "No valid regions found.", "No valid regions were found in your input.");
            return null;
        }

        List<String> invalidRegions = findInvalidRegions(regionsInput);
        if (!invalidRegions.isEmpty() && !handleInvalidRegions(invalidRegions)) {
            return null;
        }

        ArrayList<GenomicRegionSummary> tooLongRegions = new ArrayList<>();
        ArrayList<GenomicRegionSummary> validRegions = filterLongRegions(regions, tooLongRegions);

        if (!tooLongRegions.isEmpty() && !handleTooLongRegions(tooLongRegions)) {
            return null;
        }

        return validRegions;
    }

    private ArrayList<GenomicRegionSummary> parseRegions(String regionsInput) {
        ArrayList<GenomicRegionSummary> regions = new ArrayList<>();
        List<String> tempRegions = Arrays.asList(regionsInput.replaceAll("(?m)^\\s", "").split("\\n"));
        int n = 1;

        for (String r : tempRegions) {
            if (!r.matches(".*\\w.*")) {
                continue;
            }

            GenomicRegionSummary region = RegionParser.readRegion(r);
            if (region != null) {
                if (region.getName() == null || region.getName().isEmpty() || !useRegionNamesCheckBox.isSelected()) {
                    region.setName("Region_" + n++);
                }
                region.setId(region.getChromosome() + ":" + region.getStartPos() + "-" + region.getEndPos());
                regions.add(region);
            }
        }
        return regions;
    }

    private List<String> findInvalidRegions(String regionsInput) {
        List<String> invalidRegions = new ArrayList<>();
        List<String> tempRegions = Arrays.asList(regionsInput.replaceAll("(?m)^\\s", "").split("\\n"));

        for (String r : tempRegions) {
            if (!r.matches(".*\\w.*")) {
                continue;
            }

            if (RegionParser.readRegion(r) == null) {
                invalidRegions.add(r);
            }
        }

        return invalidRegions;
    }

    private boolean handleInvalidRegions(List<String> invalidRegions) {
        StringBuilder mh = new StringBuilder("Found " + invalidRegions.size() + " Invalid Region");
        if (invalidRegions.size() > 1) {
            mh.append("s");
        }
        mh.append(" in User Input");

        StringBuilder msg = new StringBuilder("Invalid regions found - continue designing without these regions?\n");
        if (invalidRegions.size() <= 10) {
            msg.append(String.join("\n", invalidRegions));
        } else {
            msg.append(String.join("\n", invalidRegions.subList(0, 10)));
            msg.append("\n...and ").append((invalidRegions.size() - 9)).append(" more.");
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Invalid Regions");
        alert.setHeaderText(mh.toString());
        alert.setContentText(msg.toString());

        ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
        alert.getButtonTypes().setAll(yesButton, noButton);

        Optional<ButtonType> response = alert.showAndWait();
        return response.isPresent() && response.get() == yesButton;
    }

    private ArrayList<GenomicRegionSummary> filterLongRegions(ArrayList<GenomicRegionSummary> regions, ArrayList<GenomicRegionSummary> tooLongRegions) {
        ArrayList<GenomicRegionSummary> validRegions = new ArrayList<>();

        for (GenomicRegionSummary r : regions) {
            if (r.getLength() > MAX_REGION_SIZE) {
                tooLongRegions.add(r);
            } else {
                validRegions.add(r);
            }
        }

        return validRegions;
    }

    private boolean handleTooLongRegions(ArrayList<GenomicRegionSummary> tooLongRegions) {
        StringBuilder mh = new StringBuilder("Found " + tooLongRegions.size() + " Region");
        if (tooLongRegions.size() > 1) {
            mh.append("s");
        }
        mh.append(" Exceeding Maximum Length in User Input");

        StringBuilder msg = new StringBuilder("Maximum region size is ").append(MAX_REGION_SIZE).append(" bp. Continue designing without these regions?\n");
        if (tooLongRegions.size() <= 10) {
            for (GenomicRegionSummary l : tooLongRegions) {
                msg.append(l.getCoordinateString()).append("\n");
            }
        } else {
            for (GenomicRegionSummary l : tooLongRegions.subList(0, 10)) {
                msg.append(l.getCoordinateString()).append("\n");
            }
            msg.append("\n...and ").append((tooLongRegions.size() - 9)).append(" more.");
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Invalid Regions");
        alert.setHeaderText(mh.toString());
        alert.setContentText(msg.toString());

        ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
        alert.getButtonTypes().setAll(yesButton, noButton);

        Optional<ButtonType> response = alert.showAndWait();
        return response.isPresent() && response.get() == yesButton;
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void designPrimersToCoordinates() {
        final String regionsInput = regionsTextArea.getText();
        final int optSize = Integer.valueOf(splitRegionsTextField.getText());
        final int flanks = Integer.valueOf(flankingRegionsTextField.getText());
        final int designBuffer = Integer.valueOf(minDistanceTextField.getText());
        final String genome = (String) genomeChoiceBox.getSelectionModel().getSelectedItem();
        if (regionsInput.isEmpty() || !checkDesignParameters()) {
            return;
        }
        final ArrayList<GenomicRegionSummary> regs = getRegionsForDesign(regionsInput);
        if (regs == null) {
            return;
        }
    }

    final Task<HashMap<String, ArrayList>> designTask = new Task<>() {
        @Override
        protected HashMap<String, ArrayList> call() throws SQLException, IOException {
            ArrayList<GenomicRegionSummary> regions = regs;
            ArrayList<Primer3Result> primers = new ArrayList<>();
            ArrayList<String> designs = new ArrayList<>();
            SequenceFromDasUcsc seqFromDas = new SequenceFromDasUcsc();
            GenomicRegionSummary merger = new GenomicRegionSummary();
            merger.mergeRegionsByPosition(regions);
            regions = splitLargeRegionsMergeSmallRegions(regions, optSize, designBuffer, false);
            int p = 0;
            updateProgress(0, regions.size() * 3);
            for (GenomicRegionSummary r : regions) {
                int start = Math.max(r.getStartPos() - flanks, 0);
                int end = r.getEndPos() + flanks;
                updateMessage("Retrieving DNA for region...");
                String dna;
                try {
                    dna = seqFromDas.retrieveSequence(genome, r.getChromosome(), start, end);
                } catch (DocumentException | MalformedURLException seqex) {
                    showErrorAlert("Error retrieving DNA", "Failed to retrieve DNA for region " +
                                   r.getChromosome() + ":" + start + "-" + end, seqex);
                    return null;
                }
                updateProgress(++p, regions.size() * 3);
                ArrayList<String> excludeRegions = getExcludeRegions(dna, start, end, genome, flanks, designBuffer, r);
                updateMessage("Designing primers...");
                String seqid = r.getName() + ": " + r.getId();
                ArrayList<String> result = designPrimers(seqid, dna, "", String.join(" ", excludeRegions));

                designs.add(String.join("\n", result));
                primers.add(parsePrimer3Output(++p, r.getName(), r.getId(), r.getChromosome(), start, result));
            }
            HashMap<String, ArrayList> primerResult = new HashMap<>();
            primerResult.put("primers", primers);
            primerResult.put("design", designs);
            return primerResult;
        }
    };


// STOPPED HERE AT 10AM WEDNESDAY JANUARY 22 2025
    progressIndicator.progressProperty().unbind();
    progressIndicator.progressProperty().bind(designTask.progressProperty());
    progressLabel.textProperty().bind(designTask.messageProperty());

    designTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
        @Override
        public void handle(WorkerStateEvent e) {
            progressIndicator.progressProperty().unbind();
            progressIndicator.progressProperty().set(100);
            progressLabel.textProperty().unbind();
            setRunning(false);

            HashMap<String, ArrayList> result = (HashMap<String, ArrayList>) e.getSource().getValue();
            if (result == null) {
                return;
            }

            if (result.get("primers").isEmpty()) {
                progressLabel.setText("No primers designed.");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("No primers found!");
                alert.setHeaderText("No primers found for your targets.");
                alert.setContentText("No primer designs were attempted for your targets.");
                alert.showAndWait();
                return;
            }

            progressLabel.setText(result.get("primers").size() + " primer pairs designed.");
            FXMLLoader tableLoader;

            if (System.getProperty("os.name").equals("Mac OS X")) {
                tableLoader = new FXMLLoader(getClass().getResource("Primer3ResultViewMac.fxml"));
            } else {
                tableLoader = new FXMLLoader(getClass().getResource("Primer3ResultView.fxml"));
            }

            try {
                Pane tablePane = tableLoader.load();
                Primer3ResultViewController resultView = tableLoader.getController();
                Scene tableScene = new Scene(tablePane);
                Stage tableStage = new Stage();
                tableStage.setScene(tableScene);
                resultView.displayData(result.get("primers"), result.get("design"),
                                       (HashMap<String, String>) result.get("ref").get(0));
                tableStage.setTitle("AutoPrimer3A Results");
                tableStage.getIcons().add(new Image(this.getClass().getResourceAsStream("icon.png")));
                tableStage.initModality(Modality.NONE);
                tableStage.show();
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Design Failed!");
                alert.setContentText("AutoPrimer3A encountered an error attempting to display your results. See exception below.");
                alert.showAndWait();
            }
        }
    });

    new Thread(designTask).start();

// Handling geneSearchTask cancellation
    geneSearchTask.setOnCancelled(new EventHandler<WorkerStateEvent>() {
        @Override
        public void handle(WorkerStateEvent e) {
            setRunning(false);
            progressLabel.textProperty().unbind();
            progressLabel.setText("Design cancelled");
            progressIndicator.progressProperty().unbind();
            progressIndicator.progressProperty().set(0);
        }
    });

// Handling geneSearchTask failure
    geneSearchTask.setOnFailed(new EventHandler<WorkerStateEvent>() {
        @Override
        public void handle(WorkerStateEvent e) {
            setRunning(false);
            progressLabel.textProperty().unbind();
            progressLabel.setText("Search failed!");
            progressIndicator.progressProperty().unbind();
            progressIndicator.progressProperty().set(0);

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Gene Search Failed!");
            alert.setContentText("AutoPrimer3A encountered an error when searching for gene targets. See exception below.");
            alert.showAndWait();
        }
    });

// Cancel button action
    cancelButton.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            geneSearchTask.cancel();
        }
    });

    setRunning(true);
    progressIndicator.progressProperty().unbind();
    progressIndicator.progressProperty().bind(geneSearchTask.progressProperty());
    progressLabel.textProperty().unbind();
    progressLabel.textProperty().bind(geneSearchTask.messageProperty());
    new Thread(geneSearchTask).start();
}

// Creating reference sequence
private String createReferenceSequence(String dna, int offset, int flanks,
                                       ArrayList<GenomicRegionSummary> exons, boolean revComp) {
    StringBuilder dnaTarget = new StringBuilder();
    int prevEnd = 0;

    for (int i = 0; i < exons.size(); i++) {
        int tStart = exons.get(i).getStartPos() - offset;
        int tEnd = 1 + exons.get(i).getEndPos() - offset;
        int subsStart = tStart - flanks > 0 ? tStart - flanks : 0;
        int subsEnd = tEnd + flanks - 1 < dna.length() ? tEnd + flanks - 1 : dna.length();

        // Make sure we don't overlap end with next exon region
        if (i < exons.size() - 1) {
            subsEnd = subsEnd < exons.get(i + 1).getStartPos() - offset ? subsEnd : exons.get(i + 1).getStartPos() - offset;
        }

        // Make sure we don't overlap current flanks with previous flanks
        if (prevEnd > 0) {
            if (prevEnd > subsStart) {
                subsStart = prevEnd;
            }
        }

        prevEnd = subsEnd;
        dnaTarget.append(dna.substring(subsStart, tStart).toLowerCase());
        dnaTarget.append(dna.substring(tStart, tEnd - 1).toUpperCase());
        dnaTarget.append(dna.substring(tEnd - 1, subsEnd).toLowerCase());
    }

    if (revComp) {
        return reverseComplement(dnaTarget.toString());
    } else {
        return dnaTarget.toString();
    }
}

/*dup will always be an unedited gene name
we need to check whether we already have made an '(alt)' version
by checking in dupStorer
*/

private String checkDuplicate(String dup, HashSet<String> dupStorer) {
    String dedupped;
    if (dupStorer.contains(dup)) {
        dedupped =  dup + "(alt)";
        if (dupStorer.contains(dedupped)) {
            for (int i = 1; i < 999; i++) {
                dedupped = dup + "(alt" + i + ")";
                if (!dupStorer.contains(dedupped)) {
                    break;
                }
            }
        }
        dupStorer.add(dedupped);
        return dedupped;
    } else {
        dupStorer.add(dup);
        return dup;
    }
}

private void numberExons(ArrayList<GenomicRegionSummary> exonRegions,
                         boolean minusStrand) {
    int n = 0;
    if (minusStrand) {
        Collections.reverse(exonRegions);
    }

    for (GenomicRegionSummary e: exonRegions) {
        e.setName(e.getName() + "_ex" + (n+1));
        n++;
    }
    if (minusStrand) { //back to original order
        Collections.reverse(exonRegions);
    }
}

private ArrayList<GenomicRegionSummary> splitLargeRegionsMergeSmallRegions(
    ArrayList<GenomicRegionSummary> regions, Integer optSize,
    Integer buffer, boolean minusStrand) {
    ArrayList<GenomicRegionSummary> splitRegions = new ArrayList<>();
    for (GenomicRegionSummary r: regions) {
        if (r.getLength() > optSize) {
            //divide length by maxSize to determine no of products to make
            Double products = Math.ceil(r.getLength().doubleValue()/
                                        optSize.doubleValue());
            if (products.intValue() < 2) {
                splitRegions.add(r);
                continue;
            }
            //divide length by no. products and make each product
            Double productSize = r.getLength().doubleValue()/products;
            for (int i = 0; i < products.intValue(); i++) {
                int increment = i * productSize.intValue();
                int startPos = r.getStartPos() + increment;
                int endPos = startPos + productSize.intValue();
                endPos = endPos < r.getEndPos() ? endPos : r.getEndPos();
                String name;
                if (minusStrand) {
                    name = r.getName() + "_part" + (products.intValue() - i);
                } else {
                    name = r.getName() + "_part" + (i+1);
                }
                ArrayList<String> ids = new ArrayList<>();
                for (String id : r.getId().split("/")) {
                    if (minusStrand) {
                        ids.add(id + "_part" + (products.intValue() - i));
                    } else {
                        ids.add(id + "_part" + (i+1));
                    }
                }
                GenomicRegionSummary s = new GenomicRegionSummary(
                    r.getChromosome(), startPos, endPos,
                    r.getStartId(), r.getEndId(), String.join("/", ids), name);
                if (i == products.intValue() - 1) {
                    s.setEndPos(r.getEndPos());
                }
                splitRegions.add(s);
            }
        } else {
            splitRegions.add(r);
        }
    }
    ArrayList<GenomicRegionSummary> splitAndMergedRegions = new ArrayList<>();
    boolean smallRegion;
    do {
        smallRegion = false;
        splitAndMergedRegions.clear();
        for (int i = 0; i < splitRegions.size() - 1; i++) {
            //merge any small and close regions
            if (! splitRegions.get(i).getChromosome().equals(
                        splitRegions.get(i+1).getChromosome())) {
                splitAndMergedRegions.add(splitRegions.get(i));
                continue;
            }
            int gap = splitRegions.get(i+1).getEndPos() - splitRegions.get(i).getStartPos();
            if (gap + (2*buffer)  <= optSize) {
                smallRegion = true;
                String chrom = splitRegions.get(i).getChromosome();
                int start = splitRegions.get(i).getStartPos();
                int end = splitRegions.get(i+1).getEndPos();
                String name = mergeNames(splitRegions.get(i).getName(),
                                         splitRegions.get(i+1).getName());
                String id = mergeIds(splitRegions.get(i).getId(),
                                     splitRegions.get(i + 1).getId());
                splitAndMergedRegions.add(new GenomicRegionSummary(chrom,
                                          start, end, null, null, id, name));
                for (int j = i +2; j < splitRegions.size(); j++) {
                    splitAndMergedRegions.add(splitRegions.get(j));
                }
                splitRegions.clear();
                splitRegions.addAll(splitAndMergedRegions);
                break;
            } else {
                splitAndMergedRegions.add(splitRegions.get(i));
            }
        }
        splitAndMergedRegions.add(splitRegions.get(splitRegions.size()-1));
    } while (smallRegion);
    return splitAndMergedRegions;
}


//create a new id from two genomic regions' ids
private String mergeIds(String id1, String id2) {
    ArrayList<String> merged = new ArrayList<>();
    List<String> ids1 = Arrays.asList(id1.split("/"));
    List<String> ids2 = Arrays.asList(id2.split("/"));
    LinkedHashMap<String, String> idToEx1 = new LinkedHashMap<>();
    LinkedHashMap<String, String> idToEx2 = new LinkedHashMap<>();
    for (String d: ids1) {
        List<String> split = Arrays.asList(d.split("_ex"));
        if (split.size() > 1) {
            idToEx1.put(split.get(0), split.get(1));
        } else {
            idToEx1.put(d, d);
        }
    }
    for (String d: ids2) {
        List<String> split = Arrays.asList(d.split("_ex"));
        if (split.size() > 1) {
            idToEx2.put(split.get(0), split.get(1));
        } else {
            idToEx2.put(d, d);
        }
    }
    for (String d: idToEx1.keySet()) {
        if (idToEx2.containsKey(d)) {
            merged.add(d + "_ex" + idToEx1.get(d) + "-" + idToEx2.get(d));
        } else {
            if (d.equals(idToEx1.get(d))) { //if regions key and value will be identical
                merged.add(d);
            } else { //if exons value will be exon number
                merged.add(d + "_ex" + idToEx1.get(d));
            }
        }
    }
    for (String d: idToEx2.keySet()) {
        if (! idToEx1.containsKey(d)) {
            if (d.equals(idToEx2.get(d))) { //if regions key and value will be identical
                merged.add(d);
            } else { //if exons value will be exon number
                merged.add(d + "_ex" + idToEx2.get(d));
            }
        }
    }
    return String.join("/", merged);
}


//create a new name from two genomic regions' names
private String mergeNames(String name1, String name2) {
    String name;
    List<String> geneName1 = Arrays.asList(name1.split("_ex"));
    List<String> geneName2 = Arrays.asList(name2.split("_ex"));
    if (geneName1.size() >= 2 && geneName2.size() >= 2 &&
            geneName1.get(0).equalsIgnoreCase(geneName2.get(0))) {
        ArrayList<Integer> sizes = new ArrayList<>();
        for (int i = 1; i < geneName1.size(); i++) {
            sizes.add(Integer.valueOf(geneName1.get(i)));
        }
        for (int i = 1; i < geneName2.size(); i++) {
            sizes.add(Integer.valueOf(geneName2.get(i)));
        }
        Collections.sort(sizes);
        name = geneName1.get(0) + "_ex" + sizes.get(0) +
               "-" + sizes.get(sizes.size()-1);
    } else {
        name = name1 + "/" +  name2;
    }
    return name;
}

//get left and right primer from Primer3 output
private Primer3Result parsePrimer3Output(int index, String name, String id,
        String chrom, int baseCoordinate, ArrayList<String> output) {
    String db = (String) genomeChoiceBox.getSelectionModel().getSelectedItem();
    final Hyperlink pcrLink = new Hyperlink();
    pcrLink.setText("in-silico PCR");
    pcrLink.setTextFill(Color.BLUE);
    pcrLink.setDisable(true);
    String left = "NOT FOUND";
    String right = "NOT FOUND";
    Integer lpos = 0;
    Integer rpos = 0;
    Integer leftStart = 0;
    Integer rightStart = 0;
    String productSize = "0";
    for (String res: output) {
        if (res.startsWith("LEFT PRIMER")) {
            List<String> split = Arrays.asList(res.split(" +"));
            left = split.get(split.size() -1);
            leftStart = Integer.valueOf(split.get(2));
        } else if (res.startsWith("RIGHT PRIMER")) {
            List<String> split = Arrays.asList(res.split(" +"));
            right = split.get(split.size() -1);
            rightStart = Integer.valueOf(split.get(2));
        } else if (res.startsWith("PRODUCT SIZE:")) {
            List<String> split = Arrays.asList(res.split(" +"));
            productSize = split.get(2).replaceAll("[^\\d/]", "");
            break;
        }
    }
    Primer3Result res = new Primer3Result();
    res.setLeftPrimer(left);
    res.setRightPrimer(right);
    res.setName(name);
    res.setTranscripts(id);
    res.setIndex(index);
    res.setProductSize(Integer.valueOf(productSize));

    if (Integer.valueOf(productSize) > 0) {
        Integer wpSize = 4000 > Integer.valueOf(productSize) * 2 ?
                         4000 : Integer.valueOf(productSize) * 2;
        lpos = baseCoordinate + leftStart;
        rpos = baseCoordinate + rightStart;
        final String pcrUrl = serverUrl + "/cgi-bin/hgPcr?db=" + db +
                              "&wp_target=genome&wp_f=" + left + "&wp_r=" + right +
                              "&wp_size=" + wpSize +
                              "&wp_perfect=15&wp_good=15&boolshad.wp_flipReverse=0";
        pcrLink.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                getHostServices().showDocument(pcrUrl);
                pcrLink.setVisited(true);
                pcrLink.setUnderline(false);
            }
        });
        pcrLink.setDisable(false);
        pcrLink.setUnderline(true);
        res.setIsPcrLink(pcrLink);
        res.setIsPcrUrl(pcrUrl);
        String region = chrom + ":" + lpos + "-" + rpos;
        final String regionUrl = serverUrl + "/cgi-bin/hgTracks?db=" + db +
                                 "&position=" + region;
        final Hyperlink regionLink = new Hyperlink();
        regionLink.setText(region);
        regionLink.setTextFill(Color.BLUE);
        regionLink.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                getHostServices().showDocument(regionUrl);
                pcrLink.setVisited(true);
                pcrLink.setUnderline(false);
            }
        });
        res.setRegion(region);
        res.setRegionLink(regionLink);
    } else {
        res.setIsPcrLink(null);
        res.setIsPcrUrl(null);
        res.setRegion(null);
    }
    return res;
}


//for given parameters design primers and return result as an array of strings
private ArrayList<String> designPrimers(String name, String dna,
                                        String target, String exclude) throws IOException {
    ArrayList<String> result = new ArrayList<>();
    StringBuilder error = new StringBuilder();
    StringBuilder p3_job = new StringBuilder("SEQUENCE_TARGET=");
    p3_job.append(target).append("\n");
    p3_job.append("SEQUENCE_EXCLUDED_REGION=").append(exclude).append("\n");
    p3_job.append("SEQUENCE_ID=").append(name).append("\n");
    p3_job.append("SEQUENCE_TEMPLATE=").append(dna).append("\n");
    p3_job.append("PRIMER_TASK=pick_pcr_primers\n");
    p3_job.append("PRIMER_OPT_SIZE=").append(optSizeTextField.getText())
    .append("\n");
    p3_job.append("PRIMER_MIN_SIZE=").append(minSizeTextField.getText())
    .append("\n");
    p3_job.append("PRIMER_MAX_SIZE=").append(maxSizeTextField.getText())
    .append("\n");
    p3_job.append("PRIMER_PRODUCT_SIZE_RANGE=")
    .append(sizeRangeTextField.getText()).append("\n");
    p3_job.append("PRIMER_MIN_TM=")
    .append(minTmTextField.getText()).append("\n");
    p3_job.append("PRIMER_OPT_TM=")
    .append(optTmTextField.getText()).append("\n");
    p3_job.append("PRIMER_MAX_TM=")
    .append(maxTmTextField.getText()).append("\n");
    p3_job.append("PRIMER_PAIR_MAX_DIFF_TM=")
    .append(maxDiffTextField.getText()).append("\n");
    p3_job.append("PRIMER_THERMODYNAMIC_PARAMETERS_PATH=").
    append(thermoConfig.toString())
    .append(System.getProperty("file.separator")).append("\n");
    String misprimeLibrary = (String)
                             misprimingLibraryChoiceBox.getSelectionModel().getSelectedItem();
    if (!misprimeLibrary.isEmpty()) {
        if (! misprimeLibrary.matches("none")) {
            p3_job.append("PRIMER_MISPRIMING_LIBRARY=")
            .append(misprimeDir.toString())
            .append(System.getProperty("file.separator"))
            .append(misprimeLibrary).append("\n");
            p3_job.append("PRIMER_MAX_LIBRARY_MISPRIMING=")
            .append(maxMisprimeTextField.getText()).append("\n");

        }
    }
    p3_job.append("=");
    System.out.println(p3_job.toString());//debug only
    ArrayList<String> command = new ArrayList<>();
    command.add(primer3ex.getAbsolutePath());
    command.add("-format_output");
    Process ps = new ProcessBuilder(command).start();
    try {
        BufferedReader errorbuf = new BufferedReader
        (new InputStreamReader( ps.getErrorStream()));
        BufferedReader inbuf = new BufferedReader
        (new InputStreamReader( ps.getInputStream()));
        BufferedWriter out = new BufferedWriter(
            new OutputStreamWriter(ps.getOutputStream()));
        out.write(p3_job.toString());
        out.flush();
        out.close();

        String line;
        while ((line = inbuf.readLine()) != null) {
            //System.out.println(line);//debug only
            result.add(line);
        }
        while ((line = errorbuf.readLine()) != null) {
            //System.out.println(line);//debug only
            error.append(line);
        }
        int exit = ps.waitFor();
        //System.out.println(exit);//debug only
    } catch(InterruptedException ex) {
        ex.printStackTrace();
        /*this should have been caused by user pressing cancel
        could do with a way of checking though and throwing if
        not caused by user cancel
        */
    }
    return result;
}

/*this method gets the start coordinates of a gene based on
the values for the designToChoiceBox and the Flanking region choice box
*/

private int getGeneStart(GeneDetails g, int flanks) {
    int start;
    if (designToChoiceBox.getSelectionModel().getSelectedItem().equals("Coding regions")) {
        start = g.getCdsStart();
    } else {
        start = g.getTxStart();
    }
    start -= flanks;
    if (start > 0) {
        return start;
    } else {
        return 0;

    }
}

/*this method gets the end coordinates of a gene based on
    the values for the designToChoiceBox and the Flanking region choice box
    */

private int getGeneEnd(GeneDetails g, int flanks) {
    int end;
    if (designToChoiceBox.getSelectionModel().getSelectedItem().equals("Coding regions")) {
        end = g.getCdsEnd();
    } else {
        end = g.getTxEnd();
    }
    end += flanks;
    return end;
}

private GetGeneCoordinates getGeneSearcher() {
    if (databaseChoiceBox.getSelectionModel().getSelectedItem().equals("refGene")
            || databaseChoiceBox.getSelectionModel().getSelectedItem().equals("xenoRefGene")) {
        return new GetGeneCoordinates();
    } else if (databaseChoiceBox.getSelectionModel().getSelectedItem().equals("knownGene")) {
        return new GetUcscGeneCoordinates();
    } else if (databaseChoiceBox.getSelectionModel().getSelectedItem().equals("ensGene")) {
        return new GetEnsemblGeneCoordinates();
    } else {
        return null;
    }
}

private ArrayList<GeneDetails> getGeneDetails(String searchString,
        GetGeneCoordinates geneSearcher)
throws SQLException, GetGeneCoordinates.GetGeneExonException, GetGeneCoordinates.GetGeneFromIDException, GetGeneCoordinates.GetGeneFromSymbolException {
    ArrayList<GeneDetails> genes = new ArrayList<>();

    if (databaseChoiceBox.getSelectionModel().getSelectedItem().equals("refGene")
            || databaseChoiceBox.getSelectionModel().getSelectedItem().equals("xenoRefGene")) {
        if (searchString.matches("[NX][MR]_\\w+(.\\d)*")) {
            // Is accession, need to remove the version number if present
            searchString = searchString.replaceAll("\\.\\d$", "");
            genes.addAll(geneSearcher.getGeneFromId(searchString,
                                                    (String) genomeChoiceBox.getSelectionModel().getSelectedItem(),
                                                    (String) databaseChoiceBox.getSelectionModel().getSelectedItem()));
        } else {
            // Is gene symbol (?)
            genes.addAll(geneSearcher.getGeneFromSymbol(searchString,
                         (String) genomeChoiceBox.getSelectionModel().getSelectedItem(),
                         (String) databaseChoiceBox.getSelectionModel().getSelectedItem()));
        }
    } else if (databaseChoiceBox.getSelectionModel().getSelectedItem().equals("knownGene")) {
        if (searchString.matches("uc\\d{3}[a-z]{3}\\.\\d")) {
            // Is accession
            genes.addAll(geneSearcher.getGeneFromId(searchString,
                                                    (String) genomeChoiceBox.getSelectionModel().getSelectedItem(),
                                                    (String) databaseChoiceBox.getSelectionModel().getSelectedItem()));
        } else {
            // Is gene symbol (?)
            genes.addAll(geneSearcher.getGeneFromSymbol(searchString,
                         (String) genomeChoiceBox.getSelectionModel().getSelectedItem(),
                         (String) databaseChoiceBox.getSelectionModel().getSelectedItem()));
        }
    } else if (databaseChoiceBox.getSelectionModel().getSelectedItem().equals("ensGene")) {
        if (searchString.matches("ENS\\w*T\\d{11}.*\\d*")) {
            // Is accession
            genes.addAll(geneSearcher.getGeneFromId(searchString,
                                                    (String) genomeChoiceBox.getSelectionModel().getSelectedItem(),
                                                    (String) databaseChoiceBox.getSelectionModel().getSelectedItem()));
        } else {
            // Is gene symbol (?)
            genes.addAll(geneSearcher.getGeneFromSymbol(searchString,
                         (String) genomeChoiceBox.getSelectionModel().getSelectedItem(),
                         (String) databaseChoiceBox.getSelectionModel().getSelectedItem()));
        }
    }

    // Debugging output
    for (int i = 0; i < genes.size(); i++) {
        System.out.println(genes.get(i).getSymbol() + ":" + genes.get(i).getId()
                           + ":" + genes.get(i).getChromosome() + ":" + genes.get(i).getTxStart()
                           + "-" + genes.get(i).getTxEnd());
    }

    return genes;
}

private void setCanRun(boolean designable) {
    CANRUN = designable;
    runButton.setDisable(!CANRUN);
    cancelButton.setDisable(CANRUN);
    runButton2.setDisable(!CANRUN);
    cancelButton2.setDisable(CANRUN);
}
private void setRunning(boolean running) {
    setCanRun(!running);
    refreshButton.setDisable(running);
    refreshMenuItem.setDisable(running);
    genomeChoiceBox.setDisable(running);
    genomeChoiceBox2.setDisable(running);
    databaseChoiceBox.setDisable(running);
    snpsChoiceBox.setDisable(running);
    snpsChoiceBox2.setDisable(running);
    designToChoiceBox.setDisable(running);
    minDistanceTextField.setDisable(running);
    minDistanceTextField2.setDisable(running);
    flankingRegionsTextField.setDisable(running);
    flankingRegionsTextField2.setDisable(running);
    genesTextField.setDisable(running);
    refreshButton.setDisable(running);
    loadFileButton.setDisable(running);
}
private void setLoading(boolean loading) {
    setCanRun(!loading);
    setRunning(loading);
    if (loading) {
        progressIndicator.setProgress(-1);
    } else {
        progressIndicator.setProgress(0);
    }
}




public void showHelp() {
    try {
        File instructionsPdf = File.createTempFile("autoprimer3_instructions", ".pdf" );
        instructionsPdf.deleteOnExit();
        InputStream inputStream = this.getClass().
                                  getResourceAsStream("instructions.pdf");
        OutputStream outputStream = new FileOutputStream(instructionsPdf);
        int read = 0;
        byte[] bytes = new byte[1024];
        while ((read = inputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, read);
        }
        inputStream.close();
        outputStream.close();
        openFile(instructionsPdf);
    } catch(IOException ex) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle("Open failed");
        errorAlert.setHeaderText("Could not open instructions PDF");
        errorAlert.setContentText(ex.getMessage("Exception encountered when attempting to open "
                                                + "AutoPrimer3's help pdf. See below:"));
        errorAlert.showAndWait();
        ex.printStackTrace();
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


// Method to compare SNP and gene tables
private boolean configTablesDiffer(LinkedHashSet<String> tables, LinkedHashSet<String> configTables) {
    ArrayList<String> tableComp = new ArrayList<>();
    ArrayList<String> configComp = new ArrayList<>();

    for (String t : tables) {
        if (matchesGeneTable(t) || matchesSnpTable(t)) {
            tableComp.add(t);
        }
    }

    for (String t : configTables) {
        if (matchesGeneTable(t) || matchesSnpTable(t)) {
            configComp.add(t);
        }
    }

    return !(tableComp.containsAll(configComp) && configComp.containsAll(tableComp));
}

// Helper method to extract genes from tables
private LinkedHashSet<String> getGenesFromTables(LinkedHashSet<String> tables) {
    LinkedHashSet<String> genes = new LinkedHashSet<>();
    for (String t : tables) {
        if (matchesGeneTable(t)) {
            genes.add(t);
        }
    }
    return genes;
}

// Helper method to check if a table matches SNP
private boolean matchesSnpTable(String t) {
    return t.matches("^snp\\d+(\\w+)*");
}

// Helper method to extract SNPs from tables
private LinkedHashSet<String> getSnpsFromTables(LinkedHashSet<String> tables) {
    LinkedHashSet<String> snps = new LinkedHashSet<>();
    for (String t : tables) {
        if (matchesSnpTable(t)) {
            snps.add(t);
        }
    }
    return snps;
}

// Helper method to check if a table matches a gene
private boolean matchesGeneTable(String t) {
    return t.equals("refGene") || t.equals("knownGene") || t.equals("ensGene") || t.equals("xenoRefGene");
}

// MAIN CLASS
public static void main(String[] args) {
    launch(args);
}

// Placeholder Classes
class GetUcscBuildsAndTables {
    public Document getTableXmlDocument(String genome) throws DocumentException {
        return null; // Placeholder
    }
}

class AutoPrimer3Config {
    public void setBuildToDescription(LinkedHashMap<String, String> map) {
        // Placeholder
    }

    public void setBuildToMapMaster(Object map) {
        // Placeholder
    }

    public void writeGenomeXmlFile() throws Exception {
        // Placeholder
    }
}

