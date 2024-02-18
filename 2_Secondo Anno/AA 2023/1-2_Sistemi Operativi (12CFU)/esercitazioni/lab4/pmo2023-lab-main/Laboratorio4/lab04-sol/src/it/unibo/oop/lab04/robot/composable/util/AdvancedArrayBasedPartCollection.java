package it.unibo.oop.lab04.robot.composable.util;

import java.util.Arrays;

import it.unibo.oop.lab04.robot.components.RobotPart;

/**
 * More efficient {@link RobotPartCollection} implementation. Starts with a
 * small array, grows it only when required to reduce the number of copies.
 *
 */
public class AdvancedArrayBasedPartCollection extends AbstractArrayBasedPartCollection {

    public static final int DEFAULT_SIZE = 10;
    private RobotPart[] parts;
    private int realSize;

    public AdvancedArrayBasedPartCollection(final int initialSize) {
        super();
        /*
         * The initial array must be *at least* one unit, or the expansion won't work.
         */
        parts = new RobotPart[Math.max(initialSize, 1)];
    }

    public AdvancedArrayBasedPartCollection() {
        this(DEFAULT_SIZE);
    }

    @Override
    public final void add(final RobotPart rp) {
        if (realSize >= parts.length) {
            expand();
        }
        parts[realSize++] = rp;
    }

    /**
     * Doubles the internal array size.
     */
    private void expand() {
        parts = Arrays.copyOf(parts, parts.length * 2);
    }

    @Override
    protected final int getSize() {
        return realSize;
    }

    @Override
    protected final RobotPart getPart(final int i) {
        return parts[i];
    }

    @Override
    protected void removePartAt(final int index) {
        /*
         * No shrinking. A more advanced collection may shrink if there are many
         * less elements than its size, to save memory.
         * 
         */
        realSize--;
        /*
         * Copy everything back of one position
         */
        for (int i = index; i < realSize; i++) {
            parts[i] = parts[i + 1];
        }
        /*
         * Clean the last element, to allow the garbage collector to clean up,
         * and prevent memory leaks.
         */
        parts[realSize] = null;
    }

}
