package projects.Main;


import java.io.*;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.Scanner;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;

class Main {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        LoadFromFile loadFromFile = new LoadFromFile();
        SaveToFile saveToFile = new SaveToFile();
        ArrayList<Book> books = loadFromFile.loadBooks("Books.txt");
        ArrayList<Student> students = loadFromFile.loadStudents("Students.txt");
        ArrayList<Librarian> librarians = loadFromFile.loadLibrarians("Librarians.txt");
        ArrayList<LoanRequest> loansRequests = new ArrayList<>();
        DataProcessor processor = new DataProcessor(input);
        ArrayList<Loan> loans = loadFromFile.loadLoans("Loans.txt",books,students,librarians);
        Library library = new Library("Centeral Library", books, students, librarians, loans);
        Manager manager = new Manager("Ali", "Jamshidy", "PHP");
        librarians.add(new Librarian("Reza", "Dalairy", "123456789"));
        books.add(new Book("Shab", "Mohammad Boomipoor", 156, 1396));
        students.add(new Student("Saadegh", "Momeni", "403463129", "Law student", LocalDate.now()));

        Menu menu = new Menu();

        System.out.println("Welcome to library!");
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
                                saveToFile.saveStudents(students, "Students.txt");
                                break;

                            case 2:
                                Student student = processor.searchStudentById(students, input);
                                if (student == null) {
                                    studentRunning = false;
                                    break;
                                } else
                                    processor.printStudentInfo(student);


                                boolean loggedIn = true;
                                while (loggedIn) {
                                    menu.studentSecondMenu();
                                    switch (menu.getOption()) {
                                        case 1:
                                            Book book = processor.searchBookByTitle(books, input);
                                            processor.printBookInfo(book);
                                            break;

                                        case 2:
                                            processor.chooseBookLoanAndRequest(student, books, librarians, loansRequests, input);
                                            saveToFile.saveLoans(loans, "Loans.txt");
                                            break;

                                        case 3:
                                        case 4:
                                            processor.notImplementedYet();
                                            break;
                                        case 5:
                                            processor.printStudentInfo(processor.searchStudentById(students, input));
                                            break;
                                        case 6:
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


                    Librarian librarian = processor.searchLibrarianByEmployeeId(librarians, input);
                    processor.printLibrarianInfo(librarian);
                    while (librarianRunning) {
                        menu.librarianMenu();
                        switch (menu.getOption()) {
                            case 1:
                                books.add(processor.addBook());
                                saveToFile.saveBooks(books, "Books.txt");
                                break;

                            case 2:
                                processor.changeLibrarianId(processor.searchLibrarianByEmployeeId(librarians, input), input);
                                saveToFile.saveLibrarian(librarians, "Librarians.txt");
                                break;

                            case 3:
                                processor.handleRequestsForLibrarian(loansRequests, loans, input);
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
                                saveToFile.saveLibrarian(librarians, "Librarians.txt");
                                break;

                            case 2:
                            case 3:
                            case 4:
                                processor.notImplementedYet();
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

    void saveBooks(ArrayList<Book> books, String filename) {
        try {
            PrintWriter writer = new PrintWriter(filename);
            for (Book book : books) {
                writer.println(book.toStringBook());
            }
            writer.close();

        } catch (IOException e) {
            System.out.println("Error while saving books file" + e.getMessage());

        }
    }


    void saveStudents(ArrayList<Student> students, String fileName) {
        try {
            PrintWriter writer = new PrintWriter(fileName);
            for (Student student : students) {
                writer.println(student.toStringStudent());
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error while saving students file" + e.getMessage());
        }

    }

    void saveLibrarian(ArrayList<Librarian> librarians, String fileName) {
        try {
            PrintWriter writer = new PrintWriter(fileName);
            for (Librarian librarian : librarians) {
                writer.println(librarian.toStringLibrarian());
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error while saving librarians file" + e.getMessage());
        }
    }

    void saveLoans(ArrayList<Loan> loans, String fileName) {
        try {
            PrintWriter writer = new PrintWriter(fileName);
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
        String[] parts = line.split(",");
        String firstName = parts[0];
        String lastName = parts[1];
        String studentId = parts[2];
        String major = parts[3];
        LocalDate membershipDate = LocalDate.parse(parts[4]);
        return new Student(firstName, lastName, studentId, major, membershipDate);
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
        String[] parts = line.split(",");
        String firstName = parts[0];
        String lastName = parts[1];
        String studentId = parts[2];

        return new Librarian(firstName, lastName, studentId);
    }

    ArrayList<Librarian> loadLibrarians(String fileName) {
        ArrayList<Librarian> librarians = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
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

    public Loan fromStringLoans(
            String line,
            ArrayList<Book> books,
            ArrayList<Student> students,
            ArrayList<Librarian> librarians) {

        String[] parts = line.split(",");
        String bookTitle = parts[0];
        String studentId = parts[1];
        String librarianId = parts[2];
        LocalDateTime loanDate = LocalDateTime.parse(parts[3]);
        LocalDateTime dueDate = LocalDateTime.parse(parts[4]);
        LocalDateTime returnDate = parts[5].isEmpty() ? null : LocalDateTime.parse(parts[5]);

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
            System.out.println("Error: Couldn't find matching book, student, or librarian.");
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
            e.printStackTrace();
        }

        return loans;
    }
}


class Book {
    private String title;
    private String author;
    private int pages;
    private int publishedYear;
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

    public void setLoanCount(int loanCount) {
        this.loanCount = loanCount++;
    }

    public void setIsLoaned(Boolean isLoaned) {
        this.isLoaned = isLoaned;
    }


    public String toStringBook() {

        return getTitle() + "," + getAuthor() + "," + getPages() + "," + getPublishedYear() + "," + getIsLoaned() + "," + getLoanCount();
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
    private Book book;
    private Student student;
    private Librarian librarian;
    private LocalDateTime loanDate;
    private LocalDateTime dueDate;
    private LocalDateTime returnDate;

    Loan(Book book, Student student, Librarian librarian, LocalDateTime loanDate, LocalDateTime dueDate) {
        this.book = book;
        this.student = student;
        this.librarian = librarian;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
        this.returnDate = null;

    }

    Loan(Book book, Student student, Librarian librarian, LocalDateTime loanDate, LocalDateTime dueDate, LocalDateTime returnDate) {
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

    public LocalDateTime getLoanDate() {
        return loanDate;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public LocalDateTime getReturnDate() {
        return returnDate;
    }

    void setReturnDate(LocalDateTime returnDate) {
        this.returnDate = returnDate;
    }

    boolean isReturned() {
        return returnDate != null;
    }

    String toStringLoans() {
        return book.getTitle() + "," + student.getStudentId() + "," + librarian.getEmployeeId() + "," + loanDate.toString() + "," + dueDate.toString() + "," + returnDate.toString();
    }


}

class LoanRequest {
    private Book book;
    private Student student;
    private Librarian assignedLibrarian;
    private Boolean isApproved;

    LoanRequest(Book book, Student student, Librarian assignedLibrarian) {
        this.book = book;
        this.student = student;
        this.assignedLibrarian = assignedLibrarian;
        this.isApproved = false;
    }

    void approve() {
        isApproved = true;
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

    boolean isApproved() {
        return isApproved;
    }


}

class Librarian {
    private String firstName;
    private String lastName;
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
    private String firstName;
    private String lastName;
    private String qualificationLevel;

    Manager(String firstName, String lastName, String qualificationLevel) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.qualificationLevel = qualificationLevel;
    }

    void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    void setLastName(String lastName) {
        this.lastName = lastName;
    }

    void setQualificationLevel(String qualificationLevel) {
        this.qualificationLevel = qualificationLevel;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getQualificationLevel() {
        return qualificationLevel;
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
        System.out.println("\n1. Searching a book");
        System.out.println("2. Loan a book ");
        System.out.println("3. Return a book ");
        System.out.println("4. Show all books ");
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
        System.out.println("4. Top 10 books of last year");
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
    private final ArrayList<Loan> loans;

    Library(String libraryName, ArrayList<Book> books, ArrayList<Student> students, ArrayList<Librarian> librarians, ArrayList<Loan> loans) {
        this.libraryName = libraryName;
        this.books = books;
        this.students = students;
        this.librarian = librarians;
        this.loans = loans;
    }


    void bookList() {

        for (Book book : books) {
            if (book.getIsLoaned() == false) {
                System.out.println("\nBook title: " + book.getTitle() +
                        "\nBook author: " + book.getAuthor() +
                        "\nBook published year: " + book.getPublishedYear() +
                        "\nBook pages: " + book.getPages() + "\n______________________");
            }
        }
    }

    void studentList(Student student) {
        for (Student students : students) {
            System.out.println("\nStudent first name:" + student.getFirstName() +
                    "\nStudent last name: " + student.getLastName() +
                    "\nStudent ID: " + student.getStudentId() +
                    "\nStudent Major: " + student.getMajor() +
                    "\nStudent membership date: " + student.getMembershipDate() + "\n");
        }


    }

    void librarianList() {
        for (Librarian librarian : librarian) {
            System.out.println("\nLibrarian first name: " + librarian.getFirstName() +
                    "\nLibrarian last name: " + librarian.getLastName() +
                    "\nEmployee Id: " + librarian.getEmployeeId() + "\n");

        }
    }

    void loansList() {
        for (Loan loan : loans) {
            Book book = loan.getBook();
            System.out.println("\nBook title: " + book.getTitle() +
                    "\nBook author: " + book.getAuthor() +
                    "\nBook published year: " + book.getPublishedYear() +
                    "\nBook pages: " + book.getPages() + "\n");
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

    Student searchStudentById(ArrayList<Student> students, Scanner scanner) {
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


    Book searchBookByTitle(ArrayList<Book> books, Scanner scanner) {

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

    Librarian searchLibrarianByEmployeeId(ArrayList<Librarian> librarians, Scanner scanner) {

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


    String chooseBookLoanAndRequest(Student student, ArrayList<Book> books, ArrayList<Librarian> librarians, ArrayList<LoanRequest> loanRequests, Scanner scanner) {
        ArrayList<Book> availableBooks = new ArrayList<>();


        for (Book book : books) {
            if (book.getIsLoaned() == false) {
                availableBooks.add(book);
            }
        }


        if (availableBooks.isEmpty()) {
            System.out.println("No books are available to borrow at the moment.");
            return null;
        }

        System.out.println("Available books:");
        for (Book book : availableBooks) {
            System.out.println("- " + book.getTitle());
        }

        while (true) {
            System.out.println("Enter the title of the book you want to borrow (or type 'exit' to cancel):");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Loan request cancelled.");
                return null;
            }

            for (Book book : availableBooks) {
                if (book.getTitle().equalsIgnoreCase(input)) {

                    Librarian assignedLibrarian = getRandomLibrarian(librarians);

                    LoanRequest request = new LoanRequest(book, student, assignedLibrarian);
                    loanRequests.add(request);

                    System.out.println("Your loan request has been sent to librarian: " + assignedLibrarian.getFirstName() + " " + assignedLibrarian.getLastName());

                    return assignedLibrarian.getEmployeeId();
                }
            }


            System.out.println("Book not found in available list. Please try again.");
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

        Iterator<LoanRequest> iterator = loanRequests.iterator();

        while (iterator.hasNext()) {
            LoanRequest req = iterator.next();
            if (req.getAssignedLibrarian().getEmployeeId().equals(id)) {
                System.out.println("Request from student " + req.getStudent().getFirstName() + " " + req.getStudent().getLastName() + " for book " + req.getBook().getTitle());
                System.out.println("Approve this request? (yes/no)");
                String choice = scanner.nextLine();

                if (choice.equalsIgnoreCase("yes")) {
                    req.getBook().setIsLoaned(true);
                    LocalDateTime now = LocalDateTime.now();
                    Loan loan = new Loan(req.getBook(), req.getStudent(), req.getAssignedLibrarian(), now, now.plusDays(14), null);
                    loans.add(loan);
                    System.out.println("Loan approved.");
                } else {
                    System.out.println("Loan request denied.");
                }
                iterator.remove();

            } else
                System.out.println("Emplyee Id is not valid.");
        }
    }


    void changeLibrarianId(Librarian librarian, Scanner scanner) {
        System.out.println("\nEnter your new employee id: ");
        String newId = scanner.nextLine();
        librarian.setEmployeeId(newId);
        System.out.println("Your new employee id is: " + newId);
    }


    void printBookInfo(Book book) {
        System.out.println("\nBook title: " + book.getTitle());
        System.out.println("Book author: " + book.getAuthor());
        System.out.println("Book published year: " + book.getPublishedYear());
        System.out.println("Book pages: " + book.getPages());
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


    void notImplementedYet() {
        System.out.println("\nSorry");
        System.out.println("this feature is currently unavailable");
    }

}
