package ru.kas.myBudget.bots.telegram.texts.addAccountDialog;

import ru.kas.myBudget.bots.telegram.texts.MessageText;

public class SaveDialogText implements MessageText {
    @Override
    public MessageText setUserId(Long userId) {
        return this;
    }

    @Override
    public String getText() {
        return "всё сохранил";
    }
}
