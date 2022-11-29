package komrachkov.anton.mybudget.bots.telegram.keyboards.dialogs.account;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import komrachkov.anton.mybudget.bots.telegram.keyboards.dialogs.DialogKeyboardImpl;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

public class SaveKeyboard extends DialogKeyboardImpl {
    public SaveKeyboard(String currentDialogName) {
        super(currentDialogName);
    }

    @Override
    public InlineKeyboardMarkup getKeyboard() {
        return null;
    }
}
