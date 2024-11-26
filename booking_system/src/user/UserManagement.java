package user;
import java.util.ArrayList;
public class UserManagement {
	private ArrayList<User> user_list= new ArrayList<User>();
	int permission;
	UserManagement(){};
	public void add_user(String name, String user_id, String password, int permission) {
		
	}
	public void print_users() {
		for(int i=0;i<user_list.size();i++) {
			System.out.println(user_list.get(i).retrieve_name()+" ");
		}
	}         
}
