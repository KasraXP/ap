package exercises.ex2;

import java.util.Scanner;

public class Main_EX2_PM_1_2 {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter any number for your hollow square: ");

        int k = sc.nextInt();
        String[][] square = new String[k][k];

        for (int i = 0; i < k + 2; i++) {
            for (int j = 0; j < k + 2; j++) {
                if (i == 0 || i == k + 1 || j == 0 || j == k + 1)
                    System.out.print("*");
                else
                    System.out.print(" ");
            }
            System.out.println();
        }

    }

}
