package com.example.battle_prototype;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import static com.example.battle_prototype.HelloApplication.PATH_TO_TEXTURES;
import static com.example.battle_prototype.TreasureType.DIAMOND;
import static com.example.battle_prototype.TreasureType.RUBIN;


public class Battle {


    // for testing
    private ArrayList<Treasure> treasures;
    private ArrayList<CrewCard> cards;

    private ArrayList<CrewCard> wonCards;

    /*
    Make the players and give them boat textures and attack power
     */

    Player player1 = new Player("Player 1", 0);
    Player player2 = new Player("Player 2",1);
    Player player3 = new Player("Player 3",2);
    Player player4 = new Player("Player 4",3);



    Player leftPlayer; // Holds the player on the left of the screen
    Player rightPlayer; // Holds the player on the right of the screen
    Player winner; // The winning player in the battle
    Player loser; // The losing player in the battle

    Image leftPlayerImage;
    Image rightPlayerImage;

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

    @FXML
    ToggleGroup playerLeft, playerRight;




    public Battle() {
    }


    public void initialize() throws FileNotFoundException {

        /*
        Testing stuff here
        ######################################################################################################
         */

        // making some treasures to test with
        Treasure diamond1 = new Treasure("diamond1", DIAMOND);
        Treasure diamond2 = new Treasure("diamond2", DIAMOND);
        Treasure diamond3 = new Treasure("diamond3", DIAMOND);
        Treasure diamond4 = new Treasure("diamond4", DIAMOND);
        Treasure ruby1 = new Treasure("ruby1", RUBIN);
        Treasure ruby2 = new Treasure("ruby2", RUBIN);
        Treasure ruby3 = new Treasure("ruby3", RUBIN);
        Treasure ruby4 = new Treasure("ruby4", RUBIN);

        // making some cards to test with
        CrewCard card1 = new CrewCard(1, 0);
        CrewCard card2 = new CrewCard(2, 0);
        CrewCard card3 = new CrewCard(3, 0);
        CrewCard card4 = new CrewCard(1, 0);
        CrewCard card5 = new CrewCard(2, 0);
        CrewCard card6 = new CrewCard(3, 0);
        CrewCard card7 = new CrewCard(1,0);
        CrewCard card8 = new CrewCard(1,1);
        CrewCard card9 = new CrewCard(2, 1);
        CrewCard card10 = new CrewCard(3, 1);
        CrewCard card11 = new CrewCard(1, 1);
        CrewCard card12 = new CrewCard(2, 1);
        CrewCard card13 = new CrewCard(3, 1);
        CrewCard card14 = new CrewCard(1, 1);




        /*
        For all 4 players, create a new arraylist of treasures, add treasures to it then give it to the player
         */

        treasures = new ArrayList<Treasure>();
        treasures.add(diamond1);
        treasures.add(ruby1);
        System.out.println(treasures.size());

        cards = new ArrayList<CrewCard>();
        cards.add(card1);
        cards.add(card8);

        player1.setTreasures(treasures);
        player1.setCards(cards);

        treasures = new ArrayList<Treasure>();
        treasures.add(diamond2);
        treasures.add(ruby3);
        treasures.add(ruby2);
        System.out.println(treasures.size());

        cards = new ArrayList<CrewCard>();
        cards.add(card2);
        cards.add(card3);
        cards.add(card4);
        cards.add(card9);
        cards.add(card10);

        player2.setTreasures(treasures);
        player2.setCards(cards);



        treasures = new ArrayList<Treasure>();
        treasures.add(diamond3);
        System.out.println(treasures.size());

        cards = new ArrayList<CrewCard>();
        cards.add(card5);
        cards.add(card6);
        cards.add(card11);
        cards.add(card12);

        player3.setTreasures(treasures);
        player3.setCards(cards);


        treasures = new ArrayList<Treasure>();
        treasures.add(ruby4);
        System.out.println(treasures.size());

        cards = new ArrayList<CrewCard>();
        cards.add(card7);
        cards.add(card13);
        cards.add(card14);


        player4.setTreasures(treasures);
        player4.setCards(cards);

        leftPlayer = player1;
        rightPlayer = player2;


        /*
        Testing stuff ends here
        ################################################################################################################
         */

        /*
        Setting pictures of the boats
         */

        leftPlayerImage = new Image(new FileInputStream(PATH_TO_TEXTURES + "Ship" + (leftPlayer.getColor() + 1) + ".png"));
        rightPlayerImage = new Image(new FileInputStream(PATH_TO_TEXTURES + "Ship" + (rightPlayer.getColor() + 1) + ".png"));

        System.out.println("Setting boat 1...");
        pLeftText.setText(leftPlayer.getName());
        battleP1Ship.setImage(leftPlayerImage);
        System.out.println("Setting boat 2...");
        pRightText.setText(rightPlayer.getName());
        battleP2Ship.setImage(rightPlayerImage);

        /*
        Showing the attack power of the players on the screen.
         */
        p1power.setText("Attack Power: " + calcAttackPower(leftPlayer));
        p2power.setText("Attack Power: " + calcAttackPower(rightPlayer));

        /*
        Calculate who won, show that on the screen then load the losing player's winnings.
         */
        calculateWin(leftPlayer, rightPlayer);

        wonBattleText.setText(winner.getName() + " has won the battle!");

        loadWinningPreview(loser);

    }




    public void continueButtonClick(ActionEvent actionEvent) {
        /*
        When the continue button is pressed
         */

        switch (treasurecards) {
            case 0:
                winner.getTreasures().addAll(loser.getTreasures());
                loser.getTreasures().removeAll(loser.getTreasures());
                break;
            case 1:
                winner.getCards().addAll(wonCards);
                loser.getCards().removeAll(wonCards);
                break;

        }
        System.out.println("Continue the game");
    }

    public void calculateWin(Player left, Player right) {
        /*
        Calculate who won based on attack power
         */
        if (calcAttackPower(left) > calcAttackPower(right)){
            winner = left;
            loser = right;
        }
        else {
            winner = right;
            loser = left;
        }
    }

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

    public void loadTreasureWinnings(Player l) throws FileNotFoundException {
        /*
        Loads the loser's treasure in to the winnings tile pane
         */

        Image texture;

        winningsPane.getChildren().clear();
        winningsPane.setPrefTileWidth(32);


        for (int i = 0; i < l.getTreasures().size(); i++) {
            texture = makeTreasureImage(l.getTreasures().get(i));
            winningsPane.getChildren().add(new ImageView(texture));
        }
    }


    public void loadCardWinnings(Player l) throws FileNotFoundException {
        /*
        Loads the loser's cards in to the winnings tile pane
         */

        winningsPane.getChildren().clear();
        winningsPane.setPrefTileWidth(55);

        String power;
        Image texture;
        CrewCard card;

        wonCards = new ArrayList<>();
        setCardsWon(l.getCards());


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


    public void changeScene() {
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


    public void cardsButtonClick(ActionEvent actionEvent) throws FileNotFoundException {
        winningsPane.setPrefTileWidth(40);
        loadCardWinnings(loser);
        changeScene();

        treasurecards = 1;

    }

    public void treasureButtonClick(ActionEvent actionEvent) throws FileNotFoundException {
        winningsPane.setPrefTileWidth(32);
        loadTreasureWinnings(loser);
        changeScene();

        treasurecards = 0;

    }



    public void setCardsWon(ArrayList<CrewCard> c) {
        CrewCard currentLowest = null;
        for (int i1 = 0; i1 < 2; i1++) {
            for (int i2 = 0; i2 < c.size(); i2++){
                if (i2 == 0) {
                    currentLowest = c.get(i2);
                }
                else if (c.get(i2 - 1).getValue() > c.get(i2).getValue()) {
                    currentLowest = c.get(i2);
                }
            }
            wonCards.add(currentLowest);
            c.remove(currentLowest);

        }

    }


    public int calcAttackPower(Player p) {
        int powerRed = 0;
        int powerBlack = 0;
        int power = 0;
        ArrayList<CrewCard> deck = p.getCards();

        for (int i = 0; i < deck.size(); i++) {
            CrewCard c = deck.get(i);
            switch (c.getColor()){
                case 0:
                    powerRed += c.getValue();
                    break;
                case 1:
                    powerBlack += c.getValue();
                    break;
            }
        }

        if (powerRed >= powerBlack) {
            power = powerRed - powerBlack;
        }
        else {
            power = powerBlack - powerRed;
        }
        return power;
    }


    public void onRadioChange(ActionEvent actionEvent) throws FileNotFoundException {

        /*
        Method which updates the program when there is a change in the radio buttons
         */


        /*
        Find out which radio buttons are selected
         */
        RadioButton selectedPlayerLeft = (RadioButton) playerLeft.getSelectedToggle();
        RadioButton selectedPlayerRight = (RadioButton) playerRight.getSelectedToggle();
        String leftPlayerValue = selectedPlayerLeft.getText();
        String rightPlayerValue = selectedPlayerRight.getText();
        System.out.println(leftPlayerValue + rightPlayerValue);

        /*
        Update the left and right player based on what's selected
         */
        switch (leftPlayerValue) {
            case "Player 1":
                leftPlayer = player1;
                break;
            case "Player 2":
                leftPlayer = player2;
                break;
            case "Player 3":
                leftPlayer = player3;
                break;
            case "Player 4":
                leftPlayer = player4;
                break;
        }

        switch (rightPlayerValue) {
            case "Player 1":
                rightPlayer = player1;
                break;
            case "Player 2":
                rightPlayer = player2;
                break;
            case "Player 3":
                rightPlayer = player3;
                break;
            case "Player 4":
                rightPlayer = player4;
                break;
        }


        /*
        Update the images and attack power to the selected players'
         */
        leftPlayerImage = new Image(new FileInputStream(PATH_TO_TEXTURES + "Ship" + (leftPlayer.getColor() + 1) + ".png"));
        rightPlayerImage = new Image(new FileInputStream(PATH_TO_TEXTURES + "Ship" + (rightPlayer.getColor() + 1) + ".png"));

        battleP1Ship.setImage(leftPlayerImage);
        pLeftText.setText(leftPlayer.getName());
        p1power.setText("Attack Power: " + calcAttackPower(leftPlayer));

        battleP2Ship.setImage(rightPlayerImage);
        pRightText.setText(rightPlayer.getName());
        p2power.setText("Attack Power: " + calcAttackPower(rightPlayer));

        /*
        Calculate who won out of the new selected players and load the winnings.
         */
        calculateWin(leftPlayer, rightPlayer);
        System.out.println(winner.getName() + " has won, loading treasures from " + loser.getName());

        wonBattleText.setText(winner.getName() + " has won the battle!");

        loadWinningPreview(loser);

        switch (treasurecards) {
            case 0:
                loadTreasureWinnings(loser);
                break;
            case 1:
                loadCardWinnings(loser);
                break;
        }

    }
}
