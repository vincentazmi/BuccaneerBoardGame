package com.example.battle_prototype;

import javafx.scene.image.Image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Player {

    private final String name; // Name of the player
    private final String texture; // Path to the player's texture
    private ArrayList<Treasure> treasures; // The treasure the player owns
    private ArrayList<Card> cards; // The cards the player owns


    public Player(String name, String texture)  {

        this.texture = texture;
        this.name = name;

    }


    public Image getTexture() throws FileNotFoundException {
        /*
        Take the path to the player's texture and return it as an image
         */
        Image image = new Image(new FileInputStream(this.texture));

        return image;
    }

    public ArrayList<Treasure> getTreasures() {
        return treasures;
    }

    public void setTreasures(ArrayList<Treasure> treasures) {
        this.treasures = treasures;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public String getName() {
        return name;
    }

    public int getAttackPower() {
        int powerRed = 0;
        int powerBlack = 0;
        int power = 0;

        for (int i = 0; i < cards.size(); i++) {
            Card c = cards.get(i);
            switch (c.getType()){
                case "red":
                    powerRed += c.getPower();
                    break;
                case "black":
                    powerBlack += c.getPower();
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
}
