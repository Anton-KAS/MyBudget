package ru.kas.myBudget.bots.telegram.commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.kas.myBudget.bots.telegram.bot.TelegramBot;
import ru.kas.myBudget.bots.telegram.keyboards.Keyboard;
import ru.kas.myBudget.bots.telegram.services.BotMessageService;
import ru.kas.myBudget.bots.telegram.services.BotMessageServiceImpl;
import ru.kas.myBudget.bots.telegram.texts.MessageText;
import ru.kas.myBudget.bots.telegram.util.ExecuteMode;
import ru.kas.myBudget.services.TelegramUserService;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

abstract class AbstractCommandTest {

    protected TelegramBot telegramBot = Mockito.mock(TelegramBot.class);
    protected BotMessageService botMessageService = new BotMessageServiceImpl(telegramBot);
    protected TelegramUserService telegramUserService = Mockito.mock(TelegramUserService.class);
    protected Keyboard keyboard = Mockito.mock(Keyboard.class);
    protected MessageText messageText = Mockito.mock(MessageText.class);
    protected long userId;
    protected long chatId;

    abstract String getCommandName();

    abstract String getCommandMessage();

    abstract Command getCommand();

    @BeforeEach
    public void beforeEach() {
        this.userId = 123456789L;
        this.chatId = userId;
        Mockito.when(keyboard.getKeyboard()).thenReturn(null);
        Mockito.when(messageText.getText()).thenReturn("Test Text");
        Mockito.when(messageText.setUserId(userId)).thenReturn(messageText);
    }

    @Test
    public void shouldProperlyExecuteCommand() throws TelegramApiException {
        //given
        Update update = givenUpdate(userId, chatId);
        SendMessage sendMessage = givenSendMessage(chatId);

        //when
        getCommand().execute(update);

        //then
        Mockito.verify(telegramBot).execute(sendMessage);
    }

    @Test
    public void shouldProperlyExecuteCommandWithSendExecuteMode() throws TelegramApiException {
        //given
        Update update = givenUpdate(userId, chatId);
        SendMessage sendMessage = givenSendMessage(chatId);
        ExecuteMode executeMode = ExecuteMode.SEND;

        //when
        getCommand().execute(update, executeMode);

        //then
        Mockito.verify(telegramBot).execute(sendMessage);
    }

    @ParameterizedTest
    @MethodSource("source")
    public void ShouldPropertyExtractMessageText(Update update, String expectedText) {
        //when
        String textResult = getCommand().getMessageText(update);

        //then
        assertEquals(expectedText, textResult);
    }

    private static Stream<Arguments> source() {
        return Stream.of(
                Arguments.of(getUpdateWithText("Test Text"), "Test Text"),
                Arguments.of(getUpdateWithText("/command"), "/command"),
                Arguments.of(getCallbackUpdateWithData("Test Data"), null),
                Arguments.of(getCallbackUpdateWithData("test_data"), null)
        );
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

    private SendMessage givenSendMessage(long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(getCommandMessage());
        sendMessage.enableHtml(true);
        return sendMessage;
    }

    private static Update getUpdateWithText(String text) {
        Update update = new Update();
        Message message = new Message();
        message.setText(text);
        update.setMessage(message);
        return update;
    }

    private static Update getCallbackUpdateWithData(String data) {
        Update update = new Update();
        CallbackQuery callbackQuery = new CallbackQuery();
        callbackQuery.setData(data);
        update.setCallbackQuery(callbackQuery);
        return update;
    }
}
