package projects.finalproject;

import java.util.Scanner;

public class Manager {
    private final String name;
    private final String password;

    public Manager() {
        name = "TheOwner";
        password = "1234567890";
    }

    String getName(){
        return name;
    }

    String getPassword(){
        return password;
    }


}
