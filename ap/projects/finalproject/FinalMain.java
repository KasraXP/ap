package projects.finalproject;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

class FinalMain {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        DataProcessor processor = new DataProcessor(input);
        processor.creatingEmptyFiles();
        LoadFromFile loadFromFile = new LoadFromFile();
        SaveToFile saveToFile = new SaveToFile();
        ArrayList<Student> students = loadFromFile.loadStudents("Students.txt.file");
        Library library = new Library("Central");


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
                                break;

                            default:
                                System.out.println("Invalid option");
                        }
                    }
            }
        }
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

    Library(String libraryName) {
        this.libraryName = libraryName;
    }

    void printStudentInfo(Student student) {
        System.out.println("\nStudent first name: " + student.getFirstName());
        System.out.println("Student last name: " + student.getLastName());
        System.out.println("Student ID: " + student.getStudentId());
        System.out.println("Student major: " + student.getMajor());
        System.out.println("Student membership date: " + student.getMembershipDate());
    }

    String getLibraryName() {
        return libraryName;
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
}

class LoadFromFile {

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
            //System.out.println("Error while loading " + e.getMessage());
        }

        return students;
    }


}

class DataProcessor {
    private final Scanner scanner;

    DataProcessor(Scanner scanner) {
        this.scanner = scanner;
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
        System.out.println("4. Exit");
    }

    void studentFirstMenu() {
        System.out.println("\n====Student Menu====");
        System.out.println("1. Register to Library ");
        System.out.println("2. Login to Library ");
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

    int getOption() {
        System.out.println("\nEnter your Option: ");
        return scan.nextInt();
    }
}

