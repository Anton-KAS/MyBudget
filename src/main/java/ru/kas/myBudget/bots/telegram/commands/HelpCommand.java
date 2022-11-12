package ru.kas.myBudget.bots.telegram.commands;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kas.myBudget.bots.telegram.services.BotMessageService;
import ru.kas.myBudget.bots.telegram.util.CommandController;
import ru.kas.myBudget.bots.telegram.util.ExecuteMode;
import ru.kas.myBudget.services.TelegramUserService;

import static ru.kas.myBudget.bots.telegram.commands.CommandName.*;

public class HelpCommand implements CommandController {
    private final BotMessageService botMessageService;
    private final TelegramUserService telegramUserService;
    private final ExecuteMode executeMode;

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

    public HelpCommand(BotMessageService botMessageService, TelegramUserService telegramUserService,
                       ExecuteMode executeMode) {
        this.botMessageService = botMessageService;
        this.telegramUserService = telegramUserService;
        this.executeMode = executeMode;
    }

    @Override
    public void execute(Update update) {
        botMessageService.executeAndUpdateUser(telegramUserService, update, executeMode, HELP_MESSAGE, null);
    }

    @Override
    public void execute(Update update, ExecuteMode executeMode) {
        execute(update);
    }
}
