package uk.ac.aber.cs221.gp02;

/**
 * This class is a representation of crewCard in the Buccaneer Game
 *
 * @version 1.0
 * @author Adrian
 */
public class CrewCard extends Card implements Comparable<CrewCard> {
    private int color;
    private int value;

    /**
     * This is the default constructor with no arguments
     */
    CrewCard(){
        this(0,1);
    }

    /**
     * This is the standard constructor
     * @param color is either 0 or 1 which means black or red
     * @param value should be in range 1..3
     */
    CrewCard(int color, int value){
        this.color = color;
        this.value = value;
    }

    /**
     * Just getter
     * @return color
     */
    public int getColor() {
        return color;
    }

    /**
     * Just setter
     * @param color new color
     */
    public void setColor(int color) {
        this.color = color;
    }

    /**
     * Just getter
     * @return value
     */
    public int getValue() {
        return value;
    }


    /**
     * Just setter
     * @param value new value
     */
    public void setValue(int value) {
        this.value = value;
    }


    /**
     * Standard toString()
     * @return String representation
     */
    @Override
    public String toString() {
        return (color == 0 ? "Black" : "Red") + " card of value " + value;
    }

    /**
     * This method allows comparing 2 crewCard objects to sort them in the list
     * @param o is another card object
     * @return card with the lowest value and color
     */
    @Override
    public int compareTo(CrewCard o) {
        if(this.getValue() != o.getValue() )
            return Integer.compare(this.getValue(), o.getValue());
        return Integer.compare(this.getColor(), o.getColor());
    }

    /**
     * This method allows comparing 2 cards to decide if they are equal
     * @param o is another card object
     * @return true if cards are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CrewCard crewCard = (CrewCard) o;
        return color == crewCard.color && value == crewCard.value;
    }

}
