package game;

/*
CLASS: Game
DESCRIPTION: A painted canvas in its own window, updated every tenth second.
USAGE: Extended by YourGameName.
NOTE: You don't need to understand the details here, no fiddling neccessary.*/
import java.awt.*;
import java.awt.event.*;

abstract class Game extends Canvas {
  /**
   * The maximum deltatime for a game update in milliseconds.
   */
  private static final long MAX_DELTA_TIME = 1000;
  /**
   * How many game updates occur in one second.
   */
  private static final double FPS = 60;
	
  protected boolean on = true;
  protected int width, height;
  protected Image buffer;
  
	public Game(String name, int inWidth, int inHeight) {
	  width = inWidth;
	  height = inHeight;
	  
	  // Frame can be read as 'window' here.
    Frame frame = new Frame(name);
    frame.add(this);
    frame.setSize(width,height);
    frame.setVisible(true);
    frame.setResizable(false);
    frame.addWindowListener(new WindowAdapter() { 
      public void windowClosing(WindowEvent e) {System.exit(0);} 
    });
    
    buffer = createImage(width, height);
	}
  
  // 'paint' will be called every tenth of a second that the game is on.
  abstract public void paint(Graphics brush);
  
  /**
   * Update is called for every physics/game step.
   * @param deltaTime the time passed since the last step in seconds
   */
  abstract public void update(double deltaTime);
  
  
  // 'sleep' is a simple helper function used in 'update'.
  private void sleep(int time) {
    try {Thread.sleep(time);} catch(Exception exc){};
  }
  
  /**
   * Starts the update cycle of the game, invoking the update and draw methods
   * every game tick.
   * This function causes the thread to yield until the game ends.
   */
  public void startGameLoop() {
	  long lastFrameTime = System.currentTimeMillis();
	  long timePerFrame = (long) (1000/FPS);
	  
	  while (on) {
		  long currentTime = System.currentTimeMillis();
		  long frameTime = currentTime - lastFrameTime;
		  
		  // Run the update method. If frameTime is bigger than the max allowed
		  // value, split into several calls of the update method
		  while (frameTime > 0) {
			  long deltaTime = Math.min(frameTime, MAX_DELTA_TIME);
			  update(deltaTime * 0.0001);
			  frameTime -= deltaTime;
		  }
		  
		  // If neccecary, wait to preserve framerate
		  if (frameTime < timePerFrame) sleep((int) (timePerFrame - frameTime));
		  
		  // Draw updated canvas
		  repaint();
	  }
  }
}