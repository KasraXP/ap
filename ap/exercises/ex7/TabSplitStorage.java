package exercises.ex7;
import java.io.*;
import java.util.ArrayList;
import java.time.LocalDate;

public class TabSplitStorage implements StorageHandler {

    @Override
    public void saveBooks(ArrayList<Book> books) {
        try (PrintWriter writer = new PrintWriter("books.txt")) {
            for (Book book : books) {
                writer.println(String.join("\t",
                        book.getTitle(),
                        book.getAuthor(),
                        String.valueOf(book.getPages()),
                        String.valueOf(book.getPublishedYear()),
                        String.valueOf(book.getIsLoaned()),
                        String.valueOf(book.getLoanCount())
                ));
            }
        } catch (IOException e) {
            System.err.println("Error saving books: " + e.getMessage());
        }
    }

    @Override
    public ArrayList<Book> loadBooks() {
        ArrayList<Book> books = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("books.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\t");
                if (parts.length == 6) {
                    books.add(new Book(
                            parts[0],
                            parts[1],
                            Integer.parseInt(parts[2]),
                            Integer.parseInt(parts[3]),
                            Boolean.parseBoolean(parts[4]),
                            Integer.parseInt(parts[5])
                    ));
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading books: " + e.getMessage());
        }
        return books;
    }

    @Override
    public void saveStudents(ArrayList<Student> students) {
        try (PrintWriter writer = new PrintWriter("students.txt")) {
            for (Student student : students) {
                writer.println(String.join("\t",
                        student.getFirstName(),
                        student.getLastName(),
                        student.getStudentId(),
                        student.getMajor(),
                        student.getMembershipDate().toString()
                ));
            }
        } catch (IOException e) {
            System.err.println("Error saving students: " + e.getMessage());
        }
    }

    @Override
    public ArrayList<Student> loadStudents() {
        ArrayList<Student> students = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("students.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\t");
                if (parts.length == 5) {
                    students.add(new Student(
                            parts[0],
                            parts[1],
                            parts[2],
                            parts[3],
                            LocalDate.parse(parts[4])
                    ));
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading students: " + e.getMessage());
        }
        return students;
    }

    @Override
    public void saveLibrarians(ArrayList<Librarian> librarians) {
        try (PrintWriter writer = new PrintWriter("librarians.txt")) {
            for (Librarian librarian : librarians) {
                writer.println(String.join("\t",
                        librarian.getFirstName(),
                        librarian.getLastName(),
                        librarian.getEmployeeId()
                ));
            }
        } catch (IOException e) {
            System.err.println("Error saving librarians: " + e.getMessage());
        }
    }

    @Override
    public ArrayList<Librarian> loadLibrarians() {
        ArrayList<Librarian> librarians = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("librarians.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\t");
                if (parts.length == 3) {
                    librarians.add(new Librarian(
                            parts[0],
                            parts[1],
                            parts[2]
                    ));
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading librarians: " + e.getMessage());
        }
        return librarians;
    }

    @Override
    public void saveLoans(ArrayList<Loan> loans) {
        try (PrintWriter writer = new PrintWriter("loans.txt")) {
            for (Loan loan : loans) {
                writer.println(String.join("\t",
                        loan.getBook().getTitle(),
                        loan.getStudent().getStudentId(),
                        loan.getLibrarian().getEmployeeId(),
                        loan.getLoanDate().toString(),
                        loan.getDueDate().toString(),
                        loan.getReturnDate() != null ? loan.getReturnDate().toString() : "null"
                ));
            }
        } catch (IOException e) {
            System.err.println("Error saving loans: " + e.getMessage());
        }
    }

    @Override
    public ArrayList<Loan> loadLoans() {
        ArrayList<Loan> loans = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("loans.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\t");
                if (parts.length == 6) {
                    Book book = new Book(parts[0], "", 0, 0, false, 0);
                    Student student = new Student("", "", parts[1], "", LocalDate.now());
                    Librarian librarian = new Librarian("", "", parts[2]);
                    LocalDate returnDate = parts[5].equals("null") ? null : LocalDate.parse(parts[5]);

                    loans.add(new Loan(
                            book,
                            student,
                            librarian,
                            LocalDate.parse(parts[3]),
                            LocalDate.parse(parts[4]),
                            returnDate
                    ));
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading loans: " + e.getMessage());
        }
        return loans;
    }

    @Override
    public void saveLoanRequests(ArrayList<LoanRequest> loanRequests) {
        try (PrintWriter writer = new PrintWriter("loan_requests.txt")) {
            for (LoanRequest request : loanRequests) {
                writer.println(String.join("\t",
                        request.getBook().getTitle(),
                        request.getStudent().getStudentId(),
                        request.getAssignedLibrarian().getEmployeeId()
                ));
            }
        } catch (IOException e) {
            System.err.println("Error saving loan requests: " + e.getMessage());
        }
    }

    @Override
    public ArrayList<LoanRequest> loadLoanRequests() {
        ArrayList<LoanRequest> requests = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("loan_requests.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\t");
                if (parts.length == 3) {
                    Book book = new Book(parts[0], "", 0, 0, false, 0);
                    Student student = new Student("", "", parts[1], "", LocalDate.now());
                    Librarian librarian = new Librarian("", "", parts[2]);

                    requests.add(new LoanRequest(book, student, librarian));
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading loan requests: " + e.getMessage());
        }
        return requests;
    }
}