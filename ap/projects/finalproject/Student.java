package projects.finalproject;
import java.time.LocalDate;

class Student {
    private final String userName;
    private final String password;
    private final String major;
    private final LocalDate membershipDate;

    Student(String userName, String password, String major, LocalDate membershipDate) {
        this.userName = userName;
        this.password = password;
        this.major = major;
        this.membershipDate = membershipDate;

    }

    String getUserName() {
        return userName;
    }

    String getPassword() {
        return password;
    }

    String getMajor() {
        return major;
    }

    LocalDate getMembershipDate() {
        return membershipDate;
    }

    public String toStringStudent() {

        return getUserName() + "," + getPassword() + "," + getMajor() + "," + getMembershipDate();
    }
}
