import com.example.hyterasignalcontrol.services.WaveData;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestReader {


    public static void main(String[] args) {


//        File papka = new File("C:\\SignalControl\\4 800\\Record_172834.wav");
        File papka = new File("C:\\Worker");
//        File[] files = papka.listFiles();

        // fayllar bo'sh emasligini tekshirish
//        if (files != null && files.length > 0) {
//            // fayllar ro'yxatini chiqarish
//            System.out.println("Papka ichida quyidagi fayllar mavjud:");
//            ReadAudio(files);
//            for (File file : files) {
//                System.out.println(file.getName());
//
//            }
//        } else {
//            System.out.println("Papka ichida fayl mavjud emas.");
//        }

        int belgiUzunligi = 566; /** DOT=4, FREQ=800 bo'lganda bitta belgining bytedagi uzunligi */

        WaveData waveData = new WaveData();
        double[] t = waveData.extractAmplitudeFromFile(papka);
        double[] a = SignalgaIshlovBerish(t);

        List<Integer> listYu = new ArrayList<>();
        List<Integer> listPas = new ArrayList<>();

        System.out.println(t.length);
        System.out.println(a.length);

        int nYuq = 0;
        int nPas = 0;

        for (int i = 11510; i < 11510 + 2*belgiUzunligi; i++) {
            nYuq += a[i];
        }
        System.out.println(nYuq);


//        /** boshini aniqlash */
//        int boshi = 0;
//        for (int i = 0; i < a.length; i++) {
//            if (a[i] == 10) {
//                boshi = i;
//                break;
//            }
//        }
//
//
//        System.out.println(boshi);
//        int nYuq = 0;
//        int nPas = 0;
//
//        for (int i = 0; i < 2; i++) {
//            nYuq = 0;
//            nPas = 0;
//            for (int j = boshi + i * belgiUzunligi + 200; j < boshi + i * belgiUzunligi + 400; j++) {
//                if (a[j] == 10) {
//                    nYuq += a[j];
//                } else {
//                    nPas += a[j];
//                }
//            }
//            if (nYuq > 1500) {
//                System.out.println("nYuq : " + nYuq);
//                System.out.println("nPas : " + nPas);
//                continue;
//            } else {
//                System.out.println("nYuq a: " + nYuq);
//                System.out.println("nPas a: " + nPas);
//            }
//        }
//
//
//        /** signalSonini aniqlash */
//        int signalSoni = (int) Math.floor((a.length - boshi) / belgiUzunligi);
//
//        System.out.println(signalSoni);
//        ReadAudio(a, 10, 10, boshi);
    }

    private static void ReadAudio(double[] fileArr, int signalSoni, int qoldiq, int boshi) {

//        WaveData waveData = new WaveData();
//        double[] t = waveData.extractAmplitudeFromFile(files);
//        double[] a = new double[(int) (t.length / 10)];
//
//        for (int i = 0; i < Math.floor(t.length / 10); i++) {
//            a[i] = t[10 * i];
//        }

//        int qoldiq;
//
//        double[] t;
//        double[] a;
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


        /************* Birinchi fayl uchun **************/


//        t = waveData.extractAmplitudeFromFile(files[0]);
//
//        a = new double[(int) Math.floor(t.length / 10)];
//
//        a = SignalgaIshlovBerish(t);
//
//
//        for (int i = 0; i < a.length; i++) {
//            if (a[i] == 10) {
//                flag = i;
//                break;
//            }
//        }
//
//        int belgiUzunligi = 2820;
//
////        System.out.println("flag => " + flag);
//
//        int countBelgi = (a.length - flag) / belgiUzunligi;
//        System.out.println("belgi => " + countBelgi);
//
//        qoldiq = (a.length - flag) % belgiUzunligi;
//        System.out.println("qoldiq => " + qoldiq);
//
//        int k = flag + 1200;


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
//        for (int i = 0; i < listYu.size(); i++) {
//            if (listYu.get(i) > listPas.get(i)) {
//                System.out.print(1);
//            } else {
//                System.out.print(0);
//            }
//        }


//
//        /******** Chastotali modulyatsiya uchun *********/
//
//        List<Integer> listYu = new ArrayList<Integer>();
//        List<Integer> listPas = new ArrayList<Integer>();
//
//        int nYuq = 0;
//        int nPas = 0;
//
//        int k = 0;
//
//
//
//
//        /** Demak Record_215628 signalniki */
////        int i = 570; i < 40465; i++
//
//        for (int i = 1000; i < 40465; i++) {
//
//            if (k < 2800) {
//                if (a[i] == 10) {
//                    nYuq++;
//                } else {
//                    nPas++;
//                }
//                k++;
//            } else {
//                k = 0;
//
//                listYu.add(nYuq);
//                listPas.add(nPas);
//                nYuq = 0;
//                nPas = 0;
//            }
//
//
//        }
//
//        System.out.println(listYu);
//        System.out.println(listPas);
//
//        for (int i = 0; i < listYu.size(); i++) {
//            if (listYu.get(i) > listPas.get(i)) {
//                System.out.print(1);
//            } else {
//                System.out.print(0);
//            }
//        }
//
//        writeExcell(a);


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
//        writeExcell(fileArr);
//
//        readWaveSignal();


    }

    private static double[] SignalgaIshlovBerish(double[] t) {
        double[] arr = new double[(int) Math.floor(t.length / 10)];
        /**Signalga ishlov berish :) ajoyib narsa */
        for (int k = 0; k < arr.length; k++) {
            if (Math.abs(t[k * 10]) > 25000) {
                arr[k] = 10;
            } else {
                arr[k] = 0;
            }
        }
        return arr;
    }


    public static void writeExcell(double[] a) {
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
        try {
            fileOut = new FileOutputStream("C:\\ishchi\\test.xls");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
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
