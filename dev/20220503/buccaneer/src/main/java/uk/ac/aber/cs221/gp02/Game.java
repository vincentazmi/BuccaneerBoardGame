package uk.ac.aber.cs221.gp02;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.jar.Attributes;
import java.util.stream.Stream;

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
    private static Deck deckOfChanceCards;

    private static ArrayList<Player> players;
    public static int turn;
    private static Player currentPlayer;

    private static State currentState;
    private static boolean canRotate;
    private static boolean canMove;
    private static boolean canTakeCard;

    private static int popupButtonID;

    private static ChanceCardManager chanceCardManager;

    private static JsonManager jsonManager;


    private Game() {

    }

    public static void initialize() throws FileNotFoundException {
        board = new Board();
        board.draw();
        initializeTreasures();
        initializePlayers();
        initializeCards();
        initializePlayersTurn();
        chanceCardManager = new ChanceCardManager();
        dealCards();
        dealTreasures();
        try {
            jsonManager = new JsonManager();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        changeState(State.CHOICE);
        //load();

    }


    public static void changeState(State newState) {


        switch (newState) {

            case CHOICE -> {
                currentState = newState;
                // CHANCE CARD TESTING
                BoardElement boardElement = board.getTile(currentPlayer.getXCor(), currentPlayer.getYCor()).getElement();
                if (boardElement instanceof Coast && canTakeCard) {
                    if (boardElement.getName().equals("Coast of Treasure Island")) {
                        canTakeCard = false;
                     //  ChanceCard c = (ChanceCard) board.getTreasureIsland().getCard();
                        ChanceCard c = new ChanceCard(17, "test");
                        board.getTreasureIsland().addCard(c);
                        chanceCardManager.applyEffect(c);
                    }
                    if (boardElement.getName().equals("Coast of Flat Island")) {
                        flatIsland();
                    }


                }
                unloadShip();
            }
            case ROTATE -> {
                if (canRotate && !isInPort())
                    currentState = newState;
            }
            case MOVE -> {
                if (canMove) {
                    currentState = isInPort() || isShipOnTile(board.getTile(currentPlayer.getXCor(), currentPlayer.getYCor())) != null ? State.MOVE_ANY : newState;
                }

            }
            case NEXT_TURN -> {
                turn = (turn + 1) % 4;
                currentPlayer = players.get(turn);
                canMove = true;
                canRotate = true;
                canTakeCard = true;
                currentState = State.CHOICE;

                try {
                    jsonManager.saveData();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            case MOVE_ANY -> {

            }
            case BATTLE -> {

            }
        }
        System.out.println("legaldirection=" + checkLegalDirection());
        System.out.println("Turn: " + turn);
    }

    public static boolean isInPort() {
        return (board.getTile(currentPlayer.getXCor(), currentPlayer.getYCor()).getElement() instanceof Port);
    }

    // I replaced first expression with isInPort()
    public static boolean isInHomePort() {
        return (isInPort() && currentPlayer.getHomePort().equals(board.getTile(currentPlayer.getXCor(), currentPlayer.getYCor()).getElement()));
    }

    // I replaced second expression with !isInHomePort()
    public static boolean canTrade() {
        return isInPort() && !isInHomePort();
    }

    private static void initializeTreasures() {
        int n = 20;

        while (n-- > 0) {
            switch (n % 5) {
                case 0 -> board.getTreasureIsland().addTreasure(new Treasure("Diamond", TreasureType.DIAMOND));
                case 1 -> board.getTreasureIsland().addTreasure(new Treasure("Rubin", TreasureType.RUBIN));
                case 2 -> board.getTreasureIsland().addTreasure(new Treasure("Gold bar", TreasureType.GOLD));
                case 3 -> board.getTreasureIsland().addTreasure(new Treasure("Pearl", TreasureType.PEARL));
                case 4 -> board.getTreasureIsland().addTreasure(new Treasure("Barrel of rum", TreasureType.BARREL));
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


        int count = 0;
        for (Player p : players) {
            p.setPlayerOrder(count);
            count++;
        }


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

        for(Player p: players)
            System.out.println(p.getColor());

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

        deckOfChanceCards = new Deck();

        String[] chanceCardsDescription = {
                "Your ship is blown 5 leagues (5 squares)\n" +
                        "off the coast of Treasure Island. If your\n" +
                        "crew total is 3 or less, take 4 crew cards\n" +
                        "from Pirate Island. If the square you are\n" +
                        "blown to is already occupied, move one\n" +
                        "square further)",
                "Present this card to any player who must\n" +
                        "then give you 3 crew cards. This card must\n" +
                        "be used at once then returned to the\n" +
                        "Chance card pack.",
                "You are blown to Mud Bay. If your crew\n" +
                        "total is 3 or less, take 4 crew cards from\n" +
                        "Pirate Island.",
                "You are blown to Cliff Creek. If your crew\n" +
                        "total is 3 or less, take 4 crew cards from\n" +
                        "Pirate Island",
                "You are blown to your Home Port. If your\n" +
                        "crew total is 3 or less, take 4 crew cards\n" +
                        "from Pirate Island.",
                "You are blown to the nearest port in the\n" +
                        "direction you are heading. If your crew\n" +
                        "total is 3 or less, take 4 crew cards from\n" +
                        "Pirate Island.",
                "One treasure from your ship or 2 crew\n" +
                        "cards from your hand are lost and washed\n" +
                        "overboard to the nearest ship. If 2 ships are\n" +
                        "equidistant from yours you may ignore this\n" +
                        "instruction.",
                "One treasure from your ship or 2 crew\n" +
                        "cards from your hand are lost and washed\n" +
                        "overboard to Flat Island.",
                "Your most valuable treasure on board or if\n" +
                        "no treasure, the best crew card from your\n" +
                        "hand is washed overboard to Flat Island.",
                "The best crew card in your hand deserts for\n" +
                        "Pirate Island. The card must be placed\n" +
                        "there immediately.",
                "Take treasure up to 5 in total value, or 2\n" +
                        "crew cards from Pirate Island.",
                "Take treasure up to 4 in total value, or 2\n" +
                        "crew cards from Pirate Island.",
                "Take treasure up to 5 in total value, or 2\n" +
                        "crew cards from Pirate Island.",
                "Take treasure up to 7 in total value, or 3\n" +
                        "crew cards from Pirate Island.",
                "Take 2 crew cards from Pirate Island",
                "Take treasure up to 7 in total value and\n" +
                        "reduce your ship's crew to 10, by taking\n" +
                        "crew cards from your hand and placing\n" +
                        "them on Pirate Island.",
                "Take treasure up to 6 in total value and\n" +
                        "reduce your ship's crew to 11, by taking\n" +
                        "crew cards from your hand and placing\n" +
                        "them on Pirate Island.",
                "Take treasure up to 4 in total value, and if\n" +
                        "your crew total is 7 or less, take 2 crew\n" +
                        "cards from Pirate Island.",
                "Exchange all crew cards in your hand as\n" +
                        "far as possible for the same number of\n" +
                        "crew cards from Pirate Island.",
                "If the ship of another player is anchored at\n" +
                        "Treasure Island, exchange 2 of your crew\n" +
                        "cards with that player. Both turn your cards\n" +
                        "face down and take 2 cards from each\n" +
                        "others hands without looking at them. If\n" +
                        "there is no other player at Treasure Island,\n" +
                        "place 2 of your crew cards on Pirate Island.",
                "Your ship is blown 3 leagues (3 squares)\n" +
                        "off the coast of Treasure Island. If your\n" +
                        "crew total is 3 or less, take 4 crew cards\n" +
                        "from Pirate Island. If the square you are\n" +
                        "blown to is already occupied, move one\n" +
                        "square further)",
                "Yellow fever! An epidemic of yellow fever\n" +
                        "strikes all ships and reduces the number of\n" +
                        "crew. Every player with more than 7 crew\n" +
                        "cards in their hand must bury the surplus crew cards at once on Pirate Island. Players\n" +
                        "are at liberty to choose which cards to\n" +
                        "bury",
                "Take treasure up to 8 in total value, or 4\n" +
                        " crew cards from Pirate Island.",
                "Take treasure up to 8 in total value and\n" +
                        "reduce your ship's crew to 7, by taking\n" +
                        "crew cards from your hand and placing\n" +
                        "them on Pirate Island." +
                        "treasure up to value 4 in any port you visit.",
                "Take treasure up to 7 in total value, or 3 crew cards from Pirate Island",
                "2 best crew cards in your hand deserts for Pirate Island\n" +
                        " The card must be placed there immediately",
                "Take treasure up to 5 in total value, or 3\n" +
                        "crew cards from Pirate Island.",
                "Take 2 crew cards from Pirate Island."
        };


//        deckOfChanceCards.addCard(new ChanceCard(12,chanceCardsDescription[10]));
        for (int i = 0; i < 28; i++) {
            deckOfChanceCards.addCard(new ChanceCard(i + 1, chanceCardsDescription[i]));
        }

        for (Card c : deckOfChanceCards.getCards()) {
            board.getTreasureIsland().addCard((ChanceCard) c);
        }
        board.getTreasureIsland().shuffle();

        deckOfChanceCards.shuffleDeck();

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

        CrewCard c;
        for (int i = 0; i < deckOfCrewCards.getCards().size(); i++) {
            c = (CrewCard) deckOfCrewCards.getCard();
            board.getPirateIsland().addCard(c);
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
                    p.addTreasure(board.getTreasureIsland().getTreasure(TreasureType.GOLD));
                    p.addTreasure(board.getTreasureIsland().getTreasure(TreasureType.BARREL));
                }
                case 5 -> p.addTreasure(board.getTreasureIsland().getTreasure(TreasureType.DIAMOND));
                case 4 -> p.addTreasure(board.getTreasureIsland().getTreasure(TreasureType.GOLD));
                case 3 -> p.addTreasure(board.getTreasureIsland().getTreasure(TreasureType.PEARL));
                case 2 -> p.addTreasure(board.getTreasureIsland().getTreasure(TreasureType.BARREL));
            }

        }
    }

    private static void initializePlayersTurn() {
        turn = 3;
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
//                checkBattle(tile);
                moveShip(tile);
                changeState(State.CHOICE);
            }
        }
    }

    public static void onMouseMove(Tile tile) {

    }

    private static Player crossingShip(Tile tile) {
        Player currentPlayer = getCurrentPlayer();
        int x = currentPlayer.getXCor();
        int y = currentPlayer.getYCor();


        int xDirection, yDirection;
        // Figure out direction of X
        xDirection = Integer.compare(tile.getXCor(), x);
        // and y
        yDirection = Integer.compare(tile.getYCor(), y);

        // start one tile away from player to not count their ship
        x += xDirection;
        y += yDirection;

//        System.out.println("tileX= " + tile.getXCor() + " tileY= " + tile.getYCor());
//        System.out.println("playerX= " + currentPlayer.getXCor() + " playerY= " + currentPlayer.getYCor());

        // if end tile reached, break loop
        while (!(x == tile.getXCor() && y == tile.getYCor())) {

//            System.out.println("x= " + x + " y= " + y);

            // if tiles before end tile has ship then return true
            Tile t = board.getTile(x, y);
            if (isShipOnTile(t) != null && !(t.getElement() instanceof Port || t.getElement() instanceof Coast))
                return isShipOnTile(t);
//            System.out.println("no ship");

            // increment direction
            x += xDirection;
            y += yDirection;
        }
//        System.out.println("crossing ship return null");
        return null;
    }

    static Player isShipOnTile(Tile tile) {
        int tileX = tile.getXCor();
        int tileY = tile.getYCor();
        Player result = null;

        for (Player p : players) {
            if (p.getXCor() == tileX && p.getYCor() == tileY && !(p.equals(currentPlayer))) {
                result = p;
            }
        }

        return result;
    }

    /**
     * If tile has no ship, no element but can be coast element returns true
     *
     * @param tile
     * @return
     */
    public static boolean isAvailable(Tile tile) {
        return isShipOnTile(tile) == null && (tile.getElement() == null || tile.getElement() instanceof Coast);
    }

    private static void changeDirection(Tile tile) {
        if (tile.getElement() instanceof Island)
            return;

        int x = tile.getXCor() - currentPlayer.getXCor() + 1;
        int y = tile.getYCor() - currentPlayer.getYCor() + 1;

        if (x < 0 || x > 2 || y < 0 || y > 2)
            return;


        canRotate = false;
        canMove = false;

        int dir = x + 3 * y;


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

            setDirectionMoveAny(tile);

            Player p2 = isShipOnTile(tile);
            if (p2 != null) {
                currentPlayer.setXCor(tile.getXCor());
                currentPlayer.setYCor(tile.getYCor());
                startBattle(currentPlayer, isShipOnTile(tile));
            } else if (crossingShip(tile) != null) {
                p2 = crossingShip(tile);
                currentPlayer.setXCor(p2.getXCor());
                currentPlayer.setYCor(p2.getYCor());


                System.out.println("crossing ship // asking for battle");
                String message = "Does " + p2.getName() + " want to attack passing player " + currentPlayer.getName() + "?";
                createPopup("Do you want to battle?",
                        message,
                        2,
                        new ArrayList<>(Arrays.asList("Yes", "No")));

                if (popupButtonID == 0) startBattle(currentPlayer, p2);
                else {
                    currentPlayer.setXCor(tile.getXCor());
                    currentPlayer.setYCor(tile.getYCor());
                }
            } else {
                currentPlayer.setXCor(tile.getXCor());
                currentPlayer.setYCor(tile.getYCor());
            }
        }
    }

    private static void setDirectionMoveAny(Tile tile) {
        int x = Integer.compare(tile.getXCor(), currentPlayer.getXCor());
        int y = Integer.compare(tile.getYCor(), currentPlayer.getYCor());
        int dir = x + 1 + 3 * (y + 1);

        switch (dir) {
            case 0 -> currentPlayer.setDirection(Direction.NORTHWEST);
            case 1 -> currentPlayer.setDirection(Direction.NORTH);
            case 2 -> currentPlayer.setDirection(Direction.NORTHEAST);
            case 3 -> currentPlayer.setDirection(Direction.WEST);
            case 5 -> currentPlayer.setDirection(Direction.EAST);
            case 6 -> currentPlayer.setDirection(Direction.SOUTHWEST);
            case 7 -> currentPlayer.setDirection(Direction.SOUTH);
            case 8 -> currentPlayer.setDirection(Direction.SOUTHEAST);
        }
    }

    static Tile nearestPort(Player p) {
        int currentX = p.getXCor();
        int currentY = p.getYCor();
        System.out.println(p.getXCor() + " " + p.getYCor() + " " + p.getDirection());
        while (!(currentX == 19 || currentX == 0 || currentY == 19 || currentY == 0)) {
            switch (p.getDirection()) {
                case NORTH -> currentY -= 1;
                case NORTHEAST -> {
                    currentY -= 1;
                    currentX += 1;
                }
                case EAST -> currentX += 1;
                case SOUTHEAST -> {
                    currentY += 1;
                    currentX += 1;
                }
                case SOUTH -> currentY += 1;
                case SOUTHWEST -> {
                    currentY += 1;
                    currentX -= 1;
                }
                case WEST -> currentX -= 1;
            }
        }

        // make list of ports
        ArrayList<Tile> tiles = new ArrayList<>();
        Tile tile;
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                tile = Game.board.getTile(i, j);
                if (tile.getElement() instanceof Port) {
                    tiles.add(tile);
                }
            }
        }
        System.out.println(tiles);

        int pathCost = 9999;
        Tile outTile = tiles.get(0);

        for (Tile t : tiles) {
            int distance = (int) (Math.pow(t.getXCor() - currentX, 2) + Math.pow(t.getYCor() - currentY, 2));
            if (distance < pathCost) {
                pathCost = distance;
                outTile = t;
            }
        }

        outTile.setPath(true);

        return outTile;

    }

    public static Player nearestShip() {
        int path = 9999;
        int startX = currentPlayer.getXCor();
        int startY = currentPlayer.getYCor();
        int goalX = 0;
        int goalY = 0;
        int result = 0;
        Player nearestPlayer = players.get(0);
        for (Player p : players) {
            if (!currentPlayer.equals(p)) {

                goalX = p.getXCor();
                goalY = p.getYCor();

                System.out.println("Current ship");
                System.out.println(startX + " " + startY);
                System.out.println("Other ship");
                System.out.println(goalX + " " + goalY);

                result = aStarPathFinding(startX, startY, goalX, goalY);
                if (result == path) {
                    return null;
                }
                if (result < path) {
                    path = result;
                    nearestPlayer = p;
                }
            }
        }
        return nearestPlayer;

    }

    private static int aStarPathFinding(int startX, int startY, int goalX, int goalY) {
        int currentX = startX;
        int currentY = startY;
        int nodeX = 0;
        int nodeY = 0;

        int result = 0;


        while (!(currentX == goalX && currentY == goalY)) {

            board.getTile(currentX, currentY).setPath(true);
            result++;
            int tmpX = 0, tmpY = 0;
            int pathCost = 9999;
            for (int i = 0; i < 9; i++) {
                nodeX = currentX - 1 + (i % 3);
                nodeY = currentY - 1 + (i / 3);
                if (nodeX < 20 && nodeX >= 0 && nodeY < 20 && nodeY >= 0) {
                    if (i != 4 && !(board.getTile(nodeX, nodeY).getElement() instanceof Island)) {
                        int distance = (int) (Math.pow(nodeX - goalX, 2) + Math.pow(nodeY - goalY, 2));
                        if (distance < pathCost) {
                            pathCost = distance;
                            tmpX = nodeX;
                            tmpY = nodeY;
                        }
                    }
                }
            }
            if (result > 100) {
                System.exit(1);
            }
            currentX = tmpX;
            currentY = tmpY;
            System.out.println(currentX + " " + currentY + " " + goalX + " " + goalY + " " + pathCost);
        }
        return result;
    }

    // hello
    public static void createPopup(String title, String message, int numButtons, ArrayList<String> labels) {
        FXMLLoader loader = new FXMLLoader(GameController.class.getResource("popup.fxml"));
        loader.setController(new PopupController(message, numButtons, labels));
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Buccaneer - " + title);
        stage.initStyle(StageStyle.UNDECORATED);
        try {
            stage.setScene(new Scene(loader.load()));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createPopup(String title, String message, int numButtons, ArrayList<String> labels, int id) {
        FXMLLoader loader = new FXMLLoader(GameController.class.getResource("popup.fxml"));
        loader.setController(new PopupController(message, numButtons, labels, id));
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Buccaneer - " + title);
        stage.initStyle(StageStyle.UNDECORATED);
        try {
            stage.setScene(new Scene(loader.load()));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
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

    public static void setPopupButtonID(int i) {
        popupButtonID = i;
    }

    public static int getPopupButtonID() {
        return popupButtonID;
    }

    public static void startBattle(Player one, Player two) {
        System.out.println("Game.startBattle");
        Battle.start(one, two);
        try {
            Stream<Window> openWindows = Stage.getWindows().stream().filter(Window::isShowing);
            FXMLLoader loader = new FXMLLoader(Game.class.getResource("BattleScreen.fxml"));
            Stage stage = (Stage) openWindows.toArray()[0];
            stage.setTitle("Buccaneer - BattleScreen");
            stage.setScene(new Scene(loader.load()));
//            if (!Battle.draw) {
//
//            }
            canMove = true;
            canRotate = false;
            currentPlayer = Battle.getLoser();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void unloadShip() {
        if (isInHomePort()) {
            for (Treasure t : currentPlayer.getTreasures()) {
                currentPlayer.getHomePort().addTreasure(t);
            }
            currentPlayer.setTreasures(new ArrayList<>());
            checkWin();
        }
    }

    private static void checkWin() {

        int currentValue = 0;
        for (Treasure t : currentPlayer.getHomePort().getTreasure()) {
            currentValue += t.getValue();
        }
        for (Treasure t : currentPlayer.getHomePort().getSafeZone()) {
            currentValue += t.getValue();
        }
        if (currentValue >= 20) {
            try {
                Stream<Window> openWindows = Stage.getWindows().stream().filter(Window::isShowing);
                FXMLLoader loader = new FXMLLoader(Game.class.getResource("endScreen.fxml"));
                Stage stage = (Stage) openWindows.toArray()[0];
                stage.setTitle("Buccaneer - Winner!");
                stage.setScene(new Scene(loader.load()));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // fdsfsa
    private static void flatIsland() {
        FlatIsland flatIsland = board.getFlatIsland();

        int n = flatIsland.getCrewCards().size();

        for (int i = 0; i < n; i++) {
            currentPlayer.addCard((CrewCard) (flatIsland.getCard()));
        }
        if (currentPlayer.getTreasures().size() == 0)
            return;

        if (currentPlayer.getTreasures().size() == 1 && flatIsland.getTreasures().size() > 0) {
            ArrayList<Treasure> treasures = flatIsland.getTreasures();
            Collections.sort(treasures);
            Collections.reverse(treasures);
            Treasure t = treasures.get(0);
            currentPlayer.addTreasure(t);
            flatIsland.removeTreasure(t);
            return;
        }

        if (flatIsland.getTreasures().size() > 1) {
            for (int i = 0; i < 2; i++) {
                Treasure t;
                t = flatIsland.getTreasures().get(0);
                currentPlayer.addTreasure(t);
                flatIsland.removeTreasure(t);
            }
        }


    }

    public static void load() {
        jsonManager.load();
        for(Player p: players)
            System.out.println(p + " " + p.getColor());

        //   initializePlayersTurn();
    }

    public static boolean checkLegalDirection() {
        ArrayList<Direction> dirs = new ArrayList<>(Arrays.asList(
                Direction.NORTHWEST, Direction.NORTH, Direction.NORTHEAST,
                Direction.WEST, Direction.NORTH, Direction.EAST,
                Direction.SOUTHWEST, Direction.SOUTH, Direction.SOUTHEAST));

        int x = currentPlayer.getXCor();
        int y = currentPlayer.getYCor();
        Direction dir = currentPlayer.getDirection();

        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (x + i >= 0 && y + j >= 0 && x + i < 20 && y + j < 20 && !(i == 0 && j == 0))
                    if (!(board.getTile(x + i, y + j).getElement() instanceof Island)) {
                        if (dir == dirs.get(i + 1 + (j + 1) * 3)) return true;
                    }
            }
        }
        return false;
    }

/*    public static void syncCrewCards() {   WIP WIP WIP WIP WIP WIP

        for (Port p : board.getPorts()) {
            if (p.getTreasure().get(0) != null){
                for (Player u : players) {
                    if (p.equals(u.getHomePort())){
                        ArrayList<Treasure> totalTreasures = new ArrayList<>();
                        Arrays.setAll(totalTreasures, i -> u.getTreasures().get(i) + p.getTreasure().get(i));
            }

                }
            }
        }
    }*/

    /*
     * FROM HERE BELOW ONLY USED IN TESTING
     * FROM HERE BELOW ONLY USED IN TESTING
     * FROM HERE BELOW ONLY USED IN TESTING
     * FROM HERE BELOW ONLY USED IN TESTING
     * FROM HERE BELOW ONLY USED IN TESTING
     * FROM HERE BELOW ONLY USED IN TESTING
     * FROM HERE BELOW ONLY USED IN TESTING
     * FROM HERE BELOW ONLY USED IN TESTING
     * FROM HERE BELOW ONLY USED IN TESTING
     * FROM HERE BELOW ONLY USED IN TESTING
     */
    public static String[] getNicknames() {
        return nicknames;
    }

    public static Board getBoard() {
        return board;
    }

    public static void setBoard(Board board) {
        Game.board = board;
    }

    public static ArrayList<Treasure> getTreasures() {
        return treasures;
    }

    public static void setTreasures(ArrayList<Treasure> treasures) {
        Game.treasures = treasures;
    }

    public static Deck getDeckOfCrewCards() {
        return deckOfCrewCards;
    }

    public static void setDeckOfCrewCards(Deck deckOfCrewCards) {
        Game.deckOfCrewCards = deckOfCrewCards;
    }

    public static Deck getDeckOfChanceCards() {
        return deckOfChanceCards;
    }

    public static void setDeckOfChanceCards(Deck deckOfChanceCards) {
        Game.deckOfChanceCards = deckOfChanceCards;
    }

    public static void setPlayers(ArrayList<Player> players) {
        Game.players = players;
    }

    public static int getTurn() {
        return turn;
    }

    public static void setTurn(int turn) {
        Game.turn = turn;
        currentPlayer = players.get(turn);
        canMove = true;
        canRotate = true;
    }

    public static void setCurrentPlayer(Player currentPlayer) {
        Game.currentPlayer = currentPlayer;
    }

    public static void setCurrentState(State currentState) {
        Game.currentState = currentState;
    }

    public static boolean isCanRotate() {
        return canRotate;
    }

    public static void setCanRotate(boolean canRotate) {
        Game.canRotate = canRotate;
    }

    public static boolean isCanMove() {
        return canMove;
    }

    public static void setCanMove(boolean canMove) {
        Game.canMove = canMove;
    }

    public static void moveShipTest(Tile tile) {
        moveShip(tile);
    }
}
