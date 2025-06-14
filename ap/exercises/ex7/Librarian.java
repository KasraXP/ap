package exercises.ex7;

import java.util.Objects;

public class Librarian {
    private final String firstName;
    private final String lastName;
    private final String employeeId;

    public Librarian(String firstName, String lastName, String employeeId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.employeeId = employeeId;
    }

    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { employeeId = employeeId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Librarian librarian = (Librarian) o;
        return employeeId.equals(librarian.employeeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId);
    }
}
