 * derived from Copyright (C) 2013,2014 David A. Parry under GPL3
    (at your option) any later version.
public class AboutDialogController implements Initializable {
    public void initialize(URL url, ResourceBundle rb) {
        closeButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                Platform.runLater(new Runnable() {
                    public void run() {
                        Stage stage = (Stage) closeButton.getScene().getWindow();
                        stage.close();
                    }
                });
            }
        });
        closeButton.setDefaultButton(true);
        closeButton.setCancelButton(true);
        final KeyCombination macCloseKeyComb = new KeyCodeCombination(KeyCode.W, KeyCombination.SHORTCUT_DOWN);
        if (System.getProperty("os.name").equals("Mac OS X")) {
            closeButton.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
                public void handle(KeyEvent ev) {
                    if (macCloseKeyComb.match(ev)) {
                        closeButton.fire();
                    }
                }
            });
        }
    }
    public void setVersion(String version) {
        if (VERSION != null) {
            versionLabel.setText("Version: " + VERSION);
        }
    }
}
