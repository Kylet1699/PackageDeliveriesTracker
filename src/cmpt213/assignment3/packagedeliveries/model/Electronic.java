package cmpt213.assignment3.packagedeliveries.model;

import java.time.LocalDateTime;

public class Electronic extends Package {
    private Double fee;

    public Electronic(String name, String note, Double price, Double weight, LocalDateTime deliveryDate, Double fee) {
        super(name, note, price, weight, deliveryDate, Electronic.class.getName());
        this.fee = fee;
    }

    @Override
    public String toString() {
        return super.toString() + "Environmental handling fee: " + fee + "\n" +
                "Package Type: Electronic \n";
    }
}