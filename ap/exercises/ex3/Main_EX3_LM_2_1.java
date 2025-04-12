package exercises.ex3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main_EX3_LM_2_1 {

    public static void main(String[] args) {
        Books book1 = new Books("Animal Farm", "George Orwell", 128, 1945);
        Books book2 = new Books("White Nights", "Fyodor Dostoevsky", 112, 1848);
        Books book3 = new Books("To Kill a Mockingbird", "Harper Lee", 281, 1960);
        Books book4 = new Books("1984", "George Orwell", 328, 1949);

        Books[] books = {book1, book2, book3, book4};
        saveBooksToFile(books);

        Students student1 = new Students("Helya", "Torabi", 403356187, "Dentistry Student");
        Students student2 = new Students("Mostafa", "Yari", 403564987, "Law Student");
        Students student3 = new Students("Morteza", "Safari", 403574690, "Computer Science Student");

        Students[] students = {student1, student2, student3};
        saveStudentsToFile(students);

        Loan loan1 = new Loan(403356187, "Animal Farm");
        Loan loan2 = new Loan(403564987, "White Nights");
        Loan loan3 = new Loan(403574690, "1984");

        Loan[] loans = {loan1, loan2, loan3};
        saveLoansToFile(loans);

        Books[] loadedBooks = loadBooksFromFile();
        printBooks(loadedBooks);

        Students[] loadedStudents = loadStudentsFromFile();
        printStudents(loadedStudents);

        Loan[] loadedLoans = loadLoansFromFile();
        printLoans(loadedLoans);

        Scanner sc = new Scanner(System.in);
        System.out.println("\nEnter a student name to search:");
        String name = sc.nextLine();

        Students foundStudent = searchStudentByName(loadedStudents, name);
        if (foundStudent != null) {
            System.out.println("Student found: " + "\nName: " + foundStudent.name + "\nSurname: " + foundStudent.surname + "\nID: " + foundStudent.studentID + "\nMajor: " + foundStudent.major);
        } else {
            System.out.println("Student not found");
        }
    }

    public static void saveBooksToFile(Books[] books) {
        try (PrintWriter writer = new PrintWriter(new File("books.txt"))) {
            for (Books book : books) {
                writer.println(book.title + "," + book.author + "," + book.pages + "," + book.year);
            }
        } catch (IOException e) {
            System.out.println("Error writing books information: " + e.getMessage());
        }
    }

    public static void saveStudentsToFile(Students[] students) {
        try (PrintWriter writer = new PrintWriter(new File("students.txt"))) {
            for (Students student : students) {
                writer.println(student.name + "," + student.surname + "," + student.studentID + "," + student.major);
            }
        } catch (IOException e) {
            System.out.println("Error writing students information: " + e.getMessage());
        }
    }

    public static void saveLoansToFile(Loan[] loans) {
        try (PrintWriter writer = new PrintWriter(new File("loans.txt"))) {
            for (Loan loan : loans) {
                writer.println(loan.studentID + "," + loan.bookTitle);
            }
        } catch (IOException e) {
            System.out.println("Error writing loans information: " + e.getMessage());
        }
    }

    public static Books[] loadBooksFromFile() {
        Books[] books = new Books[4];
        try (Scanner scanner = new Scanner(new File("books.txt"))) {
            int index = 0;
            while (scanner.hasNextLine() && index < books.length) {
                String line = scanner.nextLine();
                String[] bookData = line.split(",");
                String title = bookData[0];
                String author = bookData[1];
                int pages = Integer.parseInt(bookData[2]);
                int year = Integer.parseInt(bookData[3]);
                books[index] = new Books(title, author, pages, year);
                index++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error reading books information: " + e.getMessage());
        }
        return books;
    }

    public static Students[] loadStudentsFromFile() {
        Students[] students = new Students[3];
        try (Scanner scanner = new Scanner(new File("students.txt"))) {
            int index = 0;
            while (scanner.hasNextLine() && index < students.length) {
                String line = scanner.nextLine();
                String[] studentData = line.split(",");
                String name = studentData[0];
                String surname = studentData[1];
                int studentID = Integer.parseInt(studentData[2]);
                String major = studentData[3];
                students[index] = new Students(name, surname, studentID, major);
                index++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error reading students information: " + e.getMessage());
        }
        return students;
    }

    public static Loan[] loadLoansFromFile() {
        Loan[] loans = new Loan[3];
        try (Scanner scanner = new Scanner(new File("loans.txt"))) {
            int index = 0;
            while (scanner.hasNextLine() && index < loans.length) {
                String line = scanner.nextLine();
                String[] loanData = line.split(",");
                int studentID = Integer.parseInt(loanData[0]);
                String bookTitle = loanData[1];
                loans[index] = new Loan(studentID, bookTitle);
                index++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error reading loans information: " + e.getMessage());
        }
        return loans;
    }

    public static void printBooks(Books[] books) {
        System.out.println("Books Information:");
        for (Books book : books) {
            System.out.println("Title: " + book.title + ", Author: " + book.author + ", Pages: " + book.pages + ", Year: " + book.year);
        }
    }

    public static void printStudents(Students[] students) {
        System.out.println("\nStudents Information:");
        for (Students student : students) {
            System.out.println("Name: " + student.name + ", Surname: " + student.surname + ", ID: " + student.studentID + ", Major: " + student.major);
        }
    }

    public static void printLoans(Loan[] loans) {
        System.out.println("\nLoans Information:");
        for (Loan loan : loans) {
            System.out.println("Student ID: " + loan.studentID + ", Book Title: " + loan.bookTitle);
        }
    }

    public static Students searchStudentByName(Students[] students, String name) {
        for (Students student : students) {
            if (student.name.equalsIgnoreCase(name)) {
                return student;
            }
        }
        return null;
    }
}

class Books {
    String title;
    String author;
    int pages;
    int year;

    public Books(String title, String author, int pages, int year) {
        this.title = title;
        this.author = author;
        this.pages = pages;
        this.year = year;
    }
}

class Students {
    String name;
    String surname;
    int studentID;
    String major;

    public Students(String name, String surname, int studentID, String major) {
        this.name = name;
        this.surname = surname;
        this.studentID = studentID;
        this.major = major;
    }
}

class Loan {
    int studentID;
    String bookTitle;

    public Loan(int studentID, String bookTitle) {
        this.studentID = studentID;
        this.bookTitle = bookTitle;
    }
}