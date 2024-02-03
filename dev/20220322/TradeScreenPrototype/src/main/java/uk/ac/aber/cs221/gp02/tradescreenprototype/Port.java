package uk.ac.aber.cs221.gp02.tradescreenprototype;

import java.util.ArrayList;
import java.util.Collections;

public class Port extends BoardElement {

    private int value;
    private String description;
    private ArrayList<CrewCard> cards;
    private ArrayList<Treasure> treasures;

    // Standardised Constructor for testing

    public Port(String name){
        this(name, 0);
    }
    public Port(String name, int value) {
        super(name);
        this.value = value;

        treasures = new ArrayList<>();
        treasures.add(new Treasure("RUBIN", Treasures.RUBIN));
        treasures.add(new Treasure("DIAMOND", Treasures.DIAMOND));
        treasures.add(new Treasure("DIAMOND", Treasures.DIAMOND));
        treasures.add(new Treasure("DIAMOND", Treasures.DIAMOND));
        treasures.add(new Treasure("GOLD", Treasures.GOLD));
        treasures.add(new Treasure("GOLD", Treasures.GOLD));
        treasures.add(new Treasure("BARREL", Treasures.BARREL));

        cards = new ArrayList<>();
        cards.add(new CrewCard(0, 1));
        cards.add(new CrewCard(0, 1));
        cards.add(new CrewCard(0, 1));
        cards.add(new CrewCard(0, 1));
        cards.add(new CrewCard(0,2));
        cards.add(new CrewCard(1,3));
        cards.add(new CrewCard(1,3));
        cards.add(new CrewCard(1,2));
        sortCards();

    }

    public void addCard(CrewCard card){
        cards.add(card);
        sortCards();
    }

    public Card getCard() {
        CrewCard c = cards.get(0);
        cards.remove(0);
        return c;
    }

    public void removeCard(Card cardToRemove) {
        cards.remove(cardToRemove);
        sortCards();
    }

    public void addTreasure(Treasure treasure){
        treasures.add(treasure);
    }

    public Treasure getTreasure() {
        Treasure t = treasures.get(0);
        treasures.remove(0);
        return t;
    }

    public void removeTreasure(Treasure treasure) {
        treasures.remove(treasure);
    }

    private void sortCards() {
        Collections.sort(cards);
    }

    public int[] treasureDescription(){
        int[] desc = new int [5];

        for (Treasure t: treasures)
            switch (t.getType()) {
                case DIAMOND -> desc[0]++;
                case RUBIN -> desc[1]++;
                case GOLD -> desc[2]++;
                case PEARL -> desc[3]++;
                case BARREL -> desc[4]++;
            }
        return desc;
    }

    public int[] cardsDescription(){
        int[] desc = new int[6];

        for(CrewCard c: cards)
            desc[c.getColor() * 3 + c.getValue() - 1]++;

        return desc;
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
