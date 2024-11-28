//Group project on appointment booking system
package booking_system.src.main_process;

import javax.swing.SwingUtilities;

import booking_system.src.appointment.AppointmentBookingGUI;



/*************************
 * Run class stores the main process of MVC
 * 
 */
public class run {
    public static void main(String[] args) {
        // Run the GUI in the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Initialize the GUI (Appointment Booking)
                AppointmentBookingGUI gui = new AppointmentBookingGUI();
                gui.setVisible(true);  // Make sure the GUI is visible
            }
        });
    }
}
