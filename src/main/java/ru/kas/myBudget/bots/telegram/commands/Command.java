package ru.kas.myBudget.bots.telegram.commands;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kas.myBudget.models.TelegramUser;
import ru.kas.myBudget.services.TelegramUserService;

public interface Command {
    void execute(Update update);

    default long getUserId(Update update) {
        return update.getMessage().getFrom().getId();
    }

    default long getChatId(Update update) {
        return update.getMessage().getChatId();
    }

    default void checkUserInDb(TelegramUserService telegramUserService, Update update) {
        telegramUserService.findById(getUserId(update)).ifPresentOrElse(
                telegramUserService::save,
                () -> {
                    TelegramUser telegramUser = new TelegramUser(update);
                    telegramUserService.save(telegramUser);
                }
        );
    }
}
