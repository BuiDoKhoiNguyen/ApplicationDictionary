package Base;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

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

    public static void loadOnlyWordTarget(List<String> list, String path) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String line;
            while ((line = reader.readLine()) != null) {
                list.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static SortedMap<String, Word> partialSearch(Dictionary dictionary, String wordTarget) {
        if (!wordTarget.isEmpty()) {
            char nextLetter = (char) (wordTarget.charAt(wordTarget.length() - 1) + 1);
            String end = wordTarget.substring(0, wordTarget.length() - 1) + nextLetter;
            return dictionary.subMap(wordTarget, end);
        }
        return dictionary;
    }

    public static String lookupWord(Dictionary dictionary, String wordTarget) {
        if (dictionary.containsKey(wordTarget)) {
            return dictionary.get(wordTarget).getWordExplain();
        } else {
            return "Word not found in the dictionary.";
        }
    }
}
