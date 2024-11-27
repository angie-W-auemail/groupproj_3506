package schedule;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.text.MessageFormat;

public class ManageSchedule {
	private ArrayList<Schedule> appointment_list= new ArrayList<Schedule>();
	public ManageSchedule(){};
	
	//load all saved appointments
	public void getAll() throws IOException{
		String path = new java.io.File(".").getCanonicalPath()+"\\src\\appointments.csv";
		BufferedReader br = new BufferedReader(new FileReader(path));
		String line = "";
		int count =0;
		Date allDate;
		String[] myArray,appointmentDate;
		while ((line = br.readLine()) != null) {
			if(count>0) {
				myArray= line.split(",");
				appointmentDate = myArray[0].split("-");
				Schedule appoint = new Schedule(Integer.parseInt(appointmentDate[0]), Integer.parseInt(appointmentDate[1]),
						Integer.parseInt(appointmentDate[2]),Integer.parseInt(appointmentDate[3])-8,myArray[1],myArray[2]);
				appointment_list.add(appoint);
			}
			count++;
		}
		br.close();
		
	}

	public ArrayList<Schedule> getRecords(String patient_id){
		ArrayList<Schedule> list = new ArrayList<Schedule>();
		for (int i=0; i<appointment_list.size();i++) {
			Schedule current = appointment_list.get(i);
			if(current.patient().equals(patient_id)) {
				list.add(current);
			}
		}
		return list;
	}
	public ArrayList<Schedule> getRecorDoc(String doctor_id){
		ArrayList<Schedule> list = new ArrayList<Schedule>();
		for (int i=0; i<appointment_list.size();i++) {
			Schedule current = appointment_list.get(i);
			if(current.patient().equals(doctor_id)) {
				list.add(current);
			}
		}
		return list;
	}
	//time conflict when booking exising appointment under 1 doctor
	public boolean conflict(String doctor_id, Date time) {
		for (int i=0; i<appointment_list.size();i++) {
			Schedule current= appointment_list.get(i);
			if(current.doctor().equals(doctor_id)) {
				if (current.getTime().equals(time)) {
					return true;
				}
			}
		}
		return false;
	}
	//write new line into schedules csv
	public void writeSchedule(String path, String line)throws IOException{
		File file = new File(path);
		FileWriter fw = new FileWriter(file,true);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(line);
		bw.newLine();
		bw.close();
		fw.close();
	}
	public void updateDB(String path) throws IOException{
		String line ="";
		String formatTemplate = "{0},{1},{2}";
		File file = new File(path);
		file.delete();
		FileWriter fw = new FileWriter(file,true);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write("time,patient,doctor");
		bw.newLine();
		bw.close();
		fw.close();
		for (int i=0; i<appointment_list.size();i++) {
			Schedule appoint = appointment_list.get(i);
			line = MessageFormat.format(formatTemplate, appoint.dateString(appoint.getTime()),appoint.patient(),appoint.doctor());
			bw.write(line);
			bw.newLine();
		}
		bw.close();
		fw.close();
	}
	
	// add a new appointment
	public void addSchedule(Schedule newAppointment) throws IOException{
		String formatTemplate = "{0},{1},{2}";
		
		String path = new java.io.File(".").getCanonicalPath()+"\\src\\appointments.csv";
		if(conflict(newAppointment.doctor(),newAppointment.getTime())==false) {
			appointment_list.add(newAppointment);
			String line = MessageFormat.format(formatTemplate, newAppointment.dateString(newAppointment.getTime()),newAppointment.patient(),newAppointment.doctor());
			writeSchedule(path,line);
		}	
	}
	public void removeSchedule(Schedule newAppointment) throws IOException{
		if (appointment_list.indexOf(newAppointment)!=-1) {
			appointment_list.remove(appointment_list.indexOf(newAppointment));
			String path = new java.io.File(".").getCanonicalPath()+"\\src\\appointments.csv";
			updateDB(path);
		}
	}
	public void printAppointments() {
		System.out.println("-------Schedules--------");
		for(int i=0;i<appointment_list.size();i++) {
			System.out.println(appointment_list.get(i).appointmentString());
		}
	}
}
