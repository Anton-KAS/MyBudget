package komrachkov.anton.mybudget.bots.telegram.dialogs.account.edit;

import komrachkov.anton.mybudget.bots.telegram.texts.MessageText;
import komrachkov.anton.mybudget.bots.telegram.util.UpdateParameter;
import komrachkov.anton.mybudget.services.TelegramUserService;
import org.telegram.telegrambots.meta.api.objects.Update;
import komrachkov.anton.mybudget.bots.telegram.dialogs.account.ConfirmDialog;
import komrachkov.anton.mybudget.bots.telegram.keyboards.util.Keyboard;
import komrachkov.anton.mybudget.bots.telegram.keyboards.dialogs.account.edit.EditConfirmKeyboard;
import komrachkov.anton.mybudget.bots.telegram.services.BotMessageService;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

public class EditConfirmDialog extends ConfirmDialog {
    private final EditConfirmKeyboard editConfirmKeyboard = (EditConfirmKeyboard) keyboard;

    public EditConfirmDialog(BotMessageService botMessageService, TelegramUserService telegramUserService,
                             MessageText messageText, Keyboard keyboard) {
        super(botMessageService, telegramUserService, messageText, keyboard);

    }

    @Override
    public void setData(Update update) {
        editConfirmKeyboard.setChatId(UpdateParameter.getChatId(update));
        super.setData(update);
    }
}
