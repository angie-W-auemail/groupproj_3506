package tests;
import user.UserManagement;
import java.io.*;
import user.Admin;
import user.Doctor;
import user.Patient;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class testCases {

	@Test
	void testDoctor() {
		UserManagement users = new UserManagement();
		user.Doctor doctorTest = new user.Doctor("james@gmail.com","4166655432",55, "James Clash", "5555", "test", 2);
		boolean nameResult = doctorTest.name() == "James Clash";
		boolean emailResult = doctorTest.email() == "james@gmail.com";
		boolean numResult = doctorTest.phone() == "123456899";
		assertTrue(nameResult);
		assertTrue(emailResult);
		assertTrue(numResult);	
		
	}
	@Test
	void testAdmin() {
		UserManagement users = new UserManagement();
		user.Admin adminTest = new user.Admin("james@gmail.com","4166655432","James Clash", "5555", "test", 1);
		boolean nameResult = adminTest.name() == "James Clash";
		boolean emailResult = adminTest.email() == "james@gmail.com";
		boolean numResult = adminTest.phone() == "4166655432";
		assertTrue(nameResult);
		assertTrue(emailResult);	
		assertTrue(numResult);	
		
	}
	@Test
	void testPatient() {
		UserManagement users = new UserManagement();
		user.Patient patientTest = new user.Patient("18 algoma road", "fractured ankle" , "james@gmail.com", "4163829321", "jude clash", "413123", "test", 3);
		boolean nameResult = patientTest.name() == "James Clash";
		boolean emailResult = patientTest.email() == "james@gmail.com";
		boolean numResult = patientTest.phone() == "4166655432";
		assertTrue(nameResult);
		assertTrue(emailResult);	
		assertTrue(numResult);	
		
	}

}
