package ru.kas.myBudget.bots.telegram.commands;

import org.junit.jupiter.api.DisplayName;

import static ru.kas.myBudget.bots.telegram.commands.CommandName.STAT;
import static ru.kas.myBudget.bots.telegram.commands.StatCommand.STAT_MESSAGE;

@DisplayName("Unit-level testing for StartCommand")
public class StatCommandTest extends AbstractCommandTest {
    @Override
    String getCommandName() {
        return STAT.getCommandName();
    }

    @Override
    String getCommandMessage() {
        return String.format(STAT_MESSAGE, 0);
    }

    @Override
    Command getCommand() {
        return new StatCommand(botMessageService, telegramUserService);
    }
}
