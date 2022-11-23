package ru.kas.myBudget.bots.telegram.dialogs;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.kas.myBudget.bots.telegram.dialogs.util.MainDialogImpl;
import ru.kas.myBudget.bots.telegram.keyboards.DeleteConfirmDialogKeyboard;
import ru.kas.myBudget.bots.telegram.services.BotMessageService;
import ru.kas.myBudget.bots.telegram.texts.dialogs.DeleteConfirmDialogText;
import ru.kas.myBudget.bots.telegram.util.ExecuteMode;
import ru.kas.myBudget.bots.telegram.util.UpdateParameter;
import ru.kas.myBudget.services.TelegramUserService;

public class DeleteConfirmDialog extends MainDialogImpl {
    private final static DeleteConfirmDialogKeyboard keyboard = new DeleteConfirmDialogKeyboard();
    private final static DeleteConfirmDialogText messageText = new DeleteConfirmDialogText();

    public DeleteConfirmDialog(BotMessageService botMessageService, TelegramUserService telegramUserService) {
        super(botMessageService, telegramUserService);
    }

    @Override
    public void execute(Update update) {
        String text = messageText.setUserId(UpdateParameter.getUserId(update)).getText();
        InlineKeyboardMarkup inlineKeyboardMarkup = keyboard.setUpdate(update).getKeyboard();
        botMessageService.executeAndUpdateUser(telegramUserService, update, ExecuteMode.EDIT,
                text, inlineKeyboardMarkup);
    }
}
