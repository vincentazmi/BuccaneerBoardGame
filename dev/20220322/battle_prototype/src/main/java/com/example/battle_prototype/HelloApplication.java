package com.example.battle_prototype;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    public static final String PATH_TO_TEXTURES = "src/main/resources/com/example/battle_prototype/textures/";

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("BattleScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1260, 700);

        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}