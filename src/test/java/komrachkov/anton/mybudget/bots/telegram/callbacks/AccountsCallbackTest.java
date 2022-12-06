package komrachkov.anton.mybudget.bots.telegram.callbacks;

import komrachkov.anton.mybudget.bots.telegram.commands.CommandNamesImpl;
import komrachkov.anton.mybudget.bots.telegram.keyboards.callback.AccountsKeyboard;
import komrachkov.anton.mybudget.bots.telegram.texts.MessageText;
import komrachkov.anton.mybudget.bots.telegram.texts.callback.AccountsText;
import komrachkov.anton.mybudget.bots.telegram.util.AbstractCommandControllerTest;
import komrachkov.anton.mybudget.bots.telegram.util.CommandController;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mockito;

/**
 * Unit-tests for {@link AccountsCallback}
 * @since 0.2
 * @author Anton Komrachkov
 */

@DisplayName("Unit-level testing for callbacks.AccountsCallback")
public class AccountsCallbackTest extends AbstractCommandControllerTest {
    private final AccountsText accountsTextMock = Mockito.mock(AccountsText.class);
    private final AccountsKeyboard accountsKeyboardMock = Mockito.mock(AccountsKeyboard.class);

    @Override
    protected String getCommandName() {
        return CommandNamesImpl.ACCOUNTS.getName();
    }

    @Override
    public CommandController getCommand() {
        return new AccountsCallback(telegramUserServiceMock, accountsTextMock, accountsKeyboardMock);
    }

    @Override
    public MessageText getMockMessageText() {
        return accountsTextMock;
    }
}
