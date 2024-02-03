package com.example.testfx;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static com.example.testfx.BuccaneerApplication.PATH_TO_TEXTURES;

/**
 * This class is a template for all treasures
 */
public class Treasure extends BoardElem {


    public Treasure(int x, int y, int treasure) throws FileNotFoundException {
        super(x, y);

        String[] textures = new String[]{
                PATH_TO_TEXTURES + "treasure1.png",
                PATH_TO_TEXTURES + "treasure2.png",
        };

        ImagePattern texture = new ImagePattern(new Image(new FileInputStream(textures[treasure - 1])));
        getTextureBox().setFill(texture);
        getChildren().add(getTextureBox());
    }
}
