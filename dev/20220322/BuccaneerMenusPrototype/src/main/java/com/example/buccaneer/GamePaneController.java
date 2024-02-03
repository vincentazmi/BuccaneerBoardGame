package com.example.buccaneer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;

import java.io.IOException;

public class GamePaneController extends MainMenuController{

    @FXML private Label player1Name;
    @FXML private Label player2Name;
    @FXML private Label player3Name;
    @FXML private Label player4Name;


    public void setNames(String[] names) {
        player1Name.setText(names[0]);
        player2Name.setText(names[1]);
        player3Name.setText(names[2]);
        player4Name.setText(names[3]);
    }

    @FXML
    private void cogWheelAction(ActionEvent event) {
        Node optionsMenu = ((Node)event.getSource()).getScene().lookup("#menu");
        optionsMenu.setDisable(false);
        optionsMenu.setOpacity(100);
    }

    @FXML
    private void resumeAction(ActionEvent event) {
        Node optionsMenu = ((Node)event.getSource()).getScene().lookup("#menu");
        optionsMenu.setDisable(true);
        optionsMenu.setOpacity(0);
    }

    @FXML
    private void exitAction(ActionEvent event) throws IOException {
        super.changeScene(event,"mainMenu.fxml");
    }
}