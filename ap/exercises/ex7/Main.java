package exercises.ex7;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        StorageHandler storage = new TabSplitStorage();

        ArrayList<Book> books = storage.loadBooks();
        ArrayList<Student> students = storage.loadStudents();
        ArrayList<Librarian> librarians = storage.loadLibrarians();
        ArrayList<Loan> loans = storage.loadLoans();

        Library library = new Library("Central Library", books, students, librarians, loans);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== MAIN MENU ===");
            System.out.println("1. Student Portal");
            System.out.println("2. Librarian Portal");
            System.out.println("3. Manager Portal");
            System.out.println("4. Exit");
            System.out.print("Select option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    handleStudentPortal(library, storage, scanner);
                    break;
                case 2:
                    handleLibrarianPortal(library, storage, scanner);
                    break;
                case 3:
                    handleManagerPortal(library, storage, scanner);
                    break;
                case 4:
                    System.out.println("Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    private static void handleStudentPortal(Library library, StorageHandler storage, Scanner scanner) {
        System.out.println("\n1. Login");
        System.out.println("2. Register");
        System.out.print("Choose option: ");

        int option = scanner.nextInt();
        scanner.nextLine();

        Student student = null;
        if (option == 1) {
            student = library.studentLogin(scanner);
        } else if (option == 2) {
            student = library.registerNewStudent(scanner, storage);
        } else {
            System.out.println("Invalid option!");
            return;
        }

        if (student != null) {
            showStudentMenu(student, library, storage, scanner);
        }
    }

    private static void showStudentMenu(Student student, Library library,
                                        StorageHandler storage, Scanner scanner) {
        while (true) {
            System.out.println("\n=== STUDENT MENU ===");
            System.out.println("1. View Available Books");
            System.out.println("2. Borrow a Book");
            System.out.println("3. Return a Book");
            System.out.println("4. View My Loans");
            System.out.println("5. Logout");
            System.out.print("Choose option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    library.showAvailableBooks();
                    break;
                case 2:
                    library.borrowBook(student, storage);
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    private static void handleLibrarianPortal(Library library, StorageHandler storage, Scanner scanner) {
    }

    private static void handleManagerPortal(Library library, StorageHandler storage, Scanner scanner) {
    }
}