package komrachkov.anton.mybudget.bots.telegram.commands;

import komrachkov.anton.mybudget.bots.telegram.keyboards.commands.HelpKeyboard;
import komrachkov.anton.mybudget.bots.telegram.texts.MessageText;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mockito;
import komrachkov.anton.mybudget.bots.telegram.texts.commands.HelpText;
import komrachkov.anton.mybudget.bots.telegram.util.AbstractCommandControllerTest;
import komrachkov.anton.mybudget.bots.telegram.util.CommandController;

import static komrachkov.anton.mybudget.bots.telegram.commands.CommandNamesImpl.HELP;

/**
 * Unit-tests for {@link HelpCommand}
 * @since 0.2
 * @author Anton Komrachkov
 */

@DisplayName("Unit-level testing for commands.HelpCommand")
public class HelpCommandTest extends AbstractCommandControllerTest {
    private final HelpText helpTextMock = Mockito.mock(HelpText.class);
    private final HelpKeyboard helpKeyboardMock = Mockito.mock(HelpKeyboard.class);

    @Override
    protected String getCommandName() {
        return HELP.getName();
    }

    @Override
    public CommandController getCommand() {
        return new HelpCommand(telegramUserServiceMock, helpTextMock, helpKeyboardMock);
    }

    @Override
    public MessageText getMockMessageText() {
        return helpTextMock;
    }
}
