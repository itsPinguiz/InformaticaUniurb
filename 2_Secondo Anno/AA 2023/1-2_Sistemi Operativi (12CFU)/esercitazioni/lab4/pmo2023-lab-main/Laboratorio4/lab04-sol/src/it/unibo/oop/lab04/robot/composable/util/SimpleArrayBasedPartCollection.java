package it.unibo.oop.lab04.robot.composable.util;

import java.util.Arrays;

import it.unibo.oop.lab04.robot.components.RobotPart;

public class SimpleArrayBasedPartCollection extends AbstractArrayBasedPartCollection {

    private RobotPart[] parts = new RobotPart[0];

    @Override
    public void add(final RobotPart rp) {
        parts = Arrays.copyOf(parts, parts.length + 1);
        parts[parts.length - 1] = rp;
    }

    @Override
    protected int getSize() {
        return parts.length;
    }

    @Override
    protected RobotPart getPart(final int i) {
        return parts[i];
    }

    @Override
    protected void removePartAt(final int index) {
        final var na = new RobotPart[parts.length - 1];
        for (int i = 0; i < index; i++) {
            na[i] = parts[i];
        }
        for (int i = index + 1; i < parts.length; i++) {
            na[i - 1] = parts[i];
        }
        parts = na;
    }

}
