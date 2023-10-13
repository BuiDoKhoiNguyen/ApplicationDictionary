package DictionaryApplication;

import java.io.*;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

public class NewDictionaryManagement {
    private static final String SPLITTING_PATTERN = "<html>";

    NewDictionaryManagement() {
    }
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


    public static String lookupWord(Dictionary dictionary, String wordTarget) {
        if (dictionary.containsKey(wordTarget)) {
            return dictionary.get(wordTarget).getWordExplain();
        } else {
            return "Word not found in the dictionary.";
        }
    }

    public static void loadFromFile(Dictionary dictionary, String IN_PATH) {
        try {
            FileReader fileReader = new FileReader(IN_PATH);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String englishWord = bufferedReader.readLine();
            englishWord = englishWord.replace("|", "");
            String line = null;


            while ((line = bufferedReader.readLine()) != null) {
                Word word = new Word();
                word.setWordTarget(englishWord.trim());
                String meaning = line + "\n";
                while ((line = bufferedReader.readLine()) != null) {
                    if (!line.startsWith("|")) meaning += line + "\n";
                    else {
                        englishWord = line.replace("|", "");
                        break;
                    }
                    word.setWordExplain(meaning.trim());
                    dictionary.put(englishWord, word);
                }
            }
            bufferedReader.close();
            System.out.println("Loaded from file successfully");
        } catch (IOException e) {
            System.out.println("An error occur with file: " + e);
        } catch (Exception e) {
            System.out.println("Something went wrong: " + e);
        }
    }

    public static void exportToHistory(Dictionary dictionary, String OUT_PATH) {
        try {
            FileWriter fileWriter = new FileWriter(new File(OUT_PATH), true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (Map.Entry<String, Word> mapElement : dictionary.entrySet()) {
                bufferedWriter.write(mapElement.getValue().getWordTarget() + "\t" +
                        mapElement.getValue().getWordExplain());
                bufferedWriter.newLine();
            }
            System.out.println("Export to file successfully !");
            bufferedWriter.close();
        } catch (Exception e) {
            System.out.println("Something went wrong: " + e);
        }
    }
}
