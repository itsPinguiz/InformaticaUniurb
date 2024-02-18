package oop.lab03.shapes;

import oop.lab03.shapes.interfaces.Polygon;

public class Square implements Polygon {
    private static final int N_EDGES = 4;
    private final double edgeLength;

    public Square(final double edgeLength) {
        this.edgeLength = edgeLength;
    }

    public double getArea() {
        return this.edgeLength * this.edgeLength;
    }

    public double getPerimeter() {
        return this.edgeLength * N_EDGES;
    }

    public int getEdgeCount() {
        return Square.N_EDGES;
    }
}
