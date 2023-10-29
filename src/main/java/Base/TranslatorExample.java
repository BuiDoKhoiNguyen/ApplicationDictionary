package Base;

import com.darkprograms.speech.translator.GoogleTranslate;

import java.io.IOException;

public class TranslatorExample {
    public static String googleTranslator(String wordTarget) throws IOException {
        return GoogleTranslate.translate("vi", wordTarget);
    }
}