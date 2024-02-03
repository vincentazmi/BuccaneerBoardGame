package uk.ac.aber.cs221.gp02;

import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class BackgroundController implements Initializable {
    @FXML
    private ImageView ship;

    @FXML
    private ImageView ocean;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ocean.setTranslateX(-50);
        setTranslationWithPeriod(ocean, 100, 0, 3000);
        setTranslationWithPeriod(ocean, 0, 25, 1300);

        setTranslationWithPeriod(ship, 30, 0, 1500);
        setTranslationWithPeriod(ship, 0, 50, 2500);

        ship.setRotate(-3);
        setRotationWithPeriod(ship, 6, 1500);
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
}
