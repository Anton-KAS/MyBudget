package komrachkov.anton.mybudget.bots.telegram.keyboards.dialogs.account;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import komrachkov.anton.mybudget.bots.telegram.keyboards.dialogs.DialogKeyboardImpl;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

@Component
@Scope("prototype")
public class TitleKeyboard extends DialogKeyboardImpl {

    @Override
    public InlineKeyboardMarkup getKeyboard() {
        return null;
    }
}
