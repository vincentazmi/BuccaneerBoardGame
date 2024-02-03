package uk.ac.aber.cs221.gp02;

import java.util.ArrayList;
import java.util.List;

public class PirateIsland extends Island {

    private ArrayList<Treasure> treasures;
    private Deck crewCards;


    public PirateIsland(String name) {
        super(name);
        crewCards = new Deck();
        treasures = new ArrayList<>();
    }

    public void setTreasures(ArrayList<Treasure> treasures) {
        this.treasures = treasures;
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

    public ArrayList<Treasure> getTreasures() {
        return treasures;
    }

    public List<Card> getCrewCards() {
        return crewCards.getCards();
    }

    public void addTreasure(Treasure treasure) {
        treasures.add(treasure);
    }

    public void removeTreasure(Treasure treasure) {
        treasures.remove(treasure);
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

}
