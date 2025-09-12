package projects.finalproject;

public class User {
    protected String userName;
    protected String password;

    User(String username, String password) {
        this.userName = username;
        this.password = password;
    }

    String getUserName() {
        return userName;
    }

    String getPassword() {
        return password;
    }
}
