package by.epam.nickgrudnitsky.controller;

import by.epam.nickgrudnitsky.controller.command.Command;
import by.epam.nickgrudnitsky.controller.command.impl.LogInCommand;
import by.epam.nickgrudnitsky.controller.command.impl.ProfileCommand;
import by.epam.nickgrudnitsky.controller.command.impl.SignUpCommand;
import by.epam.nickgrudnitsky.controller.command.impl.WrongCommand;

public enum Action {
    PROFILE("/profile", new ProfileCommand()),
    JOIN("/join", new SignUpCommand()),
    LOG_IN("/login", new LogInCommand()),
    ERROR("/error", new WrongCommand());

    private String jspAddress;
    private Command command;

    Action(String jspAddress, Command command) {
        this.jspAddress = jspAddress;
        this.command = command;
    }

    public String getJspAddress() {
        return jspAddress;
    }

    public Command getCommand() {
        return command;
    }
}
