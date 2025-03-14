import java.util.Scanner;

public class ReverseWord {
    public static void main(String[] args) {

        System.out.println("Enter the string to reverse : ");
        Scanner scanner = new Scanner(System.in);
        String word = scanner.nextLine();

        for (int i = word.length() - 1; i >= 0; i--) {
            System.out.print( word.charAt(i));
        }

    }
}
