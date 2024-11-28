package user;
/*
code for the patient settings
fields: patient address, medical history
functions: 
setPatient change patient settings

contributor: ANQI WANG
*/
public class Patient extends User{
	private String address;
	private String medical_history;
	public Patient(){};
	Patient(String address, String medical_history,String email, String phone,String name, String user_id, String password, int permission){
			super(email, phone,name, user_id,  password,permission); 
			this.address=address;
			this.medical_history=medical_history;
	}
	public String address() {
		return address;
	}
	public String medicalHistory() {
		return medical_history;
	}
	public void setPatient(Patient newPatient) {
		super.setUser(newPatient.email(),newPatient.phone(),newPatient.name(), newPatient.id(),newPatient.pass(),newPatient.permission());
		this.address = newPatient.address();
		this.medical_history = newPatient.medicalHistory();
	}
}