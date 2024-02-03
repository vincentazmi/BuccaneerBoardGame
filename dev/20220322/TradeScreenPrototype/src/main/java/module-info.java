module uk.ac.aber.cs221.gp02.tradescreenprototype {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    opens uk.ac.aber.cs221.gp02.tradescreenprototype to javafx.fxml;
    exports uk.ac.aber.cs221.gp02.tradescreenprototype;
}