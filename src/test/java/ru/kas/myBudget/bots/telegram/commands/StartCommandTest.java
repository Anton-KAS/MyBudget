package ru.kas.myBudget.bots.telegram.commands;

import org.junit.jupiter.api.DisplayName;
import org.mockito.Mockito;
import ru.kas.myBudget.bots.telegram.texts.MessageText;
import ru.kas.myBudget.bots.telegram.texts.StartText;
import ru.kas.myBudget.bots.telegram.util.AbstractCommandControllerTest;
import ru.kas.myBudget.bots.telegram.util.CommandController;

import static ru.kas.myBudget.bots.telegram.commands.CommandNamesImpl.START;

@DisplayName("Unit-level testing for StartCommand")
public class StartCommandTest extends AbstractCommandControllerTest {
    @Override
    protected String getCommandName() {
        return START.getName();
    }

    @Override
    public CommandController getCommand() {
        return new StartCommand(botMessageService, telegramUserService, DEFAULT_EXECUTE_MODE, messageText, keyboard);
    }

    @Override
    public MessageText getMockMessageText() {
        return Mockito.mock(StartText.class);
    }
}
