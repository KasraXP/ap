import java.util.Scanner;

public class Tax {


    public static void main(String[] args) {

        System.out.println(" please enter your income : ");
        Scanner input = new Scanner(System.in);
        double income = input.nextDouble();
        double total = 0;
        
        if(income < 50000)
            total = income * 0.01;

        else if ((income > 50000) && (income < 75000))
            total = income * 0.02;

        else if ((income > 75000) && (income < 100000))
            total = income * 0.03;

        else if ((income > 100000) && (income < 250000))
            total = income * 0.04;

        else if ((income > 250000) && (income < 500000))
            total = income * 0.05;

        else if(income> 500000)
            total = income * 0.06;

        System.out.println("total tax is " + total + "$");

            



    }
}
