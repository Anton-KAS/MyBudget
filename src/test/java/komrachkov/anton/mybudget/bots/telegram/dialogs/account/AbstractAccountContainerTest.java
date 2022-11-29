package komrachkov.anton.mybudget.bots.telegram.dialogs.account;

import komrachkov.anton.mybudget.bots.telegram.callbacks.CallbackContainer;
import komrachkov.anton.mybudget.bots.telegram.util.AbstractContainerTest;
import komrachkov.anton.mybudget.services.AccountService;
import komrachkov.anton.mybudget.services.AccountTypeService;
import komrachkov.anton.mybudget.services.BankService;
import komrachkov.anton.mybudget.services.CurrencyService;
import org.mockito.Mockito;
import komrachkov.anton.mybudget.bots.telegram.dialogs.UnknownDialog;
import komrachkov.anton.mybudget.bots.telegram.util.ExecuteMode;

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
