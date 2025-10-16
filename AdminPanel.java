package com.example;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class AdminPanel extends JFrame {
    private final User admin;
    private JList<String> complaintsList;

    public AdminPanel(User admin) {
        this.admin = admin;
        setTitle("Admin Panel - " + admin.getFullName());
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initUI();
    }

    private void initUI() {
        JPanel p = new JPanel(new BorderLayout());
        JLabel header = new JLabel("Welcome, " + admin.getFullName(), JLabel.CENTER);
        header.setFont(header.getFont().deriveFont(Font.BOLD, 16f));
        p.add(header, BorderLayout.NORTH);

        List<Complaint> complaints = Database.loadComplaints();
        DefaultListModel<String> model = new DefaultListModel<>();
        for (Complaint c : complaints) model.addElement(c.toString());
        complaintsList = new JList<>(model);
        p.add(new JScrollPane(complaintsList), BorderLayout.CENTER);

        add(p);
    }
}
