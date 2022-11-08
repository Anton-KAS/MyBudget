package ru.kas.myBudget.bots.telegram.callbacks;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kas.myBudget.bots.telegram.services.BotMessageService;
import ru.kas.myBudget.bots.telegram.util.ExecuteMode;

public class UnknownCallback implements Callback {

    private final ExecuteMode executeMode;
    private final BotMessageService botMessageService;

    public final static String NO_MESSAGE = "Что-то пошло не так =(";

    public UnknownCallback(ExecuteMode executeMode, BotMessageService sendBotMessageService) {
        this.executeMode = executeMode;
        this.botMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        botMessageService.executeMessage(executeMode, getChatId(update), getMessageId(update), NO_MESSAGE, null);
    }

    @Override
    public void execute(Update update, ExecuteMode executeMode) {
        execute(update);
    }
}
