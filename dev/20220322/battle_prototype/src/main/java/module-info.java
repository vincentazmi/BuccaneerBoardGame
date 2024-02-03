module com.example.battle_prototype {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.battle_prototype to javafx.fxml;
    exports com.example.battle_prototype;
}