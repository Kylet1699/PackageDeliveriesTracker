package cmpt213.assignment3.packagedeliveries.model;

import java.time.LocalDateTime;

/**
 * A factory class for constructing different sub classes of Package
 * @author Kyle Tseng
 */
public class PackageFactory {

    /**
     * Takes in information and construct a sub class of Package based on the package type
     * @param name
     * @param note
     * @param price
     * @param weight
     * @param deliveryDate
     * @param fee
     * @param author
     * @param expiryDate
     * @param type
     * @return Electronic or Perishable or Book object based on the package type
     */
    public static Package createPackage(String name,
                                     String note,
                                     Double price,
                                     Double weight,
                                     LocalDateTime deliveryDate,
                                     Double fee, String author,
                                     LocalDateTime expiryDate,
                                     String type) {
        switch (type) {
            case "Electronic":
                return new Electronic(name, note, price, weight, deliveryDate, fee);
            case "Perishable":
                return new Perishable(name, note, price, weight, deliveryDate, expiryDate);
            case "Book":
                return new Book(name, note, price, weight, deliveryDate, author);
        }
        return null;
    }

}
