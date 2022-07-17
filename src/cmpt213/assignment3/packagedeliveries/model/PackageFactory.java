package cmpt213.assignment3.packagedeliveries.model;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;

public class PackageFactory {
    static Scanner console = new Scanner (System.in);

    public static Package addPackage() {
        String packageName = null;
        String packageNote = null;
        Double packagePrice = null;
        Double packageWeight = null;
        String packageAuthor = null;
        LocalDateTime packageExpiryDate = null;
        Double packageFee = null;
        LocalDateTime deliveryDate = null;
        int packageType = 0;

        while (packageType == 0) {
            System.out.println("Enter the type of the package (1: Book, 2: Perishable, 3: Electronic): ");
            packageType = console.nextInt();
            if (packageType <= 0 || packageType > 3) {
                packageType = 0;
                System.out.println("Error: Please enter a number between 1 and 3");
            }
        }
        console.nextLine();
        while (packageName == null) {
            System.out.println("Enter the name of the package: ");
            packageName = console.nextLine();
            if (!checkEmptyInput(packageName)) {
                packageName = null;
            }
        }

        while (packageNote == null) {
            System.out.println("Enter the notes of the package: ");
            packageNote = console.nextLine();
        }

        while (packagePrice == null) {
            System.out.println("Enter the price of the package (in dollar): ");
            packagePrice = console.nextDouble();
            if (!checkNegativeInput(packagePrice)) {
                packagePrice = null;
            }
        }
        while (packageWeight == null) {
            System.out.println("Enter the weight of the package (in kg): ");
            packageWeight = console.nextDouble();
            if (!checkNegativeInput(packageWeight)) {
                packageWeight = null;
            }
        }

        deliveryDate = inputDate("expected");
        console.nextLine();

        if (packageType == 1 ) {
            System.out.println("Enter the author of the book: ");
            packageAuthor = console.nextLine();
            System.out.println(packageName + " has been added to the list");
            return new Book(packageName, packageNote, packagePrice, packageWeight,
                    deliveryDate, packageAuthor);
        }

        if (packageType == 2) {
            packageExpiryDate = inputDate("expiry");
            System.out.println(packageName + " has been added to the list");
            return new Perishable(packageName, packageNote, packagePrice, packageWeight, deliveryDate, packageExpiryDate);
        }

        if (packageType == 3) {
            while (packageFee == null) {
                System.out.println("Enter the environmental handling fee (in dollar): ");
                packageFee = console.nextDouble();
                if (!checkNegativeInput(packageFee)) {
                    packageFee = null;
                }
            }
            System.out.println(packageName + " has been added to the list");
            return new Electronic(packageName, packageNote, packagePrice, packageWeight, deliveryDate, packageFee);
        }
        return null;
    }

    private static LocalDateTime inputDate(String typeOfDate) {
        int deliveryYear = 0;
        int deliveryMonth = 0;
        int deliveryDay = 0;
        int deliveryHour = -1;
        int deliveryMinute = -1;
        LocalDateTime deliveryDate = null;
        String outputText = typeOfDate;

        if (typeOfDate.equalsIgnoreCase("expected")) {
            outputText = "expected delivery";
        }

        while (deliveryYear == 0) {
            System.out.println("Enter the year of the " + outputText + " date: ");
            deliveryYear = console.nextInt();
        }
        while (deliveryMonth == 0) {
            System.out.println("Enter the month of the expected delivery date (1-12): ");
            deliveryMonth = console.nextInt();
            if (deliveryMonth < 1 || deliveryMonth > 12) {
                System.out.println("Input month " + deliveryMonth + " out of range (1-12).");
                deliveryMonth = 0;
            }
        }
        while (deliveryDay == 0) {
            System.out.println("Enter the day of the expected delivery date (1-28/29/30/31): ");
            deliveryDay = console.nextInt();
            try {
                LocalDate.of(deliveryYear, deliveryMonth, deliveryDay);
            }
            catch(DateTimeException e) {
                System.out.println("Error: Input date does not exist");
                deliveryDay = 0;
            }
        }
        while (deliveryHour == -1) {
            System.out.println("Enter the hour of the expected delivery date (0-23): ");
            deliveryHour = console.nextInt();
            if (deliveryHour < 0 || deliveryHour > 23) {
                System.out.println("Input hour " + deliveryHour + " out of range (0-23).");
                deliveryHour = -1;
            }
        }
        while (deliveryMinute == -1) {
            System.out.println("Enter the minute of expected delivery date (0-59): ");
            deliveryMinute = console.nextInt();
            if (deliveryMinute < 0 || deliveryMinute > 59) {
                System.out.println("Input miunte " + deliveryMinute + " out of range (0-59).");
                deliveryMinute = -1;
            }
        }
        deliveryDate = LocalDateTime.of(
                deliveryYear,
                deliveryMonth,
                deliveryDay,
                deliveryHour,
                deliveryMinute
        );

        return deliveryDate;
    }

    private static boolean checkEmptyInput(String input) {
        if (input == null || input.trim().length() == 0) {
            System.out.println("Error: empty input field");
            return false;
        } else {
            return true;
        }
    }

    private static boolean checkNegativeInput(Double input) {
        if (input < 0 || input == null) {
            System.out.println("Error: invalid number");
            return false;
        } else {
            return true;
        }
    }
}
