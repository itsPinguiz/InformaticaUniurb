package it.unibo.oop.lab04.robot.components;

import it.unibo.oop.lab04.robot.base.Robot;
import it.unibo.oop.lab04.robot.composable.ComposableRobot;

/**
 * Abstract class to use as a basis to implement a robotic part that can perform
 * move operation (e.g. actuators)
 *
 */
public abstract class AbstractPart implements RobotPart {

    private boolean on;
    private final String description;
    private final double energyRequired;
    private ComposableRobot robot;

    protected AbstractPart(final String desc, final double consumption) {
        description = desc;
        energyRequired = consumption;
    }

    public String getName() {
        return description;
    }

    public final boolean isOn() {
        return on;
    }

    public final void turnOff() {
        on = false;
    }

    public final void turnOn() {
        on = true;
    }

    protected final ComposableRobot getRobot() {
        return robot;
    }

    public final void plug(final ComposableRobot cr) {
        unplug();
        robot = cr;
    }

    public final void unplug() {
        turnOff();
        robot = null;
    }

    public final boolean isPlugged() {
        return robot != null;
    }

    public final boolean isPluggedTo(final Robot r) {
        return isPlugged() && robot.equals(r);
    }

    public final double getEnergyRequired() {
        return energyRequired;
    }

    public abstract String toString();
}
