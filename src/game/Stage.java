package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import javax.swing.*;

/**
 * Manager for the current game state, including spawning the map and
 * game objects as well as keeping track of win/lose conditions.
 */
public class Stage implements Updating {
	/**
	 * How many balls left the player gets at the start of each round.
	 */
	private static final int STARTING_BALL_COUNT = 8;
	
	/**
	 * Text display of the game state.
	 */
	private class StageInfoDisplay implements Drawable {
		private static Color INFO_DRAW_COLOR = new Color(1f, 1f, 1f);
		@Override
		public void paint(Graphics brush) {
			brush.setColor(INFO_DRAW_COLOR);
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
	 * How many balls the player has remaining.
	 */
	private int ballsLeft;
	/**
	 * The amount of goal pegs to spawn when generating the map.
	 */
	private int goalPegsToSpawn = 4;
	
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
	    
	    generateRandomStage(20, goalPegsToSpawn);
	    
	    ballsLeft = STARTING_BALL_COUNT;
	}
	
	/**
     * Randomly generates a stage with the given number of normal and goal pegs.
	 * @param normalPegs the number of normal (blue) pegs to create
     * @param goalPegs the number of goal (orange) pegs to create
	 * @author Carlton Luu
	 */
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
	
	/**
     * Launches a new ball from the launcher and starts a turn.
	 * @author Carlton Luu
	 */
	private void launchBall() {
		ballsLeft--;
		canShoot = false;
		inPlayBall = launcher.shootBall();
		game.addGameObject(inPlayBall);
	}
	
	/**
     * Updates the launcher's rotation based on player input.
	 * @param deltaTime the time elapsed since the last frame, used for smooth rotation
	 * @author Carlton Luu
	 */
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
	
	/**
     * Ends the current turn, removes hit pegs, and checks for win or loss.
	 * @author Carlton Luu
	 */
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
				goalPegsToSpawn++;
				generateRandomStage(20, goalPegsToSpawn);
				ballsLeft = STARTING_BALL_COUNT;
				canShoot = true;
			} else {
				// End the game with a game over message
				JDialog gameOverDialog = new JDialog();
				gameOverDialog.add(
						new JLabel("You lose! Final score: " + score)
				);
				gameOverDialog.setDefaultCloseOperation(
						JDialog.DO_NOTHING_ON_CLOSE
				);
				gameOverDialog.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosing(WindowEvent e) {
						game.endGame();
						gameOverDialog.dispose();
					}
				});
				
				gameOverDialog.pack();
				gameOverDialog.setVisible(true);
			}
		}
	}
	
	/**
     * Removes all pegs from the game.
	 * @author Carlton Luu
	 */
	private void clearPegs() {
		for (Peg p : pegs) {
			game.removeGameObject(p);
			CircleColliderSprite.removeCollider(p);
		}
		
		pegs = new ArrayList<>();
	}
	
	/**
     * Checks if the player has cleared all goal pegs.
	 * @return true if all goal pegs are cleared, false otherwise
	 * @author Carlton Luu
	 */
	private boolean hasWon() {
		// If any peg on the field is a GoalPeg, we haven't won yet
		for (Peg peg : pegs) {
			if (peg instanceof GoalPeg) {
				return false;
			}
		}
		
		return true;
	}

	/**
     * Updates the game each frame, including launcher rotation and turn progression.
	 * Also checks if active ball has fallen off screen.
	 * @param deltaTime time elapsed since last frame
	 * @author Carlton Luu
	 */
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
