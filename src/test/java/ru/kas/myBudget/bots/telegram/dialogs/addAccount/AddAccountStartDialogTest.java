package ru.kas.myBudget.bots.telegram.dialogs.addAccount;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kas.myBudget.bots.telegram.dialogs.*;
import ru.kas.myBudget.bots.telegram.texts.MessageText;
import ru.kas.myBudget.bots.telegram.texts.addAccountDialog.AddAccountText;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static ru.kas.myBudget.bots.telegram.dialogs.addAccount.AddAccountNames.*;

@DisplayName("Unit-level testing for AddAccount.SaveDialog")
public class AddAccountStartDialogTest extends AbstractDialogImplTest {
    @Test
    public void ShouldDoNothingInExecuteOrder() {
        //given
        Update update = givenUpdate(TEST_USER_ID, TEST_CHAT_ID);

        //when - then
        assertDoesNotThrow(() -> getCommand().executeByOrder(update, DEFAULT_EXECUTE_MODE));
    }

    @Override
    protected String getCommandName() {
        return START.getName();
    }

    @Override
    public Dialog getCommand() {
        return new AddAccountStartDialog(botMessageServiceMock, telegramUserServiceMock, messageTextMock, keyboardMock,
                DialogNamesImpl.ADD_ACCOUNT);
    }

    @Override
    public MessageText getMockMessageText() {
        return Mockito.mock(AddAccountText.class);
    }

    @ParameterizedTest
    @MethodSource("sourceStartCommit")
    public void shouldProperlyExecuteCommit(Update update, boolean expected) {
        //given
        int times = expected ? 1 : 0;

        //when
        boolean result = getCommand().commit(update);

        //then
        Mockito.verify(dialogsMapMock, Mockito.times(1)).remove(TEST_CHAT_ID);
        assertEquals(expected, result);
        Mockito.verify(dialogsMapMock, Mockito.times(times)).putDialogMap(anyLong(), anyMap());
    }

    public static Stream<Arguments> sourceStartCommit() {
        return Stream.of(
                Arguments.of(getUpdateWithText(TEST_TEXT), false),
                Arguments.of(getUpdateWithText(TEST_TEXT), false),

                Arguments.of(getUpdateWithText(TEST_COMMAND), false),
                Arguments.of(getUpdateWithText(TEST_COMMAND), false),

                Arguments.of(getCallbackUpdateWithData(TEST_TEXT), false),
                Arguments.of(getCallbackUpdateWithData(TEST_TEXT), false),

                Arguments.of(getCallbackUpdateWithData(TEST_COMMAND), false),
                Arguments.of(getCallbackUpdateWithData(TEST_COMMAND), false),

                Arguments.of(getCallbackUpdateWithData(TEST_DATA), true),
                Arguments.of(getCallbackUpdateWithData(TEST_DATA), true)
        );
    }

    @Override
    public CommandDialogNames getCommandDialogName() {
        return START;
    }

    @Override
    public void shouldReturnTrueByExecuteCommit(Update update) {
    }

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
