package com.example.testfx;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static com.example.testfx.BuccaneerApplication.TILE_SIZE;
import static com.example.testfx.BuccaneerApplication.PATH_TO_TEXTURES;

/**
 * This class is a template of the Island
 * note: It should be the super class of all 3 kinds of islands, since they are completely different
 */
public class Island extends BoardElem {

    private ImagePattern texture;


    public Island(int x, int y, int sizeX, int sizeY, int island) throws FileNotFoundException {
        super(x, y);
        setTextureBox(new Rectangle(sizeX * TILE_SIZE, sizeY * TILE_SIZE));

        String[] textures = new String[]{
                 "IslandF.png",
                 "IslandP.png",
                 "IslandT.png",
        };

        texture = new ImagePattern(new Image(new FileInputStream(PATH_TO_TEXTURES + textures[island - 1])));
        getTextureBox().setFill(texture);
        getChildren().add(getTextureBox());
    }
}
