package ru.kas.myBudget.bots.telegram.texts.commands;

import ru.kas.myBudget.bots.telegram.texts.MessageText;
import ru.kas.myBudget.models.TelegramUser;
import ru.kas.myBudget.services.TelegramUserService;

import java.util.Optional;

/**
 * @since 0.2
 * @author Anton Komrachkov
 */

public class MenuText implements MessageText {
    private final TelegramUserService telegramUserService;
    private Long userId;

    public MenuText(TelegramUserService telegramUserService) {
        this.telegramUserService = telegramUserService;
    }

    @Override
    public MenuText setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    @Override
    public String getText() {
        checkUserIdSet(userId);

        Optional<TelegramUser> telegramUser = telegramUserService.findById(userId);
        int accountCount = 0;
        if (telegramUser.isPresent()) {
            accountCount = telegramUser.get().getAccounts().size();
        }
        StringBuilder accountText = new StringBuilder();
        switch (accountCount) {
            case 0 -> accountText.append("нет счетов");
            case 1 -> accountText.append(accountCount).append(" счет");
            case 2, 3, 4 -> accountText.append(accountCount).append(" счета");
            default -> accountText.append(accountCount).append(" счетов");
        }
        return String.format("Меню:\n" +
                "У вас %s", accountText);
    }
}
