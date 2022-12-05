package komrachkov.anton.mybudget.bots.telegram.dialogs.account;

import komrachkov.anton.mybudget.bots.telegram.callbacks.util.CallbackType;
import komrachkov.anton.mybudget.bots.telegram.dialogs.util.CommandDialogNames;
import komrachkov.anton.mybudget.bots.telegram.dialogs.util.Dialog;
import komrachkov.anton.mybudget.bots.telegram.dialogs.util.DialogMapDefaultName;
import komrachkov.anton.mybudget.bots.telegram.keyboards.dialogs.account.CurrenciesKeyboard;
import komrachkov.anton.mybudget.bots.telegram.texts.MessageText;
import komrachkov.anton.mybudget.bots.telegram.texts.dialogs.account.AccountDialogText;
import komrachkov.anton.mybudget.models.Currency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static komrachkov.anton.mybudget.bots.telegram.dialogs.account.AccountNames.CURRENCY;
import static komrachkov.anton.mybudget.bots.telegram.dialogs.DialogNamesImpl.ADD_ACCOUNT;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

@DisplayName("Unit-level testing for account.CurrencyDialog")
public class CurrencyDialogTest extends AbstractAccountDialogTest {
    private final static int TEST_PAGE_NUM = 999;
    private final static String TEST_EXISTENT_CURRENCY_ID = "7890";
    private final static String TEST_NONEXISTENT_CURRENCY_ID = "1111";
    private final static String CALLBACK_DATA_PATTERN = "%s_%s_%s_%s_%s%s";
    private final static String TEST_DATA_WITH_PAGE_NUM = String.format(CALLBACK_DATA_PATTERN,
            CallbackType.DIALOG.getId(), ADD_ACCOUNT.getName(), ADD_ACCOUNT.getName(), CURRENCY.getName(), DialogMapDefaultName.PAGE.getId(),
            "_" + TEST_PAGE_NUM);
    private final static String TEST_DATA_WITH_EXISTENT_CURRENCY_ID = String.format(CALLBACK_DATA_PATTERN,
            CallbackType.DIALOG.getId(), ADD_ACCOUNT.getName(), ADD_ACCOUNT.getName(), CURRENCY.getName(),
            TEST_EXISTENT_CURRENCY_ID, "");
    private final static String TEST_DATA_WITH_NONEXISTENT_CURRENCY_ID = String.format(CALLBACK_DATA_PATTERN,
            CallbackType.DIALOG.getId(), ADD_ACCOUNT.getName(), ADD_ACCOUNT.getName(), CURRENCY.getName(),
            TEST_NONEXISTENT_CURRENCY_ID, "");
    protected final CurrenciesKeyboard currenciesKeyboardMock = Mockito.mock(CurrenciesKeyboard.class);

    @Override
    @BeforeEach
    public void beforeEach() {
        Mockito.when(currenciesKeyboardMock.setUserId(TEST_USER_ID)).thenReturn(currenciesKeyboardMock);
        super.beforeEach();
    }

    @Override
    protected String getCommandName() {
        return CURRENCY.getName();
    }

    @Override
    public CommandDialogNames getCommandDialogName() {
        return CURRENCY;
    }

    @Override
    public Dialog getCommand() {
        return new CurrencyDialog(telegramUserServiceMock, accountTextMock,
                currenciesKeyboardMock, currencyServiceMock);
    }

    @Override
    public MessageText getMockMessageText() {
        return Mockito.mock(AccountDialogText.class);
    }

    @Override
    public void shouldReturnTrueByExecuteCommit(Update update) {
    }

    @Test
    public void shouldProperlySetUserIdToKeyboard() {
        //given
        Update update = givenUpdate(TEST_USER_ID, TEST_CHAT_ID);

        //when
        getCommand().execute(update);

        //then
        Mockito.verify(currenciesKeyboardMock).setUserId(TEST_USER_ID);
    }

    @ParameterizedTest
    @MethodSource("sourcePage")
    public void shouldProperlySetPageToKeyboard(Update update, int expectedPage) {
        //when
        getCommand().execute(update);

        //then
        Mockito.verify(currenciesKeyboardMock).setPage(expectedPage);
    }

    private static Stream<Arguments> sourcePage() {
        return Stream.of(
                Arguments.of(getUpdateWithText(TEST_TEXT), 1),
                Arguments.of(getUpdateWithText(TEST_COMMAND), 1),
                Arguments.of(getCallbackUpdateWithData(TEST_TEXT), 1),
                Arguments.of(getCallbackUpdateWithData(TEST_COMMAND), 1),
                Arguments.of(getCallbackUpdateWithData(TEST_DATA), 1),
                Arguments.of(getCallbackUpdateWithData(TEST_DATA_WITH_PAGE_NUM), TEST_PAGE_NUM)
        );
    }

    @ParameterizedTest
    @MethodSource("sourceCurrencyCommit")
    public void shouldProperlyExecuteCommit(Update update, int testCurrencyId,
                                            Optional<Currency> returnFindById, boolean expected) {
        //given
        Mockito.when(currencyServiceMock.findById(testCurrencyId)).thenReturn(returnFindById);
        int times = expected ? 1 : 0;

        //when
        boolean result = getCommand().commit(update).isResultCommit();

        //then
        assertEquals(expected, result);
    }

    public static Stream<Arguments> sourceCurrencyCommit() {
        return Stream.of(
                Arguments.of(getUpdateWithText(TEST_TEXT),
                        TEST_EXISTENT_CURRENCY_ID, Optional.of(currencyMock), false),
                Arguments.of(getUpdateWithText(TEST_TEXT),
                        TEST_NONEXISTENT_CURRENCY_ID, Optional.empty(), false),

                Arguments.of(getUpdateWithText(TEST_COMMAND),
                        TEST_EXISTENT_CURRENCY_ID, Optional.of(currencyMock), false),
                Arguments.of(getUpdateWithText(TEST_COMMAND),
                        TEST_NONEXISTENT_CURRENCY_ID, Optional.empty(), false),

                Arguments.of(getUpdateWithText(TEST_DATA_WITH_PAGE_NUM),
                        TEST_EXISTENT_CURRENCY_ID, Optional.of(currencyMock), false),
                Arguments.of(getUpdateWithText(TEST_DATA_WITH_PAGE_NUM),
                        TEST_NONEXISTENT_CURRENCY_ID, Optional.empty(), false),

                Arguments.of(getUpdateWithText(TEST_DATA_WITH_EXISTENT_CURRENCY_ID),
                        TEST_EXISTENT_CURRENCY_ID, Optional.of(currencyMock), false),
                Arguments.of(getUpdateWithText(TEST_DATA_WITH_NONEXISTENT_CURRENCY_ID),
                        TEST_NONEXISTENT_CURRENCY_ID, Optional.empty(), false),

                Arguments.of(getCallbackUpdateWithData(TEST_TEXT),
                        TEST_EXISTENT_CURRENCY_ID, Optional.of(currencyMock), false),
                Arguments.of(getCallbackUpdateWithData(TEST_TEXT),
                        TEST_NONEXISTENT_CURRENCY_ID, Optional.empty(), false),

                Arguments.of(getCallbackUpdateWithData(TEST_COMMAND),
                        TEST_EXISTENT_CURRENCY_ID, Optional.of(currencyMock), false),
                Arguments.of(getCallbackUpdateWithData(TEST_COMMAND),
                        TEST_NONEXISTENT_CURRENCY_ID, Optional.empty(), false),

                Arguments.of(getCallbackUpdateWithData(TEST_DATA),
                        TEST_EXISTENT_CURRENCY_ID, Optional.of(currencyMock), false),
                Arguments.of(getCallbackUpdateWithData(TEST_DATA),
                        TEST_NONEXISTENT_CURRENCY_ID, Optional.empty(), false),

                Arguments.of(getCallbackUpdateWithData(TEST_DATA_WITH_PAGE_NUM),
                        TEST_EXISTENT_CURRENCY_ID, Optional.of(currencyMock), false),
                Arguments.of(getCallbackUpdateWithData(TEST_DATA_WITH_PAGE_NUM),
                        TEST_NONEXISTENT_CURRENCY_ID, Optional.empty(), false),

                Arguments.of(getCallbackUpdateWithData(TEST_DATA_WITH_EXISTENT_CURRENCY_ID),
                        TEST_EXISTENT_CURRENCY_ID, Optional.of(currencyMock), true),
                Arguments.of(getCallbackUpdateWithData(TEST_DATA_WITH_NONEXISTENT_CURRENCY_ID),
                        TEST_NONEXISTENT_CURRENCY_ID, Optional.empty(), false)
        );
    }
}
