package ru.kas.myBudget.bots.telegram.texts;

public class CancelDialogText implements MessageText {
    public CancelDialogText() {
    }

    @Override
    public MessageText setUserId(Long userId) {
        return this;
    }

    @Override
    public String getText() {
        return "Всё отменил...";
    }
}
