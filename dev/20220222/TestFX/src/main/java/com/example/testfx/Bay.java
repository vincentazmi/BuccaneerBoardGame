package com.example.testfx;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import  static com.example.testfx.BuccaneerApplication.PATH_TO_TEXTURES;

/**
 * This class is a template for all bays
 */
public class Bay extends BoardElem {

    private final ImagePattern texture;

    public Bay(int x, int y, int bay) throws FileNotFoundException {
        super(x, y);

        //initializing textures
        String[] textures = new String[]{
                "Bay.png",
                "Bay.png",
                "Bay.png",
        };

        texture = new ImagePattern(new Image(new FileInputStream(PATH_TO_TEXTURES + textures[bay - 1])));
        getTextureBox().setFill(texture);
        getChildren().add(getTextureBox());
    }



}
