module com.example.panes {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.panes to javafx.fxml;
    exports com.example.panes;
    exports com.example.panes.components;
    opens com.example.panes.components to javafx.fxml;
}