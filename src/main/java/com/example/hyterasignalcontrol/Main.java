package com.example.hyterasignalcontrol;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/ControlPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Control page");
        scene.getStylesheets().add
                (Objects.requireNonNull(Main.class.getResource("css/Style.css")).toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}