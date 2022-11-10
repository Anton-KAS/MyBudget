package ru.kas.myBudget.bots.telegram.dialogs;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kas.myBudget.bots.telegram.callbacks.Callback;
import ru.kas.myBudget.bots.telegram.services.BotMessageService;

public class UnknownDialog implements Dialog, Callback {
    private final BotMessageService botMessageService;

    public final static String NO_MESSAGE = "Что-то пошло не так =(";

    public UnknownDialog(BotMessageService botMessageService) {
        this.botMessageService = botMessageService;
    }

    @Override
    public void execute(Update update) {
        botMessageService.executeMessage(getExecuteMode(update, null),
                getChatId(update), getMessageId(update), NO_MESSAGE, null);
    }

    @Override
    public boolean commit(Update update) {
        return true;
    }
}
