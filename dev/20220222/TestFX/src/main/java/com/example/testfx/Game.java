package com.example.testfx;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

import static com.example.testfx.BuccaneerApplication.*;

public class Game {
    @FXML
    Pane pane;

    @FXML
    Button moveButton, rotateButton, endTurnButton;

    @FXML
    Group buttonsGroup;

    @FXML
    Label shipInfo, battleLabel;

    private Board board;

    private Ship[] ships;
    private Ship ship;

    private Port[] ports;
    private Bay[] bays;
    private Island[] islands;
    private Treasure[] treasures;

    private ArrayList<BoardElem> boardElements;

    private int turn;


    /**
     * This function initialize the game
     *
     * @throws FileNotFoundException because objects constructors loads textures with source path
     */
    public void initialize() throws FileNotFoundException {

        pane.setPrefSize((WIDTH + 2 * LEFT_OFFSET) * TILE_SIZE, HEIGHT * TILE_SIZE);
        setPieces();

        turn = 0;
        ship = ships[turn];

        shipInfo.setText(ship.getCords());
        board.paintReachable(ship);

        pane.setOnMousePressed(e -> {
            int x, y;
            x = (int) (e.getX() / TILE_SIZE);
            y = (int) (e.getY() / TILE_SIZE);

            if (x < 20 && !board.getPressedTile(x, y).isIsland())
                ship.move(x, y);
            update();

            //just for debugging
//            if (x < 20) {
//                Tile tile = board.getPressedTile(x, y);
//                tile.setColor(tile.isIsland() ? 4 : 5);
//                System.out.println(ship.getXCor() + " " + ship.getYCor() + " " + ship.getNumOfSteps() + " works");
//            }


        });
        // for debugging
        pane.setOnMouseMoved(e -> shipInfo.setText((int) (e.getX() < 20 * TILE_SIZE ? e.getX() / TILE_SIZE + 1 : 20) + " " + (20 - (int) (e.getY() / TILE_SIZE))));

        rotateButton.setOnAction(e -> buttonsActions(1));
        endTurnButton.setOnAction(e -> buttonsActions(2));

    }

    /**
     * This method puts all the pieces on the board
     *
     * @throws FileNotFoundException because objects constructors loads textures with source path
     */
    private void setPieces() throws FileNotFoundException {
        board = new Board();
        pane.getChildren().add(board.getTilesGroup());

        boardElements = new ArrayList<>();


        ports = new Port[]{
                new Port(20, 14, 1),
                new Port(14, 20, 2),
                new Port(7, 1, 3),
                new Port(1, 14, 4),
                new Port(20, 7, 5),
                new Port(1, 7, 6),
        };

        for (Port p : ports) {
            pane.getChildren().add(p);
            boardElements.add(p);
        }


        bays = new Bay[]{
                new Bay(20, 1, 1),
                new Bay(20, 20, 2),
                new Bay(1, 1, 3),
        };

        for (Bay b : bays) {
            pane.getChildren().add(b);
            boardElements.add(b);
        }


        islands = new Island[]{
                new Island(2, 19, 3, 4, 1),
                new Island(17, 5, 3, 4, 2),
                new Island(9, 12, 4, 4, 3),
        };

        for (Island i : islands) {
            pane.getChildren().add(i);
            boardElements.add(i);
        }


        ships = new Ship[]{
                new Ship(5, 5, 0),
                new Ship(15, 5, 1),
                new Ship(5, 15, 2),
                new Ship(15, 15, 3)
        };

        for (Ship s : ships) {
            pane.getChildren().add(s);
            boardElements.add(s);
        }

        int x, y, k;
        Random random = new Random();
        treasures = new Treasure[5];

        for (int i = 0; i < 5; i++) {
            Treasure treasure;
            do {
                x = random.nextInt(19);
                y = random.nextInt(19);
                k = 1 + random.nextInt(2);
                treasure = new Treasure(x,y,k);
            } while (isInList(boardElements, treasure) && !board.getPressedTile(x,y).isIsland());
            treasures[i] = treasure;
            boardElements.add(treasure);
        }
        for (Treasure t: treasures){
            pane.getChildren().add(t);
        }

//        Deck deck = new Deck(0,0);
//        deck.setPrefSize((WIDTH + 2 * LEFT_OFFSET) * TILE_SIZE, HEIGHT * TILE_SIZE);
//        deck.printDeck(100, 100);
//        pane.getChildren().add(deck);


    }

    private boolean isInList(ArrayList<BoardElem> boardElements, Treasure treasure) {
        for(BoardElem b: boardElements){
            if(b.getXCor() == treasure.getXCor() && b.getYCor() == treasure.getYCor())
                return true;
        }
        return false;
    }

    /**
     * This method checks if 2 ships collided and allows user entering battle mode
     */
    private void checkCollision() {
        for (Ship s : ships) {
            if (s.getXCor() == ship.getXCor() && s.getYCor() == ship.getYCor() && !(s.equals(ship))) {
                s.battleSize(1);
                ship.battleSize(2);
                battleLabel.setVisible(true);
                break;
            } else {
                s.resetSize();
                battleLabel.setVisible(false);
            }
        }

        for(Treasure t : treasures){
            System.out.println(ship.getXCor() + " " + ship.getYCor() + " " + t.getXCor() + " " + t.getYCor());
            if(ship.getXCor() + 1 == t.getXCor() && 20 - ship.getYCor() == t.getYCor()){
                System.out.println("Diamond!");
                t.setVisible(false);
                return;
            }

        }
    }

    /**
     * This method upload information on interface
     */
    private void update() {
        checkCollision();
        shipInfo.setText(ship.getCords());
        board.paintReachable(ship);
    }

    /**
     * This method handle buttons events
     *
     * @param buttonID is id of the button
     */
    private void buttonsActions(int buttonID) {
        switch (buttonID) {
            case 0 -> ship.move(0, 0);
            case 1 -> ship.changeDirection();
            case 2 -> {
                ship.resetSteps();
                turn = (turn + 1) % 4;
                ship = ships[turn];
            }
        }
        update();
    }

}
