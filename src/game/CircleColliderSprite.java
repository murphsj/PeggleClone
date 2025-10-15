package game;

import java.awt.Color;
import java.util.ArrayList;

/**
 * Base class for game objects which use a circular hitbox. Contains utilities
 * which are useful specifically for circular objects 
 */
public class CircleColliderSprite extends DrawablePolygon  {
	
	/**
	 * The radius of the circle, used for collision detection and
	 * shape generation if a shape is not provided.
	 */
	double radius = 0;
	
	public static ArrayList<CircleColliderSprite> colliders = new ArrayList<>();
	
	public static void removeCollider(CircleColliderSprite sprite) {
		colliders.remove(sprite);
	}
	
	/**
	 * Builds a circular polygonal shape with the given radius and number
	 * of points.
	 * Precondition: numPoints is &gt;= 2
	 * @param numPoints how many points the shape should have
	 * @param radius the radius of the shape
	 * @return Point[]
	 * @author Carlton Luu
	 */
	static Point[] makeCircularShape(int numPoints, double radius) {
		if (numPoints < 2) {
			throw new IllegalArgumentException(
			"CircleColliderSprite makeCircularShape: Invalid number of points"
			);
		}
		Point[] shape = new Point[numPoints];
		for (int i = 0; i < numPoints; i++) {
			double period = ((double)i / numPoints) * Math.PI * 2;
			shape[i] = new Point(
					Math.cos(period) * radius,
					Math.sin(period) * radius
			);
		}
		
		return shape;
	}
	
	/**
	 * Constructs a new CircleColliderSprite.
	 * @param shape
	 * @param position
	 * @param rotation
	 * @param drawOrder
	 * @param drawColor
	 * @param radius
	 * @author Carlton Luu
	 */
	public CircleColliderSprite(Point[] shape, Point position, double rotation,
			int drawOrder, Color drawColor, double radius) {
		super(shape, position, rotation, drawOrder, drawColor);
		this.radius = radius;
		colliders.add(this);
	}
	
	/**
	 * Constructs a new CircleColliderSprite with an automatically generated
	 * polygonal circle shape.
	 * @param numPoints how many points the shape should have; must be &gt;= 2
	 * @param position
	 * @param rotation
	 * @param drawOrder
	 * @param drawColor
	 * @param radius
	 * @author Carlton Luu
	 */
	public CircleColliderSprite(int numPoints, Point position, double rotation,
			int drawOrder, Color drawColor, double radius) {
		this(makeCircularShape(numPoints, radius), position, rotation,
				drawOrder, drawColor, radius);
	}
	
	/**
	 * Returns whether or not this sprite is in contact with another circular
	 * sprite.
	 * @param other
	 * @return true if the objects are colliding, otherwise false
	 * @author Carlton Luu
	 */
	public boolean isColliding(CircleColliderSprite other) {
		// Two circles are overlapping if the distance between their centers is
		// less than or equal to the sum of their radii
		
		// Get the difference between the two positions and determine its
		// magnitude using the Pythagorean theorem
		double x2 = Math.pow(other.position.x - position.x, 2);
		double y2 = Math.pow(other.position.y - position.y, 2);
		double distance = Math.sqrt(x2 + y2);
		
		return (distance <= radius + other.radius);
	}
	
	/**
	 * Returns whether or not this sprite is outside of the given bounds.
	 * @return true if the sprite is outside of the bounds, otherwise false
	 * @author Carlton Luu
	 */
	public boolean isTouchingBounds(Point min, Point max) {
		// Actual circle-rectangle collision is annoying, but circles will only
		// collide with the screen borders and other circles, so we can use
		// a shortcut
		return position.x - radius < min.x
				|| position.x + radius > max.x
				|| position.y - radius < min.y
				|| position.y + radius > max.y;
		
	}

}
