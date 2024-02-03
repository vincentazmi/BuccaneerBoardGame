package uk.ac.aber.cs221.gp02;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static uk.ac.aber.cs221.gp02.BuccaneerApplication.TILE_SIZE;
import static uk.ac.aber.cs221.gp02.BuccaneerApplication.PATH_TO_TEXTURES;

/**
 * This class is a template of Tiles
 */
public class Tile extends Rectangle {


//    private final Color darkColor = Color.rgb(100, 180, 250);
//    private final Color lightColor = Color.rgb(160,205,250);

    //  private final Color darkColorOnPath = Color.rgb(100, 250, 180);
    // private final Color lightColorOnPath = Color.rgb(160,250,205);


    //  private final Color darkColorInRange = Color.rgb(50, 200, 130);
    // private final Color lightColorInRange = Color.rgb(110,200,165);

    private final ImagePattern darkColor;
    private final ImagePattern lightColor;
    private final ImagePattern darkColorInRange;
    private final ImagePattern lightColorInRange;
    private final ImagePattern darkColorOnPath;
    private final ImagePattern lightColorOnPath;


    private final boolean isLight;
    private final int xCor;
    private final int yCor;
    private BoardElement element;
    private Treasure treasure;



    public Tile(boolean light, int x, int y) throws FileNotFoundException {
        xCor = x;
        yCor = y;
        isLight = light;
        setWidth(TILE_SIZE);
        setHeight(TILE_SIZE);
        relocate(x * TILE_SIZE, y * TILE_SIZE);

        darkColor = new ImagePattern(new Image(new FileInputStream(PATH_TO_TEXTURES + "TileDark.png")));
        lightColor = new ImagePattern(new Image(new FileInputStream(PATH_TO_TEXTURES + "TileLight.png")));
        darkColorInRange = new ImagePattern(new Image(new FileInputStream(PATH_TO_TEXTURES + "TileDark1.png")));
        lightColorInRange = new ImagePattern(new Image(new FileInputStream(PATH_TO_TEXTURES + "TileLight1.png")));
        darkColorOnPath = new ImagePattern(new Image(new FileInputStream(PATH_TO_TEXTURES + "TileDark2.png")));
        lightColorOnPath = new ImagePattern(new Image(new FileInputStream(PATH_TO_TEXTURES + "TileLight2.png")));
        setFill(isLight ? lightColor : darkColor);

        element = null;
        treasure = null;
    }


    /**
     * This method is to change the color of the tail
     * @param mode is ID of the color
     */
    public void setColor(int mode) {
        switch (mode){
            case 1 -> setFill(isLight ? lightColor : darkColor);
            case 2 -> setFill(isLight ? lightColorInRange : darkColorInRange);
            case 3 -> setFill(isLight ? lightColorOnPath : darkColorOnPath);
            case 4 -> setFill(Color.RED);
            default -> setFill(Color.WHITE);
        }
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

    public Treasure getTreasure() {
        return treasure;
    }

    public void setTreasure(Treasure treasure) {
        this.treasure = treasure;
    }

    @Override
    public String toString() {
        return (element != null ? element.toString() : "") +
                (treasure != null ? treasure.toString() : "");
    }
}
