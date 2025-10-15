package game;

import java.awt.Color;

public class GoalPeg extends Peg {
	/**
	 * The base color of this sprite.
	 */
	private static final Color COLOR_GOAL_PEG = new Color(1f, 0.66f, 0.16f);

	/**
	 * Builds a circular polygonal shape with the given position
	 * @param position 
	 * @author Carlton Luu
	 */
    public GoalPeg(Point position) {
        super(position, COLOR_GOAL_PEG); 
    }
	
	/**
	 * Increases score by 10 when hit and lights up
	 * @author Carlton Luu 
	 */
    public void onHit() {
    	if (!isHit) {
    		Stage.addScore(10);
    	}
		super.onHit();
    }
}
