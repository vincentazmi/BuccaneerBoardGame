package uk.ac.aber.cs221.gp02;

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
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.IntStream;


import static uk.ac.aber.cs221.gp02.Main.PATH_TO_TEXTURES;

public class TradeController implements Initializable {

    @FXML
    private Pane pane;

    @FXML
    private Button tradeButton, closeButton, rulesButton;

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

    private final Player currentPlayer;
    private final Port currentPort;
    private ImagePattern[] portTextures;
    private ImagePattern[] shipTextures;
    private IntegerProperty totalPlayerValue;
    private IntegerProperty totalPortValue;

    private Slider[] portCardsSliders;
    private Slider[] playerCardsSliders;
    private Slider[] portTreasureSliders;
    private Slider[] playerTreasureSliders;
    private Label[] portCardsAvailableValue;
    private Label[] portTreasureAvailableValue;
    private Label[] playerCardsAvailableValue;
    private Label[] playerTreasureAvailableValue;


    public TradeController(Player currentPlayer, Port currentPort) {
        this.currentPlayer = currentPlayer;
        this.currentPort = currentPort;
    }

//    public TradeController(Player currentPlayer, TreasureIsland) {
//        this.currentPlayer = currentPlayer;
//        this.currentPort = currentPort;
//    }

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

        //Add sliders and labels to array
        // portTreasure1Slider
        portCardsSliders = new Slider[] {port3BSlider,port2BSlider,port1BSlider,port3RSlider, port2RSlider, port1RSlider};
        playerCardsSliders = new Slider[] {player3BSlider,player2BSlider,player1BSlider,player3RSlider, player2RSlider, player1RSlider};
        portTreasureSliders = new Slider[] {portTreasure1Slider, portTreasure2Slider, portTreasure3Slider, portTreasure4Slider, portTreasure5Slider};
        playerTreasureSliders = new Slider[] {playerTreasure1Slider, playerTreasure2Slider, playerTreasure3Slider, playerTreasure4Slider, playerTreasure5Slider};
        portCardsAvailableValue = new Label[] {port3BAvailableValue, port2BAvailableValue, port1BAvailableValue, port3RAvailableValue, port2RAvailableValue, port1RAvailableValue};
        portTreasureAvailableValue = new Label[] {portTreasure1AvailableValue, portTreasure2AvailableValue, portTreasure3AvailableValue, portTreasure4AvailableValue, portTreasure5AvailableValue};
        playerCardsAvailableValue = new Label[] {player3BAvailableValue, player2BAvailableValue, player1BAvailableValue, player3RAvailableValue, player2RAvailableValue, player1RAvailableValue};
        playerTreasureAvailableValue = new Label[] {playerTreasure1AvailableValue, playerTreasure2AvailableValue, playerTreasure3AvailableValue, playerTreasure4AvailableValue, playerTreasure5AvailableValue};


        //Set Player and Port names and draw textures
        playerName.setText(currentPlayer.getName());
        portName.setText(currentPort.getName());
        drawTextures();

        //Configure Properties, Labels, Sliders, and Listeners
        totalPlayerValue = new SimpleIntegerProperty(0);
        totalPortValue = new SimpleIntegerProperty(0);
        initialiseValues();
        initializeListeners();

        //Trade button press
        tradeButton.setOnAction(e -> {completeTrade();});
        closeButton.setOnAction(e -> {closeTrade();});
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

        // Load available crew cards
        int count = 0;
        for (Label l : playerCardsAvailableValue){
            if (currentPlayer.cardsDescription()[count] == 0) {
                l.getParent().setVisible(false);
                l.getParent().setManaged(false);
            } else { l.setText(String.valueOf(currentPlayer.cardsDescription()[count])); }
            count++;
        }
        count = 0;
        for (Label l : portCardsAvailableValue){
            if (currentPort.cardsDescription()[count] == 0) {
                l.getParent().setVisible(false);
                l.getParent().setManaged(false);
            } else { l.setText(String.valueOf(currentPort.cardsDescription()[count])); }
            count++;
        }
        count = 0;
        for (Label l : playerTreasureAvailableValue){
            if (currentPlayer.treasureDescription()[count] == 0) {
                l.getParent().setVisible(false);
                l.getParent().setManaged(false);
            } else { l.setText(String.valueOf(currentPlayer.treasureDescription()[count])); }
            count++;
        }
        count = 0;
        for (Label l : portTreasureAvailableValue){
            if (currentPort.treasureDescription()[count] == 0) {
                l.getParent().setVisible(false);
                l.getParent().setManaged(false);
            } else { l.setText(String.valueOf(currentPort.treasureDescription()[count])); }
            count++;
        }


        //Set the sliders
        count = 0;
        for (Slider s : playerCardsSliders){
            s.setMax(currentPlayer.cardsDescription()[count]);
            count++;
        }
        count = 0;
        for (Slider s : portCardsSliders){
            s.setMax(currentPort.cardsDescription()[count]);
            count++;
        }
        count = 0;
        for (Slider s : playerTreasureSliders){
            s.setMax(currentPlayer.treasureDescription()[count]);
            count++;
        }
        count = 0;
        for (Slider s : portTreasureSliders){
            s.setMax(currentPort.treasureDescription()[count]);
            count++;
        }
    }

    private void initializeListeners() {


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
        playerTreasure4Slider.valueProperty().addListener(new SliderChangeListener(playerTreasure4Slider, playerTreasure4SelectedValue, totalPlayerValue, 3));
        playerTreasure5Slider.valueProperty().addListener(new SliderChangeListener(playerTreasure5Slider, playerTreasure5SelectedValue, totalPlayerValue, 2));

        portTreasure1Slider.valueProperty().addListener(new SliderChangeListener(portTreasure1Slider, portTreasure1SelectedValue, totalPortValue, 5));
        portTreasure2Slider.valueProperty().addListener(new SliderChangeListener(portTreasure2Slider, portTreasure2SelectedValue, totalPortValue, 5));
        portTreasure3Slider.valueProperty().addListener(new SliderChangeListener(portTreasure3Slider, portTreasure3SelectedValue, totalPortValue, 4));
        portTreasure4Slider.valueProperty().addListener(new SliderChangeListener(portTreasure4Slider, portTreasure4SelectedValue, totalPortValue, 3));
        portTreasure5Slider.valueProperty().addListener(new SliderChangeListener(portTreasure5Slider, portTreasure5SelectedValue, totalPortValue, 2));

        // Generate total value listeners
        final ChangeListener totalPlayerChangeListener = (ChangeListener<Integer>) (observableValue, oldValue, newValue) -> selectedPlayerValue.setText(totalPlayerValue.getValue().toString());
        totalPlayerValue.addListener(totalPlayerChangeListener);

        final ChangeListener totalPortChangeListener = (ChangeListener<Integer>) (observableValue, oldValue, newValue) -> selectedPortValue.setText(totalPortValue.getValue().toString());
        totalPortValue.addListener(totalPortChangeListener);
    }

    private void completeTrade() {

        //Do trade checks (equal value and ship not carrying more than 2 treasure after trade)
        //TODO implement this for the port as well? since they are synced with the ship
        if (!Objects.equals(totalPlayerValue.getValue(), totalPortValue.getValue()) || totalPlayerValue.getValue() == 0 || totalPortValue.getValue() == 0) {
            eventArea.setStyle("-fx-text-fill: #a60000;");
            eventArea.setText("Could not complete trade, selected values do not match or no value was selected");
            System.err.println("Could not complete trade, selected values do not match or no value was selected");
            return;
        } else if (portTreasure1Slider.getValue() + currentPlayer.treasureDescription()[0] > 2){
            eventArea.setStyle("-fx-text-fill: #a60000;");
            eventArea.setText("Could not complete trade, the ship can't carry more than 2 diamonds");
            System.err.println("Could not complete trade, the ship can't carry more than 2 diamonds");
            return;
        } else if (portTreasure2Slider.getValue() + currentPlayer.treasureDescription()[1] > 2){
            eventArea.setStyle("-fx-text-fill: #a60000;");
            eventArea.setText("Could not complete trade, the ship can't carry more than 2 rubies");
            System.err.println("Could not complete trade, the ship can't carry more than 2 rubies");
            return;
        } else if (portTreasure3Slider.getValue() + currentPlayer.treasureDescription()[2] > 2){
            eventArea.setStyle("-fx-text-fill: #a60000;");
            eventArea.setText("Could not complete trade, the ship can't carry more than 2 gold");
            System.err.println("Could not complete trade, the ship can't carry more than 2 gold");
            return;
        } else if (portTreasure4Slider.getValue() + currentPlayer.treasureDescription()[3] > 2){
            eventArea.setStyle("-fx-text-fill: #a60000;");
            eventArea.setText("Could not complete trade, the ship can't carry more than 2 pearls");
            System.err.println("Could not complete trade, the ship can't carry more than 2 pearls");
            return;
        } else if (portTreasure5Slider.getValue() + currentPlayer.treasureDescription()[4] > 2){
            eventArea.setStyle("-fx-text-fill: #a60000;");
            eventArea.setText("Could not complete trade, the ship can't carry more than 2 barrels");
            System.err.println("Could not complete trade, the ship can't carry more than 2 barrels");
            return;
        } else {
            eventArea.setStyle("-fx-text-fill: #00a400; -fx-font-size: 20px;");
            eventArea.setText("Trade Complete");
        }


        //FOR DEBUG AND TESTING PURPOSES TODO remove after testing
        String oldPlayerCards = Arrays.toString(currentPlayer.cardsDescription());
        String oldPortCards = Arrays.toString(currentPort.cardsDescription());
        String oldPlayerTreasure = Arrays.toString(currentPlayer.treasureDescription());
        String oldPortTreasure = Arrays.toString(currentPort.treasureDescription());
        //********************

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

        moveTreasureToPort(new Treasure("DIAMOND", TreasureType.DIAMOND), (int) playerTreasure1Slider.getValue());
        moveTreasureToPort(new Treasure("RUBIN", TreasureType.RUBIN), (int) playerTreasure2Slider.getValue());
        moveTreasureToPort(new Treasure("GOLD", TreasureType.GOLD), (int) playerTreasure3Slider.getValue());
        moveTreasureToPort(new Treasure("PEARL", TreasureType.PEARL), (int) playerTreasure4Slider.getValue());
        moveTreasureToPort(new Treasure("BARREL", TreasureType.BARREL), (int) playerTreasure5Slider.getValue());

        moveTreasureToPlayer(new Treasure("DIAMOND", TreasureType.DIAMOND), (int) portTreasure1Slider.getValue());
        moveTreasureToPlayer(new Treasure("RUBIN", TreasureType.RUBIN), (int) portTreasure2Slider.getValue());
        moveTreasureToPlayer(new Treasure("GOLD", TreasureType.GOLD), (int) portTreasure3Slider.getValue());
        moveTreasureToPlayer(new Treasure("PEARL", TreasureType.PEARL), (int) portTreasure4Slider.getValue());
        moveTreasureToPlayer(new Treasure("BARREL", TreasureType.BARREL), (int) portTreasure5Slider.getValue());

        Game.changeState(State.NEXT_TURN);


        //FOR DEBUG AND TESTING PURPOSES TODO remove after testing
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
        //********************
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
    private void closeTrade() {
        closeButton.getScene().getWindow().hide();
    }

}