package ru.kas.myBudget.bots.telegram.texts.commands;

import ru.kas.myBudget.bots.telegram.texts.MessageText;

/**
 * @since 0.2
 * @author Anton Komrachkov
 */

public class UnknownText implements MessageText {
    @Override
    public MessageText setUserId(Long userId) {
        return this;
    }

    @Override
    public String getText() {
        return "Не понимаю вас =(\n" +
                "Напишите /help чтобы узнать что я понимаю.";
    }
}
