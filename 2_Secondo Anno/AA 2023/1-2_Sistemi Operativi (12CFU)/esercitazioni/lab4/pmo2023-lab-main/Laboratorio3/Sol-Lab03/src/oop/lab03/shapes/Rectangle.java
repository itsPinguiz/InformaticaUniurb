package oop.lab03.shapes;

import oop.lab03.shapes.interfaces.Polygon;

public class Rectangle implements Polygon {
    private static final int EDGES = 4;
    private final double l1;
    private final double l2;

    public Rectangle(final double l1, final double l2) {
        this.l1 = l1;
        this.l2 = l2;
    }

    public double getArea() {
        return this.l1 * this.l2;
    }

    public double getPerimeter() {
        return (this.l1 + this.l2) * 2;
    }

    public int getEdgeCount() {
        return EDGES;
    }
}
