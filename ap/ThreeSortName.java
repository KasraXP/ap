import java.util.Scanner;

public class ThreeSortName {
    public static void main(String[] args) {
        Scanner str = new Scanner(System.in);
        System.out.println("Please enter three strings:");

        String str1 = str.next();
        String str2 = str.next();
        String str3 = str.next();

        String tmp;
        if (str1.compareTo(str2) > 0) {
            tmp = str1;
            str1 = str2;
            str2 = tmp;
        }

        if (str2.compareTo(str3) > 0) {
            tmp = str2;
            str2 = str3;
            str3 = tmp;
        }

        if (str1.compareTo(str2) > 0) {
            tmp = str1;
            str1 = str2;
            str2 = tmp;
        }

        System.out.println(str1+" "+str2 +" "+ str3);


    }
}
