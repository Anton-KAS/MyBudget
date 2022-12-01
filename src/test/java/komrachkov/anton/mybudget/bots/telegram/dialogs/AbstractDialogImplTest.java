package komrachkov.anton.mybudget.bots.telegram.dialogs;

import komrachkov.anton.mybudget.bots.telegram.dialogs.util.CommandDialogNames;
import komrachkov.anton.mybudget.bots.telegram.dialogs.util.Dialog;
import komrachkov.anton.mybudget.bots.telegram.dialogs.util.DialogIndex;
import komrachkov.anton.mybudget.bots.telegram.dialogs.util.DialogsState;
import komrachkov.anton.mybudget.bots.telegram.util.AbstractCommandControllerTest;
import komrachkov.anton.mybudget.bots.telegram.util.ExecuteMode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.*;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

public abstract class AbstractDialogImplTest extends AbstractCommandControllerTest {
    protected final static String TEST_STEP_ID = "testStepId";
    protected final static String TEST_STEP_TEXT = "test step text";
    protected Map<String, String> testDialogMap;

    protected abstract CommandDialogNames getCommandDialogName();

    @Override
    protected abstract String getCommandName();

    @Override
    protected abstract Dialog getCommand();

    @Override
    @BeforeEach
    public void beforeEach() {
        super.beforeEach();
        testDialogMap = new HashMap<>();
    }

    @ParameterizedTest
    @MethodSource("sourceCommit")
    public void shouldReturnTrueByExecuteCommit(Update update) {
        //when
        boolean result = getCommand().commit(update);

        //then
        assertTrue(result);
    }

    public static Stream<Arguments> sourceCommit() {
        return Stream.of(
                Arguments.of(givenCommandUpdateForCommit(TEST_USER_ID, TEST_CHAT_ID))
        );
    }

    protected static Update givenCommandUpdateForCommit(long userId, long chatId) {
        Update update = new Update();
        Message message = Mockito.mock(Message.class);
        User from = Mockito.mock(User.class);

        Mockito.when(message.getChatId()).thenReturn(chatId);
        Mockito.when(message.getFrom()).thenReturn(from);
        Mockito.when(from.getId()).thenReturn(userId);

        message.setFrom(from);
        update.setMessage(message);
        return update;
    }

    @Test
    public void shouldProperlyExecuteSkip() {
        //given
        Update update = givenUpdate(TEST_USER_ID, TEST_CHAT_ID);

        //when - then
        assertDoesNotThrow(() -> getCommand().skip(update));
    }

    @ParameterizedTest
    @MethodSource("sourceGetExecuteMode")
    public void shouldProperlyGetExecuteMode(Update update, Integer dialogStep, ExecuteMode expected) {
        //when
        ExecuteMode result = getCommand().getExecuteMode(update, dialogStep);

        //then
        assertEquals(expected, result);
    }

    public static Stream<Arguments> sourceGetExecuteMode() {
        return Stream.of(
                Arguments.of(getUpdateWithText(TEST_TEXT), null, ExecuteMode.SEND),
                Arguments.of(getUpdateWithText(TEST_TEXT), DialogIndex.FIRST_STEP_INDEX.ordinal(), ExecuteMode.SEND),
                Arguments.of(getUpdateWithText(TEST_TEXT), DialogIndex.FIRST_STEP_INDEX.ordinal() + 1, ExecuteMode.SEND),

                Arguments.of(getUpdateWithText(TEST_COMMAND), null, ExecuteMode.SEND),
                Arguments.of(getUpdateWithText(TEST_COMMAND), DialogIndex.FIRST_STEP_INDEX.ordinal(), ExecuteMode.SEND),
                Arguments.of(getUpdateWithText(TEST_COMMAND), DialogIndex.FIRST_STEP_INDEX.ordinal() + 1, ExecuteMode.SEND),

                Arguments.of(getCallbackUpdateWithData(TEST_TEXT), null, ExecuteMode.SEND),
                Arguments.of(getCallbackUpdateWithData(TEST_TEXT), DialogIndex.FIRST_STEP_INDEX.ordinal(), ExecuteMode.SEND),
                Arguments.of(getCallbackUpdateWithData(TEST_TEXT), DialogIndex.FIRST_STEP_INDEX.ordinal() + 1, ExecuteMode.EDIT),

                Arguments.of(getCallbackUpdateWithData(TEST_COMMAND), null, ExecuteMode.SEND),
                Arguments.of(getCallbackUpdateWithData(TEST_COMMAND), DialogIndex.FIRST_STEP_INDEX.ordinal(), ExecuteMode.SEND),
                Arguments.of(getCallbackUpdateWithData(TEST_COMMAND), DialogIndex.FIRST_STEP_INDEX.ordinal() + 1, ExecuteMode.EDIT),

                Arguments.of(getCallbackUpdateWithData(TEST_DATA), null, ExecuteMode.SEND),
                Arguments.of(getCallbackUpdateWithData(TEST_DATA), DialogIndex.FIRST_STEP_INDEX.ordinal(), ExecuteMode.SEND),
                Arguments.of(getCallbackUpdateWithData(TEST_DATA), DialogIndex.FIRST_STEP_INDEX.ordinal() + 1, ExecuteMode.EDIT)
        );
    }

    @Test
    public void shouldProperlyAddToDialogMap() {
        //given
        DialogsState.removeAllDialogs(TEST_CHAT_ID);
        int expectedSize = 2;

        //when
        getCommand().addToDialogMap(TEST_CHAT_ID, getCommandDialogName(), TEST_STEP_ID, TEST_STEP_TEXT);
        int actualSize = DialogsState.getStateSize(TEST_CHAT_ID);

        //then
        assertEquals(expectedSize, actualSize);
    }
}
