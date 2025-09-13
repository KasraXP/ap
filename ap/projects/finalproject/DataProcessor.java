package projects.finalproject;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class DataProcessor {

    Student addStudent() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n=== Student Registration ===");

        try {
            System.out.print("Enter username: ");
            String userName = scanner.nextLine().trim();

            if (userName.isEmpty()) {
                throw new IllegalArgumentException("Username cannot be empty!");
            }

            System.out.print("Enter password: ");
            String password = scanner.nextLine().trim();

            if (password.isEmpty()) {
                throw new IllegalArgumentException("Password cannot be empty!");
            }

            System.out.print("Enter major: ");
            String major = scanner.nextLine().trim();

            if (major.isEmpty()) {
                throw new IllegalArgumentException("Major cannot be empty!");
            }

            System.out.println("Student '" + userName + "' registered successfully!");
            return new Student(userName, password, major, LocalDate.now());

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    Librarian addLibrarian() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n=== Add New Librarian ===");

        try {
            System.out.print("Enter librarian full name: ");
            String fullName = scanner.nextLine().trim();

            if (fullName.isEmpty()) {
                throw new IllegalArgumentException("Name cannot be empty!");
            }

            System.out.print("Enter employee ID: ");
            String employeeId = scanner.nextLine().trim();

            if (employeeId.isEmpty()) {
                throw new IllegalArgumentException("Employee ID cannot be empty!");
            }

            System.out.println(" Librarian '" + fullName + "' added successfully!");
            return new Librarian(fullName, employeeId);

        } catch (Exception e) {
            System.out.println(" Error: " + e.getMessage());
            return null;
        }
    }

    Book addBook(Librarian librarian) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n=== Add New Book ===");

        try {
            System.out.print("Enter book title: ");
            String title = scanner.nextLine().trim();

            if (title.isEmpty()) {
                throw new IllegalArgumentException("Title cannot be empty!");
            }

            System.out.print("Enter book author: ");
            String author = scanner.nextLine().trim();

            if (author.isEmpty()) {
                throw new IllegalArgumentException("Author cannot be empty!");
            }

            System.out.print("Enter book pages: ");
            int pages = Integer.parseInt(scanner.nextLine().trim());

            if (pages <= 0) {
                throw new IllegalArgumentException("Pages must be positive number!");
            }

            System.out.print("Enter published year: ");
            int publishedYear = Integer.parseInt(scanner.nextLine().trim());

            int currentYear = LocalDate.now().getYear();
            if (publishedYear < 1000 || publishedYear > currentYear) {
                throw new IllegalArgumentException("Please enter a valid year!");
            }

            System.out.println("Book '" + title + "' added successfully!");
            return new Book(title, author, pages, publishedYear, librarian.getUserName());

        } catch (NumberFormatException e) {
            System.out.println("Error: Please enter valid numbers for pages and year!");
            return null;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
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

            if (managerName.isEmpty() || managerPassword.isEmpty()) {
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

        int newPublishedYear = 0;
        while (true) {
            System.out.print("Enter new book published year: ");
            String yearInput = scanner.nextLine().trim();

            try {
                newPublishedYear = Integer.parseInt(yearInput);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number!");
            }
        }
        book.setNewPublishedYear(newPublishedYear);

        int newPages = 0;
        while (true) {
            System.out.print("Enter new book pages: ");
            String pagesInput = scanner.nextLine().trim();

            try {
                newPages = Integer.parseInt(pagesInput);
                if (newPages <= 0) {
                    System.out.println("Pages must be positive!");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number!");
            }
        }
        book.setNewPages(newPages);

        System.out.println("Book information updated successfully!");
    }

    void requestBookToLoan(Student student, ArrayList<Book> books, ArrayList<LoanRequest> loanRequests, Scanner scanner) {

        if (books.isEmpty()) {
            System.out.println("\nThere is no book in the database");
            return;
        }

        if (!student.isActive()) {
            System.out.println("Sorry, your account is deactivated. You cannot borrow books.");
            System.out.println("Please contact the library administrator.");
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
            System.out.println((i + 1) + ". Student: " + req.getStudent().getUserName() + " - Book: " + req.getBook().getTitle() + " - Duration: " + req.getLoanDurationDays() + " days");
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

    void deactivateStudent(ArrayList<Student> students, Scanner scanner) {
        if (students == null || students.isEmpty()) {
            System.out.println("There are no students in the library.");
            return;
        }

        System.out.println("Enter the username of the student to deactivate:");

        while (true) {
            String username = scanner.nextLine().trim();

            if (username.equalsIgnoreCase("exit")) {
                System.out.println("Exiting...");
                return;
            }

            if (username.isEmpty()) {
                System.out.println("Username cannot be empty! Please try again:");
                continue;
            }

            boolean studentFound = false;

            for (Student student : students) {
                if (student != null && username.equalsIgnoreCase(student.getUserName())) {
                    studentFound = true;

                    if (!student.isActive()) {
                        System.out.println("Student " + username + " is already deactivated.");
                        break;
                    }

                    student.setActive(false);
                    System.out.println("Student " + username + " has been deactivated successfully.");
                    System.out.println("This student can no longer borrow books.");
                    break;
                }
            }

            if (!studentFound) {
                System.out.println("Student with username '" + username + "' not found.");
            }

            System.out.println("Enter another username to deactivate or type 'exit' to cancel:");
        }
    }

    void activateStudent(ArrayList<Student> students, Scanner scanner) {
        if (students == null || students.isEmpty()) {
            System.out.println("There are no students in the library.");
            return;
        }

        System.out.println("Enter the username of the student to activate:");

        while (true) {
            String username = scanner.nextLine().trim();

            if (username.equalsIgnoreCase("exit")) {
                System.out.println("Exiting...");
                return;
            }

            if (username.isEmpty()) {
                System.out.println("Username cannot be empty! Please try again:");
                continue;
            }

            boolean studentFound = false;

            for (Student student : students) {
                if (student != null && username.equalsIgnoreCase(student.getUserName())) {
                    studentFound = true;

                    if (student.isActive()) {
                        System.out.println("Student " + username + " is already active.");
                        break;
                    }

                    student.setActive(true);
                    System.out.println("Student " + username + " has been activated successfully.");
                    System.out.println("This student can now borrow books again.");
                    break;
                }
            }

            if (!studentFound) {
                System.out.println("Student with username '" + username + "' not found.");
            }

            System.out.println("Enter another username to activate or type 'exit' to cancel:");
        }
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

