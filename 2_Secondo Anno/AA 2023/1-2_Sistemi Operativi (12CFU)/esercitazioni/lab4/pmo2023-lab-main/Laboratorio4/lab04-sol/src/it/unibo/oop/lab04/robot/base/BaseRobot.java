package it.unibo.oop.lab04.robot.base;

/**
 * Models a generic Robot
 *
 */
public class BaseRobot implements Robot {

    public static final double BATTERY_FULL = 100;
    public static final double MOVEMENT_DELTA_CONSUMPTION = 1.2;
    private static final int MOVEMENT_DELTA = 1;

    private double batteryLevel;
    private final RobotEnvironment environment;
    private final String robotName;

    /**
     * Creates a new robot with a full battery
     * 
     * @param robotName
     * @throws Exception
     */
    public BaseRobot(final String robotName) {
        this.environment = new RobotEnvironment(new RobotPosition(0, 0));
        this.robotName = robotName;
        this.batteryLevel = BATTERY_FULL;
    }

    /**
     * Consume the amount of energy provided in input from the battery
     * 
     * @param amount
     */
    protected void consumeBattery(final double amount) {
        if (batteryLevel >= amount) {
            this.batteryLevel -= amount;
        } else {
            this.batteryLevel = 0;
        }
    }

    /**
     * Consume the amount of energy required to move the robot substracting it
     * from the current battery level
     */
    private void consumeBatteryForMovement() {
        consumeBattery(getBatteryRequirementForMovement());
    }

    protected double getBatteryRequirementForMovement() {
        return MOVEMENT_DELTA_CONSUMPTION;
    }

    /**
     * 
     * @return The robot's current battery level
     */
    public double getBatteryLevel() {
        return Math.round(batteryLevel * 100d) / BATTERY_FULL;
    }

    /**
     * 
     * @return The robot environment
     */
    public Position2D getPosition() {
        return environment.getPosition();
    }

    /**
     * @param operationCost
     *            how much
     * @return
     */
    protected boolean isBatteryEnough(final double operationCost) {
        return batteryLevel > operationCost;
    }

    /**
     * Log in stdout the {@link String} provided in input
     * 
     * @param msg
     */
    protected void log(final String msg) {
        System.out.println("[" + this.robotName + "]: " + msg);
    }

    private boolean move(final int dx, final int dy) {
        if (isBatteryEnough(getBatteryRequirementForMovement())) {
            if (environment.move(dx, dy)) {
                consumeBatteryForMovement();
                log("Moved to position " + environment.getPosition() + ". Battery: " + getBatteryLevel() + "%.");
                return true;
            }
            log("Can not move of (" + dx + "," + dy
                    + ") the robot is touching the world boundary: current position is " + environment.getPosition());
        } else {
            log("Can not move, not enough battery. Required: " + getBatteryRequirementForMovement()
                + ", available: " + batteryLevel + " (" + getBatteryLevel() + "%)");
        }
        return false;
    }

    /**
     * Moves the robot down by one unit
     * 
     * @return If the Down movement has been performed
     */
    public boolean moveDown() {
        return move(0, -MOVEMENT_DELTA);
    }

    /**
     * Moves the robot left by one unit
     * 
     * @return A boolean indicating if the Left movement has been performed
     */
    public boolean moveLeft() {
        return move(-MOVEMENT_DELTA, 0);
    }

    /**
     * Moves the robot right by one unit
     * 
     * @return A boolean indicating if the Right movement has been performed
     */
    public boolean moveRight() {
        return move(MOVEMENT_DELTA, 0);
    }

    /**
     * Moves the robot up by one unit
     * 
     * @return If the Up movement has been performed
     */
    public boolean moveUp() {
        return move(0, MOVEMENT_DELTA);
    }

    /**
     * Fully recharge the robot
     */
    public void recharge() {
        this.batteryLevel = BATTERY_FULL;
    }

    public String toString() {
        return robotName;
    }
}
