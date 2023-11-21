package Base;

import java.util.TreeMap;

import static Base.NewDictionaryManagement.loadDataFromHTMLFile;

public class Dictionary extends TreeMap<String, Word> {
    public Dictionary() {}

    public Dictionary(String path) {
        loadDataFromHTMLFile(this, path);
    }

    public void editWord(String wordTarget, String wordExplain) {
        this.get(wordTarget).setWordExplain(wordExplain);
    }
}