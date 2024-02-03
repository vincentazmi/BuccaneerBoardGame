import uk.ac.aber.cs221.gp02.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TestPort {
    private Port testPort;

    @BeforeEach
    public void setUp() {
        testPort = new Port("asd");
    }

    @Test
    @DisplayName("test the crewcards")
    public void testCrewCards() {
        ArrayList testCards = new ArrayList();
        assertEquals(testCards, testPort.getCards(), "Initial treasures for player are wrong");

        CrewCard currentCard = new CrewCard(0, 1);
        testPort.addCard(currentCard);
        testCards.add(currentCard);
        assertEquals(testCards, testPort.getCards(), "Treasures are wrong after adding one");

        testPort.removeCard(currentCard);
        testCards.remove(currentCard);
        assertEquals(testCards, testPort.getCards(), "Treasures are wrong after removing one");
    }

    @Test
    @DisplayName("test the treasures")
    public void testTreasures() {
        ArrayList testTreasures = new ArrayList();
        assertEquals(testTreasures, testPort.getTreasure(), "Initial treasures for player are wrong");

        Treasure currentTreasure = new Treasure("gold", TreasureType.GOLD);
        testPort.addTreasure(currentTreasure);
        testTreasures.add(currentTreasure);
        assertEquals(testTreasures, testPort.getTreasure(), "Treasures are wrong after adding one");

        testPort.removeTreasure(currentTreasure);
        testTreasures.remove(currentTreasure);
        assertEquals(testTreasures, testPort.getTreasure(), "Treasures are wrong after removing one");
    }

    @Test
    @DisplayName("Dealing Cards Test")
    public void cardTest() {
        try {
            Game.initialize();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        assertEquals(2, Game.getBoard().getPorts()[0].getCards().size(), "Not 2 cards in trading port");
        assertEquals(2, Game.getBoard().getPorts()[5].getCards().size(), "Not 2 cards in trading port");

    }

    @Test
    @DisplayName("Port Treasure Test")
    public void portTreasureValue(){
        try {
            Game.initialize();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        int n = 0;
        for (CrewCard c : Game.getBoard().getPorts()[0].getCards()){
            n = n + c.getValue();
        }
        for (Treasure t : Game.getBoard().getPorts()[0].getTreasure()){
            n = n + t.getValue();
        }
        assertEquals(8, n, "The value of the port is not 8");

        n = 0;
        for (CrewCard c : Game.getBoard().getPorts()[5].getCards()){
            n = n + c.getValue();
        }
        for (Treasure t : Game.getBoard().getPorts()[5].getTreasure()){
            n = n + t.getValue();
        }
        assertEquals(8, n, "The value of the port is not 8");

    }
    /*
     * Add and remove some crewcards and check it's the correct value
     * Add and remove some treasures and check it's the correct value
     */
}
