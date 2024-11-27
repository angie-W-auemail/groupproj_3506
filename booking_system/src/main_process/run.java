package main_process;

import java.io.*;
import java.util.ArrayList;

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
		//schedule.printAppointments();
		Schedule time = new Schedule(2022, 7, 23, 1, "p1", "d3"); 
		String comment = "patient claimes chest pain";
		String path = new java.io.File(".").getCanonicalPath()+"\\src\\appointments.csv";
		//schedule.updateDB(path);
		schedule.addSchedule(time);
		//ArrayList<Schedule> records = schedule.getRecords("p3");
//		for(int i=0;i<records.size();i++) {
//			System.out.println(records.get(i).appointmentString());
//		}
		//ArrayList<Schedule> recordsDoc = schedule.getRecords("p2");
		
		schedule.printAppointments();
		
	}

}

