package TreatementRecords;

import java.util.ArrayList;

import schedule.ManageSchedule;
import schedule.Schedule;
import user.UserManagement;

public class Records {
	String patient_id;
	private ArrayList <Schedule> appointments = new ArrayList<Schedule>();
	private double paid_amount=0;
	
	public Records(String patient_id) {
		this.patient_id = patient_id;
	}
	public void setRecords(ManageSchedule records, UserManagement users) {
		appointments = records.getRecords(patient_id);
		// accumulate hourly amount for each appointment
		for (int i=0; i<appointments.size();i++) {
			Schedule visit = appointments.get(i);
			double price = users.getDoctor(visit.doctor()).price();
			paid_amount = paid_amount+price;
		}
	}
	
	
}
