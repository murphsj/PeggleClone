package game;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Stage implements Updating {
	
	/**
	 * Text display of the game state.
	 */
	private class StageInfoDisplay implements Drawable {
		@Override
		public void paint(Graphics brush) {
			String stageInfo = "Balls Left: " + ballsLeft + "\n "
					+ "Score: " + score;
			brush.drawString(stageInfo, 0, 30);
		}

		@Override
		public int getDrawOrder() {
			return 20;
		}
	}
	
	/**
	 * Key listener which responds to gameplay control inputs.
	 */
	private class StageInputListener implements KeyListener {
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
			case KeyEvent.VK_SPACE:
				if (canShoot) {
					launchBall();
				}
				break;
			}
		}
	}
	
	/**
	 * The player's score.
	 */
	private static int score = 0;
	
	/**
	 * Whether or not the left directional key is held down.
	 */
	private boolean leftHeld = false;
	/**
	 * Whether or not the right directional key is held down.
	 */
	private boolean rightHeld = false;
	/**
	 * The ball launcher for this stage.
	 */
	private BallLauncher launcher;
	/**
	 * The game this stage is being played on.
	 */
	private PeggleClone game;
	/**
	 * The ball currently in play.
	 */
	private Ball inPlayBall;
	/**
	 * All currently spawned pegs.
	 */
	private ArrayList<Peg> pegs;
	/**
	 * Whether or not the player can currently shoot.
	 */
	private boolean canShoot = true;
	/**
	 * How many balls the player has remaining
	 */
	private int ballsLeft;
	
	/**
	 * Adds to the player's score.
	 * @param points amount of points to add
	 * @author Samuel Murphy
	 */
	public static void addScore(int points) {
		score += points;
	}
	
	/**
	 * Constructs a new Stage.
	 * @param game the game this stage is being played on
	 * @author Samuel Murphy
	 */
	public Stage(PeggleClone game) {
		this.game = game;
		// Add this as a game object and register the input listener
		game.addGameObject(this);
		game.addKeyListener(new StageInputListener());
		
		launcher = new BallLauncher(new Point(400, 50), 0);
	    game.addGameObject(launcher);
	    
	    game.addGameObject(new StageInfoDisplay());
	    
	    pegs = new ArrayList<>();
	    
	    generateRandomStage(20, 5);
	    
	    ballsLeft = 8;
	}
	
	private void generateRandomStage(int normalPegs, int goalPegs) {
		// Generate normal pegs
	    for (int i = 0; i < normalPegs; i++) {
	    	double randomX = Math.random() * 700;
	    	double randomY = Math.random() * 300;
	    	Peg peg = new NormalPeg(new Point(randomX, randomY + 200));
	    	game.addGameObject(peg);
	    	pegs.add(peg);
	    }
	    
	    // Generate goal pegs
	    for (int i = 0; i < goalPegs; i++) {
	    	double randomX = Math.random() * 700;
	    	double randomY = Math.random() * 300;
	    	Peg peg = new GoalPeg(new Point(randomX, randomY + 200));
	    	game.addGameObject(peg);
	    	pegs.add(peg);
	    }
	}
	
	private void launchBall() {
		ballsLeft--;
		canShoot = false;
		inPlayBall = launcher.shootBall();
		game.addGameObject(inPlayBall);
	}
	
	private void updateLauncher(double deltaTime) {
		double rotationDelta = 0;
		if (leftHeld) {
			rotationDelta += deltaTime;
		}
		if (rightHeld) {
			rotationDelta -= deltaTime;
		}
		
		launcher.rotate(rotationDelta);
	}
	
	private void endTurn() {
		if (inPlayBall != null) {
			game.removeGameObject(inPlayBall);
			CircleColliderSprite.removeCollider(inPlayBall);
			inPlayBall = null;
		}
		
		for (int i = pegs.size() - 1; i >= 0; i--) {
			Peg peg = pegs.get(i);
			if (peg.getIsHit()) {
				game.removeGameObject(peg);
				CircleColliderSprite.removeCollider(peg);
				pegs.remove(i);
			}
		}
		
		if (ballsLeft > 0) {
			canShoot = true;
		} else {
			if (hasWon()) {
				// Reset the game with a new level
				clearPegs();
				generateRandomStage(20, 5);
				ballsLeft = 6;
				canShoot = true;
			} else {
				// End the game with a game over message
				JOptionPane.showMessageDialog(game,
						"Game Over! Final score: " + score
				);
				
				game.endGame();
			}
		}
	}
	
	private void clearPegs() {
		for (Peg p : pegs) {
			game.removeGameObject(p);
			CircleColliderSprite.removeCollider(p);
		}
		
		pegs = new ArrayList<>();
	}
	
	private boolean hasWon() {
		// If any peg on the field is a GoalPeg, we haven't won yet
		for (Peg peg : pegs) {
			if (peg instanceof GoalPeg) {
				return false;
			}
		}
		
		return true;
	}

	@Override
	public void update(double deltaTime) {
		updateLauncher(deltaTime);
		
		if (inPlayBall != null) {
			if (inPlayBall.position.y > 900) {
				endTurn();
			}
		}
	}
}
