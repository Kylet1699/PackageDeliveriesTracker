package cmpt213.assignment3.packagedeliveries.model;

import java.time.LocalDateTime;

/**
 * A sub class of Package
 * @author Kyle Tseng
 */
public class Electronic extends Package {
    private Double fee;

    /**
     * Constructor for Electronic
     * @param name
     * @param note
     * @param price
     * @param weight
     * @param deliveryDate
     * @param fee
     */
    public Electronic(String name, String note, Double price, Double weight, LocalDateTime deliveryDate, Double fee) {
        super(name, note, price, weight, deliveryDate, Electronic.class.getName());
        this.fee = fee;
    }

    /**
     * Get title of this electronic
     * @return Title of this electronic
     */
    public String getTitle() {return super.getTitle() + " (Electronic)";}

    /**
     * Ovveride toString() to display package information when System.out.println() calls the package object
     * @return formatted package information
     */
    @Override
    public String toString() {
        return  super.toString() + "Environmental handling fee: " + fee + "\n";
    }
}