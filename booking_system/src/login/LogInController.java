package login;
import appointment.AppointmentBookingGUI;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import user.UserManagement;
public class LogInController {
    private LogInModel model;
    private LogInView view;
    private UserManagement users;
    private boolean loginSuccess;
    public LogInController(LogInView view, UserManagement user_list){
        this.view = view;
        users = user_list;
 
        view.addLoginListener(new LoginListener());
    }
 
    class LoginListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                model = view.getUser();
                if(checkUser(model)){
                    view.showMessage("Login succesfully!");
                    logged();
                    view.dispose();
                    AppointmentBookingGUI gui = new AppointmentBookingGUI();
            		gui.setVisible(true);
                    
                }else{
                	
                    view.showMessage("Invalid username and/or password!");
                }                
            } catch (Exception ex) {
                view.showMessage(ex.getStackTrace().toString());
            }
        }
    }
    public boolean logged() {
    	this.loginSuccess=true;
    	return loginSuccess;
    }
    public boolean checkUser(LogInModel user) throws Exception {
 
    	return this.users.matchID(user.getUserName(),  user.getPassword());

      }
} 