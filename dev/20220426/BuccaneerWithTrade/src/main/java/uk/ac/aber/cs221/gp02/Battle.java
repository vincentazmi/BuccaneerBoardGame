/**
 * ##################################################################################
 *  Class for battles between two players
 * To use: Call Battle.start() with the two players, then change to the battle screen
 *
 * @Author xad1
 *
 * ##################################################################################
 */

package uk.ac.aber.cs221.gp02;
import java.util.ArrayList;


public class Battle {

    static Player winner; // The winning player in the battle
    static Player loser; // The losing player in the battle

    static ArrayList<CrewCard> wonCards; // An arraylist to hold the loser's two lowest crew cards
    static boolean draw;


    public Battle() {
    }


    /**
     * Method which starts a battle between two players
     * @param p1 One of the players
     * @param p2 The other player
     */
    public static void start(Player p1, Player p2){
        System.out.println("Starting the battle");
        calculateWin(p1, p2);
        if (!draw) {
            setCardsWon(loser.getCards());
        }
    }


    /**
     * Method to run when the battle is finished so that the winnings can be transferred.
     * @param tc int so that we know whether to move cards or treasures
     */
    public static void finish(int tc) {
        if (!draw) {
            switch (tc) {
                case 0:
                    winner.getTreasures().addAll(loser.getTreasures());
                    loser.getTreasures().removeAll(loser.getTreasures());
                    break;
                case 1:
                    winner.getCards().addAll(wonCards);
                    loser.getCards().removeAll(wonCards);
                    break;
            }
        }
        System.out.println("Continue the game");
    }


    /**
     * Take two players. Set the winner and loser based on their attack power.
     * @param p1 One of the players
     * @param p2 The other player
     */
    public static void calculateWin(Player p1, Player p2) {

        if (calcAttackPower(p1) > calcAttackPower(p2)){
            winner = p1;
            loser =  p2;
        }
        else if (calcAttackPower(p2) > calcAttackPower(p1)) {
            winner = p2;
            loser = p1;
        }
        else {
            draw = true;
        }
    }

    /**
     * Takes the loser's deck of cards, figures out the two lowest and adds them to
     * an arraylist.
     * @param c ArrayList of the losers cards.
     */
    public static void setCardsWon(ArrayList<CrewCard> c) {
        wonCards = new ArrayList<>();
        CrewCard currentLowest = null;
        for (int i1 = 0; i1 < 2; i1++) {
            for (int i2 = 0; i2 < c.size(); i2++){
                if (i2 == 0) {
                    currentLowest = c.get(i2);
                }
                else if (c.get(i2 - 1).getValue() > c.get(i2).getValue()) {
                    currentLowest = c.get(i2);
                }
            }
            wonCards.add(currentLowest);

        }

    }


    /**
     * Calculate the attack power of a player
     * @param p The player to calculate
     * @return The player's attack power as an integer
     */
    public static int calcAttackPower(Player p) {
        int powerRed = 0;
        int powerBlack = 0;
        int power = 0;
        ArrayList<CrewCard> deck = p.getCards();

        for (int i = 0; i < deck.size(); i++) {
            CrewCard c = deck.get(i);
            switch (c.getColor()){
                case 0:
                    powerRed += c.getValue();
                    break;
                case 1:
                    powerBlack += c.getValue();
                    break;
            }
        }

        if (powerRed >= powerBlack) {
            power = powerRed - powerBlack;
        }
        else {
            power = powerBlack - powerRed;
        }
        System.out.println(p.getName() + " " + power);
        return power;
    }


}
