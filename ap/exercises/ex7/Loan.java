package exercises.ex7;

import java.time.LocalDate;
import java.util.Objects;

public class Loan {
    private final Book book;
    private final Student student;
    private final Librarian librarian;
    private final LocalDate loanDate;
    private final LocalDate dueDate;
    private LocalDate returnDate;

    public Loan(Book book, Student student, Librarian librarian, LocalDate loanDate, LocalDate dueDate, LocalDate returnDate) {
        this.book = book;
        this.student = student;
        this.librarian = librarian;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
    }

    public Book getBook() { return book; }
    public Student getStudent() { return student; }
    public Librarian getLibrarian() { return librarian; }
    public LocalDate getLoanDate() { return loanDate; }
    public LocalDate getDueDate() { return dueDate; }
    public LocalDate getReturnDate() { return returnDate; }
    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }
    public boolean isOverdue() {
        return returnDate == null ? LocalDate.now().isAfter(dueDate) : returnDate.isAfter(dueDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Loan loan = (Loan) o;
        return book.equals(loan.book) &&
                student.equals(loan.student) &&
                loanDate.equals(loan.loanDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(book, student, loanDate);
    }
}