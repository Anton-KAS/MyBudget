package ru.kas.myBudget.bots.telegram.texts.callback;

import ru.kas.myBudget.bots.telegram.texts.MessageText;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

public class NoText implements MessageText {
    @Override
    public MessageText setUserId(Long userId) {
        return this;
    }

    @Override
    public String getText() {
        return "Что-то пошло не так =(";
    }
}
