package ru.kas.myBudget.bots.telegram.commands;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kas.myBudget.bots.telegram.services.BotMessageService;
import ru.kas.myBudget.bots.telegram.util.ExecuteMode;

public class NoCommand implements Command {

    private final BotMessageService sendBotMessageService;

    public final static String NO_MESSAGE =
            "Я пока не умею распознавать простой текст, но умею понимать команды из списка: /help";

    public NoCommand(BotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        sendBotMessageService.executeSendMessage(getChatId(update), NO_MESSAGE);
    }

    @Override
    public void execute(Update update, ExecuteMode executeMode) {
        execute(update);
    }
}
