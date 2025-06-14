package exercises.ex7;

import java.util.Objects;

public class LoanRequest {
    private final Book book;
    private final Student student;
    private final Librarian assignedLibrarian;

    public LoanRequest(Book book, Student student, Librarian assignedLibrarian) {
        this.book = book;
        this.student = student;
        this.assignedLibrarian = assignedLibrarian;
    }

    public Book getBook() { return book; }
    public Student getStudent() { return student; }
    public Librarian getAssignedLibrarian() { return assignedLibrarian; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoanRequest that = (LoanRequest) o;
        return book.equals(that.book) &&
                student.equals(that.student);
    }

    @Override
    public int hashCode() {
        return Objects.hash(book, student);
    }
}