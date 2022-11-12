package ru.kas.myBudget.bots.telegram.commands;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kas.myBudget.bots.telegram.services.BotMessageService;
import ru.kas.myBudget.bots.telegram.util.CommandController;
import ru.kas.myBudget.bots.telegram.util.ExecuteMode;
import ru.kas.myBudget.bots.telegram.util.UpdateParameter;
import ru.kas.myBudget.services.TelegramUserService;

public class StopCommand implements CommandController {
    private final BotMessageService botMessageService;
    private final TelegramUserService telegramUserService;

    public final static String STOP_MESSAGE = "Пока =(";

    public StopCommand(BotMessageService botMessageService, TelegramUserService telegramUserService) {
        this.botMessageService = botMessageService;
        this.telegramUserService = telegramUserService;
    }

    @Override
    public void execute(Update update) {
        botMessageService.executeAndUpdateUser(telegramUserService, update, ExecuteMode.SEND,
                STOP_MESSAGE, null);

        telegramUserService.findById(UpdateParameter.getUserId(update)).ifPresent(
                user -> {
                    user.setActive(false);
                    telegramUserService.save(user);
                }
        );
    }

    @Override
    public void execute(Update update, ExecuteMode executeMode) {
        execute(update);
    }
}
