module com.example.hyterasignalcontrol {
    requires javafx.controls;
    requires javafx.fxml;
            
                        requires org.kordamp.bootstrapfx.core;
            
    opens com.example.hyterasignalcontrol to javafx.fxml;
    exports com.example.hyterasignalcontrol;
    exports com.example.hyterasignalcontrol.controllers;
    opens com.example.hyterasignalcontrol.controllers to javafx.fxml;
}