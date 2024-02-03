package uk.ac.aber.cs221.gp02;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import static uk.ac.aber.cs221.gp02.Main.PATH_TO_TEXTURES;



public class RulesScreenController extends MainMenuController {

    @FXML
    private ImageView rulesImg;
    @FXML
    private Button backBtn;
    @FXML
    private Button nextBtn;

    @FXML
    private Button iconsBtn;
    @FXML
    private Button gameRulesBtn;

    private int rulesIndex = 1;


    @FXML
    private void menuBtnAction(ActionEvent event) throws IOException {
        super.changeScene(event, "mainMenu.fxml");
    }

    /**
     * Update rules image for each screen
     * @throws FileNotFoundException
     */
    private void updateImage() throws FileNotFoundException {
        rulesImg.setImage(new Image(new FileInputStream("src/main/resources/uk/ac/aber/cs221/gp02/images/GameRulesSlidesPNG/Game_Rules_" + rulesIndex + ".png")));
    }

    /**
     * Update rules image for each screen
     * @throws FileNotFoundException
     */
    private void updateImage(String url) throws FileNotFoundException {
        rulesImg.setImage(new Image(new FileInputStream("src/main/resources/uk/ac/aber/cs221/gp02/images/" + url)));
    }

    /**
     * Go next screen
     * @throws FileNotFoundException
     */
    @FXML
    private void nextBtnAction() throws FileNotFoundException {
        if (rulesIndex == 1) {
            backBtn.setDisable(false);
            backBtn.setOpacity(1);
        }

        if (rulesIndex < 8) {
            rulesIndex++;
            updateImage();
        }

        if (rulesIndex == 8) {
            nextBtn.setDisable(true);
            nextBtn.setOpacity(0);
        }
    }

    /**
     * Go previous screen
     * @throws FileNotFoundException
     */
    @FXML
    private void backBtnAction() throws FileNotFoundException {
        if (rulesIndex == 8) {
            nextBtn.setDisable(false);
            nextBtn.setOpacity(1);
        }

        if (rulesIndex > 1) {
            rulesIndex--;
            updateImage();
        }

        if (rulesIndex == 1) {
            backBtn.setDisable(true);
            backBtn.setOpacity(0);
        }
    }

    /**
     * Go to icons screen
     * @throws FileNotFoundException
     */
    @FXML
    private void iconsBtnAction() throws FileNotFoundException {
        // Enable Game Rules Button
        gameRulesBtn.setDisable(false);

        // Disable Back & Next & Icons buttons
        nextBtn.setDisable(true);
        nextBtn.setOpacity(0);
        backBtn.setDisable(true);
        backBtn.setOpacity(0);
        iconsBtn.setDisable(true);

        updateImage("GameRulesSlidesPNG/Icons_Scroll.png");
    }

    /**
     * Go to game rules
     * @throws FileNotFoundException
     */
    @FXML
    private void gameRulesBtnAction() throws FileNotFoundException {
        // Disable Game Rules Button
        gameRulesBtn.setDisable(true);

        // Enable Back & Next & Game Rules buttons
        nextBtn.setDisable(false);
        nextBtn.setOpacity(1);
        backBtn.setDisable(false);
        backBtn.setOpacity(1);
        iconsBtn.setDisable(false);


        updateImage();
    }
}