/* The data structure for a patient record used in the PatientDatabase class.
 * The record will have the ID, the date, reason and treatment for the visit.
 */
public class PatientRecord {
	//Data fields
	private int id;
	private String date;
	private String reason;
	private String treatment;
	
	//Constructor
	public PatientRecord(int id, String date, String reason, String treatment) {
		this.setId(id);
		this.setDate(date);
		this.setReason(reason);
		this.setTreatment(treatment);
	}

	//Getters and setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getTreatment() {
		return treatment;
	}

	public void setTreatment(String treatment) {
		this.treatment = treatment;
	}
	
	//Overriding the hash code function to return the ID
	@Override
	public int hashCode() {
		return this.getId();
	}
	
	//Overriding the equals method
	@Override
	public boolean equals(Object o) {
		if (o == null || o.getClass() != this.getClass())
			return false;
		if (o == this)
			return true;
		
		return ((PatientRecord)o).getId() == this.getId() &&
				((PatientRecord)o).getDate().equals(this.getDate());
	}
}
