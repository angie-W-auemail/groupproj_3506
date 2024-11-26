package user;
import java.util.ArrayList;
public class user_management {
	ArrayList<user> user_list= new ArrayList<user>();
	int permission;
	public void add_user(String name, String user_id, String password, int permission) {
		
	}
	public void print_users() {
		for(int i=0;i<user_list.size();i++) {
			System.out.println(user_list.get(i).retrieve_name()+" ");
		}
	}
}
