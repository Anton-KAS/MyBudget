package komrachkov.anton.mybudget.bots.telegram.commands;

import komrachkov.anton.mybudget.bots.telegram.keyboards.UnknownKeyboard;
import komrachkov.anton.mybudget.bots.telegram.texts.MessageText;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mockito;
import komrachkov.anton.mybudget.bots.telegram.texts.UnknownText;
import komrachkov.anton.mybudget.bots.telegram.util.AbstractCommandControllerTest;
import komrachkov.anton.mybudget.bots.telegram.util.CommandController;

/**
 * Unit-tests for {@link UnknownCommand}
 * @since 0.2
 * @author Anton Komrachkov
 */

@DisplayName("Unit-level testing for commands.UnknownCommand")
public class UnknownCommandTest extends AbstractCommandControllerTest {
    private final UnknownText unknownTextMock = Mockito.mock(UnknownText.class);
    private final UnknownKeyboard unknownKeyboardMock = Mockito.mock(UnknownKeyboard.class);
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
        return unknownTextMock;
    }
}
