package cmpt213.assignment3.packagedeliveries.model;

import java.time.LocalDateTime;

public class Perishable extends Package {

    private LocalDateTime expiryDate;

    public Perishable(String name, String note, Double price, Double weight, LocalDateTime deliveryDate, LocalDateTime expiryDate) {
        super(name, note, price, weight, deliveryDate, Perishable.class.getName());
        this.expiryDate = expiryDate;
    }

    @Override
    public String toString() {
        return super.toString() + "The expiry date is: " + deliveryDate(expiryDate) + "\n" +
                "Package Type: Perishable\n";
    }
}