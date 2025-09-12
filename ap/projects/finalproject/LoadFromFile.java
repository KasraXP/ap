package projects.finalproject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class LoadFromFile {

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
            System.out.println("Error while loading " + e.getMessage());
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