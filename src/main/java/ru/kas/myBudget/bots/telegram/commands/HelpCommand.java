package ru.kas.myBudget.bots.telegram.commands;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kas.myBudget.bots.telegram.services.BotMessageService;
import ru.kas.myBudget.bots.telegram.util.ExecuteMode;
import ru.kas.myBudget.services.TelegramUserService;

import static ru.kas.myBudget.bots.telegram.commands.CommandName.*;

public class HelpCommand implements Command {

    private final BotMessageService sendBotMessageService;
    private final TelegramUserService telegramUserService;

    public final static String HELP_MESSAGE = String.format("""
                    <b>Доступные команды:</b>

                    %s - начать работу со мной
                    %s - открыть меню бота
                    %s - приостановить работу со мной
                    %s - количество активных пользователей бота
                    """,
            START.getCommandName(),
            MENU.getCommandName(),
            STOP.getCommandName(),
            STAT.getCommandName());

    public HelpCommand(BotMessageService sendBotMessageService, TelegramUserService telegramUserService) {
        this.sendBotMessageService = sendBotMessageService;
        this.telegramUserService = telegramUserService;
    }

    @Override
    public void execute(Update update) {
        sendBotMessageService.executeSendMessage(getChatId(update), HELP_MESSAGE);
        checkUserInDb(telegramUserService, update);
    }

    @Override
    public void execute(Update update, ExecuteMode executeMode) {
        execute(update);
    }
}
