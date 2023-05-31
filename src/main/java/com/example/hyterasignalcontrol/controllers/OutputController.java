package com.example.hyterasignalcontrol.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

public class OutputController implements Initializable {
    public TextArea id_taText;
    public TextArea id_taEncodeText;
    public JFXButton id_btnSend;
    public JFXButton id_btnStop;
    public JFXButton id_btnClear;
    public JFXButton id_btnSetting;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }
}
