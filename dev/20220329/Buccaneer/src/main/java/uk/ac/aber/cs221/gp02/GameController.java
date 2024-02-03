package uk.ac.aber.cs221.gp02;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static uk.ac.aber.cs221.gp02.Main.*;
import static uk.ac.aber.cs221.gp02.Game.*;

public class GameController {
    @FXML
    Pane pane;

    @FXML
    Button moveButton, rotateButton, endTurnButton, tradeButton, rulesButton;

    @FXML
    Group buttonsGroup;

    @FXML
    Label shipInfo, battleLabel, objectName, playerLabel, stateLabel, portLabel;

    @FXML
    TextArea objectDescription;


    //CARDS COUNTER
    @FXML
    Label card3BLabel, card2BLabel, card1BLabel, card3RLabel, card2RLabel, card1RLabel;

    @FXML
    Rectangle shipIcon;

    private ImagePattern[] treasuresTextures;

    private Group playersGroup;
    private ImagePattern[] playersTextures;


    public void initialize() throws FileNotFoundException {

      //  Game.initialize();
        pane.setPrefSize((WIDTH + RIGHT_MARGIN) * TILE_SIZE, HEIGHT * TILE_SIZE);

        loadTreasuresTextures();
        loadPlayersTextures();

        drawPlayers();
        pane.getChildren().addAll(board.getBoardGroup(), playersGroup);

        updateUI();

        setControl();
    }

    private void loadTreasuresTextures() throws FileNotFoundException {
        treasuresTextures = new ImagePattern[5];

        for (int i = 0; i < 5; i++) {
            treasuresTextures[i] = new ImagePattern(new Image(new FileInputStream(PATH_TO_TEXTURES + "treasure" + (i + 1) + ".png")));
        }
    }

    private void loadPlayersTextures() throws FileNotFoundException {
        playersTextures = new ImagePattern[4];

        for (int i = 0; i < 4; i++) {
            playersTextures[i] = new ImagePattern(new Image(new FileInputStream(PATH_TO_TEXTURES + "Ship" + (i + 1) + ".png")));
        }
    }


    private void setControl() {
        moveButton.setOnAction(e -> changeState(State.MOVE));

        rotateButton.setOnAction(e -> changeState(State.ROTATE));

        endTurnButton.setOnAction(e -> changeState(State.NEXT_TURN));

        tradeButton.setOnAction(e -> changeState(State.TRADE));

        pane.setOnMousePressed(this::onMousePress);
        pane.setOnMouseMoved(this::onMouseMove);
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
            Game.onMousePress(tile);
            update();
        }
    }

    private void onMouseMove(MouseEvent e) {
        Tile tile = getTile(e);
        if (tile != null) {
            Game.onMouseMove(tile);
            if (tile.getElement() != null) {
                if(!(objectName.getText().equals(tile.getElement().getName()) && objectDescription.getText().equals(tile.getElement().toString()))){
                    objectName.setText(tile.getElement().getName());
                    objectDescription.setText(tile.getElement().toString());
                    update();
                }
            }
        }

    }
//

    private void changeState(State newState) {
        switch (newState){

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

            }
        }
        Game.changeState(newState);
        update();
    }

    private void update() {
        board.markReachable();
        drawPlayers();
        updateUI();
    }

    private void updateUI() {
        playerLabel.setText(getCurrentPlayer().getName());
        stateLabel.setText(getCurrentState().toString());
        portLabel.setText(getCurrentPlayer().getHomePort().getName());

        tradeButton.setVisible(Game.canTrade());

        int[] cardsDescription = getCurrentPlayer().cardsDescription();

        card3BLabel.setText(String.valueOf(cardsDescription[0]));
        card2BLabel.setText(String.valueOf(cardsDescription[1]));
        card1BLabel.setText(String.valueOf(cardsDescription[2]));
        card3RLabel.setText(String.valueOf(cardsDescription[3]));
        card2RLabel.setText(String.valueOf(cardsDescription[4]));
        card1RLabel.setText(String.valueOf(cardsDescription[5]));


        shipIcon.setFill(playersTextures[getCurrentPlayer().getColor()]);

    }


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
}
