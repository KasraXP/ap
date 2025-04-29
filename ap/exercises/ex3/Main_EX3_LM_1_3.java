package exercises.ex3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main_EX3_LM_1_3 {

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

        Books[] loadedBooks = loadBooksFromFile();
        printBooks(loadedBooks);

        Students[] loadedStudents = loadStudentsFromFile();
        printStudents(loadedStudents);

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

    public static void printBooks(Books[] books) {
        System.out.println("Books Information:");
        for (Books book : books) {
            System.out.println("Title: " + book.title + ", Author: " + book.author + ", Pages: " + book.pages + ", Year: " + book.year);
        }
    }

    public static void printStudents(Students[] students) {
        System.out.println("\nStudents Information:");
        for (Students student : students) {
            System.out.println("Name: "  + student.name + ", Surname: " + student.surname + ", ID: " + student.studentID + ", Major: " + student.major);
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

