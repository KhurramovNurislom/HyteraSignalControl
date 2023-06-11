package com.example.hyterasignalcontrol.controllers;

import com.example.hyterasignalcontrol.services.FXMLLoaderMade;
import com.example.hyterasignalcontrol.services.WaveData;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import javax.sound.sampled.*;
import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import static org.apache.xmlbeans.impl.common.NameUtil.DOT;

public class InputController implements Initializable {
    public JFXButton id_btnStart;
    public JFXButton id_btnStop;
    public JFXButton id_btnClear;
    public TextArea id_taSignal;
    public TextArea id_taText;


    AudioFormat audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 2, 4, 44100, false);
    DataLine.Info dataInfo = new DataLine.Info(TargetDataLine.class, audioFormat);
    final TargetDataLine targetLine = (TargetDataLine) AudioSystem.getLine(dataInfo);

    boolean bool = false;

    WaveData waveData = new WaveData();

    public InputController() throws LineUnavailableException {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        id_btnStart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                id_btnStart.setDisable(true);
                bool = true;

                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        StartFunction();
                    }
                };
                thread.start();

                id_btnStart.setDisable(false);
            }
        });

        id_btnStop.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                id_btnStop.setDisable(true);
                bool = false;
                File papka = new File("D:\\Worker");
                File[] files = papka.listFiles();

                // fayllar bo'sh emasligini tekshirish
                if (files != null && files.length > 0) {
                    // fayllar ro'yxatini chiqarish
                    System.out.println("Papka ichida quyidagi fayllar mavjud:");
                    for (File file : files) {
                        System.out.println(file.getName());
                    }
                } else {
                    System.out.println("Papka ichida fayl mavjud emas.");
                }
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

    private void StartFunction() {
//        writeFile();
//        try {
//            new Thread().sleep(10000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        ReadAudio();
    }


    private void ClearFunction() {
        id_taText.clear();
        id_taSignal.clear();
    }

    private void writeFile() {

        /** Ishchi papkaning mavjudligi bilan ishlash*/
        File papka = new File("D:\\Worker");
        if (!papka.exists() && papka.isDirectory()) {
            new File("D:\\Worker").mkdir();
        }

/** Ma'lumotni audio shaklda yozib oladi*/
        while (bool) {
            Thread audioRecorderThread = new Thread() {
                public void run() {
                    /**Audio File bilan ishlash*/
                    try {
                        targetLine.open();
                        targetLine.start();
                        AudioInputStream recordingStream = new AudioInputStream(targetLine);
                        File outputFile = new File("D:\\Worker\\Record_" + new SimpleDateFormat("HHmmss").format(new Date()) + ".wav");
                        AudioSystem.write(recordingStream, AudioFileFormat.Type.WAVE, outputFile);
                    } catch (LineUnavailableException | IOException ex) {
                        System.out.println(ex);
                    }
                }
            };
            audioRecorderThread.start();

            try {
                new Thread().sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            targetLine.stop();
            targetLine.close();
            System.out.println("yozish tugadi");
        }
    }

    private void ReadAudio() {


        File fileTemp = new File("C:\\Worker\\Record_215828.wav"); /**+ readFolder.ReadFileName()**/

        System.out.println(fileTemp.isFile());

        double[] t = waveData.extractAmplitudeFromFile(fileTemp);
        System.out.println(t.length);
//        System.out.println((int) Math.floor(t.length / 10));
//        double[] a = new double[(int) Math.floor(t.length / 10)];
//        for (int k = 0; k < a.length; k++) {
//            a[k] = t[k * 10];
//        }

//        /******** Chastotali modulyatsiya uchun *********/

//        List<Integer> list = new ArrayList<Integer>();
//
//        int n = 0;
//        for (int i = 0; i < a.length; i++) {
//            for (int j = 0; j < 128*DOT; j++) {
//                if (a[i] > 12130) {
//                    n++;
//                }
//                list.add(n);
//                n = 0;
//            }
//        }


//        /*************  Amplitudali modulyatsiya ***********/
//        int m = 12300;
//        for (int i = 0; i < a.length; i++) {
//            if (a[i] > n) {
//                n++;
//            }
//        }
//
//
//        System.out.println("O'lcham => " + t.length);
//        writeExcell(a);
//
//        readWaveSignal();


    }


    public void readWaveSignal() {
        byte[] buffer = new byte[4096];
        double[] asd = new double[4096];
        AudioFormat audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 2, 4, 44100, false);
        DataLine.Info dataInfo = new DataLine.Info(TargetDataLine.class, audioFormat);
        TargetDataLine targetLine = null;
        try {
            targetLine = (TargetDataLine) AudioSystem.getLine(dataInfo);
            targetLine.open(audioFormat);
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }
        targetLine.start();
        try {

            int bytesRead = 0;
//            while (bytesRead != -1) {
//                bytesRead = targetLine.read(buffer, 0, buffer.length);

            System.out.println(targetLine.read(buffer, 0, buffer.length));

            for (int i = 0; i < buffer.length; i++) {
                System.out.println("buffer [" + i + "] = " + buffer[i]);
            }


//            asd = new WaveData().extractAmplitudeFromFile(buffer);

            System.out.println(asd.length);
//
//                for (int i = 0; i < bytesRead; i++) {
//                    System.out.println("buffer [" + i + "] => " + buffer[i]);
//                    System.out.println("asd [" + i + "] => " + asd[i]);
//                }
//            }

            targetLine.stop();
            targetLine.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    public void writeExcell(double[] a) {
        /****   Excellga yozish shu yerdan ***/

        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet("Qidiruv natijalari");
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("i");
        header.createCell(1).setCellValue("A");

        for (int i = 0; i < a.length; i++) {
            Row row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(i);
            row.createCell(1).setCellValue(a[i]);
        }


        FileOutputStream fileOut = null;
        FileChooser fileChooser = new FileChooser();


        fileChooser.setInitialFileName("Topigan natijalar");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel hujjatlar", "*.xls"));
        fileChooser.setTitle("Natijalarni Excelda saqlash");

        try {
            File file1 = fileChooser.showSaveDialog(new Stage());
            fileOut = new FileOutputStream(file1.getAbsolutePath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        try {
            wb.write(fileOut);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        /****   Excellga yozish tugadi */
    }
}
