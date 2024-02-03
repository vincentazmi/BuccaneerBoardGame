package uk.ac.aber.cs221.gp02;

public class Treasure extends Item {


    private Treasures type;
    private int value;
    private String name;


    public Treasure(String name, Treasures type) {
        this.name = name;
        this.type = type;

        switch (type) {
            case DIAMOND, RUBIN -> value = 5;
            case GOLD -> value = 4;
            case PEARL -> value = 3;
            case BARREL -> value = 2;
        }

    }

    public Treasures getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getValueText(){
        return "Value: " + value;
    }
}
