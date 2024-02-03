package uk.ac.aber.cs221.gp02;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class Main extends Application {

    public static final int TILE_SIZE = 35;
    public static final String PATH_TO_TEXTURES = "src/main/resources/uk/ac/aber/cs221/gp02/textures/";
    public static final int WIDTH = 20;
    public static final int HEIGHT = 20;
    public static final int RIGHT_MARGIN = 16;


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
