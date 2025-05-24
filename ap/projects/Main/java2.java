package projects.Main;

import java.io.*;
import java.util.Random;
import java.util.Scanner;
import java.time.LocalDate;
import java.util.ArrayList;

class Main {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        DataProcessor processor = new DataProcessor(input);
        processor.creatingEmptyFiles();
        LoadFromFile loadFromFile = new LoadFromFile();
        SaveToFile saveToFile = new SaveToFile();
        ArrayList<Book> books = loadFromFile.loadBooks("Books.txt");
        ArrayList<Student> students = loadFromFile.loadStudents("Students.txt");
        ArrayList<Librarian> librarians = loadFromFile.loadLibrarians();
        ArrayList<LoanRequest> loansRequests = loadFromFile.loadLoanRequests("LoanRequests.txt", books, students, librarians);
        ArrayList<Loan> loans = loadFromFile.loadLoans("Loans.txt", books, students, librarians);
        Library library = new Library("Central Library", books, students, librarians, loans);
        Manager manager = new Manager("Ali", "Akbary", "PHP");


        Menu menu = new Menu();

        System.out.println("Welcome to " + library.getLibraryName() + " library!");
        while (true) {
            menu.showMainMenu();

            switch (menu.getOption()) {
                case 1:
                    boolean studentRunning = true;
                    while (studentRunning) {
                        menu.studentFirstMenu();
                        switch (menu.getOption()) {
                            case 1:
                                students.add(processor.addStudent());
                                saveToFile.saveStudents(students);
                                break;

                            case 2:
                                Student student = library.searchStudentById(students, input);
                                if (student == null) {
                                    studentRunning = false;
                                    break;
                                } else
                                    library.printStudentInfo(student);


                                boolean loggedIn = true;
                                while (loggedIn) {
                                    menu.studentSecondMenu();
                                    switch (menu.getOption()) {
                                        case 1:
                                            Book book = library.searchBookByTitle(books, input);
                                            if (book == null) {
                                                loggedIn = false;
                                            }
                                            else {
                                                library.printBookInfo(book);
                                                break;
                                            }
                                        case 2:
                                            processor.chooseBookLoanAndRequest(student, books, librarians, loansRequests, input);
                                            saveToFile.saveLoanRequests(loansRequests);
                                            break;

                                        case 3:
                                            processor.returnBookByStudent(loans,input);
                                            processor.clearFile("Loans.txt");
                                            saveToFile.saveLoans(loans);
                                            processor.clearFile("Books.txt");
                                            saveToFile.saveBooks(books);
                                        case 4:
                                            library.printBooksOnLoan(loans);
                                            break;
                                        case 5:
                                            library.printStudentInfo(library.searchStudentById(students, input));
                                            break;
                                        case 6:
                                            System.out.println("Exiting Student Menu...");
                                            loggedIn = false;
                                            studentRunning = false;
                                            break;
                                        default:
                                            System.out.println("\nInvalid option\nPlease try again");
                                    }
                                }
                                break;

                            default:
                                System.out.println("Invalid option\nPlease try again");
                        }
                    }
                    break;

                case 2:
                    boolean librarianRunning = true;


                    Librarian librarian = library.searchLibrarianByEmployeeId(librarians, input);
                    if (librarian == null) {
                        break;
                    }
                    library.printLibrarianInfo(librarian);
                    while (librarianRunning) {
                        menu.librarianMenu();
                        switch (menu.getOption()) {
                            case 1:
                                books.add(processor.addBook());
                                saveToFile.saveBooks(books);
                                break;

                            case 2:
                                processor.changeLibrarianId(library.searchLibrarianByEmployeeId(librarians, input), input);
                                saveToFile.saveLibrarian(librarians);
                                break;

                            case 3:
                                processor.handleRequestsForLibrarian(loansRequests, loans, input);
                                saveToFile.saveLoans(loans);
                                processor.clearFile("Books.txt");
                                saveToFile.saveBooks(books);
                                processor.clearFile("LoanRequests.txt");
                                saveToFile.saveLoanRequests(loansRequests);
                                break;

                            case 4:
                                librarianRunning = false;
                                System.out.println("Exiting librarian menu...");
                                break;

                            default:
                                System.out.println("Invalid option. Please try again.");
                        }
                    }
                    break;


                case 3:
                    boolean managerRunning = true;
                    while (managerRunning) {
                        menu.ManagerMenu();
                        switch (menu.getOption()) {
                            case 1:
                                librarians.add(processor.addLibrarian());
                                saveToFile.saveLibrarian(librarians);
                                break;

                            case 2:
                                library.printLateReturns(loans);
                                break;

                            case 3:
                                library.printLibrarianStats(loans,librarians);
                                break;

                            case 4:
                                library.printTop10BooksLastYear(books,loans);
                                break;

                            case 5:
                                managerRunning = false;
                                System.out.println("Exiting manager menu...");
                                break;

                            default:
                                System.out.println("Invalid option\nPlease try again");
                        }
                    }
                    break;

                case 4:
                    System.out.println("Exiting program...");
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid option\nPlease try again");
            }
        }
    }
}

class SaveToFile {

    void saveBooks(ArrayList<Book> books) {
        try {
            PrintWriter writer = new PrintWriter("Books.txt");
            for (Book book : books) {
                writer.println(book.toStringBook());
            }
            writer.close();

        } catch (IOException e) {
            System.out.println("Error while saving books file" + e.getMessage());

        }
    }


    void saveStudents(ArrayList<Student> students) {
        try {
            PrintWriter writer = new PrintWriter("Students.txt");
            for (Student student : students) {
                writer.println(student.toStringStudent());
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error while saving students file" + e.getMessage());
        }

    }

    void saveLibrarian(ArrayList<Librarian> librarians) {
        try {
            PrintWriter writer = new PrintWriter("Librarians.txt");
            for (Librarian librarian : librarians) {
                writer.println(librarian.toStringLibrarian());
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error while saving librarians file" + e.getMessage());
        }
    }

    void saveLoanRequests(ArrayList<LoanRequest> loansRequests) {
        try {
            PrintWriter writer = new PrintWriter("LoanRequests.txt");
            for (LoanRequest loanRequest : loansRequests) {
                writer.println(loanRequest.toStringLoanRequests());
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error while saving loanRequests file" + e.getMessage());
        }
    }


    void saveLoans(ArrayList<Loan> loans) {
        try {
            PrintWriter writer = new PrintWriter("Loans.txt");
            for (Loan loan : loans) {
                writer.println(loan.toStringLoans());
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error while saving loans file" + e.getMessage());
        }
    }
}

class LoadFromFile {

    public Book fromStringBook(String line) {
        String[] parts = line.split(",");
        String title = parts[0];
        String author = parts[1];
        int pages = Integer.parseInt(parts[2]);
        int publishedDate = Integer.parseInt(parts[3]);
        boolean isLoaned = Boolean.parseBoolean(parts[4]);
        int loanCount = Integer.parseInt(parts[5]);
        return new Book(title, author, pages, publishedDate, isLoaned, loanCount);
    }

    public ArrayList<Book> loadBooks(String filename) {
        ArrayList<Book> books = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Book book = fromStringBook(line);
                books.add(book);
            }
        } catch (IOException e) {
            System.out.println("Error while loading file" + e.getMessage());
        }

        return books;
    }


    public Student fromStringStudent(String line) {
        if (line.isEmpty()) {
            return null;
        } else {
            String[] parts = line.split(",");
            String firstName = parts[0];
            String lastName = parts[1];
            String studentId = parts[2];
            String major = parts[3];
            LocalDate membershipDate = LocalDate.parse(parts[4]);
            return new Student(firstName, lastName, studentId, major, membershipDate);
        }

    }

    public ArrayList<Student> loadStudents(String fileName) {
        ArrayList<Student> students = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Student student = fromStringStudent(line);
                students.add(student);
            }
        } catch (IOException e) {
            System.out.println("Error while loading file" + e.getMessage());
        }

        return students;
    }


    public Librarian fromStringLibrarian(String line) {
        if (line.isEmpty()) {
            return null;
        } else {
            String[] parts = line.split(",");
            String firstName = parts[0];
            String lastName = parts[1];
            String studentId = parts[2];

            return new Librarian(firstName, lastName, studentId);
        }
    }

    ArrayList<Librarian> loadLibrarians() {
        ArrayList<Librarian> librarians = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("Librarians.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Librarian librarian = fromStringLibrarian(line);
                librarians.add(librarian);
            }
        } catch (IOException e) {
            System.out.println("Error while loading file" + e.getMessage());
        }
        return librarians;
    }

    public LoanRequest fromStringLoanRequest(String line, ArrayList<Book> books, ArrayList<Student> students, ArrayList<Librarian> librarians) {
        if (line.isEmpty()) {
            return null;
        } else {
            String[] parts = line.split(",");
            String bookTitle = parts[0];
            String studentId = parts[1];
            String librarianId = parts[2];


            Book book = null;
            Student student = null;
            Librarian librarian = null;

            for (Book b : books) {
                if (b.getTitle().equals(bookTitle)) {
                    book = b;
                    break;
                }
            }

            for (Student s : students) {
                if (s.getStudentId().equals(studentId)) {
                    student = s;
                    break;
                }
            }

            for (Librarian l : librarians) {
                if (l.getEmployeeId().equals(librarianId)) {
                    librarian = l;
                    break;
                }
            }

            return new LoanRequest(book, student, librarian);
        }

    }

    public ArrayList<LoanRequest> loadLoanRequests(String fileName, ArrayList<Book> books, ArrayList<Student> students, ArrayList<Librarian> librarians) {
        ArrayList<LoanRequest> loanRequests = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                LoanRequest loanRequest = fromStringLoanRequest(line, books, students, librarians);
                if (loanRequest != null) {
                    loanRequests.add(loanRequest);
                }
            }
        } catch (IOException e) {
            System.out.println("Error while loading file" + e.getMessage());
        }

        return loanRequests;

    }


    public Loan fromStringLoans(
            String line,
            ArrayList<Book> books,
            ArrayList<Student> students,
            ArrayList<Librarian> librarians) {

        String[] parts = line.split(",");

        if (parts.length < 5) {
            System.out.println("Invalid loan entry: " + line);
            return null;
        }

        String bookTitle = parts[0];
        String studentId = parts[1];
        String librarianId = parts[2];
        LocalDate loanDate = LocalDate.parse(parts[3]);
        LocalDate dueDate = LocalDate.parse(parts[4]);

        LocalDate returnDate = null;
        if (parts.length >= 6 && !parts[5].isEmpty()) {
            try {
                returnDate = LocalDate.parse(parts[5]);
            } catch (Exception e) {
                System.out.println("Invalid return date format in line: " + line);
                return null;
            }
        }


        Book book = null;
        Student student = null;
        Librarian librarian = null;

        for (Book b : books) {
            if (b.getTitle().equals(bookTitle)) {
                book = b;
                break;
            }
        }

        for (Student s : students) {
            if (s.getStudentId().equals(studentId)) {
                student = s;
                break;
            }
        }

        for (Librarian l : librarians) {
            if (l.getEmployeeId().equals(librarianId)) {
                librarian = l;
                break;
            }
        }


        if (book != null && student != null && librarian != null) {
            return new Loan(book, student, librarian, loanDate, dueDate, returnDate);
        } else {
            System.out.println("Missing data for loan: " + line);
            return null;
        }
    }


    public ArrayList<Loan> loadLoans(
            String fileName,
            ArrayList<Book> books,
            ArrayList<Student> students,
            ArrayList<Librarian> librarians) {

        ArrayList<Loan> loans = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Loan loan = fromStringLoans(line, books, students, librarians);
                if (loan != null) {
                    loans.add(loan);
                }
            }
        } catch (IOException e) {
            System.out.println("Error while loading file" + e.getMessage());
        }

        return loans;
    }
}

class Book {
    private final String title;
    private final String author;
    private final int pages;
    private final int publishedYear;
    private boolean isLoaned;
    private int loanCount;

    Book(String title, String author, int pages, int publishedDate) {
        this.title = title;
        this.author = author;
        this.pages = pages;
        this.publishedYear = publishedDate;
        this.isLoaned = false;
        this.loanCount = 0;

    }

    Book(String title, String author, int pages, int publishedDate, Boolean isLoaned, int loanCount) {
        this.title = title;
        this.author = author;
        this.pages = pages;
        this.publishedYear = publishedDate;
        this.isLoaned = isLoaned;
        this.loanCount = loanCount;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getPages() {
        return pages;
    }

    public int getPublishedYear() {
        return publishedYear;
    }

    public Boolean getIsLoaned() {
        return isLoaned;
    }

    public int getLoanCount() {
        return loanCount;
    }

    public void setIsLoaned(Boolean isLoaned) {
        this.isLoaned = isLoaned;
    }


    public String toStringBook() {

        return getTitle() + "," + getAuthor() + "," + getPages() + "," + getPublishedYear() + "," + getIsLoaned() + "," + getLoanCount();
    }


    public void setLoanCount(int count) {
        this.loanCount = count;
    }
}

class Student {
    private final String firstName;
    private final String lastName;
    private final String studentId;
    private final String major;
    private final LocalDate membershipDate;

    Student(String firstName, String lastName, String studentId, String major, LocalDate membershipDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.studentId = studentId;
        this.major = major;
        this.membershipDate = membershipDate;

    }


    String getFirstName() {
        return firstName;
    }

    String getLastName() {
        return lastName;
    }

    String getStudentId() {
        return studentId;
    }

    String getMajor() {
        return major;
    }

    LocalDate getMembershipDate() {
        return membershipDate;
    }

    public String toStringStudent() {

        return getFirstName() + "," + getLastName() + "," + getStudentId() + "," + getMajor() + "," + getMembershipDate();
    }


}

class Loan {
    private final Book book;
    private final Student student;
    private final Librarian librarian;
    private final LocalDate loanDate;
    private final LocalDate dueDate;
    private LocalDate returnDate;


    Loan(Book book, Student student, Librarian librarian, LocalDate loanDate, LocalDate dueDate, LocalDate returnDate) {
        this.book = book;
        this.student = student;
        this.librarian = librarian;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;

    }

    public Book getBook() {
        return book;
    }

    public Student getStudent() {
        return student;
    }

    public Librarian getLibrarian() {
        return librarian;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }


    String toStringLoans() {
        return book.getTitle() + "," + student.getStudentId() + "," + librarian.getEmployeeId() + "," + loanDate.toString() + "," + dueDate.toString() + "," + (returnDate != null ? returnDate.toString() : "");
    }


}

class LoanRequest {
    private Book book;
    private Student student;
    private Librarian assignedLibrarian;


    LoanRequest(Book book, Student student, Librarian assignedLibrarian) {
        this.book = book;
        this.student = student;
        this.assignedLibrarian = assignedLibrarian;
    }


    Student getStudent() {
        return student;
    }

    Book getBook() {
        return book;
    }

    Librarian getAssignedLibrarian() {
        return assignedLibrarian;
    }


    String toStringLoanRequests() {
        return book.getTitle() + "," + student.getStudentId() + "," + assignedLibrarian.getEmployeeId();
    }

}

class Librarian {
    private final String firstName;
    private final String lastName;
    private String employeeId;

    Librarian(String firstName, String lastName, String employeeId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.employeeId = employeeId;
    }

    String getFirstName() {
        return firstName;
    }

    String getLastName() {
        return lastName;
    }

    void setEmployeeId(String newEmployeeId) {
        this.employeeId = newEmployeeId;
    }

    String getEmployeeId() {
        return employeeId;
    }

    String toStringLibrarian() {
        return getFirstName() + "," + getLastName() + "," + getEmployeeId();
    }

}

class Manager {
    private final String firstName;
    private final String lastName;
    private final String qualificationLevel;

    Manager(String firstName, String lastName, String qualificationLevel) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.qualificationLevel = qualificationLevel;
    }

}

class Menu {
    private final Scanner scan;

    Menu() {
        scan = new Scanner(System.in);
    }

    void showMainMenu() {
        System.out.println("\n1. Student menu ");
        System.out.println("2. Librarian menu");
        System.out.println("3. Manager menu");
        System.out.println("4. Exit");
    }

    void studentFirstMenu() {
        System.out.println("\n====Student Menu====");
        System.out.println("1. Register to Library ");
        System.out.println("2. Login to Library ");
    }

    void studentSecondMenu() {
        System.out.println("\n1. Search a book");
        System.out.println("2. Loan a book ");
        System.out.println("3. Return a book ");
        System.out.println("4. Show all books on loan ");
        System.out.println("5. Show student info ");
        System.out.println("6. Exit");
    }

    void librarianMenu() {
        System.out.println("\n====Librarian Login====");
        System.out.println("1. Add new book ");
        System.out.println("2. Change ID ");
        System.out.println("3. Loan Requests");
        System.out.println("4. Exit");
    }

    void ManagerMenu() {
        System.out.println("\n====Manager Menu====");
        System.out.println("1. Add a new Librarian ");
        System.out.println("2. Show late returns ");
        System.out.println("3. Loan/return information per librarian ");
        System.out.println("4. Show top books on loan");
        System.out.println("5. Exit");
    }

    int getOption() {
        System.out.println("\nEnter your Option: ");
        return scan.nextInt();
    }
}

class Library {
    private final String libraryName;
    private final ArrayList<Book> books;
    private final ArrayList<Student> students;
    private final ArrayList<Librarian> librarian;
    private ArrayList<Loan> loans;

    Library(String libraryName, ArrayList<Book> books, ArrayList<Student> students, ArrayList<Librarian> librarians, ArrayList<Loan> loans) {
        this.libraryName = libraryName;
        this.books = books;
        this.students = students;
        this.librarian = librarians;
        this.loans = loans;
    }


    void printBookInfo(Book book) {
        System.out.println("\nBook title: " + book.getTitle());
        System.out.println("Book author: " + book.getAuthor());
        System.out.println("Book published year: " + book.getPublishedYear());
        System.out.println("Book pages: " + book.getPages());
        System.out.println("___________________________");
    }

    void printBooksOnLoan(ArrayList<Loan> loans) {
        for (Loan loan : loans) {
            if (loan.getBook().getIsLoaned()) {
                System.out.println("\nBook title: " + loan.getBook().getTitle());
                System.out.println("Book author: " + loan.getBook().getAuthor());
                System.out.println("Book published year: " + loan.getBook().getPublishedYear());
                System.out.println("Book pages: " + loan.getBook().getPages());
                System.out.println("Return time: " + loan.getDueDate());
                System.out.println("___________________________");
            } else {
                System.out.println("\nThere is not any book on loan");
            }
        }
    }

    public void printTop10BooksLastYear(ArrayList<Book> books, ArrayList<Loan> loans) {
        LocalDate oneYearAgo = LocalDate.now().minusYears(1);

        for (Book book : books) {
            int count = 0;
            for (Loan loan : loans) {
                if (loan.getBook().equals(book) && loan.getLoanDate().isAfter(oneYearAgo)) {
                    count++;
                }
            }
            book.setLoanCount(count);
        }


        books.sort((b1, b2) -> Integer.compare(b2.getLoanCount(), b1.getLoanCount()));

        System.out.println("Top 10 most borrowed books in the last year:");
        int limit = Math.min(10, books.size());
        for (int i = 0; i < limit; i++) {
            Book book = books.get(i);
            if (book.getLoanCount() > 0) {
                System.out.println((i + 1) + ". " + book.getTitle() + " - Borrowed " + book.getLoanCount() + " times");
            }
        }
    }

    public void printLibrarianStats(ArrayList<Loan> loans, ArrayList<Librarian> librarians) {
        for (Librarian librarian : librarians) {
            int givenCount = 0;
            int receivedCount = 0;

            for (Loan loan : loans) {
                if (loan.getLibrarian().equals(librarian)) {
                    givenCount++;
                    if (loan.getReturnDate() != null) {
                        receivedCount++;
                    }
                }
            }

            System.out.println("Librarian: " + librarian.getFirstName() + " " + librarian.getLastName());
            System.out.println("Books given: " + givenCount);
            System.out.println("Books received: " + receivedCount);
            System.out.println("------------------------------");
        }
    }

    void printStudentInfo(Student student) {
        System.out.println("\nStudent first name: " + student.getFirstName());
        System.out.println("Student last name: " + student.getLastName());
        System.out.println("Student ID: " + student.getStudentId());
        System.out.println("Student major: " + student.getMajor());
        System.out.println("Student membership date: " + student.getMembershipDate());
    }


    void printLibrarianInfo(Librarian librarian) {
        System.out.println("\nLibrarian first name: " + librarian.getFirstName());
        System.out.println("Librarian last name: " + librarian.getLastName());
        System.out.println("Librarian employee ID: " + librarian.getEmployeeId());
    }

    String getLibraryName() {
        return libraryName;
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

    Student searchStudentById(ArrayList<Student> students, Scanner scanner) {

        if (students.isEmpty()) {
            System.out.println("\nThere is no student in the database");
            return null;
        }

        System.out.println("\nEnter your student ID");

        while (true) {

            String studentId = scanner.nextLine();
            if (studentId.equalsIgnoreCase("exit")) {
                return null;
            }

            for (Student student : students) {
                if (student.getStudentId().equals(studentId)) {
                    return student;
                }
            }

            System.out.println("\nStudent ID not found. Please try again or type 'exit' to cancel.");
        }
    }


    Librarian searchLibrarianByEmployeeId(ArrayList<Librarian> librarians, Scanner scanner) {

        if (librarians.isEmpty()) {
            System.out.println("\nThere is no librarian in the database");
            return null;
        }

        System.out.println("\nEnter your Employee ID: ");
        while (true) {
            String employeeId = scanner.nextLine();

            if (employeeId.equalsIgnoreCase("exit")) {
                return null;
            }


            for (Librarian librarian : librarians) {
                if (librarian.getEmployeeId().equals(employeeId)) {
                    return librarian;
                }
            }

            System.out.println("\nThere is not any librarian with employeeId " + employeeId + "\n Please try again or type 'exit' to cancel.");
        }
    }

    public void printLateReturns(ArrayList<Loan> loans) {
        boolean hasLateReturn = false;

        for (Loan loan : loans) {
            if (loan.getReturnDate() != null && loan.getReturnDate().isAfter(loan.getDueDate())) {
                hasLateReturn = true;
                System.out.println("\nStudent info:");
                System.out.println("First Name: " + loan.getStudent().getFirstName());
                System.out.println("Last Name: " + loan.getStudent().getLastName());
                System.out.println("Student ID: " + loan.getStudent().getStudentId());
                System.out.println("Due Date: " + loan.getDueDate());
                System.out.println("Return Date: " + loan.getReturnDate());
                System.out.println("__________________________");
            }
        }

        if (!hasLateReturn) {
            System.out.println("There is not any late return.");
        }
    }
}

class DataProcessor {
    private final Scanner scanner;

    DataProcessor(Scanner scanner) {
        this.scanner = scanner;
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

    Student addStudent() {
        System.out.println("\nEnter your first name: ");
        String firstName = scanner.nextLine();
        System.out.println("Enter your last name: ");
        String lastName = scanner.nextLine();
        System.out.println("Enter your student ID: ");
        String studentId = scanner.nextLine();
        System.out.println("Enter your Major: ");
        String major = scanner.nextLine();
        System.out.println("You have been successfully added to the library ");
        return new Student(firstName, lastName, studentId, major, LocalDate.now());
    }

    Librarian addLibrarian() {
        System.out.println("\nEnter librarian first name: ");
        String libraryName = scanner.nextLine();
        System.out.println("Enter librarian last name: ");
        String libraryLastName = scanner.nextLine();
        System.out.println("Enter librarian employee ID: ");
        String EmployeeId = scanner.nextLine();
        return new Librarian(libraryName, libraryLastName, EmployeeId);
    }


    void chooseBookLoanAndRequest(Student student, ArrayList<Book> books, ArrayList<Librarian> librarians, ArrayList<LoanRequest> loanRequests, Scanner scanner) {
        boolean flag = true;

        if (books.isEmpty()) {
            System.out.println("\nThere is no book in the database");
            flag = false;
        }

        while (flag) {
            ArrayList<Book> availableBooks = new ArrayList<>();


            for (Book book : books) {
                if (!book.getIsLoaned()) {
                    availableBooks.add(book);
                }
            }

            if (availableBooks.isEmpty()) {
                System.out.println("\nThere is not any available book to loan at the moment ");
                flag = false;
            } else {

                System.out.println("Available books:");
                for (Book book : availableBooks) {
                    System.out.println("- " + book.getTitle());
                }

                boolean flag2 = true;
                while (flag2) {
                    System.out.println("Enter the title of the book you want to loan (or type 'exit' to cancel):");
                    String input = scanner.nextLine();

                    if (input.equalsIgnoreCase("exit")) {
                        flag2 = false;
                    }

                    for (Book book : availableBooks) {
                        if (book.getTitle().equalsIgnoreCase(input)) {

                            Librarian assignedLibrarian = getRandomLibrarian(librarians);

                            LoanRequest request = new LoanRequest(book, student, assignedLibrarian);
                            loanRequests.add(request);

                            System.out.println("Your loan request has been sent to librarian: " + assignedLibrarian.getFirstName() + " " + assignedLibrarian.getLastName());
                            book.setIsLoaned(true);
                            flag2 = false;
                            flag = false;

                        }
                    }
                }
            }
        }
    }


    Librarian getRandomLibrarian(ArrayList<Librarian> librarians) {
        Random random = new Random();
        int index = random.nextInt(librarians.size());
        return librarians.get(index);
    }


    public void handleRequestsForLibrarian(ArrayList<LoanRequest> loanRequests, ArrayList<Loan> loans, Scanner scanner) {
        System.out.println("Enter your librarian ID:");
        String id = scanner.nextLine();

        ArrayList<LoanRequest> assignedRequests = new ArrayList<>();
        for (LoanRequest req : loanRequests) {
            if (req.getAssignedLibrarian().getEmployeeId().equals(id)) {
                assignedRequests.add(req);
            }
        }

        if (assignedRequests.isEmpty()) {
            System.out.println("No requests assigned to this librarian ID.");
            return;
        }

        System.out.println("Select a request to handle:");
        for (int i = 0; i < assignedRequests.size(); i++) {
            LoanRequest req = assignedRequests.get(i);
            System.out.println((i + 1) + ". " + req.getStudent().getFirstName() + " " + req.getStudent().getLastName() +
                    " requests book: " + req.getBook().getTitle());
        }

        int choiceIndex = -1;
        while (choiceIndex < 1 || choiceIndex > assignedRequests.size()) {
            System.out.println("Enter the number of the request to handle:");
            try {
                choiceIndex = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }

        LoanRequest selectedReq = assignedRequests.get(choiceIndex - 1);
        System.out.println("Approve this request? (yes/no)");
        String choice = scanner.nextLine();

        if (choice.equalsIgnoreCase("yes")) {
            selectedReq.getBook().setIsLoaned(true);
            LocalDate now = LocalDate.now();
            Loan loan = new Loan(selectedReq.getBook(), selectedReq.getStudent(), selectedReq.getAssignedLibrarian(), now, now.plusDays(14), null);
            loans.add(loan);
            System.out.println("Loan approved.");

        } else {
            selectedReq.getBook().setIsLoaned(false);
            System.out.println("Loan request denied.");
        }

        loanRequests.remove(selectedReq);
    }

    public void returnBookByStudent(ArrayList<Loan> loans, Scanner scanner) {
        System.out.println("Enter your student ID:");
        String id = scanner.nextLine();

        ArrayList<Loan> activeLoans = new ArrayList<>();

        for (Loan loan : loans) {
            if (loan.getStudent().getStudentId().equals(id) && loan.getReturnDate() == null) {
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

        System.out.println("Book \"" + selectedLoan.getBook().getTitle() + "\" has been successfully returned.");
    }

    void changeLibrarianId(Librarian librarian, Scanner scanner) {
        System.out.println("\nEnter your new employee id: ");
        String newId = scanner.nextLine();
        librarian.setEmployeeId(newId);
        System.out.println("Your new employee id is: " + newId);
    }

    void clearFile(String fileName) {
        try (PrintWriter writer = new PrintWriter(fileName)) {

        } catch (IOException e) {
            System.out.println("Error while clearing file" + e.getMessage());
        }
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
