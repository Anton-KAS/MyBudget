package ru.kas.myBudget.bots.telegram.dialogs.AddAccount;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.kas.myBudget.bots.telegram.callbacks.Callback;
import ru.kas.myBudget.bots.telegram.dialogs.Dialog;
import ru.kas.myBudget.bots.telegram.dialogs.DialogsMap;
import ru.kas.myBudget.bots.telegram.keyboards.AddAccount.ConfirmKeyboard;
import ru.kas.myBudget.bots.telegram.services.BotMessageService;
import ru.kas.myBudget.bots.telegram.texts.AddAccountText;
import ru.kas.myBudget.bots.telegram.util.ExecuteMode;
import ru.kas.myBudget.models.TelegramUser;
import ru.kas.myBudget.services.TelegramUserService;

import java.util.Map;
import java.util.Optional;

import static ru.kas.myBudget.bots.telegram.dialogs.AddAccount.AddAccountName.CURRENT_DIALOG_STEP;

public class ConfirmDialog implements Dialog, Callback {
    private final BotMessageService botMessageService;
    private final TelegramUserService telegramUserService;
    private final Map<Long, Map<String, String>> dialogsMap;
    private final static String ASK_TEXT = "Всё готово! Сохранить?";

    public ConfirmDialog(BotMessageService botMessageService, TelegramUserService telegramUserService) {
        this.botMessageService = botMessageService;
        this.telegramUserService = telegramUserService;
        this.dialogsMap = DialogsMap.getDialogsMap();
    }

    @Override
    public void execute(Update update) {
        int dialogStep = Integer.parseInt(dialogsMap.get(getUserId(update)).get(CURRENT_DIALOG_STEP.getDialogId()));
        long userId = getUserId(update);

        ExecuteMode executeMode = getExecuteMode(update, dialogStep);
        String text = new AddAccountText(userId).getText();
        InlineKeyboardMarkup inlineKeyboardMarkup = new ConfirmKeyboard().getKeyboard();

        sendAndUpdateUser(telegramUserService, botMessageService, update, executeMode, String.format(text, ASK_TEXT),
                inlineKeyboardMarkup);
    }

    @Override
    public boolean commit(Update update) {
        return true;
    }
}
