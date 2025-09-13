package projects.finalproject;

import java.util.Scanner;

public class Menu {
    private final Scanner scan;

    Menu() {
        scan = new Scanner(System.in);
    }

    void showMainMenu() {
        System.out.println("\n====Main Menu====");
        System.out.println("1. Student menu ");
        System.out.println("2. Librarian menu");
        System.out.println("3. Manager menu");
        System.out.println("4. Guest menu");
        System.out.println("5. Exit");
    }

    void guestMenu() {
        System.out.println("\n====Guest Menu====");
        System.out.println("1. Show some details about Central Library");
        System.out.println("2. Search a book by title");
        System.out.println("3. Show all books");
        System.out.println("4. Exit");
    }

    void studentFirstMenu() {
        System.out.println("\n====Student Menu====");
        System.out.println("1. Register to Library ");
        System.out.println("2. Login to Library ");
        System.out.println("3. Exit");
    }

    void studentSecondMenu() {
        System.out.println("\n====Student Second Menu====");
        System.out.println("1. Search a book by title");
        System.out.println("2. Search a book by author");
        System.out.println("3. Search a book by published year");
        System.out.println("4. Loan a book ");
        System.out.println("5. Return a book ");
        System.out.println("6. Show all books on loan ");
        System.out.println("7. Show student info ");
        System.out.println("8. Exit");
    }

    void librarianMenu() {
        System.out.println("\n====Librarian Menu====");
        System.out.println("1. Change password ");
        System.out.println("2. Add new book ");
        System.out.println("3. Show loan requests ");
        System.out.println("4. Change book info ");
        System.out.println("5. Show loan information about student ");
        System.out.println("6. Make a student active");
        System.out.println("7. Make a student inactive");
        System.out.println("8. Exit");
    }

    void ManagerMenu() {
        System.out.println("====Manager Menu====");
        System.out.println("1. Add a new Librarian ");
        System.out.println("2. Show info of Librarian ");
        System.out.println("3. Exit");
    }

    int getOption() {
        System.out.print("\nEnter your Option: ");
        return scan.nextInt();
    }
}
