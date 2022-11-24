package ru.kas.myBudget.bots.telegram.keyboards;

import ru.kas.myBudget.bots.telegram.keyboards.util.DialogKeyboard;
import ru.kas.myBudget.bots.telegram.keyboards.util.Keyboard;

import static ru.kas.myBudget.bots.telegram.callbacks.util.CallbackType.DIALOG;

/**
 * @since 0.2
 * @author Anton Komrachkov
 */

public abstract class DialogKeyboardImpl implements DialogKeyboard {
    protected String currentDialogName;
    protected Long userId;

    protected String callbackPattern = DIALOG.getId() + "_%s_%s_%s_%s";

    public DialogKeyboardImpl(String currentDialogName) {
        this.currentDialogName = currentDialogName;
        this.callbackPattern = String.format(callbackPattern, currentDialogName, currentDialogName, "%s", "%s");
    }

    @Override
    public Keyboard setUserId(long userId) {
        this.userId = userId;
        return this;
    }
}
