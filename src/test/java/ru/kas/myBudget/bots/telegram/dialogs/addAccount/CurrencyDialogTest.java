package ru.kas.myBudget.bots.telegram.dialogs.addAccount;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kas.myBudget.bots.telegram.dialogs.AbstractDialogImplTest;
import ru.kas.myBudget.bots.telegram.dialogs.account.CurrencyDialog;
import ru.kas.myBudget.bots.telegram.dialogs.util.CommandDialogNames;
import ru.kas.myBudget.bots.telegram.dialogs.util.Dialog;
import ru.kas.myBudget.bots.telegram.keyboards.AccountDialog.CurrenciesKeyboard;
import ru.kas.myBudget.bots.telegram.texts.MessageText;
import ru.kas.myBudget.bots.telegram.texts.accountDialog.AccountText;
import ru.kas.myBudget.models.Currency;
import ru.kas.myBudget.services.CurrencyService;

import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.kas.myBudget.bots.telegram.callbacks.util.CallbackType.DIALOG;
import static ru.kas.myBudget.bots.telegram.dialogs.account.AccountNames.CURRENCY;
import static ru.kas.myBudget.bots.telegram.dialogs.util.DialogMapDefaultName.PAGE;
import static ru.kas.myBudget.bots.telegram.dialogs.DialogNamesImpl.ADD_ACCOUNT;


@DisplayName("Unit-level testing for AddAccount.CurrencyDialog")
public class CurrencyDialogTest extends AbstractDialogImplTest {
    private final static int TEST_PAGE_NUM = 999;
    private final static String TEST_EXISTENT_CURRENCY_ID = "7890";
    private final static String TEST_NONEXISTENT_CURRENCY_ID = "1111";
    private final static String CALLBACK_DATA_PATTERN = "%s_%s_%s_%s_%s%s";
    private final static String TEST_DATA_WITH_PAGE_NUM = String.format(CALLBACK_DATA_PATTERN,
            DIALOG.getId(), ADD_ACCOUNT.getName(), ADD_ACCOUNT.getName(), CURRENCY.getName(), PAGE.getId(),
            "_" + TEST_PAGE_NUM);
    private final static String TEST_DATA_WITH_EXISTENT_CURRENCY_ID = String.format(CALLBACK_DATA_PATTERN,
            DIALOG.getId(), ADD_ACCOUNT.getName(), ADD_ACCOUNT.getName(), CURRENCY.getName(),
            TEST_EXISTENT_CURRENCY_ID, "");
    private final static String TEST_DATA_WITH_NONEXISTENT_CURRENCY_ID = String.format(CALLBACK_DATA_PATTERN,
            DIALOG.getId(), ADD_ACCOUNT.getName(), ADD_ACCOUNT.getName(), CURRENCY.getName(),
            TEST_NONEXISTENT_CURRENCY_ID, "");
    protected final CurrencyService currencyServiceMock = Mockito.mock(CurrencyService.class);
    protected final CurrenciesKeyboard currenciesKeyboardMock = Mockito.mock(CurrenciesKeyboard.class);
    private final static Currency currencyMock = Mockito.mock(Currency.class);

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
        return new CurrencyDialog(botMessageServiceMock, telegramUserServiceMock, messageTextMock,
                currenciesKeyboardMock, currencyServiceMock);
    }

    @Override
    public MessageText getMockMessageText() {
        return Mockito.mock(AccountText.class);
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

    @Test
    public void shouldProperlySetTelegramServiceToKeyboard() {
        //given
        Update update = givenUpdate(TEST_USER_ID, TEST_CHAT_ID);

        //when
        getCommand().execute(update);

        //then
        Mockito.verify(currenciesKeyboardMock).setTelegramUserService(telegramUserServiceMock);
    }

    @Test
    public void shouldProperlySetCurrencyServiceToKeyboard() {
        //given
        Update update = givenUpdate(TEST_USER_ID, TEST_CHAT_ID);

        //when
        getCommand().execute(update);

        //then
        Mockito.verify(currenciesKeyboardMock).setCurrencyService(currencyServiceMock);
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
        boolean result = getCommand().commit(update);

        //then
        assertEquals(expected, result);
        Mockito.verify(telegramUserServiceMock, Mockito.times(times)).checkUser(telegramUserServiceMock, update);
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
