package DictionaryApplication;

public class DictionaryCommandLine {
    private DictionaryManagement dictionaryManagement;

    public DictionaryCommandLine() {
        dictionaryManagement = new DictionaryManagement();
    }

    public void dictionaryBasic() {
        dictionaryManagement.loadDictionaryFromFile();
        dictionaryManagement.insertFromCommandline();
        dictionaryManagement.showAllWords();
    }

}
