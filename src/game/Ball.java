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
	private static final int RADIUS = 12;
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
     * Constructs a new Ball.
     * @param position the position of the ball
     * @param initialVelocity the velocity of the ball at time 0
     * @author Samuel Murphy
     */
    public Ball(Point position, Point initialVelocity) {
        super(NUM_POINTS, position, 0, DRAW_ORDER_LAYER, DRAW_COLOR, RADIUS);
        velocity = initialVelocity;
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
		
		// Multiply by 2 (since the other object is stationary) minus 0.1
		// so that some energy is lost
		velocity.x -= impulseVelocity.x * 1.9;
		velocity.y -= impulseVelocity.y * 1.9;
		
		double x2 = Math.pow(collider.position.x - position.x, 2);
		double y2 = Math.pow(collider.position.y - position.y, 2);
		double distance = Math.sqrt(x2 + y2);
		
		Point pushOut = velocity.clone();
		pushOut.normalize();
		
		double pushOutDistance = (radius + collider.radius) - distance;
		
		// Apply immediately to prevent ball getting stuck
		position.x += pushOut.x * pushOutDistance;
		position.y += pushOut.y * pushOutDistance;
		
		if (collider instanceof Peg) {
			Peg peg = (Peg) collider;
			peg.onHit();
		}
    }
    
    /**
     * Advances the ball's physics.
     * @author Samuel Murphy
     */
    @Override
    public void update(double deltaTime) {
    	position = position.add(velocity.mul(deltaTime));
    	velocity.y += GRAVITY * deltaTime;
    	
    	handleCircleCollisions();
    	handleBoundsCollisions();
    	
    }
    
    private void handleCircleCollisions() {
    	for (CircleColliderSprite collider : CircleColliderSprite.colliders) {
    		if (this.equals(collider)) continue;
    		if (this.isColliding(collider)) {
    			bounce(collider);
    		}
    	}
    }
    
    private void handleBoundsCollisions() {
    	if (position.x - radius < 0) {
    		position.x = radius;
    		velocity.x *= -1;
    	} else if (position.x + radius > 800) {
    		position.x = 800 - radius;
    		velocity.x *= -1;
    	} else if (position.y - radius < 0) {
    		position.y = radius;
    		velocity.y *= -1;
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
    }
    
}