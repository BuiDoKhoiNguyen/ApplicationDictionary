package DictionaryApplication;

import java.util.*;

public class DictionaryManagement {
    public void insertFromCommandline(){
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the number of words: ");
        int numWord = sc.nextInt();
        sc.nextLine();

        while(numWord != 0){
            System.out.print("Enter English word: ");
            String EnWord = sc.nextLine();
            System.out.print("Enter Vietnamese meaning: ");
            String ViWord = sc.nextLine();

            Word word = new Word(EnWord, ViWord);
            DictionaryApplication.Dictionary.addWord(word);
            numWord--;
        }
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
