package komrachkov.anton.mybudget.bots.telegram.keyboards.callback;

import komrachkov.anton.mybudget.bots.telegram.keyboards.util.Keyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

public class NoKeyboard implements Keyboard {
    @Override
    public InlineKeyboardMarkup getKeyboard() {
        return null;
    }
}
