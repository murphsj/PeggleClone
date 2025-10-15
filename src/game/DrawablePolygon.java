package game;

import java.awt.Color;
import java.awt.Graphics;

/**
 * A polygon which can be drawn to the screen.
 * Holds information about the draw order and color with which to render
 * the polygon.
 */
public class DrawablePolygon extends Polygon implements Drawable {
	/**
	 * The draw order of this polygon.
	 */
	int drawOrder;
	
	/**
	 * The color to fill the polygon with.
	 */
	Color drawColor;

	/**
	 * Constructs a new DrawablePolygon.
	 * @param shape the shape of the polygon
	 * @param position the translation of the polygon's center
	 * @param rotation the rotation of the polygon
	 * @param drawOrder the z-index of the polygon
	 * @param drawColor the color to render the polygon with
	 * @author Carlton Luu
	 */
	public DrawablePolygon(Point[] shape, Point position, double rotation,
			int drawOrder, Color drawColor) {
		super(shape, position, rotation);
		this.drawColor = drawColor;
	}

	/**
     * Draws the polygon on the screen using its current
	 * color and vertex positions.
	 * @param brush the Graphics object used to render polygon.
	 * @author Carlton Luu
     */
	@Override
	public void paint(Graphics brush) {
		brush.setColor(drawColor);
		Point[] points = getPoints();
		int[] pointsX = new int[points.length];
		int[] pointsY = new int[points.length];
		
		for (int i = 0; i < points.length; i++) {
			pointsX[i] = (int)points[i].x;
			pointsY[i] = (int)points[i].y;
		}
		
		
		brush.fillPolygon(pointsX, pointsY, points.length);
	}

	/**
     * Returns the draw order used to determine rendering priority.
	 * @author Carlton Luu
     */
	@Override
	public int getDrawOrder() {
		return drawOrder;
	}

}
