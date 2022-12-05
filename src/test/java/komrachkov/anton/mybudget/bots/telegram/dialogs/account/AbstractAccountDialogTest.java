package komrachkov.anton.mybudget.bots.telegram.dialogs.account;

import komrachkov.anton.mybudget.bots.telegram.texts.callback.AccountText;
import komrachkov.anton.mybudget.bots.telegram.texts.dialogs.account.AccountDialogText;
import komrachkov.anton.mybudget.bots.telegram.util.CommandNames;
import komrachkov.anton.mybudget.models.*;
import komrachkov.anton.mybudget.services.AccountService;
import komrachkov.anton.mybudget.services.AccountTypeService;
import komrachkov.anton.mybudget.services.BankService;
import komrachkov.anton.mybudget.services.CurrencyService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import komrachkov.anton.mybudget.bots.telegram.dialogs.AbstractDialogImplTest;

import java.math.BigDecimal;
import java.util.Optional;

import static komrachkov.anton.mybudget.bots.telegram.callbacks.CallbackNamesImpl.ACCOUNT;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

public abstract class AbstractAccountDialogTest extends AbstractDialogImplTest {
    protected final static int TEST_CURRENCY_ID = 11111;
    protected final static int TEST_TYPE_ACCOUNT_ID = 22222;
    protected final static int TEST_BANK_ID = 33333;
    protected final static int TEST_ACCOUNT_ID = 44444;

    public final static int TEST_NUMBER_TO_BASIC = 100;
    public final static BigDecimal TEST_START_BALANCE = new BigDecimal("123.45");
    public final static BigDecimal TEST_START_BALANCE_BASIC = new BigDecimal("12345");

    public final static String TEST_TITLE_TEXT = "TEST TITLE TEXT";
    public final static String TEST_DESCRIPTION_TEXT = "TEST DESCRIPTION TEXT";

    public static final CommandNames TEST_COMMAND_NAME = ACCOUNT;

    protected static final AccountService accountServiceMock = Mockito.mock(AccountService.class);
    protected final static AccountTypeService accountTypeServiceMock = Mockito.mock(AccountTypeService.class);
    protected final static CurrencyService currencyServiceMock = Mockito.mock(CurrencyService.class);
    protected final static BankService bankServiceMock = Mockito.mock(BankService.class);

    protected final static Currency currencyMock = Mockito.mock(Currency.class);
    protected final static AccountType accountTypeMock = Mockito.mock(AccountType.class);
    protected final static TelegramUser telegramUserMock = Mockito.mock(TelegramUser.class);
    protected final static Bank bankMock = Mockito.mock(Bank.class);
    protected final static Account accountMock = Mockito.mock(Account.class);
    protected final static Country countryMock = Mockito.mock(Country.class);

    protected final static AccountDialogText accountTextMock = Mockito.mock(AccountDialogText.class);

    @Override
    @BeforeEach
    public void beforeEach() {
        super.beforeEach();

        Mockito.when(telegramUserServiceMock.findById(TEST_USER_ID)).thenReturn(Optional.of(telegramUserMock));

        Mockito.when(currencyServiceMock.findById(TEST_CURRENCY_ID)).thenReturn(Optional.of(currencyMock));
        Mockito.when(currencyMock.getNumberToBasic()).thenReturn(TEST_NUMBER_TO_BASIC);
        Mockito.when(accountTypeServiceMock.findById(TEST_TYPE_ACCOUNT_ID)).thenReturn(Optional.of(accountTypeMock));
        Mockito.when(bankServiceMock.findById(TEST_BANK_ID)).thenReturn(Optional.of(bankMock));
        Mockito.when(accountServiceMock.findById(TEST_ACCOUNT_ID)).thenReturn(Optional.of(accountMock));

//        Использую в START editAccount:
//        Mockito.when(accountMock.getAccountType()).thenReturn(accountTypeMock);
//        Mockito.when(accountMock.getCurrency()).thenReturn(currencyMock);
    }
}
