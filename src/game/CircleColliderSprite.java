package game;

import java.awt.Color;

/**
 * Base class for game objects which use a circular hitbox. Contains utilities
 * which are useful specifically for circular objects 
 */
public class CircleColliderSprite extends DrawablePolygon  {
	
	double radius = 0;
	
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
	

	public CircleColliderSprite(Point[] shape, Point position, double rotation,
			int drawOrder, Color drawColor, double radius) {
		super(shape, position, rotation, drawOrder, drawColor);
		
	}
	
	
	public CircleColliderSprite(int numPoints, Point position, double rotation,
			int drawOrder, Color drawColor, double radius) {
		this(makeCircularShape(numPoints, radius), position, rotation,
				drawOrder, drawColor, radius);
		
	}

}
