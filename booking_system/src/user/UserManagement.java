package user;
import java.util.ArrayList;
import java.util.*;
import java.util.stream.Collectors;
import java.io.*;
import java.text.MessageFormat;
public class UserManagement {
	private ArrayList<Admin> admin_list= new ArrayList<Admin>();
	private ArrayList<Doctor> doctor_list= new ArrayList<Doctor>();
	private ArrayList<Patient> patient_list= new ArrayList<Patient>();
	private ArrayList<String> id_list = new ArrayList<String>();
	private ArrayList<String> pass_list = new ArrayList<String>();

	public UserManagement(){};
	//read from db all arraylists
	public void getAll() throws IOException{
		String line = "";
		String currentPath = new java.io.File(".").getCanonicalPath();
		//read users data from csv
		String path1 = currentPath+"\\src\\admin.csv";
		String path2 = currentPath+"\\src\\doctors.csv";
		String path3 = currentPath+"\\src\\patients.csv";
		BufferedReader br = new BufferedReader(new FileReader(path1));
	    String[] myArray;
	    int count =0;
	    //read all saved admin
	    while ((line = br.readLine()) != null) {
	    	myArray= line.split(",");
	    	if(count>0) {
	    		Admin person = new Admin(myArray[4], myArray[3],myArray[0], myArray[1],  myArray[5],Integer.parseInt(myArray[2]));
	    		admin_list.add(person);
	    		id_list.add(person.id());
	    		pass_list.add(person.pass());
	    	}
	    	count++;
	    }
	    br.close();
	    //read all saved doctors
	    br = new BufferedReader(new FileReader(path2));
	    count =0;
	    while ((line = br.readLine()) != null) {
	    	myArray= line.split(",");
	    	if(count>0) {
	    		Doctor person1 = new Doctor( myArray[4], myArray[3],Double.parseDouble(myArray[6]),
        				myArray[0], myArray[1], myArray[5],Integer.parseInt(myArray[2]));
	    		doctor_list.add(person1);
	    		// Split the string by comma and collect into ArrayList
	    		ArrayList<String> patientsList = new ArrayList<>(Arrays.asList(myArray[7].split("\\|")));
	    		person1.setPatients(patientsList);
	    		id_list.add(person1.id());
	    		pass_list.add(person1.pass());
	    	}
	    	count++;
	    }
	    br.close();
	    //read all patients
	    br = new BufferedReader(new FileReader(path3));
	    count =0;
	    while ((line = br.readLine()) != null) {
	    	myArray= line.split(",");
	    	if(count>0) {
	    		Patient person2 = new Patient( myArray[7], myArray[6],myArray[4], myArray[3],
        				myArray[0],myArray[1], myArray[5],Integer.parseInt(myArray[2]));
	    		patient_list.add(person2);
	    		id_list.add(person2.id());
	    		pass_list.add(person2.pass());
	    	}
	    	count++;
	    }
	    br.close();
	    
	}
	public boolean matchID(String ID,String password) {
		if(id_list.indexOf(ID)==-1) { //if ID not found in id_list
			return false;
		}
		else if(pass_list.get(id_list.indexOf(ID)).equals(password)) {
			//if id match with the password
			return true;
		}
		else {
			//if id does not match password
			return false;
		}
	}
	//write a new line of entry to db
	public void writePerson (String path, String line)throws IOException {
		File file = new File(path);
		FileWriter fw = new FileWriter(file,true);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(line);
		bw.newLine();
		bw.close();
		fw.close();
	}
	
	//add line of entry new person
	public void addAdmin(Admin person) throws IOException{
		String path = new java.io.File(".").getCanonicalPath()+"\\src\\admin.csv";
		admin_list.add(person);
		String formatTemplate = "{0},{1},{2},{3},{4},{5}";
		String line = MessageFormat.format(formatTemplate, person.name(),person.id(),person.permission(),
				person.phone(),person.email(),person.pass());
		writePerson(path, line);
		
	}
	public void addDoctor(Doctor person) throws IOException{ 
		String path = new java.io.File(".").getCanonicalPath()+"\\src\\doctors.csv";
		doctor_list.add(person);
		String formatTemplate = "{0},{1},{2},{3},{4},{5},{6},{7}";
		String patients = person.getPatients();
		String line = MessageFormat.format(formatTemplate, person.name(),person.id(),person.permission(),
				person.phone(),person.email(),person.pass(),person.price(),patients);
		writePerson(path, line);
	}
	public void addPatient(Patient person) throws IOException {
		String path = new java.io.File(".").getCanonicalPath()+"\\src\\patients.csv";
		patient_list.add(person);
		String formatTemplate = "{0},{1},{2},{3},{4},{5},{6},{7}";
		String line = MessageFormat.format(formatTemplate, person.name(),person.id(),person.permission(),
				person.phone(),person.email(),person.pass(),person.medicalHistory(),person.address());
		writePerson(path, line);
	}
	
	
	
	public void removeAdmin(Admin person) {
		if (patient_list.indexOf(person)!=-1) {
			patient_list.remove(patient_list.indexOf(person));
		}
	}
	public void removeDoctor(Doctor person) {
		if (patient_list.indexOf(person)!=-1) {
			patient_list.remove(patient_list.indexOf(person));
		}
	}
	public void printUsers() {
		System.out.println("-------ADMIN--------");
		for(int i=0;i<admin_list.size();i++) {
			System.out.println("Name: "+admin_list.get(i).name()+" ID: "
					+admin_list.get(i).id()+" Permission: "+admin_list.get(i).permission()+
					" Phone: "+ admin_list.get(i).phone()+ " Email: "+ admin_list.get(i).email()+
					" Password: "+ admin_list.get(i).pass());
		}
		System.out.println("-------DOCTOR--------");
		for(int i=0;i<doctor_list.size();i++) {
			System.out.println("Name: "+doctor_list.get(i).name()+" ID: "
					+doctor_list.get(i).id()+" Permission: "+doctor_list.get(i).permission()+
					" Phone: "+ doctor_list.get(i).phone()+ " Email: "+ doctor_list.get(i).email()+
					" Password: "+ doctor_list.get(i).pass()+" Price: "+ doctor_list.get(i).price()+
					" Patients: "+doctor_list.get(i).getPatients());
		}
		System.out.println("-------PATIENT--------");
		for(int i=0;i<patient_list.size();i++) {
			System.out.println("Name: "+patient_list.get(i).name()+" ID: "
					+patient_list.get(i).id()+" Permission: "+patient_list.get(i).permission()+
					" Phone: "+ patient_list.get(i).phone()+ " Email: "+ patient_list.get(i).email()+
					" Password: "+ patient_list.get(i).pass()+" Price: "+ patient_list.get(i).address()+
					" Medical history: "+ patient_list.get(i).medicalHistory());
		}
	}         
}