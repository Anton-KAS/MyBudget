package ru.kas.myBudget.bots.telegram.dialogs.addAccount;

import org.junit.jupiter.api.DisplayName;
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

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.kas.myBudget.bots.telegram.dialogs.addAccount.AddAccountNames.DESCRIPTION;
import static ru.kas.myBudget.bots.telegram.dialogs.addAccount.DescriptionDialog.MAX_DESCRIPTION_LENGTH;
import static ru.kas.myBudget.bots.telegram.dialogs.addAccount.DescriptionDialog.VERIFY_EXCEPTION_TEXT;


@DisplayName("Unit-level testing for AddAccount.DescriptionDialog")
public class DescriptionDialogTest extends AbstractDialogImplTest {

    @Override
    protected String getCommandName() {
        return DESCRIPTION.getName();
    }

    @Override
    public CommandDialogNames getCommandDialogName() {
        return DESCRIPTION;
    }

    @Override
    public Dialog getCommand() {
        return new DescriptionDialog(botMessageServiceMock, telegramUserServiceMock, messageTextMock, keyboardMock);
    }

    @Override
    public MessageText getMockMessageText() {
        return Mockito.mock(AddAccountText.class);
    }

    @Override
    public void shouldReturnTrueByExecuteCommit(Update update) {
    }

    @ParameterizedTest
    @MethodSource("sourceDescriptionCommit")
    public void shouldProperlyExecuteCommit(Update update, boolean expected) {
        //given
        int timesExpected = expected ? 1 : 0;
        int timesNonExpected = !expected && !update.hasCallbackQuery() ? 1 : 0;

        //when
        boolean result = getCommand().commit(update);

        //then
        assertEquals(expected, result);
        Mockito.verify(botMessageServiceMock, Mockito.times(timesNonExpected)).executeAndUpdateUser(telegramUserServiceMock, update, ExecuteMode.SEND,
                String.format(VERIFY_EXCEPTION_TEXT, MAX_DESCRIPTION_LENGTH), keyboardMock.getKeyboard());
        Mockito.verify(telegramUserServiceMock, Mockito.times(timesExpected)).checkUser(telegramUserServiceMock, update);
    }

    public static Stream<Arguments> sourceDescriptionCommit() {
        return Stream.of(
                Arguments.of(getCallbackUpdateWithData(TEST_TEXT), false),
                Arguments.of(getCallbackUpdateWithData(TEST_COMMAND), false),
                Arguments.of(getCallbackUpdateWithData(TEST_DATA), false),
                Arguments.of(getUpdateWithText(getNormalMessageText()), true),
                Arguments.of(getUpdateWithText(getLongMessageText()), false)
        );
    }

    public static String getLongMessageText() {
        return "s".repeat(MAX_DESCRIPTION_LENGTH + 1);
    }

    public static String getNormalMessageText() {
        return "s".repeat(MAX_DESCRIPTION_LENGTH);
    }
}
