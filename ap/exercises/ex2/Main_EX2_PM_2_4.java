package exercises.ex2;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Main_EX2_PM_2_4 {

    public static void main(String[] args) {

        int k = 9;
        int c = 15;

        Random rnd = new Random();

        PacmanEngine pacmanEngine = new PacmanEngine(k, c);

        while (true) {
            pacmanEngine.printMatrix();
            pacmanEngine.printScore();
            pacmanEngine.printRemainTime();

            try {
                Thread.sleep(2000);
            } catch (Exception e) {
            }

            int direction = rnd.nextInt(4);
            pacmanEngine.move(direction);
            pacmanEngine.save();
        }

     }
}

class PacmanEngine {
    private static final String SAVE_FILE = "game_save.txt";
    int k, c, score, R, C, newC, newR;
    long start, finish;
    char[][] matrix;

    PacmanEngine(int k, int c) {
        this.k = k;
        this.c = c;
        designMap(k, c);
        printMatrix();
        start = System.currentTimeMillis();

    }

    public void designMap(int k, int c) {
        matrix = new char[k + 2][k + 2];
        for (int i = 0; i < k + 2; i++) {
            for (int j = 0; j < k + 2; j++) {
                if (i == 0 || i == k + 1 || j == 0 || j == k + 1)
                    matrix[i][j] = '*';
                else
                    matrix[i][j] = ' ';

            }
        }
        char PacMan = 'X';
        R = 1;
        C = 1;
        matrix[R][C] = PacMan;
        this.score = 0;
        newR = R;
        newC = C;

        int PlacedDots = 0;
        Random rand = new Random();
        while (PlacedDots < c) {
            int x = rand.nextInt(k) + 1;
            int y = rand.nextInt(k) + 1;

            if (matrix[x][y] == ' ') {
                matrix[x][y] = '.';
                PlacedDots++;
            }
        }
    }

    void printMatrix() {
        for (char[] row : matrix) {
            for (char col : row) {
                System.out.print(col);
            }
            System.out.print("\n");
        }
    }

    void printScore() {
        System.out.println("Score: " + score);
    }

    void printRemainTime() {
        finish = System.currentTimeMillis();
        System.out.println("Remain time: " + (finish - start) / 1000.0 + " seconds");
    }

    void move(int direction) {
        switch (direction) {

            case 0:
                newR = R - 1;
                break;

            case 1:
                newC = C - 1;
                break;

            case 2:
                newR = R + 1;
                break;

            case 3:
                newC = C + 1;
                break;

            default:
                System.out.println("Invalid direction, please try again");
                break;
        }

        if (matrix[newR][newC] == '.') {
            score++;
            c--;
        }

        if (matrix[newR][newC] == '*') {
            System.out.println("Hitting the game wall");
            System.out.println("Try again");
        } else {
            matrix[R][C] = ' ';
            R = newR;
            C = newC;
            matrix[R][C] = 'X';
        }
    }

    void save() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(SAVE_FILE))) {
            writer.println(k);
            writer.println(R);
            writer.println(C);
            writer.println(score);
            writer.println(c);
            for (int i = 0; i < k + 2; i++) {
                writer.println(new String(matrix[i]));
            }
            System.out.println("Game saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving the game: " + e.getMessage());
        }
    }
}