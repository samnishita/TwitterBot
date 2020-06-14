/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.awt.event.ActionEvent;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import mysql.Reader;

/**
 *
 * @author samnishita
 */
public class AddWordController implements Initializable {

    @FXML
    Button addbutton;

    @FXML
    TextField textfield;

    @FXML
    ComboBox combo;

    @FXML
    Button filebutton;

    @FXML
    ComboBox comboplural;

    @FXML
    Label addworderror;

    private FileChooser fc;
    private String filename;
    private boolean plurals;
    private boolean addButClickable;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.combo.getItems().addAll("TXT File", "Word");
        this.comboplural.getItems().addAll("Yes", "No");
        this.filebutton.setOpacity(0.5);
        this.filebutton.setDisable(true);
        this.comboplural.setOpacity(0.5);
        this.comboplural.setDisable(true);
        this.addworderror.setVisible(false);
        this.addbutton.setDisable(true);
        this.addbutton.setOpacity(0.5);
        this.addButClickable = false;

        this.filebutton.setOnMouseClicked(new EventHandler() {
            @Override
            public void handle(Event t) {
                fileHandler();
            }
        });
        this.addbutton.setOnMouseClicked(new EventHandler() {
            @Override
            public void handle(Event t) {
                if (textfield.getText().toLowerCase().endsWith(".txt")) {
                    Reader.readFile(textfield.getText(), plurals, addworderror);
                    textfield.clear();
                }
                else{
                    addworderror.setText("The filename must end in .txt");
                }
            }
        });
        this.textfield.setOnKeyTyped(new EventHandler() {
            @Override
            public void handle(Event t) {
                booleanChanger();
            }
        });
    }

    public void fileHandler() {
//        Node source = (Node) combo.getParent();
//        Window theStage = source.getScene().getWindow();
        this.fc = new FileChooser();
        File file = this.fc.showOpenDialog(null);
        if (file != null) {
            this.textfield.setText(file.getAbsolutePath()
            );
            this.filename = this.textfield.getText();

            if (comboplural.getValue() != null) {
                this.addbutton.setDisable(false);
                this.addbutton.setOpacity(1);
            }
        }
    }

    public void comboChanger() {
        if (combo.getValue() != null && combo.getValue().equals("TXT File")) {
            this.filebutton.setOpacity(1.00);
            this.filebutton.setDisable(false);
            this.comboplural.setOpacity(1.00);
            this.comboplural.setDisable(false);
            this.addbutton.setDisable(true);
            this.addbutton.setOpacity(0.5);
            this.textfield.clear();
            this.textfield.setPromptText("Click below to choose a text file");
        } else if (combo.getValue() != null && combo.getValue().equals("Word")) {
            this.filebutton.setOpacity(0.5);
            this.filebutton.setDisable(true);
            this.comboplural.setOpacity(0.5);
            this.comboplural.setDisable(true);
            this.textfield.clear();
            this.addbutton.setDisable(true);
            this.addbutton.setOpacity(0.5);
            this.textfield.setPromptText("Type a word to add to the database");
        }
    }

    public void booleanChanger() {
        if (!this.textfield.getText().trim().equals("")) {
            if (comboplural.getValue().equals("Yes")) {
                plurals = true;
                this.addbutton.setDisable(false);
                this.addbutton.setOpacity(1);
            } else if (comboplural.getValue().equals("No")) {
                plurals = false;
                this.addbutton.setDisable(false);
                this.addbutton.setOpacity(1);
            } else {
                this.addworderror.setText("Please select Yes or No");
            }
        } else {
            this.addbutton.setDisable(true);
            this.addbutton.setOpacity(0.5);
            this.addworderror.setText("Please select a file");
        }

    }

    public void textFieldChanger() {
        if (this.combo.getValue().equals("Word")) {
            if (this.textfield.getText().trim().equals("")) {
                this.addbutton.setDisable(true);
                this.addbutton.setOpacity(0.5);
            } else {
                this.addbutton.setDisable(false);
                this.addbutton.setOpacity(1);
            }
        }
    }

    public void addButtonActivate() {
        if (combo.getValue() != null && combo.getValue().equals("TXT File")) {
            if (!this.textfield.getText().trim().equals("")) {

            }
        }
    }

}
