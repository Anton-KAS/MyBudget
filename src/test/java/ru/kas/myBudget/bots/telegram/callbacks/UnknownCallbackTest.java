package ru.kas.myBudget.bots.telegram.callbacks;

import org.junit.jupiter.api.DisplayName;
import org.mockito.Mockito;
import ru.kas.myBudget.bots.telegram.util.AbstractCommandControllerTest;
import ru.kas.myBudget.bots.telegram.texts.MessageText;
import ru.kas.myBudget.bots.telegram.texts.callback.NoText;
import ru.kas.myBudget.bots.telegram.util.CommandController;

@DisplayName("Unit-level testing for UnknownCallback")
public class UnknownCallbackTest extends AbstractCommandControllerTest {
    @Override
    public String getCommandName() {
        return CallbackNamesImpl.NO.getName();
    }

    @Override
    public CommandController getCommand() {
        return new NoCallback(botMessageService, telegramUserService, DEFAULT_EXECUTE_MODE, messageText, keyboard);
    }

    @Override
    public MessageText getMockMessageText() {
        return Mockito.mock(NoText.class);
    }
}
