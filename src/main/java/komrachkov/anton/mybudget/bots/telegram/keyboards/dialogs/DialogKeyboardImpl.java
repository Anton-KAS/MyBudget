package komrachkov.anton.mybudget.bots.telegram.keyboards.dialogs;

import komrachkov.anton.mybudget.bots.telegram.keyboards.util.Keyboard;
import komrachkov.anton.mybudget.bots.telegram.keyboards.util.DialogKeyboard;

import static komrachkov.anton.mybudget.bots.telegram.callbacks.util.CallbackType.DIALOG;

/**
 * @author Anton Komrachkov
 * @since 0.2
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
