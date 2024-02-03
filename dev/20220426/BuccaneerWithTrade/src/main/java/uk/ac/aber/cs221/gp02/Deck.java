package uk.ac.aber.cs221.gp02;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class is an implementation of Deck of cards
 *
 * @version 1.0
 * @author Adrian
 */
public class Deck {
    private List<Card> cards;

    /**
     * This standard constructor initializes the new ArrayList
     */
    Deck() {
        cards = new ArrayList<>();
    }

    /**
     * This method shuffles cards in the list
     */
    public void shuffleDeck(){
        Collections.shuffle(cards);
    }

    /**
     * Just getter
     * @return cards
     */
    public List<Card> getCards() {
        return cards;
    }

    /**
     * This method allows adding cards to the bottom of the Deck
     */
    public void addCard(Card card) {
        cards.add(card);
    }

    /**
     * This method allows getting card from the top
     * @return crewCard
     */
    public Card getCard() {
        Card card = cards.get(0);
        cards.remove(0);
        return card;
    }

    /**
     * Standard toString()
     * @return String representation
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("This deck contains:\n");
        for (Card c : cards) {
            str.append(c);
            str.append("\n");
        }
        return str.toString();
    }
}