package uk.ac.aber.cs221.gp02;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static uk.ac.aber.cs221.gp02.Main.PATH_TO_TEXTURES;
import static uk.ac.aber.cs221.gp02.Main.TILE_SIZE;

/**
 * This class is a Board of the Buccaneer Game
 * It handles logic tasks as well as graphical
 *
 * @author Adrian
 * @version 1.0
 */
public class Board {

    private ImagePattern[] tileTextures;
    private ImagePattern[] portTextures;
    private ImagePattern[] baysTextures;
    private ImagePattern[] islandsTextures;

    private final Tile[][] tiles;
    Group boardGroup;
    Group tilesGroup;

    private Port[] ports;
    private Bay[] bays;
    private Island[] islands;
    private Coast[] coasts;
    private FlatIsland flatIsland;
    private TreasureIsland treasureIsland;
    private PirateIsland pirateIsland;


    /**
     * The constructor initialize tiles(logic structure of tiles), tilesGroup(visual structure of tiles) and
     * calls createTiles method to create tiles
     */
    public Board() throws FileNotFoundException {
        tiles = new Tile[20][20];
        boardGroup = new Group();
        tilesGroup = new Group();

        createTiles();
        initializeTextures();

        drawBoardElements();
        createBoardElements();
        assignBoardElementsToTiles();

    }

    private void createTiles() {
        for (int y = 0; y < 20; y++) {
            for (int x = 0; x < 20; x++) {
                tiles[x][y] = new Tile((x + y) % 2 == 0, x, y);
            }
        }
    }

    private void createBoardElements() {
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


        flatIsland = new FlatIsland("Flat Island");
        treasureIsland = new TreasureIsland("Treasure Island");
        pirateIsland = new PirateIsland("Pirate Island");

        coasts = new Coast[]{
                new Coast("Coast of Flat Island"),
                new Coast("Coast of Treasure Island"),
                new Coast("Coast of Pirate Island"),

        };


    }

    private void assignBoardElementsToTiles() {
        // ports
        assignElement(20, 14, ports[0]);
        assignElement(14, 20, ports[1]);
        assignElement(7, 1, ports[2]);
        assignElement(1, 14, ports[3]);
        assignElement(20, 7, ports[4]);
        assignElement(1, 7, ports[5]);

        // bays
        assignElement(20, 1, bays[0]);
        assignElement(20, 20, bays[1]);
        assignElement(1, 1, bays[2]);

        // flat island

        for (int x = 2; x <= 4; x++)
            for (int y = 16; y <= 19; y++)
                assignElement(x, y, flatIsland);

        // treasure island
        for (int x = 9; x <= 12; x++)
            for (int y = 9; y <= 12; y++)
                assignElement(x, y, treasureIsland);

        for (int x = 17; x <= 19; x++)
            for (int y = 2; y <= 5; y++)
                assignElement(x, y, pirateIsland);

        // coasts
        for (int x = 1; x <= 5; x++) {
            assignElement(x, 15, coasts[0]);
            assignElement(x, 20, coasts[0]);
        }

        for (int y = 16; y <= 19; y++) {
            assignElement(1, y, coasts[0]);
            assignElement(5, y, coasts[0]);
        }

        for (int x = 8; x <= 13; x++) {
            assignElement(x, 8, coasts[1]);
            assignElement(x, 13, coasts[1]);
        }

        for (int y = 9; y <= 12; y++) {
            assignElement(8, y, coasts[1]);
            assignElement(13, y, coasts[1]);
        }

        for (int x = 16; x <= 19; x++) {
            assignElement(x, 1, coasts[2]);
            assignElement(x, 6, coasts[2]);
        }
        assignElement(20, 6, coasts[2]);

        for (int y = 2; y <= 5; y++) {
            assignElement(16, y, coasts[2]);
            assignElement(20, y, coasts[2]);
        }


    }

    private void assignElement(int x, int y, BoardElement boardElement) {
        tiles[x - 1][20 - y].setElement(boardElement);
    }

    private void initializeTextures() throws FileNotFoundException {
        tileTextures = new ImagePattern[]{
                getTexture("TileDark.png"),
                getTexture("TileLight.png"),
                getTexture("TileDark1.png"),
                getTexture("TileLight1.png"),
        };

        portTextures = new ImagePattern[]{
                getTexture("PortA.png"),
                getTexture("PortC.png"),
                getTexture("PortG.png"),
                getTexture("PortL.png"),
                getTexture("PortM.png"),
                getTexture("PortV.png"),
        };

        baysTextures = new ImagePattern[]{
                getTexture("Bay.png"),
                getTexture("Anchor_bay.png"),
                getTexture("Mud_bay.png")
        };

        islandsTextures = new ImagePattern[]{
                getTexture("IslandF.png"),
                getTexture("IslandT.png"),
                getTexture("IslandP.png"),
        };

    }

    private ImagePattern getTexture(String filename) throws FileNotFoundException {
        return new ImagePattern(new Image(new FileInputStream(PATH_TO_TEXTURES + filename)));
    }

    public void draw() {
        boardGroup.getChildren().clear();
        drawTiles();
        drawBoardElements();
    }

    private void drawTiles() {
        for (int y = 0; y < 20; y++) {
            for (int x = 0; x < 20; x++) {
                Rectangle rec = new Rectangle(TILE_SIZE, TILE_SIZE);
                rec.relocate(x * TILE_SIZE, y * TILE_SIZE);

                Tile tile = tiles[x][y];

                if (tile.isLight())
                    rec.setFill(tile.isPath() ? tileTextures[3] : tileTextures[1]);
                else
                    rec.setFill(tile.isPath() ? tileTextures[2] : tileTextures[0]);

                boardGroup.getChildren().add(rec);

            }
        }
    }

    private void drawBoardElements() {
        // ports
        drawElement(20, 14, portTextures[0], 1, 1);
        drawElement(14, 20, portTextures[1], 1, 1);
        drawElement(7, 1, portTextures[2], 1, 1);
        drawElement(1, 14, portTextures[3], 1, 1);
        drawElement(20, 7, portTextures[4], 1, 1);
        drawElement(1, 7, portTextures[5], 1, 1);

        // bays
        drawElement(20, 1, baysTextures[1], 1, 1);
        drawElement(20, 20, baysTextures[0], 1, 1);
        drawElement(1, 1, baysTextures[2], 1, 1);

        // islands
        drawElement(2, 19, islandsTextures[0], 3, 4);
        drawElement(9, 12, islandsTextures[1], 4, 4);
        drawElement(17, 5, islandsTextures[2], 3, 4);


    }


    private void drawElement(int x, int y, ImagePattern texture, int x1, int y1) {
        Rectangle rec = new Rectangle(x1 * TILE_SIZE, y1 * TILE_SIZE);
        rec.setFill(texture);
        rec.relocate((x - 1) * TILE_SIZE, (20 - y) * TILE_SIZE);
        boardGroup.getChildren().add(rec);
    }


    public Group getBoardGroup() {
        return boardGroup;
    }

    public Tile getTile(int x, int y) {
        return tiles[x][y];
    }


    public void cleanTiles() {
        for (int y = 0; y < 20; y++) {
            for (int x = 0; x < 20; x++) {
                tiles[x][y].setPath(false);
            }
        }
    }

    public void markReachable() {
        Player p = Game.getCurrentPlayer();
        State state = Game.getCurrentState();

        cleanTiles();

        int x = p.getXCor();
        int y = p.getYCor();
        int steps = p.getSteps();
        Direction dir = p.getDirection();

//        if (state == State.CHOICE){
//            Game.nearestShip();
//        }
        // i = -1 j = 0 x = 1 y = 1
        if (state == State.ROTATE) {
            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    if (x + i >= 0 && y + j >= 0 && x + i < 20 && y + j < 20 && !(i == 0 && j == 0))
                        if (!(tiles[x + i][y + j].getElement() instanceof Island))
                            tiles[x + i][y + j].setPath(true);
                }
            }
        }


        if (state == State.MOVE) {
            Tile t = null;
            int s = 0;
            switch (dir) {
                case NORTH -> {

                    while (s++ < steps && isLegalSquare(x, y - s)) {
                        t = tiles[x][y - s];
                        t.setPath(true);
                        if (Game.isShipOnTile(t) != null && (t.getElement() instanceof Port || t.getElement() instanceof Coast)) {
                            t.setPath(false);
                        }
                    }
                }
                case NORTHEAST -> {
                    while (s++ < steps && isLegalSquare(x + s, y - s)) {
                        t = tiles[x + s][y - s];
                        t.setPath(true);
                        if (Game.isShipOnTile(t) != null && (t.getElement() instanceof Port || t.getElement() instanceof Coast)) {
                            t.setPath(false);
                        }
                    }
                }
                case EAST -> {
                    while (s++ < steps && isLegalSquare(x + s, y)) {
                        t = tiles[x + s][y];
                        t.setPath(true);
                        if (Game.isShipOnTile(t) != null && (t.getElement() instanceof Port || t.getElement() instanceof Coast)) {
                            t.setPath(false);
                        }
                    }
                }
                case SOUTHEAST -> {
                    while (s++ < steps && isLegalSquare(x + s, y + s)) {
                        t = tiles[x + s][y + s];
                        t.setPath(true);
                        if (Game.isShipOnTile(t) != null && (t.getElement() instanceof Port || t.getElement() instanceof Coast)) {
                            t.setPath(false);
                        }
                    }
                }
                case SOUTH -> {
                    while (s++ < steps && isLegalSquare(x, y + s)) {
                        t = tiles[x][y + s];
                        t.setPath(true);
                        if (Game.isShipOnTile(t) != null && (t.getElement() instanceof Port || t.getElement() instanceof Coast)) {
                            t.setPath(false);
                        }
                    }
                }
                case SOUTHWEST -> {
                    while (s++ < steps && isLegalSquare(x - s, y + s)) {
                        t = tiles[x - s][y + s];
                        t.setPath(true);
                        if (Game.isShipOnTile(t) != null && (t.getElement() instanceof Port || t.getElement() instanceof Coast)) {
                            t.setPath(false);
                        }
                    }
                }
                case WEST -> {
                    while (s++ < steps && isLegalSquare(x - s, y)) {
                        t = tiles[x - s][y];
                        t.setPath(true);
                        if (Game.isShipOnTile(t) != null && (t.getElement() instanceof Port || t.getElement() instanceof Coast)) {
                            t.setPath(false);
                        }
                    }
                }
                case NORTHWEST -> {
                    while (s++ < steps && isLegalSquare(x - s, y - s)) {
                        t = tiles[x - s][y - s];
                        t.setPath(true);
                        if (Game.isShipOnTile(t) != null && (t.getElement() instanceof Port || t.getElement() instanceof Coast)) {
                            t.setPath(false);
                        }
                    }
                }
            }
        }


        if (state == State.MOVE_ANY) {
            Tile t = null;
            int s = 0;
            while (s++ < steps && isLegalSquare(x, y - s)) {
                t = tiles[x][y - s];
                t.setPath(true);
                if (Game.isShipOnTile(t) != null && (t.getElement() instanceof Port || t.getElement() instanceof Coast)) {
                    t.setPath(false);
                }
            }

            s = 0;
            while (s++ < steps && isLegalSquare(x + s, y - s)) {
                t = tiles[x + s][y - s];
                t.setPath(true);
                if (Game.isShipOnTile(t) != null && (t.getElement() instanceof Port || t.getElement() instanceof Coast)) {
                    t.setPath(false);
                }
            }

            s = 0;
            while (s++ < steps && isLegalSquare(x + s, y)) {
                t = tiles[x + s][y];
                t.setPath(true);
                if (Game.isShipOnTile(t) != null && (t.getElement() instanceof Port || t.getElement() instanceof Coast)) {
                    t.setPath(false);
                }
            }

            s = 0;
            while (s++ < steps && isLegalSquare(x + s, y + s)) {
                t = tiles[x + s][y + s];
                t.setPath(true);
                if (Game.isShipOnTile(t) != null && (t.getElement() instanceof Port || t.getElement() instanceof Coast)) {
                    t.setPath(false);
                }
            }

            s = 0;
            while (s++ < steps && isLegalSquare(x, y + s)) {
                t = tiles[x][y + s];
                t.setPath(true);
                if (Game.isShipOnTile(t) != null && (t.getElement() instanceof Port || t.getElement() instanceof Coast)) {
                    t.setPath(false);
                }
            }

            s = 0;
            while (s++ < steps && isLegalSquare(x - s, y + s)) {
                t = tiles[x - s][y + s];
                t.setPath(true);
                if (Game.isShipOnTile(t) != null && (t.getElement() instanceof Port || t.getElement() instanceof Coast)) {
                    t.setPath(false);
                }
            }


            s = 0;
            while (s++ < steps && isLegalSquare(x - s, y)) {
                t = tiles[x - s][y];
                t.setPath(true);
                if (Game.isShipOnTile(t) != null && (t.getElement() instanceof Port || t.getElement() instanceof Coast)) {
                    t.setPath(false);
                }
            }

            s = 0;
            while (s++ < steps && isLegalSquare(x - s, y - s)) {
                t = tiles[x - s][y - s];
                t.setPath(true);
                if (Game.isShipOnTile(t) != null && (t.getElement() instanceof Port || t.getElement() instanceof Coast)) {
                    t.setPath(false);
                }
            }

        }
        draw();

    }

    private boolean isLegalSquare(int x, int y) {
        return x >= 0 && y >= 0 && x < 20 && y < 20 && !(tiles[x][y].getElement() instanceof Island);
    }

    public Port[] getPorts() {
        return ports;
    }

    public TreasureIsland getTreasureIsland() {
        return treasureIsland;
    }

    public FlatIsland getFlatIsland() {
        return flatIsland;
    }

    public PirateIsland getPirateIsland() {
        return pirateIsland;
    }
}


