package DictionaryApplication;

import java.io.IOException;
import java.util.Scanner;
import com.darkprograms.speech.translator.GoogleTranslate;

public class TranslatorExample {
    public static void googleTranslator(){
        try {
            Scanner sc = new Scanner(System.in);

            System.out.print("Enter the number of words: ");
            int numOfWord = sc.nextInt();
            sc.nextLine();

            while (numOfWord-- > 0){
                System.out.print("Enter English word: ");
                String text = sc.next();
                System.out.println("Meaning: " + GoogleTranslate.translate("vi", text));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}