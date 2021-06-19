import java.util.Arrays;
/* Array implementation of a binary tree.
 */
public class ArrayBinaryTree<T> implements BinaryTreeInterface<T> {
	private T[] tree;
	//Arbitrary default size
	private static final int DEFAULT_SIZE = 15;
	
	//Constructors
	public ArrayBinaryTree() {
		this(DEFAULT_SIZE);
	}
	
	public ArrayBinaryTree(int size) {
		tree = (T[]) new Object[size];
		Arrays.fill(tree, null);
	}
	
	//Accessing the root
	public T getRootData() {
		return tree[0];
	}
	
	//Setting the root data
	public void setRoot(T rootData) {
		tree[0] = rootData;
	}
	
	//Checking if the tree is empty
	public boolean isEmpty() {
		return tree[0] == null;
	}
	
	//Clearing the entries
	public void clear() {
		tree = (T[])new Object[DEFAULT_SIZE];
		Arrays.fill(tree, null);
	}

	//The height of the tree
	public int getHeight() {
		int index = 0;
		
		//Find last populated index
		for (int i = tree.length - 1; i >= 0; i--) {
			if (tree[i] != null) {
				index = i;
				break;
			}
		}
		
		//Since log_a(b) = log(b)/log(a)
		//and h = log_2(i)
		return (int) Math.ceil(Math.log(index)/Math.log(2));
	}

	//The total number of nodes
	public int getNumberOfNodes() {
		int count = 0;
		
		//Increment the count for every non null entry
		for (T item : tree) {
			if (item != null) {
				count++;
			}
		}
		
		return count;
	}
	
	//Adding a node
	public void addNode(T data, T parentData) {
		//Calculating the indices
		int parentIndex = getIndexOf(parentData);
		int childIndex = (int) Math.pow(2, parentIndex) +1;
		
		//Resizing if the array is too small
		if (childIndex <= tree.length) {
			expand();
		}
		
		//Add as the parent node's left or right tree
		if (tree[childIndex] == null ) {
			tree[childIndex] = data;
		} else if (tree[childIndex + 1] == null) {
			tree[childIndex + 1] = data;
		} 
	}
	
	//Adding a node sequentially to the first place with a null entry
	public void addSequentially(T data) {
		for (int i = 0; i < tree.length; i++) {
			if (tree[i] == null) {
				tree[i] = data;
				break;
			}
		}
		//If there are no null entries
		expand();
	}
	
	//Calculating the index
	public int getIndexOf(T data) {
		for (int i = 0; i < tree.length; i++) {
			if (tree[i].equals(data)) {
				return i;
			}
		}
		//If not found return -1
		return -1;
	}
	
	//Resizing and copying the array
	protected void expand() {
		T[] temp = (T[]) new Object[2*tree.length + 1];
		for (int i = 0; i < tree.length; i++) {
			temp[i] = tree[i];
		}
		
		tree = temp;
	}
}
