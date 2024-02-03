import uk.ac.aber.cs221.gp02.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.io.FileNotFoundException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TestChanceCard {

    @BeforeEach
    public void setUp() {
        try {
            Game.initialize();
        } catch (FileNotFoundException e) {}
    }

    @Test
    @DisplayName("Test chance card 1")
    public void testChanceCard1 () {
        ChanceCard chanceCard = new ChanceCard(1, "");

        Game.getCurrentPlayer().setCards(new ArrayList<>());

        //left side
        testChanceCard1OneSquare(7, 10);
        int cardAmount = Game.getCurrentPlayer().getCards().size();
        assertEquals(4, cardAmount, "Crew cards not given in chance card 1");

        //bottom side
        testChanceCard1OneSquare(10, 12);

        //top right corner
        testChanceCard1OneSquare(12, 7);


        new ChanceCardManager();
        assertEquals(4, Game.getCurrentPlayer().getCards().size(), "Too many crew cards given in chance card 1");
    }

    private void testChanceCard1OneSquare(int x, int y) {
        ChanceCard chanceCard = new ChanceCard(1, "");

        Game.getCurrentPlayer().setXCor(x);
        Game.getCurrentPlayer().setYCor(y);
        new ChanceCardManager();

        //check x
        if (x == 7) {
            assertEquals(2, Game.getCurrentPlayer().getXCor(), "Chance card 1 is broken on (" + x + ", " + y + ")");
        } else if (x == 12) {
            assertEquals(17, Game.getCurrentPlayer().getXCor(), "Chance card 1 is broken on (" + x + ", " + y + ")");
        } else {
            assertEquals(x, Game.getCurrentPlayer().getXCor(), "Chance card 1 is broken on (" + x + ", " + y + ")");
        }

        //check y
        if (y == 7) {
            assertEquals(2, Game.getCurrentPlayer().getYCor(), "Chance card 1 is broken on (" + x + ", " + y + ")");
        } else if (y == 12) {
            assertEquals(17, Game.getCurrentPlayer().getYCor(), "Chance card 1 is broken on (" + x + ", " + y + ")");
        } else {
            assertEquals(y, Game.getCurrentPlayer().getYCor(), "Chance card 1 is broken on (" + x + ", " + y + ")");
        }
    }

    @Test
    @DisplayName("Test chance card 3")
    public void testChanceCard3() {
        Game.getCurrentPlayer().setCards(new ArrayList<>());
        ChanceCard chanceCard = new ChanceCard(3, "");
        new ChanceCardManager();

        assertEquals(0, Game.getCurrentPlayer().getXCor(), "Chance card 3 doesn't move to mud bay");
        assertEquals(19, Game.getCurrentPlayer().getYCor(), "Chance card 3 doesn't move to mud bay");
        assertEquals(4, Game.getCurrentPlayer().getCards().size(), "Crew cards not given in chance card 3");

        new ChanceCardManager();
        assertEquals(4, Game.getCurrentPlayer().getCards().size(), "Too many crew cards given in chance card 3");
    }

    @Test
    @DisplayName("Test chance card 4")
    public void testChanceCard4() {
        Game.getCurrentPlayer().setCards(new ArrayList<>());
        ChanceCard chanceCard = new ChanceCard(4, "");
        new ChanceCardManager();


        assertEquals(19, Game.getCurrentPlayer().getXCor(), "Chance card 4 doesn't move to cliff creek");
        assertEquals(0, Game.getCurrentPlayer().getYCor(), "Chance card 4 doesn't move to cliff creek");
        assertEquals(4, Game.getCurrentPlayer().getCards().size(), "Crew cards not given in chance card 4");

        new ChanceCardManager();
        assertEquals(4, Game.getCurrentPlayer().getCards().size(), "Too many crew cards given in chance card 4");
    }

    @Test
    @DisplayName("Test chance card 5")
    public void testChanceCard5() {
        Game.getCurrentPlayer().setCards(new ArrayList<>());
        ChanceCard chanceCard = new ChanceCard(5, "");

        //move player away from home port first
        Game.getCurrentPlayer().setXCor(5);
        Game.getCurrentPlayer().setYCor(5);

        new ChanceCardManager();

        assertEquals(6, Game.getCurrentPlayer().getXCor(), "Chance card 5 doesn't move to home port");
        assertEquals(19, Game.getCurrentPlayer().getYCor(), "Chance card 5 doesn't move to home port");
        assertEquals(4, Game.getCurrentPlayer().getCards().size(), "Crew cards not given in chance card 5");

        new ChanceCardManager();
        assertEquals(4, Game.getCurrentPlayer().getCards().size(), "Too many crew cards given in chance card 5");
    }

    @Test
    @DisplayName("Test chance card 8")
    public void testChanceCard8 () {
        Game.getCurrentPlayer().setTreasures(new ArrayList<>());
        Game.getCurrentPlayer().addTreasure(new Treasure("", TreasureType.DIAMOND));
        ChanceCard chanceCard = new ChanceCard(8, "");
        new ChanceCardManager();

        assertEquals(0, Game.getCurrentPlayer().getTreasures().size(), "Treasure not washed away in chance card 8");
    }

    @Test
    @DisplayName("Test chance card 9")
    public void testChanceCard9 () {
        Game.getCurrentPlayer().setTreasures(new ArrayList<>());
        Treasure diamond = new Treasure("", TreasureType.DIAMOND);
        Game.getCurrentPlayer().addTreasure(diamond);
        Game.getCurrentPlayer().addTreasure(new Treasure("", TreasureType.GOLD));

        ChanceCard chanceCard = new ChanceCard(9, "");
        new ChanceCardManager();

        assertEquals(1, Game.getCurrentPlayer().getTreasures().size(), "Treasure not washed away in chance card 9");
        assertEquals(diamond, Game.getCurrentPlayer().getTreasures().get(0), "The most valuable treasure should be washed away in chance card 9");
    }

    @Test
    @DisplayName("Test chance card 10")
    public void testChanceCard10 () {
        Game.getCurrentPlayer().setCards(new ArrayList<>());
        CrewCard highestCard = new CrewCard(0, 3);
        Game.getCurrentPlayer().addCard(highestCard);
        Game.getCurrentPlayer().addCard(new CrewCard(1, 2));

        ChanceCard chanceCard = new ChanceCard(10, "");
        new ChanceCardManager();

        assertEquals(1, Game.getCurrentPlayer().getCards().size(), "Crew card not washed away in chance card 10");
        assertNotEquals(highestCard, Game.getCurrentPlayer().getCards().get(0), "The most valuable card should be washed away in chance card 9");
    }

    @Test
    @DisplayName("Test chance card 16")
    public void testChanceCard16 () {
        Game.getCurrentPlayer().setCards(new ArrayList<>());
        Game.getCurrentPlayer().addCard(new CrewCard(1, 3));
        Game.getCurrentPlayer().addCard(new CrewCard(1, 3));
        Game.getCurrentPlayer().addCard(new CrewCard(1, 3));
        Game.getCurrentPlayer().addCard(new CrewCard(1, 3));
        Game.getCurrentPlayer().addCard(new CrewCard(1, 1));
        Game.getCurrentPlayer().addCard(new CrewCard(1, 2));

        ChanceCard chanceCard = new ChanceCard(16, "");
        new ChanceCardManager();

        assertEquals(10, Game.getCurrentPlayer().getSteps(), "Crew card not removed down to 10 in chance card 16");
        int totalTreasureValue = 0;
        try {
            totalTreasureValue = Game.getCurrentPlayer().getTreasures().get(0).getValue() + Game.getCurrentPlayer().getTreasures().get(1).getValue();
        } catch (Exception e) {}

        assertEquals(7, totalTreasureValue, "Player is not given 7 points of treasure in chance card 17");
    }

    @Test
    @DisplayName("Test chance card 17")
    public void testChanceCard17 () {
        Game.getCurrentPlayer().setCards(new ArrayList<>());
        Game.getCurrentPlayer().addCard(new CrewCard(1, 3));
        Game.getCurrentPlayer().addCard(new CrewCard(1, 3));
        Game.getCurrentPlayer().addCard(new CrewCard(1, 3));
        Game.getCurrentPlayer().addCard(new CrewCard(1, 3));
        Game.getCurrentPlayer().addCard(new CrewCard(1, 1));
        Game.getCurrentPlayer().addCard(new CrewCard(1, 2));

        ChanceCard chanceCard = new ChanceCard(17, "");
        new ChanceCardManager();

        assertEquals(11, Game.getCurrentPlayer().getSteps(), "Crew card not removed down to 11 in chance card 17");
        int totalTreasureValue = 0;
        try {
            totalTreasureValue = Game.getCurrentPlayer().getTreasures().get(0).getValue() + Game.getCurrentPlayer().getTreasures().get(1).getValue();
        } catch (Exception e) {}

        assertEquals(6, totalTreasureValue, "Player is not given 6 points of treasure in chance card 17");
    }

    @Test
    @DisplayName("Test chance card 18")
    public void testChanceCard18 () {
        Game.getCurrentPlayer().setCards(new ArrayList<>());

        ChanceCard chanceCard = new ChanceCard(18, "");
        new ChanceCardManager();

        int totalTreasureValue = 0;
        try {
            totalTreasureValue = Game.getCurrentPlayer().getTreasures().get(0).getValue();
            totalTreasureValue += Game.getCurrentPlayer().getTreasures().get(1).getValue();
        } catch (Exception e) {}

        assertEquals(4, totalTreasureValue, "Player is not given 4 points of treasure in chance card 18");

        assertEquals(2, Game.getCurrentPlayer().getCards().size(), "Crew cards not given to player in chance card 18");
    }

    @Test
    @DisplayName("Test chance card 19")
    public void testChanceCard19 () {
        Game.getCurrentPlayer().setCards(new ArrayList<>());
        Game.getCurrentPlayer().addCard(new CrewCard(1, 3));
        Game.getCurrentPlayer().addCard(new CrewCard(1, 3));
        Game.getCurrentPlayer().addCard(new CrewCard(1, 3));

        ArrayList oldCrewCards = (ArrayList) Game.getCurrentPlayer().getCards().clone();

        ChanceCard chanceCard = new ChanceCard(19, "");
        new ChanceCardManager();

        assertNotEquals(oldCrewCards, Game.getCurrentPlayer().getCards(), "Crew cards not exchanged with treasure island in chance card 19");
    }

    @Test
    @DisplayName("Test chance card 28")
    public void testChanceCard28 () {
        Game.getCurrentPlayer().setCards(new ArrayList<>());

        ChanceCard chanceCard = new ChanceCard(19, "");
        new ChanceCardManager();

        assertEquals(2, Game.getCurrentPlayer().getCards().size(), "Player is not given 2 crew cards in chance card 28");
    }
}
