package user_accounts;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import user.Admin;
import user.Doctor;
import user.Patient;
import user.UserManagement;
import user.User;
import java.awt.*;
import profile.CreateProfile;
import profile.ProfileView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class AdminUserView {
    private JFrame frame;
    private JTable userTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JButton searchButton, updateButton, deleteButton, createButton;
    private final UserManagement users = UserManagement.getInstance();
    String selectedRole;
    private final User mainPerson =User.getInstance();
    private final User selected =User.getInstance();

    public AdminUserView() {
        // Frame setup
        frame = new JFrame("Manage User Accounts");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Top panel for search bar
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchField = new JTextField();
        searchButton = new JButton("Search");
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);

        // Table setup for displaying user accounts
        String[] columnNames = {"User ID", "Name", "Permission"};
        tableModel = new DefaultTableModel(columnNames, 0);
        userTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(userTable);

        // Right panel for Update and Delete buttons
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        updateButton = new JButton("Update Profile");
        deleteButton = new JButton("Delete Account");
        updateButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        deleteButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        rightPanel.add(Box.createVerticalGlue());
        rightPanel.add(updateButton);
        rightPanel.add(Box.createVerticalStrut(10));
        rightPanel.add(deleteButton);
        rightPanel.add(Box.createVerticalGlue());

        // Footer panel for Create Account button
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        createButton = new JButton("Create Account");
        footerPanel.add(createButton);

        // Add components to the frame
        frame.add(searchPanel, BorderLayout.NORTH);
        frame.add(tableScrollPane, BorderLayout.CENTER);
        frame.add(rightPanel, BorderLayout.EAST);
        frame.add(footerPanel, BorderLayout.SOUTH);

        // Add ActionListeners for buttons
        setupListeners();

        // Sample data for demonstration
        populateData();

        frame.setVisible(true);
    }

    private void setupListeners() {
        // Search button action
        searchButton.addActionListener(e -> {
            String searchId = searchField.getText().trim();
            if (!searchId.isEmpty()) {
                filterTableById(searchId);
            } else {
                JOptionPane.showMessageDialog(frame, "Please enter a User ID to search.");
            }
        });

        // Update button action
        updateButton.addActionListener(e -> {
            int selectedRow = userTable.getSelectedRow();
            if (selectedRow >= 0) {
                String userId = (String) tableModel.getValueAt(selectedRow, 0);
                int permission = (int) tableModel.getValueAt(selectedRow, 2);
                User person = new User();
                if (permission ==1) {
                	person = (User)(users.getAdmin(userId));
                }
                if (permission ==2) {
                	person = (User)(users.getDoctor(userId));
                }
                if (permission ==3) {
                	person = (User)(users.getPatient(userId));
                }
                selected.setUser(person.email(), person.phone(), person.name(), person.id(), person.pass(), permission);
                ProfileView view = new ProfileView();
            } else {
                JOptionPane.showMessageDialog(frame, "Please select a user to update.");
            }
        });

        // Delete button action
        deleteButton.addActionListener(e -> {
            int selectedRow = userTable.getSelectedRow();
            if (selectedRow >= 0) {
                String userId = (String) tableModel.getValueAt(selectedRow, 0);
                int permission = (Integer) tableModel.getValueAt(selectedRow, 2);
                int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure you want to delete User ID: " + userId + "?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    tableModel.removeRow(selectedRow);
                    try {
                    	if(permission==1) {
                    		Admin admin = users.getAdmin(userId);
                    		users.removeAdmin(admin);
						} 
                    	else if (permission==2) {
                    		Doctor doctor = users.getDoctor(userId);
                    		users.removeDoctor(doctor);
                    	}
                    	else if (permission==3) {
                    		Patient patient = users.getPatient(userId);
                    		users.removePatient(patient);
                    	}
                    }
                    catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Please select a user to delete.");
            }
        });

        // Create button action
        createButton.addActionListener(e -> {
        	permissionView();
        	// manage account won't dispose account management page frame.dispose();
        	int newPermission = 1;
        	if (selectedRole=="Admin") {
        		newPermission=1;
        	}
        	else if (selectedRole=="Doctor") {
        		newPermission=2;
        	}
        	else if (selectedRole=="Patient") {
        		newPermission=3;
        	}
        	
        	CreateProfile view = new CreateProfile(newPermission);
        });
    }

    
    private void permissionView() {
    	
        // Define options for the combo box
        String[] roles = {"Admin", "Doctor", "Patient"};
        
        // Show input dialog with a combo box
        selectedRole = (String) JOptionPane.showInputDialog(
            null, 
            "Select role:", 
            "Permission Selection", 
            JOptionPane.QUESTION_MESSAGE, 
            null, 
            roles, // Array of options
            roles[0] // Default option
        );
    }
    private void filterTableById(String searchId) {
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String userId = (String) tableModel.getValueAt(i, 0);
            if (userId.equals(searchId)) {
                userTable.setRowSelectionInterval(i, i);
                return;
            }
        }
        JOptionPane.showMessageDialog(frame, "No user found with ID: " + searchId);
    }

    private void populateData() {
    	for (int i = 0; i<users.adminSize();i++) {
    		User person = users.getUser(1, i);
    		tableModel.addRow(new Object[]{person.id(), person.name(),person.permission()});
    	}
    	for (int i = 0; i<users.doctorSize();i++) {
    		User person = users.getUser(2, i);
    		tableModel.addRow(new Object[]{person.id(), person.name(),person.permission()});
    	}
    	for (int i = 0; i<users.patientSize();i++) {
    		User person = users.getUser(3, i);
    		tableModel.addRow(new Object[]{person.id(), person.name(),person.permission()});
    	}
    	
    }

}
