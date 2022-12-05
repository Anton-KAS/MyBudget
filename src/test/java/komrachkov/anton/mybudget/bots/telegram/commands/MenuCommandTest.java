package komrachkov.anton.mybudget.bots.telegram.commands;

import komrachkov.anton.mybudget.bots.telegram.keyboards.commands.MenuKeyboard;
import komrachkov.anton.mybudget.bots.telegram.texts.MessageText;
import komrachkov.anton.mybudget.bots.telegram.texts.commands.MenuText;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mockito;
import komrachkov.anton.mybudget.bots.telegram.util.AbstractCommandControllerTest;
import komrachkov.anton.mybudget.bots.telegram.util.CommandController;

import static komrachkov.anton.mybudget.bots.telegram.commands.CommandNamesImpl.MENU;

/**
 * @since 0.2
 * @author Anton Komrachkov
 */

@DisplayName("Unit-level testing for MenuCommand")
public class MenuCommandTest extends AbstractCommandControllerTest {
    private final static MenuText menuTextMock = Mockito.mock(MenuText.class);
    private final static MenuKeyboard menuKeyboardMock = Mockito.mock(MenuKeyboard.class);

    @Override
    protected String getCommandName() {
        return MENU.getName();
    }

    @Override
    public CommandController getCommand() {
        return new MenuCommand(telegramUserServiceMock, menuTextMock, menuKeyboardMock);
    }

    @Override
    public MessageText getMockMessageText() {
        return Mockito.mock(MenuText.class);
    }
}
