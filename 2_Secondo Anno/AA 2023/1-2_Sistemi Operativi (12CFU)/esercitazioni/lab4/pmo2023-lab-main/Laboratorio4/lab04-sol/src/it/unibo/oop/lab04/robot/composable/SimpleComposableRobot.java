package it.unibo.oop.lab04.robot.composable;

import java.util.Objects;

import it.unibo.oop.lab04.robot.base.BaseRobot;
import it.unibo.oop.lab04.robot.components.RobotPart;
import it.unibo.oop.lab04.robot.composable.util.AdvancedArrayBasedPartCollection;
import it.unibo.oop.lab04.robot.composable.util.RobotPartCollection;
// import it.unibo.oop.lab04.robot.composable.util.SimpleArrayBasedPartCollection;

/**
 *
 */
public class SimpleComposableRobot extends BaseRobot implements ComposableRobot {

    /*
     * Uncomment the following line and its import to test with a simpler part
     * collection.
     */
//    private final RobotPartCollection parts = new SimpleArrayBasedPartCollection();
    private final RobotPartCollection parts = new AdvancedArrayBasedPartCollection(-5);

    public SimpleComposableRobot(final String robotName) {
        super(robotName);
    }

    public final void attachComponent(final RobotPart rp) {
        Objects.requireNonNull(rp);
        rp.plug(this);
        if (rp.isPluggedTo(this)) {
            parts.add(rp);
        }
    }

    public final void doCycle() {
        parts.resetIterator();
        while (parts.hasAnotherPart()) {
            final RobotPart p = parts.next();
            if (p.isOn()) {
                if (p.isPluggedTo(this)) {
                    if (p.getEnergyRequired() < getBatteryLevel() && p.doOperation()) {
                        consumeBattery(p.getEnergyRequired());
                        log(p + " operated successfully.");
                    } else {
                        log(p + " did not work properly.");
                    }
                } else {
                    log(p + " is not connected.");
                }
            } else {
                log(p + " is off.");
            }
        }
    }

    public final void detachComponent(final RobotPart rp) {
        Objects.requireNonNull(rp);
        parts.remove(rp);
    }

}
