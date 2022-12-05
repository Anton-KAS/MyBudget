package komrachkov.anton.mybudget.bots.telegram.commands;

import komrachkov.anton.mybudget.bots.telegram.keyboards.NoKeyboard;
import komrachkov.anton.mybudget.bots.telegram.texts.MessageText;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mockito;
import komrachkov.anton.mybudget.bots.telegram.texts.NoText;
import komrachkov.anton.mybudget.bots.telegram.util.AbstractCommandControllerTest;
import komrachkov.anton.mybudget.bots.telegram.util.CommandController;

import static komrachkov.anton.mybudget.bots.telegram.commands.CommandNamesImpl.NO;

/**
 * @since 0.2
 * @author Anton Komrachkov
 */

@DisplayName("Unit-level testing for NoCommand")
public class NoCommandTest extends AbstractCommandControllerTest {
    private final static NoText noTextMock = Mockito.mock(NoText.class);
    private final static NoKeyboard noKeyboardMock = Mockito.mock(NoKeyboard.class);

    @Override
    protected String getCommandName() {
        return NO.getName();
    }

    @Override
    public CommandController getCommand() {
        return new NoCommand(telegramUserServiceMock, noTextMock, noKeyboardMock);
    }

    @Override
    public MessageText getMockMessageText() {
        return Mockito.mock(NoText.class);
    }
}
