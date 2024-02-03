package uk.ac.aber.cs221.gp02;

import java.util.ArrayList;

public class Port extends BoardElement {

    private int value;
    private ArrayList<CrewCard> cards;
    private ArrayList<Treasure> treasures;


    public Port(String name){
        this(name, 0);
    }
    public Port(String name, int value) {
        super(name);
        this.value = value;
        cards = new ArrayList<>();
        treasures = new ArrayList<>();

    }


    public void addCard(CrewCard card){
        cards.add(card);
    }

    public void addTreasure(Treasure treasure) {treasures.add(treasure);}

    public ArrayList<CrewCard> getCards(){
        return cards;
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("It contains: ");
        for(CrewCard c: cards){
            stringBuilder.append("\n");
            stringBuilder.append(c);
        }
        for (Treasure t: treasures){
            stringBuilder.append("\n");
            stringBuilder.append(t);
        }

        return  stringBuilder.toString();
    }


}
