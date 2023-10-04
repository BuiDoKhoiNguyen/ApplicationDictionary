package DictionaryApplication;

<<<<<<< Updated upstream
import  java.util.ArrayList;
public class Dictionary {
    public static ArrayList<Word> vocab = new ArrayList<>();
=======
import java.util.TreeMap;

public class Dictionary extends TreeMap<String, Word>{
    public void editWord(String wordTarget, String wordExplain) {
        this.get(wordTarget).setWordExplain(wordExplain);
    }
>>>>>>> Stashed changes
}
