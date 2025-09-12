package projects.finalproject;

public class Librarian extends User implements Storable{

    Librarian(String userName, String password) {
     super(userName,password);
    }

    void setPassword(String newPassword) {
        this.password = newPassword;
    }

    public String toString() {
        return userName + "," + password;
    }

}