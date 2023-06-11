import javax.sound.sampled.*;

public class TestMain {

    private static final int DOT = 10, FREQ = 800;

    public static void main(String[] args) throws LineUnavailableException {


/*******************************************************************************************************************************************************************/

        try (SourceDataLine sdl = AudioSystem.getSourceDataLine(new AudioFormat(8000F, 8, 1, true, false))) {
            sdl.open(sdl.getFormat());
            sdl.start();


            for (int k = 0; k < 10000; k++) {
                for (double l = 1; l < 10; l++) {
                    for (int i = 0; i < DOT * 128; i++) {
//                      WaveGenerator(l);
                        sdl.write(new byte[]{(byte) (Math.sin(i / (8000F / FREQ) * 2.0 * Math.PI) * (0.1*


                                l)*127.0)}, 0, 1);
                    }
                }
            }
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }

//    private TargetDataLine line;

//    public static void main(String[] args) {
//        AudioRecorder audioRecorder = new AudioRecorder();
//        audioRecorder.start();
//    }
////
//
//
//    public void AudioRecorder() {
//        AudioFormat format = new AudioFormat(44100, 16, 2, true, true);
//        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
//        try {
//            line = (TargetDataLine) AudioSystem.getLine(info);
//            line.open(format);
//        } catch (LineUnavailableException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void start() {
//        line.start();
//        byte[] buffer = new byte[4096];
//        int bytesRead = 0;
//        while (bytesRead != -1) {
//            bytesRead = line.read(buffer, 0, buffer.length);
//            // Do something with the audio data here
//        }
//    }
//
//    public void stop() {
//        line.stop();
//        line.close();
//    }
//

    }


    public static void WaveGenerator(double n) throws LineUnavailableException {


        float frequency = 440; // signal chastotasi (Hz)
        float sampleRate = 44100; // namunaviy chastotasi (Hz)
        double duration = 0.2; // signal davomiyligi (soniya)
        double amplitude = 0.1; // signal amplitudasi (0 dan 1 gacha)

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
