package com.example.panes;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.EventListener;

public class PanesApplication extends Application {

    Stage window;
    Scene menuScene, gameScene;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primarystage) throws IOException {
        window = primarystage;

        //Setup scene 1
        Parent menu = FXMLLoader.load(getClass().getResource("menu.fxml"));
        window.setTitle("Hello world");
        menuScene = new Scene(menu, 300, 300);

        //Setup scene 2
        Parent game = FXMLLoader.load(getClass().getResource("game.fxml"));
        window.setTitle("Hello world");
        gameScene = new Scene(game, 300, 300);

        window.setScene(menuScene);
        window.show();
    }

    public void switchScene(String scene) {
        switch (scene) {
            case "menu":
                window.setScene(menuScene);

            case "game":
                window.setScene(gameScene);

            default:
                System.err.println("Error switching scene");
                return;
        }
    }
}