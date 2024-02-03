package com.example.battle_prototype;

import javafx.scene.image.Image;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Treasure {
    private String texturePath;
    private final String type;


    public Treasure(String type) {
        this.type = type;

    }

    public Image getTexture() throws FileNotFoundException {
        switch (type) {
            case "diamond":
                texturePath = "src/main/resources/com/example/battle_prototype/diamond.png";
            case "ruby":
                texturePath = "src/main/resources/com/example/battle_prototype/ruby.png";
            case "rum":
                texturePath = "src/main/resources/com/example/battle_prototype/rum.png";
            case "gold":
                texturePath = "src/main/resources/com/example/battle_prototype/gold.png";
        }
                Image image = new Image(new FileInputStream(this.texturePath));

                return image;
    }
}
