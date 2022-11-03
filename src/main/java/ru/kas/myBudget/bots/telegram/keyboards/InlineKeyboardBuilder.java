package ru.kas.myBudget.bots.telegram.keyboards;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class InlineKeyboardBuilder {
    private List<List<InlineKeyboardButton>> rowsInline;
    private List<InlineKeyboardButton> currentRow;

    public InlineKeyboardBuilder() {
    }

    public InlineKeyboardBuilder addRow() {
        if (rowsInline == null) {
            rowsInline = new ArrayList<>();
        }
        if (currentRow.size() != 0) {
            rowsInline.add(currentRow);
        }
        currentRow = new ArrayList<>();
        return this;
    }

    public InlineKeyboardBuilder addButton(String text, String callbackData) {
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setText(text);
        inlineKeyboardButton.setCallbackData(callbackData);
        currentRow.add(inlineKeyboardButton);
        return this;
    }

    public InlineKeyboardBuilder addButton(InlineKeyboardButton inlineKeyboardButton) {
        currentRow.add(inlineKeyboardButton);
        return this;
    }

    public InlineKeyboardMarkup build() {
        if (currentRow.size() != 0) {
            addRow();
        }
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(rowsInline);
        return inlineKeyboardMarkup;
    }
}
