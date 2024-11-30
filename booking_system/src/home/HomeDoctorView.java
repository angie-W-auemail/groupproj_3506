package home;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import user.User;
import manageAccounts.manageView;
import profile.ProfileView;

public class HomeDoctorView extends JFrame {

    public HomeDoctorView() {
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
        JLabel headerLabel = new JLabel("Welcome to the Doctor Dashboard");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerPanel.add(headerLabel);

        // Center panel with options
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(3, 1, 10, 10));

        JButton scheduleButton = new JButton("Current Schedule");
        JButton patientButton = new JButton("View Patient Profiles");
        JButton profileButton = new JButton("Doctor Profile");

        // Adding buttons to the center panel
        centerPanel.add(scheduleButton);
        centerPanel.add(patientButton);
        centerPanel.add(profileButton);


        // Add panels to the main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Add the main panel to the outer panel
        outerPanel.add(mainPanel, BorderLayout.CENTER);

        // Add the outer panel to the frame
        add(outerPanel);

        // Button actions (examples)
        scheduleButton.addActionListener((ActionEvent e) -> {
            JOptionPane.showMessageDialog(this, "View Schedule Clicked!");
        });

        patientButton.addActionListener((ActionEvent e) -> {
            JOptionPane.showMessageDialog(this, "View Patient Profiles clicked!");
            manageView view = new manageView();
            view.setVisible(true);
        });

        profileButton.addActionListener((ActionEvent e) -> {
        	ProfileView view = new  ProfileView();
       	 	this.dispose();;
        });
    }

}

