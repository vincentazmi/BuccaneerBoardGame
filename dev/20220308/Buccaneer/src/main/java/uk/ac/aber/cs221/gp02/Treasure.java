package uk.ac.aber.cs221.gp02;

public class Treasure extends Item implements Comparable<Treasure>{


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

    public int getValue(){
        return value;
    }

    @Override
    public int compareTo(Treasure o) {
        return Integer.compare(this.getValue(), o.getValue());
    }
}