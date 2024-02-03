package com.example.buccaneer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class NicknamesController extends MainMenuController {

    @FXML
    public TextField p1Field;
    @FXML
    public TextField p2Field;
    @FXML
    public TextField p3Field;
    @FXML
    public TextField p4Field;

    @FXML
    private Button playBtn;


    @FXML
    private void playAction(ActionEvent event) throws IOException {
        // Check all names have been entered
        if (!p1Field.getText().equals("") &&
                !p2Field.getText().equals("") &&
                !p3Field.getText().equals("") &&
                !p4Field.getText().equals("")) {
            System.out.println("All names entered");
        }


        FXMLLoader loader = new FXMLLoader(getClass().getResource("gamePane.fxml"));
        Scene gameScene = new Scene(loader.load());
        GamePaneController gamePaneController = loader.getController();
        gamePaneController.setNames(new String[]{p1Field.getText(), p2Field.getText(), p3Field.getText(), p4Field.getText()});

        changeScene(event, gameScene);

    }

    @FXML
    private void backAction(ActionEvent event) throws IOException {
        super.changeScene(event, "mainMenu.fxml");
    }
}