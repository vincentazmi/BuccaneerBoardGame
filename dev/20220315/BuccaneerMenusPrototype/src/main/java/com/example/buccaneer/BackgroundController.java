package com.example.buccaneer;

import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class BackgroundController implements Initializable {
    @FXML
    private ImageView boat;

    @FXML
    private ImageView ocean;

    @FXML
    private Rectangle sky;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setSkyGradient();

        setTranslationWithPeriod(ocean, 250, 0, 3000);
        setTranslationWithPeriod(ocean, 0, 50, 1300);

        setTranslationWithPeriod(boat, 30, 0, 1500);
        setTranslationWithPeriod(boat, 0, 50, 2500);

        //Two rotations to allow the boat to go from -5 to 5 instead of 0 to 5
        setRotationWithPeriod(boat, 5, 1000);
        setRotationWithPeriod(boat, -5, 1200);
    }

    //Helper methods
    private void setTranslationWithPeriod(Node node, int x, int y, int millisPeriod) {
        TranslateTransition translation = new TranslateTransition();
        translation.setNode(node);
        translation.setDuration(Duration.millis(millisPeriod));
        translation.setCycleCount(TranslateTransition.INDEFINITE);
        translation.setByX(x);
        translation.setByY(y);
        translation.setAutoReverse(true);
        translation.play();
    }

    private void setRotationWithPeriod(Node node, int angle, int millisPeriod) {
        RotateTransition rotation = new RotateTransition();
        rotation.setNode(node);
        rotation.setDuration(Duration.millis(millisPeriod));
        rotation.setCycleCount(RotateTransition.INDEFINITE);
        rotation.setByAngle(angle);
        rotation.setAutoReverse(true);
        rotation.play();
    }

    private void setSkyGradient() {
        Stop[] stops = new Stop[] {
                new Stop(0.3, Color.CYAN),
                new Stop(1, Color.LIGHTYELLOW)
        };
        LinearGradient gradient = new LinearGradient(
                0, 1,
                0, 0,
                true,
                CycleMethod.NO_CYCLE,
                stops
        );

        sky.setFill(gradient);
    }
}
