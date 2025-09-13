package projects.finalproject;


class LoanRequest implements Storable {
    private final Book book;
    private final Student student;
    private final int loanDurationDays;
    private String status;

    LoanRequest(Book book, Student student, int loanDurationDays) {
        this.book = book;
        this.student = student;
        this.loanDurationDays = loanDurationDays;
        this.status = "PENDING";
    }


    Student getStudent() {
        return student;
    }

    Book getBook() {
        return book;
    }

    int getLoanDurationDays() {
        return loanDurationDays;
    }

    String getStatus() {
        return status;
    }


    void setStatus(String status) {
        this.status = status;
    }

    public String toString() {
        return book.getTitle() + "," + student.getPassword() + "," +
                loanDurationDays + "," + status;
    }
}