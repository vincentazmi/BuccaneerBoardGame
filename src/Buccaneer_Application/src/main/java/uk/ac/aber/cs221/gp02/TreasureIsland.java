package uk.ac.aber.cs221.gp02;

import java.util.*;
import java.util.stream.IntStream;

public class TreasureIsland extends Island {

    private ArrayList<Treasure> treasures;
    private Deck chanceCards;

    /**
     * Set the treasure on the island
     * @param treasures takes and ArrayList of treasures
     */

    public void setTreasures(ArrayList<Treasure> treasures) {
        this.treasures = treasures;
    }

    /**
     * Set the ChanceCards on the Treasure island
     * @param chanceCards  takes and ArrayList of chance cards
     */

    public void setChanceCards(Deck chanceCards) {
        this.chanceCards = chanceCards;
    }

    /**
     * Constructor for the TreasureIsland
     * @param name Name of the island
     */

    public TreasureIsland(String name) {
        super(name);
        chanceCards = new Deck();
        treasures = new ArrayList<>();

    }

    /**
     * Add a card to the deck of cards on the TreasureIsland
     * @param card instance of chance card
     */

    public void addCard(ChanceCard card) {
        chanceCards.addCard(card);
    }

    /**
     * Get a chance card from the top of the deck on Treasure Island
     * @return ChanceCard
     */

    public Card getCard() {
        return chanceCards.getCard();
    }

    /**
     * Shuffles the cards in the deck on the island
     */

    public void shuffle() {
        chanceCards.shuffleDeck();
    }

    /**
     * Add a treasure to the treasures stored on the island
     * @param treasure the treasure to add
     */

    public void addTreasure(Treasure treasure) {
        treasures.add(treasure);
    }

    /**
     * Get the treasures located on treasure island
     * @return ArrayList of treasures located on the island
     */

    public ArrayList<Treasure> getTreasures(){
        return treasures;
    }

    /**
     * Get a certain from the island, if it is present
     * @param type The type of treasure requested from the island
     * @return returns the type of treasure requested if it is present, returns null otherwise
     */

    public Treasure getTreasure(TreasureType type) {
        Treasure result = null;
        for (int i = 0; i < treasures.size(); i++) {
            if (treasures.get(i).getType() == type) {
                result = treasures.get(i);
                treasures.remove(i);
                break;
            }
        }
        return result;
    }

    /**
     * Get a list of chance cards present on the island in a List format.
     * @return returns a List<ChanceCard>
     */


    public List<Card> getChanceCards() {
        return chanceCards.getCards();
    }

    /**
     * Returns a description of all treasure present on the island
     * @return an Array of integers describing the treasure
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


}
