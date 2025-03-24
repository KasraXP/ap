import java.util.Scanner;
    public class UppercasePrint {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a string: ");
        String input = scanner.nextLine();

        System.out.print("Uppercase letters: ");
        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);
            int ascii = (int) ch;

            if (ascii >= 65 && ascii <= 90) {
                System.out.print(ch);
            }
        }

        System.out.println();
        scanner.close();
    }
}