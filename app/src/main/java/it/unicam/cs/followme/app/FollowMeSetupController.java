package it.unicam.cs.followme.app;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;


public class FollowMeSetupController {

    private File programFile;

    private File environmentFile;

    Alert a = new Alert(Alert.AlertType.NONE);

    @FXML
    private Button programLoaderButton;
    @FXML
    private Button environmentLoaderButton;
    @FXML
    private Button simulationStartButton;
    @FXML
    private TextField robotNumberField;
    @FXML
    private TextField timeUnitField;

    /**
     *Consente il caricamento e la memorizzazione del file di ambiente
     */
    @FXML
    public void selectProgramFile(Event e){
        this.programFile = openFileChooser("Apri programma",e);
    }

    /**
     *Consente il caricamento e la memorizzazione del file di ambiente
     */
    @FXML
    public void selectEnvironmentFile(Event e){
        this.environmentFile = openFileChooser("Apri File di Ambiente", e);
    }


    @FXML
    public void startSimulation(Event event) throws IOException{
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/FollowMeApp.fxml"));
            Parent parent = loader.load();
            FollowMeAppController viewController = loader.getController();
            viewController.simSetup(this.programFile,
                                    this.environmentFile,
                                    Integer.parseInt(this.robotNumberField.getText()),
                                    Integer.parseInt(this.timeUnitField.getText()));
            Stage stage = (Stage) (((Node)event.getSource()).getScene().getWindow());
            Scene scene = new Scene(parent,800,800);
            stage.setScene(scene);
            stage.setTitle("FOLLOW ME ROBOT SIMULATION");
            stage.setResizable(false);
            stage.show();
        }
        catch(NumberFormatException e){
            alertError("ROBOT",e.getMessage());
        }
    }

    @FXML
    private Integer getRobotsNumber(){return 1;};

    @FXML
    private Integer getTimeUnit(){return 1000;}

    private File openFileChooser(String call,Event e){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(call);
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Txt Files", "*.txt"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        return fileChooser.showOpenDialog(((Node) e.getSource()).getScene().getWindow());
    }

    /**
     * Gestisce la visualizzazione degli errori in fase di caricamento
      */
    private void alertError(String title,String message){
        a.setAlertType(Alert.AlertType.ERROR);
        a.setTitle(title);
        a.setContentText(message);
        a.show();
    }
//
//    private void checkFields(){
//        if( robotNumberField == null )  alertError("ERRORE NUMERO ROBOT", "Il numero dei robot deve essere maggiore di 0");
//
//    }
}
