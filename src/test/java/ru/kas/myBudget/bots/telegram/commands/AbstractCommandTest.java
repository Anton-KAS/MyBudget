package ru.kas.myBudget.bots.telegram.commands;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.kas.myBudget.bots.telegram.bot.TelegramBot;
import ru.kas.myBudget.bots.telegram.services.SendBotMessageService;
import ru.kas.myBudget.bots.telegram.services.SendBotMessageServiceImpl;
import ru.kas.myBudget.services.TelegramUserService;

abstract class AbstractCommandTest {

    protected TelegramBot telegramBot = Mockito.mock(TelegramBot.class);
    protected SendBotMessageService sendBotMessageService = new SendBotMessageServiceImpl(telegramBot);
    protected TelegramUserService telegramUserService = Mockito.mock(TelegramUserService.class);

    abstract String getCommandName();

    abstract String getCommandMessage();

    abstract Command getCommand();

    @Test
    public void shouldProperlyExecuteCommand() throws TelegramApiException {
        //given
        long userId = 123456789L;
        long chatId = userId;

        Update update = new Update();
        Message message = Mockito.mock(Message.class);
        User from = Mockito.mock(User.class);

        Mockito.when(message.getChatId()).thenReturn(chatId);
        Mockito.when(message.getText()).thenReturn(getCommandName());
        Mockito.when(message.getFrom()).thenReturn(from);
        Mockito.when(from.getId()).thenReturn(userId);

        message.setFrom(from);
        update.setMessage(message);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(getCommandMessage());
        sendMessage.enableHtml(true);

        //when
        getCommand().execute(update);

        //then
        Mockito.verify(telegramBot).execute(sendMessage);
    }
}
