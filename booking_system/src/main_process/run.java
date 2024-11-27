package main_process;

import java.io.*;

import user.Admin;
import user.Doctor;
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
		
//		String name="Kris Effery";
//		String user_id = "0005";
//		String pass = "krseeefff";
//		int permission = 1;
//		String email="kriseffff@gmail.com";
//		String phone = "416-167-2136";
//		double price = 60.55;
//		Doctor person = new Doctor(email, phone,price, name, user_id,  pass,permission);
//		users.addDoctor(person);
		
		
		
		users.printUsers();
	}

}

