package com.example.battle_prototype;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;


public class HelloController {


    Player player1 = new Player("Player 1", "src/main/resources/com/example/battle_prototype/Yellow_boat.png", 179);
    Player player2 = new Player("Player 2","src/main/resources/com/example/battle_prototype/Red_boat.png", 283);
    Player player3 = new Player("Player 3","src/main/resources/com/example/battle_prototype/Blue_boat.png", 134);
    Player player4 = new Player("Player 4","src/main/resources/com/example/battle_prototype/Green_boat.png", 249);
    Player leftPlayer;
    Player rightPlayer;

    
    @FXML
    ImageView battleP1Ship;

    @FXML
    ImageView battleP2Ship;

    @FXML
    ToggleGroup playerLeft;

    @FXML
    ToggleGroup playerRight;

    @FXML
    Text wonBattleText;

    @FXML
    Text p1power;

    @FXML
    Text p2power;

    @FXML
    Text pLeftText;

    @FXML
    Text pRightText;


    public HelloController() throws FileNotFoundException {
    }


    public void initialize() throws FileNotFoundException {
            System.out.println("Setting boat 1...");
            battleP1Ship.setImage(player1.getTexture());
            System.out.println("Setting boat 2...");
            battleP2Ship.setImage(player2.getTexture());
            p1power.setText("Attack Power: " + player1.getAttackPower());
            p2power.setText("Attack Power: " + player2.getAttackPower());

            wonBattleText.setText(calculateWin(player1, player2).getName() + " has won the battle!");
    }




    public void cardsButtonClick(ActionEvent actionEvent) {
        System.out.println("You take the cards!");
    }

    public void treasureButtonClick(ActionEvent actionEvent) {
        System.out.println("You take the treasure!");
    }

    public Player calculateWin(Player left, Player right) {
        if (left.getAttackPower() > right.getAttackPower()){
            return left;
        }
        else
            return right;
    }

    public void onRadioChange(ActionEvent actionEvent) throws FileNotFoundException {

        // will clean this up later

        RadioButton selectedPlayerLeft = (RadioButton) playerLeft.getSelectedToggle();
        RadioButton selectedPlayerRight = (RadioButton) playerRight.getSelectedToggle();
        String leftPlayerValue = selectedPlayerLeft.getText();
        String rightPlayerValue = selectedPlayerRight.getText();
        System.out.println(leftPlayerValue + rightPlayerValue);


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
        battleP1Ship.setImage(leftPlayer.getTexture());
        pLeftText.setText(leftPlayer.getName());
        p1power.setText("Attack Power: " + leftPlayer.getAttackPower());

        battleP2Ship.setImage(rightPlayer.getTexture());
        pRightText.setText(rightPlayer.getName());
        p2power.setText("Attack Power: " + rightPlayer.getAttackPower());

        wonBattleText.setText(calculateWin(leftPlayer, rightPlayer).getName() + " has won the battle!");

        }

    }
