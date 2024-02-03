package uk.ac.aber.cs221.gp02;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;


public class BuccaneerApplication extends Application {
    public static final int TILE_SIZE = 35;
    public static final String PATH_TO_TEXTURES = "src/main/resources/uk/ac/aber/cs221/gp02/textures/";
    public static final int WIDTH = 20;
    public static final int HEIGHT = 20;
    public static final int RIGHT_MARGIN = 16;


    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Game.fxml"))));
        stage.setTitle("Buccaneer!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}