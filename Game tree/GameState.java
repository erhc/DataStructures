/* The state of the game tic-tac-toe. The array represents the fields.
 */
public class GameState {
	private int[][] state;
	private int score;
	
	public GameState(int score) {
		setState(new int[3][3]);
		setScore(score);
	}

	public int[][] getState() { return state; }
	public void setState(int[][] state) { this.state = state; }
	public int getScore() { return score; }
	public void setScore(int score) { this.score = score; }
	
	//Checks whether the state is a victory or a draw, or if it can be continued
	public boolean isFinal() {
		if(score != 0)
			return true;
		
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (state[i][j] == 0)
					return false;
			}
		}
		
		return true;
	}
}
