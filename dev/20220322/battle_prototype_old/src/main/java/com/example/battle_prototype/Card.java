package com.example.battle_prototype;

import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Card {
    private int power;
    private String type;
    private String texturePath;

    public Card(int power, String type) {
        this.type = type;
        this.power = power;

    }

    public int getPower() {
        return power;
    }

    public String getType() {
        return type;
    }

    public Image getTexture() throws FileNotFoundException {
        /*
        Return the correct texture as an image based on the type of card this is
         */
        switch (type) {
            case "red":
                texturePath = "src/main/resources/com/example/battle_prototype/PirateRed.png";
                break;
            case "black":
                texturePath = "src/main/resources/com/example/battle_prototype/PirateBlack.png";
                break;

        }
        Image image = new Image(new FileInputStream(this.texturePath));

        return image;
    }

}
