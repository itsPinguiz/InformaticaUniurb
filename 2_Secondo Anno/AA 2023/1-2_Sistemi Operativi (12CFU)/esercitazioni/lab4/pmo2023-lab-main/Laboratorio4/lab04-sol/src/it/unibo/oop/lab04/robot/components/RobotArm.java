package it.unibo.oop.lab04.robot.components;

/**
 * Models a robotic arm
 *
 */
public class RobotArm extends AbstractPartWithCommands {

    protected static final double ENERGY_REQUIRED = 0.2;
    protected static final String PICK = "pick";
    protected static final String DROP = "drop";
    private boolean grabbing;

    public RobotArm() {
        super("RobotArm", ENERGY_REQUIRED, PICK, DROP);
    }

    protected boolean doOperation(final String command) {
        if (PICK.equals(command)) {
            if (grabbing) {
                System.out.println(this + " is already grabbing an object");
                return false;
            }
            grabbing = true;
            return true;
        }
        if (DROP.equals(command)) {
            if (grabbing) {
                grabbing = false;
                return true;
            }
            System.out.println(this + " is not grabbing an object");
            return false;
        }
        System.out.println(this + " does not know what " + command + " is");
        return false;
    }

    public String toString() {
        return "RobotArm";
    }
}
