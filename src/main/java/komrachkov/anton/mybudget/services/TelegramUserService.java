package komrachkov.anton.mybudget.services;

import org.telegram.telegrambots.meta.api.objects.Update;
import komrachkov.anton.mybudget.models.TelegramUser;

import java.util.List;
import java.util.Optional;

/**
 * @author Anton Komrachkov
 * @since 0.1
 */

public interface TelegramUserService {
    void save(TelegramUser telegramUser);

    void setLastActive(TelegramUser telegramUser);

    void setLastMessage(TelegramUser telegramUser, Integer messageId, String messageText);

    void checkUser(TelegramUserService telegramUserService, Update update);

    List<TelegramUser> retrieveAllActiveUsers();

    Optional<TelegramUser> findById(long id);
}
