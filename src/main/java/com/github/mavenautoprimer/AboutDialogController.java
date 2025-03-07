/*
 * MavenAutoPrimer
 * derived from Copyright (C) 2013,2014 David A. Parry under GPL3
 * d.a.parry@leeds.ac.uk with revisions by Bert Gold bgold04@gmail.com
 * https://github.com/bgold04/MavenAutoPrimer
 *
    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.github.mavenautoprimer;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author David A. Parry
 */
public class AboutDialogController implements Initializable {
    @FXML
    Button closeButton;
    @FXML
    Label versionLabel;
    String VERSION;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        closeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Platform.runLater(new Runnable() {
                    @Override
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
                @Override
                public void handle(KeyEvent ev) {
                    if (macCloseKeyComb.match(ev)) {
                        closeButton.fire();
                    }
                }
            });
        }
    }
    public void setVersion(String version) {
        VERSION = version;
        if (VERSION != null) {
            versionLabel.setText("Version: " + VERSION);
        }
    }
}
