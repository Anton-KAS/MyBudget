package ru.kas.myBudget.bots.telegram.texts.accountDialog;

import ru.kas.myBudget.bots.telegram.texts.MessageText;

/**
 * @since 0.2
 * @author Anton Komrachkov
 */

public class SaveText implements MessageText {
    @Override
    public MessageText setUserId(Long userId) {
        return this;
    }

    @Override
    public String getText() {
        return "всё сохранил";
    }
}
