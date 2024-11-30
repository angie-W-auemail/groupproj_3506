package user;
import java.util.ArrayList;
import java.util.*;
import java.io.*;
import java.text.MessageFormat;
public class UserManagement {
	private ArrayList<Admin> admin_list= new ArrayList<Admin>();
	private ArrayList<Doctor> doctor_list= new ArrayList<Doctor>();
	private ArrayList<Patient> patient_list= new ArrayList<Patient>();
	private ArrayList<String> id_list = new ArrayList<String>();
	private ArrayList<String> pass_list = new ArrayList<String>();
	private static final UserManagement instance = new UserManagement();

	public UserManagement(){};
	//read from db all arraylists
	 public static UserManagement getInstance() {
	        return instance;
	  }
	 public User getUser(int permission, int index) {
		 if (permission ==1) {
			 return (User)(admin_list.get(index));
		 }
		 else if (permission ==2) {
			 return (User)(doctor_list.get(index));
		 }
		 else if (permission ==3) {
			 return (User)(patient_list.get(index));
		 }
		 return new User();
	 }
	 public int adminSize () {
		 return admin_list.size();
	 }
	 public int doctorSize () {
		 return doctor_list.size();
	 }
	 public int patientSize () {
		 return patient_list.size();
	 }
	 
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
		id_list.add(person.id());
		pass_list.add(person.pass());
		String formatTemplate = "{0},{1},{2},{3},{4},{5}";
		String line = MessageFormat.format(formatTemplate, person.name(),person.id(),person.permission(),
				person.phone(),person.email(),person.pass());
		writePerson(path, line);
		
	}
	public void addDoctor(Doctor person) throws IOException{ 
		String path = new java.io.File(".").getCanonicalPath()+"\\src\\doctors.csv";
		doctor_list.add(person);
		id_list.add(person.id());
		pass_list.add(person.pass());
		String formatTemplate = "{0},{1},{2},{3},{4},{5},{6},{7}";
		String patients = person.getPatients();
		String line = MessageFormat.format(formatTemplate, person.name(),person.id(),person.permission(),
				person.phone(),person.email(),person.pass(),person.price(),patients);
		writePerson(path, line);

	}
	public void addPatient(Patient person) throws IOException {
		String path = new java.io.File(".").getCanonicalPath()+"\\src\\patients.csv";
		patient_list.add(person);
		id_list.add(person.id());
		pass_list.add(person.pass());
		String formatTemplate = "{0},{1},{2},{3},{4},{5},{6},{7}";
		String line = MessageFormat.format(formatTemplate, person.name(),person.id(),person.permission(),
				person.phone(),person.email(),person.pass(),person.medicalHistory(),person.address());
		writePerson(path, line);
	}
	
	//delete file and re-input
	public void updateDB(String path,int permission) throws IOException{
		String line ="";
		File file = new File(path);
		file.delete();
		FileWriter fw = new FileWriter(file,true);
		BufferedWriter bw = new BufferedWriter(fw);
		if (permission==1) {
			String formatTemplate = "{0},{1},{2},{3},{4},{5}";
			bw.write("name,id,permission,phone,email,pass");
			bw.newLine();
			for (int i=0; i<admin_list.size();i++) {
				Admin person = admin_list.get(i);
				line = MessageFormat.format(formatTemplate, person.name(),person.id(),person.permission(),
						person.phone(),person.email(),person.pass());
				bw.write(line);
				bw.newLine();
				
			}
		}
		else if (permission==2) {
			String formatTemplate = "{0},{1},{2},{3},{4},{5},{6},{7}}";
			bw.write("name,id,permission,phone,email,pass,price,patients");
			bw.newLine();
			for (int i=0; i<doctor_list.size();i++) {
				Doctor person = doctor_list.get(i);
				line = MessageFormat.format(formatTemplate, person.name(),person.id(),person.permission(),
						person.phone(),person.email(),person.pass(),person.price(),person.getPatients());
				bw.write(line);
				bw.newLine();
			}
		}
		else {
			String formatTemplate = "{0},{1},{2},{3},{4},{5},{6},{7}";
			bw.write("name,id,permission,phone,email,pass,medicalHistory,address");
			bw.newLine();
			for (int i=0; i<patient_list.size();i++) {
				Patient person = patient_list.get(i);
				line = MessageFormat.format(formatTemplate, person.name(),person.id(),person.permission(),
						person.phone(),person.email(),person.pass(),person.medicalHistory(),person.address());
				bw.write(line);
				bw.newLine();
			}
		}
		
		bw.close();
		fw.close();
	}
	public void removeAdmin(Admin person) throws IOException{
		int index= id_list.indexOf(person.id());
		if (admin_list.indexOf(person)!=-1) {
			admin_list.remove(admin_list.indexOf(person));
			id_list.remove(index);
			pass_list.remove(index);
			String path = new java.io.File(".").getCanonicalPath()+"\\src\\admin.csv";
			updateDB(path,1);
		}
	}
	public void removeDoctor(Doctor person) throws IOException{
		int index= id_list.indexOf(person.id());
		if (doctor_list.indexOf(person)!=-1) {
			doctor_list.remove(doctor_list.indexOf(person));
			id_list.remove(index);
			pass_list.remove(index);
			String path = new java.io.File(".").getCanonicalPath()+"\\src\\doctors.csv";
			updateDB(path,2);
		}
	}
	public void removePatient(Patient person) throws IOException{
		int index= id_list.indexOf(person.id());
		if (patient_list.indexOf(person)!=-1) {
			patient_list.remove(patient_list.indexOf(person));
			id_list.remove(index);
			pass_list.remove(index);
			String path = new java.io.File(".").getCanonicalPath()+"\\src\\patients.csv";
			updateDB(path,3);
		}
	}
	public Admin getAdmin(String id) {
		for (int i=0; i<admin_list.size();i++) {
			if(admin_list.get(i).id().equals(id)) {
				return admin_list.get(i);
			}
		}
		return new Admin();
	}
	public Doctor getDoctor(String id) {
		for (int i=0; i<doctor_list.size();i++) {
			if(doctor_list.get(i).id().equals(id)) {
				return doctor_list.get(i);
			}
		}
		return new Doctor();
	}
	public Patient getPatient(String id) {
		for (int i=0; i<patient_list.size();i++) {
			if(patient_list.get(i).id().equals(id)) {
				return patient_list.get(i);
			}
		}
		return new Patient();
	}
	public void updateAdmin(Admin person) throws IOException{
		Admin current =  getAdmin(person.id());
		admin_list.remove(current);
		admin_list.add(person);
		String path = new java.io.File(".").getCanonicalPath()+"\\src\\admin.csv";
		updateDB(path,1);
	}
	public void updateDoctor(Doctor person) throws IOException{
		Doctor current = getDoctor(person.id());
		doctor_list.remove(current);
		doctor_list.add(person);
		//doctor_list.remove(doctor_list.indexOf(person));
		String path = new java.io.File(".").getCanonicalPath()+"\\src\\doctors.csv";
		updateDB(path,2);
	}
	public void updatePatient(Patient person) throws IOException{
		Patient current = getPatient(person.id());
		patient_list.remove(current);
		patient_list.add(person);
		String path = new java.io.File(".").getCanonicalPath()+"\\src\\patients.csv";
		updateDB(path,3);
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