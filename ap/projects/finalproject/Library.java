package projects.finalproject;

import java.util.ArrayList;
import java.util.Scanner;

public class Library {
    private final String libraryName;

    Library() {
        this.libraryName = "Central";
    }

    String getLibraryName() {
        return libraryName;
    }

    void printStudentInfo(Student student) {
        System.out.println("\nStudent username: " + student.getUserName());
        System.out.println("Student password: " + student.getPassword());
        System.out.println("Student major: " + student.getMajor());
        System.out.println("Student membership date: " + student.getMembershipDate());
    }

    void printBookInfo(Book book) {
        if (book == null) {
            return;
        }
        System.out.println("\nBook title: " + book.getTitle());
        System.out.println("Book author: " + book.getAuthor());
        System.out.println("Book pages: " + book.getPages());
        System.out.println("Book published year: " + book.getPublishedYear());
        System.out.println("___________________________");
    }

    Student studentVerification(ArrayList<Student> students, Scanner scanner) {
        System.out.println("====Student verification====");
        boolean isThereOurStudent = false;

        while (true) {

            String studentUserName = null;
            String studentPassword;


            System.out.println("Enter your username: ");
            studentUserName = scanner.nextLine();

            if (studentUserName != null && studentUserName.equalsIgnoreCase("exit")) {
                System.out.println("Exiting...");
                return null;
            }

            System.out.println("Enter your password: ");
            studentPassword = scanner.nextLine();


            for (Student student : students) {
                if (student.getPassword().equals(studentPassword) && student.getUserName().equals(studentUserName)) {
                    System.out.println("Student verified");
                    isThereOurStudent = true;
                    return student;
                }
            }

            if (!isThereOurStudent) {
                System.out.println("Wrong password or name\nplease try again or type (exit) to cancel");
            }

        }
    }

    Librarian librarianVerification(ArrayList<Librarian> librarians, Scanner scanner) {

        System.out.println("====Librarian verification====");
        boolean isThereOurLibrarian = false;

        while (true) {

            String librarianFullName = null;
            String librarianPassword;


            System.out.println("Enter your username: ");
            librarianFullName = scanner.nextLine();

            if (librarianFullName != null && librarianFullName.equalsIgnoreCase("exit")) {
                System.out.println("Exiting...");
                return null;
            }

            System.out.println("Enter your password: ");
            librarianPassword = scanner.nextLine();


            for (Librarian librarian : librarians) {
                if (librarian.getPassword().equals(librarianPassword) && librarian.getFullName().equals(librarianFullName)) {
                    System.out.println("Librarian verified");
                    isThereOurLibrarian = true;
                    return librarian;
                }
            }

            if (!isThereOurLibrarian) {
                System.out.println("Wrong password or name\nplease try again or type (exit) to cancel");
            }
        }
    }

    Book searchBookByTitle(ArrayList<Book> books, Scanner scanner) {

        if (books.isEmpty()) {
            System.out.println("\nThere is no book in the database");
            return null;
        }

        System.out.println("\nEnter the book title to search: ");

        while (true) {
            String bookTitle = scanner.nextLine();

            if (bookTitle.equalsIgnoreCase("exit")) {
                return null;
            }

            for (Book book : books) {
                if (book.getTitle().equalsIgnoreCase(bookTitle)) {
                    return book;
                }
            }

            System.out.println("\nThere is not a book with title " + bookTitle + "\n Please try again");
        }
    }

}
