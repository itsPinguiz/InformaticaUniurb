package it.unibo.oop.lab04.robot.base;

/**
 * A robot that can move
 *
 */
public interface Robot {

    /**
     * Moves the robot up by one unit
     * 
     * @return true if a movement has been performed
     */
    boolean moveUp();

    /**
     * Moves the robot down by one unit
     * 
     * @return true if a movement has been performed
     */
    boolean moveDown();

    /**
     * Moves the robot left by one unit
     * 
     * @return true if a movement has been performed
     */
    boolean moveLeft();

    /**
     * Moves the robot right by one unit
     * 
     * @return true if a movement has been performed
     */
    boolean moveRight();

    /**
     * Fully recharge the robot
     */
    void recharge();

    /**
     * @return The robot's current battery level
     */
    double getBatteryLevel();

    /**
     * @return The robot environment
     */
    Position2D getPosition();

}
