package projects.finalproject;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class SaveToFile {

    void saveStudents(ArrayList<Student> students) {
        try {
            PrintWriter writer = new PrintWriter("Students.txt.file");
            for (Student student : students) {
                writer.println(student.toString());
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
                writer.println(librarian.toString());
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
                writer.println(book.toString());
            }
            writer.close();

        } catch (IOException e) {
            System.out.println("Error while saving books file" + e.getMessage());

        }
    }

}
