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
                        books.add(processor.addBook());
                        saveToFile.saveBooks(books);
                        break;

                    case 3:
                        Book book = library.searchBookByTitle(books, input);
                        library.printBookInfo(book);
                        processor.chooseAndChangeBookInfo(book, input);
                        saveToFile.saveBooks(books);
                        break;

                    case 4:
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
                case 1: {
                    librarians.add(processor.addLibrarian());
                    saveToFile.saveLibrarian(librarians);
                }
                case 2:
                    System.out.println("Exiting...");
                    managerRunning = false;

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
                    guest.printTheNumberOfStudents(students);
                    break;

                case 2:
                    guest.printBookInfoForGuest(library.searchBookByTitle(books, input));
                    break;

                case 3:
                    System.out.println("Exiting...");
                    guestRunning = false;
                    break;

                default:
                    System.out.println("Invalid option");

            }
        }
    }
}