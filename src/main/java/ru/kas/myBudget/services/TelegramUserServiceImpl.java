package ru.kas.myBudget.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kas.myBudget.bots.telegram.util.UpdateParameter;
import ru.kas.myBudget.models.TelegramUser;
import ru.kas.myBudget.repositories.TelegramUserRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @since 0.1
 * @author Anton Komrachkov
 */

@Service
@Transactional(readOnly = true)
public class TelegramUserServiceImpl implements TelegramUserService {

    private final TelegramUserRepository telegramUserRepository;

    @Autowired
    public TelegramUserServiceImpl(TelegramUserRepository telegramUserRepository) {
        this.telegramUserRepository = telegramUserRepository;
    }

    @Override
    @Transactional
    public void save(TelegramUser telegramUser) {
        if (telegramUser.getCreatedAt() == null) telegramUser.setCreatedAt(new Date());
        telegramUser.setLastActive(new Date());
        telegramUserRepository.save(telegramUser);
    }

    @Override
    @Transactional
    public void setLastActive(TelegramUser telegramUser) {
        telegramUser.setLastActive(new Date());
        telegramUserRepository.save(telegramUser);
    }

    @Override
    @Transactional
    public void setLastMessage(TelegramUser telegramUser, Integer messageId, String messageText) {
        if (telegramUser == null || messageId == null) return;
        telegramUser.setLastMessageId(messageId);
        telegramUser.setLastMessageText(messageText);
        telegramUser.setLastMessageTimestamp(new Date());
        telegramUserRepository.save(telegramUser);
    }

    @Override
    @Transactional
    public void checkUser(TelegramUserService telegramUserService, Update update) {
        telegramUserService.findById(UpdateParameter.getUserId(update)).ifPresentOrElse(
                user -> {
                    user.setActive(true);
                    telegramUserService.save(user);
                },
                () -> {
                    TelegramUser telegramUser = new TelegramUser(update);
                    telegramUserService.save(telegramUser);
                }
        );
    }

    @Override
    public List<TelegramUser> retrieveAllActiveUsers() {
        return telegramUserRepository.findAllByActiveTrue();
    }

    @Override
    public Optional<TelegramUser> findById(long id) {
        return telegramUserRepository.findById(id);
    }
}
