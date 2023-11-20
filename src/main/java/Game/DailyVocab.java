package Game;

import Base.NewDictionaryManagement;
import Base.Word;
import Base.Dictionary;
import java.io.*;
import java.time.*;
import java.util.*;

import static Base.NewDictionaryManagement.SPLITTING_PATTERN;
import static Controllers.PreloaderController.dailyWord;
import static Controllers.PreloaderController.dictionary;

public class DailyVocab {
    public static final String DAILY_WORD = "data/dailyVocab.txt";

    private static LocalDate currentDate = LocalDate.now();
    public static void writeDailyWord(Dictionary dailyWords) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DAILY_WORD))) {
            String date = currentDate.toString();
            writer.write(date);
            writer.newLine();
            for (Map.Entry<String, Word> entry : dailyWords.entrySet()) {
                String line = entry.getKey() + entry.getValue().getWordExplain();
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void getDailyWords(Dictionary dailyWords) {
        try {
            File file = new File(DAILY_WORD);

            if (file.exists()) {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String savedDate = reader.readLine();
                LocalDate lastDate = LocalDate.parse(savedDate);

                if (currentDate.isEqual(lastDate)) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] parts = line.split(SPLITTING_PATTERN);
                        String wordTarget = parts[0];
                        String definition = SPLITTING_PATTERN + parts[1];
                        Word word = new Word(wordTarget, definition);
                        dailyWords.put(wordTarget, word);
                    }
                }
                if (dailyWords.isEmpty() || !currentDate.isEqual(lastDate)) {
                    generateNewDailyWords(dailyWords);
                    writeDailyWord(dailyWords);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void generateNewDailyWords(Dictionary dailyWords) {
        List<Map.Entry<String, Word>> entries = new ArrayList<>(dictionary.entrySet());
        Collections.shuffle(entries);

        int count = 0;
        for (Map.Entry<String, Word> entry : entries) {
            if (count >= 10) {
                break;
            }
            dailyWords.put(entry.getKey(), entry.getValue());
            count++;
        }

    }
}
