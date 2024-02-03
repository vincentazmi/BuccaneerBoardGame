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


import java.io.FileNotFoundException;
import java.util.ArrayList;


public class Battle {


    private ArrayList<Treasure> treasures;
    private ArrayList<Card> cards;
    private ArrayList<Card> selectedCards;

    /*
    Make the players and give them boat textures and attack power (will change)
     */

    Player player1 = new Player("Player 1", "src/main/resources/com/example/battle_prototype/Yellow_boat.png");
    Player player2 = new Player("Player 2","src/main/resources/com/example/battle_prototype/Red_boat.png");
    Player player3 = new Player("Player 3","src/main/resources/com/example/battle_prototype/Blue_boat.png");
    Player player4 = new Player("Player 4","src/main/resources/com/example/battle_prototype/Green_boat.png");


    Player leftPlayer; // Holds the player on the left of the screen
    Player rightPlayer; // Holds the player on the right of the screen
    Player winner; // The winning player in the battle
    Player loser; // The losing player in the battle

    int treasurecards; // so that the game knows what you clicked 0 = take treasure, 1 = take cards

    /*
    FXML Objects
     */

    @FXML
    ImageView battleP1Ship; // The picture of the left player's ship

    @FXML
    ImageView battleP2Ship; // The picture of the right player's ship

    @FXML
    ToggleGroup playerLeft; // The group of radio buttons for testing which change which player is on the left of the screen

    @FXML
    ToggleGroup playerRight; // The group of radio buttons for testing which change which player is on the right of the screen

    @FXML
    Text wonBattleText; // The text in the middle which tells us who won the battle

    @FXML
    Text p1power; // The text below the left player which tells us their attack power

    @FXML
    Text p2power; // The text below the right player which tells us their attack power

    @FXML
    Text pLeftText; // Name of left player

    @FXML
    Text pRightText; // Name of right player

    @FXML
    Text winningsLabel; // Just the label above the winnings box

    @FXML
    TilePane winningsPane; // Tile pane to show pictures of what the player has won

    @FXML
    Button continueButton;

    @FXML
    Button takeCardsButton;

    @FXML
    Button takeTreasureButton;

    @FXML
    TilePane cardsBox;

    @FXML
    TilePane treasureBox;

    @FXML
    Text or;

    @FXML
    Text couldhave;

    @FXML
    Button backButton;




    public Battle() throws FileNotFoundException {
    }


    public void initialize() throws FileNotFoundException {
        // making some treasures to test with
        Treasure diamond1 = new Treasure("diamond");
        Treasure diamond2 = new Treasure("diamond");
        Treasure diamond3 = new Treasure("diamond");
        Treasure diamond4 = new Treasure("diamond");
        Treasure ruby1 = new Treasure("ruby");
        Treasure ruby2 = new Treasure("ruby");
        Treasure ruby3 = new Treasure("ruby");
        Treasure ruby4 = new Treasure("ruby");

        // making some cards to test with
        Card card1 = new Card(1, "red");
        Card card2 = new Card(2, "red");
        Card card3 = new Card(3, "red");
        Card card4 = new Card(1, "red");
        Card card5 = new Card(2, "red");
        Card card6 = new Card(3, "red");
        Card card7 = new Card(1,"red");
        Card card8 = new Card(1,"black");
        Card card9 = new Card(2, "black");
        Card card10 = new Card(3, "black");
        Card card11 = new Card(1, "black");
        Card card12 = new Card(2, "black");
        Card card13 = new Card(3, "black");
        Card card14 = new Card(1, "black");




        /*
        For all 4 players, create a new arraylist of treasures, add treasures to it then give it to the player
         */

        treasures = new ArrayList<Treasure>();
        treasures.add(diamond1);
        treasures.add(ruby1);
        System.out.println(treasures.size());

        cards = new ArrayList<Card>();
        cards.add(card1);
        cards.add(card8);

        player1.setTreasures(treasures);
        player1.setCards(cards);

        treasures = new ArrayList<Treasure>();
        treasures.add(diamond2);
        treasures.add(ruby3);
        treasures.add(ruby2);
        System.out.println(treasures.size());

        cards = new ArrayList<Card>();
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

        cards = new ArrayList<Card>();
        cards.add(card5);
        cards.add(card6);
        cards.add(card11);
        cards.add(card12);

        player3.setTreasures(treasures);
        player3.setCards(cards);


        treasures = new ArrayList<Treasure>();
        treasures.add(ruby4);
        System.out.println(treasures.size());

        cards = new ArrayList<Card>();
        cards.add(card7);
        cards.add(card13);
        cards.add(card14);


        player4.setTreasures(treasures);
        player4.setCards(cards);

        /*
        Setting pictures of the boats
         */
        System.out.println("Setting boat 1...");
        battleP1Ship.setImage(player1.getTexture());
        System.out.println("Setting boat 2...");
        battleP2Ship.setImage(player2.getTexture());

        /*
        Showing the attack power of the players on the screen.
         */
        p1power.setText("Attack Power: " + player1.getAttackPower());
        p2power.setText("Attack Power: " + player2.getAttackPower());

        /*
        Calculate who won, show that on the screen then load the losing player's treasures into the winnings box.
         */
        calculateWin(player1, player2);

        wonBattleText.setText(winner.getName() + " has won the battle!");

        loadWinningPreview(loser);

    }




    public void continueButtonClick(ActionEvent actionEvent) {
        /*
        When the continue button is pressed
         */
        //ArrayList<Object> winnings = new ArrayList<Object>();

        switch (treasurecards) {
            case 0:
                winner.getTreasures().addAll(loser.getTreasures());
                loser.getTreasures().removeAll(loser.getTreasures());

//                System.out.println("winner now has:");
//                for (int i = 0; i < winner.getTreasures().size(); i++){
//                    System.out.println(winner.getTreasures().get(i).getType());
//                }

                break;
            case 1:
                winner.getCards().addAll(selectedCards);
                loser.getCards().removeAll(selectedCards);
                break;

        }
        System.out.println("Continue the game");
    }

    public void calculateWin(Player left, Player right) {
        /*
        Calculate who won based on attack power
         */
        if (left.getAttackPower() > right.getAttackPower()){
            winner = left;
            loser = right;
        }
        else {
            winner = right;
            loser = left;
        }
    }

    public void loadWinningPreview(Player l) throws FileNotFoundException {
        /*
        Loads the loser's treasure and cards in to the winnings preview tile pane
         */

        treasureBox.getChildren().clear();

        for (int i = 0; i < l.getTreasures().size(); i++) {
            treasureBox.getChildren().add(new ImageView(l.getTreasures().get(i).getTexture()));
        }

        Card card;
        String power;
        Image texture;
        cardsBox.getChildren().clear();

        for (int i = 0; i < l.getCards().size(); i++) {
            card = l.getCards().get(i);
            power = String.valueOf(card.getPower());
            texture = card.getTexture();

            Label cardLabel = new Label();
            cardLabel.setText(power);
            cardLabel.setGraphic(new ImageView(texture));
            cardLabel.getStyleClass().add("buttons");
            cardsBox.getChildren().add(cardLabel);
        }

    }

    public void loadTreasureWinnings(Player l) throws FileNotFoundException {
        /*
        Loads the loser's treasure in to the winnings tile pane
         */

        winningsPane.getChildren().clear();
        winningsPane.setPrefTileWidth(32);


        for (int i = 0; i < l.getTreasures().size(); i++) {
            winningsPane.getChildren().add(new ImageView(l.getTreasures().get(i).getTexture()));
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
        Card card;

        selectedCards = new ArrayList<Card>();



        for (int i = 0; i < l.getCards().size(); i++) {
            card = l.getCards().get(i);
            power = String.valueOf(card.getPower());
            texture = card.getTexture();

            Button cardButton = new Button();
            cardButton.setText(power);
            cardButton.setGraphic(new ImageView(texture));
            cardButton.getStyleClass().add("buttons");

            Card finalCard = card;
            cardButton.setOnAction((ActionEvent)->{
                buttonSelect(cardButton, finalCard);
            });


            winningsPane.getChildren().add(cardButton);


        }
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
        battleP1Ship.setImage(leftPlayer.getTexture());
        pLeftText.setText(leftPlayer.getName());
        p1power.setText("Attack Power: " + leftPlayer.getAttackPower());

        battleP2Ship.setImage(rightPlayer.getTexture());
        pRightText.setText(rightPlayer.getName());
        p2power.setText("Attack Power: " + rightPlayer.getAttackPower());

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
        loadCardWinnings(loser);
        changeScene();
        winningsLabel.setText("Pick 2 cards to take:");

        treasurecards = 1;

    }

    public void treasureButtonClick(ActionEvent actionEvent) throws FileNotFoundException {
        loadTreasureWinnings(loser);
        changeScene();
        winningsLabel.setText("Winnings:");

        treasurecards = 0;

    }



    public void buttonSelect(Button b, Card c){
        if (selectedCards.contains(c)) {
            selectedCards.remove(c);
            //b.setDefaultButton(false);
            b.getStyleClass().remove("selected");
            System.out.println("removed card, can select again");
        }
        else if (selectedCards.size() < 2) {
            //b.setDefaultButton(true);
            b.getStyleClass().add("selected");
            selectedCards.add(c);
            System.out.println("selected card");
        }

        System.out.println("done");

    }
}
