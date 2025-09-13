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
            String password= parts[1];
            int givenCount = Integer.parseInt(parts[2]);
            int receivedCount = Integer.parseInt(parts[3]);

            return new Librarian(fullName, password, givenCount, receivedCount);
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
        String theLibrarian = parts[6];
        return new Book(title, author, pages, publishedDate, isLoaned, loanCount, theLibrarian);
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

    public LoanRequest fromStringLoanRequest(String line, ArrayList<Book> books, ArrayList<Student> students) {
        if (line == null || line.trim().isEmpty()) {
            return null;
        }

        try {
            String[] parts = line.split(",");
            if (parts.length < 4) {
                System.out.println("Invalid loan request data: " + line);
                return null;
            }

            String bookTitle = parts[0];
            String studentId = parts[1];
            int loanDurationDays = Integer.parseInt(parts[2]);
            String status = parts[3];

            Book book = null;
            Student student = null;

            for (Book b : books) {
                if (b.getTitle().equals(bookTitle)) {
                    book = b;
                    break;
                }
            }

            for (Student s : students) {
                if (s.getPassword().equals(studentId)) {
                    student = s;
                    break;
                }
            }

            if (book != null && student != null) {
                LoanRequest request = new LoanRequest(book, student, loanDurationDays);
                request.setStatus(status);
                return request;
            } else {
                System.out.println("Book or student not found for loan request: " + line);
                return null;
            }

        } catch (Exception e) {
            System.out.println("Error parsing loan request: " + line + " - " + e.getMessage());
            return null;
        }
    }

    public ArrayList<LoanRequest> loadLoanRequests(String fileName, ArrayList<Book> books, ArrayList<Student> students) {
        ArrayList<LoanRequest> loanRequests = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    LoanRequest loanRequest = fromStringLoanRequest(line, books, students);
                    if (loanRequest != null) {
                        loanRequests.add(loanRequest);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error while loading loan requests file: " + e.getMessage());
        }

        return loanRequests;
    }

    public Loan fromStringLoans(String line, ArrayList<Book> books, ArrayList<Student> students) {

        String[] parts = line.split(",");

        if (parts.length < 4) {
            System.out.println("Invalid loan entry: " + line);
            return null;
        }

        String bookTitle = parts[0];
        String studentPass = parts[1];
        LocalDate loanDate = LocalDate.parse(parts[2]);
        LocalDate dueDate = LocalDate.parse(parts[3]);

        LocalDate returnDate = null;
        if (parts.length >= 6 && !parts[4].equals("null")) {
            returnDate = LocalDate.parse(parts[4]);
        }

        Book book = null;
        Student student = null;

        for (Book b : books) {
            if (b.getTitle().equals(bookTitle)) {
                book = b;
                break;
            }
        }

        for (Student s : students) {
            if (s.getPassword().equals(studentPass)) {
                student = s;
                break;
            }
        }


        if (book != null && student != null) {
            return new Loan(book, student, loanDate, dueDate, returnDate);
        } else {
            System.out.println("Missing data for loan: " + line);
            return null;
        }
    }

    public ArrayList<Loan> loadLoans(String fileName, ArrayList<Book> books, ArrayList<Student> students) {

        ArrayList<Loan> loans = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Loan loan = fromStringLoans(line, books, students);
                if (loan != null) {
                    loans.add(loan);
                }
            }
        } catch (IOException e) {
            System.out.println("Error while loading file" + e.getMessage());
        }

        return loans;
    }


}