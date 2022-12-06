package komrachkov.anton.mybudget.bots.telegram.keyboards.dialogs;

import komrachkov.anton.mybudget.bots.telegram.keyboards.util.Keyboard;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

@Component
public class CancelDialogKeyboard implements Keyboard {
    @Override
    public InlineKeyboardMarkup getKeyboard() {
        return null;
    }
}
