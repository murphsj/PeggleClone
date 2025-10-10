package game;

import java.awt.Color;
import java.awt.Graphics;

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
	 * The velocity of the ball.
	 */
    private Point velocity;
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
        velocity = new Point(0, 0);
    }
    
    public void bounce(CircleColliderSprite collider) {
    	// Circle bounce algorithm from FlatRedBall
    	// (accesed via Internet Archive)
    	// https://flatredball.com/documentation/tutorials/math/circle-collision
    	
    	// Get the line tangent to the line between the circles
		Point collisionLine = new Point(
				-(collider.position.y - position.y),
				collider.position.x - position.x
		);
		collisionLine.normalize();
		
		// Dot velocity with that line to get the velocity perpendicular to the
		// line of collision
		double magnitudePerpendicular = velocity.dot(collisionLine);
		
		Point tangentVelocity = collisionLine.mul(magnitudePerpendicular);
		Point impulseVelocity = velocity.sub(tangentVelocity);
		
		velocity.x -= impulseVelocity.x * 2;
		velocity.y -= impulseVelocity.y * 2;
    }
    
    /**
     * Advances the ball's physics.
     * @author Samuel Murphy
     */
    @Override
    public void update(double deltaTime) {
    	position = position.add(velocity.mul(deltaTime));
    	velocity.y += GRAVITY * deltaTime;
    	
    	if (position.y > 500) {
    		velocity.y *= -1;
    	}
    	
    	for (CircleColliderSprite collider : CircleColliderSprite.colliders) {
    		if (this.equals(collider)) continue;
    		if (this.isColliding(collider)) {
    			bounce(collider);
    		}
    	}
    }
    
    /**
     * Adds to the veloicty of the ball.
     * @param impulseVelocity 
     */
    public void applyImpulse(Point impulseVelocity) {
    	velocity = velocity.add(impulseVelocity);
    }
    
    @Override
	public void paint(Graphics brush) {
    	super.paint(brush);
    	brush.drawString(velocity.toString(), (int)(position.x + radius), (int)(position.y + radius));
    }
    
}