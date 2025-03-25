package exercises.ex2;

import java.util.Scanner;

public class Main_EX2_PM_1_4 {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter any direction w,a,s,d, or q to quit ");


        while (true) {

            char Direct = sc.next().charAt(0);
            switch (Direct) {


                case 'w':
                    System.out.println("UP");
                    break;
                case 'W':
                    System.out.println("UP");
                    break;


                case 'a':
                    System.out.println("LEFT");
                    break;
                case 'A':
                    System.out.println("LEFT");
                    break;


                case 's':
                    System.out.println("DOWN");
                    break;
                case 'S':
                    System.out.println("DOWN");
                    break;


                case 'd':
                    System.out.println("RIGHT");
                    break;
                case 'D':
                    System.out.println("RIGHT");
                    break;


                case 'q':
                    System.out.println("EXIT");
                    break;
                case 'Q':
                    System.out.println("EXIT");
                    break;


                default:
                    System.out.println("WARNING");


            }

        }

    }

}


