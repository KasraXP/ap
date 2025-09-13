package projects.finalproject;
import java.time.LocalDate;

class Student extends User implements Storable {
    private final String major;
    private final LocalDate membershipDate;
    private boolean isActive;

    Student(String userName, String password, String major, LocalDate membershipDate) {
        super(userName, password);
        this.major = major;
        this.membershipDate = membershipDate;
        this.isActive = true;
    }

    String getMajor() {
        return major;
    }

    LocalDate getMembershipDate() {
        return membershipDate;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String toString() {
        return  userName + "," + password + "," + major + "," + membershipDate + "," + isActive;
    }
}
