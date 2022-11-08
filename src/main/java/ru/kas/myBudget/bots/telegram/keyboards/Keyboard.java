package ru.kas.myBudget.bots.telegram.keyboards;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import static ru.kas.myBudget.bots.telegram.callbacks.CallbackName.CANCEL_DIALOG;
import static ru.kas.myBudget.bots.telegram.callbacks.CallbackName.CLOSE;
import static ru.kas.myBudget.bots.telegram.callbacks.CallbackType.NORMAL;

public interface Keyboard {
    InlineKeyboardMarkup getKeyboard();

    default InlineKeyboardButton getButton(String text, String callbackData) {
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setText(text);
        inlineKeyboardButton.setCallbackData(callbackData);
        return inlineKeyboardButton;
    }

    default InlineKeyboardButton getReturnButton(String from, String to) {
        return getButton("<- Назад", String.format("%s_%s_%s", NORMAL.getId(), from, to));
    }

    default InlineKeyboardButton getCloseButton(String from) {
        return getButton("X Закрыть",
                String.format("%s_%s_%s", NORMAL.getId(), from, CLOSE.getCallbackName()));
    }

    default InlineKeyboardButton getCancelDialogButton(String from) {
        return getButton("X Отменить",
                String.format("%s_%s_%s", NORMAL.getId(), from, CANCEL_DIALOG.getCallbackName()));
    }
}
