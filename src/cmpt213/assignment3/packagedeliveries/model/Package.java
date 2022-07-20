package cmpt213.assignment3.packagedeliveries.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * A class for holding package informaiton: name, notes, price, weight, isDelivered, deliveryDate
 * @author Kyle Tseng
 */
public class Package implements Comparable<Package> {
    private int id;
    private String type;
    private String name;
    private String note;
    private Double price;
    private Double weight;
    boolean isDelivered;
    LocalDateTime deliveryDate;

    public Package(String name, String note, Double price, Double weight, LocalDateTime deliveryDate, String type) {
        this.name = name;
        this.note = note;
        this.price = price;
        this.weight = weight;
        this.isDelivered = false;
        this.deliveryDate = deliveryDate;
        this.type = type;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String isDelivered() {
        return isDelivered ? "Yes" : "No";
    }

    public void setDelivered(boolean delivered) {
        isDelivered = delivered;
    }
    public boolean getDelivered() {return isDelivered;}

    public LocalDateTime getDeliveryDate() {
        return deliveryDate;
    }
    public String getTitle() {return "Package Number: " + getId();}

    public void setDeliveryDate(LocalDateTime deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    /**
     * Formats a LocalDateTime object
     * @param preFormattedDate
     * @return A string of the formatted delivery date
     */
    public String deliveryDate(LocalDateTime preFormattedDate) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formatDeliveryDate = preFormattedDate.format(format);
        return formatDeliveryDate;
    }

    /**
     * Override toString() to display package information when System.out.println() calls the package object
     * @return formatted package information
     */
    @Override
    public String toString() {
        return  "Name: " + name + "\n" +
                "Notes: " + note + "\n" +
                "Price: $" + price + "\n" +
                "Weight: " + weight + "kg" + "\n" +
                "Expected Delivery Date: " + deliveryDate(deliveryDate) + "\n" +
                "Delivered? " + isDelivered() + "\n";
    }

    /**
     * Override compareTo to sort the packages based on their delivery date
     * @param o the object to be compared.
     * @return
     */
    @Override
    public int compareTo(Package o) {
        return this.deliveryDate.compareTo(o.deliveryDate);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}