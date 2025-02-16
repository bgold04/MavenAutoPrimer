 * Copyright (C) 2014 David A. Parry <d.a.parry@leeds.ac.uk>
 * (at your option) any later version.
public class Primer3ResultViewController {
    private ObservableList<Primer3Result> data = FXCollections.observableArrayList();
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
            protected Task<ObservableList<Primer3Result>> createTask() {
                return new Task<ObservableList<Primer3Result>>() {
                    protected ObservableList<Primer3Result> call() throws IOException {
                        ObservableList<Primer3Result> newData = FXCollections.observableArrayList();
                        for (Primer3Result r : data) {
                            if (r.getIsPcrUrl() != null) {
                                URL url = new URL(r.getIsPcrUrl());
                                try (BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()))) {
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
        public Primer3Result(String isPcrUrl, String isPcrLink) {
        }
        public String getIsPcrUrl() {
        }
        public void setIsPcrUrl(String isPcrUrl) {
        }
        public String getIsPcrLink() {
        }
        public void setIsPcrLink(String isPcrLink) {
        }
        public String getIsPcrResults() {
        }
        public void setIsPcrResults(String isPcrResults) {
        }
    }
    public void addTestData() {
        data.add(new Primer3Result("http://example.com/valid-url", "Valid Link"));
        data.add(new Primer3Result("http://example.com/invalid-url", "Invalid Link"));
        data.add(new Primer3Result(null, "No URL"));
    }
}
