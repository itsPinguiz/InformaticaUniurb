package it.unibo.oop.lab04.robot.components;

public interface CommandableRobotPart extends RobotPart {

    void sendCommand(String c);

    String[] availableCommands();

}
