package game;

import java.awt.Graphics;

/**
 * A game object that can be drawn onto the screen.
 */
public interface Drawable {
	/**
	 * Draws this object onto the canvas using the given Graphics instance.
	 * @param brush the brush used to render the element
	 */
	void paint(Graphics brush);
	
	/**
	 * Returns a number representing when this object will be drawn compared to
	 * other elements on the canvas.
	 * @return the draw order of the game object
	 */
	int getDrawOrder();
}
