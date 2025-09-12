package projects.finalproject;

class Book implements Storable {
    private String title;
    private String author;
    private int pages;
    private int publishedYear;
    private boolean isLoaned;
    private int loanCount;

    Book(String title, String author, int pages, int publishedYear) {
        this.title = title;
        this.author = author;
        this.pages = pages;
        this.publishedYear = publishedYear;
        this.isLoaned = false;
        this.loanCount = 0;

    }

    Book(String title, String author, int pages, int publishedYear, Boolean isLoaned, int loanCount) {
        this.title = title;
        this.author = author;
        this.pages = pages;
        this.publishedYear = publishedYear;
        this.isLoaned = isLoaned;
        this.loanCount = loanCount;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getPages() {
        return pages;
    }

    public int getPublishedYear() {
        return publishedYear;
    }

    public Boolean getIsLoaned() {
        return isLoaned;
    }

    public int getLoanCount() {
        return loanCount;
    }

    public void setNewTitle(String newTitle) {
        this.title = newTitle;
    }

    public void setNewAuthor(String newAuthor) {
        this.author = newAuthor;
    }

    public void setNewPages(int newPages) {
        this.pages = newPages;
    }

    public void setNewPublishedYear(int newPublishedYear) {
        this.publishedYear = newPublishedYear;
    }

    public void setIsLoaned(Boolean isLoaned) {
        this.isLoaned = isLoaned;
    }

    public String toString() {

        return title + "," + author + "," + pages + "," + publishedYear + "," + isLoaned + "," + loanCount;
    }

    public void setLoanCount(int count) {
        this.loanCount = count;
    }
}