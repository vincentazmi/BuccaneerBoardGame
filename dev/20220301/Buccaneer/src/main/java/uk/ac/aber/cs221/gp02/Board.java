package uk.ac.aber.cs221.gp02;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static uk.ac.aber.cs221.gp02.BuccaneerApplication.PATH_TO_TEXTURES;
import static uk.ac.aber.cs221.gp02.BuccaneerApplication.TILE_SIZE;

/**
 * This class is a board for Buccaneer game
 * It is made out of Tiles
 */
public class Board {

    private final Tile[][] tiles;
    Group boardGroup;

    private Port[] ports;
    private Bay[] bays;
    private Island[] islands;


    /**
     * The constructor initialize tiles(logic structure of tiles), tilesGroup(visual structure of tiles) and
     * calls createTiles method to create tiles
     */
    public Board() throws FileNotFoundException {
        tiles = new Tile[20][20];
        boardGroup = new Group();
        createTiles();
        createBoardElements();
        initializeBoardElements();
        assignBoardElementsToTiles();
    }

    /**
     * This method create Tiles, assign them to the array and group, set them on the position, paint with proper color
     * and indicates whether Tile is under the island or not
     */
    private void createTiles() throws FileNotFoundException {
        for (int y = 0; y < 20; y++) {
            for (int x = 0; x < 20; x++) {
                Tile tile = new Tile((x + y) % 2 == 0, x, y);
                tiles[x][y] = tile;

                boardGroup.getChildren().add(tile);

                tile.setOnMouseClicked(e -> {
                    if (tile.getElement() != null)
                        System.out.println(tile.getElement().toString());
                });
            }
        }
    }

    private void createBoardElements() throws FileNotFoundException {
        // ports
        addBoardElement(20, 14, "PortA.png", 1, 1);
        addBoardElement(14, 20, "PortC.png", 1, 1);
        addBoardElement(7, 1, "PortG.png", 1, 1);
        addBoardElement(1, 14, "PortL.png", 1, 1);
        addBoardElement(20, 7, "PortM.png", 1, 1);
        addBoardElement(1, 7, "PortV.png", 1, 1);

        // bays
        addBoardElement(20, 1, "Bay.png", 1, 1);
        addBoardElement(20, 20, "Bay.png", 1, 1);
        addBoardElement(1, 1, "Bay.png", 1, 1);

        // islands
        addBoardElement(2, 19, "IslandF.png", 3, 4);
        addBoardElement(9, 12, "IslandT.png", 4, 4);
        addBoardElement(17, 5, "IslandP.png", 3, 4);


    }

    private void addBoardElement(int x, int y, String filename, int x1, int y1) throws FileNotFoundException {
        Rectangle rec = new Rectangle(x1 * TILE_SIZE, y1 * TILE_SIZE);
        rec.setFill(new ImagePattern(new Image(new FileInputStream(PATH_TO_TEXTURES + filename))));
        rec.relocate((x - 1) * TILE_SIZE, (20 - y) * TILE_SIZE);
        boardGroup.getChildren().add(rec);
    }

    private void initializeBoardElements() {
        ports = new Port[]{
                new Port("Port of Amsterdam"),
                new Port("Port of Cadiz"),
                new Port("Port of Genoa"),
                new Port("Port of London"),
                new Port("Port of Marseilles"),
                new Port("Port of Venice"),
        };

        bays = new Bay[]{
                new Bay("Anchor Bay"),
                new Bay("Cliff Creek"),
                new Bay("Mud Bay"),
        };

        islands = new Island[]{
              new Island("Flat Island"),
              new Island("Treasure Island"),
              new Island("Pirate Island"),
        };


    }

    private void assignBoardElementsToTiles() {
        // ports
        assignBoardElement(20, 14, ports[0]);
        assignBoardElement(14, 20, ports[1]);
        assignBoardElement(7, 1, ports[2]);
        assignBoardElement(1, 14, ports[3]);
        assignBoardElement(20, 7, ports[4]);
        assignBoardElement(1, 7, ports[5]);

        // bays
        assignBoardElement(20, 1, bays[0]);
        assignBoardElement(20, 20, bays[1]);
        assignBoardElement(1, 1, bays[2]);

        // flat island

        for (int x = 2; x <= 4; x++)
            for (int y = 16; y <= 19; y++)
                assignBoardElement(x, y, islands[0]);

        // treasure island
        for (int x = 9; x <= 12; x++)
            for (int y = 9; y <= 12; y++)
                assignBoardElement(x, y, islands[1]);

        for (int x = 17; x <= 19; x++)
            for (int y = 2; y <= 5; y++)
                assignBoardElement(x, y, islands[2]);

    }

    private void assignBoardElement(int x, int y, BoardElement boardElement) {
        tiles[x - 1][20 - y].setElement(boardElement);
    }

    public Group getBoardGroup() {
        return boardGroup;
    }

    public Tile getTile(int x, int y) {
        return tiles[x][y];
    }


}
