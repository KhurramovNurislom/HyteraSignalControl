//import javax.sound.sampled.*;
//
//public class TestMain {
//    private TargetDataLine line;
//
//    public static void main(String[] args) {
//        AudioRecorder audioRecorder = new AudioRecorder();
//        audioRecorder.start();
//    }
//
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
//
//
//}
