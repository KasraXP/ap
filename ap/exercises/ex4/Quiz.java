package exercises.ex4;

import java.util.ArrayList;

public class Quiz {
    public static void main(String[] args) {

        Book book1 = new Book("English",32000) ;
        Book book2 = new Book("English2",27000) ;

        ArrayList<Book> books = new ArrayList<>();
        books.add(book1);
        books.add(book2);


        Pen pen1 = new Pen("Green",2000,"Panter");
        Pen pen2 = new Pen("Yellow",3000,"Kian");

        ArrayList<Pen> pens = new ArrayList<>();
        pens.add(pen1);
        pens.add(pen2);


        for ( Book book : books ) {
            System.out.println("Book Name: " + book.name +"\n"+ "Book Price: " + book.price);
        }

        for ( Pen pen : pens ) {
            System.out.println("Pen Color: " + pen.color +"\n"+ "Pen Price: " + pen.price +"\n"+ "Pen  Brand: " + pen.Brand);
        }



    }
}

class Book {
    String name;
    float price;

    Book(String name, float price) {
        this.name = name;
        this.price = price;
    }
}

class Pen {
    String color;
    float price;
    String Brand;

    Pen(String color, float price, String Brand) {
        this.color = color;
        this.price = price;
        this.Brand = Brand;
    }
}
