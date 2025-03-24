import java.util.ArrayList;
import java.util.Scanner;

public class AdjacentDuplicates {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        ArrayList<Integer> numbers = new ArrayList<>();

        System.out.println("Enter a sequence of integers (end with a non-integer):");

        while (scan.hasNextInt()) {
            numbers.add(scan.nextInt());
        }
        

        System.out.print("Adjacent duplicates: ");
        for (int i = 1; i < numbers.size(); i++) {
            if (numbers.get(i).equals(numbers.get(i - 1))) {
                System.out.print(numbers.get(i) + " ");
            }
        }
    }
}