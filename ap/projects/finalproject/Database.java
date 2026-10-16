package projects.finalproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

public class Database {
    private static final String DB_URL = "jdbc:sqlite:library.db";

    private Connection connection;

    void init() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DB_URL);
            createTables();
        } catch (ClassNotFoundException e) {
            System.out.println("SQLite driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Error while connecting to database: " + e.getMessage());
        }
    }

    private void createTables() throws SQLException {
        String[] statements = {
                "CREATE TABLE IF NOT EXISTS students (" +
                        "username TEXT PRIMARY KEY, " +
                        "password TEXT NOT NULL, " +
                        "major TEXT NOT NULL, " +
                        "membership_date TEXT NOT NULL, " +
                        "is_active INTEGER NOT NULL DEFAULT 1)",

                "CREATE TABLE IF NOT EXISTS librarians (" +
                        "username TEXT PRIMARY KEY, " +
                        "password TEXT NOT NULL, " +
                        "given_count INTEGER NOT NULL DEFAULT 0, " +
                        "received_count INTEGER NOT NULL DEFAULT 0)",

                "CREATE TABLE IF NOT EXISTS books (" +
                        "title TEXT PRIMARY KEY, " +
                        "author TEXT NOT NULL, " +
                        "pages INTEGER NOT NULL, " +
                        "published_year INTEGER NOT NULL, " +
                        "is_loaned INTEGER NOT NULL DEFAULT 0, " +
                        "loan_count INTEGER NOT NULL DEFAULT 0, " +
                        "librarian TEXT NOT NULL)",

                "CREATE TABLE IF NOT EXISTS loan_requests (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "book_title TEXT NOT NULL, " +
                        "student_password TEXT NOT NULL, " +
                        "duration_days INTEGER NOT NULL, " +
                        "status TEXT NOT NULL)",

                "CREATE TABLE IF NOT EXISTS loans (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "book_title TEXT NOT NULL, " +
                        "student_password TEXT NOT NULL, " +
                        "loan_date TEXT NOT NULL, " +
                        "due_date TEXT NOT NULL, " +
                        "return_date TEXT)"
        };

        try (Statement statement = connection.createStatement()) {
            for (String sql : statements) {
                statement.execute(sql);
            }
        }
    }

    void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("Error while closing database: " + e.getMessage());
        }
    }

    private void beginTransaction() throws SQLException {
        connection.setAutoCommit(false);
    }

    private void commit() throws SQLException {
        connection.commit();
        connection.setAutoCommit(true);
    }

    private void rollback() {
        try {
            connection.rollback();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            System.out.println("Error while rollback: " + e.getMessage());
        }
    }

    ArrayList<Student> loadStudents() {
        ArrayList<Student> students = new ArrayList<>();
        String sql = "SELECT username, password, major, membership_date, is_active FROM students";

        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {

            while (rs.next()) {
                Student student = new Student(
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("major"),
                        LocalDate.parse(rs.getString("membership_date")));
                student.setActive(rs.getInt("is_active") == 1);
                students.add(student);
            }
        } catch (SQLException e) {
            System.out.println("Error while loading students: " + e.getMessage());
        }

        return students;
    }

    ArrayList<Librarian> loadLibrarians() {
        ArrayList<Librarian> librarians = new ArrayList<>();
        String sql = "SELECT username, password, given_count, received_count FROM librarians";

        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {

            while (rs.next()) {
                librarians.add(new Librarian(
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getInt("given_count"),
                        rs.getInt("received_count")));
            }
        } catch (SQLException e) {
            System.out.println("Error while loading librarians: " + e.getMessage());
        }

        return librarians;
    }

    ArrayList<Book> loadBooks() {
        ArrayList<Book> books = new ArrayList<>();
        String sql = "SELECT title, author, pages, published_year, is_loaned, loan_count, librarian FROM books";

        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {

            while (rs.next()) {
                books.add(new Book(
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getInt("pages"),
                        rs.getInt("published_year"),
                        rs.getInt("is_loaned") == 1,
                        rs.getInt("loan_count"),
                        rs.getString("librarian")));
            }
        } catch (SQLException e) {
            System.out.println("Error while loading books: " + e.getMessage());
        }

        return books;
    }

    ArrayList<LoanRequest> loadLoanRequests(ArrayList<Book> books, ArrayList<Student> students) {
        ArrayList<LoanRequest> loanRequests = new ArrayList<>();
        String sql = "SELECT book_title, student_password, duration_days, status FROM loan_requests";

        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {

            while (rs.next()) {
                LoanRequest request = buildLoanRequest(
                        rs.getString("book_title"),
                        rs.getString("student_password"),
                        rs.getInt("duration_days"),
                        rs.getString("status"),
                        books,
                        students);
                if (request != null) {
                    loanRequests.add(request);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error while loading loan requests: " + e.getMessage());
        }

        return loanRequests;
    }

    ArrayList<Loan> loadLoans(ArrayList<Book> books, ArrayList<Student> students) {
        ArrayList<Loan> loans = new ArrayList<>();
        String sql = "SELECT book_title, student_password, loan_date, due_date, return_date FROM loans";

        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {

            while (rs.next()) {
                String returnDateText = rs.getString("return_date");
                LocalDate returnDate = returnDateText == null ? null : LocalDate.parse(returnDateText);

                Loan loan = buildLoan(
                        rs.getString("book_title"),
                        rs.getString("student_password"),
                        LocalDate.parse(rs.getString("loan_date")),
                        LocalDate.parse(rs.getString("due_date")),
                        returnDate,
                        books,
                        students);
                if (loan != null) {
                    loans.add(loan);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error while loading loans: " + e.getMessage());
        }

        return loans;
    }

    private LoanRequest buildLoanRequest(String bookTitle, String studentPassword, int durationDays,
                                         String status, ArrayList<Book> books, ArrayList<Student> students) {
        Book book = findBookByTitle(books, bookTitle);
        Student student = findStudentByPassword(students, studentPassword);

        if (book == null || student == null) {
            System.out.println("Book or student not found for loan request: " + bookTitle + "," + studentPassword);
            return null;
        }

        LoanRequest request = new LoanRequest(book, student, durationDays);
        request.setStatus(status);
        return request;
    }

    private Loan buildLoan(String bookTitle, String studentPassword, LocalDate loanDate,
                           LocalDate dueDate, LocalDate returnDate,
                           ArrayList<Book> books, ArrayList<Student> students) {
        Book book = findBookByTitle(books, bookTitle);
        Student student = findStudentByPassword(students, studentPassword);

        if (book == null || student == null) {
            System.out.println("Book or student not found for loan: " + bookTitle + "," + studentPassword);
            return null;
        }

        return new Loan(book, student, loanDate, dueDate, returnDate);
    }

    private Book findBookByTitle(ArrayList<Book> books, String title) {
        for (Book book : books) {
            if (book.getTitle().equals(title)) {
                return book;
            }
        }
        return null;
    }

    private Student findStudentByPassword(ArrayList<Student> students, String password) {
        for (Student student : students) {
            if (student.getPassword().equals(password)) {
                return student;
            }
        }
        return null;
    }

    void saveStudents(ArrayList<Student> students) {
        String deleteSql = "DELETE FROM students";
        String insertSql = "INSERT INTO students (username, password, major, membership_date, is_active) VALUES (?, ?, ?, ?, ?)";

        try {
            beginTransaction();

            try (Statement delete = connection.createStatement();
                 PreparedStatement insert = connection.prepareStatement(insertSql)) {

                delete.executeUpdate(deleteSql);

                for (Student student : students) {
                    insert.setString(1, student.getUserName());
                    insert.setString(2, student.getPassword());
                    insert.setString(3, student.getMajor());
                    insert.setString(4, student.getMembershipDate().toString());
                    insert.setInt(5, student.isActive() ? 1 : 0);
                    insert.addBatch();
                }
                insert.executeBatch();
            }

            commit();
        } catch (SQLException e) {
            rollback();
            System.out.println("Error while saving students: " + e.getMessage());
        }
    }

    void saveLibrarians(ArrayList<Librarian> librarians) {
        String deleteSql = "DELETE FROM librarians";
        String insertSql = "INSERT INTO librarians (username, password, given_count, received_count) VALUES (?, ?, ?, ?)";

        try {
            beginTransaction();

            try (Statement delete = connection.createStatement();
                 PreparedStatement insert = connection.prepareStatement(insertSql)) {

                delete.executeUpdate(deleteSql);

                for (Librarian librarian : librarians) {
                    insert.setString(1, librarian.getUserName());
                    insert.setString(2, librarian.getPassword());
                    insert.setInt(3, librarian.getGivenCount());
                    insert.setInt(4, librarian.getReceivedCount());
                    insert.addBatch();
                }
                insert.executeBatch();
            }

            commit();
        } catch (SQLException e) {
            rollback();
            System.out.println("Error while saving librarians: " + e.getMessage());
        }
    }

    void saveBooks(ArrayList<Book> books) {
        String deleteSql = "DELETE FROM books";
        String insertSql = "INSERT INTO books (title, author, pages, published_year, is_loaned, loan_count, librarian) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            beginTransaction();

            try (Statement delete = connection.createStatement();
                 PreparedStatement insert = connection.prepareStatement(insertSql)) {

                delete.executeUpdate(deleteSql);

                for (Book book : books) {
                    insert.setString(1, book.getTitle());
                    insert.setString(2, book.getAuthor());
                    insert.setInt(3, book.getPages());
                    insert.setInt(4, book.getPublishedYear());
                    insert.setInt(5, book.getIsLoaned() ? 1 : 0);
                    insert.setInt(6, book.getLoanCount());
                    insert.setString(7, book.getTheLibrarian());
                    insert.addBatch();
                }
                insert.executeBatch();
            }

            commit();
        } catch (SQLException e) {
            rollback();
            System.out.println("Error while saving books: " + e.getMessage());
        }
    }

    void saveLoanRequests(ArrayList<LoanRequest> loanRequests) {
        String deleteSql = "DELETE FROM loan_requests";
        String insertSql = "INSERT INTO loan_requests (book_title, student_password, duration_days, status) VALUES (?, ?, ?, ?)";

        try {
            beginTransaction();

            try (Statement delete = connection.createStatement();
                 PreparedStatement insert = connection.prepareStatement(insertSql)) {

                delete.executeUpdate(deleteSql);

                for (LoanRequest request : loanRequests) {
                    insert.setString(1, request.getBook().getTitle());
                    insert.setString(2, request.getStudent().getPassword());
                    insert.setInt(3, request.getLoanDurationDays());
                    insert.setString(4, request.getStatus());
                    insert.addBatch();
                }
                insert.executeBatch();
            }

            commit();
        } catch (SQLException e) {
            rollback();
            System.out.println("Error while saving loan requests: " + e.getMessage());
        }
    }

    void saveLoans(ArrayList<Loan> loans) {
        String deleteSql = "DELETE FROM loans";
        String insertSql = "INSERT INTO loans (book_title, student_password, loan_date, due_date, return_date) VALUES (?, ?, ?, ?, ?)";

        try {
            beginTransaction();

            try (Statement delete = connection.createStatement();
                 PreparedStatement insert = connection.prepareStatement(insertSql)) {

                delete.executeUpdate(deleteSql);

                for (Loan loan : loans) {
                    insert.setString(1, loan.getBook().getTitle());
                    insert.setString(2, loan.getStudent().getPassword());
                    insert.setString(3, loan.getLoanDate().toString());
                    insert.setString(4, loan.getDueDate().toString());
                    if (loan.getReturnDate() == null) {
                        insert.setString(5, null);
                    } else {
                        insert.setString(5, loan.getReturnDate().toString());
                    }
                    insert.addBatch();
                }
                insert.executeBatch();
            }

            commit();
        } catch (SQLException e) {
            rollback();
            System.out.println("Error while saving loans: " + e.getMessage());
        }
    }
}
