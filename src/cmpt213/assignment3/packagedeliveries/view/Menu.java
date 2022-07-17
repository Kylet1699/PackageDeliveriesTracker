package cmpt213.assignment3.packagedeliveries.view;

import cmpt213.assignment3.packagedeliveries.control.PackageDeliveriesTracker;
import cmpt213.assignment3.packagedeliveries.model.Package;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Menu implements ActionListener {
    JFrame applicationFrame;
    JScrollPane packageDisplay;
    JPanel buttonPanel;
    JButton showAllButton;
    JButton showOverdueButton;
    JButton showUpcomingButton;
    JButton addPackageButton;
    JPanel panels;
    JButton testButtons;
    JList displayList;
    JScrollPane displayListScroller;

    ArrayList<Package> packageList = new ArrayList<>();
    PackageDeliveriesTracker controller = new PackageDeliveriesTracker(packageList);

    public Menu() {
        controller.readFromJson();
        displayAll();

        applicationFrame = new JFrame("Lopang Delivery");
        applicationFrame.setSize(600, 600);
        applicationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        buttonPanel = new JPanel();
        applicationFrame.add(buttonPanel, BorderLayout.NORTH);

        showAllButton = new JButton("All");
        showAllButton.addActionListener(this);
        buttonPanel.add(showAllButton);

        showOverdueButton = new JButton("Overdue");
        showOverdueButton.addActionListener(this);
        buttonPanel.add(showOverdueButton);

        showUpcomingButton = new JButton("Upcoming");
        showUpcomingButton.addActionListener(this);
        buttonPanel.add(showUpcomingButton);

        displayList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        displayList.setLayoutOrientation(JList.VERTICAL);
        displayList.setVisibleRowCount(-1);
        displayListScroller.setPreferredSize(new Dimension(250, 80));
        applicationFrame.add(displayListScroller, BorderLayout.CENTER);
        displayListScroller.setVisible(true);

        addPackageButton = new JButton("Add Package");
        applicationFrame.add(addPackageButton, BorderLayout.SOUTH);

        applicationFrame.setVisible(true);
    }

    private void displayAll() {
        displayList = new JList(controller.getPackageList().toArray());
        displayListScroller = new JScrollPane(displayList);
    }
    private void displayOverdue() {
        displayList = new JList(controller.getOverdue().toArray());
        displayListScroller = new JScrollPane(displayList);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Overdue")) {
            System.out.println("Overdue");
            displayOverdue();
            applicationFrame.add(displayListScroller, BorderLayout.CENTER);

        }
        else if (e.getActionCommand().equals("All")) {
            System.out.println("All");
            displayAll();
            applicationFrame.add(displayListScroller, BorderLayout.CENTER);

        }
        else if (e.getActionCommand().equals("Upcoming")) {
            System.out.println("Upcoming");
        }
    }
}
