package com.example.testfx;

import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

import static com.example.testfx.BuccaneerApplication.TILE_SIZE;

/**
 * This class is a super class of all board elements
 */

public class BoardElem extends StackPane {
    private int xCor, yCor;
    private Rectangle textureBox;

    /**
     * Constructor creates an empty stack pane, relocates it to given coordinates and fills it with rectangle object
     * @param x is x coordinate
     * @param y is y coordinate
     */
    public BoardElem(int x, int y) {
        // coords are converted from game format(bottom left square is 1,1) to
        // javafx format (top left square is 0, 0)
        xCor = x - 1;
        yCor = 20 - y;
        relocate(xCor * TILE_SIZE, yCor * TILE_SIZE);
        textureBox = new Rectangle(TILE_SIZE, TILE_SIZE);
    }

    //bunch of getters and setter
    // will add comments later

    public int getXCor() {
        return xCor;
    }

    public void setXCor(int xCor) {
        this.xCor = xCor;
    }

    public int getYCor() {
        return yCor;
    }

    public void setYCor(int yCor) {
        this.yCor = yCor;
    }

    public Rectangle getTextureBox() {
        return textureBox;
    }

    public void setTextureBox(Rectangle textureBox) {
        this.textureBox = textureBox;
    }
}
