package DictionaryApplication;

import java.util.*;
import java.io.*;

public class DictionaryManagement {
    public DictionaryManagement() {}

    public static void insertFromCommandline(Dictionary dictionary) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the number of words: ");
        int numWord = sc.nextInt();
        sc.nextLine();

        while (numWord-- > 0) {
            System.out.print("Enter English word: ");
            String wordTarget = sc.nextLine();
            String wordExplain = sc.nextLine();
            dictionary.put(wordTarget, new Word(wordTarget,wordExplain));
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

    public static void removeFromCommandLine(Dictionary dictionary) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the word: ");
        String wordTarget = sc.nextLine();

        if (dictionary.containsKey(wordTarget))
            dictionary.remove(wordTarget);
        else System.out.println("The word isn't existed, please try again.");
    }

    public static void editFromCommandline(Dictionary dictionary) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the word: ");
        String wordTarget = sc.nextLine();
        if (dictionary.containsKey(wordTarget)) {
            System.out.print("Enter the meaning: ");
            String wordExplain = sc.nextLine();
            dictionary.editWord(wordTarget, wordExplain);
        } else System.out.println("The word isn't existed, please try again.");
    }

    public static void showWords(Set<Map.Entry<String, Word>> entrySet) {
        System.out.printf("%-6s%c %-15s%c %-20s%n", "No", '|', "English", '|', "Vietnamese");
        int no = 0;
        for (Map.Entry<String, Word> mapElement : entrySet) {
            System.out.printf("%-6d%c %-15s%c %-15s%n", ++no, '|', mapElement.getKey(), '|',
                                mapElement.getValue().getWordExplain());
        }
    }

    private static SortedMap<String, Word> partialSearch(Dictionary dictionary, String wordTarget) {
        if (!wordTarget.isEmpty()) {
            char nextLetter = (char) (wordTarget.charAt(wordTarget.length() - 1) + 1);
            String end = wordTarget.substring(0, wordTarget.length() - 1) + nextLetter;
            return dictionary.subMap(wordTarget, end);
        }
        return dictionary;
    }

    public static void dictionarySearcher(Dictionary dictionary) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter target: ");
        String wordTarget = sc.nextLine();
        showWords(partialSearch(dictionary, wordTarget).entrySet());
    }

    public static void readDataFromFile(Dictionary dictionary) throws FileNotFoundException {
        //Doc file E_V
        Scanner sc = new Scanner(new File("data/E_V.txt"));
        while (sc.hasNextLine()) {
            String lineFetched = sc.nextLine();
            String[] lineParts = lineFetched.split("<html>");
            if (lineParts.length == 2) {
                String wordTarget = lineParts[0];
                String wordExplain = "<html>" + lineParts[1];
                dictionary.put(wordTarget, new Word(wordTarget, wordExplain));
            } else System.out.println("Error input: " + lineParts.length + " words");
        }
    }

    public static void exportWordToFile(Dictionary dictionary, String OUT_PATH) {
        try {
            FileWriter fileWriter = new FileWriter(new File(OUT_PATH));
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (Map.Entry<String, Word> mapElement : dictionary.entrySet()) {
                bufferedWriter.write("|" + mapElement.getValue().getWordTarget() + "\n" +
                        mapElement.getValue().getWordExplain());
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        } catch (Exception e) {
            System.out.println("Something went wrong: " + e);
        }
    }
}

