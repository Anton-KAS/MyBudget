package ru.kas.myBudget.bots.telegram.dialogs.account.editAccount;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kas.myBudget.bots.telegram.dialogs.account.ConfirmDialog;
import ru.kas.myBudget.bots.telegram.keyboards.util.Keyboard;
import ru.kas.myBudget.bots.telegram.keyboards.accountDialog.editAccountDialog.EditAccountConfirmKeyboard;
import ru.kas.myBudget.bots.telegram.services.BotMessageService;
import ru.kas.myBudget.bots.telegram.texts.MessageText;
import ru.kas.myBudget.bots.telegram.util.UpdateParameter;
import ru.kas.myBudget.services.TelegramUserService;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

public class EditAccountConfirmDialog extends ConfirmDialog {
    private final EditAccountConfirmKeyboard editAccountConfirmKeyboard = (EditAccountConfirmKeyboard) keyboard;

    public EditAccountConfirmDialog(BotMessageService botMessageService, TelegramUserService telegramUserService,
                                    MessageText messageText, Keyboard keyboard) {
        super(botMessageService, telegramUserService, messageText, keyboard);

    }

    @Override
    public void setData(Update update) {
        editAccountConfirmKeyboard.setChatId(UpdateParameter.getChatId(update));
        super.setData(update);
    }
}
