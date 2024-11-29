//Group project on appointment booking system
package main_process;

import java.io.IOException;

import javax.swing.SwingUtilities;

import appointment.AppointmentBookingGUI;
//import appointment.AppointmentBookingGUI;
import login.LogInController;
import login.LogInView;
import user.UserManagement;
import appointment.AppointmentBookingGUI;


/*************************
 * Run class stores the main process of MVC
 * 
 */
public class run {
    public static void main(String[] args)throws IOException {
        // Run the GUI in the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
            	
                // Initialize the GUI (Appointment Booking)
            	UserManagement users = UserManagement.getInstance();
            	try {
					users.getAll();
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	LogInView view       = new LogInView();
                LogInController controller = new LogInController(view);        
                view.setVisible(true);
                
                //AppointmentBookingGUI gui = new AppointmentBookingGUI();
        		//gui.setVisible(true);

            
            }
        });
    }
}
