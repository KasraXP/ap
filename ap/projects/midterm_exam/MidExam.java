package projects.midterm_exam;

import com.sun.deploy.security.SelectableSecurityManager;

import java.util.ArrayList;

public class MidExam {
    public static void main(String[] args) {

        LapTop lapTop1 = new LapTop("HP",54000, "HP","Black");
        Case case1 = new Case("Lenovo",94000,"25cm","square");
        Case case2 = new Case("HP",26000,"30cm","square");
        ArrayList<Case> cases = new ArrayList<>();
        ArrayList<LapTop> lapTops = new ArrayList<>();
        Shop shop = new Shop(lapTops,cases);
        shop.addCase(case1);
        shop.addCase(case2);
        shop.addLapTop(lapTop1);
        //shop.printCaseList();
        //shop.printLapTopList();
        shop.searchByBrand("HP");

    }
}


class Product {
    private String brand;
    private float price;

    Product(String brand, float price) {
        this.brand = brand;
        this.price = price;
    }

    float getPrice() {
        return price;
    }

    String getBrand() {
        return brand;
    }
}

class LapTop extends Product {
    private String coolPadBrand;
    private String color;


    LapTop(String brand, float price, String coolPadBrand, String color) {
        super(brand, price);
        this.coolPadBrand = coolPadBrand;
        this.color = color;
    }

    String getCoolPadBrand() {
        return coolPadBrand;
    }

    String getColor() {
        return color;
    }

}

class Case extends Product {
    private String shape;
    private String size;

    Case(String brand, float price, String size, String shape) {
        super(brand, price);
        this.shape = shape;
        this.size = size;
    }

    String getShape() {
        return shape;
    }

    String getSize() {
        return size;
    }

}

class Shop {
    private ArrayList<LapTop> lapTopList;
    private ArrayList<Case> caseList;

    Shop(ArrayList<LapTop> lapTopList, ArrayList<Case> caseList) {
        this.lapTopList = lapTopList;
        this.caseList = caseList;

    }

    void addLapTop(LapTop lapTop) {
        lapTopList.add(lapTop);
    }

    void addCase(Case case1) {
        caseList.add(case1);
    }

    void printLapTopList() {
        for (LapTop lapTop : lapTopList) {
            System.out.println("LapTop: " + lapTop.getBrand() + " " + lapTop.getPrice() + " " +  lapTop.getCoolPadBrand() + " " + lapTop.getColor());
        }
    }

    void printCaseList() {
        for (Case case1 : caseList) {
            System.out.println("Case: " + case1.getBrand() + " " + case1.getPrice() + " " + case1.getSize() + " " + case1.getShape());
        }
    }

    void searchByBrand(String brand) {
        for (LapTop lapTop : lapTopList) {
            if (lapTop.getBrand().equals(brand)) {
                System.out.println("LapTop: " + lapTop.getBrand() + " " + lapTop.getPrice());

            }

            else
                System.out.println("this brand does not match for laptop\n");
        }

        for (Case c: caseList) {
            if (c.getBrand().equals(brand)) {
                System.out.println("Case: " + c.getBrand() + " " + c.getPrice());
            }
            else
                System.out.println("this brand does not match for case\n");
        }
    }
}
