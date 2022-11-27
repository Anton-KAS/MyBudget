package ru.kas.myBudget.bots.telegram.dialogs.account.addAccount;

import ru.kas.myBudget.bots.telegram.dialogs.account.ConfirmDialog;
import ru.kas.myBudget.bots.telegram.keyboards.util.Keyboard;
import ru.kas.myBudget.bots.telegram.services.BotMessageService;
import ru.kas.myBudget.bots.telegram.texts.MessageText;
import ru.kas.myBudget.services.TelegramUserService;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

public class AddAccountConfirmDialog extends ConfirmDialog {

    public AddAccountConfirmDialog(BotMessageService botMessageService, TelegramUserService telegramUserService,
                                   MessageText messageText, Keyboard keyboard) {
        super(botMessageService, telegramUserService, messageText, keyboard);
    }
}
