package ru.kas.myBudget.bots.telegram.commands;

import org.junit.jupiter.api.DisplayName;
import ru.kas.myBudget.bots.telegram.util.CommandController;

import static ru.kas.myBudget.bots.telegram.commands.CommandName.STAT;

@DisplayName("Unit-level testing for StatCommand")
public class StatCommandTest extends AbstractCommandTest {
    @Override
    String getCommandName() {
        return STAT.getCommandName();
    }

    @Override
    CommandController getCommand() {
        return new StatCommand(botMessageService, telegramUserService, messageText, keyboard);
    }
}
