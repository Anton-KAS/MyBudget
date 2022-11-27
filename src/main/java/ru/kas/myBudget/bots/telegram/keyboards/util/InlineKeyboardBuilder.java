package ru.kas.myBudget.bots.telegram.keyboards.util;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static ru.kas.myBudget.bots.telegram.callbacks.CallbackNamesImpl.CANCEL_DIALOG;
import static ru.kas.myBudget.bots.telegram.callbacks.CallbackNamesImpl.CLOSE;
import static ru.kas.myBudget.bots.telegram.callbacks.util.CallbackOperator.*;
import static ru.kas.myBudget.bots.telegram.callbacks.util.CallbackType.DIALOG;
import static ru.kas.myBudget.bots.telegram.callbacks.util.CallbackType.NORMAL;
import static ru.kas.myBudget.bots.telegram.dialogs.util.DialogMapDefaultName.NEXT;
import static ru.kas.myBudget.bots.telegram.dialogs.util.DialogMapDefaultName.PAGE;
import static ru.kas.myBudget.bots.telegram.dialogs.DialogNamesImpl.DELETE_CONFIRM;
import static ru.kas.myBudget.bots.telegram.dialogs.DialogNamesImpl.DELETE_EXECUTE;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

public class InlineKeyboardBuilder {
    private List<List<InlineKeyboardButton>> rowsInline;
    private List<InlineKeyboardButton> currentRow;
    private static final String EASY_MOVING_CALLBACK_PATTERN = "%s_%s_%s";
    private static final String DIALOG_CALLBACK_PATTERN = "%s_%s_%s_%s_%s";
    private static final String EXTRA_DIALOG_CALLBACK_PATTERN = "%s_%s_%s_%s_%s_%s";

    public InlineKeyboardBuilder() {
    }

    public InlineKeyboardBuilder addRow() {
        if (rowsInline == null) {
            rowsInline = new ArrayList<>();
        }
        if (currentRow != null) {
            if (currentRow.size() != 0) {
                rowsInline.add(currentRow);
            }
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
    /*
    NORMAL CALLBACK BUTTONS
     */

    public InlineKeyboardBuilder addReturnButton(String from, String to) {
        String buttonText = "<- Назад";
        String buttonCallback = String.format(EASY_MOVING_CALLBACK_PATTERN, NORMAL.getId(), from, to);
        addButton(buttonText, buttonCallback);
        return this;
    }

    public InlineKeyboardBuilder addCloseButton(String from) {
        String buttonText = "X Закрыть";
        String buttonCallback = String.format(EASY_MOVING_CALLBACK_PATTERN, NORMAL.getId(), from, CLOSE.getName());
        addButton(buttonText, buttonCallback);
        return this;
    }

    /*
    DIALOG CALLBACK BUTTONS
     */
    public InlineKeyboardBuilder addNextButton(String fromDialog, String fromStep) {
        String buttonText = "Пропустить ➡️";
        String buttonCallback = String.format(DIALOG_CALLBACK_PATTERN,
                DIALOG.getId(), fromDialog, fromDialog, fromStep, NEXT.getId());
        addButton(buttonText, buttonCallback);
        return this;
    }

    public InlineKeyboardBuilder addCancelDialogButton(String from) {
        String buttonText = "⛔️  Отменить";
        String buttonCallback = String.format(EASY_MOVING_CALLBACK_PATTERN,
                NORMAL.getId(), from, CANCEL_DIALOG.getName());
        addButton(buttonText, buttonCallback);
        return this;
    }

    public InlineKeyboardBuilder addPreviousPageButton(String fromDialog, String fromStep, int toPage) {
        String buttonText = "◀️";
        String buttonCallback = String.format(EXTRA_DIALOG_CALLBACK_PATTERN,
                DIALOG.getId(), fromDialog, fromDialog, fromStep, PAGE.getId(), toPage);
        addButton(buttonText, buttonCallback);
        return this;
    }

    public InlineKeyboardBuilder addNextPageButton(String fromDialog, String fromStep, int toPage) {
        String buttonText = "▶️";
        String buttonCallback = String.format(EXTRA_DIALOG_CALLBACK_PATTERN,
                DIALOG.getId(), fromDialog, fromDialog, fromStep, PAGE.getId(), toPage);
        addButton(buttonText, buttonCallback);
        return this;
    }

    public InlineKeyboardBuilder addSaveButton(String fromDialog, String fromStep) {
        String buttonText = "\uD83D\uDCBE  Сохранить";
        String buttonCallback = String.format(DIALOG_CALLBACK_PATTERN,
                DIALOG.getId(), fromDialog, fromDialog, fromStep, SAVE.getId());
        addButton(buttonText, buttonCallback);
        return this;
    }

    public InlineKeyboardBuilder addDeleteButton(String returnTo, String fromDialog, String fromStep, String idToDelete) {
        String buttonText = "\uD83E\uDDE8  Удалить";
        String buttonCallback = String.format(EXTRA_DIALOG_CALLBACK_PATTERN,
                DIALOG.getId(), returnTo, DELETE_CONFIRM.getName(), fromDialog, fromStep, idToDelete);
        addButton(buttonText, buttonCallback);
        return this;
    }

    public InlineKeyboardBuilder addExecuteDeleteButton(String fromDialog, String idToDelete) {
        String buttonText = "\uD83E\uDDE8  Удалить";
        String buttonCallback = String.format(DIALOG_CALLBACK_PATTERN,
                DIALOG.getId(), fromDialog, DELETE_EXECUTE.getName(), DELETE.getId(), idToDelete);
        addButton(buttonText, buttonCallback);
        return this;
    }

    public InlineKeyboardBuilder addCancelDeleteButton(String fromMenu, String fromDialog, String fromStep) {
        String buttonText = "Отмена";
        String buttonCallback = String.format(DIALOG_CALLBACK_PATTERN,
                DIALOG.getId(), fromMenu, fromDialog, fromStep, CANCEL.getId());
        addButton(buttonText, buttonCallback);
        return this;
    }

    public InlineKeyboardMarkup build() {
        if (currentRow != null) {
            if (currentRow.size() != 0) {
                addRow();
            }
        }
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(rowsInline);
        return inlineKeyboardMarkup;
    }
}
