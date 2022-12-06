package komrachkov.anton.mybudget.bots.telegram.dialogs.account.add;

import komrachkov.anton.mybudget.bots.telegram.dialogs.account.AccountDialog;
import komrachkov.anton.mybudget.bots.telegram.util.CommandNames;
import komrachkov.anton.mybudget.services.TelegramUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static komrachkov.anton.mybudget.bots.telegram.dialogs.DialogNamesImpl.ADD_ACCOUNT;

/**
 * @author Anton Komrachkov
 * @since (04.12.2022)
 */

@Component
public class AddAccountDialog extends AccountDialog {

    @Autowired
    public AddAccountDialog(TelegramUserService telegramUserService, AddContainer dialogContainer) {
        super(telegramUserService, dialogContainer);
    }

    @Override
    protected void setDialogName() {
        dialogName = ADD_ACCOUNT.toString();
    }
}
