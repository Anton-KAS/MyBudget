package komrachkov.anton.mybudget.bots.telegram.keyboards.dialogs.account;

import komrachkov.anton.mybudget.bots.telegram.keyboards.dialogs.DialogKeyboardImpl;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

public abstract class ConfirmKeyboard extends DialogKeyboardImpl {

    @Override
    abstract public InlineKeyboardMarkup getKeyboard();
}
