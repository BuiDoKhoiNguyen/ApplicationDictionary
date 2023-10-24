package DictionaryApplication;

public class Word implements Comparable<Word> {
    private String wordTarget;
    private String wordExplain;
    private boolean favoured;

    public Word() {
        this.wordTarget = "";
        this.wordExplain = "";
        this.favoured = false;
    }

    public Word(String wordTarget, String wordExplain) {
        this.wordTarget = wordTarget;
        this.wordExplain = wordExplain;
    }

    public void setWordTarget(String wordTarget) {
        this.wordTarget = wordTarget;
    }

    public void setWordExplain(String wordExplain) {
        this.wordExplain = wordExplain;
    }

    public String getWordTarget() {
        return wordTarget;
    }

    public String getWordExplain() {
        return wordExplain;
    }

    public boolean isFavoured() {
        return favoured;
    }

    public void setFavoured(boolean favoured) {
        this.favoured = favoured;
    }

    @Override
    public int compareTo(Word other) {
        return this.wordTarget.compareToIgnoreCase(other.wordTarget);
    }
}