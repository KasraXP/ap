package projects.finalproject;

import java.util.ArrayList;

class Guest {

    void printTheNumberOfStudents(ArrayList<Student> students) {
        if (students.isEmpty()) {
            System.out.println("\nThere is no students in Central Library");
        } else if (students.size() == 1) {
            System.out.println("\nThere is one student in Central Library");
        } else
            System.out.println("\nThere are " + students.size() + " students in Central Library");
    }



}
