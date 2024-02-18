package it.unibo.oop.lab04.robot.arms;

/**
 * Models a robotic arm
 *
 */
public class BasicArm {

    private static final double ENERGY_REQUIRED_TO_MOVE = 0.2;
    private static final double ENERGY_REQUIRED_TO_GRAB = 0.1;
    private boolean grabbing;
    private final String name;

    public BasicArm(final String name) {
        this.name = name;
    }

    public boolean isGrabbing() {
        return grabbing;
    }

    public void pickUp() {
        grabbing = true;
    }

    public void dropDown() {
        grabbing = false;
    }

    public double getConsuptionForPickUp() {
        return ENERGY_REQUIRED_TO_MOVE + ENERGY_REQUIRED_TO_GRAB;
    }

    public double getConsuptionForDropDown() {
        return ENERGY_REQUIRED_TO_MOVE;
    }

    public String toString() {
        return name;
    }
}
