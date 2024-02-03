package uk.ac.aber.cs221.gp02.tradescreenprototype;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.*;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Arrays;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.IntStream;


import static uk.ac.aber.cs221.gp02.tradescreenprototype.BuccaneerApplication.PATH_TO_TEXTURES;

public class Trade implements Initializable {

    @FXML
    private Pane pane;

    @FXML
    private Button tradeButton, cancelButton, rulesButton;

    @FXML
    private TextArea eventArea;

    @FXML
    private Slider port1BSlider, port2BSlider, port3BSlider, port1RSlider, port2RSlider, port3RSlider, portTreasure1Slider,
            portTreasure2Slider, portTreasure3Slider, portTreasure4Slider, portTreasure5Slider, player1BSlider,
            player2BSlider, player3BSlider, player1RSlider, player2RSlider, player3RSlider, playerTreasure1Slider,
            playerTreasure2Slider, playerTreasure3Slider, playerTreasure4Slider, playerTreasure5Slider;

    @FXML
    private Group buttonsGroup;

    @FXML
    private Label portName, playerName, selectedPortValue, selectedPlayerValue, port1BSelectedValue, port2BSelectedValue,
            port3BSelectedValue, port1RSelectedValue, port2RSelectedValue, port3RSelectedValue, port1BAvailableValue,
            port2BAvailableValue, port3BAvailableValue, port1RAvailableValue, port2RAvailableValue, port3RAvailableValue,
            portTreasure1SelectedValue, portTreasure2SelectedValue, portTreasure3SelectedValue, portTreasure4SelectedValue,
            portTreasure5SelectedValue, portTreasure1AvailableValue, portTreasure2AvailableValue, portTreasure3AvailableValue,
            portTreasure4AvailableValue, portTreasure5AvailableValue, player1BSelectedValue, player2BSelectedValue,
            player3BSelectedValue, player1RSelectedValue, player2RSelectedValue, player3RSelectedValue, player1BAvailableValue,
            player2BAvailableValue, player3BAvailableValue, player1RAvailableValue, player2RAvailableValue, player3RAvailableValue,
            playerTreasure1SelectedValue, playerTreasure2SelectedValue, playerTreasure3SelectedValue, playerTreasure4SelectedValue,
            playerTreasure5SelectedValue, playerTreasure1AvailableValue, playerTreasure2AvailableValue, playerTreasure3AvailableValue,
            playerTreasure4AvailableValue, playerTreasure5AvailableValue;

    @FXML
    private Rectangle shipIcon, portIcon;


    private final TradeTestPlayer currentPlayer;
    private final Port currentPort;
    private ImagePattern[] portTextures;
    private ImagePattern[] shipTextures;
    private IntegerProperty totalPlayerValue;
    private IntegerProperty totalPortValue;


    public Trade(TradeTestPlayer currentPlayer, Port currentPort) {
        this.currentPlayer = currentPlayer;
        this.currentPort = currentPort;
    }

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     */

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Load textures
        try {
            initializeTextures();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //Set Player and Port names and draw textures
        playerName.setText(currentPlayer.getName());
        portName.setText(currentPort.getName());
        drawTextures();

        //Configure Properties, Labels, Sliders, and Listeners
        totalPlayerValue = new SimpleIntegerProperty(0);
        totalPortValue = new SimpleIntegerProperty(0);
        initialiseValues();
        initializeListeners();

        // Trade Button Press TODO implement check to make sure ship is not carrying more than 2 treasures after trade
        tradeButton.setOnAction(e -> {
            if (Objects.equals(totalPlayerValue.getValue(), totalPortValue.getValue()) && totalPlayerValue.getValue() != 0) {
                completeTrade();
                eventArea.setStyle("-fx-text-fill: #00a400; -fx-font-size: 20px;");
                eventArea.setText("Trade Complete");
            } else {
                eventArea.setStyle("-fx-text-fill: #a60000;");
                eventArea.setText("Could not complete trade, selected values do not match or no value was selected");
                System.err.println("Could not complete trade, selected values do not match or no value was selected");
            }
        });
    }

    private void initializeTextures() throws FileNotFoundException {
        portTextures = new ImagePattern[]{
                getTexture("PortA.png"),
                getTexture("PortC.png"),
                getTexture("PortG.png"),
                getTexture("PortL.png"),
                getTexture("PortM.png"),
                getTexture("PortV.png"),
        };
        shipTextures = new ImagePattern[]{
                getTexture("Ship1.png"),
                getTexture("Ship2.png"),
                getTexture("Ship3.png"),
                getTexture("Ship4.png"),
        };
    }

    private void drawTextures() {
        switch (currentPort.getName()) {
            case "Port of Amsterdam" -> portIcon.setFill(portTextures[0]);
            case "Port of Cadiz" -> portIcon.setFill(portTextures[1]);
            case "Port of Genoa" -> portIcon.setFill(portTextures[2]);
            case "Port of London" -> portIcon.setFill(portTextures[3]);
            case "Port of Marseilles" -> portIcon.setFill(portTextures[4]);
            case "Port of Venice" -> portIcon.setFill(portTextures[5]);
        }
        shipIcon.setFill(shipTextures[currentPlayer.getColor()]);
    }

    private ImagePattern getTexture(String filename) throws FileNotFoundException {
        return new ImagePattern(new Image(new FileInputStream(PATH_TO_TEXTURES + filename)));
    }

    private void initialiseValues() {
        // Load available crew cards TODO convert to array to reduce code
        int[] playerCardsDescription = currentPlayer.cardsDescription();
        player1BAvailableValue.setText(String.valueOf(playerCardsDescription[0]));
        player2BAvailableValue.setText(String.valueOf(playerCardsDescription[1]));
        player3BAvailableValue.setText(String.valueOf(playerCardsDescription[2]));
        player1RAvailableValue.setText(String.valueOf(playerCardsDescription[3]));
        player2RAvailableValue.setText(String.valueOf(playerCardsDescription[4]));
        player3RAvailableValue.setText(String.valueOf(playerCardsDescription[5]));
        int[] portCardsDescription = currentPort.cardsDescription();
        port1BAvailableValue.setText(String.valueOf(portCardsDescription[0]));
        port2BAvailableValue.setText(String.valueOf(portCardsDescription[1]));
        port3BAvailableValue.setText(String.valueOf(portCardsDescription[2]));
        port1RAvailableValue.setText(String.valueOf(portCardsDescription[3]));
        port2RAvailableValue.setText(String.valueOf(portCardsDescription[4]));
        port3RAvailableValue.setText(String.valueOf(portCardsDescription[5]));

        // Load available treasure TODO convert to array to reduce code
        int[] playerTreasureDescription = currentPlayer.treasureDescription();
        playerTreasure1AvailableValue.setText(String.valueOf(playerTreasureDescription[0]));
        playerTreasure2AvailableValue.setText(String.valueOf(playerTreasureDescription[1]));
        playerTreasure3AvailableValue.setText(String.valueOf(playerTreasureDescription[2]));
        playerTreasure4AvailableValue.setText(String.valueOf(playerTreasureDescription[3]));
        playerTreasure5AvailableValue.setText(String.valueOf(playerTreasureDescription[4]));
        int[] portTreasureDescription = currentPort.treasureDescription();
        portTreasure1AvailableValue.setText(String.valueOf(portTreasureDescription[0]));
        portTreasure2AvailableValue.setText(String.valueOf(portTreasureDescription[1]));
        portTreasure3AvailableValue.setText(String.valueOf(portTreasureDescription[2]));
        portTreasure4AvailableValue.setText(String.valueOf(portTreasureDescription[3]));
        portTreasure5AvailableValue.setText(String.valueOf(portTreasureDescription[4]));

        // Load crew card slider max values TODO convert to array to reduce code
        player1BSlider.setMax(playerCardsDescription[0]);
        player2BSlider.setMax(playerCardsDescription[1]);
        player3BSlider.setMax(playerCardsDescription[2]);
        player1RSlider.setMax(playerCardsDescription[3]);
        player2RSlider.setMax(playerCardsDescription[4]);
        player3RSlider.setMax(playerCardsDescription[5]);
        port1BSlider.setMax(portCardsDescription[0]);
        port2BSlider.setMax(portCardsDescription[1]);
        port3BSlider.setMax(portCardsDescription[2]);
        port1RSlider.setMax(portCardsDescription[3]);
        port2RSlider.setMax(portCardsDescription[4]);
        port3RSlider.setMax(portCardsDescription[5]);

        // Load treasure slider max values TODO convert to array to reduce code
        playerTreasure1Slider.setMax(playerTreasureDescription[0]);
        playerTreasure2Slider.setMax(playerTreasureDescription[1]);
        playerTreasure3Slider.setMax(playerTreasureDescription[2]);
        playerTreasure4Slider.setMax(playerTreasureDescription[3]);
        playerTreasure5Slider.setMax(playerTreasureDescription[4]);
        portTreasure1Slider.setMax(portTreasureDescription[0]);
        portTreasure2Slider.setMax(portTreasureDescription[1]);
        portTreasure3Slider.setMax(portTreasureDescription[2]);
        portTreasure4Slider.setMax(portTreasureDescription[3]);
        portTreasure5Slider.setMax(portTreasureDescription[4]);
    }

    private void initializeListeners() {
        // Generate Slider Listeners TODO convert to array to reduce code
        player3BSlider.valueProperty().addListener(new SliderChangeListener(player3BSlider, player3BSelectedValue, totalPlayerValue, 3));
        player2BSlider.valueProperty().addListener(new SliderChangeListener(player2BSlider, player2BSelectedValue, totalPlayerValue, 2));
        player1BSlider.valueProperty().addListener(new SliderChangeListener(player1BSlider, player1BSelectedValue, totalPlayerValue, 1));
        player3RSlider.valueProperty().addListener(new SliderChangeListener(player3RSlider, player3RSelectedValue, totalPlayerValue, 3));
        player2RSlider.valueProperty().addListener(new SliderChangeListener(player2RSlider, player2RSelectedValue, totalPlayerValue, 2));
        player1RSlider.valueProperty().addListener(new SliderChangeListener(player1RSlider, player1RSelectedValue, totalPlayerValue, 1));

        port3BSlider.valueProperty().addListener(new SliderChangeListener(port3BSlider, port3BSelectedValue, totalPortValue, 3));
        port2BSlider.valueProperty().addListener(new SliderChangeListener(port2BSlider, port2BSelectedValue, totalPortValue, 2));
        port1BSlider.valueProperty().addListener(new SliderChangeListener(port1BSlider, port1BSelectedValue, totalPortValue, 1));
        port3RSlider.valueProperty().addListener(new SliderChangeListener(port3RSlider, port3RSelectedValue, totalPortValue, 3));
        port2RSlider.valueProperty().addListener(new SliderChangeListener(port2RSlider, port2RSelectedValue, totalPortValue, 2));
        port1RSlider.valueProperty().addListener(new SliderChangeListener(port1RSlider, port1RSelectedValue, totalPortValue, 1));

        playerTreasure1Slider.valueProperty().addListener(new SliderChangeListener(playerTreasure1Slider, playerTreasure1SelectedValue, totalPlayerValue, 5));
        playerTreasure2Slider.valueProperty().addListener(new SliderChangeListener(playerTreasure2Slider, playerTreasure2SelectedValue, totalPlayerValue, 5));
        playerTreasure3Slider.valueProperty().addListener(new SliderChangeListener(playerTreasure3Slider, playerTreasure3SelectedValue, totalPlayerValue, 4));
        playerTreasure4Slider.valueProperty().addListener(new SliderChangeListener(playerTreasure4Slider, playerTreasure4SelectedValue, totalPlayerValue, 5));
        playerTreasure5Slider.valueProperty().addListener(new SliderChangeListener(playerTreasure5Slider, playerTreasure5SelectedValue, totalPlayerValue, 2));

        portTreasure1Slider.valueProperty().addListener(new SliderChangeListener(portTreasure1Slider, portTreasure1SelectedValue, totalPortValue, 5));
        portTreasure2Slider.valueProperty().addListener(new SliderChangeListener(portTreasure2Slider, portTreasure2SelectedValue, totalPortValue, 5));
        portTreasure3Slider.valueProperty().addListener(new SliderChangeListener(portTreasure3Slider, portTreasure3SelectedValue, totalPortValue, 4));
        portTreasure4Slider.valueProperty().addListener(new SliderChangeListener(portTreasure4Slider, portTreasure4SelectedValue, totalPortValue, 5));
        portTreasure5Slider.valueProperty().addListener(new SliderChangeListener(portTreasure5Slider, portTreasure5SelectedValue, totalPortValue, 2));

        // Generate total value listeners
        final ChangeListener totalPlayerChangeListener = (ChangeListener<Integer>) (observableValue, oldValue, newValue) -> selectedPlayerValue.setText(totalPlayerValue.getValue().toString());
        totalPlayerValue.addListener(totalPlayerChangeListener);

        final ChangeListener totalPortChangeListener = (ChangeListener<Integer>) (observableValue, oldValue, newValue) -> selectedPortValue.setText(totalPortValue.getValue().toString());
        totalPortValue.addListener(totalPortChangeListener);
    }

    private void completeTrade() {
        //FOR DEBUG AND TESTING PURPOSES
        String oldPlayerCards = Arrays.toString(currentPlayer.cardsDescription());
        String oldPortCards = Arrays.toString(currentPort.cardsDescription());
        String oldPlayerTreasure = Arrays.toString(currentPlayer.treasureDescription());
        String oldPortTreasure = Arrays.toString(currentPort.treasureDescription());

        //Read slider values and move cards accordingly TODO convert to array to reduce code
        moveCardToPort(new CrewCard(0, 3), (int) player3BSlider.getValue());
        moveCardToPort(new CrewCard(0, 2), (int) player2BSlider.getValue());
        moveCardToPort(new CrewCard(0, 1), (int) player1BSlider.getValue());
        moveCardToPort(new CrewCard(1, 3), (int) player3RSlider.getValue());
        moveCardToPort(new CrewCard(1, 2), (int) player2RSlider.getValue());
        moveCardToPort(new CrewCard(1, 1), (int) player1RSlider.getValue());

        moveCardToPlayer(new CrewCard(0, 3), (int) port3BSlider.getValue());
        moveCardToPlayer(new CrewCard(0, 2), (int) port2BSlider.getValue());
        moveCardToPlayer(new CrewCard(0, 1), (int) port1BSlider.getValue());
        moveCardToPlayer(new CrewCard(1, 3), (int) port3RSlider.getValue());
        moveCardToPlayer(new CrewCard(1, 2), (int) port2RSlider.getValue());
        moveCardToPlayer(new CrewCard(1, 1), (int) port1RSlider.getValue());

        moveTreasureToPort(new Treasure("DIAMOND", Treasures.DIAMOND), (int) playerTreasure1Slider.getValue());
        moveTreasureToPort(new Treasure("RUBIN", Treasures.RUBIN), (int) playerTreasure2Slider.getValue());
        moveTreasureToPort(new Treasure("GOLD", Treasures.GOLD), (int) playerTreasure3Slider.getValue());
        moveTreasureToPort(new Treasure("PEARL", Treasures.PEARL), (int) playerTreasure4Slider.getValue());
        moveTreasureToPort(new Treasure("BARREL", Treasures.BARREL), (int) playerTreasure5Slider.getValue());

        moveTreasureToPlayer(new Treasure("DIAMOND", Treasures.DIAMOND), (int) portTreasure1Slider.getValue());
        moveTreasureToPlayer(new Treasure("RUBIN", Treasures.RUBIN), (int) portTreasure2Slider.getValue());
        moveTreasureToPlayer(new Treasure("GOLD", Treasures.GOLD), (int) portTreasure3Slider.getValue());
        moveTreasureToPlayer(new Treasure("PEARL", Treasures.PEARL), (int) portTreasure4Slider.getValue());
        moveTreasureToPlayer(new Treasure("BARREL", Treasures.BARREL), (int) portTreasure5Slider.getValue());


        //FOR DEBUG AND TESTING PURPOSES
        System.out.println("Old Cards: Player, Port");
        System.out.println(oldPlayerCards);
        System.out.println(oldPortCards);
        System.out.println("New Cards: Player, Port");
        System.out.println(Arrays.toString(currentPlayer.cardsDescription()));
        System.out.println(Arrays.toString(currentPort.cardsDescription()));
        System.out.println("Old Treasure: Player, Port");
        System.out.println(oldPlayerTreasure);
        System.out.println(oldPortTreasure);
        System.out.println("New Treasure: Player, Port");
        System.out.println(Arrays.toString(currentPlayer.treasureDescription()));
        System.out.println(Arrays.toString(currentPort.treasureDescription()));
    }

    private void moveCardToPort(CrewCard card, int count) {
        if (count == 0) return;
        IntStream.range(0, count).forEach(i -> currentPort.addCard(card));
        IntStream.range(0, count).forEach(i -> currentPlayer.removeCard(card));
    }

    private void moveCardToPlayer(CrewCard card, int count) {
        if (count == 0) return;
        IntStream.range(0, count).forEach(i -> currentPlayer.addCard(card));
        IntStream.range(0, count).forEach(i -> currentPort.removeCard(card));
    }

    private void moveTreasureToPort(Treasure treasure, int count){
        if (count == 0) return;
        IntStream.range(0, count).forEach(i -> currentPort.addTreasure(treasure));
        IntStream.range(0, count).forEach(i -> currentPlayer.removeTreasure(treasure));
    }

    private void moveTreasureToPlayer(Treasure treasure, int count){
        if (count == 0) return;
        IntStream.range(0, count).forEach(i -> currentPlayer.addTreasure(treasure));
        IntStream.range(0, count).forEach(i -> currentPort.removeTreasure(treasure));
    }

}