package com.example.hyterasignalcontrol;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class SceneChooser {
    //this is for change Scene
    public static void changeScene(ActionEvent event, String fxmlFile, String title) {

        Parent root = null;
        if (event != null) {
            try {
                FXMLLoader loader = new FXMLLoader(SceneChooser.class.getResource(fxmlFile));
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                root = FXMLLoader.load(SceneChooser.class.getResource(fxmlFile));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        Scene scene = new Scene(root);
        scene.getStylesheets().add
                (Objects.requireNonNull(Main.class.getResource("css/Style.css")).toExternalForm());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
