package exercises.ex3.Main;

import java.util.Scanner;

public class EX3_LM_1_1 {
    public static void main(String[] args) {

        Book book1 = new Book("Animal Farm", "George Orwell", 128, 1945);
        Book book2 = new Book("White Nights", "Fyodor Dostoevsky", 1848, 112);
        Student student1 = new Student("Helya", " Torabi", 403356187, "Dentistry Student");
        Student student2 = new Student("Mostafa", "Yari", 403564987, "Law Student");



        System.out.println("choose your option");
        System.out.println();
        System.out.println("1- Add a book");
        System.out.println("2- Add a student");


        Scanner input = new Scanner(System.in);

        while (true) {
            int option = input.nextInt();
            switch (option) {

                case 1:
                    System.out.println("Enter the title of the book");
                    String title = input.next();
                    System.out.println("Enter the author of the book");
                    String author = input.next();
                    System.out.println("Enter the pages of the book");
                    int pages = input.nextInt();
                    System.out.println("Enter the year of the book");
                    int year = input.nextInt();
                    Book newbook = new Book(title, author, pages, year);
                    System.out.println(newbook.title + " " + newbook.author + " " + newbook.pages + " " + newbook.year);
                    break;

                case 2:
                    System.out.println("Enter the name of the student");
                    String name = input.next();
                    System.out.println("Enter the surename of the student");
                    String surename = input.next();
                    System.out.println("Enter the student ID of the student");
                    int studentID = input.nextInt();
                    System.out.println("Enter the major of the student");
                    String major = input.next();
                    Student newstudent = new Student(name, surename, studentID, major);
                    System.out.println();
                    System.out.println(newstudent.name + " " + newstudent.surename + " " + newstudent.studentID);


            }

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

    }



