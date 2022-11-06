package ru.kas.myBudget.bots.telegram.texts;

public class CancelDialogText implements MessageText {

    public CancelDialogText() {
    }

    @Override
    public String getText() {
        return "Всё отменил...";
    }
}
