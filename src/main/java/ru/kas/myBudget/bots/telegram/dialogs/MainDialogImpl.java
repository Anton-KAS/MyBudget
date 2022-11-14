package ru.kas.myBudget.bots.telegram.dialogs;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kas.myBudget.bots.telegram.services.BotMessageService;
import ru.kas.myBudget.bots.telegram.util.ExecuteMode;
import ru.kas.myBudget.services.TelegramUserService;

public abstract class MainDialogImpl implements MainDialog {
    protected final BotMessageService botMessageService;
    protected final TelegramUserService telegramUserService;
    protected final DialogsMap dialogsMap;

    public MainDialogImpl(BotMessageService botMessageService, TelegramUserService telegramUserService,
                          DialogsMap dialogsMap) {
        this.botMessageService = botMessageService;
        this.telegramUserService = telegramUserService;
        this.dialogsMap = dialogsMap;
    }

    @Override
    public void execute(Update update) {
    }

    @Override
    public void execute(Update update, ExecuteMode executeMode) {
    }
}
