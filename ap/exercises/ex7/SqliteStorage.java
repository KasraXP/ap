package exercises.ex7;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class SqliteStorage implements StorageHandler {
    private final String DB_URL = "jdbc:sqlite:library.db";

    @Override
    public void saveBooks(ArrayList<Book> books) {
    }

    @Override
    public ArrayList<Book> loadBooks() {
        ArrayList<Book> books = new ArrayList<>();
        return books;
    }

    @Override
    public void saveStudents(ArrayList<Student> students) {
    }

    @Override
    public ArrayList<Student> loadStudents() {
        ArrayList<Student> students = new ArrayList<>();
        return students;
    }

    @Override
    public void saveLibrarians(ArrayList<Librarian> librarians) {
    }

    @Override
    public ArrayList<Librarian> loadLibrarians() {
        ArrayList<Librarian> librarians = new ArrayList<>();
        return librarians;
    }

    @Override
    public void saveLoans(ArrayList<Loan> loans) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {

            stmt.execute("CREATE TABLE IF NOT EXISTS Loans (" +
                    "bookTitle TEXT, studentId TEXT, " +
                    "librarianId TEXT, loanDate TEXT, " +
                    "dueDate TEXT, returnDate TEXT)");

            stmt.execute("DELETE FROM Loans");

            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO Loans VALUES (?, ?, ?, ?, ?, ?)");

            for (Loan loan : loans) {
                ps.setString(1, loan.getBook().getTitle());
                ps.setString(2, loan.getStudent().getStudentId());
                ps.setString(3, loan.getLibrarian().getEmployeeId());
                ps.setString(4, loan.getLoanDate().toString());
                ps.setString(5, loan.getDueDate().toString());
                ps.setString(6, loan.getReturnDate() != null ?
                        loan.getReturnDate().toString() : null);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println("SQL Error saving loans: " + e.getMessage());
        }
    }

    @Override
    public ArrayList<Loan> loadLoans() {
        ArrayList<Loan> loans = new ArrayList<>();
        ArrayList<Book> books = loadBooks();
        ArrayList<Student> students = loadStudents();
        ArrayList<Librarian> librarians = loadLibrarians();

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Loans")) {

            while (rs.next()) {
                Book book = findBook(books, rs.getString("bookTitle"));
                Student student = findStudent(students, rs.getString("studentId"));
                Librarian librarian = findLibrarian(librarians, rs.getString("librarianId"));

                if (book != null && student != null && librarian != null) {
                    loans.add(new Loan(
                            book,
                            student,
                            librarian,
                            LocalDate.parse(rs.getString("loanDate")),
                            LocalDate.parse(rs.getString("dueDate")),
                            rs.getString("returnDate") != null ?
                                    LocalDate.parse(rs.getString("returnDate")) : null
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL Error loading loans: " + e.getMessage());
        }
        return loans;
    }

    @Override
    public void saveLoanRequests(ArrayList<LoanRequest> loanRequests) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {

            stmt.execute("CREATE TABLE IF NOT EXISTS LoanRequests (" +
                    "bookTitle TEXT, studentId TEXT, librarianId TEXT)");

            stmt.execute("DELETE FROM LoanRequests");

            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO LoanRequests VALUES (?, ?, ?)");

            for (LoanRequest request : loanRequests) {
                ps.setString(1, request.getBook().getTitle());
                ps.setString(2, request.getStudent().getStudentId());
                ps.setString(3, request.getAssignedLibrarian().getEmployeeId());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println("SQL Error saving loan requests: " + e.getMessage());
        }
    }

    @Override
    public ArrayList<LoanRequest> loadLoanRequests() {
        ArrayList<LoanRequest> requests = new ArrayList<>();
        ArrayList<Book> books = loadBooks();
        ArrayList<Student> students = loadStudents();
        ArrayList<Librarian> librarians = loadLibrarians();

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM LoanRequests")) {

            while (rs.next()) {
                Book book = findBook(books, rs.getString("bookTitle"));
                Student student = findStudent(students, rs.getString("studentId"));
                Librarian librarian = findLibrarian(librarians, rs.getString("librarianId"));

                if (book != null && student != null && librarian != null) {
                    requests.add(new LoanRequest(book, student, librarian));
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL Error loading loan requests: " + e.getMessage());
        }
        return requests;
    }

    private Book findBook(ArrayList<Book> books, String title) {
        return books.stream()
                .filter(b -> b.getTitle().equals(title))
                .findFirst()
                .orElse(null);
    }

    private Student findStudent(ArrayList<Student> students, String studentId) {
        return students.stream()
                .filter(s -> s.getStudentId().equals(studentId))
                .findFirst()
                .orElse(null);
    }

    private Librarian findLibrarian(ArrayList<Librarian> librarians, String employeeId) {
        return librarians.stream()
                .filter(l -> l.getEmployeeId().equals(employeeId))
                .findFirst()
                .orElse(null);
    }
}