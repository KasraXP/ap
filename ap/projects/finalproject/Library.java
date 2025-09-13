package projects.finalproject;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

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
        if (book.getIsLoaned()) {
            System.out.println("This book is loaned at the moment ");
        } else
            System.out.println("This book is not loaned at the moment ");
        System.out.println("___________________________");
    }

    void printLoanBooksInfo( List<Loan> loans) {
        if (loans == null) {
            System.out.println("No books found");
            return;
        }

        for (Loan loan : loans) {
            if (loan.getReturnDate() == null ) {
                printBookInfo(loan.getBook());
            }
        }
    }

    Book searchBookToChange(ArrayList<Book> books, Scanner scanner) {

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

    void searchBooksByPublishedYear(ArrayList<Book> books, Scanner scanner) {
        if (books.isEmpty()) {
            System.out.println("\nThere is no book in the database");
            return;
        }

        System.out.println("\nEnter a book published year to search (or 0 to exit): ");

        while (true) {
            try {
                int year = scanner.nextInt();
                scanner.nextLine();

                if (year == 0) {
                    System.out.println("Exiting search...");
                    break;
                }

                List<Book> foundBooks = books.stream()
                        .filter(book -> book.getPublishedYear() == year)
                        .collect(Collectors.toList());

                if (foundBooks.isEmpty()) {
                    System.out.println("\nNo books found published in " + year);
                } else {
                    System.out.println("\nFound " + foundBooks.size() + " book(s) published in " + year + ":");
                    foundBooks.forEach(this::printBookInfo);
                }

                System.out.println("\nEnter another year to search (or 0 to exit): ");

            } catch (java.util.InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid year number.");
                scanner.nextLine();
            }
        }
    }

    void searchBooksByAuthor(ArrayList<Book> books, Scanner scanner) {
        if (books.isEmpty()) {
            System.out.println("\nThere is no book in the database");
            return;
        }

        System.out.println("\nEnter an author name to search (or 'exit' to exit): ");

        while (true) {
            try {
                String author = scanner.nextLine();


                if (author.equalsIgnoreCase("exit")) {
                    System.out.println("Exiting search...");
                    break;
                }

                List<Book> foundBooks = books.stream()
                        .filter(book -> book.getAuthor().equalsIgnoreCase(author))
                        .collect(Collectors.toList());

                if (foundBooks.isEmpty()) {
                    System.out.println("\nNo books found by the writer " + author);
                } else {
                    System.out.println("\nFound " + foundBooks.size() + " book(s) by " + author + ":");
                    foundBooks.forEach(this::printBookInfo);
                }

                System.out.println("\nEnter another author to search (or 'exit' to exit): ");

            } catch (java.util.InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid author name.");
                scanner.nextLine();
            }
        }
    }

    void searchBooksByTitle(ArrayList<Book> books, Scanner scanner) {
        if (books.isEmpty()) {
            System.out.println("\nThere is no book in the database");
            return;
        }

        System.out.println("\nEnter a book title to search (or 'exit' to exit): ");

        while (true) {
            try {
                String title = scanner.nextLine();


                if (title.equalsIgnoreCase("exit")) {
                    System.out.println("Exiting search...");
                    break;
                }

                List<Book> foundBooks = books.stream()
                        .filter(book -> book.getTitle().equalsIgnoreCase(title))
                        .collect(Collectors.toList());

                if (foundBooks.isEmpty()) {
                    System.out.println("\nNo book found with the title " + title);
                } else {
                    System.out.println("\nFound " + foundBooks.size() + " book(s) with the title " + title + ":");
                    foundBooks.forEach(this::printBookInfo);
                }

                System.out.println("\nEnter another book title to search (or 'exit' to exit): ");

            } catch (java.util.InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid book title.");
                scanner.nextLine();
            }
        }
    }

    void printAllBooks(ArrayList<Book> books) {

        if (books.isEmpty()) {
            System.out.println("\nThere is no books in Central Library");
            return;
        }

        for (Book book : books) {
            System.out.println("\nBook title: " + book.getTitle());
            System.out.println("Book author: " + book.getAuthor());
            System.out.println("Book published year: " + book.getPublishedYear());
            System.out.println("Book pages: " + book.getPages());
            System.out.println("___________________________");
        }
    }

    void printLibrarianInfo(ArrayList<Librarian> librarians, ArrayList<Book> books, Scanner scanner) {

        if (librarians.isEmpty() || books.isEmpty()) {
            System.out.println("There is no librarian or books in library");
            return;
        }

        System.out.println("Enter a username to see librarian information about.");

        while (true) {

            String username = scanner.nextLine();

            if (username.equalsIgnoreCase("exit")) {
                System.out.println("Exiting search...");
                return;
            }

            for (Librarian librarian : librarians) {
                if(librarian.getUserName().equalsIgnoreCase(username)) {
                    System.out.println("Librarian name: " + librarian.getUserName());
                    System.out.println("Librarian password: " + librarian.getPassword());

                    for (Book book : books) {
                        if(book.getTheLibrarian().equalsIgnoreCase(librarian.getUserName())) {
                            System.out.println("Added Book : " + book.getTitle());
                        }

                    }

                    System.out.println("Given books: " + librarian.getGivenCount());
                    System.out.println("Received books: " + librarian.getReceivedCount());
                }else
                    System.out.println("Invalid username. Please enter a valid username.");
            }

            System.out.println("Enter another username to see librarian information about (or 'exit' to exit): ");
        }



    }


}
