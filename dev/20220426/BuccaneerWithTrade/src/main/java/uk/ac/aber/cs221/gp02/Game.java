package uk.ac.aber.cs221.gp02;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;

final public class Game {
    private static String[] nicknames = new String[]{
            "John",
            "Paul",
            "Jack",
            "Alex"
    };

    public static Board board;
    private static ArrayList<Treasure> treasures;
    private static Deck deckOfCrewCards;

    private static ArrayList<Player> players;
    public static int turn;
    private static Player currentPlayer;

    private static State currentState;
    private static boolean canRotate;
    private static boolean canMove;


    private Game() {

    }

    public static void initialize() throws FileNotFoundException {
        board = new Board();
        board.draw();
        initializeTreasures();
        initializePlayers();
        initializeCards();
        initializePlayersTurn();
        dealCards();
        dealTreasures();
        changeState(State.CHOICE);
    }


    public static void changeState(State newState) {
        System.out.println("Move: " + canMove + " Rotate: " + canRotate);

        switch (newState) {


            case CHOICE -> {
                currentState = newState;
            }
            case ROTATE -> {
                if (canRotate && !isInPort())
                    currentState = newState;
            }
            case MOVE -> {
                if (canMove){
                    currentState = isInPort() ?  State.MOVE_ANY : newState;
                }

            }
            case NEXT_TURN -> {
                turn = (turn + 1) % 4;
                currentPlayer = players.get(turn);
                canMove = true;
                canRotate = true;
                currentState = State.CHOICE;
            }
        }
    }

    public static boolean isInPort() {
        return  (board.getTile(currentPlayer.getXCor(), currentPlayer.getYCor()).getElement() instanceof Port);
    }

    public static boolean canTrade() {
        return isInPort() && !(board.getTile(currentPlayer.getXCor(), currentPlayer.getYCor()).getElement().equals(currentPlayer.getHomePort()));
    }

    private static void initializeTreasures() {
        treasures = new ArrayList<>();
        int n = 20;

        while (n-- > 0) {
            switch (n % 5) {
                case 0 -> treasures.add(new Treasure("Diamond", TreasureType.DIAMOND));
                case 1 -> treasures.add(new Treasure("Rubin", TreasureType.RUBIN));
                case 2 -> treasures.add(new Treasure("Gold bar", TreasureType.GOLD));
                case 3 -> treasures.add(new Treasure("Pearl", TreasureType.PEARL));
                case 4 -> treasures.add(new Treasure("Barrel of rum", TreasureType.BARREL));
            }
        }
    }

    private static void initializePlayers() {

        players = new ArrayList<>();
        players.add(new Player(nicknames[0], 0));
        players.add(new Player(nicknames[1], 1));
        players.add(new Player(nicknames[2], 2));
        players.add(new Player(nicknames[3], 3));


        ArrayList<Port> ports = new ArrayList<>();
        ports.add(board.getPorts()[2]);
        ports.add(board.getPorts()[4]);
        ports.add(board.getPorts()[1]);
        ports.add(board.getPorts()[3]);

        Collections.shuffle(players);

        for (int i = 0; i < 4; i++) {
            players.get(i).setHomePort(ports.get(i));
            System.out.println(players.get(i));
        }

        // to fix
        players.get(0).setXCor(6);
        players.get(0).setYCor(19);
        players.get(1).setXCor(19);
        players.get(1).setYCor(13);
        players.get(2).setXCor(13);
        players.get(2).setYCor(0);
        players.get(3).setXCor(0);
        players.get(3).setYCor(6);

    }

    private static void initializeCards() {
        deckOfCrewCards = new Deck();
        for (int c = 0; c < 2; c++) {
            for (int v = 1; v < 4; v++) {
                for (int n = 0; n < 6; n++) {
                    deckOfCrewCards.getCards().add(new CrewCard(c, v));
                }
            }
        }
        deckOfCrewCards.shuffleDeck();
    }

    private static void dealCards() {
        for (Player p : players) {
            for (int i = 0; i < 5; i++) {
                p.addCard((CrewCard) deckOfCrewCards.getCard());
            }
        }
        for (int i = 0; i < 2; i++) {
            board.getPorts()[0].addCard((CrewCard) deckOfCrewCards.getCard());
            board.getPorts()[5].addCard((CrewCard) deckOfCrewCards.getCard());
        }
    }

    private static void dealTreasures() {

        for (int i = 0; i < 6; i += 5) {
            Port p = board.getPorts()[i];

            int value = 0;
            for (CrewCard c : p.getCards())
                value += c.getValue();

            value = 8 - value;

            switch (value) {
                case 6 -> {
                    p.addTreasure(getTreasure(TreasureType.GOLD));
                    p.addTreasure(getTreasure(TreasureType.BARREL));
                }
                case 5 -> p.addTreasure(getTreasure(TreasureType.DIAMOND));
                case 4 -> p.addTreasure(getTreasure(TreasureType.GOLD));
                case 3 -> p.addTreasure(getTreasure(TreasureType.PEARL));
                case 2 -> p.addTreasure(getTreasure(TreasureType.BARREL));
            }

        }
    }

    private static Treasure getTreasure(TreasureType type) {
        Treasure result = null;
        for (int i = 0; i < treasures.size(); i++) {
            if (treasures.get(i).getType() == type) {
                result = treasures.get(i);
                treasures.remove(i);
                break;
            }
        }
        return result;
    }

    private static void initializePlayersTurn() {
        turn = 0;
        currentPlayer = players.get(turn);
        canMove = true;
        canRotate = true;
    }

    public static void onMousePress(Tile tile) {
        switch (currentState) {
            case CHOICE -> {
            }
            case ROTATE -> {
                changeDirection(tile);
                changeState(State.CHOICE);
            }
            case MOVE, MOVE_ANY -> {
                moveShip(tile);
                changeState(State.CHOICE);
            }
        }
    }

    public static void onMouseMove(Tile tile) {

    }

    private static void changeDirection(Tile tile) {
        if(tile.getElement() instanceof Island)
            return;

        canRotate = false;
        canMove = false;

        int dir = tile.getXCor() - currentPlayer.getXCor() + 1 + 3 * (tile.getYCor() - currentPlayer.getYCor() + 1);


        switch (dir) {
            case 0 -> currentPlayer.setDirection(Direction.NORTHWEST);
            case 1 -> currentPlayer.setDirection(Direction.NORTH);
            case 2 -> currentPlayer.setDirection(Direction.NORTHEAST);
            case 3 -> currentPlayer.setDirection(Direction.WEST);
            case 5 -> currentPlayer.setDirection(Direction.EAST);
            case 6 -> currentPlayer.setDirection(Direction.SOUTHWEST);
            case 7 -> currentPlayer.setDirection(Direction.SOUTH);
            case 8 -> currentPlayer.setDirection(Direction.SOUTHEAST);
            default -> {
                canRotate = true;
                canMove = true;
            }
        }

        System.out.println(currentPlayer.getDirection());

    }

    private static void moveShip(Tile tile) {
        if (tile.isPath()) {
            canRotate = true;
            canMove = false;
            currentPlayer.setXCor(tile.getXCor());
            currentPlayer.setYCor(tile.getYCor());
        }
    }


    public static Player getCurrentPlayer() {
        return currentPlayer;
    }

    public static State getCurrentState() {
        return currentState;
    }

    public static ArrayList<Player> getPlayers() {
        return players;
    }

    public static void setNicknames(String[] nicknames) {
        Game.nicknames = nicknames;
    }

}


