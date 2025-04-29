package exercises.ex4;

public class Main_EX4_E3_10 {
    public static void main(String[] args) {
        CashRegister register = new CashRegister();

        register.recordPurchase(34.75);
        register.recordPurchase(89.50);
        register.recordPurchase(90.25);
        register.recordPurchase(0.250);
        register.receivePayment(250);

        register.printReceipt();

        double change = register.giveChange();

        System.out.println("Change:" + change);
    }
}


class CashRegister {

    private double purchase;
    private double payment;
    private String receipt;

    public CashRegister() {
        purchase = 0;
        payment = 0;
        receipt = "";
    }

    public void recordPurchase(double amount) {
        purchase = purchase + amount;
        receipt = receipt.concat(String.valueOf(amount).concat("\n"));
    }

    public void receivePayment(double amount) {

        payment = payment + amount;
    }

    public double giveChange() {

        double change = payment - purchase;
        purchase = 0;
        payment = 0;
        receipt = "";
        return change;
    }

    public void printReceipt() {
        System.out.println("Receipt: ");
        System.out.println(receipt);
        System.out.println("Total amount: " + purchase);
    }

}

