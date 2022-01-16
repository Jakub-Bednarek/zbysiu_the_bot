package messagetemplates;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EmojiConverter {
    private static List<String> numberWords = Arrays.asList("zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine");

    public static String convertStringToEmojis(String wordToConvert){
        String coverted = "";
        wordToConvert = wordToConvert.toLowerCase();

        for(int i = 0; i < wordToConvert.length(); i++){
            coverted += ":regional_indicator_" + wordToConvert.charAt(i) + ": ";
        }

        return coverted;
    }

    public static String convertDigitsToEmojiStrings(int number){
        List<Integer> digits = getNumberDigits(number);
        String emojiString = "";

        for(int i : digits){
            emojiString += ":" + asString(i) + ":";
        }

        return emojiString;
    }

    private static List<Integer> getNumberDigits(int number){
        if(number == 0) {
            ArrayList<Integer> digits = new ArrayList<>();
            digits.add(0);

            return digits;
        }

        List<Integer> digits = new ArrayList<>();

        while(number > 0){
            digits.add(number % 10);
            number /= 10;
        }

        Collections.reverse(digits);

        return digits;
    }

    private static String asString(int digit){
        return numberWords.get(digit);
    }
}
