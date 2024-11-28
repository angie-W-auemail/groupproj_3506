package appointment;

import java.util.ArrayList;
import java.util.List;

public class AppointmentBookingSystem {
    private List<Appointment> appointments;

    // Constructor to initialize the appointment list
    public AppointmentBookingSystem() {
        this.appointments = new ArrayList<>();
    }

    // Method to book a new appointment
    public String bookAppointment(String name, String date, String time, String description) {
        Appointment appointment = new Appointment(name, date, time, description);
        appointments.add(appointment);
        return "Appointment booked successfully!";
    }

    // Method to view all appointments
    public String viewAppointments() {
        if (appointments.isEmpty()) {
            return "No appointments booked.";
        } else {
            StringBuilder allAppointments = new StringBuilder("All Appointments:\n");
            for (Appointment appointment : appointments) {
                allAppointments.append("Appointment for ").append(appointment.getName()).append("\n");
                allAppointments.append("Date: ").append(appointment.getDate()).append("\n");
                allAppointments.append("Time: ").append(appointment.getTime()).append("\n");
                allAppointments.append("Description: ").append(appointment.getDescription()).append("\n");
                allAppointments.append("--------------------------\n");
            }
            return allAppointments.toString();
        }
    }

    // Method to cancel an appointment
    public String cancelAppointment(String name, String date, String time) {
        for (Appointment appointment : appointments) {
            if (appointment.getName().equals(name) && appointment.getDate().equals(date) && appointment.getTime().equals(time)) {
                appointments.remove(appointment);
                return "Appointment cancelled successfully!";
            }
        }
        return "Appointment not found.";
    }
}

