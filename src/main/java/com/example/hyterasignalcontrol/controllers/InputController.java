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

import javax.crypto.spec.PSource;
import javax.sound.sampled.*;
import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
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


    /**
     * Qabul qilish uchun kerak parametrlar
     **/

    private static int belgiUzunligi = 566; /** Shu parametr */

    /**
     * Wave fayldan amplitudani o'qib beruvchi class
     */
    private static WaveData waveData = new WaveData();

    /**
     * 0 va 1 belgilarini qiymatini o'zlashtirish uchun listlar,
     * listYu (yuqori) 1 uchun, listPas (past) 0 uchun
     */
    private static List<Integer> listYu = new ArrayList<>();
    private static List<Integer> listPas = new ArrayList<>();

    /**
     * Qoldiq belgini hisoblash uchun
     */
    private static List<Integer> listQol = new ArrayList<>();

    private static int qoldiq = 0;

    private static String text = "";

    private static int nYuq = 0;
    private static int nPas = 0;

    /*** oxiri ***/

    public InputController() throws LineUnavailableException {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        id_btnStart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                id_btnStart.setDisable(true);
                bool = true;
                StartFunction();
                id_btnStart.setDisable(false);
            }
        });

        id_btnStop.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                id_btnStop.setDisable(true);
                bool = false;
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

        Thread thread = new Thread() {
            @Override
            public void run() {
                writeFile();
            }
        };
        thread.start();

        Thread thread2 = new Thread() {
            @Override
            public void run() {
                ReadAudio();
            }
        };
        thread2.start();
    }


    private void ClearFunction() {
        id_taText.clear();
        id_taSignal.clear();
    }

    private void writeFile() {

        /** Ishchi papkaning mavjudligi bilan ishlash*/
        File papka = new File("C:\\SignalControl");
        if (!papka.exists() && papka.isDirectory()) {
            new File("C:\\SignalControl").mkdir();
        }

        File[] files = papka.listFiles();
        for (int i = 0; i < files.length; i++) {
            files[i].delete();
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
                        File outputFile = new File("C:\\SignalControl" + File.separator + "Record_" + new SimpleDateFormat("HHmmss").format(new Date()) + ".wav");
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
//            System.out.println("yozish tugadi");
        }
    }

    private void ReadAudio() {

        try {
            new Thread().sleep(6000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        /**
         * while(true) => SignalControl papkasidagi fayllarning
         * nomini cheksiz marta o'qib turish uchun ishlatiladi
         * */
        while (true) {


            File papka = new File("C:\\SignalControl");
            File[] files = papka.listFiles();

            /**
             * fayllar bo'sh emasligini tekshirish
             * */
            if (files != null && files.length > 0) {

                /**
                 *  Papkada mavjud fayllarni ko'rish
                 *  */
//                System.out.println("Papka ichida quyidagi fayllar mavjud:");
//                for (int i = 0; i < files.length; i++) {
//                    System.out.println("file [" + i + "] = " + files[i].getName());
//                }

                /**
                 * papkaning ichidagi birinchi faylni ishchi fayl sifatida tanlab olish,
                 * bunda fayl o'qiladi, amallar yakunida fayl o'chirib yuboriladi, keyin
                 * yana papkadagi fayllar qaytadan o'qiladi natijada keyingi fayl birinchi
                 * faylga aylanadi va amallar qaytadan boshlanadi
                 * */
                File file = files[0];

                /**
                 * WaveData classi wave formatdagi audio fayldan signalning har bir baytiga
                 * mos amplitudasini o'qib beradi va signal baytlari uzunligiga teng,
                 * qiymatida amplitudani aks ettiruvchi double tipdagi massivni qaytaradi
                 * */
                double[] t = waveData.extractAmplitudeFromFile(file);
//                System.out.println("real massiv uzunligi : " + t.length);

                /**
                 * SignalgaIshlovBerish => method ga signal amplitudalari massivi
                 * va amplitudaning shartli chegarasi beriladi, method berilgan massiv
                 * uzunligini 10 barbarga qisqartirib, qiymatlarini almashtirib chiqadi,
                 * bunda, shartli chegaradan yuqori element qiymatini 10 pastkisining
                 * giymatni 0 bilan almashtirib chiqadi
                 * */
                double[] a = SignalgaIshlovBerish(t, 30000);
//                System.out.println("massivga ishlov berilgandagi uzunligi : " + a.length);

                /**
                 * Fayldagi belgilarni ajratib olish uchun qayta ishlangan massivni tahlil qilib,quyidagilar aniqlanadi:
                 * => massivning nechanchi elementidan belgi boshlanishi;
                 * => massivda nechta belgi mavjudligi;
                 * => fayl oxirida tugamay qolgan belgining elementlari soni, keyingi faylning butun belgilari
                 * nechanchi elementdan boshlanishi va ikkala faylda qism qism bo'lib qolgan belgini aniqlash uchun ishlatiladi
                 * */

                /**
                 *  Birinchi belgi boshlangan joyni aniqlash qismi, a massiv
                 * uzunligi bo'yicha har bir elementning 10 ga teng ekanligini tekshiradi,
                 * agar element qiymati 10 ga teng bo'lsa belgi shu elementdan boshlangan
                 * bo'ladi va uning indeksi boshlang'ich indeks hisoblanadi
                 */

                int k = 0; /** Besh sekundlik fileda ma'lumot yo'qligini tekshirish uchun kerak*/

                for (int i = 0; i < a.length; i++) {
                    if (a[i] == 10) {
                        k++;
                        break;
                    }
                }


                if (k == 0) {

                    try {
                        new Thread().sleep(5000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    file.delete();

                } else {



                    int boshIndex = 0;
                    for (int i = 0; i < a.length; i++) {
                        if (a[i] == 10) {
                            boshIndex=i;
                            break;
                        }
                    }

//                    System.out.println("birinchi belgining boshlang'ich indeksi : " + boshIndex);


                    /**
                     * Yozib olingan signalning ma'lumot boshlangan qismini tanib oluvchi
                     * belgi sifatida 1 ta 1 belgisi ishlatilgan, birinchi faylning tanituvchi
                     * belgisini mavjudligini tekshirish quyidagicha:
                     * */


                    /**
                     * Qayta ishlashdan hosil bo'lgan a massivda nechta belgi borligi quyidagicha aniqlanadi:
                     * => umumiy massiv uzunligidan belgilar boshlanguncha bo'lgan elementlar soni ayirib
                     * tashlanadi, ayirilgan qism audio fayldagi signal qabul qilinguncha bo'lgan (kutish) qismi hisoblanadi
                     * => natijani bitta belgining uzunligiga bo'lab, butun qismini olamiz, natija nechi dona
                     * butun belgi borligini ifodalaydi;
                     * */
                    int butunBelgilarSoni = (int) Math.floor((a.length - boshIndex) / belgiUzunligi);
                System.out.println("butun belgilar soni : " + butunBelgilarSoni);

                    /**
                     * Qoldiq qismini aniqlash, bunda bitta belgini hosil qilish uchun
                     * yetmaydigan, ortib qolgan qoldiq elementlar soni aniqlanadi
                     * */
                    qoldiq = (a.length - boshIndex) % belgiUzunligi;
                System.out.println("belgining qoldiq elementlari soni : " + qoldiq);


                    /**
                     * Belgilarni o'qish methodi
                     * */


                    String text2 = "";
                    text2 += ReadAudio(a, butunBelgilarSoni, boshIndex, qoldiq);


//                String t1 = text2;
//                t1 = text2.substring(1);
//                System.out.println("t1 = " + t1);
//t1="";
//                byte[] t1bytes = t1.getBytes();
//                System.out.println();


//                id_taSignal.setText();

//                System.out.print(text);
                    /** o'qib bo'lingan faylni o'chirib tashlash */

                    try {
                        new Thread().sleep(5000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    file.delete();
//                System.out.println("file o'chdi ... ");

                }

            } else {
                System.out.println("Papka ichida fayl mavjud emas.");
                break;
            }
        }
    }

    private static String ReadAudio(double[] fileArr, int butunBelgilarSoni, int boshIndex, int qoldiq) {

        nYuq = 0;

        System.out.println("Yangi fayl:");
        System.out.println("fileArr.length: " + fileArr.length + "\n" +
                "butunbelgi: " + butunBelgilarSoni + "\n"
                + "boshIndex: " + boshIndex + "\n" +
                "qoldiq: " + qoldiq);


        for (int i = 0; i < butunBelgilarSoni; i++) {
            for (int j = boshIndex + i * belgiUzunligi + 200; j < boshIndex + i * belgiUzunligi + 400; j++) {
                if (fileArr[j] == 10) {
                    nYuq += fileArr[j];
                }
            }

            if (nYuq > 1000) {
                text += "1";
//                continue;
            } else {
                text += "0";
            }
            nYuq = 0;
        }

        System.out.println(text);
        text = "";


//        System.out.println(signalSoni);
//        ReadAudio(a, 10, 10, boshi);


//
//        int flag = 0;
//
//        int nYuq;
//        int nPas;
//
//
//        List<Integer> listYu = new ArrayList<>();
//        List<Integer> listPas = new ArrayList<>();
//
//        List<Integer> listOraliq = new ArrayList<>();


        /***********************************************************/
//        for (int i = 0; i < countBelgi; i++) {
//
//            nYuq = 0;
//            nPas = 0;
//
//            for (int j = k + i * belgiUzunligi; j < k + (420 + i * belgiUzunligi); j++) {
//                if (a[j] == 10) {
//                    nYuq += a[j];
//                } else {
//                    nPas++;
//                }
//            }
//
//            listYu.add(nYuq);
//            listPas.add(nPas);
//        }
//
//        /** Natija text */
//        for (int i = 0; i < listYu.size(); i++) {
//            if (listYu.get(i) > listPas.get(i)) {
//                System.out.print(1);
//            } else {
//                System.out.print(0);
//            }
//        }
//
//        listYu.clear();
//        listPas.clear();


        /**  Ikkinchi va undan keyingi fayllar uchun  */

//        for (int fi = 1; fi < files.length; fi++) {
//
//
//            File fileTemp = files[fi];// new File("C:\\Worker\\Record_215623.wav"); /**+ readFolder.ReadFileName()**/
//
////            System.out.println(fileTemp.isFile());
//
//            t = waveData.extractAmplitudeFromFile(fileTemp);
//
////            System.out.println(t.length);
//
////            System.out.println((int) Math.floor(t.length / 10));

        /**** Eski fayl qoldiqlari qo'shish ***/
//            for (int i = a.length - qoldiq; i < a.length; i++) {
//                listOraliq.add((int) a[i]);
//            }
//
//
//            a = new double[(int) Math.floor(t.length / 10)];
//
//            a = SignalgaIshlovBerish(t);

        /**** Yangi fayl qoldiqlarini qo'shish ***/
//            for (int i = 0; i < belgiUzunligi - qoldiq; i++) {
//                listOraliq.add((int) a[i]);
//            }
//
//            nYuq = 0;
//            nPas = 0;
//
//            for (int i = 0; i < 2820; i++) {
//                if (a[i] == 10) {
//                    nYuq += a[i];
//                } else {
//                    nPas++;
//                }
//            }
//
//            listYu.add(nYuq);
//            listPas.add(nPas);
//
//            for (int i = 0; i < countBelgi; i++) {
//
//                nYuq = 0;
//                nPas = 0;
//
//                for (int j = k + i * belgiUzunligi; j < k + (420 + i * belgiUzunligi); j++) {
//                    if (a[j] == 10) {
//                        nYuq += a[j];
//                    } else {
//                        nPas++;
//                    }
//                }
//
//                listYu.add(nYuq);
//                listPas.add(nPas);
//            }
//
//        }
//
//        System.out.println(listYu);
//        System.out.println(listPas);


        /** Natija text */


        return ""/**text*/;
    }

    private static double[] SignalgaIshlovBerish(double[] t, int chegara) {
        double[] arr = new double[(int) (double) (t.length / 10)];
        /**Signalga ishlov berish :) ajoyib narsa */
        for (int k = 0; k < arr.length; k++) {
            if (Math.abs(t[k * 10]) > chegara) {
                arr[k] = 10;
            } else {
                arr[k] = 0;
            }
        }
        return arr;
    }

}
