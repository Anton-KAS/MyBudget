package ru.kas.myBudget.bots.telegram.texts;

public class StatText implements MessageText{
    @Override
    public MessageText setUserId(Long userId) {
        return this;
    }

    @Override
    public String getText() {
        return "Ботом пользуеются %s человек(а)";
    }
}
