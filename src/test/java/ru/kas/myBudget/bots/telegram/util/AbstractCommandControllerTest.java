package ru.kas.myBudget.bots.telegram.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.kas.myBudget.bots.telegram.dialogs.DialogsMap;
import ru.kas.myBudget.bots.telegram.keyboards.Keyboard;
import ru.kas.myBudget.bots.telegram.services.BotMessageService;
import ru.kas.myBudget.bots.telegram.texts.MessageText;
import ru.kas.myBudget.models.TelegramUser;
import ru.kas.myBudget.services.TelegramUserService;

import java.util.ArrayList;
import java.util.List;

abstract public class AbstractCommandControllerTest {
    protected static long TEST_USER_ID = 123456789L;
    protected static long TEST_CHAT_ID = TEST_USER_ID;
    protected static List<TelegramUser> TEST_USER_LIST = getUserList();
    protected static int TEST_USER_LIST_SIZE = TEST_USER_LIST.size();
    protected static InlineKeyboardMarkup TEST_INLINE_KEYBOARD = null;
    protected static String TEST_TEXT = "Test Text";
    protected static ExecuteMode DEFAULT_EXECUTE_MODE = ExecuteMode.SEND;

    protected BotMessageService botMessageServiceMock = Mockito.mock(BotMessageService.class);
    protected TelegramUserService telegramUserServiceMock = Mockito.mock(TelegramUserService.class);
    protected DialogsMap dialogMapMock = Mockito.mock(DialogsMap.class);
    protected Keyboard keyboardMock = Mockito.mock(Keyboard.class);
    protected MessageText messageTextMock = Mockito.mock(MessageText.class);

    protected abstract String getCommandName();

    public abstract CommandController getCommand();
    public abstract MessageText getMockMessageText();

    @BeforeEach
    public void beforeEach() {
        Mockito.when(keyboardMock.getKeyboard()).thenReturn(TEST_INLINE_KEYBOARD);

        this.messageTextMock = getMockMessageText();
        Mockito.when(messageTextMock.setUserId(TEST_USER_ID)).thenReturn(messageTextMock);
        Mockito.when(messageTextMock.getText()).thenReturn(TEST_TEXT);

        Mockito.when(telegramUserServiceMock.retrieveAllActiveUsers()).thenReturn(TEST_USER_LIST);

        Mockito.when(dialogMapMock.remove(TEST_USER_ID)).thenReturn(dialogMapMock);
    }

    @Test
    public void shouldProperlyExecuteSetUserId() {
        //given
        Update update = givenUpdate(TEST_USER_ID, TEST_CHAT_ID);

        //when
        getCommand().execute(update);

        //then
        Mockito.verify(messageTextMock, Mockito.times(1)).setUserId(TEST_USER_ID);
    }

    @Test
    public void shouldProperlyExecuteSetUserIdExecuteMode() {
        //given
        Update update = givenUpdate(TEST_USER_ID, TEST_CHAT_ID);

        //when
        getCommand().execute(update, ExecuteMode.EDIT);

        //then
        Mockito.verify(messageTextMock, Mockito.times(1)).setUserId(TEST_USER_ID);
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
}
