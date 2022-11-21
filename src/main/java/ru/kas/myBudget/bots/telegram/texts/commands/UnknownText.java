package ru.kas.myBudget.bots.telegram.texts.commands;

import ru.kas.myBudget.bots.telegram.texts.MessageText;

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
