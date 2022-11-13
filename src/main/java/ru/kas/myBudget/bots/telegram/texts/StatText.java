package ru.kas.myBudget.bots.telegram.texts;

public class StatText implements MessageText{
    private int activeUserCount;
    @Override
    public StatText setUserId(Long userId) {
        return this;
    }

    @Override
    public String getText() {
        return String.format("Ботом пользуеются %s человек(а)", activeUserCount);
    }

    public StatText setActiveUserCount(int activeUserCount) {
        this.activeUserCount = activeUserCount;
        return this;
    }
}
