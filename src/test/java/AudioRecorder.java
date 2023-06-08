import javax.sound.sampled.*;

public class AudioRecorder {
    private static TargetDataLine line;

   private static byte[] buffer = new byte[4096];

    public static void main(String[] args) {

        AudioFormat format = new AudioFormat(44100, 16, 2, true, true);
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

        try {
            line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(format);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }

        line.start();
        int bytesRead = 0;
        while (bytesRead != -1) {
            bytesRead = line.read(buffer, 0, buffer.length);
            // Do something with the audio data here
            for (int i = 0; i < buffer.length; i++) {
                System.out.println("buffer [" + i + "] = " + buffer[i]);
            }
        }

        line.stop();
        line.close();
    }
}