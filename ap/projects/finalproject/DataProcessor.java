package projects.finalproject;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class DataProcessor {
    private final Scanner scanner;


    DataProcessor(Scanner scanner) {
        this.scanner = scanner;

    }

    Student addStudent() {
        System.out.println("\nEnter your username: ");
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

    Book addBook(Librarian librarian) {
        System.out.print("Enter book title: ");
        String title = scanner.nextLine();
        System.out.print("Enter book author: ");
        String author = scanner.nextLine();
        System.out.print("Enter book pages: ");
        int pages = scanner.nextInt();
        System.out.println("enter book published year: ");
        int publishedYear = scanner.nextInt();
        return new Book(title, author, pages, publishedYear,librarian.getUserName());
    }

    Student studentVerification(ArrayList<Student> students, Scanner scanner) {
        if (students == null || students.isEmpty()) {
            System.out.println("There are no students in the database.");
            return null;
        }

        System.out.println("\n====Student verification====");

        while (true) {
            System.out.println("Enter your username (or type 'exit' to cancel):");
            String studentUserName = scanner.nextLine();

            if (studentUserName == null || studentUserName.trim().isEmpty()) {
                System.out.println("Username cannot be empty! Please try again.");
                continue;
            }

            if (studentUserName.trim().equalsIgnoreCase("exit")) {
                System.out.println("Exiting...");
                return null;
            }

            System.out.println("Enter your password (or type 'exit' to cancel):");
            String studentPassword = scanner.nextLine();

            if (studentPassword == null || studentPassword.trim().isEmpty()) {
                System.out.println("Password cannot be empty! Please try again.");
                continue;
            }

            if (studentPassword.trim().equalsIgnoreCase("exit")) {
                System.out.println("Exiting...");
                return null;
            }

            for (Student student : students) {
                if (student == null) {
                    continue;
                }

                String username = student.getUserName();
                String password = student.getPassword();

                if (username != null && password != null &&
                        username.equals(studentUserName.trim()) &&
                        password.equals(studentPassword.trim())) {
                    System.out.println("Student verified successfully!");
                    return student;
                }
            }

            System.out.println("Wrong username or password!\nPlease try again or type 'exit' to cancel.");
        }
    }

    Librarian librarianVerification(ArrayList<Librarian> librarians, Scanner scanner) {


        if (librarians.isEmpty()) {
            System.out.println("There is no librarian");
            return null;
        }

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
                if (librarian.getPassword().equals(librarianPassword) && librarian.getUserName().equals(librarianFullName)) {
                    System.out.println("Librarian verified");
                    return librarian;
                }
            }

            System.out.println("Wrong password or name\nplease try again or type (exit) to cancel");

        }
    }

    boolean managerVerification(Scanner scanner, Manager manager) {

        System.out.println("====Manager verification====");
        while (true) {
            System.out.print("Enter your username: ");
            String managerName = scanner.nextLine();

            if (managerName != null && managerName.equalsIgnoreCase("exit")) {
                System.out.println("Exiting...");
                return false;
            }

            System.out.print("Enter your password: ");
            String managerPassword = scanner.nextLine();

            if (managerPassword != null && managerPassword.equalsIgnoreCase("exit")) {
                System.out.println("Exiting...");
                return false;
            }

            if(managerName.isEmpty() || managerPassword.isEmpty()) {
                System.out.println("Username or password can not be empty");
                continue;
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

    void changeBookInfo(Book book, Scanner scanner) {
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

    void requestBookToLoan(Student student, ArrayList<Book> books, ArrayList<LoanRequest> loanRequests, Scanner scanner) {

        if (books.isEmpty()) {
            System.out.println("\nThere is no book in the database");
            return;
        }

        ArrayList<Book> availableBooks = new ArrayList<>();
        for (Book book : books) {
            if (!book.getIsLoaned()) {
                availableBooks.add(book);
            }
        }

        if (availableBooks.isEmpty()) {
            System.out.println("\nThere is not any available book to loan at the moment");
            return;
        }

        System.out.println("Available books:");
        for (int i = 0; i < availableBooks.size(); i++) {
            System.out.println((i + 1) + ". " + availableBooks.get(i).getTitle());
        }

        System.out.println("Enter the number of the book you want to loan (or 0 to cancel):");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 0) {
            return;
        }

        if (choice < 1 || choice > availableBooks.size()) {
            System.out.println("Invalid choice!");
            return;
        }

        Book selectedBook = availableBooks.get(choice - 1);

        System.out.println("Enter number of days for loan:");
        int loanDays = scanner.nextInt();
        scanner.nextLine();

        LoanRequest request = new LoanRequest(selectedBook, student, loanDays);
        loanRequests.add(request);

        System.out.println("Your loan request for " + loanDays + " days has been sent.");
    }

    public void handleLoanRequests(ArrayList<LoanRequest> loanRequests, Librarian librarian, ArrayList<Loan> loans, Scanner scanner) {

        ArrayList<LoanRequest> pendingRequests = new ArrayList<>();
        for (LoanRequest req : loanRequests) {
            if (req.getStatus().equals("PENDING")) {
                pendingRequests.add(req);
            }
        }

        if (pendingRequests.isEmpty()) {
            System.out.println("There are no pending loan requests.");
            return;
        }

        System.out.println("Pending loan requests:");
        for (int i = 0; i < pendingRequests.size(); i++) {
            LoanRequest req = pendingRequests.get(i);
            System.out.println((i + 1) + ". Student: " + req.getStudent().getUserName() +
                    " - Book: " + req.getBook().getTitle() +
                    " - Duration: " + req.getLoanDurationDays() + " days");
        }

        System.out.println("Enter the number of the request you want to handle (or 0 to cancel):");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 0) {
            return;
        }

        if (choice < 1 || choice > pendingRequests.size()) {
            System.out.println("Invalid choice!");
            return;
        }

        LoanRequest selectedRequest = pendingRequests.get(choice - 1);

        System.out.println("Do you want to approve this request? (yes/no)");
        String response = scanner.nextLine();

        if (response.equalsIgnoreCase("yes")) {
            selectedRequest.setStatus("APPROVED");

            LocalDate now = LocalDate.now();
            LocalDate dueDate = now.plusDays(selectedRequest.getLoanDurationDays());

            Loan loan = new Loan(selectedRequest.getBook(), selectedRequest.getStudent(),
                    now, dueDate, null);
            loans.add(loan);
            selectedRequest.getBook().setIsLoaned(true);
            selectedRequest.getBook().setLoanCount();
            librarian.setGivenCount();


            System.out.println("Loan request approved successfully.");

        } else if (response.equalsIgnoreCase("no")) {
            selectedRequest.setStatus("REJECTED");
            System.out.println("Loan request rejected.");
        } else {
            System.out.println("Invalid response. Request remains pending.");
        }
    }

    public void returnBook(ArrayList<Loan> loans, ArrayList<Librarian> librarians, Student student, Scanner scanner) {

        String pass = student.getPassword();

        ArrayList<Loan> activeLoans = new ArrayList<>();

        for (Loan loan : loans) {
            if (loan.getStudent().getPassword().equals(pass) && loan.getReturnDate() == null) {
                activeLoans.add(loan);
            }
        }

        if (activeLoans.isEmpty()) {
            System.out.println("You have no books currently on loan.");
            return;
        }

        System.out.println("Books you currently have on loan:");
        for (int i = 0; i < activeLoans.size(); i++) {
            Loan loan = activeLoans.get(i);
            System.out.println((i + 1) + ". " + loan.getBook().getTitle() + " (Due: " + loan.getDueDate() + ")");
        }

        System.out.println("Enter the number of the book you want to return:");
        int choice = Integer.parseInt(scanner.nextLine());

        if (choice < 1 || choice > activeLoans.size()) {
            System.out.println("Invalid selection.");
            return;
        }

        Loan selectedLoan = activeLoans.get(choice - 1);
        selectedLoan.getBook().setIsLoaned(false);
        selectedLoan.setReturnDate(LocalDate.now());

        Random random = new Random();
        int rnd = random.nextInt(librarians.size());
        Librarian lib = librarians.get(rnd);
        lib.setReceivedCount();


        System.out.println("Book \"" + selectedLoan.getBook().getTitle() + "\" has been successfully returned to " + lib.getUserName());
    }

    void clearFile(String fileName) {
        try (PrintWriter writer = new PrintWriter(fileName)) {

        } catch (IOException e) {
            System.out.println("Error while clearing file" + e.getMessage());
        }
    }

    void creatingEmptyFiles() {
        try {
            File file1 = new File("Books.txt.file");
            if (!file1.exists()) file1.createNewFile();

            File file2 = new File("Students.txt.file");
            if (!file2.exists()) file2.createNewFile();

            File file3 = new File("Librarians.txt.file");
            if (!file3.exists()) file3.createNewFile();

            File file4 = new File("LoanRequests.txt.file");
            if (!file4.exists()) file4.createNewFile();

            File file5 = new File("Loans.txt.file");
            if (!file5.exists()) file5.createNewFile();

        } catch (IOException e) {
            System.out.println("Error while creating files: " + e.getMessage());
        }
    }
}
