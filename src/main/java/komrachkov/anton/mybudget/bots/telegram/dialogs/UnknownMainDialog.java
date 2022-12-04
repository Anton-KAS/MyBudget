package komrachkov.anton.mybudget.bots.telegram.dialogs;

import komrachkov.anton.mybudget.bots.telegram.keyboards.UnknownKeyboard;
import komrachkov.anton.mybudget.bots.telegram.texts.UnknownText;
import komrachkov.anton.mybudget.services.TelegramUserService;
import komrachkov.anton.mybudget.bots.telegram.dialogs.util.MainDialog;
import komrachkov.anton.mybudget.bots.telegram.util.CommandControllerImpl;
import komrachkov.anton.mybudget.bots.telegram.util.ExecuteMode;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

public class UnknownMainDialog extends CommandControllerImpl implements MainDialog {

    public UnknownMainDialog(TelegramUserService telegramUserService, UnknownText messageText, UnknownKeyboard keyboard) {
        super(telegramUserService, messageText, keyboard);
    }

    @Override
    public void setDefaultExecuteMode() {
        defaultExecuteMode = ExecuteMode.SEND;
    }
}
