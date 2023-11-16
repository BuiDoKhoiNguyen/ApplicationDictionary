package Base;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import static Base.NewDictionaryManagement.loadDataFromHTMLFile;
import static Base.NewDictionaryManagement.loadOnlyWordTarget;

public class Dictionary extends TreeMap<String, Word> {
    public static final String EV_IN_PATH = "data/E_V.txt";
    public static final String FAVOURITE_IN_PATH = "data/favourite.txt";

    public Dictionary() {

    }

    public Dictionary(String path) {
        loadDataFromHTMLFile(this, path);
    }

    public void editWord(String wordTarget, String wordExplain) {
        this.get(wordTarget).setWordExplain(wordExplain);
    }
}