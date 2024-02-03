package uk.ac.aber.cs221.gp02;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.w3c.dom.Text;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class NicknamesController extends MainMenuController {

    @FXML
    public TextField p1Field;
    @FXML
    public TextField p2Field;
    @FXML
    public TextField p3Field;
    @FXML
    public TextField p4Field;
    @FXML
    private Label errorLabel;

    private ArrayList<TextField> textFields;

    @FXML
    private Button playBtn;

    public void initialize() {
        textFields = new ArrayList<>(Arrays.asList(
                p1Field,
                p2Field,
                p3Field,
                p4Field));

        for (TextField txt : textFields)
            txt.textProperty().addListener(observable -> checkNicknames());
    }

    private void checkNicknames() {
        if (checkValidity()) { // if all nicknames are valid enable play button
            playBtn.setDisable(false);
            playBtn.setOpacity(1);
        } else { // else disable it
            playBtn.setDisable(true);
            playBtn.setOpacity(0.5);
        }
    }

    private boolean checkValidity() {
        Set<String> set = new HashSet<>();
        for (TextField txt : textFields) {
            if (txt.getText().length() == 0) { // nicknames cannot be empty
                errorLabel.setText("Nicknames cannot be empty");
                return false;
            }
            if (txt.getText().length() > 10) { // nicknames must be 16 or fewer characters long
                errorLabel.setText(txt.getText() + " is too long");
                return false;
            }
            if (!txt.getText().matches("^[a-zA-Z0-9]*$")) { // nicknames can only be alphanumeric
                errorLabel.setText("No special characters allowed in " + txt.getText());
                return false;
            }
            set.add(txt.getText());
        }

        if (set.size() < textFields.size()) { // true if there are duplicates
            errorLabel.setText("Names cannot be the same");
            return false;
        }

        // if valid in all cases above (not returned false)
        errorLabel.setText("");
        return true;
    }


    @FXML
    private void playAction(ActionEvent event) throws IOException {
        // Check all names have been entered
        if (!p1Field.getText().equals("") &&
                !p2Field.getText().equals("") &&
                !p3Field.getText().equals("") &&
                !p4Field.getText().equals("")) {
            System.out.println("All names entered");
        }

        Game.setNicknames(new String[]{p1Field.getText(), p2Field.getText(), p3Field.getText(), p4Field.getText()});
        Game.initialize();


        FXMLLoader loader = new FXMLLoader(getClass().getResource("game.fxml"));
        Scene gameScene = new Scene(loader.load());
        //  GamePaneController gamePaneController = loader.getController();
        //gamePaneController.setNames(new String[]{p1Field.getText(), p2Field.getText(), p3Field.getText(), p4Field.getText()});


        changeScene(event, gameScene);

    }

    @FXML
    private void backAction(ActionEvent event) throws IOException {
        super.changeScene(event, "mainMenu.fxml");
    }
}