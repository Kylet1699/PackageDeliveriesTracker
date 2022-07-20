package cmpt213.assignment3.packagedeliveries.model;

import java.time.LocalDateTime;

/**
 * A sub class of Package
 * @author Kyle Tseng
 */
public class Perishable extends Package {

    private LocalDateTime expiryDate;

    /**
     * Constructor for this class
     * @param name
     * @param note
     * @param price
     * @param weight
     * @param deliveryDate
     * @param expiryDate
     */
    public Perishable(String name, String note, Double price, Double weight, LocalDateTime deliveryDate, LocalDateTime expiryDate) {
        super(name, note, price, weight, deliveryDate, Perishable.class.getName());
        this.expiryDate = expiryDate;
    }

    /**
     * Get title of this perishable
     * @return Title of this perishable
     */
    public String getTitle() {return super.getTitle() + " (Perishable)";}

    /**
     * Ovveride toString() to display package information when System.out.println() calls the package object
     * @return formatted package information
     */
    @Override
    public String toString() {
        return  super.toString() + "The expiry date is: " + deliveryDate(expiryDate) + "\n";
    }
}