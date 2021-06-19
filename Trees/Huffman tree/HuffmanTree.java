import java.util.List;
/* A Huffman tree is a tree used for encoding and decoding files, in this case a text file. 
 * The leaf nodes represent the characters, and the path to a given character gives its encoding.
 * Every left turn is 0, every right turn is 1.
 * The nodes are required to hold a frequency, as the tree is based on the frequency of
 * occurrence of the characcter in the text.
 * 
 * For example, the letter A can be written as 01000001, but with the Huffman tree it can be
 * represented by a much smaller binary number depending on its frequency in the text. 
 */
public class HuffmanTree extends BinaryTree<String> {
	private HuffmanNode root;

	//Constructor
	public HuffmanTree(List<HuffmanNode> l) {
		createTree(l);
	}
	
	//Getter for the root data
	public String getRootData() {
		return root.getData();
	}

	//Height of the tree
	public int getHeight() {
		return root.getHeight();
	}

	//Number of nodes
	public int getNumberOfNodes() {
		return root.getNumberOfNodes();
	}

	//Checks whether the root is null
	public boolean isEmpty() {
		return root == null;
	}

	//Clearing the tree
	public void clear() {
		root = null;
	}

	//The following methods are required by the interface,
	//but the tree needs both data and frequency to be useful
	public void setTree(String rootData) {
		setTree(rootData, 0);
	}

	public void setTree(String rootData, int frequency) {
		setTree(rootData, frequency, null, null);
	}

	public void setTree(String rootData, BinaryTreeInterface<String> leftTree, BinaryTreeInterface<String> rightTree) {
		setTree(rootData, 0, leftTree, rightTree);
	}
	
	public void setTree(String rootData, int frequency, BinaryTreeInterface<String> leftTree, BinaryTreeInterface<String> rightTree) {
		privateSetTree(rootData, frequency, (HuffmanTree) leftTree, (HuffmanTree) rightTree);
	}
	
	//This method was taken from the book and adjusted for this type of tree
	private void privateSetTree(String rootData, int frequency, HuffmanTree leftTree, HuffmanTree rightTree) {
		root = new HuffmanNode(rootData, frequency);
		if ((leftTree.getRootData() != null) && !leftTree.isEmpty())
			root.setLeftChild(leftTree.root);
		if ((rightTree.getRootData() != null) && !rightTree.isEmpty())
		{
			if (rightTree != leftTree)
				root.setRightChild(rightTree.root);
			else
				root.setRightChild(rightTree.root.copy());
		}
		if ((leftTree.getRootData() != null) && (leftTree != this))
			leftTree.clear();
		if ((rightTree.getRootData() != null) && (rightTree != this))
			rightTree.clear();
	}
	
	//Creating the tree in the most useful way for this type of tree
	public void createTree(List<HuffmanNode> entries) {
		HuffmanNode lnode, rnode, node = null;
		
		while (!entries.isEmpty()) {
			//Sort the list by frequency in ascending order
			entries.sort((e1, e2) -> {
				if (e1.getFrequency() > e2.getFrequency()) {
					return 1;
				} else {
					return -1;
				}
			});
			
			//Get the two nodes with the smallest frequency
			lnode = entries.get(0);
			entries.remove(lnode);
			
			rnode = entries.get(0);
			entries.remove(rnode);
			
			node = new HuffmanNode(null, 
					lnode.getFrequency() + rnode.getFrequency(), lnode, rnode);
			
			//If the list is empty before pushing a node to the stack,
			//that node is the complete tree 
			if (entries.isEmpty()) {
				break;
			}
			entries.add(node);
		}
		
		//Assign the root node to be the generated tree
		root = node;
	}

	//Encoder
	public String encode(String c) {
		return encode(c, root);
	}
	
	private String encode(String c, BinaryNodeInterface<String> n) {
		if(n == null) {
			return "";
		} 
		
		//If the node has a left child,
		//the child's node is not null,
		//and the data is equal to c, return 0
		if(n.hasLeftChild() 
				&& n.getLeftChild().getData() != null 
				&& n.getLeftChild().getData().equals(c)) {
			
			return "0";
		}
		
		//If the node has a right child,
		//the child's node is not null,
		//and the data is equal to c, return 1
		if (n.hasRightChild() 
				&& n.getRightChild().getData() != null 
				&& n.getRightChild().getData().equals(c)) {

			return "1";
		}
		
		String lcode = encode(c,  n.getLeftChild());
		String rcode = encode(c,  n.getRightChild());
		
		if (!lcode.equals("")) {
			return "0" + lcode;
		} else if (!rcode.equals("")) {
			return "1" + rcode;
		}
		return "";
	}
	
	//Decoder
	public String decode(String code) {
		BinaryNodeInterface<String> node = root;
		
		//Every '0' is a left node, 
		//every '1' is a right node in the traversal
		for (char c : code.toCharArray()) {
			if (c == '0') {
				node = node.getLeftChild();
			} else if (c == '1') {
				node = node.getRightChild();
			}
		}
		
		//If the node is not empty or null,
		//return the value
		if (node != null && node.getData() != null) {
			return node.getData();
		} else {
			return null;
		}
	}
}
