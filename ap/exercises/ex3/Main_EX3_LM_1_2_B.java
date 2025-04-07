package exercises.ex3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main_EX3_LM_1_2_B {

    public static void main(String[] args) {



        Book[] books = new Book[3];
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
            e.printStackTrace();
        }


        System.out.println("Books Information:");
        for (Book book : books) {
            System.out.println("Title: " + book.title + ", Author: " + book.author + ", Pages: " + book.pages + ", Year: " + book.year);
        }


        Student[] students = new Student[3];
        try (Scanner scanner = new Scanner(new File("students.txt"))) {
            int index = 0;
            while (scanner.hasNextLine() && index < students.length) {
                String line = scanner.nextLine();
                String[] studentData = line.split(",");
                String name = studentData[0];
                String surename = studentData[1];
                int studentID = Integer.parseInt(studentData[2]);
                String major = studentData[3];
                students[index] = new Student(name, surename, studentID, major);
                index++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        System.out.println("\nStudents Information:");
        for (Student student : students) {
            System.out.println("Name: " + student.name + ", surename: " + student.surename + ", ID: " + student.studentID + ", Major: " + student.major);
        }
    }

    static class Book {
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

    static class Student {
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
}