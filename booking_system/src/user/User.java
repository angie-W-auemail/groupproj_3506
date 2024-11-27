package user;
/*
COURSE: COSC3506
code for the users portion of the booking system
fields: name, id, password, email, phone, permission
permission 1: admin
permission 2: doctor
permission 3: patient
contributor: ANQI WANG
*/
public class User {
	private String name;
	private String user_id;
	private String password;
	private int permission;
	private String email;
	private String phone;
	User(){};
	User(String email, String phone,String name, String user_id, String password, int permission){
		this.name=name;
		this.user_id = user_id;
		this.password=password;
		this.permission = permission;
		this.email=email;
		this.phone = phone;
	}
	public void setUser(String email, String phone,String name, String user_id, String password, int permission){
		this.name=name;
		this.user_id = user_id;
		this.password=password;
		this.permission = permission;
		this.email=email;
		this.phone = phone;
	}
	public String name() {
		return name;
	}
	public String email() {
		return email;
	}
	public String phone() {
		return phone;
	}
	public String id() {
		return user_id;
	}
	
	public String pass() {
		return password;
	}
	public int permission() {
		return permission;
	}
	public Boolean isAdmin() {
		return (permission==1);
	}
	public Boolean isDoc() {
		return (permission==2);
	}
	public Boolean isPatient() {
		return (permission==3);
	}
}
