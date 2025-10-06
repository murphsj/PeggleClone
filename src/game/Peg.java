public abstract class Peg extends CircleColliderSprite {
    private boolean isHit;

    public Peg(Point position, Color color) {
        super(circular, position, 0, 0, color, 1);
    }

    public abstract void onHit();
}