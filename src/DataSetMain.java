import java.util.Scanner;

public class DataSetMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DataSet data = new DataSet();

        System.out.print("Enter numbers separated by spaces: ");
        String input = scanner.nextLine();
        String[] values = input.split(" ");

        for (int i = 0; i < values.length; i++) {
            try {
                double num = Double.parseDouble(values[i]);
                data.add(num);
            } catch (NumberFormatException e) {
                System.out.println(values[i] + " is not a valid number and will be ignored.");
            }
        }

        System.out.println("Average: " + data.getAverage());
        System.out.println("Smallest: " + data.getSmallest());
        System.out.println("Largest: " + data.getLargest());
        System.out.println("Range: " + data.getRange());

        scanner.close();
    }
}

