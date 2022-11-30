package komrachkov.anton.mybudget.bots.telegram.dialogs;

import komrachkov.anton.mybudget.services.TelegramUserService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.*;
import komrachkov.anton.mybudget.bots.telegram.services.BotMessageService;
import komrachkov.anton.mybudget.bots.telegram.dialogs.util.DialogsState;

import java.util.Map;

import static komrachkov.anton.mybudget.bots.telegram.callbacks.util.CallbackType.DIALOG;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

public abstract class AbstractMainDialogImplTest {
    protected final static long TEST_USER_ID = 123456789L;
    protected final static long TEST_CHAT_ID = 987654321L;
    protected final static int TEST_MESSAGE_ID = 1111111;
    protected final static String TEST_TEXT = "TEST TEXT";

    private final static String CALLBACK_PATTERN = "%s_%s_%s_%s_%s";
    protected final static String CALLBACK_DIALOG_PATTERN =
            String.format(CALLBACK_PATTERN, DIALOG.getId(), "%s", "%s", "%s", "%s");

    protected final BotMessageService botMessageServiceMock = Mockito.mock(BotMessageService.class);
    protected final TelegramUserService telegramUserServiceMock = Mockito.mock(TelegramUserService.class);

    protected final DialogsState dialogsStateMock = Mockito.mock(DialogsState.class);
    protected static Map<String, String> dialogMap;

    @BeforeEach
    public void beforeEach() {
    }

    protected static Update getCommandAndTextUpdate(String text) {
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

    protected static Update getCallbackUpdate(String data) {
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
