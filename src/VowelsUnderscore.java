import java.util.Scanner;

public class VowelsUnderscore{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a string: ");
        String input = scanner.nextLine();

        String result = "";

        for (int i = 0; i < input.length(); i++) {
            String currentChar = input.substring(i, i + 1);

            if (currentChar.equals("a") || currentChar.equals("e") || currentChar.equals("i") ||
                    currentChar.equals("o") || currentChar.equals("u") ||
                    currentChar.equals("A") || currentChar.equals("E") || currentChar.equals("I") ||
                    currentChar.equals("O") || currentChar.equals("U")) {

                result += "_";
            } else {
                result += currentChar;
            }
        }

        System.out.println("String with vowels replaced: " + result);

    }
}