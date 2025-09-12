package projects.finalproject;
import java.util.ArrayList;
import java.util.Scanner;

class FinalMain {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        Library library = new Library();
        Manager manager = new Manager();
        DataProcessor processor = new DataProcessor(input);
        Guest guest = new Guest();
        processor.creatingEmptyFiles();
        LoadFromFile loadFromFile = new LoadFromFile();
        SaveToFile saveToFile = new SaveToFile();
        ArrayList<Student> students = loadFromFile.loadStudents("Students.txt.file");
        ArrayList<Librarian> librarians = loadFromFile.loadLibrarians("Librarians.txt.file");
        ArrayList<Book> books = loadFromFile.loadBooks("Books.txt.file");


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
                                Student student = library.studentVerification(students, input);
                                if (student == null) {
                                    studentRunning = false;
                                    break;
                                } else
                                    library.printStudentInfo(student);
                                break;

                            case 3:
                                System.out.println("Exiting...");
                                studentRunning = false;
                                break;

                            default:
                                System.out.println("Invalid option");
                        }
                    }
                    break;

                case 2:

                    boolean librarianRunning = true;
                    boolean librarianRunning2 = true;

                    while (librarianRunning) {
                        Librarian librarian = library.librarianVerification(librarians, input);
                        if (librarian == null) {
                            break;
                        } else
                            while (librarianRunning2) {
                                menu.librarianMenu();
                                switch (menu.getOption()) {

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
                                }
                            }
                        librarianRunning = false;
                    }
                    break;


                case 3:
                    boolean managerRunning = manager.managerVerification(input);
                    if (!managerRunning) {
                        break;
                    }

                    while (managerRunning) {
                        menu.ManagerMenu();
                        switch (menu.getOption()) {

                            case 1:
                                librarians.add(processor.addLibrarian());
                                saveToFile.saveLibrarian(librarians);
                                break;

                            case 2:
                                System.out.println("Exiting...");
                                managerRunning = false;
                                break;
                        }
                    }
                    break;


                case 4:

                    boolean guestRunning = true;
                    while (guestRunning) {
                        menu.guestMenu();
                        switch (menu.getOption()) {

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

                        }


                    }
                    break;

                case 5:
                    System.out.println("Thank you!");
                    System.exit(0);
            }
        }
    }
}
