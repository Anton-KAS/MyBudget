package ru.kas.myBudget.bots.telegram.keyboards;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public class MenuKeyboard implements Keyboard {
    public final String FROM = "menu";
    public final String ACCOUNT_BUTTON_TEXT = "Счета";
    public final String ACCOUNT_BUTTON_CALLBACK = FROM + "_accounts";

    public MenuKeyboard() {
    }

    public InlineKeyboardMarkup getKeyboard() {
        InlineKeyboardBuilder inlineKeyboardBuilder = new InlineKeyboardBuilder();
        return inlineKeyboardBuilder
                .addRow().addButton(ACCOUNT_BUTTON_TEXT, ACCOUNT_BUTTON_CALLBACK)
                .addRow().addButton(getCloseButton(FROM)).build();
    }
}
