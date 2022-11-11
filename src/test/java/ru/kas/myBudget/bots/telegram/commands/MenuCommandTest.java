package ru.kas.myBudget.bots.telegram.commands;

import org.junit.jupiter.api.DisplayName;
import ru.kas.myBudget.bots.telegram.util.ExecuteMode;

import static ru.kas.myBudget.bots.telegram.commands.CommandName.MENU;

@DisplayName("Unit-level testing for MenuCommand")
public class MenuCommandTest extends AbstractCommandTest {
    @Override
    String getCommandName() {
        return MENU.getCommandName();
    }

    @Override
    String getCommandMessage() {
        return messageText.getText();
    }

    @Override
    Command getCommand() {
        return new MenuCommand(ExecuteMode.SEND, botMessageService, telegramUserService, keyboard, messageText);
    }
}
