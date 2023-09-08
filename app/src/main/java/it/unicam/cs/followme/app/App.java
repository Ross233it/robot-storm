package it.unicam.cs.followme.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class App extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/FollowMeApp.fxml")));
        primaryStage.setTitle("FollowMe Robot Simulation");
        primaryStage.setScene(new Scene(root, FollowMeAppController.WIDTH, FollowMeAppController.HEIGHT));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    public static void main(String[] args) { launch(args); }
}
