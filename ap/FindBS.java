import java.util.Scanner;

public class FindBS {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the size of the array :");
        int n = sc.nextInt();
        int[] arr = new int[n];

        System.out.println("Enter the elements of the array:");
        for (int i = 0; i < n; i++)
            arr[i] = sc.nextInt();

        int L = arr[0];
        int S = arr[0];

        for (int i = 0; i < n; i++) {
            if (arr[i] > L) L = arr[i];
            if (arr[i] < S) S = arr[i];
        }

        System.out.println("The largest input is " + L + " and the smallest input is " + S );
    }
}