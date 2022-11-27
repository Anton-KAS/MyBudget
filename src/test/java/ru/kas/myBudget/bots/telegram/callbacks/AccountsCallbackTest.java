package ru.kas.myBudget.bots.telegram.callbacks;

import org.junit.jupiter.api.DisplayName;
import org.mockito.Mockito;
import ru.kas.myBudget.bots.telegram.util.AbstractCommandControllerTest;
import ru.kas.myBudget.bots.telegram.texts.callback.AccountsText;
import ru.kas.myBudget.bots.telegram.texts.MessageText;
import ru.kas.myBudget.bots.telegram.util.CommandController;

import static ru.kas.myBudget.bots.telegram.commands.CommandNamesImpl.ACCOUNTS;

/**
 * @since 0.2
 * @author Anton Komrachkov
 */

@DisplayName("Unit-level testing for AccountsCallback")
public class AccountsCallbackTest extends AbstractCommandControllerTest {
    @Override
    protected String getCommandName() {
        return ACCOUNTS.getName();
    }

    @Override
    public CommandController getCommand() {
        return new AccountsCallback(botMessageServiceMock, telegramUserServiceMock, DEFAULT_EXECUTE_MODE, messageTextMock, keyboardMock);
    }

    @Override
    public MessageText getMockMessageText() {
        return Mockito.mock(AccountsText.class);
    }
}
