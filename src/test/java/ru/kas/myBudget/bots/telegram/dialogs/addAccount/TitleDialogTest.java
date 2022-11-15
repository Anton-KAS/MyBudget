package ru.kas.myBudget.bots.telegram.dialogs.addAccount;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kas.myBudget.bots.telegram.dialogs.AbstractDialogImplTest;
import ru.kas.myBudget.bots.telegram.dialogs.AddAccount.TitleDialog;
import ru.kas.myBudget.bots.telegram.dialogs.CommandDialogNames;
import ru.kas.myBudget.bots.telegram.dialogs.Dialog;
import ru.kas.myBudget.bots.telegram.texts.MessageText;
import ru.kas.myBudget.bots.telegram.texts.addAccountDialog.AddAccountText;
import ru.kas.myBudget.bots.telegram.util.ExecuteMode;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.kas.myBudget.bots.telegram.dialogs.AddAccount.AddAccountNames.TITLE;
import static ru.kas.myBudget.bots.telegram.dialogs.AddAccount.TitleDialog.VERIFY_EXCEPTION_TEXT;
import static ru.kas.myBudget.bots.telegram.dialogs.AddAccount.TitleDialog.MAX_TITLE_LENGTH;
import static ru.kas.myBudget.bots.telegram.dialogs.AddAccount.TitleDialog.MIN_TITLE_LENGTH;


@DisplayName("Unit-level testing for TitleDialog")
public class TitleDialogTest extends AbstractDialogImplTest {

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
        return new TitleDialog(botMessageServiceMock, telegramUserServiceMock, messageTextMock, keyboardMock, dialogsMapMock);
    }

    @Override
    public MessageText getMockMessageText() {
        return Mockito.mock(AddAccountText.class);
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
        Mockito.verify(telegramUserServiceMock, Mockito.times(timesExpected)).checkUser(telegramUserServiceMock, update);
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
