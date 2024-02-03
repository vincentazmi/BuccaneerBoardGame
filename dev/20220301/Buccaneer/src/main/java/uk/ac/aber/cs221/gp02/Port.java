package uk.ac.aber.cs221.gp02;

public class Port extends BoardElement {

    private int value;
    private String description;


    public Port(String name){
        super(name);
        this.value = 0;
    }
    public Port(String name, int value) {
        super(name);
        this.value = value;
    }


    @Override
    public String toString() {
        return  "Some words of description just to fill this with text";
    }


}
