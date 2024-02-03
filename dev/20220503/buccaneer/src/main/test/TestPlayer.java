import uk.ac.aber.cs221.gp02.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TestPlayer {
    private Player testPlayer;

    @BeforeEach
    public void setUp() {
        testPlayer = new Player(1, 1);
    }

    @Test
    @DisplayName("test the crewcards")
    public void testCrewCards () {
        assertEquals(1, testPlayer.getSteps(), "Calculation of steps is wrong");

        testPlayer.addCard(new CrewCard(0, 2));
        assertEquals(2, testPlayer.getSteps(), "Calculation of steps is wrong after adding a card");

        testPlayer.addCard(new CrewCard(1, 3));
        assertEquals(5, testPlayer.getSteps(), "Calculation of steps is wrong after adding a card of opposite color");

        testPlayer.getCard();
        assertEquals(3, testPlayer.getSteps(), "Calculation of steps is wrong after removing a card");
    }

    @Test
    @DisplayName("test the treasures")
    public void testTreasures () {
        ArrayList testTreasures = new ArrayList();
        assertEquals(testTreasures, testPlayer.getTreasures(), "Initial treasures for player are wrong");

        Treasure currentTreasure = new Treasure("gold", TreasureType.GOLD);
        testPlayer.addTreasure(currentTreasure);
        testTreasures.add(currentTreasure);
        assertEquals(testTreasures, testPlayer.getTreasures(), "Treasures are wrong after adding one");

        testPlayer.removeTreasure(currentTreasure);
        testTreasures.remove(currentTreasure);
        assertEquals(testTreasures, testPlayer.getTreasures(), "Treasures are wrong after removing one");
    }

    @Test
    @DisplayName("test 3 treasures not possible")
    public void testThreeTreasures () {
        Treasure t1, t2, t3;
        t1 = new Treasure("diamond", TreasureType.DIAMOND);
        t2 = new Treasure("gold", TreasureType.GOLD);
        t3 = new Treasure("pearl", TreasureType.PEARL);

        ArrayList testTreasures = new ArrayList();
        testTreasures.add(t1);
        testTreasures.add(t2);

        testPlayer.addTreasure(new Treasure("diamond", TreasureType.DIAMOND));
        testPlayer.addTreasure(new Treasure("gold", TreasureType.GOLD));
        testPlayer.addTreasure(new Treasure("pearl", TreasureType.PEARL));
        assertEquals(testTreasures, testPlayer.getTreasures(), "Player can get more than 2 treasures");
    }

    @Test
    @DisplayName("5 card Test")
    public void playerCardSetup(){
        try {
            Game.initialize();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        for(Player p : Game.getPlayers()){
            assertEquals(5, p.getCards().size(), "Player hand does not have 5 cards");
        }
    }
    /*
     * Add and remove some crewcards and check it's the correct value
     * Add and remove some treasures and check it's the correct value
     * Add 3 treasures, check third can't be added
     *
     */
}
