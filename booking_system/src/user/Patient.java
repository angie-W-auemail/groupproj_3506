package user;

public class Patient extends User{
	private String address;
	private String medical_history;
	Patient(){};
	Patient(String address, String medical_history,String email, String phone,String name, String user_id, String password, int permission){
			super(email, phone,name, user_id,  password,permission); 
			this.address=address;
			this.medical_history=medical_history;
	}
	public void set_Patient(Patient newPatient) {
		super.setUser(newPatient.email(),newPatient.phone(),);
	}
}
