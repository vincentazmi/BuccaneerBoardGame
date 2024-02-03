package uk.ac.aber.cs221.gp02;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.*;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

import static uk.ac.aber.cs221.gp02.BuccaneerApplication.*;

public class Game {
    @FXML
    Pane pane;

    @FXML
    Button moveButton, rotateButton, endTurnButton;

    @FXML
    Group buttonsGroup;

    @FXML
    Label shipInfo, battleLabel, objectName, treasureName, treasureValue;

    @FXML
    TextArea objectDescription;


    private Board board;
    private ArrayList<Treasure> treasures;
    private Group treasuresGroup;
    private ImagePattern[] treasuresTextures;


    /**
     * This function initialize the game
     *
     * @throws FileNotFoundException because objects constructors loads textures with source path
     */
    public void initialize() throws FileNotFoundException {

        pane.setPrefSize((WIDTH + RIGHT_MARGIN) * TILE_SIZE, HEIGHT * TILE_SIZE);
        board = new Board();

        //   setBackground();
        initializeTreasures();
        placeTreasures(10);
        loadTreasuresTextures();
        drawTreasures();


        pane.getChildren().addAll(board.getBoardGroup(), treasuresGroup);

        pane.setOnMouseMoved(e -> {
            Tile tile;
            int x, y;
            x = (int) (e.getX() / TILE_SIZE);
            y = (int) (e.getY() / TILE_SIZE);
            if (x < 20 & y < 20) {
                tile = board.getTile(x, y);
                if (tile.getElement() != null) {
                    objectName.setText(tile.getElement().getName());
                    objectDescription.setText(tile.getElement().toString());
                }
                if (tile.getTreasure() != null) {
                    treasureName.setText(tile.getTreasure().getName());
                    treasureValue.setText(tile.getTreasure().getValueText());
                }
            }
        });


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
        ArrayList<Tile> tiles = new ArrayList<>();
        for (int x = 0; x < 20; x++) {
            for (int y = 0; y < 20; y++) {
                if (board.getTile(x, y).getElement() == null) {
                    tiles.add(board.getTile(x, y));
                }
            }
        }

        Random random = new Random();
        for (int i = 0; i < numberOfTreasures; i++) {
            int n = random.nextInt(tiles.size());
            int t = random.nextInt(treasures.size());

            tiles.get(n).setTreasure(treasures.get(t));

            tiles.remove(n);
            treasures.remove(t);
        }

    }

    private void loadTreasuresTextures() throws FileNotFoundException {
        treasuresTextures = new ImagePattern[5];

        for (int i = 0; i < 5; i++) {
            treasuresTextures[i] = new ImagePattern(new Image(new FileInputStream(PATH_TO_TEXTURES + "treasure" + (i + 1) + ".png")));
        }
    }

    private void drawTreasures() {
        treasuresGroup = new Group();

        for (int x = 0; x < 20; x++) {
            for (int y = 0; y < 20; y++) {

                if (board.getTile(x, y).getTreasure() != null) {
                    Rectangle rec = new Rectangle(TILE_SIZE, TILE_SIZE);
                    rec.relocate(x * TILE_SIZE, y * TILE_SIZE);

                    switch (board.getTile(x, y).getTreasure().getType()) {
                        case DIAMOND -> rec.setFill(treasuresTextures[0]);
                        case RUBIN -> rec.setFill(treasuresTextures[1]);
                        case GOLD -> rec.setFill(treasuresTextures[2]);
                        case PEARL -> rec.setFill(treasuresTextures[3]);
                        case BARREL -> rec.setFill(treasuresTextures[4]);
                    }

                    treasuresGroup.getChildren().add(rec);
                }
            }
        }
    }

}
