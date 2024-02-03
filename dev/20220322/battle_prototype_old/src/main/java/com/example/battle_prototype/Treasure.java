package com.example.battle_prototype;

import javafx.scene.image.Image;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Treasure {
    private String texturePath; // Path to the texture of the treasure
    private final String type; // The type of treasure (e.g. diamond, ruby etc)


    public Treasure(String type) {
        this.type = type;

    }

    public String getType() {
        return type;
    }

    public Image getTexture() throws FileNotFoundException {
        /*
        Return the correct texture as an image based on the type of treasure this is
         */
        switch (type) {
            case "diamond":
                texturePath = "src/main/resources/com/example/battle_prototype/diamond.png";
                break;
            case "ruby":
                texturePath = "src/main/resources/com/example/battle_prototype/ruby.png";
                break;
            case "rum":
                //texturePath = "src/main/resources/com/example/battle_prototype/rum.png";
                break;
            case "gold":
                //texturePath = "src/main/resources/com/example/battle_prototype/gold.png";
                break;
        }
                Image image = new Image(new FileInputStream(this.texturePath));

                return image;
    }
}
