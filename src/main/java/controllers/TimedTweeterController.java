/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import mainapp.Helper;
import mainapp.TimerObject;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

/**
 *
 * @author samnishita
 */
public class TimedTweeterController implements Initializable {

    @FXML
    private Label schedule;
    @FXML
    private TextField nummintext;
    @FXML
    private Label inbetween;
    @FXML
    private Label error;
    @FXML
    private VBox tweetvbox;
    @FXML
    private TableView tableview;
    @FXML
    private Button tempbutton;
    @FXML
    private Button beginbutton;
    @FXML
    private Label status;
    @FXML
    private Label queuesize;
    @FXML
    private Button cancelbutton;

    @FXML
    private Label tweet0;
    @FXML
    private Label tweet1;
    @FXML
    private Label tweet2;
    @FXML
    private Label tweet3;
    @FXML
    private Label tweet4;
    @FXML
    private Label tweet5;
    @FXML
    private Label tweet6;
    @FXML
    private Label tweet7;
    @FXML
    private Label tweet8;
    @FXML
    private Label tweet9;

    @FXML
    private Button delete0;
    @FXML
    private Button delete1;
    @FXML
    private Button delete2;
    @FXML
    private Button delete3;
    @FXML
    private Button delete4;
    @FXML
    private Button delete5;
    @FXML
    private Button delete6;
    @FXML
    private Button delete7;
    @FXML
    private Button delete8;
    @FXML
    private Button delete9;

    private final int MAXTWEETS = 50;
    private int min;
    private static TimerObject timerobj = new TimerObject();
    private ArrayList<Label> tweetLabels = new ArrayList();
    private ArrayList<Button> deleteButtons = new ArrayList();
    private static List<String> tweetList = new LinkedList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        makeLists();
        double x = 0;
        double gap = 5;
        x = x + schedule.prefWidth(-1) + gap;
        nummintext.setLayoutX(x);
        x = x + nummintext.prefWidth(-1) + gap;
        inbetween.setLayoutX(x);
        error.setVisible(false);
        queuesize.setText(tweetList.size() + "");

        status.setVisible(false);
        cancelbutton.setDisable(true);
        cancelbutton.setOpacity(0.5);
        tweetLabels.forEach((each) -> {
            each.setVisible(false);
        });
        deleteButtons.stream().map((each) -> {
            each.setVisible(false);
            return each;
        }).forEachOrdered((Button each) -> {
            each.setOnMouseClicked((Event t) -> {
                int i = deleteButtons.indexOf(each);
                tweetList.remove(i);
                updateNextTweets();
                updateQueueSizeLabel(tweetList.size());
                if (tweetList.isEmpty()) {
                    if (beginbutton.isDisabled()) {
                        status.setText("Completed");
                        timerobj.getTimer().cancel();
                    }

                    enableBeginButton();
                    disableCancelButton();

                }
            });
        });

        this.beginbutton.setOnMouseClicked((Event t) -> {
            try {
                min = Integer.parseInt(nummintext.getText().trim());
            } catch (NumberFormatException e) {
                error.setText("Enter  a whole  number between 1 and 60");
                error.setVisible(true);
                nummintext.clear();
                status.setText("");
                return;
            } catch (IllegalArgumentException ex) {
                error.setText("Enter  a whole  number between 1 and 60");
                error.setVisible(true);
                nummintext.clear();
                status.setText("");
                return;
            }

            if (tweetList.size() > 0 && min >= 1 && min <= 60) {
                try {
                    tweetSchedule();
                    error.setVisible(false);
                    status.setVisible(true);
                    beginbutton.setDisable(true);
                    beginbutton.setOpacity(0.5);
                    status.setText("Running...");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else if (tweetList.isEmpty()) {
                error.setText("Queue is empty");
                error.setVisible(true);
            } else if (nummintext.getText().trim().length() == 0) {
                error.setText("Enter  a whole  number between 1 and 60");
                error.setVisible(true);
            } else if (min > 60 || min < 1) {
                error.setText("Enter  a whole  number between 1 and 60");
                error.setVisible(true);
            }
        });
        this.cancelbutton.setOnMouseClicked((Event t) -> {
            try {
                timerobj.getTimer().cancel();
                beginbutton.setDisable(false);
                beginbutton.setOpacity(1);
                cancelbutton.setDisable(true);
                cancelbutton.setOpacity(0.5);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    public static void addSchedTweet(String string) {
        tweetList.add(string);
    }

    public int getMaxTweets() {
        return MAXTWEETS;
    }

    public static List getList() {
        return tweetList;
    }

    public void tweetSchedule() throws TwitterException, InterruptedException {
        cancelbutton.setDisable(false);
        cancelbutton.setOpacity(1);
        try {
            Thread bgTimer = new Thread() {
                @Override
                public void run() {
                    Timer timer = timerobj.createTimer();
                    TimerTask task = new Helper();
                    timer.schedule(task, 200, min * 60000);
                    Platform.runLater(() -> {
                        queuesize.setText(tweetList.size() + "");
                    });

                }

            };
            bgTimer.start();

            enableBeginButton();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //enableBeginButton();
    }

    public void updateQueueSizeLabel(int i) {
        queuesize.setText(i + "");
    }

    public Label getQueueSizeLabel() {
        return queuesize;
    }

    public void updateRunStatus(String s) {
        status.setText(s);
    }

    public void enableBeginButton() {
        beginbutton.setDisable(false);
        beginbutton.setOpacity(1);
    }

    public void disableCancelButton() {
        cancelbutton.setDisable(true);
        cancelbutton.setOpacity(0.5);

    }

    public static Timer getTimer() {
        return timerobj.getTimer();
    }

    public Label getStatusLabel() {
        return this.status;
    }

    private void makeLists() {
        tweetLabels.add(tweet0);
        tweetLabels.add(tweet1);
        tweetLabels.add(tweet2);
        tweetLabels.add(tweet3);
        tweetLabels.add(tweet4);
        tweetLabels.add(tweet5);
        tweetLabels.add(tweet6);
        tweetLabels.add(tweet7);
        tweetLabels.add(tweet8);
        tweetLabels.add(tweet9);
        deleteButtons.add(delete0);
        deleteButtons.add(delete1);
        deleteButtons.add(delete2);
        deleteButtons.add(delete3);
        deleteButtons.add(delete4);
        deleteButtons.add(delete5);
        deleteButtons.add(delete6);
        deleteButtons.add(delete7);
        deleteButtons.add(delete8);
        deleteButtons.add(delete9);
    }

    public void updateNextTweets() {
        for (int i = 0; i < 10; i++) {
            if (i < tweetList.size()) {
                tweetLabels.get(i).setText(tweetList.get(i));
                tweetLabels.get(i).setVisible(true);
                deleteButtons.get(i).setVisible(true);
            } else {
                tweetLabels.get(i).setText("");
                tweetLabels.get(i).setVisible(false);
                deleteButtons.get(i).setVisible(false);
            }

        }
    }

}
