package exercises.ex2;

import java.io.*;
import java.util.Scanner;
import java.util.Random;

public class Main_EX2_PM_2_3 {
    private static final String SAVE_FILE = "game_save.txt";

    public static void saveGame(int k, int R, int C, int Score, int InputDots, char[][] square) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(SAVE_FILE))) {
            writer.println(k);
            writer.println(R + "," + C);
            writer.println(Score);
            writer.println(InputDots);
            for (int i = 0; i < k + 2; i++) {
                writer.println(new String(square[i]));
            }
            System.out.println("Game saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving the game: " + e.getMessage());
        }
    }

    public static int[] loadGame() {
        try (Scanner fileScanner = new Scanner(new File(SAVE_FILE))) {
            int k = fileScanner.nextInt();
            fileScanner.nextLine();
            String[] position = fileScanner.nextLine().split(",");
            int R = Integer.parseInt(position[0]);
            int C = Integer.parseInt(position[1]);
            int Score = fileScanner.nextInt();
            int InputDots = fileScanner.nextInt();
            fileScanner.nextLine();
            char[][] square = new char[k + 2][k + 2];
            for (int i = 0; i < k + 2; i++) {
                square[i] = fileScanner.nextLine().toCharArray();
            }
            System.out.println("Game loaded successfully.");
            return new int[]{k, R, C, Score, InputDots};
        } catch (FileNotFoundException e) {
            System.out.println("No previous saved game found. Starting a new game.");
            return null;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int k, R = 1, C = 1, Score = 0, InputDots;
        char[][] square;

        System.out.println("Do you want to load a saved game? (y/n): ");
        char loadOption = sc.next().charAt(0);

        if (loadOption == 'y' || loadOption == 'Y') {
            int[] loadedValues = loadGame();
            if (loadedValues != null) {
                k = loadedValues[0];
                R = loadedValues[1];
                C = loadedValues[2];
                Score = loadedValues[3];
                InputDots = loadedValues[4];
                square = new char[k + 2][k + 2];
            } else {
                System.out.println("Enter the number of the length of square: ");
                k = sc.nextInt();
                System.out.println("Enter the number of dots: ");
                InputDots = sc.nextInt();
                square = new char[k + 2][k + 2];
            }
        } else {
            System.out.println("Enter the number of the length of square: ");
            k = sc.nextInt();
            System.out.println("Enter the number of dots: ");
            InputDots = sc.nextInt();
            square = new char[k + 2][k + 2];
        }

        Random rand = new Random();
        for (int i = 0; i < k + 2; i++) {
            for (int j = 0; j < k + 2; j++) {
                square[i][j] = (i == 0 || i == k + 1 || j == 0 || j == k + 1) ? '*' : ' ';
            }
        }

        int PlacedDots = 0;
        while (PlacedDots < InputDots) {
            int x = rand.nextInt(k) + 1;
            int y = rand.nextInt(k) + 1;
            if (square[x][y] == ' ') {
                square[x][y] = '.';
                PlacedDots++;
            }
        }

        square[R][C] = 'X';
        System.out.println("Enter direction (w/a/s/d) or 'q' to quit:");
        long start = System.currentTimeMillis();
        while (true) {
            for (char[] row : square) {
                System.out.println(new String(row));
            }
            System.out.println("Score: " + Score);
            char move = sc.next().charAt(0);
            int newR = R, newC = C;
            switch (move) {
                case 'w':
                    newR--;
                    break;
                case 'a':
                    newC--;
                    break;
                case 's':
                    newR++;
                    break;
                case 'd':
                    newC++;
                    break;
                case 'q':
                    saveGame(k, R, C, Score, InputDots, square);
                    System.out.println("Game saved. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid input. Try again.");
                    continue;
            }
            if (square[newR][newC] == '*') {
                System.out.println("Hit the wall! Try again.");
                continue;
            }
            if (square[newR][newC] == '.') {
                Score++;
                InputDots--;
            }
            square[R][C] = ' ';
            R = newR;
            C = newC;
            square[R][C] = 'X';
            if (InputDots == 0) {
                System.out.println("You win! Score: " + Score);
                long finish = System.currentTimeMillis();
                long timeElapsed = finish - start;
                System.out.println(" time : " + timeElapsed * 0.001);

                return;
            }
        }
    }

}