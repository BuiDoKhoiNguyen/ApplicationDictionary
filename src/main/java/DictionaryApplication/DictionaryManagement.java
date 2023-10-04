package DictionaryApplication;

<<<<<<< Updated upstream
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.io.*;

public class DictionaryManagement extends Dictionary {
    private static final String IN_PATH = "src/main/resources/dictionaries.txt";

    public DictionaryManagement() {
    }
=======
import java.util.*;
import java.io.*;

public class DictionaryManagement {
    public DictionaryManagement() {}
>>>>>>> Stashed changes

    public static void insertFromCommandline() {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the number of words: ");
        int numWord = sc.nextInt();
        sc.nextLine();


        while (numWord-- > 0) {
            System.out.print("Enter English word: ");

            String wordTarget = sc.nextLine();
            String wordExplain = sc.nextLine();
<<<<<<< Updated upstream
            Word word = new Word(wordTarget,wordExplain);
            vocab.add(word);
        }
    }

    public static void insertFromFile() {
=======
            dictionary.put(wordTarget, new Word(wordTarget, wordExplain));
        }
    }

    public static void removeFromCommandLine(Dictionary dictionary) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the word: ");
        String wordTarget = sc.nextLine();

        if (dictionary.containsKey(wordTarget))
            dictionary.remove(wordTarget);
        else System.out.println("The word isn't existed, please try again.");
    }

    public static void editFromCommandline(Dictionary dictionary) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the word: ");
        String wordTarget = sc.nextLine();
        if (dictionary.containsKey(wordTarget)) {
            System.out.print("Enter the meaning: ");
            String wordExplain = sc.nextLine();
            dictionary.editWord(wordTarget, wordExplain);
        } else System.out.println("The word isn't existed, please try again.");
    }

    public static void loadFromFile(Dictionary dictionary, String IN_PATH) {
>>>>>>> Stashed changes
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
<<<<<<< Updated upstream
                    vocab.add(word);
                }
            }
            Collections.sort(vocab);
=======
                    dictionary.put(word.getWordTarget(), word);
                }
            }
//            Collections.sort(dictionary);
>>>>>>> Stashed changes
            bufferedReader.close();
        } catch (IOException e) {
            System.out.println("An error occur with file: " + e);
        } catch (Exception e) {
            System.out.println("Something went wrong: " + e);
        }

    }

<<<<<<< Updated upstream
=======
    public static void showWords(Set<Map.Entry<String, Word>> entrySet) {
        System.out.printf("%-6s%c %-15s%c %-20s%n", "No", '|', "English", '|', "Vietnamese");

        int no = 0;
        for (Map.Entry<String, Word> mapElement : entrySet) {
            System.out.printf("%-6d%c %-15s%c %-15s%n", ++no, '|', mapElement.getKey(), '|',
                    mapElement.getValue().getWordExplain());
        }
    }
>>>>>>> Stashed changes

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

<<<<<<< Updated upstream
    public static int binaryCheck(int start, int end, String word) {
=======
    public static int binaryCheck(int start, int end, String word, Dictionary dictionary) {
>>>>>>> Stashed changes
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

<<<<<<< Updated upstream
    public static int binaryLookup(int start, int end, String word) {
        if (end < start) {
            return -1;
        }
        int mid = (start + end) / 2;
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

=======
//    public static int dictionaryLookup(Dictionary dictionary, String keyWord) {
//        try {
//            int left = 0;
//            int right = dictionary.size() - 1;
//            while (left <= right) {
//                int mid = left + (right - left) / 2;
//                int res = dictionary.get(mid).getWordTarget().compareTo(keyWord);
//                if (res == 0) return mid;
//                if (res <= 0) left = mid + 1;
//                else right = mid - 1;
//            }
//        } catch (NullPointerException e) {
//            System.out.println("Null Exception.");
//        }
//        return -1;
//    }

    private static SortedMap<String, Word> partialSearch(Dictionary dictionary, String wordTarget) {
        if (!wordTarget.isEmpty()) {
            char nextLetter = (char) (wordTarget.charAt(wordTarget.length() - 1) + 1);
            String end = wordTarget.substring(0, wordTarget.length() - 1) + nextLetter;
            return dictionary.subMap(wordTarget, end);
        }
        return dictionary;
    }

    public static void dictionarySearcher(Dictionary dictionary) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter target: ");
        String wordTarget = sc.nextLine();
        showWords(partialSearch(dictionary, wordTarget).entrySet());
    }

//    public static void showWordLookup(Dictionary dictionary, String word, int index) {
//        if (index < 0) {
//            return;
//        }
//        ArrayList<Word> listWordSearching = new ArrayList<Word>();
//        int j = index;
//        while (j >= 0) {
//            if (isContain(word, dictionary.get(j).getWordTarget()) == 0) {
//                j--;
//            } else {
//                break;
//            }
//        }
//        for (int i = j + 1; i <= index; i++) {
//            Word temp = new Word(dictionary.get(i).getWordTarget(), dictionary.get(i).getWordExplain());
//            listWordSearching.add(temp);
//        }
//
//        for (int i = index + 1; i < dictionary.size(); i++) {
//            if (isContain(word, dictionary.get(i).getWordTarget()) == 0) {
//                Word temp = new Word(dictionary.get(i).getWordTarget(), dictionary.get(i).getWordExplain());
//                listWordSearching.add(temp);
//            } else {
//                break;
//            }
//        }
//        for (Word wordSearching : listWordSearching) {
//            System.out.println(wordSearching.getWordTarget());
//        }
//    }

//    public static String showAllWords(Dictionary dictionary) {
//        String ans = "";
//        System.out.printf("%-6s%c %-15s%c %-20s%n", "No", '|', "English", '|', "Vietnamese");
//        for (int i = 0; i < dictionary.size(); i++) {
//            System.out.printf("%-6d%c %-15s%c %-15s%n", i + 1, '|', dictionary.get(i).getWordTarget(), '|', dictionary.get(i).getWordExplain());
//        }
//        return ans;
//    }
    public static void insertFromFile(Dictionary dictionary) throws FileNotFoundException {
        Scanner sc = new Scanner(new File("data/E_V.txt"));
        while (sc.hasNextLine()) {
            String lineFetched = sc.nextLine();
            String[] lineParts = lineFetched.split("<html>");
            if (lineParts.length == 2) {
                String wordTarget = lineParts[0];
                String wordExplain = "<html>" + lineParts[1];
                dictionary.put(wordTarget, new Word(wordTarget, wordExplain));
            } else System.out.println("Error input: " + lineParts.length + " words");
        }
    }

    public static void exportWordToFile(Dictionary dictionary, String OUT_PATH) {
        try {
            FileWriter fileWriter = new FileWriter(new File(OUT_PATH));
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (Map.Entry<String, Word> mapElement : dictionary.entrySet()) {
                bufferedWriter.write("|" + mapElement.getValue().getWordTarget() + "\n" +
                        mapElement.getValue().getWordExplain());
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        } catch (Exception e) {
            System.out.println("Something went wrong: " + e);
        }
    }

//    public void updateWord(Dictionary dictionary, int index, String meaning, String path) {
//        try {
//            dictionary.get(index).setWordExplain(meaning);
//            exportWordToFile(dictionary, path);
//        } catch (NullPointerException e) {
//            System.out.println("Null Exception.");
//        }
//    }
>>>>>>> Stashed changes
}


