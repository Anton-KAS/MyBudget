package ru.kas.myBudget.bots.telegram.texts.commands;

import ru.kas.myBudget.bots.telegram.texts.MessageText;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

public class CancelText implements MessageText {
    public CancelText() {
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
