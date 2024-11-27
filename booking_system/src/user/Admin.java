package user;

public class Admin extends User{
	Admin(){};
	public Admin(String email, String phone,String name, String user_id, String password, int permission){
			super(email, phone,name, user_id,  password,permission); 
	}
}
