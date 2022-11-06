package ru.kas.myBudget.bots.telegram.callbacks;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kas.myBudget.services.TelegramUserService;

public interface Callback {
    void execute(Update update);

    default long getUserId(Update update) {
        return update.getCallbackQuery().getFrom().getId();
    }

    default long getChatId(Update update) {
        return update.getCallbackQuery().getMessage().getChatId();
    }

    default long getMessageId(Update update) {
        return update.getCallbackQuery().getMessage().getMessageId();
    }

    default String[] getCallbackData(Update update) {
        return update.getCallbackQuery().getData().split("_");
    }

    default void updateUserLastActiveInDb(TelegramUserService telegramUserService, Update update) {
        telegramUserService.findById(getUserId(update)).ifPresentOrElse(
                telegramUserService::save,
                () -> {
                    return;
                }
        );
    }
}
