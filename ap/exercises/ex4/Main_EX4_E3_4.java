package exercises.ex4;


public class Main_EX4_E3_4 {
    private int firstSwitch;
    private int secondSwitch;

    public Main_EX4_E3_4(int firstSwitch, int secondSwitch) {
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