public class DataSet {
    private double sum = 0;
    private double smallest = Double.MAX_VALUE;
    private double largest = Double.MIN_VALUE;
    private int count = 0;

    public void add(double value) {
        sum += value;
        if (value < smallest) {
            smallest = value;
        }
        if (value > largest) {
            largest = value;
        }
        count++;
    }

    public double getAverage() {
        if (count == 0) return 0;
        return sum / count;
    }

    public double getSmallest() {
        if (count == 0) return 0;
        return smallest;
    }

    public double getLargest() {
        if (count == 0) return 0;
        return largest;
    }

    public double getRange() {
        if (count == 0) return 0;
        return largest - smallest;
    }
}
