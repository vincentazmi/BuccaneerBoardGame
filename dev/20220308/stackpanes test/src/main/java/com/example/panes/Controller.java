package com.example.panes;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller {

    private Stage stage;
    private Scene scene;

    public void switchToMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("menu.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 300, 300);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToGame(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("game.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 300, 300);
        stage.setScene(scene);
        stage.show();
    }

    public void battle(ActionEvent event) {
        Node battlePopup = ((Node)event.getSource()).getScene().lookup("#battle");
        battlePopup.setDisable(false);
        battlePopup.setOpacity(100);
    }
}