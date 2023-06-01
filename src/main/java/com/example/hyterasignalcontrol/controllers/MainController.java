package com.example.hyterasignalcontrol.controllers;

import com.example.hyterasignalcontrol.services.FXMLLoaderMade;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    public JFXRadioButton id_rbInputSystem;
    public JFXRadioButton id_rbOutputSystem;
    public Pane id_Pane;
    private final ToggleGroup toggleGroup = new ToggleGroup();
    public JFXButton id_btnSetting;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        id_rbOutputSystem.setToggleGroup(toggleGroup);
        id_rbInputSystem.setToggleGroup(toggleGroup);

        id_rbOutputSystem.setSelected(true);

        ChangeScene("OutputPage");

        id_btnSetting.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ChangePage(0);
                id_rbOutputSystem.setSelected(false);
                id_rbInputSystem.setSelected(false);
            }
        });

        toggleGroup.selectedToggleProperty().addListener((observable, oldVal, newVal) -> {
            if (toggleGroup.getSelectedToggle() == id_rbOutputSystem) {
                ChangePage(1);
            }
            if (toggleGroup.getSelectedToggle() == id_rbInputSystem) {
                ChangePage(2);
            }
        });
    }
    private void ChangePage(int n) {

        switch (n) {
            case 1:
                ChangeScene("OutputPage");
                break;
            case 2:
                ChangeScene("InputPage");
                break;
            default: {
                id_Pane.getChildren().clear();
                ChangeScene("SettingPage");
            }
            break;
        }
    }
    private void ChangeScene(String pageName) {
        try {
            id_Pane.getChildren().add(new FXMLLoaderMade().getPane(pageName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
