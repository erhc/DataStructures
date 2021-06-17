import java.util.Stack;
/* Expression tree created from a infix expression.
 */
public class InfixExpressionTree {
	private static final char[] OPERATORS = {'+', '-', '*', '/'};
	private String expression;
	private BinaryTree<String> tree;
	
	//Constructor
	public InfixExpressionTree(String expression) {
		this.expression = expression;
		createTree();
	}
	
	//Getters
	public BinaryTree<String> getTree() {
		return tree;
	}
	
	public String getExpression() {
		return expression;
	}

	//The tree is created from the infix expression
	private void createTree() {
		char[] exp = expression.toCharArray();
		
		Stack<BinaryTree<String>> operators = new Stack<BinaryTree<String>>();
		Stack<BinaryTree<String>> operands = new Stack<BinaryTree<String>>();
		
		BinaryTree<String> tree = null;
		
		//Iterate through the stack, push operators and operands
		for (char c : exp) {
			if (c == ' ') {
				continue;
			} else if (c == ')') {
				
				//If the bracket is closed create a tree and push it to the stack
				while (!operators.peek().getRootData().equals("(")) {
					tree = operators.pop();
					
					BinaryTree<String> rtree = operands.pop();
					
					//Check if the previous operator has a higher priority
					if (!operators.isEmpty() && !hasHigherPriority(
							tree.getRootData(), operators.peek().getRootData())) {
						BinaryTree<String> tree1 = operators.pop();
						
						BinaryTree<String> rtree1 = operands.pop();
						BinaryTree<String> ltree1 = operands.pop();	
						
						tree1.setTree(tree1.getRootData(), ltree1, rtree1);
						
						//Return the trees to the stack
						operands.push(tree1);
						operands.push(rtree);
						
						operators.push(tree);
						
					} else {
						BinaryTree<String> ltree = operands.pop();		
					
						tree.setTree(tree.getRootData(), ltree, rtree);
					
						operands.push(tree);
					}
				}
				
				//Remove the opening bracket
				operators.pop();
				
			} else if (isOperator(c) || c == '(') {
				//Push the operator or bracket to the stack
				operators.push(new BinaryTree<String>(c + "", null, null));
				
			} else {
				//Push the operand to the stack
				operands.push(new BinaryTree<String>(c + "", null, null));
			}
		}
		
		while (!operators.isEmpty()) {
			tree = operators.pop();
			
			BinaryTree<String> rtree = operands.pop();
			
			//Check if the previous operator has a higher priority
			if (!operators.isEmpty() && !hasHigherPriority(
					tree.getRootData(), operators.peek().getRootData())) {
				BinaryTree<String> tree1 = operators.pop();
				
				BinaryTree<String> rtree1 = operands.pop();
				BinaryTree<String> ltree1 = operands.pop();	
				
				tree1.setTree(tree1.getRootData(), ltree1, rtree1);
				
				//Return the trees to the stack
				operands.push(tree1);
				operands.push(rtree);
				
				operators.push(tree);
				
			} else {
				BinaryTree<String> ltree = operands.pop();		
			
				tree.setTree(tree.getRootData(), ltree, rtree);
			
				operands.push(tree);
			}
		}
		//The stack will only have the final tree in the end
		this.tree = operands.pop();
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

	private boolean hasHigherPriority(String op1, String op2) {
		//Returns true if op1 has higher priority than op2
		if (op1.equals(op2)) {
			return false;
		} else if (op1.equals("^")) {
			return true;
		} else if (op2.equals("^")) {
			return false;
		} else if (op1.equals("*") || op1.equals("/")) {
			return true;
		} else {
			return false;
		}
	}


}
