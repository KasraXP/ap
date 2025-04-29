package exercises.ex4;

public class Main_EX4_E3_12 {
    public static void main(String[] args) {

        Employee employee = new Employee("Mike", 4000);
        System.out.println("Employee's Name: " + employee.getName());
        System.out.println("Employee's Salary: " + employee.getSalary());
        employee.raiseSalary(1200);
        System.out.println("Employee's new Salary: " + employee.getSalary());
    }
}

class Employee {
    private String name;
    private double salary;

    Employee(String employeeName, double currentSalary) {
        name = employeeName;
        salary = currentSalary;
    }

    String getName() {
        return name;
    }

    double getSalary() {
        return salary;
    }

    void raiseSalary(double byPercent) {
        salary += byPercent;
    }

}

