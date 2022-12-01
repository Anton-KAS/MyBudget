package komrachkov.anton.mybudget.bots.telegram.dialogs.account;

import komrachkov.anton.mybudget.bots.telegram.dialogs.util.CommandDialogNames;
import komrachkov.anton.mybudget.bots.telegram.texts.MessageText;
import komrachkov.anton.mybudget.bots.telegram.util.CommandNames;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Update;
import komrachkov.anton.mybudget.bots.telegram.dialogs.util.Dialog;
import komrachkov.anton.mybudget.bots.telegram.dialogs.util.DialogsState;
import komrachkov.anton.mybudget.bots.telegram.texts.dialogs.account.AccountText;
import komrachkov.anton.mybudget.bots.telegram.util.ExecuteMode;

import java.util.Optional;
import java.util.stream.Stream;

import static komrachkov.anton.mybudget.bots.telegram.callbacks.CallbackNamesImpl.ACCOUNT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static komrachkov.anton.mybudget.bots.telegram.dialogs.account.AccountNames.CURRENCY;
import static komrachkov.anton.mybudget.bots.telegram.dialogs.account.AccountNames.START_BALANCE;
import static komrachkov.anton.mybudget.bots.telegram.dialogs.account.StartBalanceDialog.VERIFY_EXCEPTION_TEXT;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

@DisplayName("Unit-level testing for account.StartBalanceDialog")
public class StartBalanceDialogTest extends AbstractAccountDialogTest {
    // TODO: Change extends class to AbstractAccountDialogTest
    public static final CommandNames TEST_COMMAND_NAME = ACCOUNT;

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
        return Mockito.mock(AccountText.class);
    }

    @Override
    public void shouldReturnTrueByExecuteCommit(Update update) {
    }

    @ParameterizedTest
    @MethodSource("sourceStartBalanceCommit")
    public void shouldProperlyExecuteCommit(Update update, boolean expected) {
        //given
        DialogsState.removeAllDialogs(TEST_CHAT_ID);
        DialogsState.put(TEST_CHAT_ID, TEST_COMMAND_NAME, CURRENCY.getName(), String.valueOf(TEST_CURRENCY_ID));
        Mockito.when(currencyServiceMock.findById(TEST_CURRENCY_ID)).thenReturn(Optional.of(currencyMock));
        int timesExpectedTrue = expected ? 1 : 0;
        int timesExpectedFalse = !expected ? 1 : 0;

        //when
        boolean result = getCommand().commit(update);

        //then
        assertEquals(expected, result);
        Mockito.verify(botMessageServiceMock, Mockito.times(timesExpectedFalse)).executeAndUpdateUser(
                telegramUserServiceMock, update, ExecuteMode.SEND, VERIFY_EXCEPTION_TEXT, null);
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
    public void shouldProperlyExecuteSkip() {
        // TODO: write unit-test for this
//        //given
//        DialogsState.removeAllDialogs(TEST_CHAT_ID);
//        DialogsState.put(TEST_CHAT_ID, TEST_COMMAND_NAME, CURRENCY.getName(), String.valueOf(TEST_CURRENCY_ID));
//        Mockito.when(currencyServiceMock.findById(TEST_CURRENCY_ID)).thenReturn(Optional.of(currencyMock));
//        Update update = givenUpdate(TEST_USER_ID, TEST_CHAT_ID);
//
//        //when - then
//        getCommand().skip(update);
//
//        //then
//        Mockito.verify(telegramUserServiceMock, Mockito.times(1))
//                .checkUser(telegramUserServiceMock, update);
    }
}
