package projects.finalproject;

public class Librarian {
    private final String fullName;
    private String password;

    Librarian(String fullName, String password) {
        this.fullName = fullName;
        this.password = password;
    }

    String getFullName() {
        return fullName;
    }

    void setPassword(String newPassword) {
        this.password = newPassword;
    }

    String getPassword() {
        return password;
    }

    String toStringLibrarian() {
        return getFullName() + "," + getPassword();
    }

}