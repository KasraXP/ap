import java.util.Scanner;

public class OddDigits {

    public static void main(String[] args) {

        System.out.println(" please enter a number: ");
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int sum = 0, r = 0;

        while (n != 0) {
            r = n % 10;
            if (r % 2 == 1) {
                sum = sum + r;
                n /= 10;
            } else {
                r = 0;
                sum = sum + r;
                n /= 10;
            }
        }
        System.out.println(sum);
    }


}
