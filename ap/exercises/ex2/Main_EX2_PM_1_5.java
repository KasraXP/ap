package exercises.ex2;

import java.util.Random;
import java.util.Scanner;

public class Main_EX2_PM_1_5 {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter any number for your hollow square ");
        int k = sc.nextInt();
        System.out.println("Enter the number of dots ");
        int c = sc.nextInt();

        if (c > k * k) {
            while (c > k * k) {
                System.out.println("Too many dots!");
                System.out.println("Please enter a smaller number ");
                c = sc.nextInt();
            }

        }

        char[][] square = new char[k + 2][k + 2];

        for (int i = 0; i < k + 2; i++) {
            for (int j = 0; j < k + 2; j++) {
                if (i == 0 || i == k + 1 || j == 0 || j == k + 1)
                    square[i][j] = '*';
                else
                    square[i][j] = ' ';
            }

        }

        char PacMan = 'X';
        int R = 1;
        int newR = R;
        int C = 1;
        int newC = C;
        square[R][C] = PacMan;
        Random rand = new Random();
        int PlacedDots = 0;

        while (PlacedDots < c) {
            int x = rand.nextInt(k - 1) + 1;
            int y = rand.nextInt(k - 1) + 1;

            if (square[x][y] == ' ') {
                square[x][y] = '.';
                PlacedDots++;
            }

        }

        Random rand2 = new Random();
        while (true) {

            newR = R;
            newC = C;
            int Drect = rand2.nextInt(4);

            switch (Drect) {

                case 0:
                    System.out.println("UP");
                    newR = R - 1;
                    break;

                case 1:
                    System.out.println("RIGHT");
                    newC = C + 1;
                    break;

                case 2:
                    System.out.println("DOWN");
                    newR = R + 1;
                    break;

                case 3:
                    System.out.println("LEFT");
                    newC = C - 1;
                    break;

            }


            if (square[newR][newC] == '*') {
                System.out.println("hitting the game wall");

            } else {
                square[R][C] = ' ';
                R = newR;
                C = newC;
                square[R][C] = PacMan;
            }

            for (int i = 0; i < k + 2; i++) {
                for (int j = 0; j < k + 2; j++) {
                    System.out.print(square[i][j]);

                }
                System.out.println();
            }


            try {
                Thread.sleep(1000);
            } catch (Exception e) {
            }


        }

    }

}





