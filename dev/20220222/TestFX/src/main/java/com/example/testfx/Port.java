package com.example.testfx;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


import static com.example.testfx.BuccaneerApplication.PATH_TO_TEXTURES;

/**
 * This class is a template for all ports
 */
public class Port extends BoardElem {

    private ImagePattern texture;

    public Port(int x, int y, int port) throws FileNotFoundException {
        super(x, y);
        String[] textures = new String[]{
                 "PortA.png",
                 "PortC.png",
                 "PortG.png",
                 "PortL.png",
                 "PortM.png",
                 "PortV.png",
        };
        texture = new ImagePattern(new Image(new FileInputStream(PATH_TO_TEXTURES + textures[port - 1])));
        getTextureBox().setFill(texture);
        getChildren().add(getTextureBox());
    }



}
