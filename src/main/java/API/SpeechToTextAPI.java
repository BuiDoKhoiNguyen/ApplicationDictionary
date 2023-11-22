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