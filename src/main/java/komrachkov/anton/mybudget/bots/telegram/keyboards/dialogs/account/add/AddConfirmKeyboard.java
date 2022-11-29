package komrachkov.anton.mybudget.bots.telegram.keyboards.dialogs.account.add;

import komrachkov.anton.mybudget.bots.telegram.keyboards.dialogs.account.ConfirmKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import komrachkov.anton.mybudget.bots.telegram.keyboards.util.InlineKeyboardBuilder;

import static komrachkov.anton.mybudget.bots.telegram.dialogs.account.AccountNames.CONFIRM;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

public class AddConfirmKeyboard extends ConfirmKeyboard {

    public AddConfirmKeyboard(String currentDialogName) {
        super(currentDialogName);
    }

    public InlineKeyboardMarkup getKeyboard() {
        InlineKeyboardBuilder inlineKeyboardBuilder = new InlineKeyboardBuilder();
        return inlineKeyboardBuilder
                .addRow().addSaveButton(currentDialogName, CONFIRM.getName())
                .addRow().addCancelDialogButton(currentDialogName)
                .build();
    }
}
