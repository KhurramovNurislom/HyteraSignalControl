package com.example.hyterasignalcontrol.controllers;

import com.example.hyterasignalcontrol.services.FXMLLoaderMade;
import com.jfoenix.controls.JFXRadioButton;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    public JFXRadioButton id_rbInputSystem;
    public JFXRadioButton id_rbOutputSystem;
    public Pane id_Pane;
    private final ToggleGroup toggleGroup = new ToggleGroup();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        id_rbOutputSystem.setToggleGroup(toggleGroup);
        id_rbInputSystem.setToggleGroup(toggleGroup);

        id_rbOutputSystem.setSelected(true);

        toggleGroup.selectedToggleProperty().addListener((observable, oldVal, newVal) -> {
            if (toggleGroup.getSelectedToggle() == id_rbOutputSystem) {
                try {
                    id_Pane.getChildren().add(new FXMLLoaderMade().getPane("OutputPage"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                try {
                    id_Pane.getChildren().add(new FXMLLoaderMade().getPane("InputPage"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
