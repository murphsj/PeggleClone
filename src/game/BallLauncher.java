package game;

import java.awt.Color;

/**
 * The ball launcher. Can be rotated through player input and release a
 * Ball object.
 */
public class BallLauncher extends DrawablePolygon {
	/**
	 * The speed in degrees/second that the launcher rotates.
	 */
	private static final double LAUNCHER_SPEED = 10;
	/**
	 * How fast the ball gets shot out of the launcher.
	 */
	private static final double LAUNCHER_POWER = 80;
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
	 * Constructs the ball launcher sprite's polygon.
	 * @return Point[] representation of the sprite's polygon
	 * @author Samuel Murphy
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
	 * @param position the point to create launcher
	 * @param rotation amount to rotate launcher
	 * @author Samuel Murphy
	 */
	public BallLauncher(Point position, double rotation) {
		super(makeLauncherShape(), position, rotation, DRAW_ORDER_LAYER,
				COLOR_LAUNCHER);
	}
	
	/**
	 * Spawns a new Ball at the position of the launcher moving in the
	 * direction of the launcher.
	 * @return the created Ball
	 * @author Samuel Murphy
	 */
	public Ball shootBall() {
		return new Ball(position.clone(), new Point(
				Math.cos(Math.toRadians(rotation + 90)) * LAUNCHER_POWER,
				Math.sin(Math.toRadians(rotation + 90)) * LAUNCHER_POWER
		));
	}
	
	/**
	 * Rotates the launcher by the given change value. rotationChange should be
	 * this update's delta-time value scaled based on if the launcher should be
	 * stationary, rotating left, or rotating right.
	 * @param rotationChange amount to rotate launcher
	 * @author Carlton Luu
	 */
	public void rotate(double rotationChange) {
		rotation += rotationChange * LAUNCHER_SPEED;
	}
}
