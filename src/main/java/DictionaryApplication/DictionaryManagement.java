package DictionaryApplication;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.io.*;
public class DictionaryManagement {
    private Map<String, String> dictionary;

    public DictionaryManagement() {
        dictionary = new HashMap<>();
    }

    public void insertFromCommandline(){
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the number of words: ");
        int numWord = sc.nextInt();
        sc.nextLine();

        while(numWord != 0){
            System.out.print("Enter English word: ");
            String EnWord = sc.nextLine();
            String ViWord = lookupWord(EnWord);
            Word word = new Word(EnWord, ViWord);
            DictionaryApplication.Dictionary.addWord(word);
            numWord--;
        }
    }

    public void loadDictionaryFromFile() {
        File path = new File("C:\\Users\\DICK.txt");

        try {
            BufferedReader reader = Files.newBufferedReader(path.toPath(), StandardCharsets.UTF_8);
            String englishWord = null;
            String vietnameseMeaning = null;

            while (true) {
                englishWord = reader.readLine();
                vietnameseMeaning = reader.readLine();
                if(englishWord == null || vietnameseMeaning == null) break;
                System.out.println(englishWord + " " + vietnameseMeaning);
                dictionary.put(englishWord, vietnameseMeaning);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String lookupWord(String englishWord) {
        return dictionary.get(englishWord);
    }

    public void showAllWords() {
        ArrayList<Word> words = Dictionary.getWords();
        if (words.isEmpty()) {
            System.out.println("The dictionary is empty.");
            return;
        }

             System.out.println("No | English    | Vietnamese");
             System.out.println("----------------------------------");
        for (int i = 0; i < words.size(); i++) {
            System.out.printf("%-2d | %s%n", i + 1, words.get(i));
        }
    }
}
