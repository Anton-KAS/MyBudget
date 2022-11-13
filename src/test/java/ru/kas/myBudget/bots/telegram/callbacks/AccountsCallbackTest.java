package ru.kas.myBudget.bots.telegram.callbacks;

import org.junit.jupiter.api.DisplayName;
import org.mockito.Mockito;
import ru.kas.myBudget.bots.telegram.util.AbstractCommandControllerTest;
import ru.kas.myBudget.bots.telegram.texts.AccountsText;
import ru.kas.myBudget.bots.telegram.texts.MessageText;
import ru.kas.myBudget.bots.telegram.util.CommandController;

import static ru.kas.myBudget.bots.telegram.commands.CommandName.ACCOUNTS;

@DisplayName("Unit-level testing for AccountsCallback")
public class AccountsCallbackTest extends AbstractCommandControllerTest {
    @Override
    protected String getCommandName() {
        return ACCOUNTS.getCommandName();
    }

    @Override
    public CommandController getCommand() {
        return new AccountsCallback(botMessageService, telegramUserService, DEFAULT_EXECUTE_MODE, messageText, keyboard);
    }

    @Override
    public MessageText getMockMessageText() {
        return Mockito.mock(AccountsText.class);
    }
}
