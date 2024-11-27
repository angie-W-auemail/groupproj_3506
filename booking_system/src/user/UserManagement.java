package user;
import java.util.ArrayList;
public class UserManagement {
	private ArrayList<Admin> admin_list= new ArrayList<Admin>();
	private ArrayList<Doctor> doctor_list= new ArrayList<Doctor>();
	private ArrayList<Patient> patient_list= new ArrayList<Patient>();
	UserManagement(){};
	public void addAdmin(Admin person) {
		admin_list.add(person);
	}
	public void printUsers() {
		for(int i=0;i<admin_list.size();i++) {
			System.out.println("-------ADMIN--------");
			System.out.println("Name: "+admin_list.get(i).name()+" ID: "
					+admin_list.get(i).id()+" Permission: "+admin_list.get(i).permission()+
					" Phone: "+ admin_list.get(i).phone()+ " Email: "+ admin_list.get(i).email()+
					" Password: "+ admin_list.get(i).pass());
		}
		for(int i=0;i<doctor_list.size();i++) {
			System.out.println("-------DOCTOR--------");
			System.out.println("Name: "+doctor_list.get(i).name()+" ID: "
					+doctor_list.get(i).id()+" Permission: "+doctor_list.get(i).permission()+
					" Phone: "+ doctor_list.get(i).phone()+ " Email: "+ doctor_list.get(i).email()+
					" Password: "+ doctor_list.get(i).pass()+" Price: "+ doctor_list.get(i).price());
		}
		for(int i=0;i<patient_list.size();i++) {
			System.out.println("-------PATIENT--------");
			System.out.println("Name: "+patient_list.get(i).name()+" ID: "
					+patient_list.get(i).id()+" Permission: "+patient_list.get(i).permission()+
					" Phone: "+ patient_list.get(i).phone()+ " Email: "+ patient_list.get(i).email()+
					" Password: "+ patient_list.get(i).pass()+" Price: "+ patient_list.get(i).address()+
					" Medical history: "+ patient_list.get(i).medicalHistory());
		}
	}         
}