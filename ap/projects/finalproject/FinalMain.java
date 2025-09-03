package projects.finalproject;

import java.io.*;
import java.time.LocalDate;
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
                    while (librarianRunning) {
                        Librarian librarian = library.librarianVerification(librarians, input);
                        if (librarian == null) {
                            librarianRunning = false;
                            break;
                        } else
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

                        }


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

                            case 3:
                                System.out.println("Exiting...");
                                guestRunning = false;
                                break;

                        }


                    }
            }
        }
    }
}

class Student {
    private final String userName;
    private final String password;
    private final String major;
    private final LocalDate membershipDate;

    Student(String userName, String password, String major, LocalDate membershipDate) {
        this.userName = userName;
        this.password = password;
        this.major = major;
        this.membershipDate = membershipDate;

    }

    String getUserName() {
        return userName;
    }

    String getPassword() {
        return password;
    }

    String getMajor() {
        return major;
    }

    LocalDate getMembershipDate() {
        return membershipDate;
    }

    public String toStringStudent() {

        return getUserName() + "," + getPassword() + "," + getMajor() + "," + getMembershipDate();
    }
}

class Librarian {
    private final String fullName;
    private String password;

    Librarian(String fullName, String password) {
        this.fullName = fullName;
        this.password = password;
    }

    String getFullName() {
        return fullName;
    }

    void setPassword(String newPassword) {
        this.password = newPassword;
    }

    String getPassword() {
        return password;
    }

    String toStringLibrarian() {
        return getFullName() + "," + getPassword();
    }

}

class Manager {
    private final String name;
    private final String password;

    Manager() {
        name = "TheOwner";
        password = "1234567890";
    }

    Boolean managerVerification(Scanner scanner) {

        while (true) {

            String managerName = null;
            String managerPassword;


            System.out.println("Enter your username: ");
            managerName = scanner.nextLine();

            if (managerName != null && managerName.equalsIgnoreCase("exit")) {
                System.out.println("Exiting...");
                return false;
            }

            System.out.println("Enter your password: ");
            managerPassword = scanner.nextLine();

            if (managerName.equals(name) && managerPassword.equals(password)) {
                System.out.println("Manager verified");
                return true;
            } else {
                System.out.println("Wrong password or name\nplease try again or type (exit) to cancel");

            }

        }

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

class Library {
    private final String libraryName;

    Library() {
        this.libraryName = "Central";
    }

    void printStudentInfo(Student student) {
        System.out.println("\nStudent username: " + student.getUserName());
        System.out.println("Student password: " + student.getPassword());
        System.out.println("Student major: " + student.getMajor());
        System.out.println("Student membership date: " + student.getMembershipDate());
    }

    void printBookInfo(Book book) {
        System.out.println("\nBook title: " + book.getTitle());
        System.out.println("Book author: " + book.getAuthor());
        System.out.println("Book published year: " + book.getPublishedYear());
        System.out.println("Book pages: " + book.getPages());
        System.out.println("___________________________");
    }

    String getLibraryName() {
        return libraryName;
    }

    Student studentVerification(ArrayList<Student> students, Scanner scanner) {
        System.out.println("====Student verification====");
        boolean isThereOurStudent = false;

        while (true) {

            String studentUserName = null;
            String studentPassword;


            System.out.println("Enter your username: ");
            studentUserName = scanner.nextLine();

            if (studentUserName != null && studentUserName.equalsIgnoreCase("exit")) {
                System.out.println("Exiting...");
                return null;
            }

            System.out.println("Enter your password: ");
            studentPassword = scanner.nextLine();


            for (Student student : students) {
                if (student.getPassword().equals(studentPassword) && student.getUserName().equals(studentUserName)) {
                    System.out.println("Student verified");
                    isThereOurStudent = true;
                    return student;
                }
            }

            if (!isThereOurStudent) {
                System.out.println("Wrong password or name\nplease try again or type (exit) to cancel");
            }

        }
    }

    Librarian librarianVerification(ArrayList<Librarian> librarians, Scanner scanner) {

        System.out.println("====Librarian verification====");
        boolean isThereOurLibrarian = false;

        while (true) {

            String librarianFullName = null;
            String librarianPassword;


            System.out.println("Enter your username: ");
            librarianFullName = scanner.nextLine();

            if (librarianFullName != null && librarianFullName.equalsIgnoreCase("exit")) {
                System.out.println("Exiting...");
                return null;
            }

            System.out.println("Enter your password: ");
            librarianPassword = scanner.nextLine();


            for (Librarian librarian : librarians) {
                if (librarian.getPassword().equals(librarianPassword) && librarian.getFullName().equals(librarianFullName)) {
                    System.out.println("Librarian verified");
                    isThereOurLibrarian = true;
                    return librarian;
                }
            }

            if (!isThereOurLibrarian) {
                System.out.println("Wrong password or name\nplease try again or type (exit) to cancel");
            }
        }
    }
}

class Guest {

    void printTheNumberOfStudents(ArrayList<Student> students) {
        if (students.isEmpty()) {
            System.out.println("\nThere are no students in the database");
        } else if (students.size() == 1) {
            System.out.println("\nThere is one student in the database");
        } else
            System.out.println("\nThere are " + students.size() + " students in the database");
    }
}

class SaveToFile {

    void saveStudents(ArrayList<Student> students) {
        try {
            PrintWriter writer = new PrintWriter("Students.txt.file");
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
            PrintWriter writer = new PrintWriter("Librarians.txt.file");
            for (Librarian librarian : librarians) {
                writer.println(librarian.toStringLibrarian());
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error while saving librarians file" + e.getMessage());
        }
    }

    void saveBooks(ArrayList<Book> books) {
        try {
            PrintWriter writer = new PrintWriter("Books.txt.file");
            for (Book book : books) {
                writer.println(book.toStringBook());
            }
            writer.close();

        } catch (IOException e) {
            System.out.println("Error while saving books file" + e.getMessage());

        }
    }

}

class LoadFromFile {

    public Student fromStringStudent(String line) {
        if (line.isEmpty()) {
            return null;
        } else {
            String[] parts = line.split(",");
            String userName = parts[0];
            String password = parts[1];
            String major = parts[2];
            LocalDate membershipDate = LocalDate.parse(parts[3]);
            return new Student(userName, password, major, membershipDate);
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
            //System.out.println("Error while loading " + e.getMessage());
        }

        return students;
    }

    public Librarian fromStringLibrarian(String line) {
        if (line.isEmpty()) {
            return null;
        } else {
            String[] parts = line.split(",");
            String fullName = parts[0];
            String employeeId = parts[1];

            return new Librarian(fullName, employeeId);
        }
    }

    ArrayList<Librarian> loadLibrarians(String fileName) {
        ArrayList<Librarian> librarians = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("Librarians.txt.file"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Librarian librarian = fromStringLibrarian(line);
                librarians.add(librarian);
            }
        } catch (IOException e) {
            System.out.println("Error while loading file " + e.getMessage());
        }
        return librarians;
    }


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

}

class DataProcessor {
    private final Scanner scanner;

    DataProcessor(Scanner scanner) {
        this.scanner = scanner;
    }

    Student addStudent() {
        System.out.println("\nEnter your username name: ");
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

    void changeLibrarianPassword(Librarian librarian, Scanner scanner) {
        System.out.println("\nEnter your new password: ");
        String newPass = scanner.nextLine();
        librarian.setPassword(newPass);
        System.out.println("\nYour new password has been changed\nYour new password is " + newPass);
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

class Menu {
    private final Scanner scan;

    Menu() {
        scan = new Scanner(System.in);
    }

    void showMainMenu() {
        System.out.println("\n1. Student menu ");
        System.out.println("2. Librarian menu");
        System.out.println("3. Manager menu");
        System.out.println("4. Guest menu");
        System.out.println("5. Exit");
    }

    void guestMenu() {
        System.out.println("\n====Guest Menu====");
        System.out.println("1. Show the number of students");
        System.out.println("2. Exit");
    }

    void studentFirstMenu() {
        System.out.println("\n====Student Menu====");
        System.out.println("1. Register to Library ");
        System.out.println("2. Login to Library ");
        System.out.println("3. Exit");
    }

    void studentSecondMenu() {
        System.out.println("\n1. Search a book by title");
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
        System.out.println("3. Loan Requests");
        System.out.println("4. Exit");
    }

    void ManagerMenu() {
        System.out.println("\n====Manager Menu====");
        System.out.println("1. Add a new Librarian ");
        System.out.println("2. Exit");
    }

    int getOption() {
        System.out.println("\nEnter your Option: ");
        return scan.nextInt();
    }
}



