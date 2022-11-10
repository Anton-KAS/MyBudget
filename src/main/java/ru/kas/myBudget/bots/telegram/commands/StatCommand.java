package ru.kas.myBudget.bots.telegram.commands;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kas.myBudget.bots.telegram.services.BotMessageService;
import ru.kas.myBudget.bots.telegram.util.ExecuteMode;
import ru.kas.myBudget.services.TelegramUserService;

public class StatCommand implements Command {

    private final BotMessageService botMessageService;
    private final TelegramUserService telegramUserService;

    public final static String STAT_MESSAGE = "Ботом пользуеются %s человек(а)";

    public StatCommand(BotMessageService botMessageService, TelegramUserService telegramUserService) {
        this.botMessageService = botMessageService;
        this.telegramUserService = telegramUserService;
    }

    @Override
    public void execute(Update update) {
        int activeUserCount = telegramUserService.retrieveAllActiveUsers().size();

        sendAndUpdateUser(telegramUserService, botMessageService, update, ExecuteMode.SEND,
                String.format(STAT_MESSAGE, activeUserCount), null);
    }

    @Override
    public void execute(Update update, ExecuteMode executeMode) {
        execute(update);
    }
}
