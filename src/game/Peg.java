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
    private boolean isHit;

    /**
     * Creates a new Peg.
     * @param position
     * @param color
     */
    public Peg(Point position, Color color) {
        super(NUM_POINTS, position, 0, DRAW_ORDER_LAYER, color, RADIUS);
    }

    public void onHit()
    {
    	// Pegs can only get hit once
    	if (isHit) return;
    	isHit = true;
    	
    	// Change the sprite to be brighter to reflect hit state
    	drawColor = drawColor.brighter();
    }
    
    public boolean getIsHit() {
    	return isHit;
    }
}