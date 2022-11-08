package ru.kas.myBudget.bots.telegram.commands;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kas.myBudget.bots.telegram.services.BotMessageService;
import ru.kas.myBudget.bots.telegram.util.ExecuteMode;

public class UnknownCommand implements Command {

    private final BotMessageService sendBotMessageService;

    public final static String UNKNOWN_MESSAGE = "" +
            "Не понимаю вас =(\n" +
            "Напишите /help чтобы узнать что я понимаю.";

    public UnknownCommand(BotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        sendBotMessageService.executeSendMessage(getUserId(update), UNKNOWN_MESSAGE);
    }

    @Override
    public void execute(Update update, ExecuteMode executeMode) {
        execute(update);
    }
}
