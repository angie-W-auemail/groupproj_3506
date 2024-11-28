package schedule;
import java.util.Date;

/*
 * timeBlocks: 
 * 1: 9:00-10:00
 * 2:10:00-11:00
 * 3: 11:00-12:00
 * 4: 12:00-13:00
 * 5: 13:00-14:00
 * 6: 14:00-15:00
 * 7: 15:00-16:00
 * 8: 16:00-17:00
 */
//class schedule record appointment information for each time block
public class Schedule {
	private Date appointment;
	private String patient_id;
	private String doctor_id;
	private String doctor_comment="";
	private String prescription="";
	public Schedule(int year, int month, int date, int block, String patient_id, String doctor_id ) {
		this.appointment = new Date(year, month, date,block+8,0);
		this.patient_id = patient_id;
		this.doctor_id = doctor_id;
	}
	public String doctor() {
		return doctor_id;
	}
	public String patient() {
		return patient_id;
	}
	public void setComment(String comment) {
		doctor_comment = comment;
	}
	public void setPrescription(String prescription) {
		this.prescription = prescription;
	}
	public String comment() {
		return doctor_comment;
	}
	public String prescription() {
		return prescription;
	}
	public Date getTime() {
		return appointment;
	}
	public String dateString(Date time) {
		return (appointment.getYear()+"-"+appointment.getMonth()+"-"+appointment.getDate()+"-"+appointment.getHours());
	}
	public Date StringDate(String time) {
		String[] myArray;
		myArray = time.split("-");
		return new Date(Integer.parseInt(myArray[0]),Integer.parseInt(myArray[1])
				,Integer.parseInt(myArray[2]),Integer.parseInt(myArray[3]),0);
	}
	public String appointmentString() {
		String record = "Datetime: "+dateString(appointment)+" patient: "+patient_id+" doctor_id: "+doctor_id
				+" Comment: "+doctor_comment+" prescription: "+prescription;
		return record;
	}
	
	
	
}
