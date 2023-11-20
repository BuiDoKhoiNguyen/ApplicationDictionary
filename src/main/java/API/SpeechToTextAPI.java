package API;

import com.theokanning.openai.audio.CreateTranscriptionRequest;
import com.theokanning.openai.service.OpenAiService;

import java.io.File;

public class SpeechToTextAPI {
    private static String apiKey = "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";
    public final static String RECORD_PATH = "src/main/resources/record/record.wav";

    public static String speechToTextAPI(){
        OpenAiService service = new OpenAiService(apiKey);

        CreateTranscriptionRequest request = new CreateTranscriptionRequest();
        request.setModel("whisper-1");
        File file = new File(RECORD_PATH);
        String transcription = service.createTranscription(request,RECORD_PATH).getText();
        return transcription;
    }
}