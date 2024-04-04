package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MainApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("scene.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setTitle("Team 4x100 Scheduling App");
        stage.setScene(scene);
        stage.show();
    }

    public static void launchGUI() {
        launch();
    }

}