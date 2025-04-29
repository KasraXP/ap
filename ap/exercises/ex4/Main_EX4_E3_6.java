package exercises.ex4;

public class Main_EX4_E3_6 {
    public static void main(String[] args) {
        Circuit circuit = new Circuit();

        System.out.println("Initial State:");
        printState(circuit);

        System.out.println("\nToggle switch 1:");
        circuit.toggleSwitch(1);
        printState(circuit);

        System.out.println("\nToggle switch 2:");
        circuit.toggleSwitch(2);
        printState(circuit);

        System.out.println("\nToggle switch 1 again:");
        circuit.toggleSwitch(1);
        printState(circuit);

        System.out.println("\nToggle switch 2 again:");
        circuit.toggleSwitch(2);
        printState(circuit);
    }

    private static void printState(Circuit c) {
        System.out.println("Switch 1: " + c.getSwitchState(1));
        System.out.println("Switch 2: " + c.getSwitchState(2));
        System.out.println("Lamp:     " + c.getLampState());
    }
}

class Circuit {
    private int firstSwitch;
    private int secondSwitch;
    private int lamp;

    public Circuit() {
        firstSwitch = 0;
        secondSwitch = 0;
        updateLamp();
    }

    public int getSwitchState(int switchNum) {
        if (switchNum == 1) {
            return firstSwitch;
        } else if (switchNum == 2) {
            return secondSwitch;
        } else {
            return -1;
        }
    }

    public int getLampState() {
        return lamp;
    }

    public void toggleSwitch(int switchNum) {
        if (switchNum == 1) {
            if (firstSwitch == 0) {
                firstSwitch = 1;
            } else {
                firstSwitch = 0;
            }
        } else if (switchNum == 2) {
            if (secondSwitch == 0) {
                secondSwitch = 1;
            } else {
                secondSwitch = 0;
            }
        }

        updateLamp();
    }

    private void updateLamp() {
        if (firstSwitch != secondSwitch) {
            lamp = 1;
        } else {
            lamp = 0;
        }
    }
}