import java.util.Stack;

/* Expression tree created from a postfix expression.
 */
public class PostfixExpressionTree {
	private static final char[] OPERATORS = {'+', '-', '*', '/'};
	private String expression;
	private BinaryTree<String> tree;
	
	//Constructor
	public PostfixExpressionTree(String expression) {
		this.setExpression(expression);
		createTree();
	}
	
	//Getters and setters
	public BinaryTree<String> getTree() {
		return tree;
	}

	public void setTree(BinaryTree<String> tree) {
		this.tree = tree;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	//The tree is created from the postfix expression
	private void createTree() {
		char[] exp = expression.toCharArray();
		
		Stack<BinaryTree<String>> stack = new Stack<BinaryTree<String>>();
		BinaryTree<String> tree = null;
		
		//Iterate through the expression and build the tree
		for (char c : exp) {
			if (c == ' ') {
				continue;
			} else if (isOperator(c)) {
				//Pop two operands from the stack and
				//push the tree back into the stack
				BinaryTree<String> ltree = stack.pop();
				BinaryTree<String> rtree = stack.pop();
				
				tree = new BinaryTree<String>(c + "", ltree, rtree);
				
				stack.push(tree);
			} else {
				//Push the operand to the stack
				stack.push(new BinaryTree<String>(c + "", null, null));
			}
		}
		//Only the final tree remains in the stack
		this.tree = stack.pop();
	}
	
	private boolean isOperator(char c) {
		//Checks if the character is an operator
		for (char op : OPERATORS) {
			if (c == op) {
				return true;
			}
		}
		return false;
	}
}
