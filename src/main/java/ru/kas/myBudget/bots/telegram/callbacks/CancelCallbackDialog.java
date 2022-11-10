package ru.kas.myBudget.bots.telegram.callbacks;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kas.myBudget.bots.telegram.dialogs.DialogsMap;
import ru.kas.myBudget.bots.telegram.services.BotMessageService;
import ru.kas.myBudget.bots.telegram.texts.CancelDialogText;
import ru.kas.myBudget.bots.telegram.util.ExecuteMode;
import ru.kas.myBudget.services.TelegramUserService;

import java.util.Map;

public class CancelCallbackDialog implements Callback {

    private final BotMessageService botMessageService;
    private final TelegramUserService telegramUserService;
    private final Map<Long, Map<String, String>> dialogsMap;

    public CancelCallbackDialog(BotMessageService botMessageService, TelegramUserService telegramUserService) {
        this.botMessageService = botMessageService;
        this.telegramUserService = telegramUserService;
        this.dialogsMap = DialogsMap.getDialogsMap();
    }

    @Override
    public void execute(Update update) {
        String text = new CancelDialogText().getText();
        long chatId = getChatId(update);

        botMessageService.executeRemoveInlineKeyboard(update);
        botMessageService.executeSendMessage(chatId, text);

        checkUserInDb(telegramUserService, update);

        dialogsMap.remove(getUserId(update));
    }

    @Override
    public void execute(Update update, ExecuteMode executeMode) {
        execute(update);
    }
}
