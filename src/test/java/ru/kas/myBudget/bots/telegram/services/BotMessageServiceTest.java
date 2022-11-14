package ru.kas.myBudget.bots.telegram.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.kas.myBudget.bots.telegram.bot.TelegramBot;
import ru.kas.myBudget.bots.telegram.util.ExecuteMode;

@DisplayName("Unit-level testing for BotMessageService")
public class BotMessageServiceTest {

    private TelegramBot telegramBot;
    private BotMessageService botMessageService;

    @BeforeEach
    public void init() {
        telegramBot = Mockito.mock(TelegramBot.class);
        botMessageService = new BotMessageServiceImpl(telegramBot);
    }

    @Test
    public void shouldProperlySendMessage() throws TelegramApiException {
        //given
        long chatId = 123456789L;
        String message = "test message";

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);
        sendMessage.enableHtml(true);

        //when
        botMessageService.executeMessage(ExecuteMode.SEND, chatId, null, message, null);

        //then
        Mockito.verify(telegramBot).execute(sendMessage);
    }
}
