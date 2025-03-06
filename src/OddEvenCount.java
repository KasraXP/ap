import java.util.Scanner;

public class OddEvenCount {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the size of the array");

        int oddcount = 0;
        int evencount = 0;
        int n = sc.nextInt();
        int[] arr = new int[n];

        System.out.println("Enter the elements of the array");

        for (int i = 0; i < n; i++)
            arr[i] = sc.nextInt();

        for (int i = 0; i < n; i++) {
            if (arr[i] % 2 == 0) {
                evencount++;
            } else {
                oddcount++;
            }

        }

        System.out.println("The number of odd inputs is " + oddcount);
        System.out.println("The number of even inputs is " + evencount);

    }
}
