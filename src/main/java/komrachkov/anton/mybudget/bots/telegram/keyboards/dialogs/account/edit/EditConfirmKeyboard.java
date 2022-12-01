package komrachkov.anton.mybudget.bots.telegram.keyboards.dialogs.account.edit;

import komrachkov.anton.mybudget.bots.telegram.keyboards.dialogs.account.ConfirmKeyboard;
import komrachkov.anton.mybudget.bots.telegram.keyboards.util.InlineKeyboardBuilder;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import komrachkov.anton.mybudget.bots.telegram.dialogs.util.DialogsState;

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
        inlineKeyboardBuilder.addRow().addSaveButton(currentDialogName, CONFIRM.getName());

        DialogsState.getDialogStepById(chatId, EDIT_ID.getId()).ifPresent(idToDelete
                -> inlineKeyboardBuilder.addRow().addDeleteButton(RETURN_TO, currentDialogName, CONFIRM.getName(), idToDelete));

        return inlineKeyboardBuilder
                .addRow().addCancelDialogButton(currentDialogName)
                .build();
    }

    public EditConfirmKeyboard setChatId(long chatId) {
        this.chatId = chatId;
        return this;
    }
}
