package ru.kas.myBudget.bots.telegram.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.telegram.telegrambots.meta.api.objects.*;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Unit-level testing for UpdateParameter")
public class UpdateParameterTest {
    private final static String TEST_TEXT = "Test Text";
    private final static String TEST_COMMAND = "/command";
    private final static String TEST_DATA = "test_data";
    private final static Long TEST_USER_ID = 123456789L;
    private final static Long TEST_CHAT_ID = 987654321L;
    private final static int TEST_MESSAGE_ID = 123412345;

    @ParameterizedTest
    @MethodSource("sourceUserId")
    public void ShouldPropertyExtractUserId(Update update, Long expectedId) {
        //when
        Long resultId = UpdateParameter.getUserId(update);

        //then
        assertEquals(expectedId, resultId);
    }

    private static Stream<Arguments> sourceUserId() {
        return Stream.of(
                Arguments.of(getUpdateWithText(TEST_TEXT), TEST_USER_ID),
                Arguments.of(getCallbackUpdateWithData(TEST_DATA), TEST_USER_ID)
        );
    }

    @ParameterizedTest
    @MethodSource("sourceChatId")
    public void ShouldPropertyExtractChatId(Update update, Long expectedId) {
        //when
        Long resultId = UpdateParameter.getChatId(update);

        //then
        assertEquals(expectedId, resultId);
    }

    private static Stream<Arguments> sourceChatId() {
        return Stream.of(
                Arguments.of(getUpdateWithText(TEST_TEXT), TEST_CHAT_ID),
                Arguments.of(getCallbackUpdateWithData(TEST_DATA), TEST_CHAT_ID)
        );
    }

    @ParameterizedTest
    @MethodSource("sourceMessageId")
    public void ShouldPropertyExtractMessageId(Update update, int expectedId) {
        //when
        int resultId = UpdateParameter.getMessageId(update);

        //then
        assertEquals(expectedId, resultId);
    }

    private static Stream<Arguments> sourceMessageId() {
        return Stream.of(
                Arguments.of(getUpdateWithText(TEST_TEXT), TEST_MESSAGE_ID),
                Arguments.of(getCallbackUpdateWithData(TEST_DATA), TEST_MESSAGE_ID)
        );
    }

    @ParameterizedTest
    @MethodSource("sourceMessageText")
    public void ShouldPropertyExtractMessageText(Update update, String expectedText) {
        //when
        String textResult = UpdateParameter.getMessageText(update);

        //then
        assertEquals(expectedText, textResult);
    }

    private static Stream<Arguments> sourceMessageText() {
        return Stream.of(
                Arguments.of(getUpdateWithText(TEST_TEXT), TEST_TEXT),
                Arguments.of(getUpdateWithText(TEST_COMMAND), TEST_COMMAND),
                Arguments.of(getCallbackUpdateWithData(TEST_TEXT), TEST_TEXT),
                Arguments.of(getCallbackUpdateWithData(TEST_DATA), TEST_TEXT)
        );
    }

    @ParameterizedTest
    @MethodSource("sourceCallbackData")
    public void ShouldPropertyExtractCallbackData(Update update, String[] expectedText) {
        //when
        String[] dataResult = UpdateParameter.getCallbackData(update).orElse(null);

        //then
        assertArrayEquals(expectedText, dataResult);
    }

    private static Stream<Arguments> sourceCallbackData() {
        return Stream.of(
                Arguments.of(getUpdateWithText(TEST_TEXT), null),
                Arguments.of(getUpdateWithText(TEST_COMMAND), null),
                Arguments.of(getCallbackUpdateWithData(TEST_TEXT), TEST_TEXT.split("_")),
                Arguments.of(getCallbackUpdateWithData(TEST_DATA), TEST_DATA.split("_"))
        );
    }

    private static Update getUpdateWithText(String text) {
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

    private static Update getCallbackUpdateWithData(String data) {
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
