package ru.kas.myBudget.bots.telegram.commands;

import org.junit.jupiter.api.DisplayName;

import static ru.kas.myBudget.bots.telegram.commands.CommandName.HELP;
import static ru.kas.myBudget.bots.telegram.commands.HelpCommand.HELP_MESSAGE;

@DisplayName("Unit-level testing for HelpCommand")
public class HelpCommandTest extends AbstractCommandTest{
    @Override
    String getCommandName() {
        return HELP.getCommandName();
    }

    @Override
    String getCommandMessage() {
        return HELP_MESSAGE;
    }

    @Override
    Command getCommand() {
        return new HelpCommand(sendBotMessageService, telegramUserService);
    }
}
