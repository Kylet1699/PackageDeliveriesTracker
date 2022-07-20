package cmpt213.assignment3.packagedeliveries;

import cmpt213.assignment3.packagedeliveries.view.Menu;

import javax.swing.*;

/**
 * A class that contains the main method to start the GUI
 * @author Kyle Tseng
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Menu();
            }
        });
    }
}
