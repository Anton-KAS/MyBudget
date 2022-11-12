package ru.kas.myBudget.bots.telegram.commands;

import org.junit.jupiter.api.DisplayName;
import ru.kas.myBudget.bots.telegram.util.CommandController;

@DisplayName("Unit-level testing for UnknownCommand")
public class UnknownCommandTest extends AbstractCommandTest {
    @Override
    String getCommandName() {
        return "/test_unknown_command";
    }

    @Override
    CommandController getCommand() {
        return new UnknownCommand(botMessageService, telegramUserService, messageText, keyboard);
    }
}
