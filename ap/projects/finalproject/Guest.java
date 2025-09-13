package projects.finalproject;

import java.util.ArrayList;

class Guest {

    void printSimpleDetails(ArrayList<Student> students, ArrayList<Book> books, ArrayList<Loan> loans) {
        if (students.isEmpty()) {
            System.out.println("\nThere are no students in Central Library");
        } else if (students.size() == 1) {
            System.out.println("\nThere is one student in Central Library");
        } else
            System.out.println("\nThere are " + students.size() + " students in Central Library");

        if (books.isEmpty()) {
            System.out.println("\nThere are no books in Central Library");
        } else if (books.size() == 1) {
            System.out.println("\nThere is one book in Central Library");
        }else
            System.out.println("\nThere are " + books.size() + " books in Central Library");

        if (loans.isEmpty()) {
            System.out.println("\nThere are no loans in Central Library");
        } else if (loans.size() == 1) {
            System.out.println("\nThere is one loan in Central Library");
        }else
            System.out.println("\nThere are " + loans.size() + " loans in Central Library");
    }

}
