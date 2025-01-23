/*
 * Copyright (C) 2014 David A. Parry <d.a.parry@leeds.ac.uk>
 * Edited by Bert Gold, PhD, 2025
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

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableColumn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class controllers {

    private ObservableList<Primer3Result> data = FXCollections.observableArrayList();
    private TableColumn<Primer3Result, Hyperlink> ispcrCol;
    private TableColumn<Primer3Result, Hyperlink> ispcrResCol;

    public Primer3ResultViewController() {
        ispcrCol.setCellValueFactory(cellData -> {
            Hyperlink isPcrLink = new Hyperlink(cellData.getValue().getIsPcrLink());
            return new SimpleObjectProperty<>(isPcrLink);
        });

        ispcrResCol.setCellValueFactory(cellData -> {
            Hyperlink isPcrResults = new Hyperlink(cellData.getValue().getIsPcrResults());
            return new SimpleObjectProperty<>(isPcrResults);
        });

        ispcrResCol.setVisible(false);
    }

    private void checkIsPcrResults() throws IOException {
        Service<ObservableList<Primer3Result>> service = new Service<ObservableList<Primer3Result>>() {
            @Override
            protected Task<ObservableList<Primer3Result>> createTask() {
                return new Task<ObservableList<Primer3Result>>() {
                    @Override
                    protected ObservableList<Primer3Result> call() throws IOException {
                        ObservableList<Primer3Result> newData = FXCollections.observableArrayList();

                        for (Primer3Result r : data) {
                            if (r.getIsPcrUrl() != null) {
                                URL url = new URL(r.getIsPcrUrl());
                                try (BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()))) {
                                    String inputLine;
                                    while ((inputLine = in.readLine()) != null) {
                                        if (inputLine.contains("PcrResult=pack")) {
                                            r.setIsPcrResults("Pack Found");
                                        }
                                    }
                                } catch (IOException e) {
                                    r.setIsPcrResults("Error: " + e.getMessage());
                                }
                            } else {
                                r.setIsPcrResults("No URL Provided");
                            }
                            newData.add(r);
                        }
                        return newData;
                    }
                };
            }
        };

        service.setOnSucceeded(event -> {
            data.clear();
            data.addAll(service.getValue());
        });

        service.start();
    }

    public static class Primer3Result {
        private String isPcrUrl;
        private String isPcrLink;
        private String isPcrResults;

        public Primer3Result(String isPcrUrl, String isPcrLink) {
            this.isPcrUrl = isPcrUrl;
            this.isPcrLink = isPcrLink;
        }

        public String getIsPcrUrl() {
            return isPcrUrl;
        }

        public void setIsPcrUrl(String isPcrUrl) {
            this.isPcrUrl = isPcrUrl;
        }

        public String getIsPcrLink() {
            return isPcrLink;
        }

        public void setIsPcrLink(String isPcrLink) {
            this.isPcrLink = isPcrLink;
        }

        public String getIsPcrResults() {
            return isPcrResults;
        }

        public void setIsPcrResults(String isPcrResults) {
            this.isPcrResults = isPcrResults;
        }
    }

    public void addTestData() {
        data.add(new Primer3Result("http://example.com/valid-url", "Valid Link"));
        data.add(new Primer3Result("http://example.com/invalid-url", "Invalid Link"));
        data.add(new Primer3Result(null, "No URL"));
    }
}
