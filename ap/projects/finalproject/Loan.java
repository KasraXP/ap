package projects.finalproject;
import java.time.LocalDate;

class Loan implements Storable{
    private final Book book;
    private final Student student;
    private final LocalDate loanDate;
    private final LocalDate dueDate;
    private LocalDate returnDate;

    Loan(Book book, Student student, LocalDate loanDate, LocalDate dueDate, LocalDate returnDate) {
        this.book = book;
        this.student = student;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
    }

    public Book getBook() {
        return book;
    }

    public Student getStudent() {
        return student;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public String toString() {
        return book.getTitle() + "," + student.getPassword() + "," + loanDate.toString() + "," + dueDate.toString() + "," + (returnDate != null ? returnDate.toString() : "null");
    }
}