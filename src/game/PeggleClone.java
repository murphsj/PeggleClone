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
	
	private ArrayList<Drawable> drawableObjects;

	public PeggleClone() {
	    super("Peggle",800,600);
	    drawableObjects = new ArrayList<>();
	    
	    for (int i = 0; i < 9; i++) {
	    	Point position = new Point(i * 100, 20);
	    	Drawable collider = new CircleColliderSprite((i+1) * 2, position, 0, 0, Color.BLUE, 50);
	    	addDrawable(collider);
	    }
	    
	    this.setFocusable(true);
	    this.requestFocus();
	}
	
	public void addDrawable(Drawable object) {
		drawableObjects.add(object);
	}
  
	public void paint(Graphics brush) {
    	brush.setColor(Color.black);
    	brush.fillRect(0,0,width,height);
    	
    	for (Drawable d : drawableObjects) {
    		d.paint(brush);
    	}
	}
  
	public static void main (String[] args) {
   		PeggleClone a = new PeggleClone();
		a.repaint();
  }
}