/* The main class for testing the Huffman encoder. 
 * It encodes a file, saving it in another file,
 * then decodes it and saves the decoded version in another file.
 */
//Needed for reading and writing files
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

//Map and list classes
import java.util.Hashtable;
import java.util.LinkedList;



public class Main {
	final static String ORIGINAL_PATH = ""; //insert path to the original text file to be read and encoded
	final static String ENCODE_PATH = ""; //insert path where the encoded text file is to be created
	final static String DECODE_PATH = ""; //insert path where the decoded text file is to be created

	public static void main(String[] args) throws IOException {
		
		//Load file
		Scanner sc = new Scanner(new File(ORIGINAL_PATH));
		
		//Assuming most characters in the text are lowercase, 
		//there is no need to assign a bigger table
		Hashtable<String, Integer> table = new Hashtable<String, Integer>(35);
		
		while (sc.hasNextLine()) {
			//Create a frequency table for every line
			frequency(sc.nextLine()).forEach((key, val) -> {
				//Merge the maps such that the frequency values are added
				table.merge(key, val, (v1, v2) -> (v1 + v2));
			});
		}		
		sc.close();
		
		//Transforming the map into a list
		LinkedList<HuffmanNode> l = new LinkedList<HuffmanNode>();
		table.forEach((k, v) -> l.add(new HuffmanNode(k, v)));
			
		//Create tree
		HuffmanTree tree = new HuffmanTree(l);
		
		//Encode file
		File file = new File(ENCODE_PATH);
		file.createNewFile();
		
		FileWriter writer = new FileWriter(ENCODE_PATH);
		sc = new Scanner(new File(ORIGINAL_PATH));
		
		while (sc.hasNextLine()) {
			char[] line = sc.nextLine().toCharArray();
			
			for (char c : line) {
				//write each character and separate with a space
				writer.write(tree.encode(c+"") + " ");
			}
			
			//Add a separator to the end of the line
			writer.write(System.lineSeparator());
		}
		sc.close();
		writer.close();
		
		
		//Decode file
		sc = new Scanner(file);
		file = new File(DECODE_PATH);
		file.createNewFile();
		
		writer = new FileWriter(DECODE_PATH);
		
		while(sc.hasNextLine()) {
			String[] line = sc.nextLine().split(" ");
			
			for(String code : line) {
				//Every string separated by whitespace is
				//a character from the original text
				writer.write(tree.decode(code));
			}
			
			//Add a separator to the end of the line
			writer.write(System.lineSeparator());
		}
		
		writer.close();
		sc.close();
	}
	

	//Frequency analysis
	public static Hashtable<String, Integer> frequency(String text) {
		Hashtable<String, Integer> map = new Hashtable<String, Integer>();
		
		for (char c : text.toCharArray()) {
			if (map.containsKey(c+"")) {
				map.replace(c+"", map.get(c+"") + 1);
			} else {
				map.put(c+"", 1);
			}
		}
		
		return map;
	}
}
