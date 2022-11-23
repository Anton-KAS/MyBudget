package ru.kas.myBudget.bots.telegram.dialogs.addAccount;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kas.myBudget.bots.telegram.dialogs.*;
import ru.kas.myBudget.bots.telegram.dialogs.account.BankDialog;
import ru.kas.myBudget.bots.telegram.dialogs.util.CommandDialogNames;
import ru.kas.myBudget.bots.telegram.dialogs.util.Dialog;
import ru.kas.myBudget.bots.telegram.keyboards.AccountDialog.BanksKeyboard;
import ru.kas.myBudget.bots.telegram.texts.MessageText;
import ru.kas.myBudget.bots.telegram.texts.accountDialog.AccountText;
import ru.kas.myBudget.models.Bank;
import ru.kas.myBudget.models.Country;
import ru.kas.myBudget.services.BankService;

import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.kas.myBudget.bots.telegram.callbacks.util.CallbackType.DIALOG;
import static ru.kas.myBudget.bots.telegram.dialogs.account.AccountNames.BANK;
import static ru.kas.myBudget.bots.telegram.dialogs.DialogNamesImpl.ADD_ACCOUNT;


@DisplayName("Unit-level testing for AddAccount.BankDialog")
public class BankDialogTest extends AbstractDialogImplTest {
    private final static int TEST_EXISTENT_BANK_ID = 777;
    private final static int TEST_NONEXISTENT_BANK_ID = 666;
    private final static String CALLBACK_DATA_PATTERN = "%s_%s_%s_%s_%s";
    private final static String TEST_DATA_WITH_EXISTENT_BANK_ID = String.format(CALLBACK_DATA_PATTERN,
            DIALOG.getId(), ADD_ACCOUNT.getName(), ADD_ACCOUNT.getName(), BANK.getName(), TEST_EXISTENT_BANK_ID);
    private final static String TEST_DATA_WITH_NONEXISTENT_BANK_ID = String.format(CALLBACK_DATA_PATTERN,
            DIALOG.getId(), ADD_ACCOUNT.getName(), ADD_ACCOUNT.getName(), BANK.getName(), TEST_NONEXISTENT_BANK_ID);
    protected BanksKeyboard keyboardBankMock = Mockito.mock(BanksKeyboard.class);
    private final static Bank bankMock = Mockito.mock(Bank.class);
    private final static Country countryMock = Mockito.mock(Country.class);
    protected final BankService bankServiceMock = Mockito.mock(BankService.class);


    @Override
    @BeforeEach
    public void beforeEach() {
        Mockito.when(bankMock.getCountry()).thenReturn(countryMock);
        super.beforeEach();
    }

    @Override
    protected String getCommandName() {
        return BANK.getName();
    }

    @Override
    public CommandDialogNames getCommandDialogName() {
        return BANK;
    }

    @Override
    public Dialog getCommand() {
        return new BankDialog(botMessageServiceMock, telegramUserServiceMock, messageTextMock, keyboardBankMock, bankServiceMock);
    }

    @Override
    public MessageText getMockMessageText() {
        return Mockito.mock(AccountText.class);
    }

    @Override
    public void shouldReturnTrueByExecuteCommit(Update update) {
    }

    @ParameterizedTest
    @MethodSource("sourceBankCommit")
    public void shouldProperlyExecuteCommit(Update update, int testBankId, Optional<Bank> returnFindById, boolean expected) {
        //given
        Mockito.when(bankServiceMock.findById(testBankId)).thenReturn(returnFindById);
        int times = expected ? 1 : 0;

        //when
        boolean result = getCommand().commit(update);

        //then
        assertEquals(expected, result);
        Mockito.verify(telegramUserServiceMock, Mockito.times(times)).checkUser(telegramUserServiceMock, update);
    }

    public static Stream<Arguments> sourceBankCommit() {
        return Stream.of(
                Arguments.of(getUpdateWithText(TEST_TEXT),
                        TEST_NONEXISTENT_BANK_ID, Optional.empty(), false),
                Arguments.of(getCallbackUpdateWithData(TEST_DATA),
                        TEST_NONEXISTENT_BANK_ID, Optional.empty(), false),
                Arguments.of(getCallbackUpdateWithData(TEST_DATA_WITH_NONEXISTENT_BANK_ID),
                        TEST_NONEXISTENT_BANK_ID, Optional.empty(), false),
                Arguments.of(getCallbackUpdateWithData(TEST_DATA_WITH_EXISTENT_BANK_ID),
                        TEST_EXISTENT_BANK_ID, Optional.of(bankMock), true)
        );
    }
}
