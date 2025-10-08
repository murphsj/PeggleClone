package game;

import java.awt.Color;

public class Ball extends CircleColliderSprite implements Updating {
	// Sprite Constants
	/**
	 * Number of points of this object's circle.
	 */
	private static final int NUM_POINTS = 5;
	/**
	 * Radius of this object's circle.
	 */
	private static final int RADIUS = 3;
	/**
	 * Draw color of this object's sprite.
	 */
	private static final Color DRAW_COLOR = new Color(0.77f, 0.79f, 0.81f);
	/**
	 * Z layer of this object.
	 */
	private static final int DRAW_ORDER_LAYER = 3;
	
	// Physics Constants
	/**
	 * Acceleration of the ball due to gravity.
	 */
	private static final double GRAVITY = 10;
	
	/**
	 * The velocity of the ball.
	 */
    private Point velocity;
    /**
     * The rotational acceleration of the ball.
     */
    private double rotationalAccel;

    public Ball(Point position, Point initialVelocity) {
        super(NUM_POINTS, position, 0, DRAW_ORDER_LAYER, DRAW_COLOR, RADIUS);
        this.velocity = initialVelocity;
    }
    
    @Override
    public void update(double deltaTime) {
    	// Velocity Verlet integration algorithm from Wikipedia
    	position = 
    }

    public void applyImpulse(Point velocity) {
        
    }
}