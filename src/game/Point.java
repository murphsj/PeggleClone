package game;

/*
CLASS: Point
DESCRIPTION: Ah, if only real-life classes were this straight-forward. We'll
             use 'Point' throughout the program to store and access 
             coordinates.
*/

public class Point implements Cloneable {
	public double x,y;
	public Point(double inX, double inY) { x = inX; y = inY; }
  
	//added sjp
	public double getX(){ return x;}
	public double getY(){ return y;}
	public void setX(double x){ this.x = x;}
	public void setY(double y){ this.y = y;}
	
	public Point clone() {
		return new Point(x, y);
	}
	
	public Point add(Point p) {
		return new Point(x+p.x, y+p.y);
	}
	
	public Point sub(Point p) {
		return new Point(x-p.x, y-p.y);
	}
	
	public Point mul(double s) {
		return new Point(x*s, y*s);
	}
	
	public double length() {
		return Math.sqrt(Math.pow(x,  2) + Math.pow(y, 2));
	}
	
	/**
	 * Returns the dot product of two vectors.
	 * @param p
	 * @return the dot result between this Point and p
	 */
	public double dot(Point p) {
		return (x * p.x) + (y * p.y);
	}
	
	/**
	 * Normalizes the point in-place, setting its length to 1 while maintaining
	 * direction.
	 * @author Samuel Murphy
	 */
	public void normalize() {
		double length = length();
		x /= length;
		y /= length;
	}
	
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}