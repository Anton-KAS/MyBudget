package ru.kas.myBudget.bots.telegram.commands;

import org.junit.jupiter.api.DisplayName;
import org.mockito.Mockito;
import ru.kas.myBudget.bots.telegram.texts.commands.MenuText;
import ru.kas.myBudget.bots.telegram.texts.MessageText;
import ru.kas.myBudget.bots.telegram.util.AbstractCommandControllerTest;
import ru.kas.myBudget.bots.telegram.util.CommandController;

import static ru.kas.myBudget.bots.telegram.commands.CommandNamesImpl.MENU;

/**
 * @since 0.2
 * @author Anton Komrachkov
 */

@DisplayName("Unit-level testing for MenuCommand")
public class MenuCommandTest extends AbstractCommandControllerTest {
    @Override
    protected String getCommandName() {
        return MENU.getName();
    }

    @Override
    public CommandController getCommand() {
        return new MenuCommand(botMessageServiceMock, telegramUserServiceMock, DEFAULT_EXECUTE_MODE, messageTextMock, keyboardMock);
    }

    @Override
    public MessageText getMockMessageText() {
        return Mockito.mock(MenuText.class);
    }
}
