package komrachkov.anton.mybudget.bots.telegram.dialogs;

import komrachkov.anton.mybudget.bots.telegram.util.UpdateParameter;
import komrachkov.anton.mybudget.services.TelegramUserService;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import komrachkov.anton.mybudget.bots.telegram.dialogs.util.MainDialogImpl;
import komrachkov.anton.mybudget.bots.telegram.keyboards.dialogs.DeleteConfirmDialogKeyboard;
import komrachkov.anton.mybudget.bots.telegram.services.BotMessageService;
import komrachkov.anton.mybudget.bots.telegram.texts.dialogs.DeleteConfirmDialogText;
import komrachkov.anton.mybudget.bots.telegram.util.ExecuteMode;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

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
