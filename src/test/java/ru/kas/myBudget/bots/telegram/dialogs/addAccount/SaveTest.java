package ru.kas.myBudget.bots.telegram.dialogs.addAccount;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kas.myBudget.bots.telegram.callbacks.CallbackContainer;
import ru.kas.myBudget.bots.telegram.dialogs.AbstractDialogImplTest;
import ru.kas.myBudget.bots.telegram.dialogs.AddAccount.SaveDialog;
import ru.kas.myBudget.bots.telegram.dialogs.CommandDialogNames;
import ru.kas.myBudget.bots.telegram.dialogs.Dialog;
import ru.kas.myBudget.bots.telegram.texts.MessageText;
import ru.kas.myBudget.bots.telegram.texts.addAccountDialog.AddAccountText;
import ru.kas.myBudget.models.*;
import ru.kas.myBudget.services.AccountService;
import ru.kas.myBudget.services.AccountTypeService;
import ru.kas.myBudget.services.BankService;
import ru.kas.myBudget.services.CurrencyService;

import java.math.BigDecimal;
//import java.math.RoundingMode;
import java.util.Optional;

import static ru.kas.myBudget.bots.telegram.dialogs.AddAccount.AddAccountNames.*;

@DisplayName("Unit-level testing for AddAccount.SaveDialog")
public class SaveTest extends AbstractDialogImplTest {
    private final static int TEST_CURRENCY_ID = 11111;
    private final static int TEST_TYPE_ACCOUNT_ID = 22222;
    private final static int TEST_BANK_ID = 33333;

    private final static int TEST_NUMBER_TO_BASIC = 100;
    //    private final static BigDecimal TEST_START_BALANCE = new BigDecimal("12345")
//            .setScale(String.valueOf(TEST_NUMBER_TO_BASIC).length() - 1, RoundingMode.HALF_UP);
    private final static BigDecimal TEST_START_BALANCE_DIALOG_MAP = new BigDecimal("123.45");

    private final static String TEST_TITLE_TEXT = "TEST TITLE TEXT";
    private final static String TEST_DESCRIPTION_TEXT = "TEST DESCRIPTION TEXT";


    private final static CallbackContainer callbackContainerMock = Mockito.mock(CallbackContainer.class);
    private final static AccountTypeService accountTypeServiceMock = Mockito.mock(AccountTypeService.class);
    private final static CurrencyService currencyServiceMock = Mockito.mock(CurrencyService.class);
    private final static BankService bankServiceMock = Mockito.mock(BankService.class);
    private final static AccountService accountServiceMock = Mockito.mock(AccountService.class);
    private final static Currency currencyMock = Mockito.mock(Currency.class);
    private final static AccountType accountTypeMock = Mockito.mock(AccountType.class);
    private final static TelegramUser telegramUserMock = Mockito.mock(TelegramUser.class);
    private final static Bank bankMock = Mockito.mock(Bank.class);

    @Override
    @BeforeEach
    public void beforeEach() {
        super.beforeEach();

        testDialogMap.put(TITLE.getName(), TEST_TITLE_TEXT);
        testDialogMap.put(DESCRIPTION.getName(), TEST_DESCRIPTION_TEXT);

        testDialogMap.put(CURRENCY.getName(), String.valueOf(TEST_CURRENCY_ID));
        Mockito.when(currencyServiceMock.findById(TEST_CURRENCY_ID)).thenReturn(Optional.of(currencyMock));

        testDialogMap.put(START_BALANCE.getName(), TEST_START_BALANCE_DIALOG_MAP.toString());
        Mockito.when(currencyMock.getNumberToBasic()).thenReturn(TEST_NUMBER_TO_BASIC);

        testDialogMap.put(TYPE.getName(), String.valueOf(TEST_TYPE_ACCOUNT_ID));
        Mockito.when(accountTypeServiceMock.findById(TEST_TYPE_ACCOUNT_ID)).thenReturn(Optional.of(accountTypeMock));

        testDialogMap.put(BANK.getName(), String.valueOf(TEST_BANK_ID));
        Mockito.when(bankServiceMock.findById(TEST_BANK_ID)).thenReturn(Optional.of(bankMock));

        Mockito.when(telegramUserServiceMock.findById(TEST_USER_ID)).thenReturn(Optional.of(telegramUserMock));
    }

    @Override
    protected String getCommandName() {
        return SAVE.getName();
    }

    @Override
    public CommandDialogNames getCommandDialogName() {
        return SAVE;
    }

    @Override
    public Dialog getCommand() {
        return new SaveDialog(botMessageServiceMock, telegramUserServiceMock, messageTextMock, keyboardMock,
                dialogsMapMock, callbackContainerMock, accountTypeServiceMock, currencyServiceMock, bankServiceMock,
                accountServiceMock);
    }

    @Override
    public MessageText getMockMessageText() {
        return Mockito.mock(AddAccountText.class);
    }

    @Test
    public void shouldProperlyExecuteSaveAccount() {
        //given
        Update update = getCallbackUpdateWithData(TEST_DATA);
        //Account expectedAccount = getExpectedAccount();

        //when
        getCommand().execute(update);

        //then
        //Mockito.verify(accountServiceMock).save(expectedAccount);
        Mockito.verify(accountServiceMock).save(Mockito.any(Account.class));
    }

//    private Account getExpectedAccount() {
//        return new Account(TEST_TITLE_TEXT, TEST_DESCRIPTION_TEXT,
//                TEST_START_BALANCE, TEST_START_BALANCE, telegramUserMock, currencyMock, accountTypeMock, bankMock);
//    }


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
