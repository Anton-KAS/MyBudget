package komrachkov.anton.mybudget.bots.telegram.callbacks;

import komrachkov.anton.mybudget.bots.telegram.services.BotMessageService;
import komrachkov.anton.mybudget.services.TelegramUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

/**
 * Unit-tests for {@link CloseCallback}
 * @since 0.2
 * @author Anton Komrachkov
 */

@DisplayName("Unit-level testing for callbacks.CloseCallback")
public class CloseCallbackTest {
    protected static long TEST_USER_ID = 123456789L;
    protected static long TEST_CHAT_ID = TEST_USER_ID;
    protected static int TEST_MESSAGE_ID = 123;

    protected BotMessageService botMessageService = Mockito.mock(BotMessageService.class);
    protected TelegramUserService telegramUserService = Mockito.mock(TelegramUserService.class);

    @BeforeEach
    public void beforeEach() {
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
