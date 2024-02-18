package it.unibo.oop.lab04.robot.test;

import it.unibo.oop.lab04.robot.base.BaseRobot;
import it.unibo.oop.lab04.robot.base.RobotEnvironment;
import it.unibo.oop.lab04.robot.base.RobotPosition;

/**
 * Utility class for testing componible robots
 */
public final class TestRobots {

    private TestRobots() {
    }

    private static void assertEquality(final String propertyName, final Object expected, final Object actual) {
        if (actual == null || !actual.equals(expected)) {
            System.out.println(propertyName + " was expected to be " + expected
                    + ", but it yields " + actual + " (ERROR!)");
        } else {
            System.out.println(propertyName + ": " + actual + " (CORRECT)");
        }
    }

    public static void main(final String[] args) {
        final var stepsDefault = (int) (BaseRobot.BATTERY_FULL / BaseRobot.MOVEMENT_DELTA_CONSUMPTION);
        final var r0 = new BaseRobot("R2D2");
        final var r0pos = r0 + " position";
        final var r0bat = r0 + " battery";
        assertEquality(r0pos, new RobotPosition(0, 0), r0.getPosition());
        assertEquality(r0bat, BaseRobot.BATTERY_FULL, r0.getBatteryLevel());
        var steps = stepsDefault;
        while (r0.moveUp()) {
            steps--;
        }
        if (steps == 0) {
            assertEquality(r0bat, true, r0.getBatteryLevel() < BaseRobot.MOVEMENT_DELTA_CONSUMPTION);
            r0.recharge();
            assertEquality(r0bat, BaseRobot.BATTERY_FULL, r0.getBatteryLevel());
            steps = stepsDefault;
        } else {
            assertEquality(r0pos, new RobotPosition(0, RobotEnvironment.Y_UPPER_LIMIT), r0.getPosition());
        }
        while (r0.moveRight()) {
            steps--;
        }
        if (steps == 0) {
            assertEquality(r0bat, true, r0.getBatteryLevel() < BaseRobot.MOVEMENT_DELTA_CONSUMPTION);
            r0.recharge();
            assertEquality(r0bat, BaseRobot.BATTERY_FULL, r0.getBatteryLevel());
            steps = stepsDefault;
        } else {
            assertEquality(r0pos,
                new RobotPosition(RobotEnvironment.X_UPPER_LIMIT, RobotEnvironment.Y_UPPER_LIMIT),
                r0.getPosition());
        }
        while (r0.moveRight()) {
            steps--;
        }
        if (steps == 0) {
            assertEquality(r0bat, true, r0.getBatteryLevel() < BaseRobot.MOVEMENT_DELTA_CONSUMPTION);
            r0.recharge();
            assertEquality(r0bat, BaseRobot.BATTERY_FULL, r0.getBatteryLevel());
            steps = stepsDefault;
        } else {
            assertEquality(r0pos,
                new RobotPosition(RobotEnvironment.X_UPPER_LIMIT, RobotEnvironment.Y_UPPER_LIMIT),
                r0.getPosition());
        }

    }
}
