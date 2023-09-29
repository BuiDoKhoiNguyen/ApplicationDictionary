package DictionaryApplication;

import org.apache.http.impl.cookie.DefaultCookieSpecProvider;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.io.*;

public class DictionaryManagement{

    public DictionaryManagement() {
    }

    public static void insertFromCommandline(Dictionary dictionary) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the number of words: ");
        int numWord = sc.nextInt();
        sc.nextLine();

        while (numWord-- > 0) {
            System.out.print("Enter English word: ");
            String wordTarget = sc.nextLine();
            System.out.print("Enter Vietnamese meaning: ");
            String wordExplain = sc.nextLine();
            Word word = new Word(wordTarget, wordExplain);
            dictionary.add(word);
        }
    }

    public static void removeFromCommandLine(Dictionary dictionary){
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the word: ");
        String wordTarget = scanner.nextLine();

        int index = -1;
        index = Collections.binarySearch(dictionary, new Word(wordTarget, null));
        if(index >= 0) {
            dictionary.remove(index);
        }
        else System.out.println("The word isn't existed, please try again.");
    }

    public static void loadFromFile(Dictionary dictionary, String IN_PATH) {
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
                    dictionary.add(word);
                }
            }
            Collections.sort(dictionary);
            bufferedReader.close();
            System.out.println("Loaded from file successfully");
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

    public static int binaryCheck(int start, int end, String word,Dictionary dictionary) {
        if (end < start) {
            return -1;
        }
        int mid = (start + end) / 2;
        int compareNext = word.compareTo(dictionary.get(mid).getWordTarget());
        if (mid == 0) {
            if (compareNext < 0) {
                return 0;
            } else if (compareNext > 0) {
                return binaryCheck(mid + 1, end, word, dictionary);
            } else {
                return -1;
            }
        } else {
            int comparePrevious = word.compareTo(dictionary.get(mid - 1).getWordTarget());
            if (comparePrevious > 0 && compareNext < 0) {
                return mid;
            } else if (comparePrevious < 0) {
                return binaryCheck(start, mid - 1, word, dictionary);
            } else if (compareNext > 0) {
                if (mid == dictionary.size() - 1) {
                    return dictionary.size();
                }
                return binaryCheck(mid + 1, end, word, dictionary);
            } else {
                return -1;
            }
        }
    }

    public static int dictionaryLookup(Dictionary dictionary, String keyWord) {
        try {
            int left = 0;
            int right = dictionary.size() - 1;
            while (left <= right) {
                int mid = left + (right - left) / 2;
                int res = dictionary.get(mid).getWordTarget().compareTo(keyWord);
                if (res == 0) return mid;
                if (res <= 0) left = mid + 1;
                else right = mid - 1;
            }
        } catch (NullPointerException e) {
            System.out.println("Null Exception.");
        }
        return -1;
    }

    public static void showWordLookup(Dictionary dictionary,String word, int index) {
        if (index < 0) {
            return;
        }
        ArrayList<Word> listWordSearching = new ArrayList<Word>();
        int j = index;
        while (j >= 0) {
            if (isContain(word, dictionary.get(j).getWordTarget()) == 0) {
                j--;
            } else {
                break;
            }
        }
        for (int i = j + 1; i <= index; i++) {
            Word temp = new Word(dictionary.get(i).getWordTarget(), dictionary.get(i).getWordExplain());
            listWordSearching.add(temp);
        }

        for (int i = index + 1; i < dictionary.size(); i++) {
            if (isContain(word, dictionary.get(i).getWordTarget()) == 0) {
                Word temp = new Word(dictionary.get(i).getWordTarget(), dictionary.get(i).getWordExplain());
                listWordSearching.add(temp);
            } else {
                break;
            }
        }
        for (Word wordSearching : listWordSearching) {
            System.out.println(wordSearching.getWordTarget());
        }
    }

    public static String showAllWords(Dictionary dictionary) {
        String ans = "";
        System.out.printf("%-6s%c %-15s%c %-20s%n", "No", '|', "English", '|', "Vietnamese");
        for (int i = 0; i < dictionary.size(); i++) {
            System.out.printf("%-6d%c %-15s%c %-15s%n", i + 1, '|', dictionary.get(i).getWordTarget(), '|', dictionary.get(i).getWordExplain());
        }
        return ans;
    }
    public static void exportWordToFile(Dictionary dictionary,String OUT_PATH) {
        try {
            FileWriter fileWriter = new FileWriter(new File(OUT_PATH));
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (Word word : dictionary) {
                bufferedWriter.write("|" + word.getWordTarget() + "\n" + word.getWordExplain());
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        } catch (Exception e) {
            System.out.println("Something went wrong: " + e);
        }
    }

    public void updateWord(Dictionary dictionary, int index, String meaning, String path) {
        try {
            dictionary.get(index).setWordExplain(meaning);
            exportWordToFile(dictionary, path);
        } catch (NullPointerException e) {
            System.out.println("Null Exception.");
        }
    }

}


