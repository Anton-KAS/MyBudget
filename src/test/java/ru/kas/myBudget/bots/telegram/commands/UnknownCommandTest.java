package ru.kas.myBudget.bots.telegram.commands;

import org.junit.jupiter.api.DisplayName;
import org.mockito.Mockito;
import ru.kas.myBudget.bots.telegram.texts.MessageText;
import ru.kas.myBudget.bots.telegram.texts.UnknownText;
import ru.kas.myBudget.bots.telegram.util.AbstractCommandControllerTest;
import ru.kas.myBudget.bots.telegram.util.CommandController;

@DisplayName("Unit-level testing for UnknownCommand")
public class UnknownCommandTest extends AbstractCommandControllerTest {
    @Override
    protected String getCommandName() {
        return "/test_unknown_command";
    }

    @Override
    public CommandController getCommand() {
        return new UnknownCommand(botMessageService, telegramUserService, DEFAULT_EXECUTE_MODE, messageText, keyboard);
    }

    @Override
    public MessageText getMockMessageText() {
        return Mockito.mock(UnknownText.class);
    }
}
