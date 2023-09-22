package DictionaryApplication;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.io.*;

public class DictionaryManagement extends Dictionary {
    private static final String IN_PATH = "src/main/resources/dictionaries.txt";

    public DictionaryManagement() {
    }

    public static void insertFromCommandline() {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the number of words: ");
        int numWord = sc.nextInt();
        sc.nextLine();


        while (numWord-- > 0) {
            System.out.print("Enter English word: ");

            String wordTarget = sc.nextLine();
            String wordExplain = sc.nextLine();
            Word word = new Word(wordTarget,wordExplain);
            vocab.add(word);
        }
    }

    public static void insertFromFile() {
        try {
            FileReader fileReader = new FileReader(IN_PATH);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String englishWord = bufferedReader.readLine();
            englishWord = englishWord.replace("|", "");
            String line = null;

            while ((line = bufferedReader.readLine()) != null) {
                Word word = new Word();
                word.setWordTarget(englishWord.trim());
                String meaning = line + "\n";
                while ((line = bufferedReader.readLine()) != null) {
                    if (!line.startsWith("|")) meaning += line + "\n";
                    else {
                        englishWord = line.replace("|", "");
                        break;
                    }
                    word.setWordExplain(meaning.trim());
                    vocab.add(word);
                }
            }
            Collections.sort(vocab);
            bufferedReader.close();
        } catch (IOException e) {
            System.out.println("An error occur with file: " + e);
        } catch (Exception e) {
            System.out.println("Something went wrong: " + e);

        }

    }


    public static int isContain(String str1, String str2) {
        for (int i = 0; i < Math.min(str1.length(), str2.length()); i++) {
            if (str1.charAt(i) > str2.charAt(i)) {
                return 1;
            } else if (str1.charAt(i) < str2.charAt(i)) {
                return -1;
            }
        }
        if (str1.length() > str2.length()) {
            return 1;
        }
        return 0;
    }

    public static int binaryCheck(int start, int end, String word) {
        if (end < start) {
            return -1;
        }
        int mid = (start + end) / 2;
        int compareNext = word.compareTo(vocab.get(mid).getWordTarget());
        if (mid == 0) {
            if (compareNext < 0) {
                return 0;
            } else if (compareNext > 0) {
                return binaryCheck(mid + 1, end, word);
            } else {
                return -1;
            }
        } else {
            int comparePrevious = word.compareTo(vocab.get(mid - 1).getWordTarget());
            if (comparePrevious > 0 && compareNext < 0) {
                return mid;
            } else if (comparePrevious < 0) {
                return binaryCheck(start, mid - 1, word);
            } else if (compareNext > 0) {
                if (mid == vocab.size() - 1) {
                    return vocab.size();
                }
                return binaryCheck(mid + 1, end, word);
            } else {
                return -1;
            }
        }
    }

    public static int binaryLookup(int start, int end, String word) {
        if (end < start) {
            return -1;
        }
        int mid = start + (end - start) / 2;
        int compare = isContain(word, vocab.get(mid).getWordTarget());
        if (compare == -1) {
            return binaryLookup(start, mid - 1, word);
        } else if (compare == 1) {
            return binaryLookup(mid + 1, end, word);
        } else {
            return mid;
        }
    }

    public static void showWordLookup(String word, int index) {
        if (index < 0) {
            //System.out.println((new Spelling.("src/main/java/big.txt")).correct(word.toLowerCase()));

            return;
        }
        ArrayList<Word> listWordSearching = new ArrayList<Word>();
        int j = index;
        while (j >= 0) {
            if (isContain(word, vocab.get(j).getWordTarget()) == 0) {
                j--;
            } else {
                break;
            }
        }
        for (int i = j + 1; i <= index; i++) {
            Word temp = new Word(vocab.get(i).getWordTarget(), vocab.get(i).getWordExplain());
            listWordSearching.add(temp);
        }

        for (int i = index + 1; i < vocab.size(); i++) {
            if (isContain(word, vocab.get(i).getWordTarget()) == 0) {
                Word temp = new Word(vocab.get(i).getWordTarget(), vocab.get(i).getWordExplain());
                listWordSearching.add(temp);
            } else {
                break;
            }
        }
        for (Word wordSearching : listWordSearching) {
            System.out.println(wordSearching.getWordTarget());

        }
    }

//    public static void dictionaryLookUp() throws IOException {
//        Scanner getInput = new Scanner(System.in);
//        String word = getInput.nextLine().toLowerCase();
//        int index = binaryLookup(0, vocab.size(), word);
//        if (index < 0) {
//            Spelling corrector = new Spelling("src/resource/vocab/spelling.txt");
//            word = corrector.correct(word);
//            index = binaryLookup(0, oldVocab.size(), word);
//        }
//        showWordLookup(word, index);
//    }

}


