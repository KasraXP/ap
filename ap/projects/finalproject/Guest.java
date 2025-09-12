package projects.finalproject;

import java.util.ArrayList;

public class Guest {

    void printTheNumberOfStudents(ArrayList<Student> students) {
        if (students.isEmpty()) {
            System.out.println("\nThere are no students in the database");
        } else if (students.size() == 1) {
            System.out.println("\nThere is one student in the database");
        } else
            System.out.println("\nThere are " + students.size() + " students in the database");
    }

    void printBookInfoForGuest(Book book) {
        System.out.println("\nBook title: " + book.getTitle());
        System.out.println("Book author: " + book.getAuthor());
        System.out.println("Book published year: " + book.getPublishedYear());
        System.out.println("Book pages: " + book.getPages());
        System.out.println("___________________________");
    }


}
