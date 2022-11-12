package ru.kas.myBudget.bots.telegram.commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.kas.myBudget.bots.telegram.keyboards.Keyboard;
import ru.kas.myBudget.bots.telegram.services.BotMessageService;
import ru.kas.myBudget.bots.telegram.texts.MessageText;
import ru.kas.myBudget.bots.telegram.util.CommandController;
import ru.kas.myBudget.bots.telegram.util.ExecuteMode;
import ru.kas.myBudget.services.TelegramUserService;

abstract class AbstractCommandTest {
    protected static long TEST_USER_ID = 123456789L;
    protected static long DEFAULT_CHAT_ID = TEST_USER_ID;
    protected static InlineKeyboardMarkup DEFAULT_TEST_INLINE_KEYBOARD = null;
    protected static String DEFAULT_TEST_TEXT = "Test Text";
    protected static ExecuteMode DEFAULT_EXECUTE_MODE = ExecuteMode.SEND;
    protected BotMessageService botMessageService = Mockito.mock(BotMessageService.class);
    protected TelegramUserService telegramUserService = Mockito.mock(TelegramUserService.class);
    protected Keyboard keyboard = Mockito.mock(Keyboard.class);
    protected MessageText messageText = Mockito.mock(MessageText.class);

    abstract String getCommandName();

    abstract CommandController getCommand();

    @BeforeEach
    public void beforeEach() {
        Mockito.when(keyboard.getKeyboard()).thenReturn(DEFAULT_TEST_INLINE_KEYBOARD);
        Mockito.when(messageText.setUserId(TEST_USER_ID)).thenReturn(messageText);
        Mockito.when(messageText.setUserId(TEST_USER_ID)).thenReturn(messageText);
        Mockito.when(messageText.getText()).thenReturn(DEFAULT_TEST_TEXT);
    }

    @Test
    public void shouldProperlyExecuteSetUserId() {
        //given
        Update update = givenUpdate(TEST_USER_ID, DEFAULT_CHAT_ID);

        //when
        getCommand().execute(update);

        //then
        Mockito.verify(messageText, Mockito.times(1)).setUserId(TEST_USER_ID);
    }

    @Test
    public void shouldProperlyExecuteSetUserIdExecuteMode() {
        //given
        Update update = givenUpdate(TEST_USER_ID, DEFAULT_CHAT_ID);

        //when
        getCommand().execute(update, DEFAULT_EXECUTE_MODE);

        //then
        Mockito.verify(messageText, Mockito.times(1)).setUserId(TEST_USER_ID);
    }

    @Test
    public void shouldProperlyExecuteGetText() {
        //given
        Update update = givenUpdate(TEST_USER_ID, DEFAULT_CHAT_ID);

        //when
        getCommand().execute(update);

        //then
        Mockito.verify(messageText, Mockito.times(1)).getText();
    }

    @Test
    public void shouldProperlyExecuteGetTextExecuteMode() {
        //given
        Update update = givenUpdate(TEST_USER_ID, DEFAULT_CHAT_ID);

        //when
        getCommand().execute(update, DEFAULT_EXECUTE_MODE);

        //then
        Mockito.verify(messageText, Mockito.times(1)).getText();
    }

    @Test
    public void shouldProperlyExecuteAndUpdateUser() {
        //given
        Update update = givenUpdate(TEST_USER_ID, DEFAULT_CHAT_ID);

        //when
        getCommand().execute(update);

        //then
        Mockito.verify(botMessageService, Mockito.times(1)).executeAndUpdateUser(
                telegramUserService, update, ExecuteMode.SEND, DEFAULT_TEST_TEXT, DEFAULT_TEST_INLINE_KEYBOARD);
    }

    @Test
    public void shouldProperlyExecuteAndUpdateUserExecuteMode() {
        //given
        Update update = givenUpdate(TEST_USER_ID, DEFAULT_CHAT_ID);

        //when
        getCommand().execute(update, DEFAULT_EXECUTE_MODE);

        //then
        Mockito.verify(botMessageService, Mockito.times(1)).executeAndUpdateUser(
                telegramUserService, update, ExecuteMode.SEND, DEFAULT_TEST_TEXT, DEFAULT_TEST_INLINE_KEYBOARD);
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
}
