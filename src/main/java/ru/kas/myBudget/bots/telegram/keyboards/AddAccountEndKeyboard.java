package ru.kas.myBudget.bots.telegram.keyboards;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import static ru.kas.myBudget.bots.telegram.callbacks.CallbackName.*;

public class AddAccountEndKeyboard implements Keyboard {
    public final String FROM = ADD_ACCOUNT_END.getCallbackName();
    public final String SAVE_BUTTON_TEXT = "Сохранить";
    public final String SAVE_BUTTON_CALLBACK = FROM + "_" + ADD_ACCOUNT_SAVE.getCallbackName();
    public final String CANCEL_BUTTON_TEXT = "Отменить";
    public final String CANCEL_BUTTON_CALLBACK = FROM + "_" + CANCEL_DIALOG.getCallbackName();

    public AddAccountEndKeyboard() {
    }

    public InlineKeyboardMarkup getKeyboard() {
        InlineKeyboardBuilder inlineKeyboardBuilder = new InlineKeyboardBuilder();
        return inlineKeyboardBuilder
                .addRow().addButton(SAVE_BUTTON_TEXT, SAVE_BUTTON_CALLBACK)
                .addRow().addButton(CANCEL_BUTTON_TEXT, CANCEL_BUTTON_CALLBACK)
                .build();
    }
}
