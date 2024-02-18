package it.unibo.oop.lab04.robot.components;

import it.unibo.oop.lab04.robot.base.Robot;
import it.unibo.oop.lab04.robot.composable.ComposableRobot;

/**
 * Models a generic robot part
 *
 */
public interface RobotPart {

    boolean doOperation();

    double getEnergyRequired();

    String getName();

    boolean isOn();

    boolean isPlugged();

    boolean isPluggedTo(Robot cr);

    void plug(ComposableRobot cr);

    void turnOff();

    void turnOn();

    void unplug();

}
