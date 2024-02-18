package it.unibo.oop.lab04.robot.components;

import java.util.Arrays;

/**
 *
 */
public abstract class AbstractPartWithCommands extends AbstractPart implements CommandableRobotPart {

    protected static final String NULL_CMD = "No command";

    private final String[] commands;
    private String selectedCommand = NULL_CMD;

    protected AbstractPartWithCommands(final String desc, final double consumption, final String... cmds) {
        super(desc, consumption);
        commands = Arrays.copyOf(cmds, cmds.length);
    }

    public String[] availableCommands() {
        return Arrays.copyOf(commands, commands.length);
    }

    public boolean doOperation() {
        if (isPlugged() && isOn() && !selectedCommand.equals(NULL_CMD)) {
            return doOperation(selectedCommand);
        }
        return false;
    }

    protected abstract boolean doOperation(String command);

    public void sendCommand(final String c) {
        for (final String s : commands) {
            if (s.equals(c)) {
                selectedCommand = s;
            }
        }
    }

}
