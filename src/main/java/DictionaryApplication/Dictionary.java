package DictionaryApplication;

import  java.util.*;
public class Dictionary extends ArrayList<Word> {
    private static ArrayList<Word> words = new ArrayList<>();

    public static void addWord(Word word){
        words.add(word);
    }

    public static ArrayList<Word> getWords(){
        return words;
    }
}
