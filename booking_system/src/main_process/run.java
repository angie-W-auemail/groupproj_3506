package main_process;

import java.io.*;

import schedule.ManageSchedule;
import schedule.Schedule;
import user.Admin;
import user.Doctor;
import user.Patient;
import user.UserManagement;
/*************************
 * Run class stores the main process of MVC
 * 
 */
public class run {

	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
		//set up instance of user management and load stored users
		UserManagement users = new UserManagement();
		users.getAll();
		Patient person = users.getPatient("p6");
		users.removePatient(person);
		users.addPatient(person);
		users.printUsers();
		ManageSchedule schedule = new ManageSchedule();
		schedule.getAll();
		schedule.printAppointments();
		Schedule time = new Schedule(2022, 4, 15, 2, "d2", "p4"); 
		schedule.addSchedule(time);
		schedule.removeSchedule(time);
		schedule.printAppointments();
		
	}

}

