
    import java.util.Scanner;

    public class number{

        public static void main(String[] args) {

            float x;
            Scanner input = new Scanner(System.in);
            System.out.println("Enter number: ");
            x = input.nextFloat();


            if (x == 0)
                System.out.println("Zero");
            else if ((x < 1) && (x > -1))
                System.out.println(" Small");
            else if ((x > 1000000) || (x < -1000000))
                System.out.println("Large");
            else if (x > 0)
                System.out.println("Positive");
            else if (x < 0)
                System.out.println("Negative");

        }
    }