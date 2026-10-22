import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

class Person {
    protected String name;
    Person(String name) { this.name = name; }
}

class TaxPayer extends Person {
    private double income;
    TaxPayer(String name, double income) { super(name); this.income = income; }
    public double getIncome() { return income; }
    public void setIncome(double income) { this.income = income; }
    public String getName() { return name; }
}

class TaxCalculator 
    public double calculateTax(double income) {
        if (income < 250000) return 0;
        else if (income < 500000) return income * 0.05;
        else if (income < 1000000) return income * 0.1;
        else return income * 0.2;
    }

    public String detectFraud(double income, double tax) {
        if (income > 1000000 && tax < income * 0.1) return "⚠ Suspicious Income Detected!";
        else return "No suspicious activity.";
    }
}

public class TaxSystemGUI extends JFrame {
    private JTextField nameField, incomeField;
    private JTextArea outputArea;
    private TaxPayer tp;
    private TaxCalculator calc;

    public TaxSystemGUI() {
        setTitle("Income Tax Estimation and Detection System");
        setSize(480, 420); // Quick Fix: Increased height slightly
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Use a container panel with padding
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10)); // Gap between main areas
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15)); // Margin around window

        calc = new TaxCalculator();

        // Input Panel (compacted within a panel)
        JPanel inputContainer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 8, 8)); // Gap between fields
        inputPanel.add(new JLabel("Name:"));
        nameField = new JTextField(15); // Quick Fix: Set column size
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Income:"));
        incomeField = new JTextField(15); // Quick Fix: Set column size
        inputPanel.add(incomeField);
        inputContainer.add(inputPanel);
        mainPanel.add(inputContainer, BorderLayout.NORTH);

        // Buttons Panel (more spacing)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Gap around buttons
        JButton enterBtn = new JButton("Enter Details");
        JButton calcBtn = new JButton("Calculate Tax");
        JButton detectBtn = new JButton("Detect Fraud");
        JButton saveBtn = new JButton("Save to File");
        buttonPanel.add(enterBtn);
        buttonPanel.add(calcBtn);
        buttonPanel.add(detectBtn);
        buttonPanel.add(saveBtn);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        // Output Area (properly sized)
        outputArea = new JTextArea(10, 30); // Quick Fix: Fixed size
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Output"));
        mainPanel.add(scrollPane, BorderLayout.SOUTH);

        add(mainPanel); // Add the structured container to the frame

        // Button Actions
        enterBtn.addActionListener(e -> enterDetails());
        calcBtn.addActionListener(e -> calculateTax());
        detectBtn.addActionListener(e -> detectFraud());
        saveBtn.addActionListener(e -> saveToFile());

        setVisible(true);
    }

    private void enterDetails() {
        try {
            String name = nameField.getText();
            double income = Double.parseDouble(incomeField.getText());
            tp = new TaxPayer(name, income);
            outputArea.setText("Details Entered!\nName: " + name + "\nIncome: " + income);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Enter valid income!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void calculateTax() {
        if (tp == null) JOptionPane.showMessageDialog(this, "Enter details first!", "Warning", JOptionPane.WARNING_MESSAGE);
        else {
            double tax = calc.calculateTax(tp.getIncome());
            outputArea.append("\nCalculated Tax: " + tax);
        }
    }

    private void detectFraud() {
        if (tp == null) JOptionPane.showMessageDialog(this, "Enter details first!", "Warning", JOptionPane.WARNING_MESSAGE);
        else {
            double tax = calc.calculateTax(tp.getIncome());
            String result = calc.detectFraud(tp.getIncome(), tax);
            outputArea.append("\n" + result);
        }
    }

    private void saveToFile() {
        if (tp == null) JOptionPane.showMessageDialog(this, "Enter details first!", "Warning", JOptionPane.WARNING_MESSAGE);
        else {
            try {
                FileWriter fw = new FileWriter("taxdata.txt", true);
                fw.write("Name: " + tp.getName() + ", Income: " + tp.getIncome() + "\n");
                fw.close();
                outputArea.append("\nSaved to file!");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "File error!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TaxSystemGUI::new);
    }
}
