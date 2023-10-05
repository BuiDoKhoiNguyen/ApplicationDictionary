package DictionaryApplication;

import java.io.BufferedReader;
import java.io.FileReader;

public class NewDictionaryManagement {
    private static final String SPLITTING_PATTERN = "<html>";

    public static void loadDataFromHTMLFile(Dictionary dictionary, String path) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(SPLITTING_PATTERN);
                String wordTarget = parts[0];
                String definition = SPLITTING_PATTERN + parts[1];
                Word word = new Word(wordTarget, definition);
                dictionary.put(wordTarget, word);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
