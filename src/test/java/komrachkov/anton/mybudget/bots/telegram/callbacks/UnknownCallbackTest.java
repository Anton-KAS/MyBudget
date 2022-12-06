package komrachkov.anton.mybudget.bots.telegram.callbacks;

import komrachkov.anton.mybudget.bots.telegram.keyboards.NoKeyboard;
import komrachkov.anton.mybudget.bots.telegram.texts.MessageText;
import komrachkov.anton.mybudget.bots.telegram.texts.NoText;
import komrachkov.anton.mybudget.bots.telegram.util.AbstractCommandControllerTest;
import komrachkov.anton.mybudget.bots.telegram.util.CommandController;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mockito;

/**
 * Unit-tests for {@link UnknownCallback}
 * @since 0.2
 * @author Anton Komrachkov
 */

@DisplayName("Unit-level testing for callbacks.UnknownCallback")
public class UnknownCallbackTest extends AbstractCommandControllerTest {
    private final NoText noTextMock = Mockito.mock(NoText.class);
    private final NoKeyboard noKeyboardMock = Mockito.mock(NoKeyboard.class);

    @Override
    public String getCommandName() {
        return CallbackNamesImpl.NO.getName();
    }

    @Override
    public CommandController getCommand() {
        return new NoCallback(telegramUserServiceMock, noTextMock, noKeyboardMock);
    }

    @Override
    public MessageText getMockMessageText() {
        return noTextMock;
    }
}
