import com.sun.org.apache.xpath.internal.objects.XString;

import java.util.Scanner;
import java.lang.String;

public class SeparetedWord {

    public static void main(String[] args) {

        System.out.println("Enter a word: ");
        Scanner scanner = new Scanner(System.in);
        String word = scanner.next();

        String separetedWord = "";
        for (int i = 0; i < word.length(); i++) {
            separetedWord = word.substring(i, i + 1);
            System.out.println(separetedWord);
        }


    }
}
