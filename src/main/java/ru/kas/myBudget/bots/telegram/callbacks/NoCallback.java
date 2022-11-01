package ru.kas.myBudget.bots.telegram.callbacks;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kas.myBudget.bots.telegram.services.SendBotMessageService;

public class NoCallback implements Callback {

    private final SendBotMessageService sendBotMessageService;

    public final static String NO_MESSAGE = "Что-то пошло не так =(";

    public NoCallback(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        sendBotMessageService.editMessage(getChatId(update), getMessageId(update), NO_MESSAGE);
    }
}
