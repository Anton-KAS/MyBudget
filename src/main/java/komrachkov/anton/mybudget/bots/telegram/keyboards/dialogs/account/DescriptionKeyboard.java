package komrachkov.anton.mybudget.bots.telegram.keyboards.dialogs.account;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import komrachkov.anton.mybudget.bots.telegram.keyboards.dialogs.DialogKeyboardImpl;
import komrachkov.anton.mybudget.bots.telegram.keyboards.util.InlineKeyboardBuilder;

import static komrachkov.anton.mybudget.bots.telegram.dialogs.account.AccountNames.DESCRIPTION;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

public class DescriptionKeyboard extends DialogKeyboardImpl {
    public DescriptionKeyboard(String currentDialogName) {
        super(currentDialogName);
    }

    public InlineKeyboardMarkup getKeyboard() {
        InlineKeyboardBuilder inlineKeyboardBuilder = new InlineKeyboardBuilder();
        return inlineKeyboardBuilder
                .addRow().addNextButton(currentDialogName, DESCRIPTION.getName())
                .build();
    }
}
