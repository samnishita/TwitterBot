/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import mainapp.Driver;

/**
 *
 * @author samnishita
 */
public class GourmetController implements Initializable {

    @FXML
    Tab posttab;

    @FXML
    Tab addtab;

    @FXML
    Pane pane;
//not for use now
//    @FXML
//    Tab analyticstab;
    @FXML
    Tab scheduletab;

    private PostTweetController PTC;
    private AddWordController AWC;
    private TimedTweeterController TTC;
    private FXMLLoader loader1;
    private FXMLLoader loader2;
    private FXMLLoader loader3;
    private FXMLLoader loader4;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
            try {
            loader1 = new FXMLLoader(getClass().getResource("/fxmlfiles/PostTweetFXML.fxml"));

            loader2 = new FXMLLoader(getClass().getResource("/fxmlfiles/AddWordFXML.fxml"));

            loader4 = new FXMLLoader(getClass().getResource("/fxmlfiles/TimedTweeterFXML.fxml"));

            Pane pane1 = loader1.load();
            posttab.setContent(pane1);

            Pane pane2 = loader2.load();
            addtab.setContent(pane2);

//            Pane pane3 = loader.load(getClass().getResource("/fxmlfiles/AnalyticsFXML.fxml"));
//            analyticstab.setContent(pane3);
            Pane pane4 = loader4.load();
            scheduletab.setContent(pane4);
            PTC = (PostTweetController) loader1.getController();
            AWC = loader2.getController();
            TTC = loader4.getController();

        } catch (IOException iex) {
            iex.printStackTrace();
        }
    }

    public PostTweetController getPTC() {
        return PTC;
    }

    public AddWordController getAWC() {
        return AWC;
    }

    public TimedTweeterController getTTC() {
        return TTC;
    }

}
