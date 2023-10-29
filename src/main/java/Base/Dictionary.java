package Base;

import java.util.TreeMap;

public class Dictionary extends TreeMap<String, Word> {
    public void editWord(String wordTarget, String wordExplain) {
        this.get(wordTarget).setWordExplain(wordExplain);
    }
}

