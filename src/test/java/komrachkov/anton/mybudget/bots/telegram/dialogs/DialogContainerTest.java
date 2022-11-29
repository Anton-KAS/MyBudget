package komrachkov.anton.mybudget.bots.telegram.dialogs;

import komrachkov.anton.mybudget.services.AccountService;
import komrachkov.anton.mybudget.services.AccountTypeService;
import komrachkov.anton.mybudget.services.BankService;
import komrachkov.anton.mybudget.services.CurrencyService;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mockito;
import komrachkov.anton.mybudget.bots.telegram.callbacks.CallbackContainer;
import komrachkov.anton.mybudget.bots.telegram.util.AbstractContainerTest;
import komrachkov.anton.mybudget.bots.telegram.util.ExecuteMode;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

@DisplayName("Unit-level testing for DialogContainer")
public class DialogContainerTest extends AbstractContainerTest {
    private final AccountTypeService accountTypeServiceMock = Mockito.mock(AccountTypeService.class);
    private final CurrencyService currencyServiceMock = Mockito.mock(CurrencyService.class);
    private final BankService bankServiceMock = Mockito.mock(BankService.class);
    private final AccountService accountServiceMock = Mockito.mock(AccountService.class);
    private final CallbackContainer callbackContainerMock = new CallbackContainer(
            botMessageServiceMock, telegramUserServiceMock, accountServiceMock);

    @Override
    protected void setContainer() {
        container = new DialogContainer(botMessageServiceMock, telegramUserServiceMock, callbackContainerMock,
                accountTypeServiceMock, currencyServiceMock, bankServiceMock, accountServiceMock);
    }

    @Override
    protected void setNames() {
        commandNames = DialogNamesImpl.values();
    }

    @Override
    protected void setUnknownCommand() {
        unknownCommand = new UnknownDialog(botMessageServiceMock, telegramUserServiceMock, ExecuteMode.SEND,
                messageTextMock, keyboardMock);
    }
}
