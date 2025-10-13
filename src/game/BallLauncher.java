package game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * The ball launcher. Can be rotated through player input and release a
 * Ball object.
 */
public class BallLauncher extends DrawablePolygon implements Updating {
	/**
	 * Z layer of this object.
	 */
	private static final int DRAW_ORDER_LAYER = 5;
	/**
	 * The base color of this sprite.
	 */
	private static final Color COLOR_LAUNCHER = 
			new Color(0.32f, 0.40f, 0.81f);
	/**
	 * The speed in degrees/second that the launcher rotates.
	 */
	private static final double LAUNCHER_SPEED = 10;
	
	/**
	 * Whether or not the left directional key is held down.
	 */
	private boolean leftHeld = false;
	/**
	 * Whether or not the right directional key is held down.
	 */
	private boolean rightHeld = false;
	
	/**
	 * The input listener which listens for inputs that control the ball
	 * launcher. 
	 */
	public class BallInputListener implements KeyListener {
		@Override
		public void keyTyped(KeyEvent e) {};

		@Override
		public void keyPressed(KeyEvent e) {
			int keyCode = e.getKeyCode();
			switch (keyCode) {
			case KeyEvent.VK_LEFT:
				leftHeld = true;
				break;
			case KeyEvent.VK_RIGHT:
				rightHeld = true;
				break;
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			int keyCode = e.getKeyCode();
			switch (keyCode) {
			case KeyEvent.VK_LEFT:
				leftHeld = false;
				break;
			case KeyEvent.VK_RIGHT:
				rightHeld = false;
				break;
			}
		}
		
	}
	
	/**
	 * Constructs the ball launcher sprite's polygon.
	 * @return Point[] representation of the sprite's polygon
	 */
	private static Point[] makeLauncherShape() {
		return new Point[] {
				new Point(-20, 20), new Point(0, 0), new Point(20, 20),
				new Point(15, 30), new Point(7, 50), new Point(-7, 50),
				new Point(-15, 30)
		};
	}

	/**
	 * Creates a new BallLauncher.
	 * @param position
	 * @param rotation
	 */
	public BallLauncher(Point position, double rotation) {
		super(makeLauncherShape(), position, rotation, DRAW_ORDER_LAYER,
				COLOR_LAUNCHER);
	}
	
	public void SubscribeInputListener(Canvas canvas) {
		canvas.addKeyListener(new BallInputListener());
	}

	@Override
	public void update(double deltaTime) {
		if (leftHeld) {
			rotation += deltaTime * LAUNCHER_SPEED;
		}
		if (rightHeld) {
			rotation -= deltaTime * LAUNCHER_SPEED;
		}
	}

	
}
