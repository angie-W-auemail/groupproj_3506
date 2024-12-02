package user_accounts;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import TreatementRecords.PatientRecord;
import user.Admin;
import user.Doctor;
import user.Patient;
import user.UserManagement;
import user.User;
import java.awt.*;
import profile.CreateProfile;
import profile.ProfileView;
import schedule.ManageSchedule;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class PatientsView {
	private final User mainPerson =User.getInstance();
	private final User selectedPatient =User.getInstance();
	private final UserManagement users = UserManagement.getInstance();
	public  PatientsView() {
		Doctor mainDoc = users.getDoctor(mainPerson.id());
		ArrayList<String> patients = mainDoc.Patients();
        // Create the frame
        JFrame frame = new JFrame("Patient List");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        // Create the table
        String[] columnNames = {"Patient ID", "Patient Name", "Patient Email"};
        Object[][] data = new Object [patients.size()][3];
        String id;
        Patient patient;
        for (int i=0; i<patients.size(); i++){
        	
        	id =patients.get(i).replace("}", ""); //patient id
        	System.out.println(patients.get(i));
        	patient = users.getPatient(id);
        	data[i][i]=patients.get(i);
        	data[i][1]=patient.name();
        	data[i][2]=patient.email();
        	
        };

        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Create the button
        JButton recordButton = new JButton("View Patient Record");
        recordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    String patientId = table.getValueAt(selectedRow, 0).toString();
                    String patientName = table.getValueAt(selectedRow, 1).toString();
                    Patient selectedPerson = users.getPatient(patientId);
                    //email, String phone,String name, String user_id, String password, int permission
                    selectedPatient.setUser(selectedPerson.email(), selectedPerson.phone(),selectedPerson.name(),
                    		selectedPerson.id(),selectedPerson.pass(), selectedPerson.permission());
                    try {
						PatientRecord view = new PatientRecord();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                    
                    
                } else {
                    JOptionPane.showMessageDialog(frame, "Please select a patient from the list.");
                }
            }
        });

        // Add components to the frame
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(recordButton, BorderLayout.SOUTH);

        // Make the frame visible
        frame.setVisible(true);
    }
}
