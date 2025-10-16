package com.example;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class CitizenPanel extends JFrame {
    private final User user;
    private DefaultListModel<String> model;

    public CitizenPanel(User user) {
        this.user = user;
        setTitle("Citizen Panel - " + user.getFullName());
        setSize(600,400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initUI();
    }

    private void initUI() {
        JPanel p = new JPanel(new BorderLayout());
        JLabel header = new JLabel("Hello, " + user.getFullName(), JLabel.CENTER);
        header.setFont(header.getFont().deriveFont(Font.BOLD, 16f));
        p.add(header, BorderLayout.NORTH);

        model = new DefaultListModel<>();
        List<Complaint> complaints = Database.getComplaintsByUserId(user.getUserId());
        for (Complaint c : complaints) model.addElement(c.toString());
        JList<String> list = new JList<>(model);
        p.add(new JScrollPane(list), BorderLayout.CENTER);

        JButton addBtn = new JButton("Add Complaint");
        addBtn.addActionListener(e -> showAddDialog());
        JPanel bottom = new JPanel();
        bottom.add(addBtn);
        p.add(bottom, BorderLayout.SOUTH);

        add(p);
    }

    private void showAddDialog() {
        String title = JOptionPane.showInputDialog(this, "Complaint title:");
        if (title == null || title.trim().isEmpty()) return;
        String desc = JOptionPane.showInputDialog(this, "Complaint description:");
        Complaint c = new Complaint(user.getUserId(), user.getFullName(), title, desc, "General", "", "");
        Database.addComplaint(c);
        model.addElement(c.toString());
        JOptionPane.showMessageDialog(this, "Complaint added.");
    }
}
