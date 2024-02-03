package uk.ac.aber.cs221.gp02;

public class BoardElement {
    private String name;


    public BoardElement(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name + "\n";
    }

    public String getName() {
        return name;
    }

}
