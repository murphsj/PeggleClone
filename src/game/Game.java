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
   * How many game updates occur in one second.
   */
  private static final long FPS = 60;
	
  protected boolean on = true;
  protected int width, height;
  protected Image buffer;
  
  private Frame frame;
  
  public Game(String name, int inWidth, int inHeight) {
	  width = inWidth;
	  height = inHeight;
	  
	  // Frame can be read as 'window' here.
    frame = new Frame(name);
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
  
  /**
   * Starts the update cycle of the game, invoking the update and draw methods
   * every game tick.
   * This function causes the thread to yield until the game ends.
   * @author Samuel Murphy
   */
  public void startGameLoop() {
	  long lastFrameTime = System.nanoTime();
	  double nsPerFrame = 1000000000D/(double)FPS;
	  double ticks = 0;
	  
	  while (on) {
		  long currentTime = System.nanoTime();
		  
		  ticks += (double)(currentTime - lastFrameTime)/nsPerFrame;
		  
		  // Run the update method. If frameTime is bigger than the max allowed
		  // value, split into several calls of the update method
		  while (ticks >= 1) {
			  update((nsPerFrame * 1e-9D) * 5);
			  ticks -= 1;
		  }
		  
		  // Draw updated canvas
		  repaint();
		  lastFrameTime = currentTime;
	  }
  }
  
  /**
   * Disposes of frames when game is over.
   * @author Carlton Luu
   */
  public void endGame() {
	  frame.dispose();
  }
}
