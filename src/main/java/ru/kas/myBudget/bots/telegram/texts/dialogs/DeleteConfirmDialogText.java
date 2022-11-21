package ru.kas.myBudget.bots.telegram.texts.dialogs;

import ru.kas.myBudget.bots.telegram.texts.MessageText;

public class DeleteConfirmDialogText implements MessageText {
    @Override
    public MessageText setUserId(Long userId) {
        return this;
    }

    @Override
    public String getText() {
        return "Удалить раз и навсегда?";
    }
}
