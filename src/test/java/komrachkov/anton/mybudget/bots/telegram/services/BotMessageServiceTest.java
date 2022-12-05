package komrachkov.anton.mybudget.bots.telegram.services;

import komrachkov.anton.mybudget.bots.telegram.bot.TelegramBot;
import komrachkov.anton.mybudget.services.TelegramUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import komrachkov.anton.mybudget.bots.telegram.util.ExecuteMode;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

@DisplayName("Unit-level testing for BotMessageService")
public class BotMessageServiceTest {

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
        long chatId = 123456789L;
        String message = "test message";

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);
        sendMessage.enableHtml(true);
        sendMessage.setDisableNotification(true);

        //when
        botMessageService.executeMessage(ExecuteMode.SEND, null, message, null);

        //then
        Mockito.verify(telegramBotMock).execute(sendMessage);
    }
}
