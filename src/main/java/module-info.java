
module com.example.hyterasignalcontrol {

    requires javafx.controls;
    requires javafx.fxml;
    requires org.kordamp.bootstrapfx.core;
    requires com.jfoenix;
    requires org.apache.logging.log4j;
    requires org.apache.poi.examples;
    requires org.apache.poi.ooxml;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml.schemas;
    requires java.desktop;

    exports com.example.hyterasignalcontrol.modules;
    opens com.example.hyterasignalcontrol.modules to javafx.fxml;

    exports com.example.hyterasignalcontrol;
    opens com.example.hyterasignalcontrol to javafx.fxml;

    exports com.example.hyterasignalcontrol.controllers;
    opens com.example.hyterasignalcontrol.controllers to javafx.fxml;

    exports com.example.hyterasignalcontrol.kerak;
    opens com.example.hyterasignalcontrol.kerak to javafx.fxml;
}