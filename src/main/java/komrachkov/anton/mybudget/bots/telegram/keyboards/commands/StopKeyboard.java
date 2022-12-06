package komrachkov.anton.mybudget.bots.telegram.keyboards.commands;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import komrachkov.anton.mybudget.bots.telegram.keyboards.util.Keyboard;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

@Component
public class StopKeyboard implements Keyboard {
    @Override
    public InlineKeyboardMarkup getKeyboard() {
        return null;
    }
}
