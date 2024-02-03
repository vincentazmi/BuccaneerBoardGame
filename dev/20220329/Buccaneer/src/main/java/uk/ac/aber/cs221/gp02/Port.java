package uk.ac.aber.cs221.gp02;

import java.util.ArrayList;

/**
 * This class is a template for every bay in the game
 *
 * @author Adrian
 * @version 1.0
 */
public class Port extends BoardElement {

    private ArrayList<CrewCard> cards;
    private ArrayList<Treasure> treasures;


    /**
     * This is the standard constructor
     *
     * @param name name
     */
    public Port(String name) {
        super(name);
        cards = new ArrayList<>();
        treasures = new ArrayList<>();
    }

    public void addCard(CrewCard card) {
        cards.add(card);
    }

    public void removeCard(Card card) {
        cards.remove(card);
    }


    /**
     * Just getter
     *
     * @return cards
     */
    public ArrayList<CrewCard> getCards() {
        return cards;
    }

    /**
     * Just getter
     *
     * @return treasures
     */
    public ArrayList<Treasure> getTreasure() {
        return treasures;
    }

    /**
     * This method allows adding treasures to the port
     *
     * @param treasure is a treasure to be added
     */
    public void addTreasure(Treasure treasure) {
        treasures.add(treasure);
    }

    /**
     * This method allows removing treasure from the list
     * @param treasure treasure to be removed
     */
    public void removeTreasure(Treasure treasure) {
        cards.remove(treasure);
    }


    /**
     * This method returns array of ints which represents number of each kind of card
     * @return int representation of cards in the list
     */
    public int[] cardsDescription() {
        int[] desc = new int[6];

        for (CrewCard c : cards)
            desc[c.getColor() * 3 + c.getValue() - 1]++;

        return desc;
    }

    /**
     * This method returns array of ints which represents number of each kind of treasure
     * @return int representation of treasures in the list
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
     * Just getter
     * @return name
     */
    @Override
    public String getName() {
        return super.getName();
    }

    /**
     * Just toString()
     * @return String representation
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("It contains: ");
        for (CrewCard c : cards) {
            stringBuilder.append("\n");
            stringBuilder.append(c);
        }
        for (Treasure t : treasures) {
            stringBuilder.append("\n");
            stringBuilder.append(t);
        }

        return stringBuilder.toString();
    }


}
