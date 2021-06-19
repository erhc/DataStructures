/* The node designed for the Huffman tree class.
 * It needs to store both the data and the frequency of the character.
 */
public class HuffmanNode extends BinaryNode<String> {
	//Data fields
	private String data;
	private int frequency;
	private HuffmanNode left, right;

	//Constructors
	public HuffmanNode(String data, int frequency) {
		this(data, frequency, null, null);
	}
	
	public HuffmanNode(String data, int frequency, HuffmanNode left, HuffmanNode right) {
		this.data = data;
		this.frequency = frequency;
		this.left = left;
		this.right = right;
	}
	
	//Getters and setters
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	public BinaryNodeInterface<String> getLeftChild() {
		return left;
	}

	public BinaryNodeInterface<String> getRightChild() {
		return right;
	}

	public void setLeftChild(BinaryNodeInterface<String> left) {
		this.left = (HuffmanNode) left;
	}

	public void setRightChild(BinaryNodeInterface<String> right) {
		this.right = (HuffmanNode) right;
	}

	//Checks if the node has children
	public boolean hasLeftChild() {
		return left != null;
	}

	public boolean hasRightChild() {
		return right != null;
	}

	public boolean isLeaf() {
		return left == null && right == null;
	}

	//Height of the tree rooted at this node
	public int getHeight()	{
		return getHeight(this);
	}
	
	//Private recursive method
	private int getHeight(HuffmanNode node) {
		int height = 0;
		
		if (node != null)
			height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
		
		return height;
	} 
	
	//Number of nodes
	public int getNumberOfNodes() {
		int leftNumber = 0;
		int rightNumber = 0;
		
		if (left != null)
			leftNumber = left.getNumberOfNodes();
		
		if (right != null)
			rightNumber = right.getNumberOfNodes();
		
		return 1 + leftNumber + rightNumber;
	}

	//Copying the node
	public BinaryNodeInterface<String> copy() {
		return new HuffmanNode(data, frequency, left, right);
	}
}