package uk.ac.aber.cs221.gp02;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private List<Card> cards;

    Deck() {
        cards = new ArrayList<>();
    }

    public void shuffleDeck(){
        Collections.shuffle(cards);
    }

    public List<Card> getCards() {
        return cards;
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public Card getCard() {
        Card card = cards.get(0);
        cards.remove(0);
        return card;
    }

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