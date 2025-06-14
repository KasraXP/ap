package exercises.ex7;

import java.util.ArrayList;

public interface StorageHandler {
    void saveBooks(ArrayList<Book> books);
    ArrayList<Book> loadBooks();
    void saveStudents(ArrayList<Student> students);
    ArrayList<Student> loadStudents();
    void saveLibrarians(ArrayList<Librarian> librarians);
    ArrayList<Librarian> loadLibrarians();
    void saveLoans(ArrayList<Loan> loans);
    ArrayList<Loan> loadLoans();
    void saveLoanRequests(ArrayList<LoanRequest> loanRequests);
    ArrayList<LoanRequest> loadLoanRequests();
}