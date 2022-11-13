package ru.kas.myBudget.bots.telegram.callbacks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.kas.myBudget.bots.telegram.services.BotMessageService;
import ru.kas.myBudget.bots.telegram.util.ExecuteMode;
import ru.kas.myBudget.services.TelegramUserService;

@DisplayName("Unit-level testing for CloseCallback")
public class CloseCallbackTest {
    protected static long TEST_USER_ID = 123456789L;
    protected static long TEST_CHAT_ID = TEST_USER_ID;
    protected static int TEST_MESSAGE_ID = 123;
    protected static ExecuteMode DEFAULT_EXECUTE_MODE = ExecuteMode.SEND;

    protected BotMessageService botMessageService = Mockito.mock(BotMessageService.class);
    protected TelegramUserService telegramUserService = Mockito.mock(TelegramUserService.class);

    @BeforeEach
    public void beforeEach() {
    }

    @Test
    public void shouldProperlyExecuteDeleteMessage() {
        //given
        Update update = givenUpdate(TEST_USER_ID, TEST_CHAT_ID);
        CloseCallback closeCallback = new CloseCallback(botMessageService, telegramUserService, ExecuteMode.SEND,
                null, null);

        //when
        closeCallback.execute(update);

        //then
        Mockito.verify(botMessageService, Mockito.times(1)).deleteMessage(TEST_USER_ID, TEST_MESSAGE_ID);
    }
    @Test
    public void shouldProperlyExecuteUpdateUser() {
        //given
        Update update = givenUpdate(TEST_USER_ID, TEST_CHAT_ID);
        CloseCallback closeCallback = new CloseCallback(botMessageService, telegramUserService, ExecuteMode.SEND,
                null, null);

        //when
        closeCallback.execute(update);

        //then
        Mockito.verify(botMessageService, Mockito.times(1)).updateUser(telegramUserService, update);
    }

    protected Update givenUpdate(long userId, long chatId) {
        Update update = new Update();
        Message message = Mockito.mock(Message.class);
        User from = Mockito.mock(User.class);

        Mockito.when(message.getChatId()).thenReturn(chatId);
        Mockito.when(message.getText()).thenReturn(CallbackNamesImpl.CLOSE.getName());
        Mockito.when(message.getMessageId()).thenReturn(TEST_MESSAGE_ID);
        Mockito.when(message.getFrom()).thenReturn(from);
        Mockito.when(from.getId()).thenReturn(userId);

        message.setFrom(from);
        update.setMessage(message);
        return update;
    }
}
