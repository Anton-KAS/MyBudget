package komrachkov.anton.mybudget.bots.telegram.keyboards.dialogs.account;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import komrachkov.anton.mybudget.bots.telegram.keyboards.dialogs.DialogKeyboardImpl;
import komrachkov.anton.mybudget.bots.telegram.keyboards.util.InlineKeyboardBuilder;

import static komrachkov.anton.mybudget.bots.telegram.dialogs.account.AccountNames.DESCRIPTION;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

@Component
@Scope("prototype")
public class DescriptionKeyboard extends DialogKeyboardImpl {

    public InlineKeyboardMarkup getKeyboard() {
        InlineKeyboardBuilder inlineKeyboardBuilder = new InlineKeyboardBuilder();
        return inlineKeyboardBuilder
                .addRow().addNextButton(currentDialogName, DESCRIPTION.getName())
                .build();
    }
}
