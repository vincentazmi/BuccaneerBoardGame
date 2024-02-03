/**
 * ##################################################################################
 *  Controller for the End screen
 *
 * @Author xad1, mub11
 *
 * ##################################################################################
 */

package uk.ac.aber.cs221.gp02;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import static uk.ac.aber.cs221.gp02.Main.PATH_TO_TEXTURES;

public class EndScreenController {

    @FXML
    ImageView WinnerShip;

    @FXML
    TilePane TreasuresPane;

    @FXML
    Text WinnerName;

    Player winner;

    @FXML
    Button quitbutton, newbutton;

    /**
     * Just a constructor
     */
    public EndScreenController() {
    }

    /**
     * Just an initializer
     * @throws FileNotFoundException .
     */
    public void initialize() throws FileNotFoundException {
        winner = Game.getCurrentPlayer();
        loadTreasureWinnings(winner.getHomePort().getTreasure(), winner.getHomePort().getSafeZone());
        Image WinnerImage = new Image(new FileInputStream(PATH_TO_TEXTURES + "Ship" + (winner.getColor() + 1) + ".png"));
        WinnerShip.setImage(WinnerImage);
        WinnerName.setText(winner.getName());
    }

    /**
     * It creates treasure image
     * @param t treasure
     * @return image
     * @throws FileNotFoundException .
     */
    public Image makeTreasureImage(Treasure t) throws FileNotFoundException {
        System.out.println(t);
        switch (t.getType()) {
            case DIAMOND:
                return new Image(new FileInputStream(PATH_TO_TEXTURES + "treasure" + 1 + ".png"));
            case RUBIN:
                return new Image(new FileInputStream(PATH_TO_TEXTURES + "treasure" + 2 + ".png"));
            case GOLD:
                return new Image(new FileInputStream(PATH_TO_TEXTURES + "treasure" + 3 + ".png"));
            case PEARL:
                return new Image(new FileInputStream(PATH_TO_TEXTURES + "treasure" + 4 + ".png"));
            case BARREL:
                return new Image(new FileInputStream(PATH_TO_TEXTURES + "treasure" + 5 + ".png"));
            default:
                return new Image(new FileInputStream(PATH_TO_TEXTURES + "treasure" + 1 + ".png"));
        }
    }

    /**
     * Load the treasures that the winner has won.
     * @param t An arraylist of the loser's treasure.
     * @throws FileNotFoundException We are loading textures from file.
     */
    public void loadTreasureWinnings(ArrayList<Treasure> t, ArrayList<Treasure> sz) throws FileNotFoundException {
        /*
        Loads the winner's treasure in to the winnings tile pane
         */

        Image texture;

        TreasuresPane.getChildren().clear();
        TreasuresPane.setPrefTileWidth(32);



        for (int i = 0; i < t.size(); i++) {
            texture = makeTreasureImage(t.get(i));
            TreasuresPane.getChildren().add(new ImageView(texture));
        }

        for (int i = 0; i < sz.size(); i++) {
            texture = makeTreasureImage(sz.get(i));
            TreasuresPane.getChildren().add(new ImageView(texture));
        }
    }

    public void quitButtonPress(ActionEvent actionEvent) {
        Stage window = (Stage) quitbutton.getScene().getWindow();
        window.close();

    }

    public void newButtonPress(ActionEvent actionEvent) throws IOException {
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("nicknames.fxml")));
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
}
