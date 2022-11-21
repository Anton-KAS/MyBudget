package ru.kas.myBudget.bots.telegram.dialogs.editAccount;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kas.myBudget.bots.telegram.dialogs.addAccount.AddAccountConfirmDialog;
import ru.kas.myBudget.bots.telegram.keyboards.Keyboard;
import ru.kas.myBudget.bots.telegram.keyboards.callback.EditAccountConfirmKeyboard;
import ru.kas.myBudget.bots.telegram.services.BotMessageService;
import ru.kas.myBudget.bots.telegram.texts.MessageText;
import ru.kas.myBudget.bots.telegram.util.UpdateParameter;
import ru.kas.myBudget.services.TelegramUserService;

public class EditAccountConfirmDialog extends AddAccountConfirmDialog {
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
