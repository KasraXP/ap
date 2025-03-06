public class EvenNumbers {
    public static void main(String[] args) {

        int i, sum = 0;
        for (i = 2; i <= 100 ;i += 2)
            sum = sum + i;


        System.out.println(sum);
    }
}