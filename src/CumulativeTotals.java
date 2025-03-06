import java.util.Scanner;
public class CumulativeTotals {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter a sequence of integers (end with a non-integer):");

        int sum = 0;

        while (scanner.hasNextInt()) {
            int num = scanner.nextInt();
            sum += num;
            System.out.print(sum + " ");
        }

    }
}
