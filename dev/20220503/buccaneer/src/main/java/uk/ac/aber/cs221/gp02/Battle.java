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
import java.util.Collections;
import java.util.Random;


public class Battle {

    static Player winner; // The winning player in the battle
    static Player loser; // The losing player in the battle
    static boolean haveTreasures; // does the player have treasures

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
        if (p1 == null || p2 == null) {
            System.err.println("Cannot start the battle. A player is null.");
          //  System.exit(1);
        }
        System.out.println("Starting the battle");
        calculateWin(p1, p2);
        if (!draw) {
            setCardsWon(loser.getCards());
        }
        if (loser.getTreasures().size() == 0) {
            haveTreasures = false;
        } else {
            haveTreasures = true;
        }
    }


    /**
     * Method to run when the battle is finished so that the winnings can be transferred.
     */
    public static void finish() {
        if (!draw) {
            if (haveTreasures) {
                System.out.println("Moving treasures from loser to winner.");

                ArrayList<Treasure> lT = new ArrayList<>();
                lT.addAll(loser.getTreasures());
                for (Treasure t : lT) winner.addTreasure(t);
                for (Treasure t : lT) loser.removeTreasure(t);

//                    winner.getTreasures().addAll(loser.getTreasures());
//                    loser.getTreasures().removeAll(loser.getTreasures());
            } else {
                System.out.println("Moving cards from loser to winner.");
                for (CrewCard c : wonCards) winner.addCard(c);
                for (CrewCard c : wonCards) loser.removeCard(c);

//                    winner.getCards().addAll(wonCards);
//                    loser.getCards().removeAll(wonCards);
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
        System.out.println("Calculating who won from " + p1.getName() + " and " + p2.getName());

        if (calcAttackPower(p1) > calcAttackPower(p2)){
            winner = p1;
            loser =  p2;
            System.out.println(p1.getName() + " wins the battle.");
        }
        else if (calcAttackPower(p2) > calcAttackPower(p1)) {
            winner = p2;
            loser = p1;
            System.out.println(p2.getName() + " wins the battle.");
        }
        else {
            draw = true;
            System.out.println("The battle was a draw.");
            int rand = new Random().nextInt(1);
            if (rand == 1) {
                loser = p1;
                winner = p2;
            }
            else {
                loser = p2;
                winner = p1;
            }

        }

    }

    /**
     * Takes the loser's deck of cards, figures out the two lowest and adds them to
     * an arraylist.
     * @param c ArrayList of the losers cards.
     */
    public static void setCardsWon(ArrayList<CrewCard> c) {
        System.out.println("Losers deck of cards: " + c);
        wonCards = new ArrayList<>();

        if (c.size() > 1) {
            Collections.sort(c);
            wonCards.add(c.get(0));
            wonCards.add(c.get(1));
        }
        else if (c.size() == 1) {
            wonCards.add(c.get(0));
        }

        System.out.println(c);


        System.out.println(wonCards);

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


    public static Player getWinner() {
        return winner;
    }

    public static Player getLoser() {
        return loser;
    }

    public static ArrayList<CrewCard> getWonCards() {
        return wonCards;
    }
}
