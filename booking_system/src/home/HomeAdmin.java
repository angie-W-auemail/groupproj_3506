package home;

import javax.swing.*;
import manageAccounts.manageView;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class HomeAdmin extends JFrame {

    public HomeAdmin() {
        // Set the frame properties
        setTitle("Homepage");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Outer panel with border
        JPanel outerPanel = new JPanel();
        outerPanel.setLayout(new BorderLayout());
        outerPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.DARK_GRAY, 2),
                "Weclome to HealthCare Clinic",
                TitledBorder.CENTER,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 16),
                Color.DARK_GRAY
        ));

        // Main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Header panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.LIGHT_GRAY);
        JLabel headerLabel = new JLabel("Welcome to the Admin Dashboard");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerPanel.add(headerLabel);

        // Center panel with options
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(3, 1, 10, 10));

        JButton manageUsersButton = new JButton("Manage Patient Accounts");
        JButton manageScheduleButton = new JButton("Manage Schedule");
        JButton manageAdminProfileButton = new JButton("Manage Admin Profile");

        // Adding buttons to the center panel
        centerPanel.add(manageUsersButton);
        centerPanel.add(manageScheduleButton);
        centerPanel.add(manageAdminProfileButton);


        // Add panels to the main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Add the main panel to the outer panel
        outerPanel.add(mainPanel, BorderLayout.CENTER);

        // Add the outer panel to the frame
        add(outerPanel);

        // Button actions (examples)
        manageUsersButton.addActionListener((ActionEvent e) -> {
            JOptionPane.showMessageDialog(this, "Manage Patient Accounts clicked!");
            manageView view = new manageView();
            view.setVisible(true);
        });

        manageScheduleButton.addActionListener((ActionEvent e) -> {
            JOptionPane.showMessageDialog(this, "Manage Schedule clicked!");
        });

        manageAdminProfileButton.addActionListener((ActionEvent e) -> {
            JOptionPane.showMessageDialog(this, "Manage Admin Profile clicked!");
        });
    }

}
