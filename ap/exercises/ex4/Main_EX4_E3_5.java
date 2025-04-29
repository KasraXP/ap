package exercises.ex4;


public class Main_EX4_E3_5 {

    public static void main(String[] args) {
        HallwayLight hallway = new HallwayLight(0, 1);

        printState("Case 0", hallway);


        hallway.toggleSecondSwitch();
        printState("Case 1", hallway);


        hallway.toggleFirstSwitch();
        printState("Case 2", hallway);


        hallway.toggleSecondSwitch();
        printState("Case 3", hallway);
    }

    public static void printState(String label, HallwayLight controller) {
        System.out.println(label);
        System.out.println("First Switch: " + controller.getFirstSwitchState());
        System.out.println("Second Switch: " + controller.getSecondSwitchState());
        System.out.println("Lamp State: " + controller.getLampState());
        System.out.println();
    }
}

class HallwayLight {
    private int firstSwitch;
    private int secondSwitch;

    public HallwayLight(int firstSwitch, int secondSwitch) {
        this.firstSwitch = firstSwitch;
        this.secondSwitch = secondSwitch;
    }

    int getFirstSwitchState() {
        return firstSwitch;
    }

    int getSecondSwitchState() {
        return secondSwitch;
    }

    int getLampState() {
        if (firstSwitch != secondSwitch) {
            return 1;
        } else {
            return 0;
        }
    }

    void toggleFirstSwitch() {
        if (firstSwitch == 0) {
            firstSwitch = 1;
        } else {
            firstSwitch = 0;
        }
    }

    void toggleSecondSwitch() {
        if (secondSwitch == 0) {
            secondSwitch = 1;
        } else {
            secondSwitch = 0;
        }
    }
}