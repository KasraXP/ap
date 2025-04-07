package exercises.ex3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main_EX3_LM_1_2_C {

    public static void main(String[] args) {
        Book book1 = new Book("Animal Farm", "George Orwell", 128, 1945);
        Book book2 = new Book("White Nights", "Fyodor Dostoevsky", 112, 1848);
        Book book3 = new Book("To Kill a Mockingbird", "Harper Lee", 281, 1960);
        Book book4 = new Book("1984", "George Orwell", 328, 1949);

        Book[] books = {book1, book2, book3, book4};

        saveBooksToFile(books);

        Student student1 = new Student("Helya", "Torabi", 403356187, "Dentistry Student");
        Student student2 = new Student("Mostafa", "Yari", 403564987, "Law Student");
        Student student3 = new Student("Morteza", "Safari", 403574690, "Computer Science Student");

        Student[] students = {student1, student2, student3};

        saveStudentsToFile(students);

        Book[] loadedBooks = loadBooksFromFile();
        printBooks(loadedBooks);

        Student[] loadedStudents = loadStudentsFromFile();
        printStudents(loadedStudents);
    }

    public static void saveBooksToFile(Book[] books) {
        try (PrintWriter writer = new PrintWriter(new File("books.txt"))) {
            for (Book book : books) {
                writer.println(book.title + "," + book.author + "," + book.pages + "," + book.year);
            }
        } catch (IOException e) {
            System.out.println("Error writing books information: " + e.getMessage());
        }
    }

    public static void saveStudentsToFile(Student[] students) {
        try (PrintWriter writer = new PrintWriter(new File("students.txt"))) {
            for (Student student : students) {
                writer.println(student.name + "," + student.surname + "," + student.studentID + "," + student.major);
            }
        } catch (IOException e) {
            System.out.println("Error writing students information: " + e.getMessage());
        }
    }

    public static Book[] loadBooksFromFile() {
        Book[] books = new Book[4];
        try (Scanner scanner = new Scanner(new File("books.txt"))) {
            int index = 0;
            while (scanner.hasNextLine() && index < books.length) {
                String line = scanner.nextLine();
                String[] bookData = line.split(",");
                String title = bookData[0];
                String author = bookData[1];
                int pages = Integer.parseInt(bookData[2]);
                int year = Integer.parseInt(bookData[3]);
                books[index] = new Book(title, author, pages, year);
                index++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error reading books information: " + e.getMessage());
        }
        return books;
    }

    public static Student[] loadStudentsFromFile() {
        Student[] students = new Student[3];
        try (Scanner scanner = new Scanner(new File("students.txt"))) {
            int index = 0;
            while (scanner.hasNextLine() && index < students.length) {
                String line = scanner.nextLine();
                String[] studentData = line.split(",");
                String name = studentData[0];
                String surname = studentData[1];
                int studentID = Integer.parseInt(studentData[2]);
                String major = studentData[3];
                students[index] = new Student(name, surname, studentID, major);
                index++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error reading students information: " + e.getMessage());
        }
        return students;
    }

    public static void printBooks(Book[] books) {
        System.out.println("Books Information:");
        for (Book book : books) {
            System.out.println("Title: " + book.title + ", Author: " + book.author + ", Pages: " + book.pages + ", Year: " + book.year);
        }
    }

    public static void printStudents(Student[] students) {
        System.out.println("\nStudents Information:");
        for (Student student : students) {
            System.out.println("Name: " + student.name + ", Surname: " + student.surname + ", ID: " + student.studentID + ", Major: " + student.major);
        }
    }
}

class Book {
    String title;
    String author;
    int pages;
    int year;

    public Book(String title, String author, int pages, int year) {
        this.title = title;
        this.author = author;
        this.pages = pages;
        this.year = year;
    }
}

class Student {
    String name;
    String surname;
    int studentID;
    String major;

    public Student(String name, String surname, int studentID, String major) {
        this.name = name;
        this.surname = surname;
        this.studentID = studentID;
        this.major = major;
    }
}