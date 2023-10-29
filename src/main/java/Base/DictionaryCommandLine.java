package Base;

import java.io.FileNotFoundException;
import java.util.*;

public class DictionaryCommandLine {
    private Dictionary dictionary;
    private Dictionary vocab;
    private static final String OUT_PATH = "data/dictionaries_out.txt";
    private static final String IN_PATH = "data/dictionaries.txt";
    private static final String path = "data/E_V.txt";

    public DictionaryCommandLine() {
        dictionary = new Dictionary();
        vocab = new Dictionary();
        DictionaryManagement.loadFromFile(dictionary, IN_PATH);
//        NewDictionaryManagement.loadDataFromHTMLFile(dictionary, path);
    }

    public void dictionaryAdvanced() throws FileNotFoundException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to My Application!");

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
                    try{
                        System.out.print("Enter number of word: ");
                        int numWord = sc.nextInt();
                        sc.nextLine();

                        while(numWord-- > 0){
                            System.out.print("Enter word: ");
                            String wordTarget = sc.next();
                            String wordExplain = TranslatorExample.googleTranslator(wordTarget);
                            System.out.println(wordExplain);
                        }
                    } catch (Exception e){
                        System.out.print(e);
                    }

                }

                case 7 -> {}

                case 8 -> {
                    DictionaryManagement.importFromFile(vocab, OUT_PATH);
                }
                case 9 -> {
                    DictionaryManagement.exportWordToFile(vocab, OUT_PATH);
                }
            }

            System.out.print("Press ENTER to continue...");
            sc.nextLine();
        } while (true);
    }

}