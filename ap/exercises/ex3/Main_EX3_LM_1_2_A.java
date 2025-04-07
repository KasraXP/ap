
package exercises.ex3;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Main_EX3_LM_1_2_A {
    public static void main(String[] args) {


        Book book1 = new Book("Animal Farm", "George Orwell", 128, 1945);
        Book book2 = new Book("White Nights", "Fyodor Dostoevsky", 112, 1848);
        Book book3 = new Book("To Kill a Mockingbird", "Harper Lee", 281, 1960);
        Book book4 = new Book("1984", "George Orwell", 328, 1949);
        Student student1 = new Student("Helya", " Torabi", 403356187, "Dentistry Student");
        Student student2 = new Student("Mostafa", "Yari", 403564987, "Law Student");
        Student student3 = new Student("Morteza", "Safari", 403574690, "Computer Science Student");


        Book[] books = {book1, book2, book3, book4};
        Student[] students = {student1, student2, student3};


        try {
            PrintWriter bookWriter = new PrintWriter(new PrintWriter("books.txt"));

            for (Book book : books) {
                bookWriter.println(book.title + "," + book.author + "," + book.pages + "," + book.year);
            }
            bookWriter.close();

        } catch (IOException e) {
            System.out.println("Error writing books information" + e.getMessage());
        }

        try {
            PrintWriter studentWriter = new PrintWriter(new FileWriter("students.txt"));

            for (Student student : students) {
                studentWriter.println(student.name + "," + student.surename + "," + student.studentID + "," + student.major);
            }
            studentWriter.close();

        } catch (IOException e){
            System.out.println("Error writing students information" + e.getMessage() );
        }
    }


    public static class Student {
        String name;
        String surename;
        int studentID;
        String major;


        public Student(String name, String surename, int studentID, String major) {
            this.name = name;
            this.surename = surename;
            this.studentID = studentID;
            this.major = major;

        }
    }

    public static class Book {
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
}

