package TreatementRecords;
import java.io.*;
import java.text.MessageFormat;
import java.util.ArrayList;

import schedule.ManageSchedule;
import schedule.Schedule;
import user.*;

public class Records {
	private String patient_id;
	private ArrayList <Schedule> appointments = new ArrayList<Schedule>();
	private ArrayList <Double> payment = new ArrayList <Double>();
	private ArrayList <String> doctor = new ArrayList <String>();
	private double paid_amount=0;
	private Patient person;
	
	public Records(String patient_id) {
		this.patient_id = patient_id;
	}
	public void setRecords(ManageSchedule records, UserManagement users) {
		appointments = records.getRecords(patient_id);
		this.person = users.getPatient(patient_id);
		double price ;
		String doctor;
		// accumulate hourly amount for each appointment
		for (int i=0; i<appointments.size();i++) {
			Schedule visit = appointments.get(i);
			price = users.getDoctor(visit.doctor()).price();
			doctor = users.getDoctor(visit.doctor()).name();
			payment.add(price);
			this.doctor.add(doctor);
			paid_amount = paid_amount+price;
		}
	}
	public void genReport() throws IOException{
		String formatTemplate = "datetime: {0}	 	doctor: {1}		price: {2}		comment: {3}		prescription: {4}";
		String path = new java.io.File(".").getCanonicalPath()+"\\src\\"+patient_id+"_report.csv";
		String line ="";
		File file = new File(path);
		file.delete();
		FileWriter fw = new FileWriter(file,true);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write("-------------------Medical Report-------------------");
		bw.newLine();
		bw.newLine();
		bw.write("PATIENT NAME: "+person.name()+"     PATIENT ID: "+person.id());
		bw.newLine();
		bw.write("EMAIL;: "+person.email()+"     PHONE NUMBER: "+person.phone()+ "     ADDRESS: "+person.address());
		bw.newLine();
		bw.write("-------------------Historical Visits-------------------");
		bw.newLine();
		bw.write("time,doctor,price, comment, prescription");
		bw.newLine();
		for(int i =0;i<appointments.size();i++) {
			Schedule time = appointments.get(i);
			line = MessageFormat.format(formatTemplate, time.dateString(time.getTime()), doctor.get(i),
					payment.get(i),time.comment(),time.prescription());
			bw.write(line);
			bw.newLine();
		}
		bw.write("-------------------Historical Visits-------------------");
		bw.newLine();
		bw.write("total:				"+paid_amount);
		bw.newLine();
		bw.close();
	}
	
	
}
