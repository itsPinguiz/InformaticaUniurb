package it.unibo.oop.lab04.robot.test;

import it.unibo.oop.lab04.robot.arms.RobotWithArms;
import it.unibo.oop.lab04.robot.arms.RobotWithTwoArms;
import it.unibo.oop.lab04.robot.base.BaseRobot;
import it.unibo.oop.lab04.robot.base.RobotPosition;

/**
 * Utility class for testing componible robots
 * 
 */
public final class TestRobotWithArms {

    private static final int CYCLES = 10;

    private TestRobotWithArms() {
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
        final RobotWithArms walle = new RobotWithTwoArms("Wall-e");
        final String wallePosition = walle + "'s position";
        final String walleItems = walle + "'s items carried";
        final String walleConsumption = walle + "'s consumption is correct";
        assertEquality(wallePosition, new RobotPosition(0, 0), walle.getPosition());
        assertEquality(walleConsumption, BaseRobot.BATTERY_FULL, walle.getBatteryLevel());
        double consumptionEmpty = walle.getBatteryLevel();
        for (int i = 0; i < CYCLES; i++) {
            walle.moveRight();
        }
        consumptionEmpty -= walle.getBatteryLevel();
        walle.pickUp();
        assertEquality(walleItems, 1, walle.getCarriedItemsCount());
        double consumption1Item = walle.getBatteryLevel();
        for (int i = 0; i < CYCLES; i++) {
            walle.moveUp();
        }
        consumption1Item -= walle.getBatteryLevel();
        assertEquality(walleConsumption, true, consumption1Item > consumptionEmpty);
        walle.pickUp();
        assertEquality(walleItems, 2, walle.getCarriedItemsCount());
        double consumption2Item = walle.getBatteryLevel();
        for (int i = 0; i < CYCLES; i++) {
            walle.moveUp();
        }
        consumption2Item -= walle.getBatteryLevel();
        assertEquality(walleConsumption, true, consumption2Item > consumption1Item);
        walle.pickUp();
        assertEquality(walleItems, 2, walle.getCarriedItemsCount());
        walle.dropDown();
        assertEquality(walleItems, 1, walle.getCarriedItemsCount());
        walle.dropDown();
        assertEquality(walleItems, 0, walle.getCarriedItemsCount());
        walle.dropDown();
        assertEquality(walleItems, 0, walle.getCarriedItemsCount());
    }
}
