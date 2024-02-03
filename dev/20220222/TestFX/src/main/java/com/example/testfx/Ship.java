package com.example.testfx;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static com.example.testfx.BuccaneerApplication.TILE_SIZE;
import static com.example.testfx.BuccaneerApplication.PATH_TO_TEXTURES;

/**
 * This class is a template for all ships (players)
 */
public class Ship extends BoardElem {
    private Directions currentDirection;
    private int numOfSteps;

    private final ImagePattern[] texture;


    public Ship(int x, int y, int textureID) throws FileNotFoundException {
        super(x, y);

        texture = new ImagePattern[3];
        String[][] textures = new String[][]{
                {"Ship1.png",
                        "Ship11.png",
                        "Ship12.png"},
                {"Ship2.png",
                        "Ship21.png",
                        "Ship22.png"},
                {"Ship3.png",
                        "Ship31.png",
                        "Ship32.png"},
                {"Ship4.png",
                        "Ship41.png",
                        "Ship42.png"}
        };
        for (int i = 0; i < 3; i++)
            texture[i] = new ImagePattern(new Image(new FileInputStream(PATH_TO_TEXTURES + textures[textureID][i])));

        getTextureBox().setFill(texture[0]);
        getChildren().add(getTextureBox());

        currentDirection = Directions.NORTH;
        numOfSteps = 4;
    }

    public void changeDirection() {
        switch (currentDirection) {
            case NORTH -> currentDirection = Directions.EAST;
            case EAST -> currentDirection = Directions.SOUTH;
            case SOUTH -> currentDirection = Directions.WEST;
            case WEST -> currentDirection = Directions.NORTH;
        }
    }

    public void move(int x, int y) {

        switch (currentDirection) {
            case NORTH -> {
                if (y >= 0 && y < getYCor() && x == getXCor() && Math.abs(y - getYCor()) <= numOfSteps) {
                    numOfSteps -= Math.abs(y - getYCor());
                    setYCor(y);
                }
            }
            case EAST -> {
                if (x < 20 && x > getXCor() && y == getYCor() && Math.abs(x - getXCor()) <= numOfSteps) {
                    numOfSteps -= Math.abs(x - getXCor());
                    setXCor(x);
                }
            }
            case SOUTH -> {
                if (y < 20 && y > getYCor() && x == getXCor() && Math.abs(y - getYCor()) <= numOfSteps) {
                    numOfSteps -= Math.abs(y - getYCor());
                    setYCor(y);
                }
            }
            case WEST -> {
                if (x >= 0 && x < getXCor() && y == getYCor() && Math.abs(x - getXCor()) <= numOfSteps) {
                    numOfSteps -= Math.abs(x - getXCor());
                    setXCor(x);
                }
            }
        }
        relocate(getXCor() * TILE_SIZE, getYCor() * TILE_SIZE);

    }

    public String getCords() {
        return getXCor() + " " + getYCor() + " " + currentDirection + " steps remaining: " + numOfSteps;
    }


    public Directions getCurrentDirection() {
        return currentDirection;
    }

    public int getNumOfSteps() {
        return numOfSteps;
    }

    public void resetSteps() {
        numOfSteps = 4;
    }

    public void battleSize(int mode) {
        if (mode == 1) {
            getTextureBox().setFill(texture[1]);
        } else
            getTextureBox().setFill(texture[2]);

    }

    public void resetSize() {
        getTextureBox().setFill(texture[0]);

    }

}
