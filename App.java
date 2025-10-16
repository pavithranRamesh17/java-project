package com.example;

import javax.swing.*;

public class App {
    public static void main(String[] args) {
        // Initialize storage files
        Database.initializeDatabase();

        // Ensure swing UI runs on EDT
        SwingUtilities.invokeLater(() -> {
            LoginFrame login = new LoginFrame();
            login.setVisible(true);
        });
    }
}
