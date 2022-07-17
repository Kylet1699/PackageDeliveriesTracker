package cmpt213.assignment3.packagedeliveries.model;

import java.time.LocalDateTime;

public class Book extends Package {
    private String author;

    public Book(String name, String note, Double price, Double weight, LocalDateTime deliveryDate, String author) {
        super(name, note, price, weight, deliveryDate, Book.class.getName());
        this.author = author;
    }

    @Override
    public String toString() {
        return super.toString() + "The author is: " + author + "\n" +
                "Package Type: Book\n";
    }
}
