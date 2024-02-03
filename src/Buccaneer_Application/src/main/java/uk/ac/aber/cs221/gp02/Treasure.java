package uk.ac.aber.cs221.gp02;


/**
 * This class is a template for every Treasure
 *
 * @author Adrian
 * @version 1.0
 */
public class Treasure implements Comparable<Treasure>{


    private TreasureType type;
    private int value;
    private String name;


    /**
     * Just constructor
     * @param name is a name (to display in the UI)
     * @param type is a type of treasure e.g. Diamond
     */
    public Treasure(String name, TreasureType type) {
        this.name = name;
        this.type = type;

        switch (type) {
            case DIAMOND, RUBIN -> value = 5;
            case GOLD -> value = 4;
            case PEARL -> value = 3;
            case BARREL -> value = 2;
        }

    }

    public Treasure(TreasureType type){
        this("unknown",type);
    }

    /**
     * Just getter
     * @return treasureType
     */
    public TreasureType getType() {
        return type;
    }

    /**
     * Just getter
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Just getter
     * @return value
     */
    public int getValue(){
        return value;
    }

    /**
     * Get a string representation of the treasure type
     * @return String representation of the treasure type
     */

    public String typeToString() {
        return getType().toString();
    }

    @Override
    public int compareTo(Treasure o) {
        return Integer.compare(this.getValue(), o.getValue());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Treasure treasure = (Treasure) o;
        return this.getType() == treasure.getType();
    }

    /**
     * Just toString()
     * @return String representation
     */
    @Override
    public String toString() {
        return name + " of value: " + value;
    }
}
