package projects.finalproject;

public class Manager {
    private final String username;
    private final String password;

    public Manager() {
        username = "TheOwner";
        password = "1234567890";
    }

    String getName() {
        return username;
    }

    String getPassword() {
        return password;
    }

}
