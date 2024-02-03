package com.example.panes.components;

import javafx.event.ActionEvent;
import javafx.scene.Node;

public class BattleScreenController {

    public void endBattle(ActionEvent event) {
        Node battlePopup = ((Node)event.getSource()).getScene().lookup("#battle");
        battlePopup.setDisable(true);
        battlePopup.setOpacity(0);
    }
}
