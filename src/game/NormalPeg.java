package game;

import java.awt.Color;

public class NormalPeg extends Peg {
	/**
	 * The base color of this sprite.
	 */
	private static final Color COLOR_NORMAL_PEG = 
			new Color(0.32f, 0.40f, 0.81f);

    public NormalPeg(Point position) {
        super(position, COLOR_NORMAL_PEG);
    }
    
    /**
     * Adds 1 to this peg's score and causes it to light up when hit
     * @author Carlton Luu
     */
    @Override
    public void onHit() {
    	if (!isHit) {
    		Stage.addScore(1);
    	}
    	super.onHit();
    }
}