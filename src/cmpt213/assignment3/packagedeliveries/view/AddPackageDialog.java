package cmpt213.assignment3.packagedeliveries.view;

import com.github.lgooddatepicker.components.DateTimePicker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;

/**
 * A class for creating and displaying the "Add Package" dialog
 * @author Kyle Tseng
 */
public class AddPackageDialog extends JDialog implements ActionListener{
    private String inputName;
    private String inputNote;

    private Double inputPrice;
    private Double inputWeight;
    private LocalDateTime inputDeliveryDate;
    private LocalDateTime inputExpiryDate = null;
    private Double inputEnvFee = null;
    private String inputAuthor = null;

    private String[] typeOptions = {"Electronic", "Perishable", "Book"};

    private JComboBox packageType = new JComboBox(typeOptions);

    final JTextField
            packageName = new JTextField(12),
            packageNote = new JTextField(12),
            packagePrice = new JTextField(12),
            packageWeight = new JTextField(12),
            environmentalFee = new JTextField(12),
            author = new JTextField(12);
    final DateTimePicker
            deliveryDate = new DateTimePicker(),
            expiryDate = new DateTimePicker();

    /**
     * Create the skeleton structure of the dialog
     */
    public AddPackageDialog() {
        JButton addButton = new JButton("Add");
        JButton cancelButton = new JButton("Cancel");
        JPanel south = new JPanel();
        south.add(addButton);
        south.add(cancelButton);

        addButton.addActionListener(this);
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = gbc.REMAINDER;
        gbc.insets = new Insets(5,5,5,5);
        JLabel packageTypeLabel = new JLabel("Package Type: ");
        panel.add(packageTypeLabel);
        panel.add(packageType, gbc);
        JLabel packageNameLabel = new JLabel("Package Name: ");
        panel.add(packageNameLabel);
        panel.add(packageName, gbc);
        JLabel packageNoteLabel = new JLabel("Package Note: ");
        panel.add(packageNoteLabel);
        panel.add(packageNote, gbc);
        JLabel packagePriceLabel = new JLabel("Package Price: ");
        panel.add(packagePriceLabel);
        panel.add(packagePrice, gbc);
        JLabel packageWeightLabel = new JLabel("Package Weight: ");
        panel.add(packageWeightLabel);
        panel.add(packageWeight, gbc);
        JLabel deliveryDateLabel = new JLabel("Delivery Date: ");
        panel.add(deliveryDateLabel);
        panel.add(deliveryDate, gbc);
        JLabel typeLabel = new JLabel("Electronic Fee: ");
        panel.add(typeLabel);
        panel.add(environmentalFee);

        packageType.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (packageType.getSelectedItem().equals("Electronic")) {
                    typeLabel.setText("Environmental Fee: ");
                    typeLabel.paintImmediately(typeLabel.getVisibleRect());
                    panel.remove(expiryDate);
                    panel.remove(author);
                    panel.add(environmentalFee, gbc);
                    panel.paintImmediately(panel.getVisibleRect());
                } else if (packageType.getSelectedItem().equals("Book")) {
                    typeLabel.setText("Author: ");
                    typeLabel.paintImmediately(typeLabel.getVisibleRect());
                    panel.remove(environmentalFee);
                    panel.remove(expiryDate);
                    panel.add(author, gbc);
                    panel.paintImmediately(panel.getVisibleRect());
                } else if (packageType.getSelectedItem().equals("Perishable")) {
                    typeLabel.setText("Expiry Date: ");
                    typeLabel.paintImmediately(typeLabel.getVisibleRect());
                    panel.remove(environmentalFee);
                    panel.remove(author);
                    panel.add(expiryDate, gbc);
                    panel.paintImmediately(panel.getVisibleRect());
                }
            }
        });

        this.getContentPane().add(south, "South");
        this.getContentPane().add(panel);
        this.pack();
        this.setLocation(525,200);
        this.setSize(new Dimension(400, 400));
        this.setModal(true);
    }

    /**
     * Get package name
     * @return Package name
     */
    public String getInputName() {
        return inputName;
    }

    /**
     * Set package name
     * @param inputName
     */
    public void setInputName(String inputName) {
        this.inputName = inputName;
    }

    /**
     * Get package note
     * @return Package note
     */
    public String getInputNote() {
        return inputNote;
    }

    /**
     * Set package note
     * @param inputNote
     */
    public void setInputNote(String inputNote) {
        this.inputNote = inputNote;
    }

    /**
     * Get package price
     * @return Package price
     */
    public Double getInputPrice() {
        return inputPrice;
    }

    /**
     * Set package price
     * @param inputPrice
     */
    public void setInputPrice(Double inputPrice) {
        this.inputPrice = inputPrice;
    }

    /**
     * Get package weight
     * @return Package weight
     */
    public Double getInputWeight() {
        return inputWeight;
    }

    /**
     * Set package weight
     * @param inputWeight
     */
    public void setInputWeight(Double inputWeight) {
        this.inputWeight = inputWeight;
    }

    /**
     * Get package delivery date
     * @return Package delivery date
     */
    public LocalDateTime getInputDeliveryDate() {
        return inputDeliveryDate;
    }

    /**
     * Set package delivery date
     * @param inputDeliveryDate
     */
    public void setInputDeliveryDate(LocalDateTime inputDeliveryDate) {
        this.inputDeliveryDate = inputDeliveryDate;
    }

    /**
     * Get package expiry date
     * @return Package expiry date
     */
    public LocalDateTime getInputExpiryDate() {
        return inputExpiryDate;
    }

    /**
     * Set package expiry date
     * @param inputExpiryDate
     */
    public void setInputExpiryDate(LocalDateTime inputExpiryDate) {
        this.inputExpiryDate = inputExpiryDate;
    }

    /**
     * Get package environmental fee
     * @return Package environmental fee
     */
    public Double getInputEnvFee() {
        return inputEnvFee;
    }

    /**
     * Set package environmental fee
     * @param inputEnvFee
     */
    public void setInputEnvFee(Double inputEnvFee) {
        this.inputEnvFee = inputEnvFee;
    }

    /**
     * Get package author
     * @return Package author
     */
    public String getInputAuthor() {
        return inputAuthor;
    }

    /**
     * Set package author
     * @param inputAuthor
     */
    public void setInputAuthor(String inputAuthor) {
        this.inputAuthor = inputAuthor;
    }

    /**
     * Get package type (Electronic, Book, Perishable)
     * @return Package type
     */
    public Object getPackageType() {
        return packageType.getSelectedItem();
    }

    /**
     * Action listener for the "Add" button
     * Set package information with user inputs
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (checkEmptyInput() == false) {
            setInputName(packageName.getText());
            setInputNote(packageNote.getText());
            setInputPrice(Double.valueOf(packagePrice.getText()));
            setInputWeight(Double.valueOf(packageWeight.getText()));
            setInputDeliveryDate(deliveryDate.getDateTimeStrict());
            if (getPackageType().equals("Electronic")) {
                setInputEnvFee(Double.valueOf(environmentalFee.getText()));
            } else if (getPackageType().equals("Book")) {
                setInputAuthor(author.getText());

            } else if (getPackageType().equals("Perishable")) {
                setInputExpiryDate(expiryDate.getDateTimeStrict());

            }
            dispose();
        }
        else {
            return;
        }
    }

    /**
     * Check if user input is empty
     * @return true if any one of the required field is empty, otherwise false
     */
    private boolean checkEmptyInput() {
        if (packageName.getText().trim().isEmpty()
            || packagePrice.getText().trim().isEmpty()
            || packageWeight.getText().trim().isEmpty()
            || deliveryDate.getDateTimeStrict() == null
            || (environmentalFee.getText().trim().isEmpty() && author.getText().trim().isEmpty() && expiryDate.getDateTimeStrict() == null)) {
            JOptionPane.showMessageDialog(packageName.getParent(), "Please check your input, only NOTE can be empty.");
            return true;
        }
        return false;
    }
}
