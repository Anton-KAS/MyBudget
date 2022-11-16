package ru.kas.myBudget.bots.telegram.dialogs.addAccount;

import ru.kas.myBudget.bots.telegram.dialogs.DialogImpl;
import ru.kas.myBudget.bots.telegram.dialogs.DialogsMap;
import ru.kas.myBudget.bots.telegram.keyboards.Keyboard;
import ru.kas.myBudget.bots.telegram.services.BotMessageService;
import ru.kas.myBudget.bots.telegram.texts.MessageText;
import ru.kas.myBudget.services.TelegramUserService;

public class ConfirmDialog extends DialogImpl {
    private final static String ASK_TEXT = "Всё готово! Сохранить?";

    public ConfirmDialog(BotMessageService botMessageService, TelegramUserService telegramUserService,
                         MessageText messageText, Keyboard keyboard, DialogsMap dialogsMap) {
        super(botMessageService, telegramUserService, messageText, keyboard, dialogsMap, ASK_TEXT);
    }
}
