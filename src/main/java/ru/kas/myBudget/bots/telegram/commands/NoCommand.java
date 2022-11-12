package ru.kas.myBudget.bots.telegram.commands;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kas.myBudget.bots.telegram.services.BotMessageService;
import ru.kas.myBudget.bots.telegram.util.CommandController;
import ru.kas.myBudget.bots.telegram.util.ExecuteMode;
import ru.kas.myBudget.services.TelegramUserService;

public class NoCommand implements CommandController {
    private final BotMessageService botMessageService;
    private final TelegramUserService telegramUserService;

    public final static String NO_MESSAGE =
            "Я пока не умею распознавать простой текст, но умею понимать команды из списка: /help";

    public NoCommand(BotMessageService botMessageService, TelegramUserService telegramUserService) {
        this.botMessageService = botMessageService;
        this.telegramUserService = telegramUserService;
    }

    @Override
    public void execute(Update update) {
        botMessageService.executeAndUpdateUser(telegramUserService, update, ExecuteMode.SEND, NO_MESSAGE, null);
    }

    @Override
    public void execute(Update update, ExecuteMode executeMode) {
        execute(update);
    }
}
