package oop.lab03.shapes;

import oop.lab03.shapes.interfaces.Polygon;

public class SimpleTriangle implements Polygon {

    private static final int N_EDGES = 3;
    private final double l1;
    private final double l2;
    private final double l3;
    private final double h1;

    public SimpleTriangle(final double l1, final double l2, final double l3, final double h1) {
        this.l1 = l1;
        this.l2 = l2;
        this.l3 = l3;
        this.h1 = h1;
    }

    public double getArea() {
        return (this.l1 * this.h1) / 2;
    }

    public double getPerimeter() {
        return this.l1 + this.l2 + this.l3;
    }

    public int getEdgeCount() {
        return N_EDGES;
    }
}
