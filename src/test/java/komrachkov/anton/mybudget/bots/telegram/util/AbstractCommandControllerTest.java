package komrachkov.anton.mybudget.bots.telegram.util;

import komrachkov.anton.mybudget.bots.telegram.dialogs.AbstractDialogImplTest;
import komrachkov.anton.mybudget.bots.telegram.keyboards.util.Keyboard;
import komrachkov.anton.mybudget.bots.telegram.texts.MessageText;
import komrachkov.anton.mybudget.models.TelegramUser;
import komrachkov.anton.mybudget.services.TelegramUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import komrachkov.anton.mybudget.bots.telegram.services.BotMessageService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

abstract public class AbstractCommandControllerTest {
    protected final static long TEST_USER_ID = 123456789L;
    protected final static long TEST_CHAT_ID = 987654321L;
    protected final static int TEST_MESSAGE_ID = 123;
    protected final static String TEST_CALLBACK_ID = "111222333444";

    protected static List<TelegramUser> TEST_USER_LIST = getUserList();
    protected static int TEST_USER_LIST_SIZE = TEST_USER_LIST.size();
    protected static InlineKeyboardMarkup TEST_INLINE_KEYBOARD = null;
    protected final static String TEST_TEXT = "Test Text";
    protected final static String TEST_COMMAND = "/command";
    protected final static String TEST_DATA = "test_data";

    protected static ExecuteMode DEFAULT_EXECUTE_MODE = ExecuteMode.SEND;

    protected BotMessageService botMessageServiceMock = Mockito.mock(BotMessageService.class);
    protected TelegramUserService telegramUserServiceMock = Mockito.mock(TelegramUserService.class);
    protected Keyboard keyboardMock = Mockito.mock(Keyboard.class);
    protected MessageText messageTextMock;

    protected abstract String getCommandName();

    protected abstract CommandController getCommand();

    protected abstract MessageText getMockMessageText();

    @BeforeEach
    public void beforeEach() {
        Mockito.when(keyboardMock.getKeyboard()).thenReturn(TEST_INLINE_KEYBOARD);

        this.messageTextMock = getMockMessageText();
        Mockito.when(messageTextMock.setChatId(TEST_CHAT_ID)).thenReturn(messageTextMock);
        Mockito.when(messageTextMock.getText()).thenReturn(TEST_TEXT);

        Mockito.when(telegramUserServiceMock.retrieveAllActiveUsers()).thenReturn(TEST_USER_LIST);
    }

    @Test
    public void shouldProperlyExecuteSetUserId() {
        //given
        Update update = givenUpdate(TEST_USER_ID, TEST_CHAT_ID);

        //when
        getCommand().execute(update);

        //then
        Mockito.verify(messageTextMock, Mockito.times(1)).setChatId(TEST_CHAT_ID);
    }

    @Test
    public void shouldProperlyExecuteSetUserIdExecuteMode() {
        //given
        Update update = givenUpdate(TEST_USER_ID, TEST_CHAT_ID);

        //when
        getCommand().execute(update, ExecuteMode.EDIT);

        //then
        Mockito.verify(messageTextMock, Mockito.times(1)).setChatId(TEST_CHAT_ID);
    }

    @Test
    public void shouldProperlyExecuteGetText() {
        //given
        Update update = givenUpdate(TEST_USER_ID, TEST_CHAT_ID);

        //when
        getCommand().execute(update);

        //then
        Mockito.verify(messageTextMock, Mockito.times(1)).getText();
    }

    @Test
    public void shouldProperlyExecuteGetTextExecuteMode() {
        //given
        Update update = givenUpdate(TEST_USER_ID, TEST_CHAT_ID);

        //when
        getCommand().execute(update, ExecuteMode.EDIT);

        //then
        Mockito.verify(messageTextMock, Mockito.times(1)).getText();
    }

    @Test
    public void shouldProperlyExecuteAndUpdateUser() {
        //given
        Update update = givenUpdate(TEST_USER_ID, TEST_CHAT_ID);

        //when
        getCommand().execute(update);

        //then
        Mockito.verify(botMessageServiceMock, Mockito.times(1)).executeAndUpdateUser(
                telegramUserServiceMock, update, ExecuteMode.SEND, TEST_TEXT, TEST_INLINE_KEYBOARD);
    }

    @Test
    public void shouldProperlyExecuteAndUpdateUserExecuteMode() {
        //given
        Update update = givenUpdate(TEST_USER_ID, TEST_CHAT_ID);

        //when
        getCommand().execute(update, ExecuteMode.EDIT);

        //then
        Mockito.verify(botMessageServiceMock, Mockito.times(1)).executeAndUpdateUser(
                telegramUserServiceMock, update, ExecuteMode.EDIT, TEST_TEXT, TEST_INLINE_KEYBOARD);
    }

    protected Update givenUpdate(long userId, long chatId) {
        Update update = new Update();
        Message message = Mockito.mock(Message.class);
        User from = Mockito.mock(User.class);

        Mockito.when(message.getChatId()).thenReturn(chatId);
        Mockito.when(message.getText()).thenReturn(getCommandName());
        Mockito.when(message.getFrom()).thenReturn(from);
        Mockito.when(from.getId()).thenReturn(userId);

        message.setFrom(from);
        update.setMessage(message);
        return update;
    }

    protected static List<TelegramUser> getUserList() {
        List<TelegramUser> telegramUsers = new ArrayList<>();
        telegramUsers.add(new TelegramUser());
        telegramUsers.add(new TelegramUser());
        telegramUsers.add(new TelegramUser());
        return telegramUsers;
    }

    /**
     * moved from {@link AbstractDialogImplTest}
     * @author Anton Komrachkov
     * @since 0.4
     */
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

    /**
     * moved from {@link AbstractDialogImplTest}
     * @author Anton Komrachkov
     * @since 0.4
     */
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

        callbackQuery.setId(TEST_CALLBACK_ID);
        callbackQuery.setData(data);
        callbackQuery.setFrom(user);
        callbackQuery.setMessage(message);
        update.setCallbackQuery(callbackQuery);
        return update;
    }
}
