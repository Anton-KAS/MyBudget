package ru.kas.myBudget.bots.telegram.dialogs.addAccount;

import org.junit.jupiter.api.DisplayName;
import org.mockito.Mockito;
import ru.kas.myBudget.bots.telegram.callbacks.CallbackContainer;
import ru.kas.myBudget.bots.telegram.dialogs.UnknownDialog;
import ru.kas.myBudget.bots.telegram.dialogs.account.addAccount.AddAccountContainer;
import ru.kas.myBudget.bots.telegram.dialogs.account.AccountNames;
import ru.kas.myBudget.bots.telegram.util.AbstractContainerTest;
import ru.kas.myBudget.bots.telegram.util.ExecuteMode;
import ru.kas.myBudget.services.AccountService;
import ru.kas.myBudget.services.AccountTypeService;
import ru.kas.myBudget.services.BankService;
import ru.kas.myBudget.services.CurrencyService;

/**
 * @since 0.2
 * @author Anton Komrachkov
 */

@DisplayName("Unit-level testing for AddAccountContainer")
public class AddAccountContainerTest extends AbstractContainerTest {
    private final AccountTypeService accountTypeServiceMock = Mockito.mock(AccountTypeService.class);
    private final CurrencyService currencyServiceMock = Mockito.mock(CurrencyService.class);
    private final BankService bankServiceMock = Mockito.mock(BankService.class);
    private final AccountService accountServiceMock = Mockito.mock(AccountService.class);
    private final CallbackContainer callbackContainerMock = new CallbackContainer(
            botMessageServiceMock, telegramUserServiceMock, accountServiceMock);

    @Override
    protected void setContainer() {
        container = new AddAccountContainer(botMessageServiceMock, telegramUserServiceMock, callbackContainerMock,
                accountTypeServiceMock, currencyServiceMock, bankServiceMock, accountServiceMock);
    }

    @Override
    protected void setNames() {
        commandNames = AccountNames.values();
    }

    @Override
    protected void setUnknownCommand() {
        unknownCommand = new UnknownDialog(botMessageServiceMock, telegramUserServiceMock, ExecuteMode.SEND,
                messageTextMock, keyboardMock);
    }
}
