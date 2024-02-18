package it.unibo.oop.lab04.robot.base;

/**
 * Models a (x,y) position for a {@link it.unibo.oop.lab04.robot.base.Robot}
 *
 */
public class RobotPosition implements Position2D {

    private final int x;
    private final int y;

    /**
     * @param x
     *            X position
     * @param y
     *            Y position
     */
    public RobotPosition(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    public boolean equals(final Object o) {
        if (o instanceof Position2D) {
            final var p = (Position2D) o;
            return x == p.getX() && y == p.getY();
        }
        return false;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int hashCode() {
        /*
         * This could be implemented WAY better.
         */
        return x ^ y;
    }

    public RobotPosition sumVector(final int x, final int y) {
        return new RobotPosition(this.x + x, this.y + y);
    }

    public RobotPosition sumVector(final Position2D p) {
        return new RobotPosition(x + p.getX(), y + p.getY());
    }

    public String toString() {
        return "[" + x + ", " + y + "]";
    }
}
