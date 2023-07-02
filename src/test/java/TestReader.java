import com.example.hyterasignalcontrol.Main;
import com.example.hyterasignalcontrol.services.WaveData;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
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

    /**
     * DOT=4, FREQ=800 bo'lganda bitta belgining bytedagi uzunligi quyidagicha:
     */
    private static int belgiUzunligi = 566;

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

    private static String text = null;

    private static int nYuq = 0;
    private static int nPas = 0;


    public static void main(String[] args) throws InterruptedException, IOException {

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
                 * Yozib olingan signalning ma'lumot boshlangan qismini tanib oluvchi
                 * belgi sifatida 1 ta 1 belgisi ishlatilgan, birinchi faylning tanituvchi
                 * belgisini mavjudligini tekshirish quyidagicha:
                 * */

                /********  Nimalardir yoziladi  *******/

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
                int boshIndex = 0;
                for (int i = 0; i < a.length; i++) {
                    if (a[i] == 10) {
                        boshIndex = i;
                        break;
                    }
                }
//                System.out.println("birinchi belgining boshlang'ich indeksi : " + boshIndex);

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
                qoldiq = (int) ((a.length - boshIndex) % belgiUzunligi);
//                System.out.println("belgining qoldiq elementlari soni : " + qoldiq);


                /**
                 * Belgilarni o'qish methodi
                 * */


                text += ReadAudio(a, butunBelgilarSoni, boshIndex, qoldiq);
                System.out.print(text);
                /** o'qib bo'lingan faylni o'chirib tashlash */

                file.delete();
//                System.out.println("file o'chdi ... ");

//                Thread thread = new Thread();
//                thread.sleep(5000);
            } else {
                System.out.println("Papka ichida fayl mavjud emas.");
                break;
            }
        }
    }


    private static String ReadAudio(double[] fileArr, int butunBelgilarSoni, int boshIndex, int qoldiq) {


        nYuq = 0;

        System.out.println("Yangi fayl:");

        for (int i = 0; i < butunBelgilarSoni; i++) {
            for (int j = boshIndex + i * belgiUzunligi + 200; j < boshIndex + i * belgiUzunligi + 400; j++) {
                if (fileArr[j] == 10) {
                    nYuq += fileArr[j];
                }
            }
            System.out.println("nYuq [" + i + "] = " + nYuq);
            nYuq = 0;
        }


//
//
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
//
//
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

        return "ad";
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
