import java.util.Scanner;

public class BineryPrint{

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the positive number: ");
        int number = scanner.nextInt();

        if (number == 0) {
            System.out.println("Binary representation: 0");
            return;
        }

        String binary = "";

        while (number != 0) {
            int r = number % 2;
            binary = r + binary;
            number = number / 2;
        }

        System.out.println( binary );
    }
}

