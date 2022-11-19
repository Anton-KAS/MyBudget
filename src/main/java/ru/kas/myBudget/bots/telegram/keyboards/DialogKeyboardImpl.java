package ru.kas.myBudget.bots.telegram.keyboards;

import static ru.kas.myBudget.bots.telegram.callbacks.CallbackType.DIALOG;

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
