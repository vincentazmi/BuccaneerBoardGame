import uk.ac.aber.cs221.gp02.Deck;
import uk.ac.aber.cs221.gp02.CrewCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TestDeck {
    private Deck testDeck;

    @BeforeEach
    public void setUp() {
        testDeck = new Deck();
    }

    @Test
    @DisplayName("Empty deck")
    public void testEmptyDeck() {
        Throwable exception = assertThrows(IndexOutOfBoundsException.class, () -> {
            testDeck.getCard();
        });
        assertEquals("Index 0 out of bounds for length 0", exception.getMessage(), "Empty deck should throw indexoutofbounds when get is called");
    }

    @Test
    @DisplayName("Get all cards")
    public void testGetCards() {
        CrewCard card1 = new CrewCard(0, 1);
        CrewCard card2 = new CrewCard(1, 2);
        testDeck.addCard(card1);
        testDeck.addCard(card2);
        ArrayList cards = new ArrayList<>();
        cards.add(card1);
        cards.add(card2);

        assertEquals(cards, testDeck.getCards(), "Deck.getCards() returns wrong output");
    }

    @Test
    @DisplayName("Get cards individually")
    public void testGetCardsIndividually() {
        CrewCard card1 = new CrewCard(0, 1);
        CrewCard card2 = new CrewCard(1, 2);
        testDeck.addCard(card1);
        testDeck.addCard(card2);

        assertEquals(card1, testDeck.getCard(), "First card is not returned with getCard()");
        assertEquals(card2, testDeck.getCard(), "Second card is not returned with second getCard()");

        Throwable exception = assertThrows(IndexOutOfBoundsException.class, () -> {
            testDeck.getCard();
        });
        assertEquals("Index 0 out of bounds for length 0", exception.getMessage(), "Deck not empty after pushing 2 cards and popping 2");
    }

    @Test
    @DisplayName("Test deck shuffle")
    public void testDeckShuffle() {
        testDeck.addCard(new CrewCard(0, 1));
        testDeck.addCard(new CrewCard(0, 1));
        testDeck.addCard(new CrewCard(0, 2));
        testDeck.addCard(new CrewCard(0, 2));
        testDeck.addCard(new CrewCard(0, 3));
        testDeck.addCard(new CrewCard(1, 1));
        testDeck.addCard(new CrewCard(1, 2));
        testDeck.addCard(new CrewCard(1, 3));
        testDeck.addCard(new CrewCard(1, 3));
        testDeck.addCard(new CrewCard(1, 2));

        // copy unshuffled arraylist
        ArrayList unshuffledCards = new ArrayList();
        for (int i = 0; i < 10; i++) {
            unshuffledCards.add(testDeck.getCards().get(i));
        }

        testDeck.shuffleDeck();

        assertFalse(unshuffledCards.equals(testDeck.getCards()), "Deck is not shuffled properly");
    }
}
