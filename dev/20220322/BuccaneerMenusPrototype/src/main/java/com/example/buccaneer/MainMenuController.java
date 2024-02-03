package com.example.buccaneer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenuController {

    @FXML
    private void continueBtnAction(ActionEvent event) throws IOException {
        changeScene(event, "gamePane.fxml");
    }

    @FXML
    private void newGameBtnAction(ActionEvent event) throws IOException {
        changeScene(event, "nicknames.fxml");
    }

    @FXML
    private void quitBtnAction(ActionEvent event) {
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        System.out.println("Exiting program");
        window.close();
    }

    void changeScene(ActionEvent event, String fxml) throws IOException {
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource(fxml)));
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    void changeScene(ActionEvent event, Scene scene) {
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
}