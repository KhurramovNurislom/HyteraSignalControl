package com.example.hyterasignalcontrol.controllers;

import com.example.hyterasignalcontrol.Main;
import com.example.hyterasignalcontrol.utils.ByteEncode;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

import javax.sound.sampled.*;
import java.io.*;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.ResourceBundle;

@SuppressWarnings("ALL")
public class OutputController implements Initializable {
    public TextArea id_taText;
    public TextArea id_taEncodeText;
    public JFXButton id_btnSend;
    public JFXButton id_btnStop;
    public JFXButton id_btnClear;

    String line;
    private boolean sound;

//    private static final int DOT = 10, FREQ = 800;

    // Signal parametric
//    float frequency = 440; // signal chastotasi (Hz)
//    float sampleRate = 44100; // namunaviy chastotasi (Hz)
//    double duration = 0.2; // signal davomiyligi (soniya)
//    double amplitude = 0.1; // signal amplitudasi (0 dan 1 gacha)


    double frequency = 440; // Signalning chastotasi
    int durationMs = 5000; // Signal davomiyligi milisekundlarda
    int sampleRate = 44100; // Namunaviy chastota

    public static Thread thread;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        id_btnSend.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                id_btnSend.setDisable(true);
                SendFunction();
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

    private void SendFunction() {

        saveSignaltoFile();


//        thread = new Thread() {
//            @Override
//            public void run() {
//                sound = true;
//                line = id_taText.getText();
//
//                int n;
//                for (char c : line.toCharArray()) {
//                    String string_number = Integer.toString(c);
//                    for (int i = 0; i < string_number.length(); i++) {
//                        n = Integer.parseInt(String.valueOf(string_number.charAt(i)));
////                        System.out.println("node => " + n);
////                        sendWave(n);
//
//                        try {
//                            System.out.println(n);
////                            WaveGenerator(n);
//                        } catch (LineUnavailableException e) {
//                            throw new RuntimeException(e);
//                        }
//                    }
//                }
//            }
//        };
//        thread.start();

    }

    private void StopFunction() {
        sound = false;

    }

    private void ClearFunction() {
        id_taText.clear();
        id_taEncodeText.clear();
    }


    /***********************  Signalni ijro etmasdan filega generatsiya qilish */


    public void saveSignaltoFile() {
        AudioFormat format = new AudioFormat(sampleRate, 16, 2, true, true); // Stereofonik format
        int numSamples = durationMs * sampleRate / 1000;
        byte[] signal = new byte[numSamples * 2];

        for (int i = 0; i < signal.length; i += 4) {
            double angle = 2.0 * Math.PI * frequency * i / sampleRate;
            short val = (short) (Math.sin(angle) * Short.MAX_VALUE);
            signal[i] = (byte) (val & 0xff);
            signal[i + 1] = (byte) ((val >> 8) & 0xff);
            signal[i + 2] = signal[i];
            signal[i + 3] = signal[i + 1];
        }
        saveSugnaltoFile(signal, format, numSamples);
    }

    private void saveSugnaltoFile(byte[] signal, AudioFormat format, int numSamples) {
        // Faylga signalni yozish
        try {
            File file = new File("signal.wav");
            AudioSystem.write(new AudioInputStream(new ByteArrayInputStream(signal), format, numSamples), AudioFileFormat.Type.WAVE, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /***************   Auxiliary functions   ***************/


//    public void sendWave(int n) {
//        try (SourceDataLine sdl = AudioSystem.getSourceDataLine(new AudioFormat(8000F, 8, 1, true, false))) {
//            sdl.open(sdl.getFormat());
//            sdl.start();
//            for (int i = 0; i < DOT * 128; i++) {
//                sdl.write(new byte[]{(byte) (Math.sin(i / ((8000F * (n + 1)) / FREQ) * 2.0 * Math.PI) * 127.0)}, 0, 1);
//            }
//            sdl.drain();
//        } catch (LineUnavailableException e) {
//            e.printStackTrace();
//        }
//    }
//    public void WaveGenerator(double n) throws LineUnavailableException {
//
//        // Audio formati
//        AudioFormat format = new AudioFormat(sampleRate, 16, 1, true, true);
//
//        // AudioDataLine yaratish
//        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
//        SourceDataLine line = null;
//
//        line = (SourceDataLine) AudioSystem.getLine(info);
//        line.open(format);
//        line.start();
//
//
//        // Signalni hosil qilish
//        byte[] buffer = new byte[(int) (duration * sampleRate)];
//        for (int i = 0; i < buffer.length; i++) {
//
//
//            double angle = 2.0 * Math.PI * frequency * i / sampleRate;
//            double sample = 0;
//
//            // Sinus signal
////            sample = amplitude * (n ) * Math.sin(angle);
//
////            System.out.println("asdasdad => " + Math.sin(angle));
//            // Kvadrat signal
//             sample = (Math.sin(angle) > 0) ? amplitude : -amplitude;
//
//            // Uchburchak signal
////             sample = (2 * amplitude / Math.PI) * Math.asin(Math.sin(angle));
//
//            buffer[i] = (byte) (sample * 127);
//        }
//
//        // Signalni ijro etish
//        line.write(buffer, 0, buffer.length);
//        line.drain();
//        line.close();
//    }
    private void test(){

    }





}
