package Controllers;

import com.sun.javafx.application.PlatformImpl;
import com.voicerss.tts.AudioFormat;
import com.voicerss.tts.VoiceParameters;
import com.voicerss.tts.VoiceProvider;

import java.io.File;
import java.io.FileOutputStream;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


public class VoiceController {
    private static final String API_KEY = "ee1a861047db41e3aed6cca75554a826";
    private static final String AUDIO_PATH = "src/main/resources/Voice/audio.wav";

    public static String voiceNameUS;
    public static String voiceNameUK;
    public static String language = "en-gb";
    public static String Name = "Linda";
    public static double speed = 1;

    public static void speakWord(String word) {
        VoiceProvider tts = new VoiceProvider(API_KEY);
        VoiceParameters params = new VoiceParameters(word, AudioFormat.Format_44KHZ.AF_44khz_16bit_stereo);
        params.setBase64(false);
        params.setLanguage(language);
        params.setVoice(Name);
        params.setRate((int) Math.round(-2.9936 * speed * speed + 15.2942 * speed - 12.7612));


        try {
            byte[] voice = tts.speech(params);
            FileOutputStream fos = new FileOutputStream(AUDIO_PATH);
            fos.write(voice, 0, voice.length);
            fos.flush();
            fos.close();

            String audioPath = new File(AUDIO_PATH).toURI().toString();
            Media media = new Media(audioPath);
            MediaPlayer mediaPlayer = new MediaPlayer(media);

            mediaPlayer.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        PlatformImpl.startup(() -> {
            speakWord("fuck you");
        });
        //speakWord("idiot");
    }
}


