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
