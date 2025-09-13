package projects.finalproject;

import java.util.ArrayList;
import java.util.Scanner;

public class Application {
    private final Scanner input;
    private final Library library;
    private final Manager manager;
    private final DataProcessor processor;
    private final Guest guest;
    private final LoadFromFile loadFromFile;
    private final SaveToFile saveToFile;
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
        this.processor = new DataProcessor(input);
        this.guest = new Guest();
        this.loadFromFile = new LoadFromFile();
        this.saveToFile = new SaveToFile();
        this.menu = new Menu();
        initializeData();
    }

    private void initializeData() {
        processor.creatingEmptyFiles();
        students = loadFromFile.loadStudents("Students.txt.file");
        librarians = loadFromFile.loadLibrarians("Librarians.txt.file");
        books = loadFromFile.loadBooks("Books.txt.file");
        loanRequests = loadFromFile.loadLoanRequests("LoanRequests.txt.file", books, students);
        loans = loadFromFile.loadLoans("Loans.txt.file", books, students);
    }

    public void run() {
        System.out.println("Welcome to " + library.getLibraryName() + " library!");

        while (true) {
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
                    System.exit(0);
                    break;
                }
                default:
                    System.out.println("Invalid option");
            }
        }
    }

    private void handleStudentMenu() {

        boolean studentRunning = true;
        while (studentRunning) {
            menu.studentFirstMenu();
            int option = menu.getOption();

            switch (option) {
                case 1:
                    students.add(processor.addStudent());
                    saveToFile.saveStudents(students);
                    break;

                case 2:
                    Student student = processor.studentVerification(students, input);
                    if (student == null) {
                        studentRunning = false;
                    } else {
                        library.printStudentInfo(student);

                        boolean studentRunning2 = true;
                        while (studentRunning2) {
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
                                    saveToFile.saveLoanRequests(loanRequests);
                                    break;

                                case 5:
                                    processor.returnBook(loans, librarians, student, input);
                                    processor.clearFile("Loans.txt.file");
                                    saveToFile.saveLoans(loans);
                                    processor.clearFile("Books.txt.file");
                                    saveToFile.saveBooks(books);
                                    processor.clearFile("Librarians.txt.file");
                                    saveToFile.saveLibrarian(librarians);
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
        }
    }

    private void handleLibrarianMenu() {
        boolean librarianRunning = true;

        while (librarianRunning) {
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
                        saveToFile.saveLibrarian(librarians);
                        break;

                    case 2:
                        books.add(processor.addBook(librarian));
                        saveToFile.saveBooks(books);
                        break;

                    case 3:
                        processor.handleLoanRequests(loanRequests, librarian, loans, input);
                        saveToFile.saveLoans(loans);
                        processor.clearFile("LoanRequests.txt.file");
                        processor.clearFile("Books.txt.file");
                        processor.clearFile("Librarians.txt.file");
                        saveToFile.saveBooks(books);
                        saveToFile.saveLibrarian(librarians);
                        break;

                    case 4:
                        Book book = library.searchBookToChange(books, input);
                        library.printBookInfo(book);
                        processor.changeBookInfo(book, input);
                        saveToFile.saveBooks(books);
                        break;

                    case 5:
                        saveToFile.saveLoans(loans);
                        library.printStudentLoanInfo(loans, input);
                        break;

                    case 6:
                        System.out.println("Exiting...");
                        librarianRunning2 = false;
                        break;

                    default:
                        System.out.println("Invalid option");
                }
            }
            librarianRunning = false;
        }
    }

    private void handleManagerMenu() {

        boolean managerRunning = processor.managerVerification(input, manager);
        if (!managerRunning) {
            return;
        }

        while (managerRunning) {
            menu.ManagerMenu();
            int option = menu.getOption();

            switch (option) {
                case 1:
                    librarians.add(processor.addLibrarian());
                    saveToFile.saveLibrarian(librarians);
                    break;


                case 2:
                    library.printLibrarianInfo(librarians, books, input);
                    break;

                case 3:
                    System.out.println("Exiting...");
                    managerRunning = false;
                    break;

                default:
                    System.out.println("Invalid option");
            }
        }
    }

    private void handleGuestMenu() {

        boolean guestRunning = true;
        while (guestRunning) {
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

                case 4:
                    System.out.println("Exiting...");
                    guestRunning = false;
                    break;

                default:
                    System.out.println("Invalid option");

            }
        }
    }
}