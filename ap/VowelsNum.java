import java.util.Scanner;

public class VowelsNum {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a string: ");
        String input = scanner.nextLine();

        int count = 0;

        for (int i = 0; i < input.length(); i++) {
            String currentChar = input.substring(i, i + 1);

            if (currentChar.equals("a") || currentChar.equals("e") || currentChar.equals("i") ||
                    currentChar.equals("o") || currentChar.equals("u") ||
                    currentChar.equals("A") || currentChar.equals("E") || currentChar.equals("I") ||
                    currentChar.equals("O") || currentChar.equals("U"))

                count++;
        }
        System.out.println(" thr number of vowels: " + count);


    }
}