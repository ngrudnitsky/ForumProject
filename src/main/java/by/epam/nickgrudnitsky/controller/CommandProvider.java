package by.epam.nickgrudnitsky.controller;

import by.epam.nickgrudnitsky.controller.command.impl.SignUpCommand;
import by.epam.nickgrudnitsky.controller.command.Command;
import org.apache.commons.lang3.EnumUtils;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class CommandProvider {
    private final Map<CommandName, Command> commands = new HashMap<>();

    CommandProvider() {
        commands.put(CommandName.SIGN_UP, new SignUpCommand());
        commands.put(CommandName.WRONG_REQUEST, new SignUpCommand());
    }

    Command getCommand(String input) {
        try {
            String commandName = input.toUpperCase().replace(" ", "_");
            return commands.get(EnumUtils.getEnum(CommandName.class, commandName));
        } catch (IllegalArgumentException | NullPointerException e) {
            return commands.get(CommandName.WRONG_REQUEST);
        }
    }
}
