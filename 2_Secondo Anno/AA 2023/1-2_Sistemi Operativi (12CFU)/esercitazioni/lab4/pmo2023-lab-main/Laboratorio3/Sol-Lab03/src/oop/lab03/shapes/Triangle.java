package oop.lab03.shapes;

import oop.lab03.shapes.interfaces.Polygon;

public class Triangle implements Polygon {

    private static final int N_EDGES = 3;
    private final double l1;
    private final double l2;
    private final double l3;

    public Triangle(final double l1, final double l2, final double l3) {
        this.l1 = l1;
        this.l2 = l2;
        this.l3 = l3;
    }

    /**
     * @see https://en.wikipedia.org/wiki/Heron%27s_formula
     */
    public double getArea() {
        final double semiPerimeter = this.getPerimeter() / 2;
        return Math.sqrt(semiPerimeter * (semiPerimeter - this.l1) * (semiPerimeter - this.l2) * (semiPerimeter - this.l3));
    }

    public double getPerimeter() {
        return this.l1 + this.l2 + this.l3;
    }

    public int getEdgeCount() {
        return N_EDGES;
    }

}
