package exercises.ex2;

import java.util.Scanner;
import java.util.Random;

public class Main_EX2_PM_2_2 {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the number of the length of square: ");
        int k = sc.nextInt();
        System.out.println("Enter the number of dots ");
        int InputDots = sc.nextInt();
        if (InputDots > k * k) {
            while (InputDots > k * k) {
                System.out.println("Too many dots!");
                System.out.println("Please enter a smaller number ");
                InputDots = sc.nextInt();
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

        long start = System.currentTimeMillis();


        char PacMan = 'X';
        int R = 1;
        int newR = R;
        int C = 1;
        int newC = C;
        int Score = 0;
        square[R][C] = PacMan;
        char Direction;
        boolean flag = false;
        int PlacedDots = 0;
        Random rand = new Random();

        while (PlacedDots < InputDots) {

            int x = rand.nextInt(k - 1) + 1;
            int y = rand.nextInt(k - 1) + 1;

            if (square[x][y] == ' ') {
                square[x][y] = '.';
                PlacedDots++;
            }

        }

        if (InputDots == 0) {
            flag = true;
            long finish = System.currentTimeMillis();
            long timeElapsed = finish - start;
        }

        for (int i = 0; i < k + 2; i++) {
            for (int j = 0; j < k + 2; j++) {
                System.out.print(square[i][j]);

            }
            System.out.println();
        }

        System.out.println("Enter your direction ( q to quit): ");
        while (true) {


            Direction = sc.next().charAt(0);

            newR = R;
            newC = C;

            switch (Direction) {

                case 'w':
                    newR = R - 1;
                    break;

                case 'a':
                    newC = C - 1;
                    break;

                case 's':
                    newR = R + 1;
                    break;

                case 'd':
                    newC = C + 1;
                    break;

                case 'q':
                    System.out.println("Goodbye!");
                    try {
                        Thread.sleep(2000);
                    } catch (Exception e) {
                    }
                    System.exit(0);

                default:
                    System.out.println("Invalid direction, please try again");
                    break;
            }

            if (square[newR][newC] == '.') {
                Score++;
                InputDots--;

            }

            if (InputDots == 0) {
                flag = true;
                long finish = System.currentTimeMillis();
                long timeElapsed = finish - start;

            }

            if (square[newR][newC] == '*') {
                System.out.println("hitting the game wall");
                System.out.println("try again");

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

            System.out.println("Score: " + Score);

            if (flag) {
                System.out.println("You win");
                long finish = System.currentTimeMillis();
                long timeElapsed = finish - start;
                System.out.println("time: " + timeElapsed * 0.001 + " seconds");
            }

            try {
                Thread.sleep(1000);
            } catch (Exception e) {
            }


        }
    }
}