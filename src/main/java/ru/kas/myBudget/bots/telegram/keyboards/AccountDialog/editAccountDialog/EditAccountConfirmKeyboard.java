package ru.kas.myBudget.bots.telegram.keyboards.AccountDialog.editAccountDialog;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.kas.myBudget.bots.telegram.dialogs.util.DialogsMap;
import ru.kas.myBudget.bots.telegram.keyboards.AccountDialog.ConfirmKeyboard;
import ru.kas.myBudget.bots.telegram.keyboards.util.InlineKeyboardBuilder;

import static ru.kas.myBudget.bots.telegram.commands.CommandNamesImpl.ACCOUNTS;
import static ru.kas.myBudget.bots.telegram.dialogs.util.DialogMapDefaultName.EDIT_ID;
import static ru.kas.myBudget.bots.telegram.dialogs.account.AccountNames.CONFIRM;

/**
 * @since 0.2
 * @author Anton Komrachkov
 */

public class EditAccountConfirmKeyboard extends ConfirmKeyboard {
    private final static String RETURN_TO = ACCOUNTS.getName();
    private long chatId;

    public EditAccountConfirmKeyboard(String currentDialogName) {
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

    public EditAccountConfirmKeyboard setChatId(long chatId) {
        this.chatId = chatId;
        return this;
    }
}
