package uk.ac.aber.cs221.gp02;

/**
 * This class is a template for every Tile
 *
 * @author Adrian
 * @version 1.0
 */
public class Tile {



    private final boolean isLight;
    private final int xCor;
    private final int yCor;
    private boolean isPath;

    private BoardElement element;

    /**
     * Just constructor
     * @param light true if the color is supposed to be light, false otherwise
     * @param x x coordinate
     * @param y y coordinate
     */
    public Tile(boolean light, int x, int y) {
        xCor = x;
        yCor = y;
        isLight = light;
        isPath = false;
        element = null;
    }

    /**
     * Just getter
     * @return isLight
     */
    public boolean isLight() {
        return isLight;
    }

    /**
     * Just getter
     * @return isPath
     */
    public boolean isPath() {
        return isPath;
    }

    /**
     * Just setter
     * @param path new path
     */
    public void setPath(boolean path) {
        isPath = path;
    }

    /**
     * Just getter
     * @return x
     */
    public int getXCor() {
        return xCor;
    }

    /**
     * Just getter
     * @return y
     */
    public int getYCor() {
        return yCor;
    }

    /**
     * Just getter
     * @return boardElement
     */
    public BoardElement getElement() {
        return element;
    }

    /**
     * Just setter
     * @param element new boardElement
     */
    public void setElement(BoardElement element) {
        this.element = element;
    }

    /**
     * Just toString()
     * @return
     */
    @Override
    public String toString() {
        return getXCor() + " " + getYCor();
    }
}
