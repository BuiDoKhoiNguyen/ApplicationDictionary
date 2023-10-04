package DictionaryApplication;

import java.io.FileNotFoundException;
import java.util.*;
import java.io.IOException;

<<<<<<< Updated upstream
public class DictionaryCommandLine extends Dictionary {
    public static String showAllWords() {
        String ans = "";
        System.out.printf("%-6s%c %-15s%c %-20s%n","No", '|' ,"English", '|', "Vietnamese");
        for (int i = 0; i < vocab.size(); i++) {
            System.out.printf("%-6d%c %-15s%c %-15s%n", i + 1,'|', vocab.get(i).getWordTarget(), '|',vocab.get(i).getWordExplain());
        }
        return ans;
=======
public class DictionaryCommandLine {
    private Dictionary dictionary;
    private Dictionary vocab;
    private static final String DICTIONARY = "src/main/resources/dictionaries.txt";
    private static final String OUT_PATH = "src/main/resources/dictionaries_out.txt";
    private static final String IN_PATH = "src/main/resources/dictionaries_out.txt";

    public DictionaryCommandLine() {
        dictionary = new Dictionary();
        vocab = new Dictionary();
        DictionaryManagement.loadFromFile(dictionary, DICTIONARY);
>>>>>>> Stashed changes
    }

    public void dictionaryAdvanced() throws FileNotFoundException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to My Application!");
<<<<<<< Updated upstream

//        do {
//            System.out.println("[0] Exit\n[1] Add\n[2] Remove\n[3] Update\n[4] Display\n[5] Lookup");
//            System.out.println("[6] Search\n[7] Game\n[8] Import from file\n[9] Export to file");
//            System.out.print("Your action: ");
//
//            int id = sc.nextInt();
//            sc.nextLine();
//
//            switch (id) {
//                case 0 -> {
//                    System.out.print("Goodbye Master");
//                    break ;
//                }
//
//                case 1 -> {
//                    DictionaryManagement.insertFromCommandline();
//                }
//
//                case 2 -> {
//                    DictionaryManagement.removeFromCommandLine();
//                }
//
//                case 3 -> {
//                    DictionaryManagement.editFromCommandline();
//                }
//
//                case 4 -> {
//                    this.showAllWords();
//                }
//
//                case 5 -> {
//                    DictionaryManagement.dictionaryLookup();
//                }
//
//                case 6 -> {
//                    DictionaryManagement.dictionarySearcher();
//                }
//
//                case 7 -> {
//
//                }
//
//                case 8 -> {
//                    DictionaryManagement.insertFromFile();
//                    System.out.println("The data has been imported!");
//                }
//                case 9 -> {
//                    DictionaryManagement.dictionaryExportToFile();
//                    System.out.println("The data has been exported!");
//                }
//            }
//
//
//            System.out.print("Press ENTER to continue...");
//            sc.nextLine();
//        } while (true);
=======

        Loop:
        do {
            System.out.println("[0] Exit\n[1] Add\n[2] Remove\n[3] Update\n[4] Display\n[5] Lookup");
            System.out.println("[6] Search\n[7] Game\n[8] Import from file\n[9] Export to file");
            System.out.print("Your action: ");

            int id = sc.nextInt();
            sc.nextLine();

            switch (id) {
                case 0 -> {
                    System.out.print("Goodbye Master");
                    break Loop;
                }

                case 1 -> {
                    DictionaryManagement.insertFromCommandline(vocab);
//                    DictionaryManagement.showWords(vocab.entrySet());
                }

                case 2 -> {
                    DictionaryManagement.removeFromCommandLine(vocab);
//                    DictionaryManagement.showWords(vocab.entrySet());
                }

                case 3 -> {
                    DictionaryManagement.editFromCommandline(vocab);
                }

                case 4 -> {
                    DictionaryManagement.showWords(vocab.entrySet());
                }

                case 5 -> {
                    DictionaryManagement.dictionarySearcher(dictionary);
                }

                case 6 -> {
                    //use API
                    TranslatorExample.googleTranslator();
                }

                case 7 -> {}

                case 8 -> {
                    DictionaryManagement.loadFromFile(dictionary, DICTIONARY);
                }
                case 9 -> {
                    DictionaryManagement.exportWordToFile(vocab, OUT_PATH);
                }
            }

            System.out.print("Press ENTER to continue...");
            sc.nextLine();
        } while (true);
>>>>>>> Stashed changes
    }

}
