import uk.ac.aber.cs221.gp02.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TestGame {
    private Player p1;
    private Player p2;
    private Player p3;
    private Player p4;

    @BeforeEach
    public void setUp() {
        try {
            Game.initialize();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        p1 = Game.getPlayers().get(0);
        p2 = Game.getPlayers().get(1);
        p3 = Game.getPlayers().get(2);
        p4 = Game.getPlayers().get(3);
    }

    @Test
    @DisplayName("Test battle after landing on same square")
    public void testDirectBattle () throws IOException {
        //move p1 to (12, 13)
        helperMoveShip(12, 13);
        Game.changeState(State.NEXT_TURN);

        //move p2 to (12, 3)
        helperMoveShip(12, 13);
        Game.setCanMove(true);
        Game.setCurrentPlayer(Battle.getLoser());

        //move loser
        Player loser = Battle.getLoser();
        assertEquals(loser, Game.getCurrentPlayer(), "Loser after battle is not set properly");

        helperMoveShip(11, 12);
        assertEquals(11, Game.getCurrentPlayer().getXCor(), "Loser after battle can't move in every direction");
        assertEquals(12, Game.getCurrentPlayer().getYCor(), "Loser after battle can't move in every direction");


    }

    private void helperMoveShip(int x, int y) throws IOException {
        Game.changeState(State.MOVE);
        Game.getBoard().getTile(x, y).setPath(true);
        try {
            Game.moveShipTest(Game.getBoard().getTile(x, y));
        } catch (Exception e) {}
    }
}
