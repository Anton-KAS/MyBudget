package ru.kas.myBudget.services;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kas.myBudget.models.TelegramUser;

import java.util.List;
import java.util.Optional;

public interface TelegramUserService {
    void save(TelegramUser telegramUser);

    void setLastActive(TelegramUser telegramUser);

    void setLastMessage(TelegramUser telegramUser, Long messageId, String messageText);

    void checkUser(TelegramUserService telegramUserService, Update update);

    List<TelegramUser> retrieveAllActiveUsers();

    Optional<TelegramUser> findById(long id);
}
