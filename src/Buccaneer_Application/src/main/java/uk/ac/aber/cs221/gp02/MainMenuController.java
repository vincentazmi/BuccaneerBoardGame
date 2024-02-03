package uk.ac.aber.cs221.gp02;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenuController {

    @FXML
    private void rulesBtnAction(ActionEvent event) throws IOException {
        changeScene(event, "rulesScreen.fxml");
    }

    @FXML
    private void continueBtnAction(ActionEvent event) throws IOException {
        Game.initialize();
        Game.load();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("game.fxml"));
        Scene gameScene = new Scene(loader.load());


        changeScene(event, gameScene);
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