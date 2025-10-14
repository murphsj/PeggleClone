package game;

/*
CLASS: YourGameNameoids
DESCRIPTION: Extending Game, YourGameName is all in the paint method.
NOTE: This class is the metaphorical "main method" of your program,
      it is your control center.

*/
import java.awt.*;
import java.util.ArrayList;

class PeggleClone extends Game {
	private static final long serialVersionUID = 1L;
	
	/**
	 * A list of all drawable game objects currently in the scene.
	 */
	private ArrayList<Drawable> drawableObjects;
	/**
	 * A list of all updating game objects currently in the scene.
	 */
	private ArrayList<Updating> updatingObjects;

	/**
	 * Constructs a new PeggleClone.
	 */
	public PeggleClone() {
	    super("Peggle",800,600);
	    drawableObjects = new ArrayList<>();
	    updatingObjects = new ArrayList<>();
	    
	    // Initialize the game stage
	    Stage stage = new Stage(this);
	    
	    setFocusable(true);
	    requestFocus();
	    startGameLoop();
	}

	/**
	 * Adds a game object to the scene, enabling drawing and updating behavior.
	 * @param <T> the type of the game object
	 * @param gameObject the game object to add
	 * @author Samuel Murphy
	 */
	public <T> void addGameObject(T gameObject) {
		if (gameObject instanceof Drawable) {
			drawableObjects.add((Drawable) gameObject);
		}
		if (gameObject instanceof Updating) {
			updatingObjects.add((Updating) gameObject);
		}
	}
	
	/**
	 * Removes a game object from the scene.
	 * @param <T> the type of the game object
	 * @param gameObject the game object to remove
	 * @author Samuel Murphy
	 */
	public <T> void removeGameObject(T gameObject) {
		if (gameObject instanceof Drawable) {
			drawableObjects.remove((Drawable) gameObject);
		}
		if (gameObject instanceof Updating) {
			updatingObjects.remove((Updating) gameObject);
		}
	}
  
	public void paint(Graphics brush) {
    	brush.setColor(Color.black);
    	brush.fillRect(0,0,width,height);
    	
    	for (Drawable d : drawableObjects) {
    		d.paint(brush);
    	}
	}
	
	public void update(double deltaTime) {
		for (Updating u : updatingObjects) {
			u.update(deltaTime);
		}
	}
  
	public static void main (String[] args) {
   		PeggleClone a = new PeggleClone();
		a.repaint();
  }
}