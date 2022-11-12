package ru.kas.myBudget.bots.telegram.commands;

import org.junit.jupiter.api.DisplayName;
import ru.kas.myBudget.bots.telegram.util.CommandController;
import ru.kas.myBudget.bots.telegram.util.ExecuteMode;

import static ru.kas.myBudget.bots.telegram.commands.CommandName.MENU;

@DisplayName("Unit-level testing for MenuCommand")
public class MenuCommandTest extends AbstractCommandTest {
    @Override
    String getCommandName() {
        return MENU.getCommandName();
    }

    @Override
    CommandController getCommand() {
        return new MenuCommand(botMessageService, telegramUserService, ExecuteMode.SEND, keyboard, messageText);
    }
}
