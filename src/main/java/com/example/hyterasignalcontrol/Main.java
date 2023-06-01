package com.example.hyterasignalcontrol;


import com.example.hyterasignalcontrol.modules.SignalInfo;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private static SignalInfo param;

    public static SignalInfo getParam() {
        return param;
    }

    public static void setParam(SignalInfo param) {
        Main.param = param;
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/MainPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Main page...");
//        scene.getStylesheets().add
//                (Objects.requireNonNull(Main.class.getResource("css/Style.css")).toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }


}