module com.example.buccaneer {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.buccaneer to javafx.fxml;
    exports com.example.buccaneer;
}