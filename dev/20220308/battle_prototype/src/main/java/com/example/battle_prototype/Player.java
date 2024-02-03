package com.example.battle_prototype;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Player {
    private final String name;
    private final String texture;
    private final int attackPower;
    //private ArrayList<Treasure> treasures;


    public Player(String name, String texture, int attackPower) throws FileNotFoundException {

        File file = new File(texture);

        if (!file.exists()) {
            throw new FileNotFoundException();
        }

        this.texture = texture;
        System.out.println("File OK");
        this.name = name;
        this.attackPower = attackPower;
    }

    public Image getTexture() throws FileNotFoundException {
        Image image = new Image(new FileInputStream(this.texture));

        return image;
    }

    public String getName() {
        return name;
    }

    public int getAttackPower() {
        return attackPower;
    }
}
