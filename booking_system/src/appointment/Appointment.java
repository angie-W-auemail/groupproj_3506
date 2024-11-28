package appointment;


public class Appointment {
    private String name;        // Name of the person booking the appointment
    private String date;        // Date of the appointment (e.g., "2024-11-30")
    private String time;        // Time of the appointment (e.g., "14:00")
    private String description; // Description of the appointment

    // Constructor to initialize an Appointment object
    public Appointment(String name, String date, String time, String description) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.description = description;
    }

    // Getter and Setter methods
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // A method to display appointment details (optional)
    public void displayAppointment() {
        System.out.println("Appointment for " + name);
        System.out.println("Date: " + date);
        System.out.println("Time: " + time);
        System.out.println("Description: " + description);
    }
}
