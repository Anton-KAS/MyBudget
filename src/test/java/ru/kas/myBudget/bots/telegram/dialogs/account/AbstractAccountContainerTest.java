package ru.kas.myBudget.bots.telegram.dialogs.account;

import org.mockito.Mockito;
import ru.kas.myBudget.bots.telegram.callbacks.CallbackContainer;
import ru.kas.myBudget.bots.telegram.dialogs.UnknownDialog;
import ru.kas.myBudget.bots.telegram.util.AbstractContainerTest;
import ru.kas.myBudget.bots.telegram.util.ExecuteMode;
import ru.kas.myBudget.services.AccountService;
import ru.kas.myBudget.services.AccountTypeService;
import ru.kas.myBudget.services.BankService;
import ru.kas.myBudget.services.CurrencyService;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

public abstract class AbstractAccountContainerTest extends AbstractContainerTest {
    protected final static AccountTypeService accountTypeServiceMock = Mockito.mock(AccountTypeService.class);
    protected final static CurrencyService currencyServiceMock = Mockito.mock(CurrencyService.class);
    protected final static BankService bankServiceMock = Mockito.mock(BankService.class);
    protected final static AccountService accountServiceMock = Mockito.mock(AccountService.class);
    protected final CallbackContainer callbackContainerMock = new CallbackContainer(
            botMessageServiceMock, telegramUserServiceMock, accountServiceMock);

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
