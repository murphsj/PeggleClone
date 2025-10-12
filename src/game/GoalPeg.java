package game;

import java.awt.Color;

public class GoalPeg extends Peg {
	/**
	 * The base color of this sprite.
	 */
	private static final Color COLOR_GOAL_PEG = new Color(0.32f, 0.40f, 0.81f);

	/**
	 * Builds a circular polygonal shape with the given position
	 * @param position 
	 */
    public GoalPeg(Point position) {
        super(position, COLOR_GOAL_PEG); 
    }
	
	/**
	 * Increases score by 1 when hit and lights up
	 * @author Carlton Luu 
	 * @return void
	 */
    public void onHit() {
		
    }
}
