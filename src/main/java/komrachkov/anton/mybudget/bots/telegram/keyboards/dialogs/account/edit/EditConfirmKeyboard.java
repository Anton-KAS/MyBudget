package komrachkov.anton.mybudget.bots.telegram.keyboards.dialogs.account.edit;

import komrachkov.anton.mybudget.bots.telegram.keyboards.dialogs.account.ConfirmKeyboard;
import komrachkov.anton.mybudget.bots.telegram.keyboards.util.InlineKeyboardBuilder;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import komrachkov.anton.mybudget.bots.telegram.dialogs.util.DialogsMap;

import static komrachkov.anton.mybudget.bots.telegram.commands.CommandNamesImpl.ACCOUNTS;
import static komrachkov.anton.mybudget.bots.telegram.dialogs.util.DialogMapDefaultName.EDIT_ID;
import static komrachkov.anton.mybudget.bots.telegram.dialogs.account.AccountNames.CONFIRM;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

public class EditConfirmKeyboard extends ConfirmKeyboard {
    private final static String RETURN_TO = ACCOUNTS.getName();
    private long chatId;

    public EditConfirmKeyboard(String currentDialogName) {
        super(currentDialogName);
    }

    public InlineKeyboardMarkup getKeyboard() {
        InlineKeyboardBuilder inlineKeyboardBuilder = new InlineKeyboardBuilder();
        String idToDelete = DialogsMap.getDialogStepById(chatId, EDIT_ID.getId());

        return inlineKeyboardBuilder
                .addRow().addSaveButton(currentDialogName, CONFIRM.getName())
                .addRow().addDeleteButton(RETURN_TO, currentDialogName, CONFIRM.getName(), String.valueOf(idToDelete))
                .addRow().addCancelDialogButton(currentDialogName)
                .build();
    }

    public EditConfirmKeyboard setChatId(long chatId) {
        this.chatId = chatId;
        return this;
    }
}
