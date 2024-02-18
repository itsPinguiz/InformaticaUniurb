package it.unibo.oop.lab04.robot.composable.util;

import it.unibo.oop.lab04.robot.components.RobotPart;

public abstract class AbstractArrayBasedPartCollection implements RobotPartCollection {

    private int iteratorIndex;

    /**
     * @return the actual size of the collection
     */
    protected abstract int getSize();

    /**
     * Given a legal index, return the corresponding {@link RobotPart}.
     * 
     * @param a legal index
     * @return a {@link RobotPart}
     */
    protected abstract RobotPart getPart(int i);

    /**
     * Given a legal index, remove the corresponding {@link RobotPart}.
     * 
     * @param i a legal index
     */
    protected abstract void removePartAt(int i);

    @Override
    public final void resetIterator() {
        iteratorIndex = 0;
    }

    @Override
    public final boolean hasAnotherPart() {
        return iteratorIndex < getSize();
    }

    @Override
    public final RobotPart next() {
        if (hasAnotherPart()) {
            return getPart(iteratorIndex++);
        }
        return null;
    }

    private int indexOf(final RobotPart rp) {
        for (int i = 0; i < getSize(); i++) {
            if (getPart(i).equals(rp)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public final void remove(final RobotPart rp) {
        final int index = indexOf(rp);
        if (index >= 0) {
            /*
             * Index is legal!
             */
            removePartAt(index);
        }
        /*
         * Do nothing.
         */
    }

}
