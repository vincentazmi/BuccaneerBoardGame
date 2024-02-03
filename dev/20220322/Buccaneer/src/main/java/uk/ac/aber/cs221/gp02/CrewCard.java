package uk.ac.aber.cs221.gp02;


public class CrewCard extends Card implements Comparable<CrewCard> {
    private int color;
    private int value;

    CrewCard(){
        this(0,1);
    }
    CrewCard(int color, int value){
        this.color = color;
        this.value = value;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }


    @Override
    public String toString() {
        return (color == 0 ? "Black" : "Red") + " card of value " + value;
    }

    @Override
    public int compareTo(CrewCard o) {
        if(this.getValue() != o.getValue() )
            return Integer.compare(this.getValue(), o.getValue());
        return Integer.compare(this.getColor(), o.getColor());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CrewCard crewCard = (CrewCard) o;
        return color == crewCard.color && value == crewCard.value;
    }

}
