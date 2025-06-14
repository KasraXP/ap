package exercises.ex7;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Library {
    private final String name;
    private final ArrayList<Book> books;
    private final ArrayList<Student> students;
    private final ArrayList<Librarian> librarians;
    private final ArrayList<Loan> loans;

    public Library(String name, ArrayList<Book> books, ArrayList<Student> students,
                   ArrayList<Librarian> librarians, ArrayList<Loan> loans) {
        this.name = name;
        this.books = books;
        this.students = students;
        this.librarians = librarians;
        this.loans = loans;
    }


    public Student registerNewStudent(Scanner scanner, StorageHandler storage) {
        System.out.println("\n=== Student Registration ===");

        System.out.print("First Name: ");
        String firstName = scanner.nextLine();

        System.out.print("Last Name: ");
        String lastName = scanner.nextLine();

        String studentId;
        while (true) {
            System.out.print("Student ID: ");
            studentId = scanner.nextLine();
            if (getStudentById(studentId) == null) {
                break;
            }
            System.out.println("This ID already exists! Please try another one.");
        }

        System.out.print("Major: ");
        String major = scanner.nextLine();

        Student newStudent = new Student(firstName, lastName, studentId, major, LocalDate.now());
        students.add(newStudent);
        storage.saveStudents(students);

        System.out.println("\nRegistration successful! Welcome to " + name);
        return newStudent;
    }

    public Student studentLogin(Scanner scanner) {
        System.out.print("\nEnter Student ID: ");
        String studentId = scanner.nextLine();

        Student student = getStudentById(studentId);
        if (student == null) {
            System.out.println("Student not found!");
            return null;
        }

        System.out.println("Welcome back, " + student.getFirstName() + "!");
        return student;
    }

    private Student getStudentById(String studentId) {
        for (Student student : students) {
            if (student.getStudentId().equals(studentId)) {
                return student;
            }
        }
        return null;
    }


    public void showAvailableBooks() {
        System.out.println("\nAvailable Books:");
        books.stream()
                .filter(book -> !book.getIsLoaned())
                .forEach(book -> System.out.println("- " + book.getTitle() + " by " + book.getAuthor()));
    }

    public void borrowBook(Student student, StorageHandler storage) {
        showAvailableBooks();

        System.out.print("\nEnter book title to borrow: ");
        Scanner scanner = new Scanner(System.in);
        String title = scanner.nextLine();

        Book book = findBookByTitle(title);
        if (book != null && !book.getIsLoaned()) {
            Loan newLoan = new Loan(book, student, librarians.get(0),
                    LocalDate.now(), LocalDate.now().plusWeeks(2), null);
            loans.add(newLoan);
            book.setIsLoaned(true);
            storage.saveLoans(loans);
            storage.saveBooks(books);
            System.out.println("Book borrowed successfully!");
        } else {
            System.out.println("Book not available for borrowing.");
        }
    }

    private Book findBookByTitle(String title) {
        return books.stream()
                .filter(b -> b.getTitle().equalsIgnoreCase(title))
                .findFirst()
                .orElse(null);
    }

}