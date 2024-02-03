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
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.stream.IntStream;

import static uk.ac.aber.cs221.gp02.Main.PATH_TO_TEXTURES;

public class SafeZoneController implements Initializable {

    @FXML
    private Pane pane;

    @FXML
    private Button tradeButton, closeButton, rulesButton;

    @FXML
    private TextArea eventArea;

    @FXML
    private Slider portTreasure1Slider, portTreasure2Slider, portTreasure3Slider, portTreasure4Slider, portTreasure5Slider;

    @FXML
    private Group buttonsGroup;

    @FXML
    private Label portName, playerName, selectedPortValue, selectedTreasureValue, portTreasure1SelectedValue, portTreasure2SelectedValue, portTreasure3SelectedValue, portTreasure4SelectedValue,
            portTreasure5SelectedValue, portTreasure1AvailableValue, portTreasure2AvailableValue, portTreasure3AvailableValue,
            portTreasure4AvailableValue, portTreasure5AvailableValue, safeZoneValue1, safeZoneValue2, safeZoneValue3, safeZoneValue4, safeZoneValue5;
    @FXML
    private Rectangle shipIcon, portIcon;

    private final Player currentPlayer;
    private ImagePattern[] portTextures;
    private ImagePattern[] shipTextures;
    private IntegerProperty totalSelectedValue;

    private Slider[] portTreasureSliders;
    private Label[] portTreasureAvailableValue;
    private Label[] safeZoneTreasure;


    public SafeZoneController(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
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

        //Add sliders and labels to array

        portTreasureSliders = new Slider[] {portTreasure1Slider, portTreasure2Slider, portTreasure3Slider, portTreasure4Slider, portTreasure5Slider};
        portTreasureAvailableValue = new Label[] {portTreasure1AvailableValue, portTreasure2AvailableValue, portTreasure3AvailableValue, portTreasure4AvailableValue, portTreasure5AvailableValue};
        safeZoneTreasure = new Label[] {safeZoneValue1, safeZoneValue2, safeZoneValue3, safeZoneValue4, safeZoneValue5};


        //Set Player and Port names and draw textures
        playerName.setText(currentPlayer.getName());
        drawTextures();

        //Configure Properties, Labels, Sliders, and Listeners
        totalSelectedValue = new SimpleIntegerProperty(0);
        initialiseValues();
        initializeListeners();

        //Trade button press
        tradeButton.setOnAction(e -> {
            completeTransfer();});
        closeButton.setOnAction(e -> {
            closeSafeZone();});
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
        switch (currentPlayer.getHomePort().getName()) {
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

        // Load available treasure
        int count = 0;
        for (Label l : portTreasureAvailableValue){
            if (currentPlayer.getHomePort().treasureDescription()[count] == 0) {
                l.getParent().setVisible(false);
                l.getParent().setManaged(false);
            } else { l.setText(String.valueOf(currentPlayer.getHomePort().treasureDescription()[count])); }
            count++;
        }

        count = 0;
        for (Label l : safeZoneTreasure){
            if (currentPlayer.getHomePort().safeZoneDescription()[count] == 0) {
                l.getParent().setVisible(false);
                l.getParent().setManaged(false);
            } else { l.setText(String.valueOf(currentPlayer.getHomePort().safeZoneDescription()[count])); }
            count++;
        }

        //Set the sliders
        count = 0;
        for (Slider s : portTreasureSliders){
            s.setMax(currentPlayer.getHomePort().treasureDescription()[count]);
            count++;
        }
    }

    private void initializeListeners() {


        portTreasure1Slider.valueProperty().addListener(new SliderChangeListener(portTreasure1Slider, portTreasure1SelectedValue, totalSelectedValue, 5));
        portTreasure2Slider.valueProperty().addListener(new SliderChangeListener(portTreasure2Slider, portTreasure2SelectedValue, totalSelectedValue, 5));
        portTreasure3Slider.valueProperty().addListener(new SliderChangeListener(portTreasure3Slider, portTreasure3SelectedValue, totalSelectedValue, 4));
        portTreasure4Slider.valueProperty().addListener(new SliderChangeListener(portTreasure4Slider, portTreasure4SelectedValue, totalSelectedValue, 3));
        portTreasure5Slider.valueProperty().addListener(new SliderChangeListener(portTreasure5Slider, portTreasure5SelectedValue, totalSelectedValue, 2));

        // Generate total value listeners
        final ChangeListener totalPlayerChangeListener = (ChangeListener<Integer>) (observableValue, oldValue, newValue) -> selectedTreasureValue.setText(totalSelectedValue.getValue().toString());
        totalSelectedValue.addListener(totalPlayerChangeListener);

    }

    private void completeTransfer() {



        moveTreasureToSafeZone(new Treasure("DIAMOND", TreasureType.DIAMOND), (int) portTreasure1Slider.getValue());
        moveTreasureToSafeZone(new Treasure("RUBIN", TreasureType.RUBIN), (int) portTreasure2Slider.getValue());
        moveTreasureToSafeZone(new Treasure("GOLD", TreasureType.GOLD), (int) portTreasure3Slider.getValue());
        moveTreasureToSafeZone(new Treasure("PEARL", TreasureType.PEARL), (int) portTreasure4Slider.getValue());
        moveTreasureToSafeZone(new Treasure("BARREL", TreasureType.BARREL), (int) portTreasure5Slider.getValue());

        closeSafeZone();

    }

    private void moveTreasureToSafeZone(Treasure treasure, int count){

        if (count == 0) return;
        IntStream.range(0, count).forEach(i -> currentPlayer.getHomePort().addTreasureToSafeZone(treasure));
        IntStream.range(0, count).forEach(i -> currentPlayer.getHomePort().removeTreasure(treasure));
    }

    private void closeSafeZone() {
        closeButton.getScene().getWindow().hide();
    }

}