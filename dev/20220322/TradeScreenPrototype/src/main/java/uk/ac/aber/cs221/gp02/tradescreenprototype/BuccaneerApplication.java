package uk.ac.aber.cs221.gp02.tradescreenprototype;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;


public class BuccaneerApplication extends Application {

    public static final String PATH_TO_TEXTURES = "src/main/resources/uk/ac/aber/cs221/gp02/tradescreenprototype/textures/";

    @Override
    public void start(Stage stage) throws IOException {
        TradeTestPlayer tradeTestPlayer = new TradeTestPlayer("Alex", 1);
        Port port = new Port("Port of Cadiz");


        FXMLLoader loader = new FXMLLoader(getClass().getResource("tradeScreen.fxml"));

        // Create a controller instance
        Trade controller = new Trade(tradeTestPlayer, port);
        // Set it in the FXMLLoader
        loader.setController(controller);
        Pane Pane = loader.load();
        Scene scene = new Scene(Pane);
        stage.setTitle("Buccaneer! - TradeScreenPrototype");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}