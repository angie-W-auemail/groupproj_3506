package profile;

import user.User;
import user.UserManagement;
import java.util.*;
import java.io.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import user.Doctor;
import user.Patient;
import user.Admin;

public class  CreateProfile {
	
	private User mainPerson=new User();
	private final UserManagement users = UserManagement.getInstance();
	private Admin admin;
	private Doctor doctor;
	private Patient patient;
	private int permission;
	private JFrame frame;
    private JTextField nameField, idField, emailField, addressField, permissionField, pricingField, medicalHistoryField,phoneField;
    private JLabel nameLabel, idLabel, emailLabel, addressLabel, permissionLabel, pricingLabel, medicalHistoryLabel,phoneLabel;
    private JButton updateButton, saveButton;
    private boolean isEditable = false; // Tracks if fields are editable
	
    
    public CreateProfile(int perm) {
        // Frame setup
    	this.permission = perm;
        frame = new JFrame("User Profile");
        frame.setSize(1000, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Main panel with padding
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        JPanel contentPanel = new JPanel();
        // Add padding
        //JPanel contentPanel = new JPanel(new GridLayout(4, 2, 10, 10)); // 4 rows, 2 columns layout
        if (permission == 1) {
        	contentPanel = new JPanel(new GridLayout(6, 2, 10, 10)); // 4 rows, 2 columns layout
        }
        else if (permission == 2) {
        	contentPanel = new JPanel(new GridLayout(7, 2, 10, 10)); // 8 rows, 2 columns layout
        }
        else if (permission == 3) {
        	contentPanel = new JPanel(new GridLayout(8, 2, 10, 10)); // 8 rows, 2 columns layout
        }
        else {
        	contentPanel = new JPanel(new GridLayout(5, 2, 10, 10)); // 8 rows, 2 columns layout
        }
        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20)); // Add padding around the grid
        
        // fill in User union data
        String userName = mainPerson.name();
        String userId = mainPerson.id();
        String userEmail = mainPerson.email();
        String userPhone = mainPerson.phone();
        String userPermission = Integer.toString(permission);
        // Labels and fields
        nameLabel = new JLabel("Name:");
        idLabel = new JLabel("ID:");
        emailLabel = new JLabel("Email:");
        permissionLabel = new JLabel("Permission:");
        phoneLabel = new JLabel("Phone Number: ");
        // Text fields
        nameField = createTextField(userName);
        idField = createTextField(userId);
        emailField = createTextField(userEmail);
        permissionField = createTextField(userPermission);
        phoneField = createTextField(userPhone);
        // Add components to content panel
        contentPanel.add(nameLabel);
        contentPanel.add(nameField);
        contentPanel.add(idLabel);
        contentPanel.add(idField);
        contentPanel.add(emailLabel);
        contentPanel.add(emailField);
        contentPanel.add(permissionLabel);
        contentPanel.add(permissionField);
        contentPanel.add(phoneLabel);
        contentPanel.add(phoneField);
        //fill in the user subtype label and fields
        if (permission ==1) {
    		admin=users.getAdmin(mainPerson.id());
    	}
    	else if (permission ==2) {
    		pricingLabel = new JLabel("Pricing:");
    		pricingField = createTextField("");
    		contentPanel.add(pricingLabel);
    	    contentPanel.add(pricingField);
    	}
    	else if (permission ==3) {
            addressLabel = new JLabel("Address:");
            medicalHistoryLabel = new JLabel("Medical History:");
            addressField = createTextField("");
            medicalHistoryField = createTextField("");
            contentPanel.add(addressLabel);
            contentPanel.add(addressField);        
            contentPanel.add(medicalHistoryLabel);
            contentPanel.add(medicalHistoryField);
            
    	}
        // Buttons
        updateButton = new JButton("Update Information");
        saveButton = new JButton("Save Changes");
        saveButton.setEnabled(false); // Save button is initially disabled

        contentPanel.add(updateButton);
        contentPanel.add(saveButton);

        // Add content panel to main panel
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        // Add main panel to frame
        frame.add(mainPanel);
        toggleEditable(true);

        // Action listeners
        updateButton.addActionListener(e -> toggleEditable(true));
        saveButton.addActionListener(e -> {
			try {
				saveChanges();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

        frame.setVisible(true);
    }

    private JTextField createTextField(String text) {
        JTextField textField = new JTextField(text);
        textField.setEditable(false);
        textField.setPreferredSize(new Dimension(300, 25)); // Set preferred width and height
        return textField;
    }

    private void toggleEditable(boolean editable) {
        // Toggle editability of text fields
        nameField.setEditable(editable);
        emailField.setEditable(editable);
        phoneField.setEditable(editable);
        if (permission==3) {
        	addressField.setEditable(editable);
        	medicalHistoryField.setEditable(editable);
        	idField.setEditable(editable);
        }
        if(permission ==2) {
        	pricingField.setEditable(editable);
        	idField.setEditable(editable);
        }
        //admin capable of editing permission and id
        if (permission ==1) {
        	idField.setEditable(editable);
        	// create account already has permission set up permissionField.setEditable(editable);
        }
        
        // Enable/Disable buttons
        updateButton.setEnabled(!editable);
        saveButton.setEnabled(editable);

        isEditable = editable;
    }

    private void saveChanges() throws IOException {
        if (isEditable) {
            // Save the changes (in a real app, you would send the data to the backend)
            String updatedName = nameField.getText();
            String updatedId = idField.getText();
            String updatedEmail = emailField.getText();
            String updatedPermission = permissionField.getText();
            String updatedPhone = phoneField.getText();
            if (permission==3) {
            	String updatedAddress = addressField.getText();
            	String updatedMedicalHistory = medicalHistoryField.getText();
            	Patient newPerson = new Patient(updatedAddress, updatedMedicalHistory,updatedEmail, 
            			updatedPhone,updatedName, updatedId, mainPerson.pass(), permission);
            	users.updatePatient(newPerson);
            }
            if (permission ==2) {
            	String updatedPricing = pricingField.getText();
            	Doctor newPerson = new Doctor( updatedEmail, updatedPhone,Double.parseDouble(updatedPricing),updatedName,
            			updatedId, mainPerson.pass(), permission);
            	users.updateDoctor(newPerson);
			
            }
            else {
            	Admin newPerson = new Admin(updatedEmail, updatedPhone,updatedName, updatedId,  mainPerson.pass(),permission);
            	users.updateAdmin(newPerson);
            	}

            // Display confirmation message
            JOptionPane.showMessageDialog(frame, "Changes saved successfully!");

            // Set fields back to non-editable
            toggleEditable(false);

        }
    }
    

}