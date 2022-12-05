package komrachkov.anton.mybudget.bots.telegram.commands;

import komrachkov.anton.mybudget.bots.telegram.keyboards.UnknownKeyboard;
import komrachkov.anton.mybudget.bots.telegram.keyboards.commands.StopKeyboard;
import komrachkov.anton.mybudget.bots.telegram.texts.MessageText;
import komrachkov.anton.mybudget.bots.telegram.texts.commands.StopText;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mockito;
import komrachkov.anton.mybudget.bots.telegram.texts.UnknownText;
import komrachkov.anton.mybudget.bots.telegram.util.AbstractCommandControllerTest;
import komrachkov.anton.mybudget.bots.telegram.util.CommandController;

/**
 * @since 0.2
 * @author Anton Komrachkov
 */

@DisplayName("Unit-level testing for UnknownCommand")
public class UnknownCommandTest extends AbstractCommandControllerTest {
    private final static UnknownText unknownTextMock = Mockito.mock(UnknownText.class);
    private final static UnknownKeyboard unknownKeyboardMock = Mockito.mock(UnknownKeyboard.class);
    @Override
    protected String getCommandName() {
        return "/test_unknown_command";
    }

    @Override
    public CommandController getCommand() {
        return new UnknownCommand(telegramUserServiceMock, unknownTextMock, unknownKeyboardMock);
    }

    @Override
    public MessageText getMockMessageText() {
        return Mockito.mock(UnknownText.class);
    }
}
