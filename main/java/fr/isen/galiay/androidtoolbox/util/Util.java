package fr.isen.galiay.androidtoolbox.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class Util {
    public static String convertStreamToString(InputStream is) {
    /*
     * To convert the InputStream to String we use the
     * BufferedReader.readLine() method. We iterate until the BufferedReader
     * return null which means there's no more data to read. Each line will
     * appended to a StringBuilder and returned as String.
     */
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public static String toFirstLetterUpperCase(String input) {
        if (!input.contains(" "))
            return input.substring(0, 1).toUpperCase() + input.substring(1, input.length());
        else
            if (input.contains(" ")) {
                StringBuilder output = new StringBuilder();

                for (String s : input.split(" "))
                    output.append(s.substring(0, 1).toUpperCase()).append(s.substring(1, s.length())).append(" ");

                return output.toString().substring(0, output.toString().length() - 1);
            }

        return null;
    }
}
