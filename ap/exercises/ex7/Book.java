package exercises.ex7;
import java.util.Objects;

public class Book {
    private final String title;
    private final String author;
    private final int pages;
    private final int publishedYear;
    private boolean isLoaned;
    private int loanCount;

    public Book(String title, String author, int pages, int publishedYear, boolean isLoaned, int loanCount) {
        this.title = title;
        this.author = author;
        this.pages = pages;
        this.publishedYear = publishedYear;
        this.isLoaned = isLoaned;
        this.loanCount = loanCount;
    }

    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public int getPages() { return pages; }
    public int getPublishedYear() { return publishedYear; }
    public boolean getIsLoaned() { return isLoaned; }
    public int getLoanCount() { return loanCount; }
    public void setIsLoaned(boolean isLoaned) { this.isLoaned = isLoaned; }
    public void incrementLoanCount() { this.loanCount++; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return title.equals(book.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }
}