

import java.util.Scanner;
import java.util.Random;
import java.io.*;

    public class Main_test{
        private static final String SAVE_FILE = "game_save.txt";

        public static void main(String[] args) {
            Scanner sc = new Scanner(System.in);
            int k, InputDots;
            char[][] square;
            int R, C, Score;
            boolean flag = false;


            GameData gameData = loadGame();
            if (gameData == null) {
                System.out.println("Enter square size: ");
                k = sc.nextInt();
                System.out.println("Enter the number of dots: ");
                InputDots = sc.nextInt();

                if (InputDots > k * k -1) {
                    while (InputDots > k * k -1) {
                        System.out.println("Too many dots!");
                        System.out.println("Please enter a smaller number ");
                        InputDots = sc.nextInt();
                    }
                }

                square = new char[k + 2][k + 2];
                for (int i = 0; i < k + 2; i++) {
                    for (int j = 0; j < k + 2; j++) {
                        if (i == 0 || i == k + 1 || j == 0 || j == k + 1)
                            square[i][j] = '*';
                        else
                            square[i][j] = ' ';
                    }
                }



                char PacMan = 'X';
                R = 1;
                C = 1;
                square[R][C] = PacMan;
                Score = 0;


                int PlacedDots = 0;
                Random rand = new Random();
                while (PlacedDots < InputDots) {
                    int x = rand.nextInt(k) + 1;
                    int y = rand.nextInt(k) + 1;

                    if (square[x][y] == ' ') {
                        square[x][y] = '.';
                        PlacedDots++;
                    }
                }

                printMap(square);

            } else {

                k = gameData.k;
                InputDots = gameData.InputDots;
                square = gameData.square;
                R = gameData.R;
                C = gameData.C;
                Score = gameData.Score;

                printMap(square);
            }
            long start = System.currentTimeMillis();
            System.out.println("Enter your direction (q to quit): ");
            while (true) {
                char Direction = sc.next().charAt(0);
                int newR = R, newC = C;

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

                        saveGame(k, R, C, Score, InputDots, square);

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
                }

                if (square[newR][newC] == '*') {
                    System.out.println("Hitting the game wall");
                    System.out.println("Try again");
                } else {
                    square[R][C] = ' ';
                    R = newR;
                    C = newC;
                    square[R][C] = 'X';
                }


                printMap(square);

                System.out.println("Score: " + Score);

                if (flag) {
                    long end = System.currentTimeMillis();
                    long elapsed = end - start;
                    System.out.println("You win!");
                    System.out.println("Game time: " + (float)(elapsed/1000) + " seconds");
                    File file = new File(SAVE_FILE);
                    file.delete();
                    break;
                }
            }
        }

         static void printMap (char[][] map){
            for (char[] row : map) {
                for (char col : row) {
                    System.out.print(col);
                }
                System.out.print("\n");
            }
        }


        private static void saveGame(int k, int R, int C, int Score, int InputDots, char[][] square) {
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


        private static GameData loadGame() {
            try (Scanner fileScanner = new Scanner(new File(SAVE_FILE))) {
                int k = fileScanner.nextInt();
                fileScanner.nextLine();
                String[] position = fileScanner.nextLine().split(",");
                int R = Integer.parseInt(position[0]);
                int C = Integer.parseInt(position[1]);
                int Score = fileScanner.nextInt();
                int InputDots = fileScanner.nextInt();
                fileScanner.nextLine();
                char[][] square = new char[k + 2][];
                for (int i = 0; i < k + 2; i++) {
                    square[i] = fileScanner.nextLine().toCharArray();
                }
                System.out.println("Game loaded successfully.");
                return new GameData(k, R, C, Score, InputDots, square);
            } catch (FileNotFoundException e) {
                System.out.println("No previous saved game found. Starting a new game.");
                return null;
            }
        }


        static class GameData {
            int k, R, C, Score, InputDots;
            char[][] square;

            public GameData(int k, int R, int C, int Score, int InputDots, char[][] square) {
                this.k = k;
                this.R = R;
                this.C = C;
                this.Score = Score;
                this.InputDots = InputDots;
                this.square = square;
            }
        }
    }

