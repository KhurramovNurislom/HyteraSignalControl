package com.example.hyterasignalcontrol.services;

import javax.sound.sampled.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class RecorderYouTube {

    public static void main(String[] args) {
        try {
            AudioFormat audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 2, 4, 44100, false);
            DataLine.Info dataInfo = new DataLine.Info(TargetDataLine.class, audioFormat);

            if (!AudioSystem.isLineSupported(dataInfo)) {
                System.out.println("Not Supported");
            }

            TargetDataLine targetLine = (TargetDataLine) AudioSystem.getLine(dataInfo);
            targetLine.open();

            JOptionPane.showMessageDialog(null, "Hi to start recording");
            targetLine.start();

            TargetDataLine finalTargetLine = targetLine;
            Thread audioRecorderThread = new Thread() {
                public void run() {
                    AudioInputStream recordingStream = new AudioInputStream(finalTargetLine);
                    File outputFile = new File("record.wav");

                    try {
                        AudioSystem.write(recordingStream, AudioFileFormat.Type.WAVE, outputFile);
                    } catch (IOException ex) {
                        System.out.println(ex);
                    }
                    System.out.println("Stopped recording");
                }
            };

            audioRecorderThread.start();
            JOptionPane.showMessageDialog(null, "Hi to stop recording");
            targetLine.stop();
            targetLine.close();

        } catch (LineUnavailableException ex) {
            throw new RuntimeException(ex);
        }
    }


}
