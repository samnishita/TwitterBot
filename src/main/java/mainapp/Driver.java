/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainapp;

import controllers.GourmetController;
import java.io.IOException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author samnishita
 */
public class Driver extends Application {

    private FXMLLoader loader;
    private static GourmetController GC;

    @Override
    public void start(Stage stage) throws IOException {
        loader = new FXMLLoader(getClass().getResource("/fxmlfiles/GourmetFXML.fxml"));
        Parent root = loader.load();
        GC = loader.getController();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("IWDFCFTBATK");
        stage.setResizable(false);
        stage.show();
        stage.setOnCloseRequest((WindowEvent t) -> {
            Platform.exit();
            System.exit(0);
        });

    }

    public static void main(String[] args) {
        launch(args);
    }

    public static GourmetController getGC() {
        return GC;
    }
    
    public FXMLLoader getGCLoader(){
        return loader;
    }
}
