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
    private static final int DOT = 20, FREQ = 800;

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

                /**  Belgi bilan ishlash */
                List<Integer> list = new ArrayList<Integer>();

                for (char c : text.toCharArray()) {
                    String string_number = Integer.toBinaryString(c);
//                    System.out.println("belgining binar ko'rinishi => " + string_number);
                    String newCharBinary = "";
                    int bitCounter = 0;
                    for (char ch : string_number.toCharArray()) {
                        bitCounter++;
                    }
                    for (int i = 0; i < 8 - bitCounter; i++) {
                        newCharBinary += "0";
                    }
                    newCharBinary += string_number;
                    System.out.println("old qismi 0 bilan to'ldirilib standart 8 xonaga keltirildi => " + newCharBinary);

                    for (char b : newCharBinary.toCharArray()) {
                        if (b == 48)
                            list.add(0);
                        else list.add(1);
                    }
                }

/****** textni binarniyda ko'rish ishlamadi *****/

                /** Signal bilan ishlash */

                try (SourceDataLine sdl = AudioSystem.getSourceDataLine(new AudioFormat(8000F, 8, 1, true, false))) {
                    sdl.open(sdl.getFormat());
                    sdl.start();

                    int textSize = DOT * 128 * list.size();
                    int bitSize = DOT * 128;

                    int k = 0;
                    int n = 0;
                    int m = 0;

                    String te = list.get(0).toString();

                    while (k < textSize) {
                        if (n < bitSize) {
                            sdl.write(new byte[]{(byte) (Math.sin((k * list.get(m)) / (8000F / FREQ) * 2.0 * Math.PI) * 127.0)}, 0, 1);
                            k++;
                            n++;
                        } else {
                            n = 0;
                            m++;
                            te += list.get(m);
                            id_taEncodeText.setText(te);
                        }
                    }
                    sdl.drain();
                    System.out.println("TUGADI");
                } catch (LineUnavailableException e) {
                    e.printStackTrace();
                }
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
}


//    /***********************  Signalni ijro etmasdan filega generatsiya qilish */
//
//    public void WaveGenerator(double n) throws LineUnavailableException {
//        // Audio formati
//        AudioFormat format = new AudioFormat(sampleRate, 16, 1, true, true);
//        // AudioDataLine yaratish
//        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
//        SourceDataLine line = null;
//        line = (SourceDataLine) AudioSystem.getLine(info);
//        line.open(format);
//        line.start();
//        // Signalni hosil qilish
//        byte[] buffer = new byte[(int) (duration * sampleRate)];
//        for (int i = 0; i < buffer.length; i++) {
//            double angle = 2.0 * Math.PI * frequency * i / sampleRate;
//            double sample = 0;
//            // Sinus signal
//            sample = amplitude * (n) * Math.sin(angle);
////            System.out.println("asdasdad => " + Math.sin(angle));
//            // Kvadrat signal
////             sample = (Math.sin(angle) > 0) ? amplitude : -amplitude;
//            // Uchburchak signal
////             sample = (2 * amplitude / Math.PI) * Math.asin(Math.sin(angle));
//            buffer[i] = (byte) (sample * 127);
//        }
//        // Signalni ijro etish
//        line.write(buffer, 0, buffer.length);
//        line.drain();
//        line.close();
//    }


/*********Send function ishlash kerak *********/

//    private void SendFunction(String text) {
//
//        thread = new Thread() {
//            @Override
//            public void run() {
//
////                int k = 0;
////                for (char c : text.toCharArray()) {
////                    String string_number = Integer.toString(c);
////                    for (int i = 0; i < string_number.length(); i++) {
////                        k++;
////                    }
////                    k++;
////                }
//
//                /**********  Chastotali modulyatsiya  **********/
////               System.out.println("kerak => k = " + k);
//                List<Integer> list = new ArrayList<Integer>();
//                int n;
//                for (char c : text.toCharArray()) {
//                    String string_number = Integer.toString(c);
//                    for (int i = 0; i < string_number.length(); i++) {
//                        list.add(Integer.parseInt(String.valueOf(string_number.charAt(i))));
//                    }
//                    list.add(10);
//                }
//                int counter = 0;
//                int t = 0;
//
//
//                try (SourceDataLine sdl = AudioSystem.getSourceDataLine(new AudioFormat(8000F, 8, 1, true, false))) {
//                    sdl.open(sdl.getFormat());
//                    sdl.start();
//
//
//                    for (int k = 0; k < 10000; k++) {
//                        for (int l = 1; l < 13; l++) {
//                            for (int i = 0; i < DOT * 128; i++) {
//                                sdl.write(new byte[]{(byte) (Math.sin(i / ((8000F * l) / FREQ) * 2.0 * Math.PI) * 127.0)}, 0, 1);
//                            }
//                        }
//                    }
//
//
//                    /** Tanituvchi signal */
////                    for (int i = 0; i < DOT * 128 * 3; i++) {
////                        sdl.write(new byte[]{(byte) (Math.sin(i / ((8000F * 20) / FREQ) * 2.0 * Math.PI) * 127.0)}, 0, 1);
////                    }
////                    System.out.println("tanituvchi signal");
//
//                    /** Ma'lumot signali */
//            for (int i = 0; i < DOT * 128 * list.size(); i++) {
//
//                counter++;
//                if (counter != DOT * 128) {

//                      sdl.write(new byte[]{(byte) (Math.sin(i / ((8000F * (list.get(t) + 1)) / FREQ) * 2.0 * Math.PI) * 127.0)}, 0, 1);

//                    sdl.write(new byte[]{(byte) (Math.sin(i / ((8000F+ list.get(t)) / FREQ) * 2.0 * Math.PI) * 127.0)}, 0, 1);

//                } else {
//                    counter = 0;
//                    t++;
//                }
//            }

////                    /** tugatuvchi signal */
////                    for (int i = 0; i < DOT * 128 * 3; i++) {
////                        sdl.write(new byte[]{(byte) (Math.sin(i / ((8000F * 20) / FREQ) * 2.0 * Math.PI) * 127.0)}, 0, 1);
////
////                    }
//                    System.out.println("tugatuvchi signal");
//                    sdl.drain();
//                } catch (LineUnavailableException e) {
//                    e.printStackTrace();
//
//                }
//
///**********  Amplituda modulyatsiya *******************/
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
///*****************/
//
//            }
//        };
//        thread.start();
//    }
