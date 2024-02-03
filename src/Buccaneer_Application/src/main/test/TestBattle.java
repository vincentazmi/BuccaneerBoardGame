import uk.ac.aber.cs221.gp02.Battle;
import uk.ac.aber.cs221.gp02.CrewCard;
import uk.ac.aber.cs221.gp02.Deck;
import uk.ac.aber.cs221.gp02.Player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TestBattle {
    private Battle testBattle;
    private Player p1, p2;

    @BeforeEach
    public void setUp() {
        testBattle = new Battle();
        p1 = new Player(1, 1);
        p2 = new Player(1, 1);
    }

    @Test
    @DisplayName("p1 > p2")
    public void testP1BeatsP2 () {
        p1.addCard(new CrewCard(0, 3));
        p1.addCard(new CrewCard(0, 3));

        p2.addCard(new CrewCard(0, 3));
        Battle.calculateWin(p1, p2);

        assertEquals(p1, Battle.getWinner(), "Battle doesn't set p1 as winner when p1 wins");
        assertEquals(p2, Battle.getLoser(), "Battle doesn't set p2 as loser when p2 loses");
    }

    @Test
    @DisplayName("p2 > p1")
    public void testP2BeatsP1 () {
        p2.addCard(new CrewCard(0, 3));
        p2.addCard(new CrewCard(0, 3));

        p1.addCard(new CrewCard(0, 3));
        Battle.calculateWin(p1, p2);

        assertEquals(p1, Battle.getLoser(), "Battle doesn't set p1 as loser when p1 loses");
        assertEquals(p2, Battle.getWinner(), "Battle doesn't set p2 as winner when p2 wins");
    }

    @Test
    @DisplayName("p2 loses 2 lowest cards")
    public void testP2Loses2LowestCards () {
        p2.addCard(new CrewCard(0, 1));
        p2.addCard(new CrewCard(1, 1));
        p2.addCard(new CrewCard(0, 3));
        p2.addCard(new CrewCard(1, 2));

        p1.addCard(new CrewCard(0, 3));
        Battle.start(p1, p2);

        ArrayList testWonCards = new ArrayList();
        testWonCards.add(p2.getCards().get(0));
        testWonCards.add(p2.getCards().get(1));

        assertEquals(testWonCards, Battle.getWonCards(), "The two won cards after battle are wrong");
    }

    @Test
    @DisplayName("p2 loses only card")
    public void testP2LosesOnlyCard () {
        p2.addCard(new CrewCard(0, 1));

        p1.addCard(new CrewCard(0, 3));
        Battle.start(p1, p2);

        ArrayList testWonCards = new ArrayList();
        testWonCards.add(p2.getCards().get(0));

        assertEquals(testWonCards, Battle.getWonCards(), "When a player has only one card, won cards after battle are wrong");
    }

    @Test
    @DisplayName("p2 loses nothing")
    public void testP2LosesNothing () {
        p1.addCard(new CrewCard(0, 3));
        Battle.start(p1, p2);

        ArrayList testWonCards = new ArrayList();

        assertEquals(testWonCards, Battle.getWonCards(), "When a player has only one card, won cards after battle are wrong");
    }

    @Test
    @DisplayName("p1 has 10 red, 8 black, p2 has 4 red, 1 black, check calcAttackPower gives 2 for p1, 3 for p2")
    public void testCalculatePower () {
        p1.addCard(new CrewCard(0, 3));
        p1.addCard(new CrewCard(0, 3));
        p1.addCard(new CrewCard(0, 2));

        p1.addCard(new CrewCard(1, 3));
        p1.addCard(new CrewCard(1, 3));
        p1.addCard(new CrewCard(1, 3));
        p1.addCard(new CrewCard(1, 1));


        p2.addCard(new CrewCard(0, 1));
        p2.addCard(new CrewCard(1, 3));
        p2.addCard(new CrewCard(1, 1));

        assertEquals(2, Battle.calcAttackPower(p1), "calcAttackPower gives wrong value with 10 red, 8 black");
        assertEquals(3, Battle.calcAttackPower(p2), "calcAttackPower gives wrong value with 4 red, 1 black");

        Battle.calculateWin(p1, p2);
        assertEquals(p2, Battle.getWinner(), "calculateWin gives wrong winner");
    }
}
