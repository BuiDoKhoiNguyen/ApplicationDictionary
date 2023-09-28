package DictionaryApplication;

import java.util.*;

public class DictionaryCommandLine  {
    private Dictionary dictionary;
    private Dictionary vocab;
    private final String IN_PATH;
    public DictionaryCommandLine(){
        dictionary = new Dictionary();
        vocab = new Dictionary();
        IN_PATH = "src/main/resources/dictionaries.txt";
        DictionaryManagement.loadFromFile(dictionary,IN_PATH);
    }
    public void dictionaryAdvanced() {
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
                    DictionaryManagement.showAllWords(vocab);
                }

                case 2 -> {
                    DictionaryManagement.removeFromCommandLine(vocab);
                    DictionaryManagement.showAllWords(vocab);
                }

                case 3 -> {
//                    DictionaryManagement.editFromCommandline();
                }

                case 4 -> {
                    DictionaryManagement.showAllWords(vocab);
                }

                case 5 -> {
                    System.out.print("Word target: ");
                    String wordTarget = sc.nextLine();
                    int index = DictionaryManagement.dictionaryLookup(dictionary,wordTarget);
                    System.out.print("Meaning: " + dictionary.get(index).getWordExplain() );
                }

                case 6 -> {
//                    DictionaryManagement.dictionarySearcher();
                }

                case 7 -> {

                }

                case 8 -> {
                    System.out.println("The data has been imported!");
                }
                case 9 -> {
//                    DictionaryManagement.dictionaryExportToFile();
                }
            }
            System.out.print("Press ENTER to continue...");
            sc.nextLine();
        } while (true);
    }
}
