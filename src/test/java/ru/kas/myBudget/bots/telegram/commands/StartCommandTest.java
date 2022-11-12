package ru.kas.myBudget.bots.telegram.commands;

import org.junit.jupiter.api.DisplayName;
import ru.kas.myBudget.bots.telegram.util.CommandController;

import static ru.kas.myBudget.bots.telegram.commands.CommandName.START;

@DisplayName("Unit-level testing for StartCommand")
public class StartCommandTest extends AbstractCommandTest {
    @Override
    String getCommandName() {
        return START.getCommandName();
    }

    @Override
    CommandController getCommand() {
        return new StartCommand(botMessageService, telegramUserService);
    }
}
