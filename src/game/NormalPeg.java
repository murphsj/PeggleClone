package game;

import java.awt.Color;

public class NormalPeg extends Peg {
	/**
	 * The base color of this sprite.
	 */
	private static final Color COLOR_NORMAL_PEG = new Color(0.32f, 0.40f, 0.81f);

    public NormalPeg(Point position) {
        super(position, COLOR_NORMAL_PEG);
    }
}