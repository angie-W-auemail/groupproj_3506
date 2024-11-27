package user;
import java.util.ArrayList;

/*
code for the doctor settings
fields: doctor email, doctor phone, doctor pricing, doctor assigned patients
functions: constructor setup for all doctor info other than patients
get_doctor return doctor instance
contributor: ANQI WANG
*/
public class Doctor extends User {
	private double price;
	private ArrayList<String> patients= new ArrayList<String>();
	Doctor(){};
	Doctor(String email, String phone,double price,String name, String user_id, String password, int permission){
			super(email, phone, name, user_id,  password,permission); 
			this.price = price;
	}
	public double price() {
		return price;
	}
	public ArrayList<String> Patients(){
		return patients;
	}
	public boolean search(String id) {
		if(patients.indexOf(id)!=-1) {
			return false;
		}
		else {
			return true;
		}
	}
	public void addPatient(String id) {
		this.patients.add(id);
	}
	public void removePatient(String id) {
		patients.remove(patients.indexOf(id));
	}
}