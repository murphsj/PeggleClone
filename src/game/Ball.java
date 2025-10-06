public class Ball extends CircleColliderSprite {
    private Point velocity;
    private double rotationalAccel;

    public Ball(Point position, Point initialVelocity) {
        super(circular, position, );
        this.velocity = initialVelocity;
    }

    public applyImpulse(Point velocity) {
        
    }
}