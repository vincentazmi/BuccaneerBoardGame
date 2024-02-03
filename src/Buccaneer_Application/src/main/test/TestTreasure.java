import uk.ac.aber.cs221.gp02.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

public class TestTreasure {
    private Treasure testTreasure;
    private Treasure testTreasure2;

    @Test
    @DisplayName("All types and values")
    public void testTypesAndValues () {
        testTreasure = new Treasure("", TreasureType.DIAMOND);
        assertEquals(5, testTreasure.getValue(), "Diamond should be worth 5");
        assertEquals(TreasureType.DIAMOND, testTreasure.getType(), "Diamond should be type DIAMOND");

        testTreasure = new Treasure("", TreasureType.RUBIN);
        assertEquals(5, testTreasure.getValue(), "Ruby should be worth 5");
        assertEquals(TreasureType.RUBIN, testTreasure.getType(), "Diamond should be type RUBIN");

        testTreasure = new Treasure("", TreasureType.GOLD);
        assertEquals(4, testTreasure.getValue(), "Gold should be worth 4");
        assertEquals(TreasureType.GOLD, testTreasure.getType(), "Diamond should be type GOLD");

        testTreasure = new Treasure("", TreasureType.PEARL);
        assertEquals(3, testTreasure.getValue(), "Pearl should be worth 3");
        assertEquals(TreasureType.PEARL, testTreasure.getType(), "Diamond should be type PEARL");

        testTreasure = new Treasure("", TreasureType.BARREL);
        assertEquals(2, testTreasure.getValue(), "Barrel should be worth 2");
        assertEquals(TreasureType.BARREL, testTreasure.getType(), "Diamond should be type BARREL");
    }

    @Test
    @DisplayName("Comparing some treasures")
    public void testComparingTreasures () {
        testTreasure = new Treasure("", TreasureType.DIAMOND);
        testTreasure2 = new Treasure("", TreasureType.RUBIN);
        assertEquals(0, testTreasure.compareTo(testTreasure2), "Diamond should equal ruby in value");

        testTreasure = new Treasure("", TreasureType.BARREL);
        testTreasure2 = new Treasure("", TreasureType.BARREL);
        assertEquals(0, testTreasure.compareTo(testTreasure2), "Barrel should equal itself in value");

        testTreasure = new Treasure("", TreasureType.PEARL);
        testTreasure2 = new Treasure("", TreasureType.GOLD);
        assertEquals(-1, testTreasure.compareTo(testTreasure2), "Pearl should be less than gold in value");

        testTreasure = new Treasure("", TreasureType.DIAMOND);
        testTreasure2 = new Treasure("", TreasureType.BARREL);
        assertEquals(1, testTreasure.compareTo(testTreasure2), "Diamond should be greater than barrel in value");


    }

    @Test
    @DisplayName("Treasure Count")
    public void treasureCount(){
        try {
            Game.initialize();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        int n = 0;
        for (Port p : Game.getBoard().getPorts()) {
            n = n + p.getTreasure().size();
        }
        n = n + Game.getBoard().getTreasureIsland().getTreasures().size();
        System.out.println("All Treasure Present");

        assertEquals(20, n, "Error, less than 20 treasures.");
    }
    /*
     * Type DIAMOND, check value 5
     * Type RUBIN, check value 5
     * Type GOLD, check value 4
     * Type PEARL, check value 3
     * Type BARREL, check value 2
     *
     * Compare some of them
     */
}