package komrachkov.anton.mybudget.bots.telegram.dialogs.account;

import komrachkov.anton.mybudget.bots.telegram.callbacks.util.CallbackType;
import komrachkov.anton.mybudget.bots.telegram.dialogs.DialogNamesImpl;
import komrachkov.anton.mybudget.bots.telegram.dialogs.util.CommandDialogNames;
import komrachkov.anton.mybudget.bots.telegram.dialogs.util.Dialog;
import komrachkov.anton.mybudget.bots.telegram.keyboards.dialogs.account.BanksKeyboard;
import komrachkov.anton.mybudget.bots.telegram.texts.MessageText;
import komrachkov.anton.mybudget.bots.telegram.texts.dialogs.account.AccountDialogText;
import komrachkov.anton.mybudget.models.Bank;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

@DisplayName("Unit-level testing for account.BankDialog")
public class BankDialogTest extends AbstractAccountDialogTest {
    private final static int TEST_EXISTENT_BANK_ID = 777;
    private final static int TEST_NONEXISTENT_BANK_ID = 666;
    private final static String CALLBACK_DATA_PATTERN = "%s_%s_%s_%s_%s";
    private final static String TEST_DATA_WITH_EXISTENT_BANK_ID = String.format(CALLBACK_DATA_PATTERN,
            CallbackType.DIALOG.getId(), DialogNamesImpl.ADD_ACCOUNT.getName(), DialogNamesImpl.ADD_ACCOUNT.getName(), AccountNames.BANK.getName(), TEST_EXISTENT_BANK_ID);
    private final static String TEST_DATA_WITH_NONEXISTENT_BANK_ID = String.format(CALLBACK_DATA_PATTERN,
            CallbackType.DIALOG.getId(), DialogNamesImpl.ADD_ACCOUNT.getName(), DialogNamesImpl.ADD_ACCOUNT.getName(), AccountNames.BANK.getName(), TEST_NONEXISTENT_BANK_ID);
    protected BanksKeyboard keyboardBankMock = Mockito.mock(BanksKeyboard.class);

    @Override
    @BeforeEach
    public void beforeEach() {
        Mockito.when(bankMock.getCountry()).thenReturn(countryMock);
        super.beforeEach();
    }

    @Override
    protected String getCommandName() {
        return AccountNames.BANK.getName();
    }

    @Override
    public CommandDialogNames getCommandDialogName() {
        return AccountNames.BANK;
    }

    @Override
    public Dialog getCommand() {
        return new BankDialog(botMessageServiceMock, telegramUserServiceMock, messageTextMock, keyboardBankMock, bankServiceMock);
    }

    @Override
    public MessageText getMockMessageText() {
        return Mockito.mock(AccountDialogText.class);
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
//        Mockito.verify(telegramUserServiceMock, Mockito.times(times)).checkUser(telegramUserServiceMock, update);
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
