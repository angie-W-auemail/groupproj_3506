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
		
		users.printUsers();
	}

}

