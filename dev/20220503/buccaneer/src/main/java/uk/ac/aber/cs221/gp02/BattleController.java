/**
 * ##################################################################################
 *  Controller for the battle screen
 *  Must use Battle.start() with the two players before running
 *
 * @Author xad1
 *
 * ##################################################################################
 */

package uk.ac.aber.cs221.gp02;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import static uk.ac.aber.cs221.gp02.Main.PATH_TO_TEXTURES;



public class BattleController {

    /*
    FXML Objects
     */

    @FXML
    ImageView battleP1Ship, battleP2Ship;

    @FXML
    Text wonBattleText,  p1power, p2power, pLeftText, pRightText;

    @FXML
    TilePane winningsPane;

    @FXML
    Button continueButton;






    public BattleController() {
    }

    /**
     * Starting the battle, Check whether the loser has treasures.
     * @throws FileNotFoundException
     */

    public void initialize() throws FileNotFoundException {
        if (Battle.draw) {
            wonBattleText.setText("The result was a draw!");
        } else
        {
            if (Battle.haveTreasures) {
                winningsPane.setPrefTileWidth(32);
                loadTreasureWinnings(Battle.loser.getTreasures());
                System.out.println("the player has treasures, showing treasures");

            } else {
                winningsPane.setPrefTileWidth(40);
                loadCardWinnings(Battle.wonCards);
                System.out.println("the player does not have treasures");
            }
            winningsPane.setVisible(true);
        }
        updatePlayers(Battle.winner, Battle.loser);
    }

    /**
     * Update the players on the screen
     * @param winner The winner of the battle
     * @param loser The loser of the battle
     * @throws FileNotFoundException We are loading the texture of the player from a file
     */


    public void updatePlayers(Player winner, Player loser) throws FileNotFoundException {

        p1power.setText("Attack Power: " + Battle.calcAttackPower(winner));
        p2power.setText("Attack Power: " + Battle.calcAttackPower(loser));
        /*
        Setting pictures of the boats
         */

        Image leftPlayerImage = new Image(new FileInputStream(PATH_TO_TEXTURES + "Ship" + (winner.getColor() + 1) + ".png"));
        Image rightPlayerImage = new Image(new FileInputStream(PATH_TO_TEXTURES + "Ship" + (loser.getColor() + 1) + ".png"));

        System.out.println("Setting boat 1...");
        pLeftText.setText(winner.getName());
        battleP1Ship.setImage(leftPlayerImage);
        System.out.println("Setting boat 2...");
        pRightText.setText(loser.getName());
        battleP2Ship.setImage(rightPlayerImage);

        if (!Battle.draw) {
            wonBattleText.setText(winner.getName() + " has won the battle!");
        }



    }


    /**
     * Finish the battle and switch the scene back to the game when the continue button is pressed.
     * @param actionEvent runs when the continue button is pressed.
     */
    public void continueButtonClick(ActionEvent actionEvent) throws IOException {

        Battle.finish();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("game.fxml"));
        Scene gameScene = new Scene(loader.load());

        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(gameScene);
        window.show();


    }


    /**
     * Make an image for a treasure depending on its type.
     * @param t The treasure to make an image for
     * @return An image
     * @throws FileNotFoundException We are loading textures from file
     */
    public Image makeTreasureImage(Treasure t) throws FileNotFoundException {
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
     * Make an image for a card depending on it's colour.
     * @param c The card to make an image for
     * @return An image
     * @throws FileNotFoundException We are loading textures from file
     */
    public Image makeCardImage(CrewCard c) throws FileNotFoundException {
        switch (c.getColor()) {
            case 0:
                return new Image(new FileInputStream(PATH_TO_TEXTURES + "PirateRed.png"));
            case 1:
                return new Image(new FileInputStream(PATH_TO_TEXTURES + "PirateBlack.png"));
            default:
                return new Image(new FileInputStream(PATH_TO_TEXTURES + "PirateRed.png"));
        }
    }



    /**
     * Load the treasures that the winner has won.
     * @param t An arraylist of the loser's treasure.
     * @throws FileNotFoundException We are loading textures from file.
     */
    public void loadTreasureWinnings(ArrayList<Treasure> t) throws FileNotFoundException {
        /*
        Loads the loser's treasure in to the winnings tile pane
         */

        Image texture;

        winningsPane.getChildren().clear();
        winningsPane.setPrefTileWidth(32);


        for (int i = 0; i < t.size(); i++) {
            texture = makeTreasureImage(t.get(i));
            winningsPane.getChildren().add(new ImageView(texture));
        }
    }


    /**
     * Load the cards that the winner has won.
     * @param wonCards An arraylist of the cards won.
     * @throws FileNotFoundException We are loading the textures from file
     */
    public void loadCardWinnings(ArrayList<CrewCard> wonCards) throws FileNotFoundException {
        /*
        Loads the loser's cards in to the winnings tile pane
         */

        winningsPane.getChildren().clear();
        winningsPane.setPrefTileWidth(32);

        int power;
        Image texture;
        CrewCard card;

        for (int i = 0; i < wonCards.size(); i++) {
            card = wonCards.get(i);
            power = card.getValue();
            texture = makeCardImage(wonCards.get(i));
            HBox cardLabel = new HBox();
            cardLabel.setAlignment(Pos.CENTER);
            for (int p = 0; p < power; p++) {
                cardLabel.getChildren().add(new ImageView(texture));
            }
            Region cardRegion = new Region();
            cardRegion.setMinWidth(2);
            cardRegion.setMaxWidth(2);
            cardLabel.getStyleClass().add("cardlabel");
            winningsPane.getChildren().add(cardLabel);
            winningsPane.getChildren().add(cardRegion);
        }
    }




}
