package uk.ac.aber.cs221.gp02;
/**
 * This class is a superclass of any non-movable object on the Board
 *
 * @version 1.0
 * @author Adrian
 */
public class BoardElement {
    private String name;


    /**
     * Standard constructor
     * @param name name
     */
    public BoardElement(String name) {
        this.name = name;
    }

    /**
     * Standard toString()
     * @return
     */
    @Override
    public String toString() {
        return name + "\n";
    }

    /**
     * Just getter
     * @return name
     */
    public String getName() {
        return name;
    }

}
