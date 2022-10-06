package ru.kas.myBudget.bots.telegram.commands;

import org.junit.jupiter.api.DisplayName;

import static ru.kas.myBudget.bots.telegram.commands.CommandName.STOP;
import static ru.kas.myBudget.bots.telegram.commands.StopCommand.STOP_MESSAGE;

@DisplayName("Unit-level testing for StopCommand")
public class StopCommandTest extends AbstractCommandTest {
    @Override
    String getCommandName() {
        return STOP.getCommandName();
    }

    @Override
    String getCommandMessage() {
        return STOP_MESSAGE;
    }

    @Override
    Command getCommand() {
        return new StopCommand(sendBotMessageService);
    }
}
