package komrachkov.anton.mybudget.bots.telegram.dialogs.account.add;

import komrachkov.anton.mybudget.bots.telegram.keyboards.dialogs.account.add.AddConfirmKeyboard;
import komrachkov.anton.mybudget.bots.telegram.texts.dialogs.account.AccountDialogText;
import komrachkov.anton.mybudget.services.TelegramUserService;
import komrachkov.anton.mybudget.bots.telegram.dialogs.account.ConfirmDialog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

@Component
public class AddConfirmDialog extends ConfirmDialog {

    @Autowired
    public AddConfirmDialog(TelegramUserService telegramUserService, AccountDialogText messageText, AddConfirmKeyboard keyboard) {
        super(telegramUserService, messageText, keyboard);
    }
}
