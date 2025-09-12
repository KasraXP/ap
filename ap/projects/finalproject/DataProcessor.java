package projects.finalproject;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;

public class DataProcessor {
    private final Scanner scanner;


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
        System.out.println("\nEnter book title: ");
        String title = scanner.nextLine();
        System.out.println("Enter book author: ");
        String author = scanner.nextLine();
        System.out.println("Enter book published year: ");
        int publishedYear = scanner.nextInt();
        System.out.println("enter book pages: ");
        int pages = scanner.nextInt();
        return new Book(title, author, publishedYear, pages);
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
        System.out.println("\nEnter new book title: ");
        String newTitle = scanner.nextLine();
        book.setNewTitle(newTitle);
        System.out.println("\nEnter new book author: ");
        String newAuthor = scanner.nextLine();
        book.setNewAuthor(newAuthor);
        System.out.println("\nEnter new book published year: ");
        int newPublishedYear = scanner.nextInt();
        book.setNewPublishedYear(newPublishedYear);
        System.out.println("\nEnter new book pages: ");
        int newPages = scanner.nextInt();
        book.setNewPages(newPages);
        System.out.println("The book information has been changed successfully");

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
