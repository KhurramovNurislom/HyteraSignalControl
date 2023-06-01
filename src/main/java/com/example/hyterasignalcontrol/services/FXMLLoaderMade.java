package com.example.hyterasignalcontrol.services;

import com.example.hyterasignalcontrol.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;

public class FXMLLoaderMade {
    public Pane getPane(String fileName) throws IOException {

        URL fileURL = Main.class.getResource("fxml/" + fileName + ".fxml");
        if (fileURL == null) {
            throw new java.io.FileNotFoundException("FXML file topilmadi");
        }

        return FXMLLoader.load(fileURL);
    }
}
