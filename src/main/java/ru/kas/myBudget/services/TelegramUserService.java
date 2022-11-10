package ru.kas.myBudget.services;

import ru.kas.myBudget.models.TelegramUser;

import java.util.List;
import java.util.Optional;

public interface TelegramUserService {
    void save(TelegramUser telegramUser);

    void setLastActive(TelegramUser telegramUser);

    void setLastMessage(TelegramUser telegramUser, Long messageId, String messageText);

    List<TelegramUser> retrieveAllActiveUsers();

    Optional<TelegramUser> findById(long id);
}
