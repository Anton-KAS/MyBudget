package ru.kas.myBudget.bots.telegram.dialogs.account;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kas.myBudget.bots.telegram.callbacks.CallbackContainer;
import ru.kas.myBudget.bots.telegram.dialogs.util.CommandDialogNames;
import ru.kas.myBudget.bots.telegram.dialogs.util.DialogsMap;
import ru.kas.myBudget.bots.telegram.texts.MessageText;
import ru.kas.myBudget.bots.telegram.texts.accountDialog.AccountText;
import ru.kas.myBudget.models.*;
import ru.kas.myBudget.services.AccountService;
import ru.kas.myBudget.services.AccountTypeService;
import ru.kas.myBudget.services.BankService;
import ru.kas.myBudget.services.CurrencyService;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import static ru.kas.myBudget.bots.telegram.dialogs.account.AccountNames.*;
import static ru.kas.myBudget.bots.telegram.dialogs.util.DialogMapDefaultName.EDIT_ID;

/**
 * @since 0.2
 * @author Anton Komrachkov
 */

@DisplayName("Unit-level testing for account.SaveDialog")
public abstract class SaveDialogTest extends AbstractAccountDialogTest {
    public final static int TEST_NUMBER_TO_BASIC = 100;
    public final static Date TEST_DATE = new Date();
    public final static BigDecimal TEST_START_BALANCE = new BigDecimal("123.45");
    public final static BigDecimal TEST_START_BALANCE_DIALOG_MAP = new BigDecimal("12345");

    public final static String TEST_TITLE_TEXT = "TEST TITLE TEXT";
    public final static String TEST_DESCRIPTION_TEXT = "TEST DESCRIPTION TEXT";


    public final static CallbackContainer callbackContainerMock = Mockito.mock(CallbackContainer.class);
    public final static AccountTypeService accountTypeServiceMock = Mockito.mock(AccountTypeService.class);
    public final static CurrencyService currencyServiceMock = Mockito.mock(CurrencyService.class);
    public final static BankService bankServiceMock = Mockito.mock(BankService.class);
    public final static AccountService accountServiceMock = Mockito.mock(AccountService.class);
    public final static Currency currencyMock = Mockito.mock(Currency.class);
    public final static AccountType accountTypeMock = Mockito.mock(AccountType.class);
    public final static TelegramUser telegramUserMock = Mockito.mock(TelegramUser.class);
    public final static Bank bankMock = Mockito.mock(Bank.class);

    @Override
    protected String getCommandName() {
        return SAVE.getName();
    }

    @Override
    protected CommandDialogNames getCommandDialogName() {
        return SAVE;
    }

    protected abstract Account getExpectedAccount();

    protected abstract Account getTestAccount();

    @Override
    @BeforeEach
    public void beforeEach() {
        super.beforeEach();

        DialogsMap.remove(TEST_CHAT_ID);
        DialogsMap.put(TEST_CHAT_ID, TITLE.getName(), TEST_TITLE_TEXT);
        DialogsMap.put(TEST_CHAT_ID, DESCRIPTION.getName(), TEST_DESCRIPTION_TEXT);
        DialogsMap.put(TEST_CHAT_ID, CURRENCY.getName(), String.valueOf(TEST_CURRENCY_ID));
        DialogsMap.put(TEST_CHAT_ID, TYPE.getName(), String.valueOf(TEST_TYPE_ACCOUNT_ID));
        DialogsMap.put(TEST_CHAT_ID, START_BALANCE.getName(), String.valueOf(TEST_START_BALANCE));
        DialogsMap.put(TEST_CHAT_ID, BANK.getName(), String.valueOf(TEST_BANK_ID));
        DialogsMap.put(TEST_CHAT_ID, EDIT_ID.getId(), String.valueOf(TEST_ACCOUNT_ID));

        Mockito.when(currencyServiceMock.findById(TEST_CURRENCY_ID)).thenReturn(Optional.of(currencyMock));
        Mockito.when(currencyMock.getNumberToBasic()).thenReturn(TEST_NUMBER_TO_BASIC);
        Mockito.when(accountTypeServiceMock.findById(TEST_TYPE_ACCOUNT_ID)).thenReturn(Optional.of(accountTypeMock));
        Mockito.when(bankServiceMock.findById(TEST_BANK_ID)).thenReturn(Optional.of(bankMock));

        Mockito.when(telegramUserServiceMock.findById(TEST_USER_ID)).thenReturn(Optional.of(telegramUserMock));
    }

    @Override
    public MessageText getMockMessageText() {
        return Mockito.mock(AccountText.class);
    }

    @Test
    public void shouldProperlyExecuteSaveAccount() {
        //given
        Update update = getCallbackUpdateWithData(TEST_DATA);
        Account expectedAccount = getExpectedAccount();

        //when
        getCommand().execute(update);

        //then
        Mockito.verify(accountServiceMock).save(expectedAccount);
    }

    @Override
    public void shouldProperlyExecuteSetUserId() {
    }

    @Override
    public void shouldProperlyExecuteSetUserIdExecuteMode() {
    }

    @Override
    public void shouldProperlyExecuteGetText() {
    }

    @Override
    public void shouldProperlyExecuteGetTextExecuteMode() {
    }

    @Override
    public void shouldProperlyExecuteAndUpdateUser() {
    }

    @Override
    public void shouldProperlyExecuteAndUpdateUserExecuteMode() {
    }
}
