import java.util.Collections;
import java.util.List;

/* A tree representing all the possible game states of tic-tac-toe.
 * Can be used as an AI for the game.
 */

public class GameTree extends LinkedTree<GameState>{
	public GameTree() {
		GameState g = new GameState(0);
		addRoot(g);
		initialize(root, g, 1);
	}
	
	//Initialises all possible game states
	private void initialize(Node<GameState> n, GameState g, int turn) {
		if (g.isFinal())
			return;
		int[][] state = g.getState();
		
		//Iterate through the state, altering each empty position
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (state[i][j] == 0) {
					int[][] newState = state;
					newState[i][j] = turn;
					GameState gs = new GameState(evaluate(newState));
					gs.setState(newState);
					Node<GameState> child = createNode(gs, n);
					n.addChild(child);
					initialize(child, gs, turn == 1 ? -1 : 1);
				}
			}
		}
	}

	//Evaluates the score of the state, 1 if the current player wins, -1 if the other player wins and 0 otherwise 
	private int evaluate(int[][] state) {
		//Horizontal and vertical win states
		for(int i = 0; i < 3; i++) {
			if (state[i][1] == state[i][2] && state[i][2] == state[i][3])
				if(state[i][1] == 1)
					return 1;
				else if (state[i][1] == -1)
					return -1;
			if (state[1][i] == state[2][i] && state[2][i] == state[3][i])
				if(state[1][i] == 1)
					return 1;
				else if (state[1][i] == -1)
					return -1;
		}
		
		//Cross win states
		if (state[1][1] == state[2][2] && state[2][2] == state[3][3])
			if(state[1][1] == 1)
				return 1;
			else if (state[1][1] == -1)
				return -1;
		
		if (state[1][3] == state[2][2] && state[2][2] == state[3][1])
			if(state[1][3] == 1)
				return 1;
			else if (state[1][3] == -1)
				return -1;
			
		return 0;
	}

	//Calculates the best move to play based on the score
	public Position<GameState> best(Position<GameState> p) {
		Node<GameState> n = validate(p);
		if (n.getElement().isFinal())
			return null;
		
		List<Position<GameState>> children = (List<Position<GameState>>) n.getChildren();
		Collections.shuffle(children); //shuffling provides a bit more diversity when many moves have the same score
		
		Position<GameState> best = null;
		for (Position<GameState> child : children) {
			if (best == null || score(child) > score(best))
				best = child;
		}
		
		return best;
	}

	//Scores the game state based on the scores of its children
	private int score(Position<GameState> p) {
		Node<GameState> n = validate(p);
		if (n.getElement().isFinal())
			return n.getElement().getScore();
		
		int score = 0;
		for (Position<GameState> child : n.getChildren())
			score = Math.max(0, score(child));
		
		return score;
	}
}
