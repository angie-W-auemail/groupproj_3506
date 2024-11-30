package appointment;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import schedule.ManageSchedule;
import schedule.Schedule;
import user.Admin;
import user.Doctor;
import user.Patient;
import user.User;
import user.UserManagement;
import java.awt.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class AppointmentView {

    private JFrame frame;
    private JTable scheduleTable;
    private DefaultTableModel tableModel;
    private JSpinner dateSpinner;
    private JButton blockButton, unblockButton;
    //load the logged in user and users list and schedule_list;
    private final User mainPerson =User.getInstance();
	private final UserManagement users = UserManagement.getInstance();
	private final ManageSchedule schedule = ManageSchedule.getInstance();
	private Admin admin;
	private Doctor doctor;
	private Patient patient;
	private User user;
	private int permission;
    private String[] doctorNames = new String [users.doctorSize()] ;
    private JComboBox<String> doctorDropdown;

    public AppointmentView() throws IOException {
    	schedule.getAll();
        // Frame setup
        frame = new JFrame("Doctor Schedule - Appointment Page");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Top panel for date selector
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel dateLabel = new JLabel("Select Date:");
        dateSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd");
        dateSpinner.setEditor(dateEditor);
        JButton showWeekButton = new JButton("Show Week Schedule");
        topPanel.add(dateLabel);
        topPanel.add(dateSpinner);
        topPanel.add(showWeekButton);
        // top panel for doctor selector
        JLabel doctorLabel = new JLabel("Select Doctor:");

        for(int i =0; i<users.doctorSize();i++) {
        	doctorNames[i] = users.getUser(2,i).name();
  
        }
      
        doctorDropdown = new JComboBox<>(doctorNames);
        doctorDropdown.setPreferredSize(new Dimension(200, 25));
        topPanel.add(doctorLabel);
        topPanel.add(doctorDropdown);
        
        
        
        
        // Table setup
        String[] columnNames = {"Time", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Prevent editing of time column
                return column != 0;
            }
        };
        scheduleTable = new JTable(tableModel);
        scheduleTable.setRowHeight(30);

        // Custom renderer for blocked hours
        scheduleTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                String cellValue = value != null ? value.toString() : "";
                if (cellValue.equals("Booked")) {
                    cell.setBackground(Color.BLUE);
                    cell.setForeground(Color.WHITE);
                } else {
                    cell.setBackground(Color.WHITE);
                    cell.setForeground(Color.BLACK);
                }
                return cell;
            }
        });

        JScrollPane tableScrollPane = new JScrollPane(scheduleTable);

        // Bottom panel for block/unblock buttons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        blockButton = new JButton("Make Appointmnet");
        unblockButton = new JButton("Cancel Appointment");
        bottomPanel.add(blockButton);
        bottomPanel.add(unblockButton);

        // Add components to frame
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(tableScrollPane, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        // Add action listeners
        showWeekButton.addActionListener(e -> loadWeekSchedule());
        blockButton.addActionListener(e -> {
			try {
				blockSelectedTime();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
        unblockButton.addActionListener(e -> unblockSelectedTime());
        doctorDropdown.addActionListener(e -> loadSchedule(doctorDropdown.getSelectedItem().toString()));

        // Populate initial schedule
        loadWeekSchedule();
        frame.setVisible(true);
    }
    private void loadSchedule(String name) {
    	
    	Date selectedDate = (Date) dateSpinner.getValue();
        LocalDate startDate = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(selectedDate), DateTimeFormatter.ISO_DATE)
                .with(java.time.DayOfWeek.MONDAY);
        System.out.println(name);
        String id=users.doctorID(name);
    	ArrayList <Schedule> blocks = schedule.getRecorDoc(id);
    	int year,mon,day;
    	int startY = startDate.getYear();
    	int startM = startDate.getMonthValue();
    	int startD = startDate.getDayOfMonth();
    	//System.out.println(startY+" "+startM+" "+blocks.size());
    	for (int i=0;i<blocks.size();i++) {
    		Date current=blocks.get(i).getTime();
    		year = current.getYear();
    		mon = current.getMonth();
    		day = current.getDate();
    		for(int j=0; j<5;j++) {
    			startD = startDate.plusDays(j).getDayOfMonth();
    			startM = startDate.plusDays(j).getMonthValue();
    			startD = startDate.plusDays(j).getDayOfMonth();
    			if(startY==year&&startM==mon&&startD==day) {
    				tableModel.setValueAt("Booked", current.getHours()-8, j+1);
    			}
    		}
    	}
    }
    private void loadWeekSchedule() {
    	
        // Get selected date
        Date selectedDate = (Date) dateSpinner.getValue();
        int year = selectedDate.getYear();
        int month = selectedDate.getMonth();
        int day = selectedDate.getDate();
        //LocalDate today = LocalDate.now();
        
        LocalDate startDate = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(selectedDate), DateTimeFormatter.ISO_DATE)
                .with(java.time.DayOfWeek.MONDAY);
        //add date to column
        String[] columnNames = new String[6];
        columnNames[0] = "Time"; // Time column header
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for (int i = 0; i < 5; i++) {
            LocalDate currentDay = startDate.plusDays(i);
            columnNames[i + 1] = currentDay.getDayOfWeek().name() + " (" + currentDay.format(dateFormatter) + ")";
        }
        tableModel.setColumnIdentifiers(columnNames);

        // Clear existing rows
        tableModel.setRowCount(0);

        // Populate rows with time slots
        for (int hour = 9; hour < 17; hour++) {
            String timeSlot = String.format("%02d:00 - %02d:00", hour, hour + 1);
            Object[] row = new Object[6];
            row[0] = timeSlot; // Time column
            Arrays.fill(row, 1, 6, ""); // Days (Monday to Friday) initialized to empty
            tableModel.addRow(row);
        }
        
        loadSchedule(doctorDropdown.getSelectedItem().toString());
    }

    private void blockSelectedTime() throws IOException {
    	
        int selectedRow = scheduleTable.getSelectedRow();
        int selectedColumn = scheduleTable.getSelectedColumn();
        Date selectedDate = (Date) dateSpinner.getValue();
        LocalDate date = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(selectedDate), DateTimeFormatter.ISO_DATE);
        String id = showPatientIDPopup();
        String doctorId=users.doctorID(doctorDropdown.getSelectedItem().toString());
        
        System.out.println(date.getYear()+ date.getMonthValue()+ 
       		 date.getDayOfMonth()+(selectedRow+8));
        Schedule selected = new Schedule(date.getYear(), date.getMonthValue(), 
        		 date.getDayOfMonth(), selectedRow+8, id,  doctorId);
        
        

        if (selectedRow >= 0 && selectedColumn > 0) {
            tableModel.setValueAt("Booked", selectedRow, selectedColumn);
            schedule.addSchedule(selected);
            
        } else {
            JOptionPane.showMessageDialog(frame, "Please select a time slot for a specific day.");
        }
    }

    private void unblockSelectedTime() {
        int selectedRow = scheduleTable.getSelectedRow();
        int selectedColumn = scheduleTable.getSelectedColumn();

        if (selectedRow >= 0 && selectedColumn > 0) {
            tableModel.setValueAt("", selectedRow, selectedColumn);
        } else {
            JOptionPane.showMessageDialog(frame, "Please select a blocked time slot to unblock.");
        }
    }
    private static String showPatientIDPopup() {
        // Create a panel to hold the input components
        JPanel panel = new JPanel();
        String patientID="";
        // Add a label and text field for patient_id
        JLabel label = new JLabel("Enter Patient ID:");
        JTextField textField = new JTextField(15); // Text field with 15-character width

        panel.add(label);
        panel.add(textField);

        // Display the dialog
        int result = JOptionPane.showConfirmDialog(
                null, 
                panel, 
                "Patient ID Input", 
                JOptionPane.PLAIN_MESSAGE
        );

        // Handle user input
        if (result == JOptionPane.OK_OPTION) {
            patientID = textField.getText().trim();
            if (patientID.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Patient ID cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                return patientID;
            }
        } 
        return patientID;
    }

}

