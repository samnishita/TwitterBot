/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainapp;

import controllers.TimedTweeterController;
import java.util.List;
import java.util.TimerTask;
import java.util.Timer;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

/**
 *
 * @author samnishita
 */
public class Helper extends TimerTask {

    public int i = 0;
    //KEEP
    //public ConcurrentLinkedQueue queue = TimedTweeterController.getQueue();
    public List<String> list = TimedTweeterController.getList();
    public Timer timer = TimedTweeterController.getTimer();
    public long timeprev = 0;
    public long timecur = 0;
    public long diff;

    public void run() {
        timecur = System.currentTimeMillis();
        diff = timecur - timeprev;
        System.out.println("Time Elapsed: " + diff);

        //Twitter twitter = TwitterFactory.getSingleton();
        try {
            //twitter.updateStatus(queue.poll().toString());
            timecur = System.currentTimeMillis();
            //KEEP
//            System.out.println(queue.poll().toString());
            System.out.println(list.get(0).toString());
            list.remove(0);
            timeprev = System.currentTimeMillis();
            Platform.runLater(new Runnable() {
                public void run() {
                    //KEEP
//                    Driver.getGC().getTTC().updateQueueSizeLabel(queue.size());
                    Driver.getGC().getTTC().updateQueueSizeLabel(list.size());
                    Driver.getGC().getTTC().updateNextTweets();
                }
            });

        } //catch (TwitterException ex) {
        //            System.out.println("error in tweeting");
        //        }
        catch (Exception ex) {
            System.out.println("error in tweeting");
        }
//        if (queue.size() == 0) {
//            timer.cancel();
//            Platform.runLater(new Runnable() {
//                public void run() {
//                    Driver.getGC().getTTC().getStatusLabel().setText("Complete!");
//                    Driver.getGC().getTTC().enableBeginButton();
//                }
//            });
//        }
        if (list.size() == 0) {
            timer.cancel();
            Platform.runLater(new Runnable() {
                public void run() {
                    Driver.getGC().getTTC().getStatusLabel().setText("Complete!");
                    Driver.getGC().getTTC().enableBeginButton();
                    Driver.getGC().getTTC().disableCancelButton();
                }
            });
        }
    }
}
