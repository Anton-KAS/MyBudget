package ru.kas.myBudget.bots.telegram.texts;

import ru.kas.myBudget.models.TelegramUser;
import ru.kas.myBudget.services.TelegramUserService;

import java.util.Optional;

public class MenuText implements MessageText {
    private final TelegramUserService telegramUserService;
    private final Long userId;

    public MenuText(TelegramUserService telegramUserService, Long userId) {
        this.telegramUserService = telegramUserService;
        this.userId = userId;
    }

    @Override
    public String getText() {
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
