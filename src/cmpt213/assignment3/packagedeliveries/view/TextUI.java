package cmpt213.assignment3.packagedeliveries.view;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class TextUI {
    private String title = "Lopang Delivery";
    ArrayList<String> options = new ArrayList<>();

    public void addMenu() {

        options.add("List all packages");
        options.add("Add a package");
        options.add("Remove a package");
        options.add("List overdue packages");
        options.add("List upcoming packages");
        options.add("Mark package as delivered");
        options.add("Exit");
    }

    public void displayMenu() {
        printHash();
        System.out.println("# " + title + " #");
        printHash();
        System.out.println("Today's date is " + getTodayDate());

        int currentIndex = 1;
        for (String i : options) {
            System.out.println(currentIndex + ": " + i);
            currentIndex++;
        }
    }

    public String getTodayDate() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String now = LocalDateTime.now().format(format);
        return now;
    }

    public int getInput() {
        Scanner console = new Scanner(System.in);
        return console.nextInt();
    }

    private void printHash() {
        for (int i = 0; i < title.length() + 4; i++) {
            System.out.print("#");
        }
        System.out.println();
    }
}
