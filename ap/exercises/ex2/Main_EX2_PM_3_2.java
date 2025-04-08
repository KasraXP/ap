package exercises.ex2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import java.util.Scanner;

public class Main_EX2_PM_3_2 extends JFrame implements KeyListener {

    private Point pacmanPoint = new Point();
    private Point dotPoint = new Point();
    private int score = 0;
    private int remainDots;
    private int inputDots;
    private int timeLimitMinutes;
    private long startTime;

    final int width = 300, height = 300, boxSize = 5;
    int direction = 1;
    final int A = 1, D = 2, W = 3, S = 4;

    public Main_EX2_PM_3_2(int dots, int timeLimitMinutes) {
        addKeyListener(this);
        inputDots = dots;
        remainDots = dots;
        this.timeLimitMinutes = timeLimitMinutes;
        pacmanPoint.setLocation((width / boxSize) / 2, (height / boxSize) / 2);
        getNewDotPointLocation();
        setSize(width, height);
        startTime = System.currentTimeMillis();
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        g.clearRect(0, 0, width, height);
        logic();
        drawPacman(g2D);
        drawDotPoint(g2D);
        drawScoreAndPrintTime(g2D);
        setVisible(true);
    }

    private void drawPacman(Graphics2D g2d) {
        g2d.setColor(Color.BLUE);
        g2d.fillRect(pacmanPoint.x * boxSize, pacmanPoint.y * boxSize, boxSize, boxSize);
    }

    private void drawDotPoint(Graphics2D g2d) {
        g2d.setColor(Color.RED);
        g2d.fillRect(dotPoint.x * boxSize, dotPoint.y * boxSize, boxSize, boxSize);
    }

    private void drawScoreAndPrintTime(Graphics2D g2d) {
        g2d.setColor(Color.BLACK);

        long now = System.currentTimeMillis();
        long elapsedSeconds = (now - startTime) / 1000;
        long totalTimeSeconds = timeLimitMinutes * 60;
        long remainingTime = totalTimeSeconds - elapsedSeconds;

        if (remainingTime <= 0) {
            System.out.println("Time's Up!");
            System.out.println("You Lose!");
            try {
                Thread.sleep(3000);
            } catch (Exception e) {
            }
            System.exit(0);
        }

        g2d.drawString("Your Score: " + score, 25, 50);
        g2d.drawString("RemainDots: " + remainDots, 25, 60);
        g2d.drawString("Time Left: " + remainingTime + " sec", 170, 50);
    }

    private void logic() {
        if (dotPoint.equals(pacmanPoint)) {
            getNewDotPointLocation();
            score++;
            remainDots--;
            System.out.println("Score: " + score);

            if (remainDots == 0) {
                System.out.println("You Win!");
                try {
                    Thread.sleep(3000);
                } catch (Exception e) {
                }
                System.exit(0);
            }
        }
        movePacman();
    }

    private void movePacman() {
        int rMovement = 0, cMovement = 0;

        switch (direction) {
            case A:
                rMovement = -1;
                break;
            case D:
                rMovement = 1;
                break;
            case W:
                cMovement = -1;
                break;
            case S:
                cMovement = 1;
                break;
        }

        pacmanPoint.setLocation(pacmanPoint.x + rMovement, pacmanPoint.y + cMovement);
        handleCrossBorder();
    }

    private void getNewDotPointLocation() {
        Random rand = new Random();
        int delta = 2;
        dotPoint.setLocation(rand.nextInt(width / boxSize - 2 * delta) + delta, rand.nextInt(height / boxSize - 2 * delta) + delta);
    }

    private void handleCrossBorder() {
        if (pacmanPoint.x >= 58) {
            pacmanPoint.x--;
            System.out.println("Hitting the game wall");
        } else if (pacmanPoint.y >= 58) {
            pacmanPoint.y--;
            System.out.println("Hitting the game wall");
        } else if (pacmanPoint.x <= 1) {
            pacmanPoint.x++;
            System.out.println("Hitting the game wall");
        } else if (pacmanPoint.y <= 7) {
            pacmanPoint.y++;
            System.out.println("Hitting the game wall");
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                direction = W;
                break;
            case KeyEvent.VK_S:
                direction = S;
                break;
            case KeyEvent.VK_A:
                direction = A;
                break;
            case KeyEvent.VK_D:
                direction = D;
                break;
            case KeyEvent.VK_P:
                direction = 0;
                break;
            case KeyEvent.VK_Q:
                System.out.println("Goodbye");
                System.exit(0);
                break;
            default:
                direction = -1;
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter number of dots:");
        int dots = scanner.nextInt();

        System.out.println("Enter time (minutes):");
        int time = scanner.nextInt();

        Main_EX2_PM_3_2 frame = new Main_EX2_PM_3_2(dots, time);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}