package uk.ac.aber.cs221.gp02;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.ArrayList;

public class PopupController {

    @FXML
    Pane mainPane;

    @FXML
    Text message = new Text();

    String textMessage;
    int numButtons;
    ArrayList<Button> buttons;
    ArrayList<String> labels;

    public PopupController(String text, int nButtons, ArrayList<String> l){
        textMessage = text;
        numButtons = nButtons;
        labels = l;
    }

    public PopupController(String text, int nButtons, ArrayList<String> l, int id){
        textMessage = text+" id= "+id;
        numButtons = nButtons;
        labels = l;
    }

    @FXML
    public void initialize(){
        message.setText(textMessage);
        buttons = new ArrayList<>();
        int x = 490-100;
        int s = x/numButtons;

        for(int i = 0; i < numButtons; i++){
            Button b = new Button();
            b.setLayoutX(i*s+50);
            b.setLayoutY(200);
            b.setText(labels.get(i));
            b.setMinSize(70,30);
            int finalI = i;
            b.setOnAction(e -> {
                Game.setPopupButtonID(finalI);
                closeWindow();
            });
            buttons.add(b);
            mainPane.getChildren().add(b);
        }
    }

    @FXML
    private void closeWindow() {
        Stage window = (Stage) mainPane.getScene().getWindow();
        window.close();
    }
}
