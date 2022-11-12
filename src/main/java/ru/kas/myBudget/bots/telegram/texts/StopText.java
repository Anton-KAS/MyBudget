package ru.kas.myBudget.bots.telegram.texts;

public class StopText implements MessageText{
    @Override
    public MessageText setUserId(Long userId) {
        return this;
    }

    @Override
    public String getText() {
        return "Пока =(";
    }
}
