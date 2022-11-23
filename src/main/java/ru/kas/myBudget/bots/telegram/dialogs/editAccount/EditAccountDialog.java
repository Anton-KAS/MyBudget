package ru.kas.myBudget.bots.telegram.dialogs.editAccount;

import ru.kas.myBudget.bots.telegram.dialogs.DialogStepsContainer;
import ru.kas.myBudget.bots.telegram.dialogs.addAccount.AddAccountDialog;
import ru.kas.myBudget.bots.telegram.services.BotMessageService;
import ru.kas.myBudget.services.TelegramUserService;

public class EditAccountDialog extends AddAccountDialog {

    public EditAccountDialog(BotMessageService botMessageService, TelegramUserService telegramUserService,
                             DialogStepsContainer editAccountContainer) {
        super(botMessageService, telegramUserService, editAccountContainer);
    }
}
