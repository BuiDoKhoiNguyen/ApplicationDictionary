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
            System.out.print("Enter meaning: ");
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

    public static String lookupWord(Dictionary dictionary, String wordTarget) {
        if (dictionary.containsKey(wordTarget)) {
            return dictionary.get(wordTarget).getWordExplain();
        } else {
            return "Word not found in the dictionary.";
        }
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

    public static void importFromFile(Dictionary dictionary, String IN_PATH) {
        try {
            File inFile = new File(IN_PATH);
            FileReader fileReader = new FileReader(inFile);
            BufferedReader reader = new BufferedReader(fileReader);
            String line = null;
            while ((line = reader.readLine()) != null) {
                String[] wordsInLine = line.split("\t");
                Word temp = new Word(wordsInLine[0], wordsInLine[1]);
                dictionary.put(wordsInLine[0], temp);
            }
            System.out.println("Import from file sucessfully !");
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void importFromFile(Dictionary dictionary, String IN_PATH) {
        try {
            File inFile = new File(IN_PATH);
            FileReader fileReader = new FileReader(inFile);
            BufferedReader reader = new BufferedReader(fileReader);
            String line = null;
            while ((line = reader.readLine()) != null) {
                String[] wordsInLine = line.split("\t");
                Word temp = new Word(wordsInLine[0], wordsInLine[1]);
                dictionary.put(wordsInLine[0], temp);
            }
            System.out.println("Import from file sucessfully !");
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void exportWordToFile(Dictionary dictionary, String OUT_PATH) {
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

