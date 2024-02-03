package uk.ac.aber.cs221.gp02;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static uk.ac.aber.cs221.gp02.BuccaneerApplication.TILE_SIZE;
import static uk.ac.aber.cs221.gp02.BuccaneerApplication.PATH_TO_TEXTURES;

/**
 * This class is a template of Tiles
 */
public class Tile {



    private final boolean isLight;
    private final int xCor;
    private final int yCor;
    private boolean isPath;

    private BoardElement element;



    public Tile(boolean light, int x, int y) {
        xCor = x;
        yCor = y;
        isLight = light;
        isPath = false;

        element = null;
    }


    public boolean isLight() {
        return isLight;
    }

    public boolean isPath() {
        return isPath;
    }

    public void setPath(boolean path) {
        isPath = path;
    }

    public int getXCor() {
        return xCor;
    }

    public int getYCor() {
        return yCor;
    }




    public BoardElement getElement() {
        return element;
    }

    public void setElement(BoardElement element) {
        this.element = element;
    }


    @Override
    public String toString() {
        return getXCor() + " " + getYCor();
    }
}
