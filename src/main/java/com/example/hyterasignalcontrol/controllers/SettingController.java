package com.example.hyterasignalcontrol.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;


public class SettingController implements Initializable {

    public Button id_btnStart;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        id_btnStart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {


                System.out.println("Bosildi...");

            }
        });
    }


}


/**
 * Online qabul qilish kodlari
 */

//    private void AudioTest() throws LineUnavailableException {
//
///** Micdan ovozni o'qib olish */
//
//        TargetDataLine line;
//        AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 2, 4, 44100, false);
//        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
//        line = (TargetDataLine) AudioSystem.getLine(info);
//        line.open(format);
//        line.start();
//
//        byte[] buffer = new byte[4096];
//        int bufferSize = buffer.length / 2;
//        double[] audioData = new double[bufferSize];
//
//
///** Bufferdagi bytelardan signal ma'lumotlarini olish */
//
//        while (true) {
//            int count = line.read(buffer, 0, buffer.length);
//            if (count > 0) {
//                for (int i = 0, j = 0; i < bufferSize; i++, j += 2) {
//                    double sample = ((buffer[j + 1] << 8) | buffer[j] & 0xFF) / 32768.0;
//                    audioData[i] = sample;
//
//                    System.out.println("sample => " + sample);
//                }
//                FFT fft = new FFT(audioData.length);
//                fft.forward(audioData);
//
//                double[] magnitude = fft.getMagnitude();
//                double[] frequency = fft.getFrequency();
//
//                // Chastota va amplituda ma'lumotlari
//                double maxMagnitude = 0;
//                double dominantFrequency = 0;
//                for (int i = 0; i < frequency.length; i++) {
//                    if (magnitude[i] > maxMagnitude) {
//                        maxMagnitude = magnitude[i];
//                        dominantFrequency = frequency[i];
//                    }
//                }
//                System.out.println("Dominant frequency: " + dominantFrequency + " Hz, Max magnitude: " + maxMagnitude);
//            }
//        }
//
//
//    }
//
//    private void DataLine2() {
//
//    }