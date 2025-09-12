package projects.finalproject;
import java.util.Scanner;

public class Manager {
    private final String name;
    private final String password;

    Manager() {
        name = "TheOwner";
        password = "1234567890";
    }

    Boolean managerVerification(Scanner scanner) {

        while (true) {

            String managerName = null;
            String managerPassword;


            System.out.println("Enter your username: ");
            managerName = scanner.nextLine();

            if (managerName != null && managerName.equalsIgnoreCase("exit")) {
                System.out.println("Exiting...");
                return false;
            }

            System.out.println("Enter your password: ");
            managerPassword = scanner.nextLine();

            if (managerName.equals(name) && managerPassword.equals(password)) {
                System.out.println("Manager verified");
                return true;
            } else {
                System.out.println("Wrong password or name\nplease try again or type (exit) to cancel");

            }

        }

    }

}
