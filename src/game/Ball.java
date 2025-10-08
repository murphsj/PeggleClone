package game;

import java.awt.Color;

/**
 * A pinball-like physics object which can bounce off pegs.
 */
public class Ball extends CircleColliderSprite implements Updating {
	// Sprite Constants
	/**
	 * Number of points of this object's circle.
	 */
	private static final int NUM_POINTS = 7;
	/**
	 * Radius of this object's circle.
	 */
	private static final int RADIUS = 20;
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
	private static final double GRAVITY = 9.8;
	
	/**
	 * The last position of the ball. Used for Verlet integration.
	 */
    private Point lastPosition;
    /**
     * The rotational acceleration of the ball.
     */
    private double rotationalAccel;

    /**
     * Constructs a new Ball.
     * @param position the position of the ball
     * @param initialVelocity the velocity of the ball at time 0
     * @author Samuel Murphy
     */
    public Ball(Point position, Point initialVelocity) {
        super(NUM_POINTS, position, 0, DRAW_ORDER_LAYER, DRAW_COLOR, RADIUS);
        setVelocity(initialVelocity);
    }
    
    /**
     * Advances the ball's physics simulation using Verlet integration.
     * @author Samuel Murphy
     */
    @Override
    public void update(double deltaTime) {
    	// Velocity Verlet integration algorithm from Gorilla Sun
    	Point newPosition = position.mul(2).sub(lastPosition);
    	// Acceleraton is constant since gravity is the only force
    	newPosition.y += GRAVITY * (deltaTime*deltaTime);
    	
    	lastPosition = position;
    	position = newPosition;
    }
    
    /**
     * Set the instentaneous velocity of the ball.
     * @param velocity the new velocity to set
     * @author Samuel Murphy
     */
    public void setVelocity(Point velocity) {
        // With Verlet integration, we calculate velocity using the ball's
    	// previous position. As a result, we can't directly set velocity.
    	// Instead, manipulate lastPosition so that the calculated velocity is
    	// as we want it to be
    	lastPosition = position.sub(velocity);
    }
}