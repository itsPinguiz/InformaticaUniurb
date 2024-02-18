/**
 * 
 */
package it.unibo.oop.lab04.robot.components;

import it.unibo.oop.lab04.robot.base.BaseRobot;

/**
 *
 */
public class BorderNavigator extends AbstractPart {

    private static final int NORTH = 0;
    private static final int EAST = 1;
    private static final int SOUTH = 2;
    private static final int WEST = 3;
    private static final int[] DIRECTIONS = new int[] { NORTH, EAST, SOUTH, WEST };
    private int curDir;

    public BorderNavigator() {
        super("Border Navigator", BaseRobot.MOVEMENT_DELTA_CONSUMPTION);
    }

    private boolean tryToMove() {
        switch (curDir % DIRECTIONS.length) {
        case NORTH:
            return getRobot().moveUp();
        case EAST:
            return getRobot().moveRight();
        case SOUTH:
            return getRobot().moveDown();
        case WEST:
            return getRobot().moveLeft();
        default:
            System.out.println("There is a bug in " + getClass());
            return false;
        }
    }

    @Override
    public boolean doOperation() {
        if (isOn() && isPlugged()) {
            while (!tryToMove()) {
                curDir++;
                if (curDir > DIRECTIONS.length) {
                    curDir = 0;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Border Navigator";
    }

}
