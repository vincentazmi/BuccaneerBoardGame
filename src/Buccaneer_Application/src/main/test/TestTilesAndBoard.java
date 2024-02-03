import uk.ac.aber.cs221.gp02.Tile;
import uk.ac.aber.cs221.gp02.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TestTilesAndBoard {
    private Board testBoard;

    @BeforeEach
    public void setUp() {
        try {
            testBoard = new Board();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Initial board")
    public void testInitialBoard () {
        //check some random tiles for x and y coordinates
        assertEquals(2, testBoard.getTile(2, 3).getXCor(), "Tiles x coord are wrong");
        assertEquals(3, testBoard.getTile(2, 3).getYCor(), "Tiles y coord are wrong");
    }
}
