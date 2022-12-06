package komrachkov.anton.mybudget.bots.telegram.services;

import komrachkov.anton.mybudget.bots.telegram.bot.TelegramBot;
import komrachkov.anton.mybudget.services.TelegramUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import komrachkov.anton.mybudget.bots.telegram.util.ExecuteMode;

import static komrachkov.anton.mybudget.bots.telegram.services.BotMessageServiceImpl.CACHE_TIME;

/**
 * Unit-tests for {@link BotMessageService}
 * @author Anton Komrachkov
 * @since 0.2
 */

@DisplayName("Unit-level testing for BotMessageService")
public class BotMessageServiceTest {
    protected final static long TEST_USER_ID = 123456789L;
    protected final static long TEST_CHAT_ID = 987654321L;
    protected final static int TEST_MESSAGE_ID = 123;
    protected final static String TEST_CALLBACK_QUERY_ID = "11223344";
    protected final static String TEST_TEXT = "Test Text";

    private final TelegramBot telegramBotMock = Mockito.mock(TelegramBot.class);
    private final TelegramUserService telegramUserServiceMock = Mockito.mock(TelegramUserService.class);
    private BotMessageService botMessageService;

    @BeforeEach
    public void init() {
        botMessageService = new BotMessageServiceImpl(telegramBotMock, telegramUserServiceMock);
    }

    @Test
    public void shouldProperlySendMessage() throws TelegramApiException {
        //given
        SendMessage sendMessage = getExpectedSendMessage();
        Update update = givenUpdate();

        //when
        botMessageService.executeMessage(ExecuteMode.SEND, update, TEST_TEXT, null);

        //then
        Mockito.verify(telegramBotMock).execute(sendMessage);
    }

    /**
     * @author Anton Komrachkov
     * @since 0.4 (05.12.2022)
     */
    private SendMessage getExpectedSendMessage() {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(TEST_CHAT_ID);
        sendMessage.setText(TEST_TEXT);
        sendMessage.enableHtml(true);
        sendMessage.setDisableNotification(true);
        return sendMessage;
    }

    /**
     * @author Anton Komrachkov
     * @since 0.4 (05.12.2022)
     */
    @Test
    public void shouldProperlyEditMessage() throws TelegramApiException {
        //given
        EditMessageText editMessage = getExpectedEditMessage();
        Update update = givenUpdate();

        //when
        botMessageService.executeMessage(ExecuteMode.EDIT, update, TEST_TEXT, null);

        //then
        Mockito.verify(telegramBotMock).execute(editMessage);
    }

    /**
     * @author Anton Komrachkov
     * @since 0.4 (05.12.2022)
     */
    private EditMessageText getExpectedEditMessage() {
        EditMessageText editMessage = new EditMessageText();
        editMessage.setChatId(TEST_CHAT_ID);
        editMessage.setText(TEST_TEXT);
        editMessage.setMessageId(TEST_MESSAGE_ID);
        editMessage.enableHtml(true);
        return editMessage;
    }

    /**
     * @author Anton Komrachkov
     * @since 0.4 (05.12.2022)
     */
    @Test
    public void shouldProperlyDeleteMessage() throws TelegramApiException {
        //given
        DeleteMessage deleteMessage = getExpectedDeleteMessage();
        Update update = givenUpdate();

        //when
        botMessageService.executeMessage(ExecuteMode.DELETE, update, null, null);

        //then
        Mockito.verify(telegramBotMock).execute(deleteMessage);
    }

    /**
     * @author Anton Komrachkov
     * @since 0.4 (05.12.2022)
     */
    private DeleteMessage getExpectedDeleteMessage() {
        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setChatId(TEST_CHAT_ID);
        deleteMessage.setMessageId(TEST_MESSAGE_ID);
        return deleteMessage;
    }


    /**
     * @author Anton Komrachkov
     * @since 0.4 (05.12.2022)
     */
    @Test
    public void shouldNotExecutePopupMessage() throws TelegramApiException {
        //given
        AnswerCallbackQuery answerCallbackQuery = getExpectedPopupMessage();
        Update update = givenUpdate();

        //when
        botMessageService.executeMessage(ExecuteMode.POPUP, update, TEST_TEXT, null);

        //then
        Mockito.verify(telegramBotMock, Mockito.times(0)).execute(answerCallbackQuery);
    }

    @Test
    public void shouldProperlyExecutePopupMessage() throws TelegramApiException {
        //given
        AnswerCallbackQuery answerCallbackQuery = getExpectedPopupMessage();
        Update update = givenCallbackUpdate();

        //when
        botMessageService.executeMessage(ExecuteMode.POPUP, update, TEST_TEXT, null);

        //then
        Mockito.verify(telegramBotMock).execute(answerCallbackQuery);
    }

    /**
     * @author Anton Komrachkov
     * @since 0.4 (05.12.2022)
     */
    private AnswerCallbackQuery getExpectedPopupMessage() {
        AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
        answerCallbackQuery.setCallbackQueryId(TEST_CALLBACK_QUERY_ID);
        answerCallbackQuery.setText(TEST_TEXT);
        answerCallbackQuery.setShowAlert(false);
        answerCallbackQuery.setCacheTime(CACHE_TIME);
        return answerCallbackQuery;
    }

    /**
     * @author Anton Komrachkov
     * @since 0.4 (05.12.2022)
     */
    protected Update givenUpdate() {
        Update update = new Update();
        Message message = Mockito.mock(Message.class);
        User from = Mockito.mock(User.class);

        Mockito.when(message.getChatId()).thenReturn(TEST_CHAT_ID);
        Mockito.when(message.getText()).thenReturn(TEST_TEXT);
        Mockito.when(message.getFrom()).thenReturn(from);
        Mockito.when(message.getMessageId()).thenReturn(TEST_MESSAGE_ID);
        Mockito.when(from.getId()).thenReturn(TEST_USER_ID);

        message.setFrom(from);
        update.setMessage(message);
        return update;
    }

    /**
     * @author Anton Komrachkov
     * @since 0.4 (05.12.2022)
     */
    protected Update givenCallbackUpdate() {
        Update update = new Update();
        Message message = Mockito.mock(Message.class);
        User from = Mockito.mock(User.class);
        CallbackQuery callbackQuery = Mockito.mock(CallbackQuery.class);

        Mockito.when(message.getChatId()).thenReturn(TEST_CHAT_ID);
        Mockito.when(message.getText()).thenReturn(TEST_TEXT);
        Mockito.when(message.getFrom()).thenReturn(from);
        Mockito.when(message.getMessageId()).thenReturn(TEST_MESSAGE_ID);
        Mockito.when(from.getId()).thenReturn(TEST_USER_ID);
        Mockito.when(callbackQuery.getId()).thenReturn(TEST_CALLBACK_QUERY_ID);

        message.setFrom(from);
        update.setCallbackQuery(callbackQuery);
        update.setMessage(message);
        return update;
    }
}
