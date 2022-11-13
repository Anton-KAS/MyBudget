package ru.kas.myBudget.bots.telegram.commands;

import org.junit.jupiter.api.DisplayName;
import org.mockito.Mockito;
import ru.kas.myBudget.bots.telegram.texts.MenuText;
import ru.kas.myBudget.bots.telegram.texts.MessageText;
import ru.kas.myBudget.bots.telegram.util.AbstractCommandControllerTest;
import ru.kas.myBudget.bots.telegram.util.CommandController;

import static ru.kas.myBudget.bots.telegram.commands.CommandName.MENU;

@DisplayName("Unit-level testing for MenuCommand")
public class MenuCommandTest extends AbstractCommandControllerTest {
    @Override
    protected String getCommandName() {
        return MENU.getCommandName();
    }

    @Override
    public CommandController getCommand() {
        return new MenuCommand(botMessageService, telegramUserService, DEFAULT_EXECUTE_MODE, messageText, keyboard);
    }

    @Override
    public MessageText getMockMessageText() {
        return Mockito.mock(MenuText.class);
    }
}
