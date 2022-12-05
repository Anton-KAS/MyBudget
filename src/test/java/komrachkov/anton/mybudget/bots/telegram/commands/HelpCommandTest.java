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
 * @since 0.2
 * @author Anton Komrachkov
 */

@DisplayName("Unit-level testing for HelpCommand")
public class HelpCommandTest extends AbstractCommandControllerTest {
    private final static HelpText helpTextMock = Mockito.mock(HelpText.class);
    private final static HelpKeyboard helpKeyboardMock = Mockito.mock(HelpKeyboard.class);

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
        return Mockito.mock(HelpText.class);
    }
}
