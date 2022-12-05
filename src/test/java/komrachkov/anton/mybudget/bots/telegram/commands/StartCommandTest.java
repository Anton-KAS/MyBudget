package komrachkov.anton.mybudget.bots.telegram.commands;

import komrachkov.anton.mybudget.bots.telegram.keyboards.commands.StartKeyboard;
import komrachkov.anton.mybudget.bots.telegram.keyboards.commands.StatKeyboard;
import komrachkov.anton.mybudget.bots.telegram.texts.MessageText;
import komrachkov.anton.mybudget.bots.telegram.texts.commands.StartText;
import komrachkov.anton.mybudget.bots.telegram.texts.commands.StatText;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mockito;
import komrachkov.anton.mybudget.bots.telegram.util.AbstractCommandControllerTest;
import komrachkov.anton.mybudget.bots.telegram.util.CommandController;

import static komrachkov.anton.mybudget.bots.telegram.commands.CommandNamesImpl.START;

/**
 * @since 0.2
 * @author Anton Komrachkov
 */

@DisplayName("Unit-level testing for StartCommand")
public class StartCommandTest extends AbstractCommandControllerTest {
    private final static StartText startTextMock = Mockito.mock(StartText.class);
    private final static StartKeyboard startKeyboardMock = Mockito.mock(StartKeyboard.class);

    @Override
    protected String getCommandName() {
        return START.getName();
    }

    @Override
    public CommandController getCommand() {
        return new StartCommand(telegramUserServiceMock, startTextMock, startKeyboardMock);
    }

    @Override
    public MessageText getMockMessageText() {
        return Mockito.mock(StartText.class);
    }
}
