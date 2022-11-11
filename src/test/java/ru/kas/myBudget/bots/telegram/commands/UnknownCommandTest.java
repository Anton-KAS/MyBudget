package ru.kas.myBudget.bots.telegram.commands;

import org.junit.jupiter.api.DisplayName;

import static ru.kas.myBudget.bots.telegram.commands.UnknownCommand.UNKNOWN_MESSAGE;

@DisplayName("Unit-level testing for UnknownCommand")
public class UnknownCommandTest extends AbstractCommandTest {
    @Override
    String getCommandName() {
        return "/test_unknown_command";
    }

    @Override
    String getCommandMessage() {
        return UNKNOWN_MESSAGE;
    }

    @Override
    Command getCommand() {
        return new UnknownCommand(botMessageService);
    }
}
