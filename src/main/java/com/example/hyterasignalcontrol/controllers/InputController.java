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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static org.apache.xmlbeans.impl.common.NameUtil.DOT;

public class InputController implements Initializable {
    public JFXButton id_btnStart;
    public JFXButton id_btnStop;
    public JFXButton id_btnClear;
    public TextArea id_taSignal;
    public TextArea id_taText;

    double[] a = new double[44100];
    WaveData waveData = new WaveData();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        id_btnStart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                id_btnStart.setDisable(true);

                StartFunction();

                id_btnStart.setDisable(false);
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

    private void StartFunction() {

        File fileTemp = null;
        fileTemp = new File("signal.wav"); /**+ readFolder.ReadFileName()**/


        double[] t = waveData.extractAmplitudeFromFile(fileTemp);
        System.out.println(t.length);

        for (int k = 0; k < a.length; k++) {
            a[k] = t[k * 10];
        }

        /******** Chastotali modulyatsiya uchun *********/

        List<Integer> list = new ArrayList<Integer>();

        int n = 0;
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < 128*DOT; j++) {
                if (a[i] > 12130) {
                    n++;
                }
                list.add(n);
                n = 0;
            }
        }


        /*************  Amplitudali modulyatsiya ***********/
        int m = 12300;
        for (int i = 0; i < a.length; i++) {
            if (a[i] > n) {
                n++;
            }
        }


        System.out.println("O'lcham => " + t.length);
        writeExcell(a);


    }

    private void StopFunction() {

    }

    private void ClearFunction() {
        id_taText.clear();
        id_taSignal.clear();
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
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Excel hujjatlar", "*.xls")
        );
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
