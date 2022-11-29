package komrachkov.anton.mybudget.bots.telegram.commands;

import komrachkov.anton.mybudget.bots.telegram.texts.MessageText;
import komrachkov.anton.mybudget.bots.telegram.texts.commands.StartText;
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
    @Override
    protected String getCommandName() {
        return START.getName();
    }

    @Override
    public CommandController getCommand() {
        return new StartCommand(botMessageServiceMock, telegramUserServiceMock, DEFAULT_EXECUTE_MODE, messageTextMock, keyboardMock);
    }

    @Override
    public MessageText getMockMessageText() {
        return Mockito.mock(StartText.class);
    }
}
