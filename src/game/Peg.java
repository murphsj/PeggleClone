package game;

import java.awt.Color;

public abstract class Peg extends CircleColliderSprite {
	/**
	 * Number of points of this object's circle.
	 */
	private static final int NUM_POINTS = 8;
	/**
	 * Radius of this object's circle.
	 */
	private static final int RADIUS = 14;
	/**
	 * Z layer of this object.
	 */
	private static final int DRAW_ORDER_LAYER = 2;
	
	/**
	 * True if this peg has been hit.
	 */
    protected boolean isHit;

    /**
     * Creates a new Peg.
     * @param position the position of peg on game field.
     * @param color the color used to draw peg.
	 * @author Carlton Luu
     */
    public Peg(Point position, Color color) {
        super(NUM_POINTS, position, 0, DRAW_ORDER_LAYER, color, RADIUS);
    }

	/**
	 * Handles peg hit by ball, marks as hit, and lightens color
	 * @author Carlton Luu
	 */
    public void onHit()
    {
    	// Pegs can only get hit once
    	if (isHit) return;
    	isHit = true;
    	
    	// Change the sprite to be brighter to reflect hit state
    	drawColor = drawColor.brighter();
    }

	/**
     * Returns whether the peg has been hit.
	 * @return boolean true if hit, false if not
	 * @author Carlton Luu
     */
    public boolean getIsHit() {
    	return isHit;
    }
}
