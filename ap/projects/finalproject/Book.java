package projects.finalproject;

class Book implements Storable {
    private String title;
    private String author;
    private int pages;
    private int publishedYear;
    private boolean isLoaned;
    private int loanCount;
    private final String theLibrarian;

    Book(String title, String author, int pages, int publishedYear, String theLibrarian) {
        this.title = title;
        this.author = author;
        this.pages = pages;
        this.publishedYear = publishedYear;
        this.theLibrarian = theLibrarian;
        this.isLoaned = false;
        this.loanCount = 0;

    }

    Book(String title, String author, int pages, int publishedYear, Boolean isLoaned, int loanCount, String theLibrarian) {
        this.title = title;
        this.author = author;
        this.pages = pages;
        this.publishedYear = publishedYear;
        this.isLoaned = isLoaned;
        this.loanCount = loanCount;
        this.theLibrarian = theLibrarian;
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

    public void setLoanCount(){
        this.loanCount++;
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

    public String getTheLibrarian() {
        return theLibrarian;
    }

    public String toString() {

        return title + "," + author + "," + pages + "," + publishedYear + "," + isLoaned + "," + loanCount + "," + theLibrarian;
    }

}