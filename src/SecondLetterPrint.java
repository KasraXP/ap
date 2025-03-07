import java.util.Scanner;

public class SecondLetterPrint {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a line: ");
        String line = scanner.nextLine();

        String[] words = line.split(" ");

        System.out.println("Second letter of each word:");
        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            if (word.length() > 1) {
                System.out.println(word.charAt(1));
            }
        }

        scanner.close();
    }
}
