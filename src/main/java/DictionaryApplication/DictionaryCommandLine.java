package DictionaryApplication;

import java.util.*;
import java.io.IOException;

public class DictionaryCommandLine extends Dictionary {
    public static String showAllWords() {
        String ans = "";
        System.out.printf("%-6s%c %-15s%c %-20s%n","No", '|' ,"English", '|', "Vietnamese");
        for (int i = 0; i < vocab.size(); i++) {
            System.out.printf("%-6d%c %-15s%c %-15s%n", i + 1,'|', vocab.get(i).getWordTarget(), '|',vocab.get(i).getWordExplain());
        }
        return ans;
    }
    public void dictionaryBasic() {
        DictionaryManagement.insertFromFile();
        //DictionaryManagement.dictionaryLookUp();
        System.out.println(showAllWords());
    }

}
