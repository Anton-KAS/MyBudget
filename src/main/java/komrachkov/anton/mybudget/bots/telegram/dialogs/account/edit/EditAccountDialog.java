package komrachkov.anton.mybudget.bots.telegram.dialogs.account.edit;

import komrachkov.anton.mybudget.bots.telegram.dialogs.account.AccountDialog;
import komrachkov.anton.mybudget.services.TelegramUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static komrachkov.anton.mybudget.bots.telegram.dialogs.DialogNamesImpl.EDIT_ACCOUNT;

/**
 * @author Anton Komrachkov
 * @since (04.12.2022)
 */

@Component
public class EditAccountDialog extends AccountDialog {

    @Autowired
    public EditAccountDialog(TelegramUserService telegramUserService, EditContainer dialogContainer) {
        super(telegramUserService, dialogContainer);
    }

    @Override
    protected void setDialogName() {
        dialogName = EDIT_ACCOUNT.toString();
    }
}
