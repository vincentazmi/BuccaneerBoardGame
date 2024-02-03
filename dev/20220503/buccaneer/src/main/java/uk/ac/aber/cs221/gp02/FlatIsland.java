package uk.ac.aber.cs221.gp02;

import java.util.*;

public class FlatIsland extends Island {

    private ArrayList<Treasure> treasures;
    private Deck crewCards;

    public void setCrewCards(Deck crewCards) {
        this.crewCards = crewCards;
    }

    public FlatIsland(String name) {
        super(name);
        treasures = new ArrayList<>();
        crewCards = new Deck();
    }

    public void addCard(CrewCard card) {
        crewCards.addCard(card);
    }

    public Card getCard() {
        return crewCards.getCard();
    }


    public int[] cardsDescription() {
        int[] desc = new int[6];

        System.out.println(crewCards);
        for (Card c : crewCards.getCards())
            desc[((CrewCard) (c)).getColor() * 3 + 3 - ((CrewCard) (c)).getValue()]++;

        return desc;
    }

    public void addTreasure(Treasure treasure) {
        treasures.add(treasure);
    }

    public void removeTreasure(Treasure treasure) {
        treasures.remove(treasure);
    }

    public ArrayList<Treasure> getTreasures() {
        return treasures;
    }

    public List<Card> getCrewCards() {
        return crewCards.getCards();
    }

    public int[] treasureDescription() {
        int[] desc = new int[5];

        for (Treasure t : treasures)
            switch (t.getType()) {
                case DIAMOND -> desc[0]++;
                case RUBIN -> desc[1]++;
                case GOLD -> desc[2]++;
                case PEARL -> desc[3]++;
                case BARREL -> desc[4]++;
            }
        return desc;

    }

    public void setTreasures(ArrayList<Treasure> treasures) {
        this.treasures = treasures;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for(Card c: crewCards.getCards()){
            stringBuilder.append((CrewCard)c);
            stringBuilder.append("\n");
        }
        for(Treasure t: treasures){
            stringBuilder.append(t);
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}
