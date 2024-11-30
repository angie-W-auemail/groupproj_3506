package appointment;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import schedule.ManageSchedule;
import user.Admin;
import user.Doctor;
import user.Patient;
import user.User;
import user.UserManagement;
import java.awt.*;
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

    public AppointmentView() {
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
        
        JLabel doctorLabel = new JLabel("Select Doctor:");
        String[] doctorNames = new String [users.doctorSize()] ;
        for(int i =0; i<users.doctorSize();i++) {
        	doctorNames[i] = users.getUser(2,i).name();
        }
        JComboBox<String> doctorDropdown = new JComboBox<>(doctorNames);
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
                if (cellValue.equals("Blocked")) {
                    cell.setBackground(Color.RED);
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
        blockButton.addActionListener(e -> blockSelectedTime());
        unblockButton.addActionListener(e -> unblockSelectedTime());

        // Populate initial schedule
        loadWeekSchedule();

        frame.setVisible(true);
    }

    private void loadWeekSchedule() {
        // Get selected date
        Date selectedDate = (Date) dateSpinner.getValue();
        LocalDate startDate = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(selectedDate), DateTimeFormatter.ISO_DATE)
                .with(java.time.DayOfWeek.MONDAY);

        // Clear existing rows
        tableModel.setRowCount(0);

        // Populate rows with time slots
        for (int hour = 9; hour <= 17; hour++) {
            String timeSlot = String.format("%02d:00 - %02d:00", hour, hour + 1);
            Object[] row = new Object[6];
            row[0] = timeSlot; // Time column
            Arrays.fill(row, 1, 6, ""); // Days (Monday to Friday) initialized to empty
            tableModel.addRow(row);
        }
    }

    private void blockSelectedTime() {
        int selectedRow = scheduleTable.getSelectedRow();
        int selectedColumn = scheduleTable.getSelectedColumn();

        if (selectedRow >= 0 && selectedColumn > 0) {
            tableModel.setValueAt("Blocked", selectedRow, selectedColumn);
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

}

