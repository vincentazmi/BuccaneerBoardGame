package uk.ac.aber.cs221.gp02;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.*;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

import static uk.ac.aber.cs221.gp02.BuccaneerApplication.*;

public class Game {
    @FXML
    Pane pane;

    @FXML
    Button moveButton, rotateButton, endTurnButton;

    @FXML
    Group buttonsGroup;

    @FXML
    Label shipInfo, battleLabel, objectName, playerLabel, stateLabel;

    @FXML
    TextArea objectDescription;


    //CARDS COUNTER
    @FXML
    Label card3BLabel, card2BLabel, card1BLabel, card3RLabel, card2RLabel, card1RLabel;

    @FXML
    Rectangle shipIcon;


    private Board board;


    private ArrayList<Treasure> treasures;
    private Group treasuresGroup;
    private ImagePattern[] treasuresTextures;

    private Player[] players;
    private String[] nicknames;
    private ImagePattern[] playersTextures;
    private Group playersGroup;

    private Deck deckOfCrewCards;

    private int turn;
    private boolean canMove;
    private boolean canRotate;
    private Player currentPlayer;

    private State state;

    /**
     * This function initialize the game
     *
     * @throws FileNotFoundException because objects constructors loads textures with source path
     */
    public void initialize() throws FileNotFoundException {

        pane.setPrefSize((WIDTH + RIGHT_MARGIN) * TILE_SIZE, HEIGHT * TILE_SIZE);
        board = new Board();

        initializeTreasures();
        placeTreasures(10);
        loadTreasuresTextures();
        drawTreasures();

        createPlayers();
        loadPlayersTextures();
        drawPlayers();

        prepareCards();
        dealCards();
        initializePlayersTurn();

        board.markReachable(currentPlayer, state);
        board.draw();


        pane.getChildren().addAll(board.getBoardGroup(), treasuresGroup, playersGroup);

        state = State.CHOICE;
        canMove = true;
        canRotate = true;
        refreshUI();


        moveButton.setOnAction(e -> {
            if (canMove)
                changeState(State.MOVE);
        });

        rotateButton.setOnAction(e -> {
            if (canRotate)
                changeState(State.ROTATE);
        });

        endTurnButton.setOnAction(e -> {
            turn = (turn + 1) % 4;
            currentPlayer = players[turn];
            canMove = true;
            canRotate = true;
            changeState(State.CHOICE);
        });

        pane.setOnMousePressed(this::onMousePress);
        pane.setOnMouseMoved(this::onMouseMove);



        int[] cc = currentPlayer.cardsDescription();
        System.out.println("Cards summarize: ");
        for (int c : cc)
            System.out.println(c);

        System.out.println();
        System.out.println(deckOfCrewCards.toString());


    }

    private Tile getTile(MouseEvent e) {
        int x, y;
        x = (int) (e.getX() / TILE_SIZE);
        y = (int) (e.getY() / TILE_SIZE);
        if (x < 20 & y < 20)
            return board.getTile(x, y);
        return null;
    }

    private void onMousePress(MouseEvent e) {
        Tile tile = getTile(e);
        if (tile != null) {
            switch (state) {
                case CHOICE -> {
                }
                case ROTATE -> {
                    changeDirection(tile);
                    //  canRotate = false;
                    //      canMove = false;
                    changeState(State.CHOICE);
                }
                case MOVE -> {
                    moveShip(tile);
                    //   canMove = false;
                    changeState(State.CHOICE);
                }
            }
        }
    }

    private void onMouseMove(MouseEvent e) {
        Tile tile = getTile(e);
        if (tile != null) {
            if (tile.getElement() != null) {
                objectName.setText(tile.getElement().getName());
                objectDescription.setText(tile.getElement().toString());
            }
        }
    }


    private void changeState(State newState) {
        state = newState;
        refreshBoard();
        refreshUI();
        System.out.println(state);
        System.out.println();

    }

    private void refreshBoard() {
        board.markReachable(currentPlayer, state);
        board.draw();
        drawPlayers();
    }

    private void refreshUI() {
        playerLabel.setText(currentPlayer.getName());
        stateLabel.setText(state.toString());

        // refresh crew cards counter
        int[] cardsDescription = currentPlayer.cardsDescription();

        card3BLabel.setText(String.valueOf(cardsDescription[0]));
        card2BLabel.setText(String.valueOf(cardsDescription[1]));
        card1BLabel.setText(String.valueOf(cardsDescription[2]));
        card3RLabel.setText(String.valueOf(cardsDescription[3]));
        card2RLabel.setText(String.valueOf(cardsDescription[4]));
        card1RLabel.setText(String.valueOf(cardsDescription[5]));
        ////

        shipIcon.setFill(playersTextures[turn]);

        System.out.println(Arrays.toString(cardsDescription));
        System.out.println(currentPlayer.getCards());
    }


    private void changeDirection(Tile tile) {
        if (tile.getYCor() == currentPlayer.getYCor() - 1 && tile.getXCor() == currentPlayer.getXCor())
            currentPlayer.setDirection(Direction.NORTH);

        else if (tile.getYCor() == currentPlayer.getYCor() - 1 && tile.getXCor() == currentPlayer.getXCor() + 1)
            currentPlayer.setDirection(Direction.NORTHEAST);

        else if (tile.getYCor() == currentPlayer.getYCor() && tile.getXCor() == currentPlayer.getXCor() + 1)
            currentPlayer.setDirection(Direction.EAST);

        else if (tile.getYCor() == currentPlayer.getYCor() + 1 && tile.getXCor() == currentPlayer.getXCor() + 1)
            currentPlayer.setDirection(Direction.SOUTHEAST);

        else if (tile.getYCor() == currentPlayer.getYCor() + 1 && tile.getXCor() == currentPlayer.getXCor())
            currentPlayer.setDirection(Direction.SOUTH);

        else if (tile.getYCor() == currentPlayer.getYCor() + 1 && tile.getXCor() == currentPlayer.getXCor() - 1)
            currentPlayer.setDirection(Direction.SOUTHWEST);

        else if (tile.getYCor() == currentPlayer.getYCor() && tile.getXCor() == currentPlayer.getXCor() - 1)
            currentPlayer.setDirection(Direction.WEST);

        else if (tile.getYCor() == currentPlayer.getYCor() - 1 && tile.getXCor() == currentPlayer.getXCor() - 1)
            currentPlayer.setDirection(Direction.NORTHWEST);

        System.out.println(currentPlayer.getDirection());

    }

    private void moveShip(Tile tile) {
        board.printOnPath();

        System.out.println(tile.getXCor() + " " + tile.getYCor());
        System.out.println(tile.isPath() ? "Can move" : "Nope");
        if (tile.isPath()) {
            currentPlayer.setXCor(tile.getXCor());
            currentPlayer.setYCor(tile.getYCor());
        }
    }

    private void initializeTreasures() {
        treasures = new ArrayList<>();
        int n = 20;

        while (n-- > 0) {
            switch (n % 5) {
                case 0 -> treasures.add(new Treasure("Diamond", Treasures.DIAMOND));
                case 1 -> treasures.add(new Treasure("Rubin", Treasures.RUBIN));
                case 2 -> treasures.add(new Treasure("Gold bar", Treasures.GOLD));
                case 3 -> treasures.add(new Treasure("Pearl", Treasures.PEARL));
                case 4 -> treasures.add(new Treasure("Barrel of rum", Treasures.BARREL));
            }
        }
    }

    private void placeTreasures(int numberOfTreasures) {

    }

    private void loadTreasuresTextures() throws FileNotFoundException {
        treasuresTextures = new ImagePattern[5];

        for (int i = 0; i < 5; i++) {
            treasuresTextures[i] = new ImagePattern(new Image(new FileInputStream(PATH_TO_TEXTURES + "treasure" + (i + 1) + ".png")));
        }
    }

    private void drawTreasures() {
        if (treasuresGroup == null)
            treasuresGroup = new Group();
    }

    private void createPlayers() {
        nicknames = new String[]{
                "John",
                "Paul",
                "Jack",
                "Alex"
        };

        players = new Player[]{
                new Player(5, 5, nicknames[0], 0),
                new Player(15, 5, nicknames[1], 1),
                new Player(5, 15, nicknames[2], 2),
                new Player(15, 15, nicknames[3], 3)
        };
    }

    private void loadPlayersTextures() throws FileNotFoundException {
        playersTextures = new ImagePattern[4];

        for (int i = 0; i < 4; i++) {
            playersTextures[i] = new ImagePattern(new Image(new FileInputStream(PATH_TO_TEXTURES + "Ship" + (i + 1) + ".png")));
        }
    }

    private void drawPlayers() {
        if (playersGroup == null)
            playersGroup = new Group();

        playersGroup.getChildren().clear();

        for (int i = 0; i < 4; i++) {
            Rectangle rec = new Rectangle(TILE_SIZE, TILE_SIZE);
            rec.relocate(players[i].getXCor() * TILE_SIZE, players[i].getYCor() * TILE_SIZE);
            rec.setFill(playersTextures[i]);
            playersGroup.getChildren().add(rec);
        }
    }

    private void prepareCards() {
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

    private void dealCards() {
        for (Player p : players) {
            for (int i = 0; i < 5; i++) {
                p.addCard((CrewCard) deckOfCrewCards.getCard());
            }
        }

        for (Port p: board.getPorts()){
            for (int i = 0; i < 2; i++){
                p.addCard((CrewCard) deckOfCrewCards.getCard());
            }
        }
    }

    private void initializePlayersTurn() {
        turn = 0;
        currentPlayer = players[turn];
    }


}
