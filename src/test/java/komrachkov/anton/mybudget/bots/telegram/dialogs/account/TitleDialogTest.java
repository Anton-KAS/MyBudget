package komrachkov.anton.mybudget.bots.telegram.dialogs.account;

import komrachkov.anton.mybudget.bots.telegram.dialogs.util.CommandDialogNames;
import komrachkov.anton.mybudget.bots.telegram.texts.MessageText;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Update;
import komrachkov.anton.mybudget.bots.telegram.dialogs.util.Dialog;
import komrachkov.anton.mybudget.bots.telegram.texts.dialogs.account.AccountDialogText;
import komrachkov.anton.mybudget.bots.telegram.util.ExecuteMode;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static komrachkov.anton.mybudget.bots.telegram.dialogs.account.AccountNames.TITLE;
import static komrachkov.anton.mybudget.bots.telegram.dialogs.account.TitleDialog.VERIFY_EXCEPTION_TEXT;
import static komrachkov.anton.mybudget.bots.telegram.dialogs.account.TitleDialog.MAX_TITLE_LENGTH;
import static komrachkov.anton.mybudget.bots.telegram.dialogs.account.TitleDialog.MIN_TITLE_LENGTH;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

@DisplayName("Unit-level testing for account.TitleDialog")
public class TitleDialogTest extends AbstractAccountDialogTest {

    @Override
    protected String getCommandName() {
        return TITLE.getName();
    }

    @Override
    public CommandDialogNames getCommandDialogName() {
        return TITLE;
    }

    @Override
    public Dialog getCommand() {
        return new TitleDialog(botMessageServiceMock, telegramUserServiceMock, messageTextMock, keyboardMock);
    }

    @Override
    public MessageText getMockMessageText() {
        return Mockito.mock(AccountDialogText.class);
    }

    @Override
    public void shouldReturnTrueByExecuteCommit(Update update) {
    }

    @ParameterizedTest
    @MethodSource("sourceTitleCommit")
    public void shouldProperlyExecuteCommit(Update update, boolean expected) {
        //given
        int timesExpected = expected ? 1 : 0;
        int timesNonExpected = !expected && !update.hasCallbackQuery() ? 1 : 0;

        //when
        boolean result = getCommand().commit(update);

        //then
        assertEquals(expected, result);
        Mockito.verify(botMessageServiceMock, Mockito.times(timesNonExpected)).executeAndUpdateUser(telegramUserServiceMock, update, ExecuteMode.SEND,
                String.format(VERIFY_EXCEPTION_TEXT, MIN_TITLE_LENGTH, MAX_TITLE_LENGTH), keyboardMock.getKeyboard());
//        Mockito.verify(telegramUserServiceMock, Mockito.times(timesExpected)).checkUser(telegramUserServiceMock, update);
    }

    public static Stream<Arguments> sourceTitleCommit() {
        return Stream.of(
                Arguments.of(getCallbackUpdateWithData(TEST_TEXT), false),
                Arguments.of(getCallbackUpdateWithData(TEST_COMMAND), false),
                Arguments.of(getCallbackUpdateWithData(TEST_DATA), false),
                Arguments.of(getCallbackUpdateWithData(getLongMessageText()), false),
                Arguments.of(getCallbackUpdateWithData(getNormalLongMessageText()), false),
                Arguments.of(getCallbackUpdateWithData(getNormalShortMessageText()), false),
                Arguments.of(getCallbackUpdateWithData(getShortMessageText()), false),
                Arguments.of(getUpdateWithText(getNormalLongMessageText()), true),
                Arguments.of(getUpdateWithText(getLongMessageText()), false),
                Arguments.of(getUpdateWithText(getNormalShortMessageText()), true),
                Arguments.of(getUpdateWithText(getShortMessageText()), false)
        );
    }

    public static String getLongMessageText() {
        return "t".repeat(MAX_TITLE_LENGTH + 1);
    }

    public static String getNormalLongMessageText() {
        return "t".repeat(MAX_TITLE_LENGTH);
    }

    public static String getNormalShortMessageText() {
        return "t".repeat(MIN_TITLE_LENGTH);
    }

    public static String getShortMessageText() {
        return "t".repeat(MIN_TITLE_LENGTH - 1);
    }
}
