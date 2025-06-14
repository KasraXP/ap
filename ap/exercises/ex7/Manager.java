package exercises.ex7;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
import java.time.LocalDate;


public class Manager {
    private final String firstName;
    private final String lastName;
    private final String qualificationLevel;

    public Manager(String firstName, String lastName, String qualificationLevel) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.qualificationLevel = qualificationLevel;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getQualificationLevel() {
        return qualificationLevel;
    }

    public void addNewLibrarian(ArrayList<Librarian> librarians, StorageHandler storage) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n--- Add New Librarian ---");
        System.out.print("First Name: ");
        String firstName = scanner.nextLine();

        System.out.print("Last Name: ");
        String lastName = scanner.nextLine();

        System.out.print("Employee ID: ");
        String employeeId = scanner.nextLine();

        Librarian newLibrarian = new Librarian(firstName, lastName, employeeId);
        librarians.add(newLibrarian);
        storage.saveLibrarians(librarians);

        System.out.println("Librarian added successfully!");
    }

    public void generateLibraryReport(ArrayList<Book> books, ArrayList<Student> students,
                                      ArrayList<Loan> loans) {
        System.out.println("\n=== Library Management Report ===");

        System.out.println("\n--- Book Statistics ---");
        System.out.println("Total Books: " + books.size());
        System.out.println("Available Books: " +
                books.stream().filter(book -> !book.getIsLoaned()).count());
        System.out.println("Borrowed Books: " +
                books.stream().filter(Book::getIsLoaned).count());

        System.out.println("\n--- Student Statistics ---");
        System.out.println("Total Students: " + students.size());

        System.out.println("\n--- Loan Statistics ---");
        System.out.println("Active Loans: " +
                loans.stream().filter(loan -> loan.getReturnDate() == null).count());
        System.out.println("Overdue Loans: " +
                loans.stream().filter(Loan::isOverdue).count());

        System.out.println("\n--- Top 5 Most Borrowed Books ---");
        books.stream()
                .sorted(Comparator.comparingInt(Book::getLoanCount).reversed())
                .limit(5)
                .forEach(book -> System.out.println(
                        book.getTitle() + " (" + book.getLoanCount() + " loans)"));

        System.out.println("\n--- Top 5 Most Active Students ---");
        students.stream()
                .sorted((s1, s2) -> Long.compare(
                        loans.stream().filter(l -> l.getStudent().equals(s2)).count(),
                        loans.stream().filter(l -> l.getStudent().equals(s1)).count()))
                .limit(5)
                .forEach(student -> System.out.println(
                        student.getFirstName() + " " + student.getLastName() +
                                " (" + loans.stream().filter(l -> l.getStudent().equals(student)).count() + " loans)"));
    }

    public void viewLateReturns(ArrayList<Loan> loans) {
        System.out.println("\n=== Late Returns ===");
        long overdueCount = loans.stream()
                .filter(Loan::isOverdue)
                .peek(loan -> System.out.println(
                        "Book: " + loan.getBook().getTitle() +
                                "\nBorrower: " + loan.getStudent().getFirstName() + " " + loan.getStudent().getLastName() +
                                "\nDue Date: " + loan.getDueDate() +
                                "\nDays Overdue: " + (loan.getReturnDate() != null ?
                                loan.getReturnDate().minusDays(loan.getDueDate().getDayOfMonth()).getDayOfMonth() :
                                LocalDate.now().minusDays(loan.getDueDate().getDayOfMonth()).getDayOfMonth()) +
                                "\n----------------------"))
                .count();

        if (overdueCount == 0) {
            System.out.println("No late returns found!");
        }
    }

    public void librarianPerformanceReport(ArrayList<Librarian> librarians, ArrayList<Loan> loans) {
        System.out.println("\n=== Librarian Performance Report ===");

        librarians.forEach(librarian -> {
            long processedLoans = loans.stream()
                    .filter(loan -> loan.getLibrarian().equals(librarian))
                    .count();

            System.out.println(
                    librarian.getFirstName() + " " + librarian.getLastName() +
                            " (" + librarian.getEmployeeId() + ")" +
                            "\nTotal Loans Processed: " + processedLoans +
                            "\n----------------------");
        });
    }
}
