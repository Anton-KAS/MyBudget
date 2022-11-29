package komrachkov.anton.mybudget.bots.telegram.texts.commands;

import komrachkov.anton.mybudget.bots.telegram.texts.MessageText;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

public class StatText implements MessageText {
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
