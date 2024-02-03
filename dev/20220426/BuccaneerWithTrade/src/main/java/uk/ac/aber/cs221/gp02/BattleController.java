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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import static uk.ac.aber.cs221.gp02.Main.PATH_TO_TEXTURES;



public class BattleController {
    int treasurecards; // so that the game knows what you clicked 0 = take treasure, 1 = take cards

    /*
    FXML Objects
     */

    @FXML
    ImageView battleP1Ship, battleP2Ship;

    @FXML
    Text wonBattleText,  p1power, p2power, pLeftText, pRightText, winningsLabel, or, couldhave;

    @FXML
    TilePane winningsPane, cardsBox,  treasureBox;

    @FXML
    Button continueButton, takeCardsButton, takeTreasureButton, backButton;






    public BattleController() {
    }


    public void initialize() throws FileNotFoundException {
        if (Battle.draw) {

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

        wonBattleText.setText(winner.getName() + " has won the battle!");

        loadWinningPreview(loser);

    }


    /**
     * Finish the battle and switch the scene back to the game when the continue button is pressed.
     * @param actionEvent runs when the continue button is pressed.
     */
    public void continueButtonClick(ActionEvent actionEvent) {

        Battle.finish(treasurecards);

        /*
        ##########################
        Switch scene back here
        ##########################
         */





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
     * Load a preview of the loser's inventory so the winner knows what they could win.
     * @param l The losing player
     * @throws FileNotFoundException We are loading textures from file.
     */
    public void loadWinningPreview(Player l) throws FileNotFoundException {
        /*
        Loads the loser's treasure and cards in to the winnings preview tile pane
         */

        Image texture;

        treasureBox.getChildren().clear();

        for (int i = 0; i < l.getTreasures().size(); i++) {
            texture = makeTreasureImage(l.getTreasures().get(i));
            treasureBox.getChildren().add(new ImageView(texture));
        }

        CrewCard card;
        String power;

        cardsBox.getChildren().clear();

        for (int i = 0; i < l.getCards().size(); i++) {
            card = l.getCards().get(i);
            power = String.valueOf(card.getValue());
            texture = makeCardImage(l.getCards().get(i));

            Label cardLabel = new Label();
            cardLabel.setText(power);
            cardLabel.setGraphic(new ImageView(texture));
            cardLabel.getStyleClass().add("buttons");
            cardLabel.getStyleClass().add("cardlabel");
            cardsBox.getChildren().add(cardLabel);
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
        winningsPane.setPrefTileWidth(55);

        String power;
        Image texture;
        CrewCard card;

        for (int i = 0; i < wonCards.size(); i++) {
            card = wonCards.get(i);
            power = String.valueOf(card.getValue());
            texture = makeCardImage(wonCards.get(i));

            Label cardLabel = new Label();
            cardLabel.setText(power);
            cardLabel.setGraphic(new ImageView(texture));
            cardLabel.getStyleClass().add("buttons");
            cardLabel.getStyleClass().add("cardlabel");
            winningsPane.getChildren().add(cardLabel);
        }
    }


    /**
     * For changing what's on screen once the player has decided what they want to take.
     */
    public void changeToWinnings() {
        cardsBox.setVisible(false);
        treasureBox.setVisible(false);
        or.setVisible(false);
        couldhave.setVisible(false);

        winningsPane.setVisible(true);
        takeCardsButton.setVisible(false);
        takeTreasureButton.setVisible(false);
        continueButton.setVisible(true);
        winningsLabel.setVisible(true);
        backButton.setVisible(true);
    }

    /**
     * For changing what's on screen if the result is a draw
     */
    public void changeToDraw() {
        cardsBox.setVisible(false);
        treasureBox.setVisible(false);
        or.setVisible(false);
        couldhave.setVisible(false);
        takeCardsButton.setVisible(false);
        takeTreasureButton.setVisible(false);
        continueButton.setVisible(true);
        wonBattleText.setText("The result was a draw!");
    }

    /**
     * Go back to the first screen when the player clicks the back button.
     * @param actionEvent runs when the back button is pressed.
     */
    public void backButtonClick(ActionEvent actionEvent) {
        cardsBox.setVisible(true);
        treasureBox.setVisible(true);
        or.setVisible(true);
        couldhave.setVisible(true);

        winningsPane.setVisible(false);
        takeCardsButton.setVisible(true);
        takeTreasureButton.setVisible(true);
        continueButton.setVisible(false);
        winningsLabel.setVisible(false);
        backButton.setVisible(false);


    }

    /**
     * Loads the card winnings when the take cards button is pressed
     * @param actionEvent runs when the take cards button is pressed
     * @throws FileNotFoundException We are loading the card textures from file
     */
    public void cardsButtonClick(ActionEvent actionEvent) throws FileNotFoundException {
        winningsPane.setPrefTileWidth(40);
        loadCardWinnings(Battle.wonCards);
        changeToWinnings();

        treasurecards = 1;

    }

    /**
     * Loads the treasure winnings when the take treasure button is pressed.
     * @param actionEvent runs when the take treasure button is pressed
     * @throws FileNotFoundException We are loading the treasure textures from file
     */
    public void treasureButtonClick(ActionEvent actionEvent) throws FileNotFoundException {
        winningsPane.setPrefTileWidth(32);
        loadTreasureWinnings(Battle.loser.getTreasures());
        changeToWinnings();

        treasurecards = 0;

    }



}
