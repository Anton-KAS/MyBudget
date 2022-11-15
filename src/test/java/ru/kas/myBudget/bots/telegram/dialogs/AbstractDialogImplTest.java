package ru.kas.myBudget.bots.telegram.dialogs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.*;
import ru.kas.myBudget.bots.telegram.util.AbstractCommandControllerTest;
import ru.kas.myBudget.bots.telegram.util.ExecuteMode;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static ru.kas.myBudget.bots.telegram.dialogs.DialogIndex.FIRST_STEP_INDEX;
import static ru.kas.myBudget.bots.telegram.dialogs.DialogMapDefaultName.CURRENT_DIALOG_STEP;

public abstract class AbstractDialogImplTest extends AbstractCommandControllerTest {
    protected final static String TEST_DIALOG_STEP_ID = "3";
    protected final static int TEST_MESSAGE_ID = 123;
    protected final static String TEST_COMMAND = "/command";
    protected final static String TEST_DATA = "test_data";
    protected final static String TEST_STEP_ID = "testStepId";
    protected final static String TEST_STEP_TEXT = "test step text";
    protected Map<String, String> testDialogMap;

    @Override
    public abstract Dialog getCommand();

    public abstract CommandDialogNames getCommandDialogName();

    @Override
    @BeforeEach
    public void beforeEach() {
        super.beforeEach();
        testDialogMap = new HashMap<>();
        Mockito.when(dialogsMapMock.getDialogMapById(TEST_CHAT_ID)).thenReturn(testDialogMap);
        Mockito.when(dialogsMapMock.getDialogStepById(TEST_USER_ID, CURRENT_DIALOG_STEP.getId())).thenReturn(TEST_DIALOG_STEP_ID);
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
                Arguments.of(getUpdateWithText(TEST_TEXT), FIRST_STEP_INDEX.getIndex(), ExecuteMode.SEND),
                Arguments.of(getUpdateWithText(TEST_TEXT), FIRST_STEP_INDEX.getIndex() + 1, ExecuteMode.SEND),

                Arguments.of(getUpdateWithText(TEST_COMMAND), null, ExecuteMode.SEND),
                Arguments.of(getUpdateWithText(TEST_COMMAND), FIRST_STEP_INDEX.getIndex(), ExecuteMode.SEND),
                Arguments.of(getUpdateWithText(TEST_COMMAND), FIRST_STEP_INDEX.getIndex() + 1, ExecuteMode.SEND),

                Arguments.of(getCallbackUpdateWithData(TEST_TEXT), null, ExecuteMode.SEND),
                Arguments.of(getCallbackUpdateWithData(TEST_TEXT), FIRST_STEP_INDEX.getIndex(), ExecuteMode.SEND),
                Arguments.of(getCallbackUpdateWithData(TEST_TEXT), FIRST_STEP_INDEX.getIndex() + 1, ExecuteMode.EDIT),

                Arguments.of(getCallbackUpdateWithData(TEST_COMMAND), null, ExecuteMode.SEND),
                Arguments.of(getCallbackUpdateWithData(TEST_COMMAND), FIRST_STEP_INDEX.getIndex(), ExecuteMode.SEND),
                Arguments.of(getCallbackUpdateWithData(TEST_COMMAND), FIRST_STEP_INDEX.getIndex() + 1, ExecuteMode.EDIT),

                Arguments.of(getCallbackUpdateWithData(TEST_DATA), null, ExecuteMode.SEND),
                Arguments.of(getCallbackUpdateWithData(TEST_DATA), FIRST_STEP_INDEX.getIndex(), ExecuteMode.SEND),
                Arguments.of(getCallbackUpdateWithData(TEST_DATA), FIRST_STEP_INDEX.getIndex() + 1, ExecuteMode.EDIT)
        );
    }

    @Test
    public void shouldProperlyAddToDialogMap() {
        //given
        int expectedSize = testDialogMap.size() + 2;

        //when
        getCommand().addToDialogMap(TEST_CHAT_ID, getCommandDialogName(), TEST_STEP_ID, TEST_STEP_TEXT);


        //then
        assertEquals(testDialogMap.size(), expectedSize);
    }

    protected static Update getUpdateWithText(String text) {
        Update update = new Update();
        Message message = new Message();
        User user = new User();
        Chat chat = new Chat();

        user.setId(TEST_USER_ID);
        chat.setId(TEST_CHAT_ID);

        message.setText(text);
        message.setFrom(user);
        message.setMessageId(TEST_MESSAGE_ID);
        message.setChat(chat);
        update.setMessage(message);
        return update;
    }

    protected static Update getCallbackUpdateWithData(String data) {
        Update update = new Update();
        CallbackQuery callbackQuery = new CallbackQuery();
        Message message = new Message();
        User user = new User();
        Chat chat = new Chat();

        user.setId(TEST_USER_ID);
        chat.setId(TEST_CHAT_ID);

        message.setMessageId(TEST_MESSAGE_ID);
        message.setText(TEST_TEXT);
        message.setChat(chat);

        callbackQuery.setData(data);
        callbackQuery.setFrom(user);
        callbackQuery.setMessage(message);
        update.setCallbackQuery(callbackQuery);
        return update;
    }

}
