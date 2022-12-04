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

    protected final String CALLBACK_FORMAT = DIALOG.getId() + "_%s_%s_%s_%s";

    public DialogKeyboardImpl() {
    }

    @Override
    public Keyboard setUserId(long userId) {
        this.userId = userId;
        return this;
    }

    @Override
    public Keyboard setDialogName(String dialogName) {
        this.currentDialogName = dialogName;
//        this.callbackPattern = String.format(callbackPattern, currentDialogName, currentDialogName, "%s", "%s");
        return this;
    }
}
