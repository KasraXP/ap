package exercises.ex7;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.util.ArrayList;

public class JsonStorage implements StorageHandler {
    private final Gson gson = new Gson();

    public void saveBooks(ArrayList<Book> books) {
        try (FileWriter writer = new FileWriter("Books.json")) {
            gson.toJson(books, writer);
        } catch (IOException e) {
            System.err.println("Error saving books: " + e.getMessage());
        }
    }

    public ArrayList<Book> loadBooks() {
        try (Reader reader = new FileReader("Books.json")) {
            return gson.fromJson(reader, new TypeToken<ArrayList<Book>>(){}.getType());
        } catch (IOException e) {
            System.err.println("Error loading books: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void saveStudents(ArrayList<Student> students) {
        try (FileWriter writer = new FileWriter("Students.json")) {
            gson.toJson(students, writer);
        } catch (IOException e) {
            System.err.println("Error saving students: " + e.getMessage());
        }
    }

    public ArrayList<Student> loadStudents() {
        try (Reader reader = new FileReader("Students.json")) {
            return gson.fromJson(reader, new TypeToken<ArrayList<Student>>(){}.getType());
        } catch (IOException e) {
            System.err.println("Error loading students: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void saveLibrarians(ArrayList<Librarian> librarians) {
        try (FileWriter writer = new FileWriter("Librarians.json")) {
            gson.toJson(librarians, writer);
        } catch (IOException e) {
            System.err.println("Error saving librarians: " + e.getMessage());
        }
    }

    public ArrayList<Librarian> loadLibrarians() {
        try (Reader reader = new FileReader("Librarians.json")) {
            return gson.fromJson(reader, new TypeToken<ArrayList<Librarian>>(){}.getType());
        } catch (IOException e) {
            System.err.println("Error loading librarians: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void saveLoans(ArrayList<Loan> loans) {
        try (FileWriter writer = new FileWriter("Loans.json")) {
            gson.toJson(loans, writer);
        } catch (IOException e) {
            System.err.println("Error saving loans: " + e.getMessage());
        }
    }

    public ArrayList<Loan> loadLoans() {
        try (Reader reader = new FileReader("Loans.json")) {
            return gson.fromJson(reader, new TypeToken<ArrayList<Loan>>(){}.getType());
        } catch (IOException e) {
            System.err.println("Error loading loans: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void saveLoanRequests(ArrayList<LoanRequest> loanRequests) {
        try (FileWriter writer = new FileWriter("LoanRequests.json")) {
            gson.toJson(loanRequests, writer);
        } catch (IOException e) {
            System.err.println("Error saving loan requests: " + e.getMessage());
        }
    }

    public ArrayList<LoanRequest> loadLoanRequests() {
        try (Reader reader = new FileReader("LoanRequests.json")) {
            return gson.fromJson(reader, new TypeToken<ArrayList<LoanRequest>>(){}.getType());
        } catch (IOException e) {
            System.err.println("Error loading loan requests: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}