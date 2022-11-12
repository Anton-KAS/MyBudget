package ru.kas.myBudget.bots.telegram.commands;

import org.junit.jupiter.api.DisplayName;
import ru.kas.myBudget.bots.telegram.util.CommandController;
import ru.kas.myBudget.bots.telegram.util.ExecuteMode;

import static ru.kas.myBudget.bots.telegram.commands.CommandName.HELP;

@DisplayName("Unit-level testing for HelpCommand")
public class HelpCommandTest extends AbstractCommandTest{
    @Override
    String getCommandName() {
        return HELP.getCommandName();
    }

    @Override
    CommandController getCommand() {
        return new HelpCommand(botMessageService, telegramUserService, ExecuteMode.SEND);
    }
}
