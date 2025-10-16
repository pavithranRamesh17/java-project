package com.example;

import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
 

public class RegistrationForm extends JDialog {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField fullNameField;
    private JTextField emailField;
    private JTextField phoneField;
    private JTextField addressField;
    private JButton submitButton;
    private JButton cancelButton;

    public RegistrationForm(Frame owner) {
        super(owner, "Register", true);
        setSize(400, 350);
        setLocationRelativeTo(owner);
        initializeComponents();
        layoutComponents();
        setupHandlers();
    }

    private void initializeComponents() {
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        fullNameField = new JTextField(20);
        emailField = new JTextField(20);
        phoneField = new JTextField(20);
        addressField = new JTextField(20);
        submitButton = new JButton("Register");
        cancelButton = new JButton("Cancel");
    }

    private void layoutComponents() {
        JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6,6,6,6);
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.EAST;
        p.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        p.add(usernameField, gbc);

        gbc.gridx = 0; gbc.gridy++; gbc.anchor = GridBagConstraints.EAST;
        p.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        p.add(passwordField, gbc);

        gbc.gridx = 0; gbc.gridy++; gbc.anchor = GridBagConstraints.EAST;
        p.add(new JLabel("Full name:"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        p.add(fullNameField, gbc);

        gbc.gridx = 0; gbc.gridy++; gbc.anchor = GridBagConstraints.EAST;
        p.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        p.add(emailField, gbc);

        gbc.gridx = 0; gbc.gridy++; gbc.anchor = GridBagConstraints.EAST;
        p.add(new JLabel("Phone:"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        p.add(phoneField, gbc);

        gbc.gridx = 0; gbc.gridy++; gbc.anchor = GridBagConstraints.EAST;
        p.add(new JLabel("Address:"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        p.add(addressField, gbc);

        gbc.gridx = 0; gbc.gridy++; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        JPanel buttons = new JPanel();
        buttons.add(submitButton);
        buttons.add(cancelButton);
        p.add(buttons, gbc);

        add(p);
    }

    private void setupHandlers() {
        submitButton.addActionListener(e -> submitRegistration());

        cancelButton.addActionListener(e -> dispose());
    }

    private void submitRegistration() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        String fullName = fullNameField.getText().trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();
        String address = addressField.getText().trim();

        if (username.isEmpty() || password.isEmpty() || fullName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill at least username, password and full name.", "Validation", JOptionPane.WARNING_MESSAGE);
            return;
        }

        User user = new User(username, password, email, fullName, phone, address, "CITIZEN");
        boolean ok = Database.addUser(user);
        if (!ok) {
            JOptionPane.showMessageDialog(this, "Username already exists.", "Registration", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(this, "Registration successful. You can now login.", "Success", JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }
}
