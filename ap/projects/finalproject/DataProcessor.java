package projects.finalproject;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class DataProcessor {
    private final Scanner scanner;

    public DataProcessor() {
        scanner = new Scanner(System.in);
    }


    DataProcessor(Scanner scanner) {
        this.scanner = scanner;

    }

    Student addStudent() {
        System.out.println("\nEnter your username name: ");
        String userName = scanner.nextLine();
        System.out.println("Enter your password: ");
        String password = scanner.nextLine();
        System.out.println("Enter your Major: ");
        String major = scanner.nextLine();
        System.out.println("You have been successfully added to the library ");
        return new Student(userName, password, major, LocalDate.now());
    }

    Librarian addLibrarian() {
        System.out.println("\nEnter librarian full name: ");
        String fullName = scanner.nextLine();
        System.out.println("Enter librarian employee ID: ");
        String employeeId = scanner.nextLine();
        return new Librarian(fullName, employeeId);
    }

    Book addBook() {
        System.out.print("Enter book title: ");
        String title = scanner.nextLine();
        System.out.print("Enter book author: ");
        String author = scanner.nextLine();
        System.out.print("Enter book published year: ");
        int publishedYear = scanner.nextInt();
        System.out.println("enter book pages: ");
        int pages = scanner.nextInt();
        return new Book(title, author, publishedYear, pages);
    }

    Student studentVerification(ArrayList<Student> students, Scanner scanner) {

        System.out.println("====Student verification====");
        while (true) {
            System.out.println("Enter your username: ");
            String studentUserName = scanner.nextLine();

            if (studentUserName.equalsIgnoreCase("exit")) {
                System.out.println("Exiting...");
                return null;
            }

            System.out.println("Enter your password: ");
            String studentPassword = scanner.nextLine();

            if (studentPassword.equalsIgnoreCase("exit")) {
                System.out.println("Exiting...");
                return null;
            }

            for (Student student : students) {
                if (student.getPassword().equals(studentPassword) && student.getUserName().equals(studentUserName)) {
                    System.out.println("Student verified");
                    return student;
                }
            }

            System.out.println("Wrong password or name\nplease try again or type (exit) to cancel");

        }
    }

    Librarian librarianVerification(ArrayList<Librarian> librarians, Scanner scanner) {

        System.out.println("====Librarian verification====");

        while (true) {

            System.out.println("Enter your username: ");
            String librarianFullName = scanner.nextLine();

            if (librarianFullName != null && librarianFullName.equalsIgnoreCase("exit")) {
                System.out.println("Exiting...");
                return null;
            }

            System.out.println("Enter your password: ");
            String librarianPassword = scanner.nextLine();

            for (Librarian librarian : librarians) {
                if (librarian.getPassword().equals(librarianPassword) && librarian.getFullName().equals(librarianFullName)) {
                    System.out.println("Librarian verified");
                    return librarian;
                }
            }

            System.out.println("Wrong password or name\nplease try again or type (exit) to cancel");

        }
    }

    public boolean managerVerification(Scanner scanner, Manager manager) {
        while (true) {
            System.out.print("Enter your username: ");
            String managerName = scanner.nextLine();

            if ("exit".equalsIgnoreCase(managerName)) {
                System.out.println("Exiting...");
                return false;
            }

            System.out.print("Enter your password: ");
            String managerPassword = scanner.nextLine();

            if ("exit".equalsIgnoreCase(managerPassword)) {
                System.out.println("Exiting...");
                return false;
            }

            if (managerName.equals(manager.getName()) && managerPassword.equals(manager.getPassword())) {
                System.out.println("Manager verified");
                return true;
            } else {
                System.out.println("Wrong password or name\nPlease try again or type (exit) to cancel");
            }
        }
    }

    void changeLibrarianPassword(Librarian librarian, Scanner scanner) {
        System.out.println("\nEnter your new password: ");
        String newPass = scanner.nextLine();
        librarian.setPassword(newPass);
        System.out.println("\nYour new password has been changed\nYour new password is " + newPass);
    }

    void chooseAndChangeBookInfo(Book book, Scanner scanner) {
        if (book == null) {
            return;
        }
        System.out.print("Enter new book title: ");
        String newTitle = scanner.nextLine();
        book.setNewTitle(newTitle);
        System.out.print("Enter new book author: ");
        String newAuthor = scanner.nextLine();
        book.setNewAuthor(newAuthor);
        System.out.print("Enter new book published year: ");
        int newPublishedYear = scanner.nextInt();
        book.setNewPublishedYear(newPublishedYear);
        System.out.print("Enter new book pages: ");
        int newPages = scanner.nextInt();
        book.setNewPages(newPages);
        System.out.print("The book information has been changed successfully");

    }


    void creatingEmptyFiles() {
        try {
            File file1 = new File("Books.txt");
            if (!file1.exists()) file1.createNewFile();

            File file2 = new File("Students.txt");
            if (!file2.exists()) file2.createNewFile();

            File file3 = new File("Librarians.txt");
            if (!file3.exists()) file3.createNewFile();

            File file4 = new File("LoanRequests.txt");
            if (!file4.exists()) file4.createNewFile();

            File file5 = new File("Loans.txt");
            if (!file5.exists()) file5.createNewFile();

        } catch (IOException e) {
            System.out.println("Error while creating files: " + e.getMessage());
        }
    }
}
