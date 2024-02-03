package uk.ac.aber.cs221.gp02;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import org.json.simple.parser.ParseException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;

import static uk.ac.aber.cs221.gp02.Game.getCurrentState;
import static uk.ac.aber.cs221.gp02.Main.*;
import static uk.ac.aber.cs221.gp02.Game.*;

public class GameController {
    @FXML
    Pane pane;

    @FXML
    Button moveButton, rotateButton, endTurnButton, tradeButton, safeZoneButton, rulesButton;

    @FXML
    Group buttonsGroup;

    @FXML
    Label shipInfo, battleLabel, objectName, playerLabel, portLabel, movesLabel;

    @FXML
    TextArea objectDescription;

    @FXML
    GridPane battlePopup;


    //CARDS COUNTER
    @FXML
    Label card3BLabel, card2BLabel, card1BLabel, card3RLabel, card2RLabel, card1RLabel, treasure1Label, treasure2Label, treasure3Label, treasure4Label, treasure5Label, currentTreasureCounter1, currentTreasureCounter2;

    @FXML
    Rectangle shipIcon, currentTreasureIcon1, currentTreasureIcon2;

    private ImagePattern[] treasuresTextures;

    private int playerFontSize = 25;
    private Group playersGroup;
    private Group arrowsGroup;
    private ImagePattern[] playersTextures;
    private ImagePattern[] arrowsTextures;


    /**
     * Initialize the game screen
     * @throws FileNotFoundException We are loading textures
     */
    public void initialize() throws FileNotFoundException {

        // Game.initialize();
        pane.setPrefSize((WIDTH + RIGHT_MARGIN) * TILE_SIZE, HEIGHT * TILE_SIZE);


        loadTreasuresTextures();
        loadPlayersTextures();
        loadArrowsTextures();

        drawPlayers();
        drawArrows();
        pane.getChildren().addAll(board.getBoardGroup(), playersGroup, arrowsGroup);

        updateUI();

        setControl();

    }

    /**
     * Loading textures for treasures and making images
     * @throws FileNotFoundException We are loading textures from file
     */
    private void loadTreasuresTextures() throws FileNotFoundException {
        treasuresTextures = new ImagePattern[5];

        for (int i = 0; i < 5; i++) {
            treasuresTextures[i] = new ImagePattern(new Image(new FileInputStream(PATH_TO_TEXTURES + "treasure" + (i + 1) + ".png")));
        }
    }

    /**
     * Load the textures of the players ships
     * @throws FileNotFoundException We are loading textures from file
     */
    private void loadPlayersTextures() throws FileNotFoundException {
        playersTextures = new ImagePattern[4];

        for (int i = 0; i < 4; i++) {
            playersTextures[i] = new ImagePattern(new Image(new FileInputStream(PATH_TO_TEXTURES + "Ship" + (i + 1) + ".png")));
        }
    }

    /**
     * Load the textures of the arrows into an array of images
     * @throws FileNotFoundException Loading textures from file
     */
    private void loadArrowsTextures() throws FileNotFoundException {
        arrowsTextures = new ImagePattern[32];

        for (int i = 0; i < 32; i++) {
            arrowsTextures[i] = new ImagePattern(new Image(new FileInputStream(PATH_TO_TEXTURES + "Dir_" + (i + 1) + ".png")));
        }
    }


    /**
     * Do things when we click buttons
     */
    private void setControl() {
        moveButton.setOnAction(e -> changeState(State.MOVE));

        rotateButton.setOnAction(e -> changeState(State.ROTATE));

        endTurnButton.setOnAction(e -> changeState(State.NEXT_TURN));

        tradeButton.setOnAction(e -> changeState(State.TRADE));

        safeZoneButton.setOnAction(event -> {
            try {
                openSafeZone();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


        pane.setOnMousePressed(this::onMousePress);
        pane.setOnMouseMoved(this::onMouseMove);
    }

    /**
     * Get the tile we are clicking on
     * @param e where we clicked
     * @return the tile
     */
    private Tile getTile(MouseEvent e) {
        int x, y;
        x = (int) (e.getX() / TILE_SIZE);
        y = (int) (e.getY() / TILE_SIZE);
        if (x < 20 & y < 20)
            return board.getTile(x, y);
        return null;
    }


    /**
     * Function which does stuff when the mouse is clicked
     * @param e mouse event
     */
    private void onMousePress(MouseEvent e) {
        Tile tile = getTile(e);
        if (tile != null) {
            Game.onMousePress(tile);
            update();
        }
    }

    /**
     * Runs when the mouse is moved. We get the tile the mouse is hovered over and update the description on the screen and stuff.
     * @param e mouse event
     */
    private void onMouseMove(MouseEvent e) {
        Tile tile = getTile(e);
        if (tile != null) {
            Game.onMouseMove(tile);
            if (tile.getElement() != null) {
                if (!(objectName.getText().equals(tile.getElement().getName()) && objectDescription.getText().equals(tile.getElement().toString()))) {
                    objectName.setText(tile.getElement().getName());
                    objectDescription.setText(tile.getElement().toString());
                    updateUI();
                }
            }
        }
    }

    /**
     * Change the state of the game
     * @param newState new state of the game
     */
    public void changeState(State newState) {
        switch (newState) {

            case CHOICE -> {
            }
            case ROTATE -> {
            }
            case MOVE -> {
            }
            case NEXT_TURN -> {
            }
            case MOVE_ANY -> {
            }
            case TRADE -> {
                try {
                    openTrade();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Game.changeState(newState);
        update();
    }


    /**
     * Update the game board
     * Draw players, arrows, mark which tiles are reachable and update the ui
     */
    private void update() {
        board.markReachable();
        drawPlayers();
        drawArrows();
        updateUI();
        Game.syncCrewCards();
    }

    /**
     * Updates the UI
     */
    private void updateUI() {
        playerLabel.setText(getCurrentPlayer().getName());
        playerFontSize = 25;
        if(playerLabel.getText().length() > 6)
            playerFontSize -= 13;
        else if(playerLabel.getText().length() > 5)
            playerFontSize -= 10;
        else if(playerLabel.getText().length() > 4)
            playerFontSize -= 5;
        playerLabel.setStyle("-fx-font-size: "+playerFontSize);
        portLabel.setText(getCurrentPlayer().getHomePort().getName());

        tradeButton.setVisible(Game.canTrade());
        safeZoneButton.setVisible(Game.isInHomePort());



        movesLabel.setText(String.valueOf(Game.getCurrentPlayer().getSteps()));

        moveButton.setDisable(!Game.isCanMove());
        moveButton.setOpacity(Game.isCanMove() ? 1 : 0.5);
        // button enabled if
        // check legal = true OR in port = true
        //

        endTurnButton.setDisable(!((Game.checkLegalDirection() || isInPort()) && !isCanMove()));
        endTurnButton.setOpacity((Game.checkLegalDirection() || isInPort()) && !isCanMove() ? 1 : 0.5);
        rotateButton.setDisable(!Game.isCanRotate() || Game.isInHomePort());
        rotateButton.setOpacity(Game.isCanRotate() ? 1 : 0.5 );
        rotateButton.setOpacity(!Game.isInHomePort() ? 1 : 0.5);



        int[] cardsDescription = getCurrentPlayer().cardsDescription();

        card3BLabel.setText(String.valueOf(cardsDescription[0]));
        card2BLabel.setText(String.valueOf(cardsDescription[1]));
        card1BLabel.setText(String.valueOf(cardsDescription[2]));
        card3RLabel.setText(String.valueOf(cardsDescription[3]));
        card2RLabel.setText(String.valueOf(cardsDescription[4]));
        card1RLabel.setText(String.valueOf(cardsDescription[5]));

        int[] treasureDescription = new int[getCurrentPlayer().getHomePort().treasureDescription().length];
        Arrays.setAll(treasureDescription, i -> getCurrentPlayer().getHomePort().treasureDescription()[i] + getCurrentPlayer().getHomePort().safeZoneDescription()[i]);

        treasure1Label.setText(String.valueOf(treasureDescription[0]));
        treasure2Label.setText(String.valueOf(treasureDescription[1]));
        treasure3Label.setText(String.valueOf(treasureDescription[2]));
        treasure4Label.setText(String.valueOf(treasureDescription[3]));
        treasure5Label.setText(String.valueOf(treasureDescription[4]));

        shipIcon.setFill(playersTextures[getCurrentPlayer().getColor()]);


        updateTreasureOnShip();
    }


    /**
     * Update the treasure on the ship
     */
    private void updateTreasureOnShip() {
        ArrayList<Treasure> treasures = getCurrentPlayer().getTreasures();

        currentTreasureIcon1.setVisible(false);
        currentTreasureIcon2.setVisible(false);
        ArrayList<Rectangle> icons = new ArrayList<>(Arrays.asList(currentTreasureIcon1, currentTreasureIcon2));

//        System.out.println(treasures.size());
        for (int i = 0; i < treasures.size(); i++) {
            icons.get(i).setVisible(true);
            switch (treasures.get(i).getType()) {
                case DIAMOND -> icons.get(i).setFill(treasuresTextures[0]);
                case RUBIN -> icons.get(i).setFill(treasuresTextures[1]);
                case GOLD -> icons.get(i).setFill(treasuresTextures[2]);
                case PEARL -> icons.get(i).setFill(treasuresTextures[3]);
                case BARREL -> icons.get(i).setFill(treasuresTextures[4]);
            }
        }
    }


    /**
     * Draw the players on the board
     */
    private void drawPlayers() {
        if (playersGroup == null)
            playersGroup = new Group();

        playersGroup.getChildren().clear();

        for (int i = 0; i < 4; i++) {
            Rectangle rec = new Rectangle(TILE_SIZE, TILE_SIZE);
            rec.relocate(getPlayers().get(i).getXCor() * TILE_SIZE, getPlayers().get(i).getYCor() * TILE_SIZE);
            rec.setFill(playersTextures[getPlayers().get(i).getColor()]);
            playersGroup.getChildren().add(rec);
        }
    }

    /**
     * Draw the arrows around the players based on direction and colour
     */
    private void drawArrows() {
        if (arrowsGroup == null)
            arrowsGroup = new Group();

        arrowsGroup.getChildren().clear();

        for (Player p : Game.getPlayers()) {
            Rectangle rec = new Rectangle(2 * TILE_SIZE, 2 * TILE_SIZE);
            rec.relocate((p.getXCor() - 0.5) * TILE_SIZE, (p.getYCor() - 0.5) * TILE_SIZE);
            int c = 0;
            switch (p.getColor()) {
                case 1:
                    c = 8;
                    break;
                case 2:
                    c = 16;
                    break;
                case 3:
                    c = 24;
                    break;
            }

            switch (p.getDirection()) {
                case NORTH -> rec.setFill(arrowsTextures[0 + c]);
                case NORTHEAST -> rec.setFill(arrowsTextures[1 + c]);
                case EAST -> rec.setFill(arrowsTextures[2 + c]);
                case SOUTHEAST -> rec.setFill(arrowsTextures[3 + c]);
                case SOUTH -> rec.setFill(arrowsTextures[4 + c]);
                case SOUTHWEST -> rec.setFill(arrowsTextures[5 + c]);
                case WEST -> rec.setFill(arrowsTextures[6 + c]);
                case NORTHWEST -> rec.setFill(arrowsTextures[7 + c]);
            }
            arrowsGroup.getChildren().add(rec);
        }
    }


    /**
     * Open the trade screen
     * @throws IOException
     */
    private void openTrade() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("tradeScreen.fxml"));
        TradeController tradeController = new TradeController(getCurrentPlayer(), (Port) board.getTile(getCurrentPlayer().getXCor(), getCurrentPlayer().getYCor()).getElement());
        loader.setController(tradeController);
        Stage stage = new Stage();
        stage.initOwner(tradeButton.getScene().getWindow());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Buccaneer - Trade");
        stage.setScene(new Scene(loader.load()));
        stage.showAndWait();
    }

    /**
     * Open the safe zone trade screen
     * @throws IOException
     */
    private void openSafeZone() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("safeZone.fxml"));
        SafeZoneController safeZoneController = new SafeZoneController(getCurrentPlayer());
        loader.setController(safeZoneController);
        Stage stage = new Stage();
        stage.initOwner(safeZoneButton.getScene().getWindow());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Buccaneer - Safe Zone");
        stage.setScene(new Scene(loader.load()));
        stage.showAndWait();
    }

}