package ru.kas.myBudget.bots.telegram.keyboards;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public class AccountKeyboard implements Keyboard{
    public final String FROM = "account";
    public final String TO = "menu";
    public final String ADD_ACCOUNT_BUTTON_TEXT = "+ Добавить";
    public final String ADD_ACCOUNT_BUTTON_CALLBACK = FROM + "_addAccount";
    @Override
    public InlineKeyboardMarkup getKeyboard() {
        InlineKeyboardBuilder inlineKeyboardBuilder = new InlineKeyboardBuilder();
        return inlineKeyboardBuilder
                .addRow().addButton(ADD_ACCOUNT_BUTTON_TEXT, ADD_ACCOUNT_BUTTON_CALLBACK)
                .addRow().addButton(getReturnButton(FROM, TO)).build();
    }
}
