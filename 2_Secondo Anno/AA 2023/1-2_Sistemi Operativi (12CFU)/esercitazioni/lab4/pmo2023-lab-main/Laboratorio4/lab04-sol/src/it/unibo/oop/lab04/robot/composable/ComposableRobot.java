package it.unibo.oop.lab04.robot.composable;

import it.unibo.oop.lab04.robot.base.Robot;
import it.unibo.oop.lab04.robot.components.RobotPart;

public interface ComposableRobot extends Robot {

    void attachComponent(RobotPart rp);

    void detachComponent(RobotPart rp);

    void doCycle();

}
