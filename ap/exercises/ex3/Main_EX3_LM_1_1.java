package exercises.ex3;

public class Main_EX3_LM_1_1 {
    public static void main(String[] args) {

        Book book1 = new Book("Animal Farm", "George Orwell", 128, 1945);
        Book book2 = new Book("White Nights", "Fyodor Dostoevsky", 112, 1848);
        Student student1 = new Student("Helya", " Torabi", 403356187, "Dentistry Student");
        Student student2 = new Student("Mostafa", "Yari", 403564987, "Law Student");

        System.out.println("Book 1: ");
        System.out.println(" Title : " + book1.title);
        System.out.println(" Author : " + book1.author);
        System.out.println(" Pages : " + book1.pages);
        System.out.println(" Published year :" + book1.year);
        System.out.println();

        System.out.println("Book 2: ");
        System.out.println(" Title : " + book2.title);
        System.out.println(" Author : " + book2.author);
        System.out.println(" Pages : " + book2.pages);
        System.out.println("Published year :" + book2.year);
        System.out.println();

        System.out.println("Student 1: ");
        System.out.println(" Name : " + student1.name);
        System.out.println(" Sure name : " + student1.surename);
        System.out.println(" ID : " + student1.studentID);
        System.out.println(" Major: " + student1.major);
        System.out.println();

        System.out.println("Student 2: ");
        System.out.println("Name :" + student2.name);
        System.out.println(" Sure name : " + student2.surename);
        System.out.println(" ID : " + student2.studentID);
        System.out.println(" Major: " + student2.major);

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

