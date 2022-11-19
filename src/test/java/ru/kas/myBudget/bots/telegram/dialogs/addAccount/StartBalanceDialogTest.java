package ru.kas.myBudget.bots.telegram.dialogs.addAccount;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kas.myBudget.bots.telegram.dialogs.AbstractDialogImplTest;
import ru.kas.myBudget.bots.telegram.dialogs.CommandDialogNames;
import ru.kas.myBudget.bots.telegram.dialogs.Dialog;
import ru.kas.myBudget.bots.telegram.texts.MessageText;
import ru.kas.myBudget.bots.telegram.texts.addAccountDialog.AddAccountText;
import ru.kas.myBudget.bots.telegram.util.ExecuteMode;
import ru.kas.myBudget.models.Currency;
import ru.kas.myBudget.services.CurrencyService;

import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.kas.myBudget.bots.telegram.dialogs.addAccount.AddAccountNames.CURRENCY;
import static ru.kas.myBudget.bots.telegram.dialogs.addAccount.AddAccountNames.START_BALANCE;
import static ru.kas.myBudget.bots.telegram.dialogs.addAccount.StartBalanceDialog.VERIFY_EXCEPTION_TEXT;


@DisplayName("Unit-level testing for AddAccount.StartBalanceDialog")
public class StartBalanceDialogTest extends AbstractDialogImplTest {
    private static final int TEST_CURRENCY_ID = 999;
    private static final CurrencyService currencyServiceMock = Mockito.mock(CurrencyService.class);
    private static final Currency currencyMock = Mockito.mock(Currency.class);

    @Override
    protected String getCommandName() {
        return START_BALANCE.getName();
    }

    @Override
    public CommandDialogNames getCommandDialogName() {
        return START_BALANCE;
    }

    @Override
    public Dialog getCommand() {
        return new StartBalanceDialog(botMessageServiceMock, telegramUserServiceMock, messageTextMock, keyboardMock,
                currencyServiceMock);
    }

    @Override
    public MessageText getMockMessageText() {
        return Mockito.mock(AddAccountText.class);
    }

    @Override
    public void shouldReturnTrueByExecuteCommit(Update update) {
    }

    @ParameterizedTest
    @MethodSource("sourceStartBalanceCommit")
    public void shouldProperlyExecuteCommit(Update update, boolean expected) {
        //given
        this.testDialogMap.put(CURRENCY.getName(), String.valueOf(TEST_CURRENCY_ID));
        Mockito.when(dialogsMapMock.getDialogMapById(TEST_CHAT_ID)).thenReturn(testDialogMap);
        Mockito.when(currencyServiceMock.findById(TEST_CURRENCY_ID)).thenReturn(Optional.of(currencyMock));
        int timesExpectedTrue = expected ? 1 : 0;
        int timesExpectedFalse = !expected ? 1 : 0;

        //when
        boolean result = getCommand().commit(update);

        //then
        assertEquals(expected, result);
        Mockito.verify(botMessageServiceMock, Mockito.times(timesExpectedFalse)).executeAndUpdateUser(
                telegramUserServiceMock, update, ExecuteMode.SEND, VERIFY_EXCEPTION_TEXT, null);
        Mockito.verify(telegramUserServiceMock, Mockito.times(timesExpectedTrue)).checkUser(telegramUserServiceMock, update);
    }

    public static Stream<Arguments> sourceStartBalanceCommit() {
        return Stream.of(
                Arguments.of(getCallbackUpdateWithData(TEST_TEXT), false),
                Arguments.of(getCallbackUpdateWithData(TEST_COMMAND), false),
                Arguments.of(getCallbackUpdateWithData(TEST_DATA), false),
                Arguments.of(getUpdateWithText(TEST_TEXT), false),
                Arguments.of(getUpdateWithText("0.0 1.2"), false),
                Arguments.of(getUpdateWithText("test 1.2"), false),
                Arguments.of(getUpdateWithText("0.0"), true),
                Arguments.of(getUpdateWithText("1"), true),
                Arguments.of(getUpdateWithText("1,2345"), true),
                Arguments.of(getUpdateWithText("123456789,123456789"), true)
        );
    }

    @Override
    @Test
    public void shouldProperlyExecuteSkip() {
        //given
        this.testDialogMap.put(CURRENCY.getName(), String.valueOf(TEST_CURRENCY_ID));
        Mockito.when(dialogsMapMock.getDialogMapById(TEST_CHAT_ID)).thenReturn(testDialogMap);
        Mockito.when(currencyServiceMock.findById(TEST_CURRENCY_ID)).thenReturn(Optional.of(currencyMock));
        Update update = givenUpdate(TEST_USER_ID, TEST_CHAT_ID);

        //when - then
        getCommand().skip(update);

        //then
        Mockito.verify(telegramUserServiceMock, Mockito.times(1))
                .checkUser(telegramUserServiceMock, update);
    }
}
