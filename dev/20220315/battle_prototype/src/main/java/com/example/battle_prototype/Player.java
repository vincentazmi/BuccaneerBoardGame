package com.example.battle_prototype;

import javafx.scene.image.Image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Player {

    private final String name; // Name of the player
    private final String texture; // Path to the player's texture
    private final int attackPower; // The player's attack power
    private ArrayList<Treasure> treasures; // The treasure the player owns


    public Player(String name, String texture, int attackPower)  {

        this.texture = texture;
        this.name = name;
        this.attackPower = attackPower;

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

    public String getName() {
        return name;
    }

    public int getAttackPower() {
        return attackPower;
    }
}
