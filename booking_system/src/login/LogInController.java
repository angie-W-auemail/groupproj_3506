package login;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import user.UserManagement;
import home.HomeAdmin;
import home.HomeDoctorView;
import home.HomePatientView;
import user.User;
public class LogInController {
    private LogInModel model;
    private LogInView view;
    private boolean loginSuccess;
    private int permission;
    private User person;
    private final UserManagement users = UserManagement.getInstance();
    private final User mainPerson =User.getInstance(); 
    
    public LogInController(LogInView view){
        this.view = view;
 
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
                    if (permission ==1) {
                    	HomeAdmin view  = new HomeAdmin();
                    	view.setVisible(true);
                    }
                    else if (permission ==2) {
                    	HomeDoctorView view  = new HomeDoctorView();
                    	view.setVisible(true);
                    	
                    }
                    else if (permission ==3) {
                    	HomePatientView view = new HomePatientView();
                    	view.setVisible(true);
                    	
                    }                   
                    
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
    	if (users.getAdmin(user.getUserName()).permission()!=0) {
    		person=users.getAdmin(user.getUserName());
    		permission = this.person.permission();
    	}
    	else if (users.getDoctor(user.getUserName()).permission()!=0) {
    		person=users.getDoctor(user.getUserName());
    		permission = this.person.permission();
    	}
    	else if (users.getPatient(user.getUserName()).permission()!=0) {
    		person=users.getPatient(user.getUserName());
    		this.permission =this.person.permission();
    	}
    	mainPerson.setUser(person.email(), person.phone(), person.name(), person.id(), person.pass(), permission);
    	return this.users.matchID(user.getUserName(),  user.getPassword());

      }
} 