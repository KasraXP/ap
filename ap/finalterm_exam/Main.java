package finalterm_exam;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        Book book1 = new Book("Book",12000,"Mark","A good day");
        List<Product>  mainList = new ArrayList<>();
        mainList.add(book1);


    }
}


class Product {
    protected String name;
    protected int price;


    Product(String name, int price) {
        this.name = name;
        this.price = price;

    }
}


class Book extends Product {
    protected String author;
    protected String title;

    Book(String name, int price, String author, String title) {
        super(name, price);
        this.title = title;
        this.author = author;
    }


}



class Pen extends Product {
    protected String color;

    Pen(String name, int price, String color) {
        super(name, price);
        this.color = color;
    }



}


class ProductTools{

    static void printSortedInfo( List<Book> bookList, List<Pen> penList) {

        List<Book> sortedBooks;
        sortedBooks = bookList.stream().distinct().collect(Collectors.toList());

        List<Pen> sortedPenList;
        sortedPenList = penList.stream().distinct().collect(Collectors.toList());

        List<Product> sortedProductList;


        for (Pen p : sortedPenList) {
            System.out.println("Product Name :" + p.name + " Price " + p.price + " Color: " + p.color);
        }



    }

}