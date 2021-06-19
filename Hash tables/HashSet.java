import java.util.ArrayList;
import java.util.Collections;

/* This is a hash table stored in an array list, with the resizing and rehashing enabled.
 */
public class HashSet<E> {
	//Arbitrary prime size
	private static final int DEFAULT_SIZE = 31;
	private ArrayList<E> entries;
	private int numEntries = 0;
	private double maxLoadFactor;
	
	//Constructors
	public HashSet() {
		this(DEFAULT_SIZE, 0.5);
	}
	
	public HashSet(int size, double loadFactor) {
		//Create an ArrayList and populate it with null
		entries = new ArrayList<E>(Collections.nCopies(size, null));
		maxLoadFactor = loadFactor;
	}

	//Getters and setters
	public int getNumEntries() {
		return numEntries;
	}
	
	public void setNumEntries(int numEntries) {
		this.numEntries = numEntries;
	}
	

	public double getMaxLoadFactor() {
		return maxLoadFactor;
	}
		
	public void setMaxLoadFactor(int lf) {
		this.maxLoadFactor = lf;
	}
	
	public double getLoadFactor() {
		return numEntries/entries.size();
	}
	
	//Adding an entry to the hash set
	public void add(E e) {
		//If the load factor is higher than the max, rehash
		if (getLoadFactor() >= maxLoadFactor) {
			rehash();
		}
		
		int index = e.hashCode() % entries.size();
		
		//If the entry isn't already in the set, add it
		if (!entries.contains(e)) {
			//Linear probing
			for (int i = 1; i < entries.size(); i++) {
				int j = (index + i) % entries.size();
				if (entries.get(j) == null) {
					entries.set(j, e);
				}
			}
			numEntries++;
		}
	}
	
	//Removing an entry from the hash set
	public boolean remove(E e) {
		if (entries.contains(e)) {
			//Get the index of the entry and replace the entry it with null
			entries.set(entries.lastIndexOf(e), null);
			numEntries--;
			return true;
		} else {
			return false;
		}
		
	}
	
	//Expanding the set and rehashing the entries
	public void rehash() {	
		ArrayList<E> old = entries;
		entries = new ArrayList<E>(Collections.nCopies(nextPrime(old.size() * 2), null));
		
		//Iterate through the entries and add them one by one
		for (E e : old) {
			if (e != null) {
				add(e);
			}
		}
	}

	//Returns the next prime number
	private int nextPrime(int i) {
		boolean isPrime = false;
		
		while(!isPrime) {
			isPrime = true;
			
			//A number is prime if it has no multiples up to its square root
			for(int j = 2; j <= Math.sqrt(i); j++) {
				if(i%j == 0) {
					isPrime = false;
					break;
				}
			}
			
			if(!isPrime) {
				i++;
			}
		}
		
		return i;
	}
}
