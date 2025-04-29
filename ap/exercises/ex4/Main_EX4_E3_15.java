package exercises.ex4;

public class Main_EX4_E3_15 {
    public static void main(String[] args) {
        Letter letter = new Letter("Mary", "John");
        letter.addLine("I am sorry we must part.");
        letter.addLine("I wish you all the best.");

        System.out.println(letter.getText());
    }
}

class Letter {
    private String from;
    private String to;
    private String body;

    public Letter(String from, String to) {
        this.from = from;
        this.to = to;
        this.body = "";
    }

    public void addLine(String line) {
        body = body.concat(line).concat("\n");
    }

    public String getText() {
        String text = "Dear " + to + ":\n\n";
        text = text.concat(body);
        text = text.concat("\nSincerely,\n\n");
        text = text.concat(from);
        return text;
    }

}
