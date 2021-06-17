import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

/* A database of fictional patients intended to store a thousand patients.
 * The database will be an array list of linked lists, where each entry in the array
 * will correspond to a patient with a given ID, and the list will contain their
 * records.
 */
public class PatientDataBase {
	//Arbitrary prime number > 1000
	private static final int DEFAULT_SIZE =	1009;
	private int size;
	private ArrayList<LinkedList<PatientRecord>> records;
	private int numRecords = 0;
	
	//Constructors
	public PatientDataBase() {
		this(DEFAULT_SIZE);
	}
	
	public PatientDataBase(int size) {
		records = new ArrayList<LinkedList<PatientRecord>>(Collections.nCopies(size, null));
		this.size = size;
	}
	
	//Getter for the number of records
	public int getNumRecords() {
		return numRecords;
	}
	
	//Adding a record
	public void addRecord(PatientRecord rec) {
		//Hash the index
		int index = hashIndex(rec.getId());
		
		//If the entry is empty, create a new list
		if (records.get(index) == null) {
			records.set(index, new LinkedList<PatientRecord>());
		} 
		
		//Add the record to the list
		records.get(index).add(rec);
		numRecords++;
	}
	
	//Returns the reason for the visit
	public String getReason(int id, String date) {
		//Hash the index
		int index = hashIndex(id);
		
		//If the index is found
		if (records.get(index) != null) {
			//Iterate through the visits to find the match
			for (PatientRecord rec : records.get(index)) {
				if (rec.getDate().equals(date)) {
					return rec.getReason();
				}
			}
		}
		return null;
	}
	
	//Returns the treatment prescribed
	public String getTreatment(int id, String date) {
		//Hash the index
		int index = hashIndex(id);
		
		//If the index is found
		if (records.get(index) != null) {
			//Iterate through the visits to find the match
			for (PatientRecord rec : records.get(index)) {
				if (rec.getDate().equals(date)) {
					return rec.getTreatment();
				}
			}
		}
		return null;
	}
	
	
	public String[] getDates(int id) {
		//Hash the index
		int index = hashIndex(id);
		
		//If the index is found
		if (records.get(index) != null) {
			//Store all the dates in an array
			String[] dates = new String[records.get(index).size()];
			
			int i = 0;
			for (PatientRecord rec : records.get(index)) {
				dates[i] = rec.getDate();
				i++;
			}
			return dates;
		}
		return null;
	}
	
	//Hash function
	private int hashIndex(int id) {
		return id % size;
	}
}
