package uk.ac.aber.cs221.gp02;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class ChanceCardManager {
    private Player currentPlayer;



    public ChanceCardManager() {
        currentPlayer = Game.getCurrentPlayer();
    }

    public void applyEffect(ChanceCard c) {
        currentPlayer = Game.getCurrentPlayer();
        Game.createPopup("Chance Card!", c.getDescription(), 1, new ArrayList<>(Arrays.asList("Ok!")), c.getId());
        int id = c.getId();

        switch (id) {
            case 1 -> chanceCard1();
            case 2 -> chanceCard2();
            case 3 -> chanceCard3();
            case 4 -> chanceCard4();
            case 5 -> chanceCard5();
            case 6 -> chanceCard6();
            case 7 -> chanceCard7();
            case 8 -> chanceCard8();
            case 9 -> chanceCard9();
            case 10 -> chanceCard10();
            case 11 -> chanceCard11();
            case 12 -> chanceCard12();
            case 13 -> chanceCard13();
            case 14 -> chanceCard14();
            case 15 -> chanceCard15();
            case 16 -> chanceCard16();
            case 17 -> chanceCard17();
            case 18 -> chanceCard18();
            case 19 -> chanceCard19();
            case 20 -> chanceCard20();
            case 21 -> chanceCard21();
            case 22 -> chanceCard22();
            case 23 -> chanceCard23();
            case 24 -> chanceCard24();
            case 25 -> chanceCard25();
            case 26 -> chanceCard26();
            case 27 -> chanceCard27();
            case 28 -> chanceCard28();
        }

    }

    private ArrayList<CrewCard> getLowestValueCards(ArrayList<CrewCard> cards, int numCards) {
        Collections.sort(cards);
        ArrayList<CrewCard> result = new ArrayList<>();
        for (int i = 0; i < numCards; i++) {
            result.add(cards.get(i));
        }
        return result;

    }

    private ArrayList<CrewCard> getHighestValueCards(ArrayList<CrewCard> cards, int numCards) {
        Collections.sort(cards);
        Collections.reverse(cards);
        ArrayList<CrewCard> result = new ArrayList<>();
        for (int i = 0; i < numCards; i++) {
            result.add(cards.get(i));
        }
        return result;
    }

    private void moveCrewCards(ArrayList<CrewCard> cards, Player fromP, Player toP) {
        for (int i = cards.size() - 1; i >= 0; i--) {
            toP.addCard(cards.get(i));
            fromP.removeCard(cards.get(i));
        }
    }

    private Treasure getLowestValueTreasure(Player p) {
        Collections.sort(p.getTreasures());
        return p.getTreasures().get(0);
    }

    private Treasure getHighestValueTreasure(Player p) {
        Collections.sort(p.getTreasures());
        Collections.reverse(p.getTreasures());
        return p.getTreasures().get(0);
    }

    private void moveTreasure(Treasure t, Player fromP, Player toP) {
        toP.addTreasure(t);
        fromP.removeTreasure(t);
    }

    private void givePlayerNCrewCardsFromPirateIsland(int n) {
        ArrayList<CrewCard> cards = new ArrayList<>();
        for (int i = 0; i < n; i++) cards.add((CrewCard) Game.board.getPirateIsland().getCard());
        for (CrewCard c : cards) currentPlayer.addCard(c);
    }

    private void reducePlayersCrewCardsToValueN(int n) {
        int[] desc = currentPlayer.cardsDescription();

        int sum = (desc[0] + desc[1]) * 3 + (desc[2] + desc[3]) * 2 + (desc[4] + desc[5]);
        if(sum < n){
            int target = n;
            int[] playersHand = new int[]{-1};
            while (playersHand[0] == -1) {
                playersHand = reducingCardsHelper(desc, target);
                target--;
            }
            int[] cardsToBeReturned = new int[6];
            for (int i = 0; i < 6; i++) {
                cardsToBeReturned[i] = desc[i] - playersHand[i];
            }

            currentPlayer.setCards(new ArrayList<CrewCard>());
            for (CrewCard c : fromDescToListCards(playersHand)) {
                currentPlayer.addCard(c);
            }
            ArrayList<CrewCard> cardsToThePirateIsland = fromDescToListCards(cardsToBeReturned);
            for (CrewCard c : cardsToThePirateIsland) {
                Game.board.getPirateIsland().addCard(c);
            }
        }
    }

    private int[] reducingCardsHelper(int[] desc, int n) {
        for (int i = desc[0]; i >= 0; i--) {
            for (int j = desc[0]; j >= 0; j--) {
                for (int k = desc[0]; k >= 0; k--) {
                    for (int l = desc[0]; l >= 0; l--) {
                        for (int m = desc[0]; m >= 0; m--) {
                            for (int o = desc[0]; o >= 0; o--) {
                                if ((i + j) * 3 + (k + l) * 2 + (m + o) == n)
                                    return new int[]{i, j, k, l, m, o};
                            }
                        }
                    }
                }
            }
        }
        return new int[]{-1};
    }

    private ArrayList<CrewCard> fromDescToListCards(int[] desc) {
        ArrayList<CrewCard> result = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            for (int j = desc[i]; j > 0; j--) {
                result.add(new CrewCard(i / 3, i % 3 + 1));
            }
        }
        return result;
    }

    private ArrayList<Treasure> fromDescToListTreasures(int[] desc) {
        ArrayList<Treasure> result = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            for (int j = desc[i]; j > 0; j--) {
                Treasure t;
                switch (i) {
                    case 0 -> t = new Treasure("Diamond", TreasureType.DIAMOND);
                    case 1 -> t = new Treasure("Rubin", TreasureType.RUBIN);
                    case 2 -> t = new Treasure("Gold bar", TreasureType.GOLD);
                    case 3 -> t = new Treasure("Pearl", TreasureType.PEARL);
                    case 4 -> t = new Treasure("Barrel of rum", TreasureType.BARREL);
                    default -> t = new Treasure("Barrel of rum", TreasureType.BARREL);
                }
                result.add(t);
            }
        }
        return result;
    }

    private int[] getTargetValueTreasureCombination(int[] desc, int target, int freeSlots) {
        for (int i = desc[0]; i > -1; i--) {
            for (int j = desc[1]; j > -1; j--) {
                for (int k = desc[2]; k > -1; k--) {
                    for (int l = desc[3]; l > -1; l--) {
                        for (int m = desc[4]; m > -1; m--) {
                            if (i + j + k + l + m <= freeSlots && i * 5 + j * 5 + k * 4 + l * 3 + m * 2 == target) {
                                return new int[]{i, j, k, l, m};
                            }
                        }
                    }
                }
            }
        }
        return new int[]{-1};
    }

    private void givePlayerUpToNTreasure(int treasureValue) {

    }


    private void givePlayerUpToNTreasureOrCrewCards(int treasureValue, int crewCardsCount) {
        String message = "";


        int[] treasureCounts = Game.board.getTreasureIsland().treasureDescription();

        int[] result = new int[]{-1};

        while (result[0] == -1) {
            result = getTargetValueTreasureCombination(treasureCounts, treasureValue, 2 - currentPlayer.getTreasures().size());
            treasureValue--;
        }

        ArrayList<Treasure> treasuresToBeTransferred = fromDescToListTreasures(result);

        message = "You can take the following treasures: " + treasuresToBeTransferred + " or " + crewCardsCount + " crew cards";

        Game.createPopup("Chance Card!", message,
                2, new ArrayList<>(Arrays.asList("Treasure", crewCardsCount + " Crew Cards")));

        if (Game.getPopupButtonID() == 0) {
            for (Treasure t : treasuresToBeTransferred) {
                currentPlayer.addTreasure(Game.board.getTreasureIsland().getTreasure(t.getType()));
            }

        } else
            givePlayerNCrewCardsFromPirateIsland(crewCardsCount);

    }


    /**
     * Your ship is blown 5 leagues (5 squares)
     * off the coast of Treasure Island. If your
     * crew total is 3 or less, take 4 crew cards
     * from Pirate Island. If the square you are
     * blown to is already occupied, move one
     * square further)
     */
    private void chanceCard1() {
        System.out.println("You are blown 5 squares from treasure island");
        int pX = currentPlayer.getXCor();
        int pY = currentPlayer.getYCor();

        int xChange = 0;
        int yChange = 0;
        int distance = 5;
        boolean forward = true;

        // maybe Integer.compare ? dont think that will work.
        if (pX == 7) xChange = -1;
        if (pX == 12) xChange = 1;
        if (pY == 7) yChange = -1;
        if (pY == 12) yChange = 1;

        // DONE make pX + xChange * distance a variable to not type this formula over and over
        int newX = pX + xChange * distance;
        int newY = pY + yChange * distance;

        while (!Game.isAvailable(Game.board.getTile(newX, newY))) {
            if (newX < 19 && newX > 0 && newY < 19 && newY > 0) {
                if (forward) distance++;
                else distance--;
            } else {
                forward = false;
                distance = 4;
            }
            newX = pX + xChange * distance;
            newY = pY + yChange * distance;
        }
        currentPlayer.setXCor(newX);
        currentPlayer.setYCor(newY);
    }


    /**
     * Present this card to any player who must
     * then give you 3 crew cards. This card must
     * be used at once then returned to the
     * Chance card pack.
     */
    private void chanceCard2() {
        System.out.println("Any player gives you 3 lowest value cards");
        ArrayList<String> names = new ArrayList<>();
        ArrayList<Player> players = new ArrayList<>();
        for (Player p : Game.getPlayers()) {
            if (!p.equals(currentPlayer)) {
                names.add(p.getName());
                players.add(p);
            }
        }
        Game.createPopup("Chance Card!", "Pick a player to take 3 of the lowest value crew cards from", 3, names);

        Player pickedPlayer = players.get(Game.getPopupButtonID());
        ArrayList<CrewCard> cards = getLowestValueCards(pickedPlayer.getCards(), 3);
        moveCrewCards(cards, pickedPlayer, currentPlayer);

    }

    /**
     * You are blown to Mud Bay. If your crew
     * total is 3 or less, take 4 crew cards from
     * Pirate Island.
     */
    private void chanceCard3() {
        System.out.println("You are blown to mud bay!");
        System.out.println(currentPlayer.getName());
        currentPlayer.setXCor(0);
        currentPlayer.setYCor(19);

        int sum = 0;
        for (CrewCard c : currentPlayer.getCards()) sum += c.getValue();
        if (sum <= 3) givePlayerNCrewCardsFromPirateIsland(4);
    }

    /**
     * You are blown to Cliff Creek. If your crew
     * total is 3 or less, take 4 crew cards from
     * Pirate Island.
     */
    private void chanceCard4() {
        System.out.println("You are blown to cliff creek!");
        currentPlayer.setXCor(19);
        currentPlayer.setYCor(0);

        int sum = 0;
        for (CrewCard c : currentPlayer.getCards()) sum += c.getValue();
        if (sum <= 3) givePlayerNCrewCardsFromPirateIsland(4);
    }

    /**
     * You are blown to your Home Port. If your
     * crew total is 3 or less, take 4 crew cards
     * from Pirate Island.
     */
    private void chanceCard5() {
        System.out.println("You are blown to your home port!");
        for (int x = 0; x < 20; x++) {
            for (int y = 0; y < 20; y++) {
                Tile t = Game.board.getTile(x, y);
                if (t != null && t.getElement() instanceof Port && t.getElement().equals(currentPlayer.getHomePort())) {
                    currentPlayer.setXCor(x);
                    currentPlayer.setYCor(y);
                }
            }
        }
        int sum = 0;
        for (CrewCard c : currentPlayer.getCards()) sum += c.getValue();
        if (sum <= 3) givePlayerNCrewCardsFromPirateIsland(4);
    }

    /**
     * You are blown to the nearest port in the
     * direction you are heading. If your crew
     * total is 3 or less, take 4 crew cards from
     * Pirate Island.
     */
    private void chanceCard6() {
        System.out.println("You are blown to the nearest port!");
        Tile t = Game.nearestPort(currentPlayer);
        currentPlayer.setXCor(t.getXCor());
        currentPlayer.setYCor(t.getYCor());

        int sum = 0;
        for (CrewCard c : currentPlayer.getCards()) sum += c.getValue();
        if (sum <= 3) givePlayerNCrewCardsFromPirateIsland(4);
    }

    /**
     * One treasure from your ship or 2 crew
     * cards from your hand are lost and washed
     * overboard to the nearest ship. If 2 ships are
     * equidistant from yours you may ignore this
     * instruction.
     */
    private void chanceCard7() {
        System.out.println("One treasure or 2 crew cards are washed overboard to the nearest ship.");
        Player p = Game.nearestShip();
        if (p == null)
            return;

        if (currentPlayer.getTreasures().size() > 0 && p.getTreasures().size() < 2) {
            Treasure t = getLowestValueTreasure(currentPlayer);
            moveTreasure(t, currentPlayer, p);
        } else {
            ArrayList<CrewCard> cards = getLowestValueCards(currentPlayer.getCards(), 2);
            moveCrewCards(cards, currentPlayer, p);
        }
        // DONE move tresures or crew cards from current player to the p
    }

    /**
     * One treasure from your ship or 2 crew
     * cards from your hand are lost and washed
     * overboard to Flat Island.
     */
    private void chanceCard8() {
        if (currentPlayer.getTreasures().size() > 0) {
            Treasure t = getLowestValueTreasure(currentPlayer);
            Game.board.getFlatIsland().addTreasure(t);
            currentPlayer.removeTreasure(t);
        } else {
            ArrayList<CrewCard> cards = getLowestValueCards(currentPlayer.getCards(), 2);
            for (CrewCard c : cards) {
                Game.board.getFlatIsland().addCard(c);
                currentPlayer.removeCard(c);
            }
        }
    }

    /**
     * Your most valuable treasure on board or if
     * no treasure, the best crew card from your
     * hand is washed overboard to Flat Island.
     */
    private void chanceCard9() {
        if (currentPlayer.getTreasures().size() > 0) {
            Treasure t = getHighestValueTreasure(currentPlayer);
            Game.board.getFlatIsland().addTreasure(t);
            currentPlayer.removeTreasure(t);
        } else {
            ArrayList<CrewCard> cards = getHighestValueCards(currentPlayer.getCards(), 1);
            Game.board.getFlatIsland().addCard(cards.get(0));
            currentPlayer.removeCard(cards.get(0));
        }
    }

    /**
     * The best crew card in your hand deserts for
     * Pirate Island. The card must be placed
     * there immediately.
     */
    private void chanceCard10() {
        ArrayList<CrewCard> cards = getHighestValueCards(currentPlayer.getCards(), 1);
        Game.board.getPirateIsland().addCard(cards.get(0));
        currentPlayer.removeCard(cards.get(0));
    }

    /**
     * Take treasure up to 5 in total value, or 2
     * crew cards from Pirate Island.
     */
    private void chanceCard11() {
        String message = "";
        ArrayList<Treasure> treasures = Game.board.getTreasureIsland().getTreasures();

        if (currentPlayer.getTreasures().size() == 2 || treasures.isEmpty()) {
            // 0 spaces; get cards
            message = "Your ship's treasury is full, you will get 2 crew cards from pirate island";
            if (treasures.isEmpty()) message = "There is no treasure at treasure island, please take crew cards";

            Game.createPopup("Chance Card!", message,
                    1, new ArrayList<>(Arrays.asList("Ok!")));
            givePlayerNCrewCardsFromPirateIsland(2);
        } else {
            givePlayerUpToNTreasureOrCrewCards(5, 2);
        }
    }

    /**
     * Take treasure up to 4 in total value, or 2
     * crew cards from Pirate Island.
     */
    private void chanceCard12() {
        String message = "";
        ArrayList<Treasure> treasures = Game.board.getTreasureIsland().getTreasures();

        if (currentPlayer.getTreasures().size() == 2 || treasures.isEmpty()) {
            // 0 spaces; get cards
            message = "Your ship's treasury is full, you will get 2 crew cards from pirate island";
            if (treasures.isEmpty()) message = "There is no treasure at treasure island, please take crew cards";

            Game.createPopup("Chance Card!", message,
                    1, new ArrayList<>(Arrays.asList("Ok!")));
            givePlayerNCrewCardsFromPirateIsland(2);
        } else {
            givePlayerUpToNTreasureOrCrewCards(4, 2);
        }
    }

    /**
     * Take treasure up to 5 in total value, or 2
     * crew cards from Pirate Island.
     */
    private void chanceCard13() {
        String message = "";
        ArrayList<Treasure> treasures = Game.board.getTreasureIsland().getTreasures();

        if (currentPlayer.getTreasures().size() == 2 || treasures.isEmpty()) {
            // 0 spaces; get cards
            message = "Your ship's treasury is full, you will get 2 crew cards from pirate island";
            if (treasures.isEmpty()) message = "There is no treasure at treasure island, please take crew cards";

            Game.createPopup("Chance Card!", message,
                    1, new ArrayList<>(Arrays.asList("Ok!")));
            givePlayerNCrewCardsFromPirateIsland(2);
        } else {
            givePlayerUpToNTreasureOrCrewCards(5, 2);
        }
    }

    /**
     * Take treasure up to 7 in total value, or 3
     * crew cards from Pirate Island.
     */
    private void chanceCard14() {
        String message = "";
        ArrayList<Treasure> treasures = Game.board.getTreasureIsland().getTreasures();

        if (currentPlayer.getTreasures().size() == 2 || treasures.isEmpty()) {
            // 0 spaces; get cards
            message = "Your ship's treasury is full, you will get 2 crew cards from pirate island";
            if (treasures.isEmpty()) message = "There is no treasure at treasure island, please take crew cards";

            Game.createPopup("Chance Card!", message,
                    1, new ArrayList<>(Arrays.asList("Ok!")));
            givePlayerNCrewCardsFromPirateIsland(3);
        } else {
            givePlayerUpToNTreasureOrCrewCards(7, 3);
        }
    }

    /**
     * Take 2 crew cards from Pirate Island.
     */
    private void chanceCard15() {
        givePlayerNCrewCardsFromPirateIsland(2);
    }

    /**
     * Take treasure up to 7 in total value and
     * reduce your ship's crew to 10, by taking
     * crew cards from your hand and placing
     * them on Pirate Island.
     */
    private void chanceCard16() {
        String message = "";
        int treasureValue = 7;
        ArrayList<Treasure> treasures = Game.board.getTreasureIsland().getTreasures();

        reducePlayersCrewCardsToValueN(10);

        if (currentPlayer.getTreasures().size() == 2 || treasures.isEmpty()) {
            // 0 spaces; get cards
            message = "Your ship's treasury is full";
            if (treasures.isEmpty()) message = "There is no treasure at treasure island";
        } else {
            int[] treasureCounts = Game.board.getTreasureIsland().treasureDescription();

            int[] result = new int[]{-1};

            while (result[0] == -1) {
                result = getTargetValueTreasureCombination(treasureCounts, treasureValue, 2 - currentPlayer.getTreasures().size());
                treasureValue--;
            }

            ArrayList<Treasure> treasuresToBeTransferred = fromDescToListTreasures(result);
            for (Treasure t : treasuresToBeTransferred) {
                currentPlayer.addTreasure(Game.board.getTreasureIsland().getTreasure(t.getType()));
            }
            message = "You can take the following treasures: " + treasuresToBeTransferred;

        }
        Game.createPopup("Chance Card!", message,
                1, new ArrayList<>(Arrays.asList("Ok!")));
    }

    /**
     * Take treasure up to 6 in total value and
     * reduce your ship's crew to 11, by taking
     * crew cards from your hand and placing
     * them on Pirate Island.
     */
    private void chanceCard17() {
        String message = "";
        int treasureValue = 6;
        ArrayList<Treasure> treasures = Game.board.getTreasureIsland().getTreasures();
        if (currentPlayer.getSteps() > 11) System.out.println("true");
        else System.out.println("false");
        reducePlayersCrewCardsToValueN(11);

        if (currentPlayer.getTreasures().size() == 2 || treasures.isEmpty()) {
            // 0 spaces; get cards
            message = "Your ship's treasury is full";
            if (treasures.isEmpty()) message = "There is no treasure at treasure island";
        } else {
            int[] treasureCounts = Game.board.getTreasureIsland().treasureDescription();

            int[] result = new int[]{-1};

            while (result[0] == -1) {
                result = getTargetValueTreasureCombination(treasureCounts, treasureValue, 2 - currentPlayer.getTreasures().size());
                treasureValue--;
            }

            ArrayList<Treasure> treasuresToBeTransferred = fromDescToListTreasures(result);
            for (Treasure t : treasuresToBeTransferred) {
                currentPlayer.addTreasure(Game.board.getTreasureIsland().getTreasure(t.getType()));
            }
            message = "You can take the following treasures: " + treasuresToBeTransferred;

        }
        Game.createPopup("Chance Card!", message,
                1, new ArrayList<>(Arrays.asList("Ok!")));

    }

    /**
     * Take treasure up to 4 in total value, and if
     * your crew total is 7 or less, take 2 crew
     * cards from Pirate Island.
     */
    private void chanceCard18() {
        String message = "";
        int treasureValue = 4;
        ArrayList<Treasure> treasures = Game.board.getTreasureIsland().getTreasures();


        if (currentPlayer.getTreasures().size() == 2 || treasures.isEmpty()) {
            // 0 spaces; get cards
            message = "Your ship's treasury is full";
            if (treasures.isEmpty()) message = "There is no treasure at treasure island";
        } else {
            int[] treasureCounts = Game.board.getTreasureIsland().treasureDescription();

            int[] result = new int[]{-1};

            while (result[0] == -1) {
                result = getTargetValueTreasureCombination(treasureCounts, treasureValue, 2 - currentPlayer.getTreasures().size());
                treasureValue--;
            }

            ArrayList<Treasure> treasuresToBeTransferred = fromDescToListTreasures(result);
            for (Treasure t : treasuresToBeTransferred) {
                currentPlayer.addTreasure(Game.board.getTreasureIsland().getTreasure(t.getType()));
            }
            message = "You can take the following treasures: " + treasuresToBeTransferred;

        }
        if (currentPlayer.getSteps() <= 7) {
            givePlayerNCrewCardsFromPirateIsland(2);
            message += " and 2 crew cards";
        }


        Game.createPopup("Chance Card!", message,
                1, new ArrayList<>(Arrays.asList("Ok!")));

    }

    /**
     * Exchange all crew cards in your hand as
     * far as possible for the same number of
     * crew cards from Pirate Island.
     */
    private void chanceCard19() {
        int cardCount = currentPlayer.getCards().size();

        for (int i = cardCount - 1; i >= 0; i--) {
            Game.board.getPirateIsland().addCard(currentPlayer.getCards().get(i));
            currentPlayer.getCards().remove(i);
        }

        for (int i = 0; i < cardCount; i++) {
            currentPlayer.addCard((CrewCard) Game.board.getPirateIsland().getCard());
        }
    }

    /**
     * If the ship of another player is anchored at
     * Treasure Island, exchange 2 of your crew
     * cards with that player. Both turn your cards
     * face down and take 2 cards from each
     * others hands without looking at them. If
     * there is no other player at Treasure Island,
     * place 2 of your crew cards on Pirate Island.
     */
    private void chanceCard20() {
        ArrayList<Player> players = new ArrayList<>();
        ArrayList<String> playerNames = new ArrayList<>();
        for (Player p : Game.getPlayers()) {
            if (!currentPlayer.equals(p) && Game.board.getTile(p.getXCor(), p.getYCor()).getElement() != null && Game.board.getTile(p.getXCor(), p.getYCor()).getElement().getName().equals("Coast of Treasure Island")) {
                players.add(p);
                playerNames.add(p.getName());
            }
        }
        if (players.size() > 0) {
            // u and someone else discard cards to pirate island
            Player pickedPlayer = players.get(0);
            if (players.size() > 1) {
                // you get to choose if more than one
                Game.createPopup("Chance Card!", "Choose player to discard 2 cards", playerNames.size(), playerNames);
                pickedPlayer = players.get(Game.getPopupButtonID());
            }

            if (pickedPlayer.getCards().size() > 1) {
                Collections.shuffle(pickedPlayer.getCards());
                System.out.println("pickedPlayer=" + pickedPlayer.getCards());
                Card c1 = pickedPlayer.getCards().get(0);
                Card c2 = pickedPlayer.getCards().get(1);
                Game.board.getPirateIsland().addCard((CrewCard) c1);
                Game.board.getPirateIsland().addCard((CrewCard) c2);
                pickedPlayer.removeCard(c1);
                pickedPlayer.removeCard(c2);
                System.out.println("pickedPlayer=" + pickedPlayer.getCards());
            } else if (pickedPlayer.getCards().size() == 1) {
                Collections.shuffle(pickedPlayer.getCards());
                System.out.println("pickedPlayer=" + pickedPlayer.getCards());
                Card c1 = pickedPlayer.getCards().get(0);
                Game.board.getPirateIsland().addCard((CrewCard) c1);
                pickedPlayer.removeCard(c1);
                System.out.println("pickedPlayer=" + pickedPlayer.getCards());
            } else {
                System.out.println("picked player had no cards to discard");
            }

        } else {
            System.out.println("you are the only player on treasure island");
        }
        if (currentPlayer.getCards().size() > 1) {
            Collections.shuffle(currentPlayer.getCards());
            System.out.println("currentplayer=" + currentPlayer.getCards());
            Card c1 = currentPlayer.getCards().get(0);
            Card c2 = currentPlayer.getCards().get(1);
            Game.board.getPirateIsland().addCard((CrewCard) c1);
            Game.board.getPirateIsland().addCard((CrewCard) c2);
            currentPlayer.removeCard(c1);
            currentPlayer.removeCard(c2);
            System.out.println("currentplayer=" + currentPlayer.getCards());
        } else if (currentPlayer.getCards().size() == 1) {
            Collections.shuffle(currentPlayer.getCards());
            System.out.println("currentplayer=" + currentPlayer.getCards());
            Card c1 = currentPlayer.getCards().get(0);
            Game.board.getPirateIsland().addCard((CrewCard) c1);
            currentPlayer.removeCard(c1);
            System.out.println("currentplayer=" + currentPlayer.getCards());
        } else {
            System.out.println("you had no cards to discard");
        }

    }

    /**
     * Your ship is blown 3 leagues (3 squares)
     * off the coast of Treasure Island. If your
     * crew total is 3 or less, take 4 crew cards
     * from Pirate Island. If the square you are
     * blown to is already occupied, move one
     * square further)
     */
    private void chanceCard21() {
        System.out.println("You are blown 3 squares from treasure island");
        int pX = currentPlayer.getXCor();
        int pY = currentPlayer.getYCor();

        int xChange = 0;
        int yChange = 0;
        int distance = 3;
        boolean forward = true;

        // maybe Integer.compare ? dont think that will work.
        if (pX == 7) xChange = -1;
        if (pX == 12) xChange = 1;
        if (pY == 7) yChange = -1;
        if (pY == 12) yChange = 1;

        // DONE make pX + xChange * distance a variable to not type this formula over and over
        int newX = pX + xChange * distance;
        int newY = pY + yChange * distance;

        while (!Game.isAvailable(Game.board.getTile(newX, newY))) {
            if (newX < 19 && newX > 0 && newY < 19 && newY > 0) {
                if (forward) distance++;
                else distance--;
            } else {
                forward = false;
                distance = 4;
            }
            newX = pX + xChange * distance;
            newY = pY + yChange * distance;
        }
        currentPlayer.setXCor(newX);
        currentPlayer.setYCor(newY);
    }

    /**
     * Yellow fever! An epidemic of yellow fever
     * strikes all ships and reduces the number of
     * crew. Every player with more than 7 crew
     * cards in their hand must bury the surplus
     * crew cards at once on Pirate Island. Players
     * are at liberty to choose which cards to
     * bury.
     */
    private void chanceCard22() {
        for (Player p : Game.getPlayers()) {
            while (p.getSteps() > 7) {
                Collections.sort(p.getCards());
                p.removeCard(p.getCards().get(0));
            }
        }
    }

    /**
     * Take treasure up to 8 in total value, or 4
     * crew cards from Pirate Island.
     */
    private void chanceCard23() {
        String message = "";
        ArrayList<Treasure> treasures = Game.board.getTreasureIsland().getTreasures();

        if (currentPlayer.getTreasures().size() == 2 || treasures.isEmpty()) {
            // 0 spaces; get cards
            message = "Your ship's treasury is full, you will get 2 crew cards from pirate island";
            if (treasures.isEmpty()) message = "There is no treasure at treasure island, please take crew cards";

            Game.createPopup("Chance Card!", message,
                    1, new ArrayList<>(Arrays.asList("Ok!")));
            givePlayerNCrewCardsFromPirateIsland(4);
        } else {
            givePlayerUpToNTreasureOrCrewCards(8, 4);
        }
    }

    /**
     * Take treasure up to 8 in total value and
     * reduce your ship's crew to 7, by taking
     * crew cards from your hand and placing
     * them on Pirate Island.
     */
    private void chanceCard24() {
        String message = "";
        int treasureValue = 8;
        ArrayList<Treasure> treasures = Game.board.getTreasureIsland().getTreasures();

        reducePlayersCrewCardsToValueN(7);

        if (currentPlayer.getTreasures().size() == 2 || treasures.isEmpty()) {
            // 0 spaces; get cards
            message = "Your ship's treasury is full";
            if (treasures.isEmpty()) message = "There is no treasure at treasure island";
        } else {
            int[] treasureCounts = Game.board.getTreasureIsland().treasureDescription();

            int[] result = new int[]{-1};

            while (result[0] == -1) {
                result = getTargetValueTreasureCombination(treasureCounts, treasureValue, 2 - currentPlayer.getTreasures().size());
                treasureValue--;
            }

            ArrayList<Treasure> treasuresToBeTransferred = fromDescToListTreasures(result);
            for (Treasure t : treasuresToBeTransferred) {
                currentPlayer.addTreasure(Game.board.getTreasureIsland().getTreasure(t.getType()));
            }
            message = "You can take the following treasures: " + treasuresToBeTransferred;

        }
        Game.createPopup("Chance Card!", message,
                1, new ArrayList<>(Arrays.asList("Ok!")));
    }

    /**
     * Take treasure up to 7 in total value, or 3 crew cards from Pirate Island
     */
    private void chanceCard25() {
        String message = "";
        ArrayList<Treasure> treasures = Game.board.getTreasureIsland().getTreasures();

        if (currentPlayer.getTreasures().size() == 2 || treasures.isEmpty()) {
            // 0 spaces; get cards
            message = "Your ship's treasury is full, you will get 2 crew cards from pirate island";
            if (treasures.isEmpty()) message = "There is no treasure at treasure island, please take crew cards";

            Game.createPopup("Chance Card!", message,
                    1, new ArrayList<>(Arrays.asList("Ok!")));
            givePlayerNCrewCardsFromPirateIsland(3);
        } else {
            givePlayerUpToNTreasureOrCrewCards(7, 3);
        }
    }

    /**
     * 2 best crew cards in your hand deserts for Pirate Island\n
     * The card must be placed there immediately
     */
    private void chanceCard26() {
        chanceCard10();
        chanceCard10();
    }

    /**
     * Take treasure up to 5 in total value, or 3
     * crew cards from Pirate Island.
     */
    private void chanceCard27() {
        String message = "";
        ArrayList<Treasure> treasures = Game.board.getTreasureIsland().getTreasures();

        if (currentPlayer.getTreasures().size() == 2 || treasures.isEmpty()) {
            // 0 spaces; get cards
            message = "Your ship's treasury is full, you will get 2 crew cards from pirate island";
            if (treasures.isEmpty()) message = "There is no treasure at treasure island, please take crew cards";

            Game.createPopup("Chance Card!", message,
                    1, new ArrayList<>(Arrays.asList("Ok!")));
            givePlayerNCrewCardsFromPirateIsland(3);
        } else {
            givePlayerUpToNTreasureOrCrewCards(5, 3);
        }
    }

    /**
     * Take 2 crew cards from Pirate Island.
     */
    private void chanceCard28() {
        givePlayerNCrewCardsFromPirateIsland(2);
    }
}
