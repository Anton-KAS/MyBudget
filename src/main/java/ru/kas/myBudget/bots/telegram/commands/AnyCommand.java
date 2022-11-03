package ru.kas.myBudget.bots.telegram.commands;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kas.myBudget.models.TelegramUser;
import ru.kas.myBudget.services.TelegramUserService;

public class AnyCommand implements Command {
    private final TelegramUserService telegramUserService;

    public AnyCommand(TelegramUserService telegramUserService) {
        this.telegramUserService = telegramUserService;
    }

    @Override
    public void execute(Update update) {
        telegramUserService.findById(getUserId(update)).ifPresentOrElse(
                telegramUserService::save,
                () -> {
                    TelegramUser telegramUser = new TelegramUser(update);
                    telegramUserService.save(telegramUser);
                }
        );
    }
}
