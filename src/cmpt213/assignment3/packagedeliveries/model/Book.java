package cmpt213.assignment3.packagedeliveries.model;

import java.time.LocalDateTime;

/**
 * A sub class of Package
 * @author Kyle Tseng
 */
public class Book extends Package {
    private String author;

    /**
     * Constructor for Book
     * @param name
     * @param note
     * @param price
     * @param weight
     * @param deliveryDate
     * @param author
     */
    public Book(String name, String note, Double price, Double weight, LocalDateTime deliveryDate, String author) {
        super(name, note, price, weight, deliveryDate, Book.class.getName());
        this.author = author;
    }

    /**
     * Get title of this book
     * @return Title of this book
     */
    public String getTitle() {return super.getTitle() + " (Book)";}

    /**
     * Ovveride toString() to display package information when System.out.println() calls the package object
     * @return formatted package information
     */
    @Override
    public String toString() {
        return  super.toString() + "The author is: " + author + "\n";
    }
}
