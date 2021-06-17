import java.util.Scanner;
import java.util.Random;

/* A simple guessing game where the user inputs A or B, and based on their
 * last four choices, the program will predict the user's next choice.
 * A hash table is used for this purpose.
 */
public class GuessingGame {
	
	public static void main (String[] args) {
		Hashtable table = new Hashtable();
		
		String prediction = "";
		String choice = "";
		int correct = 0;
		int incorrect = 0;
		
		//String which stores the last four choices
		String lastChoices = "";
		
		//Scanner is necessary for user input
		Scanner sc = new Scanner(System.in);
		
		
		//The game will repeat indefinitely
		while (true) {
			System.out.println("Choose either A or B, and I will guess your choice."
					+ "\nPress Return when you are ready.");
			//Await user input
			sc.nextLine();
			
			//Make the prediction
			prediction = predict(table, lastChoices);
			System.out.println("I guess that you chose " + prediction + "; am I right?");
			//Await user input
			String answer = sc.nextLine().toLowerCase();
			
			//Increment the counter of correct and incorrect guesses
			if (answer.equals("yes")) {
				correct++;
				if (prediction.equals("A")) {
					choice = "A";
				} else {
					choice = "B";
				}
			} else if (answer.equals("no")) {
				incorrect++;
				if (prediction.equals("A")) {
					choice = "B";
				} else {
					choice = "A";
				}
			}
			//Print out the score
			System.out.println("Score: " + correct + " correct, " + incorrect + " incorrect");
			
			
			//For the first four games we won't have the key to hash,
			//so we will execute this command only after four runs
			if (correct + incorrect > 4) {
				table.add(lastChoices, choice);
			}
			
			//Append the last choice to the four prior
			lastChoices += choice;
			
			//If the key is more than 4 chars long,
			//remove the first letter
			if (lastChoices.length() > 4) {
				lastChoices = lastChoices.substring(1);
			}
		}
	}
	
	
	private static String predict(Hashtable table, String lastFour) {
		Random rand = new Random();
		//Returns a random choice if there are less than 4
		//previous predictions
		if (lastFour.length() < 4) {
			return "" + "AB".toCharArray()[rand.nextInt(2)];
		}
		
		Entry entry = table.find(lastFour);
		
		//Also returns a random choice if the entry is
		//not in the table
		if (entry == null) {
			return "" + "AB".toCharArray()[rand.nextInt(2)];
		} else if (entry.a < entry.b) {
			return "B";
		} else {
			return "A";
		}
	}

}

class Hashtable {
	Entry[] entries;
	
	//The number of possible combinations is 2^4
	static final int SIZE = 16; 
	
	public Hashtable() {
		entries = new Entry[SIZE];
	}
	
	public void add(String key, String choice) {
		//Hash the key
		int index = key.hashCode();
		
		//Increment the counter for the given key
		//based on the choice
		int a = 0;
		int b = 0;
		if (choice.equals("A")) {
			a++;
		} else {
			b++;
		}
		
		//Linear probing to handle collisions
		for (int i = 0; i < SIZE; i++) {
			int j = (index + i) % SIZE;
			
			if (j < 0) {
				j += SIZE;
			}
			
			//If no entry is present, create a new one
			if (entries[j] == null) {
				entries[j] = new Entry(key, a, b);
				break;
				
			} else {
				//If an entry is present and it has the same key,
				//then update values
				if (entries[j].key.equals(key)) {
					entries[j].a += a;
					entries[j].b += b;
					break;
				}
			}
		}
	}
	
	public Entry find(String key) {
		//Hash the key
		int index = key.hashCode();
		
		for (int i = 0; i < SIZE; i++) {
			int j = (index + i) % SIZE;
			
			if (j < 0) {
				j += SIZE;
			}
			//If no entry is present, return null
			if (entries[j] == null) {
				return null;
			} else {
				//If an entry is present and it has the same key,
				//then return the entry
				if (entries[j].key.equals(key)) {
					return entries[j];
				}
			}
		}
		return null;
	}
}

class Entry {
	String key;
	//The number of times A and B occurred as the next choice
	int a;
	int b;
	
	public Entry(String key, int a, int b) {
		this.key = key;
		this.a = a;
		this.b = b;
	}
}
