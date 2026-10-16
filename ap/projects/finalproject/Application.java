package projects.finalproject;

import java.util.ArrayList;
import java.util.Scanner;

public class Application {
    private final Scanner input;
    private final Library library;
    private final Manager manager;
    private final DataProcessor processor;
    private final Guest guest;
    private final Database db;
    private final Menu menu;
    private ArrayList<Student> students;
    private ArrayList<Librarian> librarians;
    private ArrayList<Loan> loans;
    private ArrayList<LoanRequest> loanRequests;
    private ArrayList<Book> books;

    public Application() {
        this.input = new Scanner(System.in);
        this.library = new Library();
        this.manager = new Manager();
        this.processor = new DataProcessor();
        this.guest = new Guest();
        this.db = new Database();
        this.menu = new Menu();
        initializeData();
    }

    private void initializeData() {
        db.init();
        students = db.loadStudents();
        librarians = db.loadLibrarians();
        books = db.loadBooks();
        loanRequests = db.loadLoanRequests(books, students);
        loans = db.loadLoans(books, students);
    }

    public void run() {
        System.out.println("Welcome to " + library.getLibraryName() + " library!");

        while (true) {
            try {
                menu.showMainMenu();
                int option = menu.getOption();

                switch (option) {
                    case 1:
                        handleStudentMenu();
                        break;

                    case 2:
                        handleLibrarianMenu();
                        break;

                    case 3:
                        handleManagerMenu();
                        break;

                    case 4:
                        handleGuestMenu();
                        break;

                    case 5: {
                        System.out.println("Thank you!");
                        db.close();
                        System.exit(0);
                        break;
                    }
                    default:
                        System.out.println("Invalid option");
                }

            } catch (Exception e) {
                System.out.println("An error occurred" + e.getMessage());
            }
        }
    }

    private void handleStudentMenu() {

        boolean studentRunning = true;
        while (studentRunning) {
            try {
                menu.studentFirstMenu();
                int option = menu.getOption();

                switch (option) {
                    case 1:
                        students.add(processor.addStudent());
                        db.saveStudents(students);
                        break;

                    case 2:
                        Student student = processor.studentVerification(students, input);
                        if (student == null) {
                            studentRunning = false;
                        } else {
                            library.printStudentInfo(student);

                            boolean studentRunning2 = true;
                            while (studentRunning2) {
                                try {
                                    menu.studentSecondMenu();
                                    int option2 = menu.getOption();

                                    switch (option2) {
                                        case 1:
                                            library.searchBooksByTitle(books, input);
                                            break;

                                        case 2:
                                            library.searchBooksByAuthor(books, input);
                                            break;

                                        case 3:
                                            library.searchBooksByPublishedYear(books, input);
                                            break;

                                        case 4:
                                            processor.requestBookToLoan(student, books, loanRequests, input);
                                            db.saveLoanRequests(loanRequests);
                                            break;

                                        case 5:
                                            processor.returnBook(loans, librarians, student, input);
                                            db.saveLoans(loans);
                                            db.saveBooks(books);
                                            db.saveLibrarians(librarians);
                                            break;

                                        case 6:
                                            library.printLoanBooksInfo(loans);
                                            break;

                                        case 7:
                                            library.printStudentInfo(student);
                                            break;

                                        case 8:
                                            System.out.println("Exiting...");
                                            studentRunning2 = false;
                                            break;

                                        default:
                                            System.out.println("Invalid option");
                                    }

                                } catch (Exception e) {
                                    System.out.println("An error occurred" + e.getMessage());
                                }
                            }
                        }
                        break;


                    case 3:
                        System.out.println("Exiting...");
                        studentRunning = false;
                        break;

                    default:
                        System.out.println("Invalid option");
                }
            } catch (Exception e) {
                System.out.println("An error occurred" + e.getMessage());
            }
        }
    }

    private void handleLibrarianMenu() {
        boolean librarianRunning = true;

        while (librarianRunning) {
            try {
                Librarian librarian = processor.librarianVerification(librarians, input);
                if (librarian == null) {
                    break;
                }

                boolean librarianRunning2 = true;
                while (librarianRunning2) {
                    menu.librarianMenu();
                    int option = menu.getOption();

                    switch (option) {
                    case 1:
                        processor.changeLibrarianPassword(librarian, input);
                        db.saveLibrarians(librarians);
                        break;

                    case 2:
                        books.add(processor.addBook(librarian));
                        db.saveBooks(books);
                        break;

                    case 3:
                        processor.handleLoanRequests(loanRequests, librarian, loans, input);
                        db.saveLoanRequests(loanRequests);
                        db.saveLoans(loans);
                        db.saveBooks(books);
                        db.saveLibrarians(librarians);
                        break;

                    case 4:
                        Book book = library.searchBookToChange(books, input);
                        library.printBookInfo(book);
                        processor.changeBookInfo(book, input);
                        db.saveBooks(books);
                        break;

                    case 5:
                        db.saveLoans(loans);
                        library.printStudentLoanInfo(loans, input);
                        break;

                    case 6:
                        processor.activateStudent(students, input);
                        db.saveStudents(students);
                        break;

                    case 7:
                        processor.deactivateStudent(students, input);
                        db.saveStudents(students);
                        break;

                        case 8:
                            System.out.println("Exiting...");
                            librarianRunning2 = false;
                            break;

                        default:
                            System.out.println("Invalid option");
                    }
                }
                librarianRunning = false;
            } catch (Exception e) {
                System.out.println("An error occurred" + e.getMessage());
            }
        }
    }

    private void handleManagerMenu() {

        boolean managerRunning = processor.managerVerification(input, manager);
        if (!managerRunning) {
            return;
        }

        while (managerRunning) {
            try {

                menu.ManagerMenu();
                int option = menu.getOption();

                switch (option) {
                    case 1:
                        librarians.add(processor.addLibrarian());
                        db.saveLibrarians(librarians);
                        break;


                    case 2:
                        library.printLibrarianInfo(librarians, books, input);
                        break;

                    case 3:
                        library.printLoanStatistics(books, loans);
                        break;

                    case 4:
                        library.printTop10StudentsWithMostDelays(students, loans);
                        break;

                    case 5:
                        System.out.println("Exiting...");
                        managerRunning = false;
                        break;

                    default:
                        System.out.println("Invalid option");
                }
            } catch (Exception e) {
                System.out.println("An error occurred" + e.getMessage());
            }
        }
    }

    private void handleGuestMenu() {

        boolean guestRunning = true;
        while (guestRunning) {
            try {
                menu.guestMenu();
                int option = menu.getOption();

                switch (option) {
                    case 1:
                        guest.printSimpleDetails(students, books, loans);
                        break;

                    case 2:
                        library.printAllBooks(books);
                        break;

                    case 3:
                        library.searchBooksByTitle(books, input);
                        break;

                    case 4:
                        System.out.println("Exiting...");
                        guestRunning = false;
                        break;

                    default:
                        System.out.println("Invalid option");

                }
            } catch (Exception e) {
                System.out.println("An error occurred" + e.getMessage());
            }
        }
    }
}