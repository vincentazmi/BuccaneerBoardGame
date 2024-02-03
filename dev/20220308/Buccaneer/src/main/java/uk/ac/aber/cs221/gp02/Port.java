package uk.ac.aber.cs221.gp02;

import java.util.ArrayList;

public class Port extends BoardElement {

    private int value;
    private String description;
    private ArrayList<CrewCard> cards;


    public Port(String name){
        this(name, 0);
    }
    public Port(String name, int value) {
        super(name);
        this.value = value;
        cards = new ArrayList<>();

    }


    public void addCard(CrewCard card){
        cards.add(card);
    }



    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("It contains: ");
        for(CrewCard c: cards){
            stringBuilder.append("\n");
            stringBuilder.append(c);

        }

        return  stringBuilder.toString();
    }


}
