package controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentLinkedQueue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import mainapp.Driver;
import mysql.Reader;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author samnishita
 */
public class PostTweetController implements Initializable {

    @FXML
    Button genbutton;
    @FXML
    Button tweetbutton;
    @FXML
    Button custombutton;

    @FXML
    TextArea tweetarea;

    @FXML
    Label posterror;

    @FXML
    TextField wordfield;
    @FXML
    TextField newword;
    @FXML
    Button pluralizer;
    @FXML
    Button replacebutton;
    @FXML
    Label wordlabel;
    @FXML
    Label newwordlabel;
    @FXML
    Button capitalizer;
    @FXML
    Button bothbutton;
    @FXML
    Button schedbutton;

    private String template1 = "Pastry Chef Attempts to Make Gourmet ";
    private String template2 = " | Gourmet Makes | Bon App\u00E9tit";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.posterror.setVisible(false);
        this.genbutton.setOnMouseClicked(new EventHandler() {
            @Override
            public void handle(Event t) {
                try {

                    String word = getWord();
                    if (word.equals("")) {
                        disableButton(tweetbutton);
                        posterror.setText("Database is Empty");
                        posterror.setVisible(true);
                    } else {
                        tweetarea.setText(template1 + word + template2);
                        posterror.setVisible(false);
                        enableButton(tweetbutton);
                        enableButton(schedbutton);
                        wordfield.setText(word);
                        enableTextField(wordfield);
                        enableButton(pluralizer);
                        enableTextField(newword);
                        wordlabel.setOpacity(1);
                        newword.clear();
                        enableButton(bothbutton);
                        enableButton(capitalizer);
                        newwordlabel.setOpacity(0.5);
                        disableButton(replacebutton);
                    }

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        this.custombutton.setOnMouseClicked(new EventHandler() {
            @Override
            public void handle(Event t) {
                try {
                    tweetarea.setText(template1 + "YOUR WORD HERE" + template2);
                    enableButton(tweetbutton);
                    enableButton(schedbutton);
                    posterror.setVisible(false);
                    wordfield.clear();
                    disableTextField(wordfield);
                    disableButton(pluralizer);
                    disableTextField(newword);
                    //newword.setEditable(false);
                    disableLabel(wordlabel);
                    newword.clear();
                    disableButton(bothbutton);
                    disableButton(capitalizer);
                    disableLabel(newwordlabel);
                    disableButton(replacebutton);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        this.tweetbutton.setOnMouseClicked(new EventHandler() {
            @Override
            public void handle(Event t) {
                try {
                    Tweet();
                    tweetbutton.setOpacity(0.5);
                    tweetbutton.setDisable(true);
                    schedbutton.setOpacity(0.5);
                    schedbutton.setDisable(true);
                    wordfield.setDisable(true);
                    wordfield.setOpacity(0.5);
                    pluralizer.setDisable(true);
                    pluralizer.setOpacity(0.5);
                    newword.setEditable(false);
                    newword.setOpacity(0.5);
                    replacebutton.setDisable(true);
                    replacebutton.setOpacity(0.5);
                    wordlabel.setOpacity(0.5);
                    newwordlabel.setOpacity(0.5);
                    bothbutton.setOpacity(0.5);
                    bothbutton.setDisable(true);
                    capitalizer.setOpacity(0.5);
                    capitalizer.setDisable(true);
                    wordfield.clear();
                    newword.clear();
                    posterror.setVisible(false);
                } catch (TwitterException ex) {
                    System.out.println("error1");
                    ex.printStackTrace();
                }
            }
        });
        this.schedbutton.setOnMouseClicked(new EventHandler() {
            @Override
            public void handle(Event t) {
                try {
//KEEP
                    //if (TimedTweeterController.getQueue().size() <= 49) {
                    if (TimedTweeterController.getList().size() <= 49) {
                        TimedTweeterController.addSchedTweet(tweetarea.getText());
                        //ConcurrentLinkedQueue clq = TimedTweeterController.getQueue();
                        List lst = TimedTweeterController.getList();
                        //Driver.getGC().getTTC().updateQueueSizeLabel(clq.size());
                        Driver.getGC().getTTC().updateQueueSizeLabel(lst.size());
                        if (lst.size()<11){
                            Driver.getGC().getTTC().updateNextTweets();
                        }
                        tweetbutton.setOpacity(0.5);
                        tweetbutton.setDisable(true);
                        schedbutton.setOpacity(0.5);
                        schedbutton.setDisable(true);
                        wordfield.setDisable(true);
                        wordfield.setOpacity(0.5);
                        pluralizer.setDisable(true);
                        pluralizer.setOpacity(0.5);
                        newword.setEditable(false);
                        newword.setOpacity(0.5);
                        replacebutton.setDisable(true);
                        replacebutton.setOpacity(0.5);
                        wordlabel.setOpacity(0.5);
                        newwordlabel.setOpacity(0.5);
                        bothbutton.setOpacity(0.5);
                        bothbutton.setDisable(true);
                        capitalizer.setOpacity(0.5);
                        capitalizer.setDisable(true);
                        wordfield.clear();
                        newword.clear();
                        posterror.setVisible(false);
                        tweetarea.clear();
                    } else {
                        posterror.setText("Queue is full!");
                        posterror.setVisible(true);
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        this.pluralizer.setOnMouseClicked(new EventHandler() {
            @Override
            public void handle(Event t) {
                String plural = pluralize(wordfield.getText());
                newword.setText(plural);
                newword.setEditable(true);
                newwordlabel.setOpacity(1);
                replacebutton.setDisable(false);
                replacebutton.setOpacity(1);
            }

        });

        this.capitalizer.setOnMouseClicked(new EventHandler() {
            @Override
            public void handle(Event t) {
                String string = capitalize(wordfield.getText());
                newword.setText(string);
                newword.setEditable(true);
                newwordlabel.setOpacity(1);
                replacebutton.setDisable(false);
                replacebutton.setOpacity(1);
            }

        });
        this.bothbutton.setOnMouseClicked(new EventHandler() {
            @Override
            public void handle(Event t) {
                String string = pluralize(wordfield.getText());
                string = capitalize(string);
                newword.setEditable(true);
                newwordlabel.setOpacity(1);
                newword.setText(string);
                replacebutton.setDisable(false);
                replacebutton.setOpacity(1);
            }
        });

        this.replacebutton.setOnMouseClicked(new EventHandler() {
            @Override
            public void handle(Event t) {
                try {
                    update(newword.getText());
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        this.tweetbutton.setOpacity(0.5);
        this.tweetbutton.setDisable(true);
        schedbutton.setOpacity(0.5);
        schedbutton.setDisable(true);
        this.wordfield.setDisable(true);
        this.wordfield.setOpacity(0.5);
        this.pluralizer.setDisable(true);
        this.pluralizer.setOpacity(0.5);
        newword.setEditable(false);
        this.newword.setOpacity(0.5);
        this.replacebutton.setDisable(true);
        this.replacebutton.setOpacity(0.5);
        this.wordlabel.setOpacity(0.5);
        this.newwordlabel.setOpacity(0.5);
        this.capitalizer.setDisable(true);
        this.capitalizer.setOpacity(0.5);
        this.bothbutton.setDisable(true);
        this.bothbutton.setOpacity(0.5);
    }

    private String getWord() throws SQLException {
        Connection conn = DriverManager.getConnection(Reader.getJDBC(), Reader.getUsername(), Reader.getPassword());
        int dbSize = Reader.getDBSize();
        Random random = new Random();
        int rand = random.ints(0, dbSize).findFirst().getAsInt();
        String sql = "SELECT * FROM misc_nouns LIMIT " + rand + ",1";
        String word = "";
        if (conn != null) {
            try {
                ResultSet rs = conn.createStatement().executeQuery(sql);
                try {
                    rs.next();
                    word = rs.getString(1);
                } catch (Exception ex) {
                    if (dbSize == 0) {
                        System.out.println("Database is Empty");
                    } else {
                        ex.printStackTrace();
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return word;
    }

    private void Tweet() throws TwitterException {
        Twitter twitter = TwitterFactory.getSingleton();
        //twitter.updateStatus(this.tweetarea.getText());
        System.out.println(this.tweetarea.getText());
        this.tweetarea.clear();
    }

    private String pluralize(String sing) {
        int length = sing.length();
        String sub1 = sing.substring(length - 1);
        String sub2 = sing.substring(length - 2, length);
        String added = "";
        String plur = "";
        if (sub1.equals("s") || sub1.equals("x") || sub1.equals("z") || sub2.equals("ss") || sub2.equals("sh") || sub2.equals("ch")) {
            added = "es";
            plur = sing + added;
        } else if (sub1.equals("f")) {
            String temp = sing.substring(0, length - 1);
            plur = temp + "ves";
        } else if (sub2.equals("fe")) {
            String temp = sing.substring(0, length - 2);
            plur = temp + "ves";
        } else if (sub1.equals("y")) {
            if ("aeiou".indexOf(sing.charAt(length - 2)) == -1) {
                String temp = sing.substring(0, length - 1);
                plur = temp + "ies";
            } else {
                plur = sing + "s";
            }
        } else if (sub1.equals("o")) {
            plur = sing + "es";
        } else {
            plur = sing + "s";
        }
        return plur;
    }

    private void update(String newWord) throws SQLException {
        tweetarea.setText(template1 + newWord + template2);
        String deletedWord = wordfield.getText();
        String addedWord = newWord;
        String sqlDelete = "DELETE FROM misc_nouns WHERE word = '" + deletedWord + "'";
        String sqlInsert = "INSERT INTO misc_nouns (word) VALUES (?) ";
        Connection conn = DriverManager.getConnection(Reader.getJDBC(), Reader.getUsername(), Reader.getPassword());
        PreparedStatement stmt = conn.prepareStatement(sqlDelete);
        stmt.execute();
        stmt = conn.prepareStatement(sqlInsert);
        stmt.setString(1, addedWord);
        try {
            stmt.execute();
            posterror.setVisible(true);
            posterror.setText("Successful add.");
        } catch (SQLIntegrityConstraintViolationException ex) {
            posterror.setVisible(true);
            posterror.setText("Word is already included in the database.");
        }

    }

    private String capitalize(String string) {
        StringBuilder sb = new StringBuilder(string);
        for (int i = 0; i < sb.length(); i++) {
            if (i == 0 || sb.charAt(i - 1) == ' ') {
                sb.setCharAt(i, Character.toUpperCase(sb.charAt(i)));
            }
        }
        return sb.toString();
    }

    public String getTemp() {
        return this.template1;
    }

    public void enableButton(Button button) {
        button.setDisable(false);
        button.setOpacity(1.00);
    }

    public void disableButton(Button button) {
        button.setDisable(true);
        button.setOpacity(0.5);
    }

    public void enableLabel(Label label) {
        label.setDisable(false);
        label.setOpacity(1.00);
    }

    public void disableLabel(Label label) {
        label.setDisable(true);
        label.setOpacity(0.5);
    }

    public void enableTextField(TextField tf) {
        tf.setDisable(false);
        tf.setOpacity(1.00);
    }

    public void disableTextField(TextField tf) {
        tf.setDisable(true);
        tf.setOpacity(0.5);
    }

    public void enableTextArea(TextArea ta) {
        ta.setDisable(false);
        ta.setOpacity(1.00);
    }

    public void disableTextArea(TextArea ta) {
        ta.setDisable(true);
        ta.setOpacity(0.5);
    }

}
