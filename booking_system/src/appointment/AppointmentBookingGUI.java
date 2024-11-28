package appointment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AppointmentBookingGUI {
    private JFrame frame;
    private AppointmentBookingSystem system;

    private JTextField nameField;
    private JTextField dateField;
    private JTextField timeField;
    private JTextField descriptionField;
    private JTextArea appointmentArea;

    public AppointmentBookingGUI() {
        system = new AppointmentBookingSystem();
        frame = new JFrame("Appointment Booking System");

        // Initialize UI components
        nameField = new JTextField(20);
        dateField = new JTextField(20);
        timeField = new JTextField(20);
        descriptionField = new JTextField(20);
        appointmentArea = new JTextArea(10, 40);
        appointmentArea.setEditable(false);

        JButton bookButton = new JButton("Book Appointment");
        JButton viewButton = new JButton("View Appointments");
        JButton cancelButton = new JButton("Cancel Appointment");

        // Set up layout
        frame.setLayout(new FlowLayout());

        frame.add(new JLabel("Name:"));
        frame.add(nameField);
        frame.add(new JLabel("Date (YYYY-MM-DD):"));
        frame.add(dateField);
        frame.add(new JLabel("Time (HH:MM):"));
        frame.add(timeField);
        frame.add(new JLabel("Description:"));
        frame.add(descriptionField);

        frame.add(bookButton);
        frame.add(viewButton);
        frame.add(cancelButton);
        frame.add(new JScrollPane(appointmentArea));

        // Action Listeners
        bookButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String date = dateField.getText();
                String time = timeField.getText();
                String description = descriptionField.getText();
                String result = system.bookAppointment(name, date, time, description);
                appointmentArea.setText(result);
            }
        });

        viewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String result = system.viewAppointments();
                appointmentArea.setText(result);
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String date = dateField.getText();
                String time = timeField.getText();
                String result = system.cancelAppointment(name, date, time);
                appointmentArea.setText(result);
            }
        });

        // Finalize frame setup
        frame.setSize(500, 400); // Make sure the frame size is appropriate
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Ensure it closes correctly
        frame.setVisible(true); // Make the frame visible
    }

    public static void main(String[] args) {
        // Ensuring GUI runs on the EDT (Event Dispatch Thread)
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new AppointmentBookingGUI(); // Initialize the GUI
            }
        });
    }

	public void setVisible(boolean b) {
		// TODO Auto-generated method stub
		
	}
}
