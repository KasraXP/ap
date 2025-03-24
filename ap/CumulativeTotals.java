import java.util.ArrayList;
import java.util.Scanner;

public class CumulativeTotals {

    public static void main(String[ ] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter a sequence of integers (end with a non-integer):");

        ArrayList<Integer> ints = new ArrayList<>();
        while (sc.hasNextInt()) {
            ints.add(sc.nextInt());
        }
        int sum = 0;
        int i = 0 , j = 0;
        for (i = 0;(i < ints.size() ) && (i <= j); i++) {
            sum = sum + ints.get(i);
            j++;

        }

        System.out.println(sum);
        i = 0;





        }


    }



