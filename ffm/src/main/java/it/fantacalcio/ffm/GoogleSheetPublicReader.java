package it.fantacalcio.ffm;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GoogleSheetPublicReader {

   public static void main(String[] args) {
        try {
            // URL per esportare il foglio in formato CSV
            String sheetUrl = "https://docs.google.com/spreadsheets/d/1Bb9GfXisOTYDnT7JCBGYXKAa3BLUthS-YpViTJSHrZw/export?format=csv&gid=1180225410";
            URL url = new URL(sheetUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Lettura dei dati dal foglio
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}