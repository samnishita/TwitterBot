/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import twitter4j.AccountTotals;
import twitter4j.Paging;
import twitter4j.RateLimitStatus;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

/**
 *
 * @author samnishita
 */
public class AnalyticsController implements Initializable,AccountTotals {

    @FXML
    Button retweetbutton;

    @FXML
    Label followercount;
    
    @FXML
    Label retweetcount;
    @FXML
    Label likecount;

    @FXML
    TableView tweettable;
    @FXML
    TableColumn tweetcolumn;
    @FXML
    TableColumn retweetcolumn;
    @FXML
    TableColumn likecolumn;

    private Twitter twitter;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.twitter = TwitterFactory.getSingleton();
        this.retweetbutton.setOnMouseClicked(new EventHandler() {
            @Override
            public void handle(Event t) {
                try {
                    getRetweets();
                } catch (TwitterException ex) {
                    ex.printStackTrace();
                }
            }
        });
        this.tweettable.setPlaceholder(new Label("No rows to display. Click the button to analyze tweets."));

        try {
            this.followercount.setText(this.twitter.showUser(this.twitter.getId()).getFollowersCount() + "");
            this.retweetcount.setText(this.twitter.getRetweetsOfMe().size()+"");
            //this.likecount.setText(this.twitter.getFavorites().size()+"");
            
            ResponseList<Status> allStatuses = this.twitter.timelines().getUserTimeline(new Paging(1,10000));
            int favs = 0;
            
            for (Status each : allStatuses){
                if (each.getFavoriteCount()!=0){
                    favs=favs+each.getFavoriteCount();
                }
                
            }
            this.likecount.setText(favs+"");
            System.out.println(allStatuses.size());
            System.out.println(favs);
            System.out.println(getFavorites());
        } catch (TwitterException ex) {
            ex.printStackTrace();
        } catch (IllegalStateException ex) {
            ex.printStackTrace();
        }
        this.tweetcolumn.setCellValueFactory(new PropertyValueFactory<>("tweet"));
        this.retweetcolumn.setCellValueFactory(new PropertyValueFactory<>("retweet"));
        this.likecolumn.setCellValueFactory(new PropertyValueFactory<>("like"));

    }

    private void getRetweets() throws TwitterException {
        ResponseList<Status> rs = this.twitter.getRetweetsOfMe();
        for (Status each : rs) {
            System.out.println(each.getText());
        }
    }
    private void GetTotals(){
        
    }

    @Override
    public int getUpdates() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getFollowers() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getFavorites() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getFriends() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public RateLimitStatus getRateLimitStatus() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getAccessLevel() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
