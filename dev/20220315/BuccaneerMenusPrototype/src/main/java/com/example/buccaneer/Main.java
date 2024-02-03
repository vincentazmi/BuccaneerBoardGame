package com.example.buccaneer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;


public class Main extends Application {

    protected Stage window;
    protected Scene mainMenu, inGameOptions, gamePane, nicknames;

    int windowWidth = 1280;
    int windowHeight = 720;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
//        createScenes();
        createWindow(stage);
    }

//    private void createScenes() throws IOException {
//        mainMenu = new Scene(FXMLLoader.load(getClass().getResource("mainMenu.fxml")),windowWidth,windowHeight);
//        gamePane = new Scene(FXMLLoader.load(getClass().getResource("gamePane.fxml")),windowWidth,windowHeight);
//        nicknames = new Scene(FXMLLoader.load(getClass().getResource("nicknames.fxml")),windowWidth,windowHeight);
//    }

    private void createWindow(Stage stage) throws IOException {
        // Initialize Window
        window = stage;
        window.setTitle("Buccaneer");
        // 'X' button sends request to custom method instead of simply closing
        window.setOnCloseRequest(e -> {
            e.consume();
            closeProgram();
        });
        // Startup scene is mainMenu
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("mainMenu.fxml")), windowWidth, windowHeight));
        window.show();
    }

    protected void closeProgram() {
        window.close();
    }


}
