package game;

/**
 * A game object that is dynamic, i.e. changes over time.
 */
public interface Updating {
	/**
	 * Called every game tick. Should be used to handle dynamic behavior for
	 * the object.
	 * @param deltaTime time passed, in seconds, between this frame and the last
	 */
	public void update(double deltaTime);
}
