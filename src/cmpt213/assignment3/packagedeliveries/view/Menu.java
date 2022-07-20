package cmpt213.assignment3.packagedeliveries.view;

import cmpt213.assignment3.packagedeliveries.control.PackageDeliveriesTracker;
import cmpt213.assignment3.packagedeliveries.model.Package;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * A class for creating and displaying the user interface
 * @author Kyle Tseng
 */
public class Menu {
    private JScrollPane allPane;
    private JScrollPane upcomingPane;
    private JScrollPane overduePane;

    private JButton addButton;
    private JFrame applicationFrame;
    private JLabel packageInfo;
    private JTabbedPane tabs;
    private JPanel packageInfoPanel;
    private JPanel upcomingInfoPanel;
    private JPanel overdueInfoPanel;

    private PackageDeliveriesTracker controller;
    private ArrayList<Package> packageList = new ArrayList<>();

    /**
     * Create the skeleton structure of GUI
     */
    public Menu() {
        controller = new PackageDeliveriesTracker(packageList);
        controller.readFromJson();

        applicationFrame = new JFrame("Lopang Delivery");
        applicationFrame.setSize(600, 600);
        applicationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        applicationFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                controller.saveToJson();
            }
        });

        tabs = new JTabbedPane();

        GridLayout layout = new GridLayout(0,1);
        layout.setVgap(30);
        layout.setHgap(100);

        packageInfoPanel = new JPanel();
        packageInfoPanel.setLayout(layout);

        upcomingInfoPanel = new JPanel();
        upcomingInfoPanel.setLayout(layout);

        overdueInfoPanel = new JPanel();
        overdueInfoPanel.setLayout(layout);


        allPane = new JScrollPane(packageInfoPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        upcomingPane = new JScrollPane(upcomingInfoPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        overduePane = new JScrollPane(overdueInfoPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        updateList();

        tabs.addTab("All", allPane);
        tabs.addTab("Upcoming", upcomingPane);
        tabs.addTab("Overdue", overduePane);

        applicationFrame.add(tabs, BorderLayout.CENTER);

        addButton = new JButton("Add Package");
        addButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                launchDialog();
            }
        });
        applicationFrame.add(addButton, BorderLayout.SOUTH);

        applicationFrame.setVisible(true);
    }

    /**
     * Using the parent component of a Jbutton or JCheckBox to extract package information in the same parent component
     * @param parentComponent the parent component of a JButton or JCheckBox
     * @return Reference to the extracted package
     */
    private Package extractPackage(Container parentComponent) {
        for (Component thisComponent : parentComponent.getComponents()) {
            if (thisComponent instanceof JLabel) {
                JLabel label = (JLabel) thisComponent;
                String text = label.getText().replace("<html>", "").replace("<br>", "\n");
                for (Package thisPackage : controller.getPackageList()) {
                    if (thisPackage.toString().equals(text)) {
                        return thisPackage;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Remove the package in the same parent component as the JButton from the package list
     * @param thisButton the JButton used to get its parent component
     */
    private void removeItem(JButton thisButton) {
        Container parentComponent = thisButton.getParent();
        controller.removePackage(extractPackage(parentComponent));
        updateList();
    }

    /**
     * Update delivered status of the package in the same parent component as the JCheckBox
     * @param thisCheckBox the JCheckBox used to get its parent component
     * @param state the boolean state to update delivered state of the package
     */
    private void updateDelivered(JCheckBox thisCheckBox, boolean state) {
        Container parentComponent = thisCheckBox.getParent();
        controller.updateDelivered(extractPackage(parentComponent), state);
        updateList();
    }

    /**
     * Update the view of all three tabs (Show all, Show upcoming, Show overdue) with the updated package list
     */
    private void updateList() {
        controller.sortPackages();

        packageInfoPanel.removeAll();
        upcomingInfoPanel.removeAll();
        overdueInfoPanel.removeAll();
        JPanel container;
        JLabel infoLabel;
        JButton removeButton;
        JCheckBox checkDelivered;

        if (controller.getPackageList().isEmpty()) {
            infoLabel = new JLabel("No packages to show");
            infoLabel.setFont(new Font("Mono", Font.BOLD, 14));
            container = new JPanel();
            container.add(infoLabel);
            packageInfoPanel.add(container);
        }

        if (controller.getOverdue().isEmpty()) {
            infoLabel = new JLabel("No overdue packages to show");
            infoLabel.setFont(new Font("Mono", Font.BOLD, 14));
            container = new JPanel();
            container.add(infoLabel);
            overdueInfoPanel.add(container);
        }

        if (controller.getUpcoming().isEmpty()) {
            infoLabel = new JLabel("No upcoming packages to show");
            infoLabel.setFont(new Font("Mono", Font.BOLD, 14));
            container = new JPanel();
            container.add(infoLabel);
            upcomingInfoPanel.add(container);
        }

        for (Package thisPackage : controller.getPackageList()) {
            infoLabel = new JLabel("<html>" + thisPackage.toString().replace("\n", "<br>") + "<html>");
            infoLabel.setFont(new Font("Mono", Font.BOLD, 14));
            container = new JPanel();
            container.setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();
            c.gridwidth = c.REMAINDER;
            c.insets = new Insets(10, 5, 5, 5);
            container.add(infoLabel, c);
            container.setBorder(new TitledBorder(new LineBorder(Color.BLACK), thisPackage.getTitle(), 2, 2, new Font("Mono", Font.BOLD, 16)));
            removeButton = new JButton("Remove");
            removeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JButton thisButton = (JButton) e.getSource();
                    removeItem(thisButton);
                }
            });
            checkDelivered = new JCheckBox("Delivered", thisPackage.getDelivered());
            checkDelivered.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    JCheckBox thisCheckBox = (JCheckBox) e.getSource();
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        updateDelivered(thisCheckBox, true);
                    } else {
                        updateDelivered(thisCheckBox, false);
                    }
                }
            });
            container.add(checkDelivered, c);
            container.add(removeButton, c);
            packageInfoPanel.add(container);
        }


        for (Package thisPackage : controller.getUpcoming()) {
            infoLabel = new JLabel("<html>" + thisPackage.toString().replace("\n", "<br>") + "<html>");
            infoLabel.setFont(new Font("Mono", Font.BOLD, 14));

            container = new JPanel();
            container.setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();
            c.gridwidth = c.REMAINDER;
            c.insets = new Insets(10, 5, 5, 5);
            container.add(infoLabel, c);
            container.setBorder(new TitledBorder(new LineBorder(Color.BLACK), thisPackage.getTitle(), 2, 2, new Font("Mono", Font.BOLD, 16)));

            upcomingInfoPanel.add(container);
        }

        for (Package thisPackage : controller.getOverdue()) {
            infoLabel = new JLabel("<html>" + thisPackage.toString().replace("\n", "<br>") + "<html>");
            infoLabel.setFont(new Font("Mono", Font.BOLD, 14));

            container = new JPanel();
            container.setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();
            c.gridwidth = c.REMAINDER;
            c.insets = new Insets(10, 5, 5, 5);
            container.add(infoLabel, c);
            container.setBorder(new TitledBorder(new LineBorder(Color.BLACK), thisPackage.getTitle(), 2, 2, new Font("Mono", Font.BOLD, 16)));

            overdueInfoPanel.add(container);
        }
    }

    /**
     * Launch a dialog for adding a new package to the package list
     */
    private void launchDialog() {
        AddPackageDialog addDialog = new AddPackageDialog();
        addDialog.setVisible(true);
        if (addDialog.getInputName() == null || addDialog.getInputName().isEmpty()) {
            return;
        } else {
            controller.addPackage(
                    addDialog.getInputName(),
                    addDialog.getInputNote(),
                    addDialog.getInputPrice(),
                    addDialog.getInputWeight(),
                    addDialog.getInputDeliveryDate(),
                    addDialog.getInputEnvFee(),
                    addDialog.getInputAuthor(),
                    addDialog.getInputExpiryDate(),
                    addDialog.getPackageType()

            );
            updateList();
        }
    }
}
