package uk.ac.aber.cs221.gp02.tradescreenprototype;

import java.util.ArrayList;
import java.util.Collections;

public class TradeTestPlayer {

    private String name;
    private int color;
    private int steps;

    private ArrayList<Treasure> treasures;
    private ArrayList<CrewCard> cards;



    // Standardised Constructor for testing

    TradeTestPlayer(String name, int color) {

        this.name = name;
        this.color = color;


        treasures = new ArrayList<>();
        treasures.add(new Treasure("RUBIN", Treasures.RUBIN));
        treasures.add(new Treasure("RUBIN", Treasures.RUBIN));
        treasures.add(new Treasure("RUBIN", Treasures.RUBIN));
        treasures.add(new Treasure("DIAMOND", Treasures.DIAMOND));
        treasures.add(new Treasure("GOLD", Treasures.GOLD));
        treasures.add(new Treasure("GOLD", Treasures.GOLD));
        treasures.add(new Treasure("BARREL", Treasures.BARREL));

        cards = new ArrayList<>();
        cards.add(new CrewCard(0, 1));
        cards.add(new CrewCard(0,2));
        cards.add(new CrewCard(1,3));
        cards.add(new CrewCard(1,3));
        cards.add(new CrewCard(1,2));
        sortCards();
        calculateSteps();

    }

    public void addCard(CrewCard card) {
        cards.add(card);
        sortCards();
        calculateSteps();
    }

    public Card getCard() {
        CrewCard c = cards.get(0);
        cards.remove(0);
        calculateSteps();
        return c;
    }

    public void removeCard(Card card) {
        cards.remove(card);
        sortCards();
        calculateSteps();

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

    public String getName() {
        return name;
    }

    public int getColor() {
        return color;
    }

    public int getSteps() {
        return steps;
    }

    private void sortCards() {
        Collections.sort(cards);
    }

    private void sortTreasure(){
        Collections.sort(treasures);
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

    private void calculateSteps() {
        int s = 0;
        for (CrewCard c : cards)
            s += c.getValue();

        steps = Math.max(1, s);
    }

}
