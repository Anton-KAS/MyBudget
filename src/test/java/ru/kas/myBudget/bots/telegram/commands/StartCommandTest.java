package ru.kas.myBudget.bots.telegram.commands;

import org.junit.jupiter.api.DisplayName;

import static ru.kas.myBudget.bots.telegram.commands.CommandName.START;
import static ru.kas.myBudget.bots.telegram.commands.StartCommand.START_MESSAGE;

@DisplayName("Unit-level testing for StartCommand")
public class StartCommandTest extends AbstractCommandTest {
    @Override
    String getCommandName() {
        return START.getCommandName();
    }

    @Override
    String getCommandMessage() {
        return START_MESSAGE;
    }

    @Override
    Command getCommand() {
        return new StartCommand(botMessageService, telegramUserService);
    }
}
