package practiceProjects;

import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Pattern;

public class SentenceReversalApp {

    static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {

        Pattern pattern = Pattern.compile("\\s");

        String str = in.nextLine();
        String[] temp = pattern.split(str);
        String result = "";


        for (int i = 0; i < temp.length; i++) {
            if (i == temp.length - 1)
                result = temp[i] + result;
            else
                result = " " + temp[i] + result;
        }
        String lowerCase = result.toLowerCase();
        String capitalized = lowerCase.substring(0, 1).toUpperCase() + lowerCase.substring(1);
        System.out.println(str);
        System.out.println(capitalized);
    }
}


