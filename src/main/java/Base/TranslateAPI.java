package Base;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;
public class TranslateAPI {

    public static String googleTranslate(String langFrom, String langTo, String text) throws IOException {
        String urlScript = "https://script.google.com/macros/s/AKfycbwOy2fEDwyMPzPDwKsJ3zSEbgudEB1z_bjB3LvM8O7puTZA6Whhw6wy4bCNnGWYXJcSsQ/exec" +
                "?q=" + URLEncoder.encode(text, "UTF-8") +
                "&target=" + langTo +
                "&source=" + langFrom;
        URL url = new URL(urlScript);
        StringBuilder response = new StringBuilder();
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the number of words: ");
//        int numOfWord = sc.nextInt();
//        while (numOfWord-- > 0){
            String text = sc.nextLine();
            System.out.println("Translated text: \n" + googleTranslate("", "vi", text));
//        }
    }

}

