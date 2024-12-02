package TreatementRecords;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import schedule.ManageSchedule;
import schedule.Schedule;
import user.Patient;
import user.User;
import user.UserManagement;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class PatientRecord{
	private final User selectedPatient =User.getInstance();
	private final UserManagement users = UserManagement.getInstance();
	private final ManageSchedule schedule = ManageSchedule.getInstance();
    public PatientRecord() throws IOException {
        // Create the frame
        JFrame frame = new JFrame("Patient Record");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        // Create the patient details panel
        JPanel patientDetailsPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        patientDetailsPanel.setBorder(BorderFactory.createTitledBorder("Patient Details"));

        // Row 1: ID, Name, Email
        JPanel row1Panel = new JPanel(new GridLayout(1, 6, 10, 10));
        JLabel idLabel = new JLabel("Patient ID:");
        JTextField idField = new JTextField(selectedPatient.id());
        idField.setEditable(false);

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField(selectedPatient.name());
        nameField.setEditable(false);

        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField(selectedPatient.email());
        emailField.setEditable(false);

        row1Panel.add(idLabel);
        row1Panel.add(idField);
        row1Panel.add(nameLabel);
        row1Panel.add(nameField);
        row1Panel.add(emailLabel);
        row1Panel.add(emailField);
        
        Patient newPatient = users.getPatient(selectedPatient.id());

        // Row 2: Address, Phone
        JPanel row2Panel = new JPanel(new GridLayout(1, 4, 10, 10));
        JLabel addressLabel = new JLabel("Address:");
        JTextField addressField = new JTextField(newPatient.address());
        addressField.setEditable(false);

        JLabel phoneLabel = new JLabel("Phone:");
        JTextField phoneField = new JTextField(newPatient.phone());
        phoneField.setEditable(false);

        row2Panel.add(addressLabel);
        row2Panel.add(addressField);
        row2Panel.add(phoneLabel);
        row2Panel.add(phoneField);

        // Add rows to the patient details panel
        patientDetailsPanel.add(row1Panel);
        patientDetailsPanel.add(row2Panel);

        // Create the appointments table with a custom table model
        String[] columnNames = {"Appointment Date", "Doctor Comment", "Prescription", "Payment"};
        schedule.getAll();
        ArrayList<Schedule> appointment_list = schedule.getRecords(newPatient.id());
        Schedule visit;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH");

        Object[][] data = new Object [appointment_list.size()][4];
        for (int i=0; i<appointment_list.size();i++){
        	visit = appointment_list.get(i);
        	System.out.println(users.getDoctor(visit.doctor()).id());
        	data[i][0]=formatter.format(visit.getTime());
        	//data[i][1]=users.getDoctor(visit.doctor()).name();
        	data[i][1]=visit.comment();
        	data[i][2]=visit.prescription();
        	data[i][3]=users.getDoctor(visit.doctor()).price();
        };

        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Allow editing for "Doctor Comment" and "Prescription" columns (1 and 2)
                return column == 1 || column == 2;
            }
        };

        JTable appointmentTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(appointmentTable);
        tableScrollPane.setBorder(BorderFactory.createTitledBorder("Appointments"));

        // Save Changes button
        JButton saveButton = new JButton("Save Changes");
        saveButton.addActionListener(e -> {
            int rowCount = tableModel.getRowCount();
            StringBuilder message = new StringBuilder("Updated Appointment Data:\n");
            for (int i = 0; i < rowCount; i++) {
                String date = tableModel.getValueAt(i, 0).toString();
                String comment = tableModel.getValueAt(i, 1).toString();
                String prescription = tableModel.getValueAt(i, 2).toString();
                String payment = tableModel.getValueAt(i, 3).toString();
                message.append("Date: ").append(date)
                        .append(", Comment: ").append(comment)
                        .append(", Prescription: ").append(prescription)
                        .append(", Payment: ").append(payment)
                        .append("\n");
            }
            JOptionPane.showMessageDialog(frame, message.toString());
        });

        // Send Email button
        JButton emailButton = new JButton("Send Email");
        emailButton.addActionListener(e -> {
            StringBuilder emailContent = new StringBuilder();
            emailContent.append("Patient Information:\n")
                    .append("ID: ").append(idField.getText()).append("\n")
                    .append("Name: ").append(nameField.getText()).append("\n")
                    .append("Email: ").append(emailField.getText()).append("\n")
                    .append("Address: ").append(addressField.getText()).append("\n")
                    .append("Phone: ").append(phoneField.getText()).append("\n\n")
                    .append("Medical History:\n").append("N/A").append("\n\n")
                    .append("Appointments:\n");

            int rowCount = tableModel.getRowCount();
            for (int i = 0; i < rowCount; i++) {
                emailContent.append("Date: ").append(tableModel.getValueAt(i, 0))
                        .append(", Comment: ").append(tableModel.getValueAt(i, 1))
                        .append(", Prescription: ").append(tableModel.getValueAt(i, 2))
                        .append(", Payment: ").append(tableModel.getValueAt(i, 3))
                        .append("\n");
            }

            JOptionPane.showMessageDialog(frame, "Email Content:\n\n" + emailContent.toString());
            // Integrate your email sending logic here.
        });

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(saveButton);
        buttonPanel.add(emailButton);

        // Add components to the frame
        frame.add(patientDetailsPanel, BorderLayout.NORTH);
        frame.add(tableScrollPane, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Make the frame visible
        frame.setVisible(true);
    }
}

