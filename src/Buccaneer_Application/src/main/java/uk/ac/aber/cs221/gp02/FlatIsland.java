package uk.ac.aber.cs221.gp02;

import java.util.*;

public class FlatIsland extends Island {

    private ArrayList<Treasure> treasures;
    private Deck crewCards;

    public void setCrewCards(Deck crewCards) {
        this.crewCards = crewCards;
    }

    /**
     * Just a constructor
     * @param name name of the island
     */
    public FlatIsland(String name) {
        super(name);
        treasures = new ArrayList<>();
        crewCards = new Deck();
    }

    /**
     * It adds card to the deck
     * @param card card
     */
    public void addCard(CrewCard card) {
        crewCards.addCard(card);
    }

    /**
     * It removes card from the deck
     * @return card
     */
    public Card getCard() {
        return crewCards.getCard();
    }

    /**
     * It returns summary of crew cards
     * @return array of ints
     */
    public int[] cardsDescription() {
        int[] desc = new int[6];

        System.out.println(crewCards);
        for (Card c : crewCards.getCards())
            desc[((CrewCard) (c)).getColor() * 3 + 3 - ((CrewCard) (c)).getValue()]++;

        return desc;
    }

    /**
     * It adds treasure to the list
     * @param treasure treasure
     */
    public void addTreasure(Treasure treasure) {
        treasures.add(treasure);
    }

    /**
     * It removes treasure from the list
     * @param treasure treasure
     */
    public void removeTreasure(Treasure treasure) {
        treasures.remove(treasure);
    }

    /**
     * It returns list of treasures
     * @return list of treasures
     */
    public ArrayList<Treasure> getTreasures() {
        return treasures;
    }

    /**
     * It returns list of cards
     * @return list of cards
     */
    public List<Card> getCrewCards() {
        return crewCards.getCards();
    }

    /**
     * It returns summary of the treasures
     * @return summary of the treasures
     */
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

    /**
     * Sets list of treasures to the new one
     * @param treasures list of treasures
     */
    public void setTreasures(ArrayList<Treasure> treasures) {
        this.treasures = treasures;
    }

    /**
     * Just a to string
     * @return
     */
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
