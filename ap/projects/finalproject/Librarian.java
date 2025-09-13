package projects.finalproject;

public class Librarian extends User implements Storable {
    private int givenCount;
    private int receivedCount;

    Librarian(String userName, String password, int givenCount, int receivedCount) {
        super(userName, password);
        this.givenCount = givenCount;
        this.receivedCount = receivedCount;
    }

    Librarian(String userName, String password) {
        super(userName, password);
        this.givenCount = 0;
        this.receivedCount = 0;
    }

    void setPassword(String newPassword) {
        this.password = newPassword;
    }

    public String toString() {
        return userName + "," + password + "," + givenCount + "," + receivedCount;
    }

    void setGivenCount() {
        this.givenCount++;
    }

    void setReceivedCount() {
        this.receivedCount++;
    }

    int getGivenCount() {
        return givenCount;
    }

    int getReceivedCount() {
        return receivedCount;
    }

}