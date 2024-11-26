package user;
/*
COURSE: COSC3506
code for the users portion of the booking system
contributor: ANQI WANG
*/
public class user {
	private String name;
	private String user_id;
	private String password;
	private int permission;
	user(String name, String user_id, String password, int permission){
		this.name=name;
		this.user_id = user_id;
		this.password=password;
		this.permission = permission;
	}
	public String retrieve_name() {
		return name;
	}
	public String retrieve_id() {
		return user_id;
	}
	
	public String retrieve_pass() {
		return password;
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
