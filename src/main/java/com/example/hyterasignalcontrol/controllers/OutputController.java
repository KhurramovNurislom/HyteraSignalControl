package com.example.hyterasignalcontrol.controllers;


import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;

import javax.sound.sampled.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class OutputController implements Initializable {
    public TextArea id_taText;
    public TextArea id_taEncodeText;
    public JFXButton id_btnSend;
    public JFXButton id_btnStop;
    public JFXButton id_btnClear;

    //    private boolean bool;
    private static final int DOT = 10, FREQ = 800;

    // Signal parametric
    float frequency = 440; // signal chastotasi (Hz)
    float sampleRate = 44100; // namunaviy chastotasi (Hz)
    double duration = 0.2; // signal davomiyligi (soniya)
    double amplitude = 0.1; // signal amplitudasi (0 dan 1 gacha)

    public static Thread thread;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        id_btnSend.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                id_btnSend.setDisable(true);
                if (id_taText.getText() == null || id_taText.getText().equals("")) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Xatolik");
                    alert.setHeaderText("Text maydoni bo'sh");
                    alert.setContentText("Text maydonini to'ldiring !");
                    alert.show();
                } else {
                    SendFunction(id_taText.getText());
                }
                id_btnSend.setDisable(false);
            }
        });
        id_btnStop.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                id_btnStop.setDisable(true);
                StopFunction();
                id_btnStop.setDisable(false);
            }
        });
        id_btnClear.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                id_btnClear.setDisable(true);
                ClearFunction();
                id_btnClear.setDisable(false);
            }
        });
    }

    private void SendFunction(String text) {

        thread = new Thread() {
            @Override
            public void run() {


//                int k = 0;
//                for (char c : text.toCharArray()) {
//                    String string_number = Integer.toString(c);
//                    for (int i = 0; i < string_number.length(); i++) {
//                        k++;
//                    }
//                    k++;
//                }


                /**********  Chastotali modulyatsiya  **********/
//               System.out.println("kerak => k = " + k);
               List<Integer> list = new ArrayList<Integer>();
               int n;
               for (char c : text.toCharArray()) {
                   String string_number = Integer.toString(c);
                   for (int i = 0; i < string_number.length(); i++) {
                       list.add(Integer.parseInt(String.valueOf(string_number.charAt(i))));
                   }
                   list.add(10);
               }
               for (int t = 0; t < list.size(); t++) {
                   n = list.get(t);
                   try (SourceDataLine sdl = AudioSystem.getSourceDataLine(new AudioFormat(8000F, 8, 1, true, false))) {
                       sdl.open(sdl.getFormat());
                       sdl.start();
                       for (int i = 0; i < DOT * 128; i++) {
                           sdl.write(new byte[]{(byte) (Math.sin(i / ((8000F * (n + 1)) / FREQ) * 2.0 * Math.PI) * 127.0)}, 0, 1);
                       }
                       sdl.drain();
                   } catch (LineUnavailableException e) {
                       e.printStackTrace();
                   }
               }

/**********  Amplituda modulyatsiya *******************/
//                try {
//                    for (char c : text.toCharArray()) {
//                        String string_number = Integer.toString(c);
//                        for (int i = 0; i < string_number.length(); i++) {
//                            WaveGenerator(Integer.parseInt(String.valueOf(string_number.charAt(i))));
//                        }
//                        WaveGenerator(10);
//                    }
//                } catch (LineUnavailableException e) {
//                    throw new RuntimeException(e);
//                }
/*****************/

            }
        };
        thread.start();
    }

    private void StopFunction() {
//        sound = false;
    }

    private void ClearFunction() {
        id_taText.clear();
        id_taEncodeText.clear();
    }

    /***********************  Signalni ijro etmasdan filega generatsiya qilish */

    public void WaveGenerator(double n) throws LineUnavailableException {
        // Audio formati
        AudioFormat format = new AudioFormat(sampleRate, 16, 1, true, true);
        // AudioDataLine yaratish
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
        SourceDataLine line = null;
        line = (SourceDataLine) AudioSystem.getLine(info);
        line.open(format);
        line.start();
        // Signalni hosil qilish
        byte[] buffer = new byte[(int) (duration * sampleRate)];
        for (int i = 0; i < buffer.length; i++) {
            double angle = 2.0 * Math.PI * frequency * i / sampleRate;
            double sample = 0;
            // Sinus signal
            sample = amplitude * (n) * Math.sin(angle);
//            System.out.println("asdasdad => " + Math.sin(angle));
            // Kvadrat signal
//             sample = (Math.sin(angle) > 0) ? amplitude : -amplitude;
            // Uchburchak signal
//             sample = (2 * amplitude / Math.PI) * Math.asin(Math.sin(angle));
            buffer[i] = (byte) (sample * 127);
        }
        // Signalni ijro etish
        line.write(buffer, 0, buffer.length);
        line.drain();
        line.close();
    }
}
