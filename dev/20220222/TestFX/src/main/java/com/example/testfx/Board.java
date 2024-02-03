package com.example.testfx;

import javafx.scene.Group;

import java.io.FileNotFoundException;

/**
 * This class is a board for Buccaneer game
 * It is made out of Tiles
 */
public class Board {

    private final Tile[][] tiles;
    Group tilesGroup;


    /**
     * The constructor initialize tiles(logic structure of tiles), tilesGroup(visual structure of tiles) and
     * calls createTiles method to create tiles
     */
    public Board() throws FileNotFoundException {
        tiles = new Tile[20][20];
        tilesGroup = new Group();
        createTiles();
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

                tilesGroup.getChildren().add(tile);
            }
        }

        // flat island
        for (int x = 2; x <= 4; x++)
            for (int y = 16; y <= 19; y++)
                tiles[x - 1][20 - y].setIsland(true);

        // pirate island
        for (int x = 17; x <= 19; x++)
            for (int y = 2; y <= 5; y++)
                tiles[x - 1][20 - y].setIsland(true);

        // treasure island
        for (int x = 9; x <= 12; x++)
            for (int y = 9; y <= 12; y++)
                tiles[x - 1][20 - y].setIsland(true);
    }

    public Group getTilesGroup() {
        return tilesGroup;
    }

   public Tile getPressedTile(int x, int y){
        return tiles[x][y];
   }


    /**
     * This method changes color of all Tiles that are reachable in the players turn
     * @param ship is a reference to the ship of current player
     */
    public void paintReachable(Ship ship) {

        // ship parameters are assigned to the local variables
        int shipX = ship.getXCor();
        int shipY = ship.getYCor();
        Directions shipDir = ship.getCurrentDirection();
        int shipSteps = ship.getNumOfSteps();

        // for each Tile color is set to default
        for (int y = 0; y < 20; y++) {
            for (int x = 0; x < 20; x++) {
                tiles[x][y].setColor(1);

                // if the Tile is in the range of Ship it is painted with green
                if (!(x == shipX && y == shipY) &&
                        Math.abs(x - shipX) + Math.abs(y - shipY) <= shipSteps &&
                    !tiles[x][y].isIsland()) {
                    tiles[x][y].setColor(2);

                    // if the Tile is also on the Ship's way it is painted with light green
                    switch (shipDir) {
                        case NORTH -> {
                            if (y < shipY && x == shipX)
                                tiles[x][y].setColor(3);
                        }
                        case WEST -> {
                            if (x < shipX && y == shipY)
                                tiles[x][y].setColor(3);
                        }
                        case SOUTH -> {
                            if (y > shipY && x == shipX)
                                tiles[x][y].setColor(3);
                        }
                        case EAST -> {
                            if (x > shipX && y == shipY)
                                tiles[x][y].setColor(3);
                        }
                    }
                }
            }
        }
    }

}
